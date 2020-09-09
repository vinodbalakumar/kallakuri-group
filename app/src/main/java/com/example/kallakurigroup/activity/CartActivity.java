package com.example.kallakurigroup.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.stmt.query.In;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements CartItemListener {

    private List<ProductDetails> productsList = new ArrayList<>();

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    /*@BindView(R.id.back_arrow)
    ImageView back_arrow;*/

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

    @BindView(R.id.imageRightArrow)
    ImageView imageRightArrow;

    @BindView(R.id.rl_main)
    RelativeLayout rl_main;

    @BindView(R.id.ll_noDataFound)
    LinearLayout ll_noDataFound;

    @BindView(R.id.textShop)
    TextView textShop;

    ProductTableDAO productTableDAO;

    Context context;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";

    CartAdapter cartAdapter;

    List<String> cartList = new ArrayList<>();

    String fromType = "Home";

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

        //back_arrow.setImageDrawable(getResources().getDrawable(R.drawable.home_icon));

        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //productsList = this.getIntent().getExtras().getParcelableArrayList("products");


        List<ProductDetails> productDetailsList = productTableDAO.getProductsCart();

        for (ProductDetails productDetails:productDetailsList){
            if(!productDetails.getSelectedQty().equalsIgnoreCase("0")){
                productsList.add(productDetails);
            }
        }

        if(productsList!=null && productsList.size()>0){
            rl_main.setVisibility(View.VISIBLE);
            ll_noDataFound.setVisibility(View.GONE);
            productRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            cartAdapter = new CartAdapter(productsList, CartActivity.this, context);
            productRecyclerView.setAdapter(cartAdapter);
        }else {
            rl_main.setVisibility(View.GONE);
            ll_noDataFound.setVisibility(View.VISIBLE);
        }

        fromType = getIntent().getStringExtra("from");
        //loadAnimation(llBrands);
        loadAnimation(productRecyclerView);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              backToPage();
            }
        });

        ll_shop_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              backToHome();
            }
        });

        textShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });

        rl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, OrderPaymentsActivity.class));
            }
        });

        imageRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, OrderPaymentsActivity.class));
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

        float totalAmount = Float.parseFloat(amount_final.getText().toString());

        if(type.equalsIgnoreCase("plus")){
            totalAmount = totalAmount+prodPrice;
        }else if(type.equalsIgnoreCase("delete")){
            totalAmount = totalAmount-prodPrice;
        }else {
            totalAmount = totalAmount-prodPrice;
        }

        if(!cartList.contains(String.valueOf(productsList.get(position).getId()))){
            cartList.add(String.valueOf(productsList.get(position).getId()));
        }else if(selectedCount==0){
            cartList.remove(String.valueOf(productsList.get(position).getId()));
        }

        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("cart_count", json);
        editor.apply();

        editor.putFloat("total_amount", totalAmount).apply();
        editor.commit();

        if(cartList.size()>0) {
            setDataCartAmount();
        }else {
            amount_final.setText("0.0");
            sub_total_amount.setText("0.0");
            backToHome();
        }
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
            Gson gson = new Gson();
            String json = sharedpreferences.getString("cart_count", null);
        try {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            cartList = gson.fromJson(json, type);

            if (cartList != null && cartList.size() > 0) {
                textCartCount.setText(String.valueOf(cartList.size()));
                // textCartCount.setVisibility(View.VISIBLE);
            } else {
                cartList = new ArrayList<>();
                textCartCount.setVisibility(View.GONE);
            }

            if (sharedpreferences.contains("total_amount") && sharedpreferences.getFloat("total_amount", 0) != 0) {
                amount_final.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
                sub_total_amount.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        backToPage();
    }


    void backToHome(){
        Intent i = new Intent(CartActivity.this, Homepage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    void backToPage(){
        if(fromType.equalsIgnoreCase("prodAct")){
            Intent returnIntent = new Intent();
            setResult(100, returnIntent);
            finish();
        }else if(fromType.equalsIgnoreCase("prodDet")){
            Intent returnIntent = new Intent();
            setResult(200, returnIntent);
            finish();
        }else {
            finish();
        }
    }
}
