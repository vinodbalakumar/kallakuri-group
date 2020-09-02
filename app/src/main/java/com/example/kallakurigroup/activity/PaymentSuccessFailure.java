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


public class PaymentSuccessFailure extends AppCompatActivity {

    String PREFERENCE = "KALLAKURI";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;

    @BindView(R.id.status_image)
    ImageView statusImage;

    @BindView(R.id.status_text)
    TextView statusText;

    @BindView(R.id.go_home)
    TextView goHome;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success_failure);

        context = this;

        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        statusText.setText(getResources().getString(R.string.orderPlaced));
        mainLayout.setBackgroundColor(Color.GREEN);
        statusImage.setBackground(getResources().getDrawable(R.drawable.tick_white));

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

}
