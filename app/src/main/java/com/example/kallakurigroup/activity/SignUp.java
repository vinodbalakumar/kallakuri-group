package com.example.kallakurigroup.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.models.localdbmodels.UserTableModel;
import com.example.kallakurigroup.models.otpmodels.OTPResponceModel;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.Network_info;
import com.example.kallakurigroup.utils.Storage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    @BindView(R.id.edit_username)
    EditText edit_username;

    @BindView(R.id.butRegister)
    Button butRegister;

    @BindView(R.id.llTerms)
    LinearLayout llTerms;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.header_text)
    TextView header_text;

    @BindView(R.id.register_partclick)
    TextView register_partclick;

    Spannable mySpannable;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = this;

        ButterKnife.bind(this);
        header_text.setText(getString(R.string.register));

        butRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile_no = edit_username.getText().toString();

                if (mobile_no.isEmpty()) {

                    Dialogs.show_popUp(getResources().getString(R.string.enter_mobile_number), context);

                }else if (!Network_info.valid(mobile_no)) {

                    Dialogs.show_popUp(getResources().getString(R.string.enter_valid_ten_digit_mobile_number), context);

                }else {

                    if (Network_info.isNetworkAvailable(context)) {

                        sendOTP(mobile_no);

                    } else {

                        Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

                    }

                }

            }
        });


        String myString = (String) register_partclick.getText();
        int i1 = myString.indexOf("Terms and");
        int i2 = myString.indexOf("Conditions")+9;
        //System.out.println(" i1   " +i1+"i2 value"+i2);//41 and 62

        String myString1 = (String) register_partclick.getText();
        int l1 = myString.indexOf("Privacy");
        int l2 = myString.indexOf(".");
       // System.out.println(" l1   " +l1+"l2 value"+l2);//67 and 81
        register_partclick.setMovementMethod(LinkMovementMethod.getInstance());
        register_partclick.setText(myString, TextView.BufferType.SPANNABLE);
        register_partclick.setText(myString1, TextView.BufferType.SPANNABLE);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mySpannable = (Spannable) register_partclick.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View v) {

            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
                ds.setUnderlineText(false);
            }
        };
        try {
            mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //41,62  i1,i2
            mySpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#0099ff")),
                    i1, i2 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            register_partclick.setText(mySpannable);
            register_partclick.setMovementMethod(LinkMovementMethod.getInstance());
            //myTextView.setHighlightColor(Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Spannable mySpannable1 = (Spannable) register_partclick.getText();
        ClickableSpan myClickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                //ds.linkColor = selected? fg : 0xffeeeeee;
            }
        };

        try {
            mySpannable1.setSpan(myClickableSpan1, l1, l2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mySpannable1.setSpan(new ForegroundColorSpan(Color.parseColor("#0099ff")),
                    l1, l2 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            register_partclick.setText(mySpannable1);
            register_partclick.setMovementMethod(LinkMovementMethod.getInstance());

            //myTextView.setHighlightColor(Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendOTP(String mobileNumber) {

        Dialogs.ProgressDialog(context);

        edit_username.clearFocus();

        JSONObject mainObject = new JSONObject();

        final JSONObject data  = new JSONObject();

        JSONObject roles  = new JSONObject();

        JSONObject error = new JSONObject();

        JSONObject ustrd = new JSONObject();

        JSONArray ustrdArray = new JSONArray();

        JSONObject emptyUstrd = new JSONObject();

        JSONObject jsonPhoneNo = new JSONObject();

        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = null;

        try {

            data.put("reqType", "String");
            data.put("roles", roles);
            data.put("signIn", JSONObject.NULL);
            data.put("userProfile",JSONObject.NULL);
            data.put("userProfiles",JSONObject.NULL);

            ustrdArray.put(emptyUstrd);
            ustrd.put("ustrd", ustrdArray);
            error.put("xcptInf", ustrd);

            jsonPhoneNo.put("phoneNo", mobileNumber);

            mainObject.put("data", data);
            mainObject.put("error", error);
            mainObject.put("header", jsonPhoneNo);

            jsonObject = (JsonObject) jsonParser.parse(mainObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<OTPResponceModel> call = apiService.getOTP("application/json", jsonObject);

        call.enqueue(new Callback<OTPResponceModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OTPResponceModel> call, Response<OTPResponceModel> response) {

                Dialogs.Cancel();

                if (!response.isSuccessful()) {

                    Dialogs.show_popUp(getResources().getString(R.string.try_again), context);

                    return;
                }

                if (response.code() == 200) {

                    OTPResponceModel responceModel = response.body();

                    String responePhoneNo = responceModel.getHeader().getPhoneNo();
                    String otpCode = responceModel.getHeader().getOtpCode();

                    String phoneNo = responceModel.getHeader().getPhoneNo();
                    if(phoneNo == null) {
                        Dialogs.show_popUp(responceModel.getHeader().getMessage(), context);
                    }else {

                        Storage storage = new Storage(context);
                        SQLiteDatabase database = storage.getWritableDatabase();

                        if (storage.getUserDetails().getPhoneNo() == null) {
                            UserTableModel model = new UserTableModel(1, responePhoneNo, "", "", "", "", "", "", "", "", "", "");
                            storage.insertUserDetails(model);
                        } else {
                            ContentValues values = new ContentValues();
                            values.put(Storage.USER_PHONE_NO, responePhoneNo);
                            database.update(Storage.USER_TABLE, values, "uno=1", null);
                        }
                        database.close();

                        Toast.makeText(context, getResources().getString(R.string.otp_request_Sent), Toast.LENGTH_SHORT).hashCode();

                        Intent intent = new Intent(SignUp.this, Verify_otp_Activity.class);
                        intent.putExtra("mobile_num", mobileNumber);
                        intent.putExtra("otp", otpCode);
                        intent.putExtra("from", "signup");
                        startActivity(intent);
                    }

                } else {
                    OTPResponceModel responceModel = response.body();
                    Dialogs.show_popUp(responceModel.getHeader().getMessage(), context);
                }
            }

            @Override
            public void onFailure(Call<OTPResponceModel> call, Throwable t) {
                Dialogs.Cancel();
                Dialogs.show_popUp(getResources().getString(R.string.error) + ": " + t.getMessage(), context);

            }
        });
    }

}
