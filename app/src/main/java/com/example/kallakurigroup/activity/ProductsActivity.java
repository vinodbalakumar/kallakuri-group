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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kallakurigroup.R;
import com.example.kallakurigroup.adapters.ProductsAdapter;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.databinding.ActivityProductsBinding;
import com.example.kallakurigroup.listeners.ProductItemListener;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements ProductItemListener {

    private List<ProductDetails> productsList;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.shp_cart)
    ImageView shp_cart;

    @BindView(R.id.header_text)
    TextView header_text;

    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;

    @BindView(R.id.rl_noDataFound)
    RelativeLayout rlNoDataFound;

    @BindView(R.id.textTotAmount)
    TextView textTotAmount;

    @BindView(R.id.cart_text_number)
    TextView textCartCount;

    @BindView(R.id.ll_bottom_amount)
    LinearLayout llBottomAmount;

    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;

    ProductTableDAO productTableDAO;

    Context context;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";

    ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products);

        context = this;

        ButterKnife.bind(this);

        rl_cart.setVisibility(View.VISIBLE);

        sharedpreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        productTableDAO = new ProductTableDAO(this);

        header_text.setText(getIntent().getStringExtra("brand_name"));

        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //productsList = this.getIntent().getExtras().getParcelableArrayList("products");


        productsList = productTableDAO.getProductByBrandId(getIntent().getStringExtra("brand_id"));

        if(productsList!=null && productsList.size()>0){
            productRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            productsAdapter = new ProductsAdapter(productsList, ProductsActivity.this, context);
            productRecyclerView.setAdapter(productsAdapter);
        }else {
            productRecyclerView.setVisibility(View.GONE);
            rlNoDataFound.setVisibility(View.VISIBLE);
        }

        //loadAnimation(llBrands);
        loadAnimation(productRecyclerView);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shp_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textCartCount.getText().toString()!=null && !textCartCount.getText().toString().equalsIgnoreCase("0")) {
                    startActivity(new Intent(ProductsActivity.this, CartActivity.class));
                }            }
        });

        setDataCartAmount();
    }

    @Override
    public void productSelected(int position) {
        startActivityForResult(new Intent(context, ProductsDetailsActivity.class).putExtra("product_id", productsList.get(position).getId()), 100);
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

        float totalAmount = Float.parseFloat(textTotAmount.getText().toString());

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
            textCartCount.setText(String.valueOf(sharedpreferences.getInt("cart_count", 0)));
            textCartCount.setVisibility(View.VISIBLE);
        }else {
            textCartCount.setVisibility(View.GONE);
        }

        if(sharedpreferences.contains("total_amount") && sharedpreferences.getFloat("total_amount", 0)!=0){
            textTotAmount.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
            llBottomAmount.setVisibility(View.VISIBLE);
        }else {
            llBottomAmount.setVisibility(View.GONE);
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
