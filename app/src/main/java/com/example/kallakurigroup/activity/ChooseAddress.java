package com.example.kallakurigroup.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class ChooseAddress extends AppCompatActivity {

    JSONArray dates = new JSONArray();
    JSONArray times = new JSONArray();

    LinearLayout llDelAddress;

    RelativeLayout header_top, left_lay;
    LinearLayout ll_add_new;
    TextView header_text;
    Context context;
    ImageView back_icon;
    RelativeLayout ll_proceedtoPay;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String PREFERENCE = "KALLAKURI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        context = this;

        sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        header_top = (RelativeLayout) findViewById(R.id.header_top);
        left_lay = (RelativeLayout) header_top.findViewById(R.id.left_lay);
        back_icon = (ImageView) findViewById(R.id.back_arrow);

        header_text = (TextView) header_top.findViewById(R.id.header_text);
        header_text.setText(getResources().getString(R.string.address));
        ll_add_new = (LinearLayout) findViewById(R.id.add_new);

        ll_proceedtoPay = (RelativeLayout) findViewById(R.id.ll_proceedtoPay);

        llDelAddress = (LinearLayout) findViewById(R.id.llDelAddress);


        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(ChooseAddress.this, AddAddress.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

        ll_proceedtoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent intent = new Intent(ChooseAddress.this, OrderPaymentsActivityNew.class);
                intent.putExtra("billingAddresId", billingAddresId + "");
                intent.putExtra("delAddressId", delAddressId + "");
                startActivity(intent);*/

            }
        });

    }

}