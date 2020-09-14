package com.example.kallakurigroup.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.models.ResetPinResponse;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.Network_info;
import com.example.kallakurigroup.utils.Popup_Class;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Reset_password_Activity extends AppCompatActivity implements View.OnClickListener{

    Context context;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.header_text)
    TextView header_text;

    @BindView(R.id.butResetPass)
    Button butResetPass;

    @BindView(R.id.account_pass_forgot)
    EditText password_et;

    @BindView(R.id.account_confirm_pass_forgot)
    EditText confirmPass_et;


    String mMobileNum="";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        context = this;

        ButterKnife.bind(this);

        mMobileNum = getIntent().getStringExtra("mobile_num");

        header_text.setText(getString(R.string.resetPass));

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        butResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = password_et.getText().toString();
                String confirmPass = confirmPass_et.getText().toString();

                if (password.isEmpty()) {
                    Dialogs.show_popUp(getResources().getString(R.string.enter_password), context);
                    password_et.requestFocus();
                } else if (confirmPass.isEmpty()) {
                    Dialogs.show_popUp(getResources().getString(R.string.enter_confirm_pass), context);
                    confirmPass_et.requestFocus();
                } else if (!password.equalsIgnoreCase(confirmPass)) {
                    Dialogs.show_popUp(getResources().getString(R.string.pass_does_not_matched), context);
                    confirmPass_et.requestFocus();
                }else {
                    if (Network_info.isNetworkAvailable(context)) {

                        resetPassword(password);

                    } else {

                        Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

                    }
                }
            }
        });

    }


    @Override
    public void onBackPressed() {

        Intent i = new Intent(Reset_password_Activity.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }


    private void resetPassword(String password) {

        Dialogs.ProgressDialog(context);

        password_et.clearFocus();
        confirmPass_et.clearFocus();

        final JSONObject data  = new JSONObject();

        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = null;

        try {

            data.put("phoneNo", mMobileNum);
            data.put("psswd", password);
            //data.put("iemi", "");

            jsonObject = (JsonObject) jsonParser.parse(data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<ResetPinResponse> call = apiService.resetPass("application/json", jsonObject);

        call.enqueue(new Callback<ResetPinResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResetPinResponse> call, Response<ResetPinResponse> response) {

                Dialogs.Cancel();

                if (response.code() == 200) {

                    if(response.body().getCode() == 200){
                        ResetPinResponse responceModel = response.body();

                        if(!responceModel.getMessage().equalsIgnoreCase("success")) {
                            Dialogs.show_popUp(responceModel.getMessage(), context);
                        }else {

                            Toast.makeText(context, getResources().getString(R.string.password_changed), Toast.LENGTH_SHORT).show();

                            Intent i1 = new Intent(Reset_password_Activity.this, Login.class);
                            i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i1);
                        }
                    }else {
                        Dialogs.show_popUp(response.body().getMessage(), context);
                        new Popup_Class().sendError("forgot-password", response.body().getMessage(), 0, mMobileNum);
                    }

                } else {
                    Dialogs.show_popUp(response.message(), context);
                    new Popup_Class().sendError("forgot-password", response.message(), 0, mMobileNum);
                }
            }

            @Override
            public void onFailure(Call<ResetPinResponse> call, Throwable t) {
                Dialogs.Cancel();
                Dialogs.show_popUp(getResources().getString(R.string.error) + ": " + t.getMessage(), context);
                new Popup_Class().sendError("forgot-password", t.getMessage(), 0, mMobileNum);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.back_arrow:

                //clearPin();

                Intent i = new Intent(Reset_password_Activity.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("reset", false);
                startActivity(i);

                break;

            default:
                break;

            case R.id.left_lay:
                Intent i1 = new Intent(Reset_password_Activity.this, Login.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                i1.putExtra("reset", false);
                startActivity(i1);
                break;
        }
        try {

            TextView tv = (TextView) v.getTag();
            Button btn = (Button) v;
            tv.append(btn.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
        //Log.d("This is: " + v.getId(), "::For " + v.getTag());

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
