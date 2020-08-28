package com.example.kallakurigroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.Network_info;

import butterknife.BindView;
import butterknife.ButterKnife;


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

        mMobileNum = getIntent().getStringExtra("mobileNum");

        header_text.setText(getString(R.string.register));

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
        i.putExtra("reset", false);
        startActivity(i);

    }



    public  void resetPassword(String password){

     /*   try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Log.e("OTP=",OTP);

            Call<JsonElement> call = apiService.resetConfirmPin(PropertiesFile.BASEURL, "newPIN", mMobileNumber, mPin, OTP);

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                    Log.e("xbj j", response.toString());

                    if (response.code() != 200) {

                        Dialogs.show_popUp(getResources().getString(R.string.networkalert) + "-" + response.code(), context);


                    } else {

                        if (response.body() != null && !response.body().equals("")) {


                            try {
                                //    System.out.println("RESPONSE in POST execute:\n" + result);
                                JSONObject jsonObject = new JSONObject(response.body().toString());

                                String status_message = jsonObject.getString("status");
                                String message = jsonObject.getString("msg");
                                //     Log.e("Status message", status_message);

                                if (status_message.equals("1"))
                                {
                                    clearPin();



                                    int[] location = new int[2];
                                    mEditPin14.getLocationOnScreen(location);


                                    coins_popup(location[0],location[1]);
                                   // alertPopup(ll_edit);

                                    //delay in ms
                                    int DELAY = 2000;

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            Intent i = new Intent(Reset_pin_Activity.this, LogInActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            i.putExtra("reset", true);
                                            startActivity(i);
                                        }
                                    }, DELAY);


                                }
                                else
                                {
                                    Dialogs.show_popUp(message, context);

                                }

                            }catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {

                            Dialogs.show_popUp(getResources().getString(R.string.no_data_found), context);
git config --global user.name "Haritha"

                        }


                    }

                    Dialogs.Cancel();
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("xbj j", t.toString());

                    Dialogs.Cancel();
                }
            });
        }catch (Exception e){
            Dialogs.Cancel();
            Dialogs.show_popUp(getResources().getString(R.string.PleaseTryAgain), context);
            e.printStackTrace();
        }*/
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
