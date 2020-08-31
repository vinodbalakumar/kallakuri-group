package com.example.kallakurigroup.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.models.otpmodels.OTPResponceModel;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.ClassicSingleton;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.Network_info;
import com.example.kallakurigroup.utils.Popup_Class;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Verify_otp_Activity extends AppCompatActivity {

    InputMethodManager inputMethodManager;

    @BindView(R.id.buttonVerify)
    Button mButtonVerify;

    @BindView(R.id.textResendOtp)
    TextView mResendOtp;

    @BindView(R.id.back_arrow)
    ImageView mImageBack;

    @BindView(R.id.header_text)
    TextView mTextTitle;

    @BindView(R.id.editOtp)
    EditText mEditOtp;

    @BindView(R.id.imageVeroifyCall)
    ImageView mImageVerifyCall;

    @BindView(R.id.number_button)
    ImageView number_button;

    @BindView(R.id.to_number)
    TextView to_number;

    @BindView(R.id.from_number)
    TextView from_number;

    @BindView(R.id.show_misscall_ll)
    LinearLayout show_misscall_ll;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.textNote)
    TextView textNote;

    String mMobileNumber, mOTPCode, mFrom;
    public static String mOTP;

    Context context;

    private SmsVerifyCatcher smsVerifyCatcher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verifyotp);

        try {

            context = this;

            ButterKnife.bind(this);

            if (getIntent().getExtras() != null) {
                mMobileNumber = getIntent().getStringExtra("mobile_num");
                mOTPCode = getIntent().getStringExtra("otp");
                mFrom = getIntent().getStringExtra("from");

                textNote.setText(mMobileNumber+": Your One Time Password is KK - "+mOTPCode+" for registering an new Account with KK-GROUPS");
            }

            try {
                if (ClassicSingleton.getInstance().missedcall_number.startsWith("+91")) {
                    to_number.setText(ClassicSingleton.getInstance().missedcall_number);
                } else {
                    to_number.setText("+91 " +ClassicSingleton.getInstance().missedcall_number);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            from_number.setText("+91 "+mMobileNumber);

            mButtonVerify.setText(getString(R.string.verify_otp));

            mTextTitle.setText(getResources().getString(R.string.otp));


            left_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            if(ClassicSingleton.getInstance().MissedCallFeature.equalsIgnoreCase("1")) {
                show_misscall_ll.setVisibility(View.VISIBLE);
            }else {
                show_misscall_ll.setVisibility(View.GONE);
            }


        }catch (Exception e){
            e.printStackTrace();
        }


        //init SmsVerifyCatcher
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                mEditOtp.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });

        smsVerifyCatcher.setPhoneNumberFilter("MD-NUMMAL");

        mButtonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               validations();

            }
        });

        number_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("onclicked", "onthatview");
                    final Dialog lt = new Popup_Class().showDialog_dual_action_yes(context, getResources().getString(R.string.call_are_you_sure), getString(R.string.verifyCall));
                    lt.setCancelable(false);
                    TextView t_yes = (TextView) lt.findViewById(R.id.p_yes);
                    TextView t_no = (TextView) lt.findViewById(R.id.p_no);

                    mButtonVerify.setText(getString(R.string.verify));

                    t_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String mobile_number = to_number.getText().toString();
                                mobile_number = mobile_number.replace("+91", "");
                                call(mobile_number);

                                mEditOtp.setText("");
                                lt.dismiss();
                            } catch (Exception e) {
                                Log.e("exception_hierarchy1", e.getStackTrace() + "");
                                e.printStackTrace();
                            }
                        }
                    });
                    t_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mButtonVerify.setText(getString(R.string.verify_otp));

                            lt.dismiss();
                        }
                    });

                    lt.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                     mButtonVerify.setText(getString(R.string.verify_otp));
                     mEditOtp.setText("");

                    if (Network_info.isNetworkAvailable(context)) {

                        try {
                             inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        resendOtp(mMobileNumber);

                    } else {

                        Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * Parse verification code
     *
     * @param message sms message
     * @return only four numbers from massage string
     */
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            smsVerifyCatcher.onStart();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            smsVerifyCatcher.onStop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    void call(String number) {
        Log.e("in","call,"+number);

        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e("no","permission");
                return;
            }
            startActivity(callIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void validations(){

        try {
            if(mButtonVerify.getText().toString().equalsIgnoreCase(getString(R.string.verify)))
            {
                if (Network_info.isNetworkAvailable(context)) {
                    Dialogs.ProgressDialog(context);
                    verifycall();
                }else {

                    Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

                }
            }
            else
            {
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mOTP = mEditOtp.getText().toString();

                verifyOtp();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void verifyOtp() {

        if(mOTPCode.equals(mOTP)){
            if(mFrom.equalsIgnoreCase("signup")) {
                startActivity(new Intent(Verify_otp_Activity.this, Rolespage.class).putExtra("mobile_num", mMobileNumber));
            }else {
                startActivity(new Intent(Verify_otp_Activity.this, Reset_password_Activity.class).putExtra("mobile_num", mMobileNumber));
            }
        } else {
            Toast.makeText(Verify_otp_Activity.this, getResources().getString(R.string.otp_not_match), Toast.LENGTH_SHORT).show();
        }
    }

    public void verifycall() {
        /*try {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            // Log.e("idtwofactorauth","method=getMissedCallStatus&mobilenumber="+mMobileNumber+"&otp=XXXXXX&Idtwofactorauthentication="+ClassicSingleton.getInstance().Idtwofactorauth+"&logintype=4");
            Call<JsonElement> call = apiService.Verifycall(PropertiesFile.baseUrl, "getMissedCallStatus", mMobileNumber, "XXXXXX", ClassicSingleton.getInstance().Idtwofactorauth, "4");

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                    Log.e("xbj j", response.toString());

                    if (response.code() != 200) {

                        Dialogs.show_popUp(getResources().getString(R.string.networkalert), context);

                    } else {

                        if (response.body() != null && !response.body().equals("")) {

                            try {
                                //System.out.println("RESPONSE in POST execute:\n" + result);
                                JSONObject jsonObject = new JSONObject(response.body().toString());

                                String message = jsonObject.getString("msg");
                                String status_message = jsonObject.getString("status");

                                if (status_message.equals("1")) {

                                    Intent intent = new Intent(context, Reset_pin_Activity.class);
                                    intent.putExtra("mobile_no", mMobileNumber);
                                    intent.putExtra("OTP","XXXXXX");
                                    startActivity(intent);


                                } else if (status_message.equals("0")) {

                                    Dialogs.show_popUp(""+message, context);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                        } else {
                            Dialogs.show_popUp(getResources().getString(R.string.no_data_found), context);
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

    private void resendOtp(String mobileNumber) {

        Dialogs.ProgressDialog(context);

        Dialogs.show_popUp(getResources().getString(R.string.otp_request_Sent), context);

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

                        mOTPCode = otpCode;
                        textNote.setText(mMobileNumber+": Your One Time Password is KK - "+mOTPCode+" for registering an new Account with KK-GROUPS");

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        inputMethodManager = null;
    }
}