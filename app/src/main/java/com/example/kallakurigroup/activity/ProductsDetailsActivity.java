package com.example.kallakurigroup.activity;

import android.content.Context;
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
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsDetailsActivity extends AppCompatActivity {

    private List<ProductDetails> productsList;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

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

    ProductTableDAO productTableDAO;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products_details);

        context = this;

        ButterKnife.bind(this);

        productTableDAO = new ProductTableDAO(this);

        header_text.setText("");

        productsList = productTableDAO.getProductbyId(getIntent().getExtras().getInt("product_id"));

        //loadAnimation(llBrands);
        loadAnimation(llMain);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                if(selectedCount<10) {
                    quantity_count.setText(String.valueOf(selectedCount));
                    updateCartCountandTotAmount("plus", selectedPrice);
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedCount = Integer.parseInt(quantity_count.getText().toString());
                float prodPrice = Float.parseFloat(ProductOfferPrice.getText().toString());
                float selectedPrice = prodPrice*selectedCount;
                if(selectedCount<10) {
                    selectedCount = selectedCount + 1;
                    quantity_count.setText(String.valueOf(selectedCount));
                    updateCartCountandTotAmount("plus", selectedPrice);
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedCount = Integer.parseInt(quantity_count.getText().toString());
                float prodPrice = Float.parseFloat(ProductOfferPrice.getText().toString());
                float selectedPrice = prodPrice*selectedCount;
                if(selectedCount>1) {
                    selectedCount = selectedCount - 1;
                    quantity_count.setText(String.valueOf(selectedCount));
                }else if(selectedCount == 1){
                    inner_lt.setVisibility(View.GONE);
                    quantity_count.setVisibility(View.VISIBLE);
                    selectedCount = 0;
                    quantity_count.setText(String.valueOf(selectedCount));
                    updateCartCountandTotAmount("minus", selectedPrice);
                }
            }
        });

        setData();

    }

   void setData(){

        ProductDetails prodDetails = productsList.get(0);
        Glide.with(prodImage).load(prodDetails.getProductImage())/*.apply(RequestOptions.circleCropTransform())*/.into(prodImage);

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

    private void loadAnimation(ViewGroup view) {
        Context context = view.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_right_slide);
        view.setLayoutAnimation(layoutAnimationController);
    }

    void updateCartCountandTotAmount(String type, float price){

        float totalAmount = Float.parseFloat(textTotAmount.getText().toString());

        int totCartCount = Integer.parseInt(textCartCount.getText().toString());

        if(type.equalsIgnoreCase("plus")){
            totalAmount = totalAmount+price;
            totCartCount = totCartCount+1;
        }else {
            totalAmount = totalAmount-price;
            totCartCount = totCartCount-1;
        }

        textCartCount.setText(String.valueOf(totCartCount));
        textTotAmount.setText(String.valueOf(totalAmount));

    }

}
