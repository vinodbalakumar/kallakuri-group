package com.example.kallakurigroup.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kallakurigroup.activity.Homepage;
import com.example.kallakurigroup.R;
import com.example.kallakurigroup.models.localdbmodels.UserTableModel;
import com.example.kallakurigroup.models.loginmodel.LoginProfileModel;
import com.example.kallakurigroup.models.loginmodel.LoginResponceModel;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android on 9/3/17.
 */

public class LoginCommon {

    Context context;

    String mPhoneNumber, mPass;

    ReadInfoFromPhone mReadInfo;

    JSONObject obj = null;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";

    public LoginCommon(Context context, String mPhoneNumber, String mPass) {

        try {
            this.context = context;

            this.mPhoneNumber = mPhoneNumber;
            this.mPass = mPass;

            sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();

            mReadInfo = new ReadInfoFromPhone(context);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doLogin() {

        try {

            Dialogs.ProgressDialog(context);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            //String DeviceId = mReadInfo.telephonyManager.getDeviceId();

            String DeviceId = "";

            JsonObject dataCall = createJsonObject(DeviceId);

            //JsonObject

            Call<LoginResponceModel> call = apiService.loginUser("application/json", dataCall);

            if (ClassicSingleton.enable_logs)
            Log.e("Login_request",call.request().toString());

            call.enqueue(new Callback<LoginResponceModel>() {
                @Override
                public void onResponse(Call<LoginResponceModel> call, Response<LoginResponceModel> response) {

                    Dialogs.Cancel();

                    if (ClassicSingleton.enable_logs)
                    Log.e("Login_Response", response.toString());

                    if (response.code() != 200) {

                        Dialogs.show_popUp(context.getResources().getString(R.string.networkalert) + "-" + response.code(), context);

                    } else {

                        LoginResponceModel loginProfileModel = response.body();

                        LoginProfileModel model = loginProfileModel.getData().getLoginProfileModel();

                        if (model==null || !model.getPassword().equals(mPass)) {
                            Dialogs.show_popUp(context.getResources().getString(R.string.invalidlogin) , context);
                        }else {
                            localStorage(model);

                            context.startActivity(new Intent(context, Homepage.class));

                        }

                    }

                }

                @Override
                public void onFailure(Call<LoginResponceModel> call, Throwable t){
                    Dialogs.Cancel();
                    Dialogs.show_popUp(context.getResources().getString(R.string.unabletologin) + " " + t.getMessage(), context);

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Dialogs.Cancel();
            Dialogs.show_popUp(context.getResources().getString(R.string.please_check_internet)+" "+e.toString(), context);
        }

    }

    private JsonObject createJsonObject(String deviceId) {
        JsonObject jsonObject = new JsonObject();
        JSONObject mainObject = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject signIn = new JSONObject();
        JSONArray userProfilesArray = new JSONArray();
        JSONArray ustrd= new JSONArray();
        JSONObject emptyObject = new JSONObject();
        JSONObject xcptInf =  new JSONObject();
        JSONObject error = new JSONObject();

        try {

            signIn.put("imei", deviceId);
            signIn.put("password", mPass);
            signIn.put("uname", mPhoneNumber);

            data.put("reqType", "sign-in");
            data.put("roles", emptyObject);
            data.put("signIn", signIn);
            data.put("userProfile", JSONObject.NULL);
            data.put("userProfiles", userProfilesArray);

            ustrd.put(emptyObject);

            xcptInf.put("ustrd", ustrd);

            error.put("xcptInf", xcptInf);

            mainObject.put("data", data);
            mainObject.put("error", error);
            mainObject.put("header", emptyObject);

            JsonParser jsonParser = new JsonParser();
            jsonObject = (JsonObject) jsonParser.parse(mainObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
      return jsonObject;
    }

    private void localStorage(LoginProfileModel model) {

        Storage storage = new Storage(context);
        SQLiteDatabase database = storage.getWritableDatabase();

        UserTableModel model1 = new UserTableModel();
        model1.setName(model.getName());
        model1.setPhoneNo(model.getPhoneNo());
        model1.setEmail(model.getEmail());
        model1.setPassword(model.getPassword());
        model1.setVillage(model.getVillage());
        model1.setTown(model.getTown());
        model1.setDistrict(model.getDistrict());
        model1.setState(model.getState());
        model1.setPincode(model.getPincode());
        model1.setRoleNumber(model.getRole());

        if (storage.getUserDetails().getPhoneNo() == null) {
            storage.insertUserDetails(model1);
        } else {
            storage.updateUserDetails(model1);
        }
        database.close();


    }

}