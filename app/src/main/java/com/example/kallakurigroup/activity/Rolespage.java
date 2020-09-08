package com.example.kallakurigroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.models.rolesmodels.RolesResponceModel;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.Network_info;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Rolespage extends AppCompatActivity{

    RadioGroup radioGroup;
    private ArrayList<Integer> roleValues;
    private ArrayList<String> roleNames;
    TextView textHeader;
    RelativeLayout left_lay;
    Button btnSubmit;
    Context context;

    String mobileNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rolespage);

        context = Rolespage.this;

        mobileNum = getIntent().getStringExtra("mobile_num");

        radioGroup = (RadioGroup) findViewById(R.id.rolesRadiGroup);

        textHeader = (TextView) findViewById(R.id.header_text);

        textHeader.setText(R.string.chooseRole);

        btnSubmit = (Button) findViewById(R.id.submit_btn_role);

        left_lay = (RelativeLayout) findViewById(R.id.left_lay);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRole();
            }
        });

        roleValues = new ArrayList<>();

        if (Network_info.isNetworkAvailable(context)) {

            getRoles();

        } else {

            Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

        }

    }


    private void getRoles(){

        Dialogs.ProgressDialog(context);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<RolesResponceModel> call = apiService.getRoles();

        call.enqueue(new Callback<RolesResponceModel>() {
            @Override
            public void onResponse(Call<RolesResponceModel> call, Response<RolesResponceModel> response) {

                Dialogs.Cancel();

                if (!response.isSuccessful()) {
                    Dialogs.show_popUp(getResources().getString(R.string.try_again), context);
                    return;
                }

                if (response.code() == 200) {

                    RolesResponceModel model = response.body();

                    roleValues = new ArrayList<>();
                    roleNames = new ArrayList<>();

                    for(Map.Entry<String, Integer> rolesMap : model.getRolesData().getRoles().entrySet()) {

                        roleValues.add(rolesMap.getValue());

                        roleNames.add(rolesMap.getKey());

                        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(50,10,50,10);
                        RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.template_radio_button, null);
                        radioButton.setId(RadioButton.generateViewId());
                        radioButton.setText(rolesMap.getKey());
                        radioButton.setLayoutParams(layoutParams);
                        radioGroup.addView(radioButton);
                    }

                } else {
                    RolesResponceModel model = response.body();
                    Dialogs.show_popUp(model.getHeader().getSuccess(), context);
                }
            }

            @Override
            public void onFailure(Call<RolesResponceModel> call, Throwable t) {
                Dialogs.Cancel();
                Dialogs.show_popUp(getResources().getString(R.string.error) + ": " + t.getMessage(), context);
            }
        });
    }


    void submitRole() {
        int selectedRadioId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioId);
        if (selectedRadioButton != null) {
            String roleName = selectedRadioButton.getText().toString().trim();
            int index = radioGroup.indexOfChild(selectedRadioButton);
            startActivity(new Intent(Rolespage.this, Account_setup.class).putExtra("role_name", roleName).putExtra("role_num", String.valueOf(roleValues.get(index))).putExtra("mobile_num", mobileNum));
        } else {
            Dialogs.show_popUp(getResources().getString(R.string.Please_select_your_role),context);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(Rolespage.this, Login.class);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i1);
    }
}
