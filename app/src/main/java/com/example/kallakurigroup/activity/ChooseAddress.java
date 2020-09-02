package com.example.kallakurigroup.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.models.userModels.UserTableModel;

import org.json.JSONArray;


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

    UserTableDAO userTableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        context = this;

        userTableDAO = new UserTableDAO(this);

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

                Intent intent = new Intent(ChooseAddress.this, OrderPaymentsActivity.class);
                /*intent.putExtra("billingAddresId", billingAddresId + "");
                intent.putExtra("delAddressId", delAddressId + "");*/
                startActivity(intent);

            }
        });

        setaddress();
    }

    void setaddress(){
        LinearLayout ll = (LinearLayout) getLayoutInflater().inflate(R.layout.item_address, null);
        TextView name = (TextView) ll.findViewById(R.id.name);
        TextView phonenum = (TextView) ll.findViewById(R.id.phone_no);
        TextView address = (TextView) ll.findViewById(R.id.address);
       /* TextView village = (TextView) ll.findViewById(R.id.village);
        TextView city = (TextView) ll.findViewById(R.id.city);
        TextView district = (TextView) ll.findViewById(R.id.district);
        TextView state = (TextView) ll.findViewById(R.id.state);
        TextView pincode = (TextView) ll.findViewById(R.id.pincode);*/

        UserTableModel model = userTableDAO.getData().get(0);

        name.setText(""+ model.getName());

        phonenum.setText("+91 " + model.getPhoneNo());

        address.setText(""+ model.getDeliveryAddress());

       /* village.setText(""+ model.ger());

        city.setText(""+ model.getName());

        state.setText(""+ model.getName());

        district.setText(""+ model.getName());

        pincode.setText(""+ model.getName());*/

        View v = new View(this);
        v.setBackgroundResource(R.color.viewline);
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        llDelAddress.addView(ll);
    }

}