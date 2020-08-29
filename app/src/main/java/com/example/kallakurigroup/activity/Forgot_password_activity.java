package com.example.kallakurigroup.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.kallakurigroup.utils.Validations;
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

public class Forgot_password_activity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.buttonReset)
    Button mButtonReset;

    @BindView(R.id.back_arrow)
    ImageView mImageBack;

    @BindView(R.id.header_text)
    TextView mTextTitle;

    @BindView(R.id.editMobilenUmber)
    EditText mEditMobileNUmber;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    public static String mMobilenUmber;

    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        try {
            context = Forgot_password_activity.this;

            ButterKnife.bind(this);

            mButtonReset.setOnClickListener(this);
            mImageBack.setOnClickListener(this);

            mTextTitle.setText(getResources().getString(R.string.forgot_pin));

            left_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.buttonReset:
                    validations();
                    break;
                case R.id.back_arrow:
                    finish();
                    break;

                default:
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void validations(){

        mMobilenUmber = mEditMobileNUmber.getText().toString();

        if (mMobilenUmber.isEmpty()) {

            Dialogs.show_popUp(getResources().getString(R.string.enter_mobile_number), context);

        }else if (!Network_info.valid(mMobilenUmber)) {

            Dialogs.show_popUp(getResources().getString(R.string.enter_valid_ten_digit_mobile_number), context);

        }else {

            if (Network_info.isNetworkAvailable(context)) {

                sendOTP(mMobilenUmber);

            } else {

                Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

            }

        }
    }


    private void sendOTP(String mobileNumber) {

        Dialogs.ProgressDialog(context);

        mEditMobileNUmber.clearFocus();

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
            data.put("reqType", "forgot-password");

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

                        Intent intent = new Intent(Forgot_password_activity.this, Verify_otp_Activity.class);
                        intent.putExtra("mobile_num", mobileNumber);
                        intent.putExtra("otp", otpCode);
                        intent.putExtra("from", "forgotpass");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
    }
}