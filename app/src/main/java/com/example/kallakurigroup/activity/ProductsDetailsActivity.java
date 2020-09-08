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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kallakurigroup.R;
import com.example.kallakurigroup.adapters.ProductsAdapter;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.stmt.query.In;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsDetailsActivity extends AppCompatActivity {

    private List<ProductDetails> productsList;

    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.shp_cart)
    ImageView shp_cart;

    @BindView(R.id.header_text)
    TextView header_text;

    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.prodImage)
    ImageView prodImage;

    @BindView(R.id.prodName)
    TextView prodName;

    @BindView(R.id.ProductOfferPrice)
    TextView ProductOfferPrice;

    @BindView(R.id.prodCost)
    TextView prodCost;

    @BindView(R.id.prodDiscount)
    TextView prodDiscount;

    @BindView(R.id.productQty)
    TextView productQty;

    @BindView(R.id.quantity_count)
    TextView quantity_count;

    @BindView(R.id.prodDiscription)
    TextView prodDiscription;

    @BindView(R.id.prodDeliveryCharges)
    TextView prodDeliveryCharges;

    @BindView(R.id.prodDeliveryTime)
    TextView prodDeliveryTime;

    @BindView(R.id.prodBrand)
    TextView prodBrand;

    @BindView(R.id.plus)
    ImageView plus;

    @BindView(R.id.minus)
    ImageView minus;

    @BindView(R.id.inner_lt)
    LinearLayout inner_lt;

    @BindView(R.id.productAdd)
    TextView textProductAdd;

    @BindView(R.id.textTotAmount)
    TextView textTotAmount;

    @BindView(R.id.cart_text_number)
    TextView textCartCount;

    @BindView(R.id.rl_bottom_amount)
    RelativeLayout rlBottomAmount;

    @BindView(R.id.imageRightArrow)
    ImageView imageRightArrow;

    ProductTableDAO productTableDAO;

    Context context;

    int productId = 0;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";

    List<String> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products_details);

        context = this;

        ButterKnife.bind(this);

        rl_cart.setVisibility(View.VISIBLE);

        sharedpreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        productTableDAO = new ProductTableDAO(this);

        header_text.setText("");

        productId = getIntent().getExtras().getInt("product_id");

        //loadAnimation(llBrands);
        loadAnimation(llMain);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivityPage();
            }
        });

        shp_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(textCartCount.getText().toString()!=null && !textCartCount.getText().toString().equalsIgnoreCase("0")) {
                    startActivityForResult(new Intent(ProductsDetailsActivity.this, CartActivity.class).putExtra("from","prodDet"), 200);
               // }
            }
        });

        textCartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if(textCartCount.getText().toString()!=null && !textCartCount.getText().toString().equalsIgnoreCase("0")) {
                    startActivityForResult(new Intent(ProductsDetailsActivity.this, CartActivity.class).putExtra("from","prodDet"), 200);
               // }
            }
        });

        textProductAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inner_lt.setVisibility(View.VISIBLE);
                textProductAdd.setVisibility(View.GONE);
                int selectedCount = 1;
                float prodPrice = Float.parseFloat(ProductOfferPrice.getText().toString());
                float selectedPrice = prodPrice*selectedCount;
                quantity_count.setText(String.valueOf(selectedCount));
                updateCartCountandTotAmount("plus", prodPrice, selectedCount, selectedPrice);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedCount = Integer.parseInt(quantity_count.getText().toString());
                float prodPrice = Float.parseFloat(ProductOfferPrice.getText().toString());
                if(selectedCount!=10) {
                    selectedCount = selectedCount + 1;
                    float selectedPrice = prodPrice*selectedCount;
                    quantity_count.setText(String.valueOf(selectedCount));
                    updateCartCountandTotAmount("plus", prodPrice, selectedCount, selectedPrice);
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedCount = Integer.parseInt(quantity_count.getText().toString());
                float prodPrice = Float.parseFloat(ProductOfferPrice.getText().toString());
                if(selectedCount==1) {
                    inner_lt.setVisibility(View.GONE);
                    textProductAdd.setVisibility(View.VISIBLE);
                }
                selectedCount = selectedCount - 1;
                float selectedPrice = prodPrice*selectedCount;
                quantity_count.setText(String.valueOf(selectedCount));
                updateCartCountandTotAmount("minus", prodPrice, selectedCount, selectedPrice);
            }
        });

        rlBottomAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ProductsDetailsActivity.this, CartActivity.class).putExtra("from","prodDet"), 100);
            }
        });

        imageRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ProductsDetailsActivity.this, CartActivity.class).putExtra("from","prodDet"), 100);
            }
        });

        setData();
        setDataCartAmount();
    }

   void setData(){
        if(productsList!=null && productsList.size()>0){
            productsList.clear();
        }
       productsList = productTableDAO.getProductbyId(productId);

        ProductDetails prodDetails = productsList.get(0);
        Glide.with(prodImage).load(prodDetails.getProductImage())/*.apply(RequestOptions.circleCropTransform())*/.into(prodImage);

       if(prodDetails.getSelectedQty()!=null && !prodDetails.getSelectedQty().equalsIgnoreCase("0")){
           textProductAdd.setVisibility(View.GONE);
           inner_lt.setVisibility(View.VISIBLE);
       }

       quantity_count.setText(prodDetails.getSelectedQty());

       prodName.setText(prodDetails.getProductName());

       ProductOfferPrice.setText(prodDetails.getProductFinalPrice());

       prodCost.setText(prodDetails.getProductCost());

       prodDiscount.setText(prodDetails.getProductDiscount());

       productQty.setText(prodDetails.getProductQuantity());

       prodDiscription.setText(prodDetails.getProductDescription());

       prodDeliveryCharges.setText(prodDetails.getDeliveryCharge());

       prodDeliveryTime.setText(prodDetails.getDeliveryTime());

      prodBrand.setText(prodDetails.getProductBrand());
    }

    void setDataCartAmount(){
        Gson gson = new Gson();
        String json = sharedpreferences.getString("cart_count", null);
        try{
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            cartList = gson.fromJson(json, type);

            if(cartList!=null && cartList.size()>0){
                textCartCount.setText(String.valueOf(cartList.size()));
                textCartCount.setVisibility(View.VISIBLE);
            }else {
                cartList = new ArrayList<>();
                textCartCount.setVisibility(View.GONE);
            }

            if(sharedpreferences.contains("total_amount") && sharedpreferences.getFloat("total_amount", 0)!=0){
                textTotAmount.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
                rlBottomAmount.setVisibility(View.VISIBLE);
            }else {
                rlBottomAmount.setVisibility(View.GONE);
            }

        }catch (Exception e){
            e.printStackTrace();
            rlBottomAmount.setVisibility(View.GONE);
        }
    }

    private void loadAnimation(ViewGroup view) {
        Context context = view.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_right_slide);
        view.setLayoutAnimation(layoutAnimationController);
    }

    void updateCartCountandTotAmount(String type, float price, int selectedCount, float selectedPrice){

        ContentValues values = new ContentValues();
        values.put("selectedQty", selectedCount);
        productTableDAO.updateRow("ProductDetails", values, "Product_Id", productId);

        ContentValues value1 = new ContentValues();
        value1.put("selectedPrice", String.valueOf(selectedPrice));
        productTableDAO.updateRow("ProductDetails", value1, "Product_Id", productId);

        float totalAmount = Float.parseFloat(textTotAmount.getText().toString());

        if(type.equalsIgnoreCase("plus")){
            totalAmount = totalAmount+price;
        }else {
            totalAmount = totalAmount-price;
        }

        if(!cartList.contains(String.valueOf(productId))){
            cartList.add(String.valueOf(productId));
        }else if(selectedCount==0){
            cartList.remove(String.valueOf(productId));
        }

        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("cart_count", json);
        editor.apply();

        editor.putFloat("total_amount", totalAmount).apply();
        editor.commit();

        setDataCartAmount();
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
       finishActivityPage();
    }

    void finishActivityPage(){
        Intent returnIntent = new Intent();
        setResult(100, returnIntent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200) {

            if (resultCode == 200) {
                setData();
                setDataCartAmount();
            }

        }
    }//onActivityResult
}
