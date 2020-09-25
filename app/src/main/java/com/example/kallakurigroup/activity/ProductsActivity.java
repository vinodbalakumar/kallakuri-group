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
import com.example.kallakurigroup.listeners.ProductItemListener;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements ProductItemListener {

    private List<ProductDetails> productsList = new ArrayList<>();

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

    @BindView(R.id.rl_bottom_amount)
    RelativeLayout rl_bottom_amount;

    @BindView(R.id.imageRightArrow)
    ImageView imageRightArrow;

    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;

    List<String> cartProductIds = new ArrayList<>();

    ProductTableDAO productTableDAO;

    Context context;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";

    ProductsAdapter productsAdapter;

    List<String> cartList = new ArrayList<>();

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
               // if(textCartCount.getText().toString()!=null && !textCartCount.getText().toString().equalsIgnoreCase("0")) {
                    startActivityForResult(new Intent(ProductsActivity.this, CartActivity.class).putExtra("from","prodAct"), 100);
               // }
            }
        });

        textCartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(textCartCount.getText().toString()!=null && !textCartCount.getText().toString().equalsIgnoreCase("0")) {
                    startActivityForResult(new Intent(ProductsActivity.this, CartActivity.class).putExtra("from","prodAct"), 100);
               // }
            }
        });

        rl_bottom_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ProductsActivity.this, CartActivity.class).putExtra("from","prodAct"), 100);
            }
        });

        imageRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ProductsActivity.this, CartActivity.class).putExtra("from","prodAct"), 100);
            }
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

        float totalAmount = 0;
        if(sharedpreferences.contains("total_amount") && sharedpreferences.getFloat("total_amount", 0)!=0){
            totalAmount = sharedpreferences.getFloat("total_amount", 0);
        }

        if(type.equalsIgnoreCase("plus")){
            totalAmount = totalAmount+prodPrice;
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

        try{

        if(sharedpreferences.contains("total_amount") && sharedpreferences.getFloat("total_amount", 0)!=0){
            textTotAmount.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
            rl_bottom_amount.setVisibility(View.VISIBLE);
        }else {
            textTotAmount.setText("0");
            rl_bottom_amount.setVisibility(View.GONE);
        }

        Gson gson = new Gson();
        String json = sharedpreferences.getString("cart_count", null);

            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            cartList = gson.fromJson(json, type);

            if(cartList!=null && cartList.size()>0){
                textCartCount.setText(String.valueOf(cartList.size()));
                textCartCount.setVisibility(View.VISIBLE);
            }else {
                cartList = new ArrayList<>();
                textCartCount.setVisibility(View.GONE);
            }

        }catch (Exception e){
            e.printStackTrace();
            rl_bottom_amount.setVisibility(View.GONE);
            textTotAmount.setText("0");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {

            if (resultCode == 100) {
                setDataCartAmount();
                if(productsList!=null && productsList.size()>0){
                    productsList.clear();
                    productsList = productTableDAO.getProductByBrandId(getIntent().getStringExtra("brand_id"));
                    productsAdapter = new ProductsAdapter(productsList, ProductsActivity.this, context);
                    productRecyclerView.setAdapter(productsAdapter);
                }else {
                    productRecyclerView.setVisibility(View.GONE);
                    rlNoDataFound.setVisibility(View.VISIBLE);
                }
            }
        }
    }//onActivityResult
}
