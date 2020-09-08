package com.example.kallakurigroup.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;


public class PaymentSuccessFailure extends AppCompatActivity {

    String PREFERENCE = "KALLAKURI";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @BindView(R.id.status_text)
    TextView statusText;

    @BindView(R.id.orderId)
    TextView orderId;

    @BindView(R.id.go_home)
    TextView goHome;

    @BindView(R.id.failed)
    ImageView failed;

    @BindView(R.id.success)
    GifImageView success;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success_failure);

        context = this;

        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        statusText.setText(getIntent().getStringExtra("message"));
        orderId.setText(getIntent().getStringExtra("orderId"));

        if(getIntent().getExtras().getInt("status") != 200){
            failed.setVisibility(View.VISIBLE);
        }

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentSuccessFailure.this, Homepage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
