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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.kallakurigroup.R;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.models.productsmodels.OrderDetails;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderTrackingActivity extends AppCompatActivity {

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.header_text)
    TextView header_text;

    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.textDeliverTo)
    TextView textDeliverTo;

    @BindView(R.id.prodImage)
    ImageView prodImage;

    @BindView(R.id.prodName)
    TextView prodName;

    @BindView(R.id.productQty)
    TextView productQty;

    @BindView(R.id.productType)
    TextView productType;

    @BindView(R.id.ProductOfferPrice)
    TextView ProductOfferPrice;

    @BindView(R.id.ProductOrderedQty)
    TextView ProductOrderedQty;

    @BindView(R.id.ProductOrderedDate)
    TextView ProductOrderedDate;

    @BindView(R.id.productDelStatus)
    TextView productDelStatus;

    @BindView(R.id.orderId)
    TextView orderId;

    @BindView(R.id.check1)
    CheckBox check1;

    Context context;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";

    OrderDetails orderDetails;

    UserTableDAO userTableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_tracking);

        context = this;

        ButterKnife.bind(this);

        userTableDAO = new UserTableDAO(context);

        sharedpreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        header_text.setText("Order Tracking");

        textDeliverTo.setText(userTableDAO.getData().get(0).getDeliveryAddress());

        orderDetails = this.getIntent().getExtras().getParcelable("orderDetails");

        //loadAnimation(llBrands);
        loadAnimation(llMain);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        setData();
    }

   void setData(){

       Glide.with(prodImage).load(orderDetails.getImage())/*.apply(RequestOptions.circleCropTransform())*/.into(prodImage);

       prodName.setText(orderDetails.getName());

       productQty.setText(orderDetails.getQuantity());

       productType.setText(orderDetails.getType());

       ProductOfferPrice.setText(orderDetails.getFinalPrice());

       ProductOrderedQty.setText(orderDetails.getOrderedQunatity());

       ProductOrderedDate.setText(orderDetails.getOrderedDateTime());

       productDelStatus.setText(orderDetails.getDeliveryStatus());

       orderId.setText("Order Id: "+orderDetails.getOrderCode());

       check1.setText("Ordered on "+orderDetails.getOrderedDateTime());
    }

    private void loadAnimation(ViewGroup view) {
        Context context = view.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_right_slide);
        view.setLayoutAnimation(layoutAnimationController);
    }

}
