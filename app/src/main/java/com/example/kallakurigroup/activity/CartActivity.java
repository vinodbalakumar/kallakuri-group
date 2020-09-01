package com.example.kallakurigroup.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.adapters.CartAdapter;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.listeners.CartItemListener;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements CartItemListener {

    private List<ProductDetails> productsList = new ArrayList<>();

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.header_text)
    TextView header_text;

    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;

    @BindView(R.id.cart_text_number)
    TextView textCartCount;

    @BindView(R.id.amount_final)
    TextView amount_final;

    @BindView(R.id.sub_total_amount)
    TextView sub_total_amount;

    @BindView(R.id.delChargesAmount)
    TextView delChargesAmount;

    @BindView(R.id.GSTChargesAmount)
    TextView GSTChargesAmount;

    @BindView(R.id.ll_shop_more)
    LinearLayout ll_shop_more;

    @BindView(R.id.rl_checkout)
    RelativeLayout rl_checkout;

    ProductTableDAO productTableDAO;

    Context context;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";

    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);

        context = this;

        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        productTableDAO = new ProductTableDAO(this);

        header_text.setText(getResources().getString(R.string.cart));

        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //productsList = this.getIntent().getExtras().getParcelableArrayList("products");


        List<ProductDetails> productDetailsList = productTableDAO.getProductsCart();

        for (ProductDetails productDetails:productDetailsList){
            if(!productDetails.getSelectedQty().equalsIgnoreCase("0")){
                productsList.add(productDetails);
            }
        }

        if(productsList!=null && productsList.size()>0){
            productRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            cartAdapter = new CartAdapter(productsList, CartActivity.this, context);
            productRecyclerView.setAdapter(cartAdapter);
        }

        //loadAnimation(llBrands);
        loadAnimation(productRecyclerView);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ll_shop_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, Homepage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        setDataCartAmount();

    }

    @Override
    public void quantityCountChanges(int position, int selectedCount, float selectedPrice, float prodPrice, String type) {

        ContentValues values = new ContentValues();
        values.put("selectedQty", selectedCount);
        productTableDAO.updateRow("ProductDetails", values, "Product_Id", productsList.get(position).getId());

        ContentValues value1 = new ContentValues();
        value1.put("selectedPrice", String.valueOf(selectedPrice));
        productTableDAO.updateRow("ProductDetails", value1, "Product_Id", productsList.get(position).getId());

        int totCartCount = Integer.parseInt(textCartCount.getText().toString());

        float totalAmount = Float.parseFloat(amount_final.getText().toString());

        if(type.equalsIgnoreCase("plus")){
            totalAmount = totalAmount+prodPrice;
            totCartCount = totCartCount+1;
        }else {
            totalAmount = totalAmount-prodPrice;
            totCartCount = totCartCount-1;
        }

      /*  textCartCount.setText(String.valueOf(totCartCount));
        textTotAmount.setText(String.valueOf(totalAmount));*/

        editor.putInt("cart_count", totCartCount).apply();
        editor.putFloat("total_amount", totalAmount).apply();
        editor.commit();

        setDataCartAmount();
    }

   /* @Override
    public void quantityChanged() {
        setDataCartAmount();
    }*/

    private void loadAnimation(ViewGroup view) {
        Context context = view.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_right_slide);
        view.setLayoutAnimation(layoutAnimationController);
    }

    void setDataCartAmount(){
        if(sharedpreferences.contains("cart_count") && sharedpreferences.getInt("cart_count", 0)!=0){
            amount_final.setText(String.valueOf(sharedpreferences.getInt("cart_count", 0)));
            textCartCount.setVisibility(View.VISIBLE);
        }else {
            textCartCount.setVisibility(View.GONE);
        }

        if(sharedpreferences.contains("total_amount") && sharedpreferences.getFloat("total_amount", 0)!=0){
            amount_final.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {

            if (resultCode == 100) {
                setDataCartAmount();
                //productsAdapter.notifyDataSetChanged();
               /* productsAdapter = new ProductsAdapter(productsList, ProductsActivity.this, context);
                productRecyclerView.setAdapter(productsAdapter);*/
            }

        }
    }//onActivityResult

}
