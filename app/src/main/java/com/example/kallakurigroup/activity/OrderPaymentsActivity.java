package com.example.kallakurigroup.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.models.loginmodel.LoginResponceModel;
import com.example.kallakurigroup.models.otpmodels.OTPResponceModel;
import com.example.kallakurigroup.models.productsmodels.OrderDetails;
import com.example.kallakurigroup.models.productsmodels.PlaceOrderDetails;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.Network_info;
import com.example.kallakurigroup.utils.Popup_Class;
import com.example.kallakurigroup.utils.PropertiesFile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;


public class OrderPaymentsActivity extends AppCompatActivity /*implements PaymentRelatedDetailsListener */{

    Context context;

    UserTableDAO userTableDAO;
    ProductTableDAO productTableDAO;

    @BindView(R.id.textDeliverTo)
    TextView textDeliverTo;

    @BindView(R.id.header_text)
    TextView name_h;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.subTotalAmount)
    TextView subTotalAmount;

    @BindView(R.id.gst_charges_amount)
    TextView gstChargesAmount;

    @BindView(R.id.delCharges)
    TextView delCharges;

    @BindView(R.id.Total_amount_value)
    TextView totalAmount;

    @BindView(R.id.netPayableAmount)
    TextView netPayableAmount;

    @BindView(R.id.textCodAmount)
    TextView textCodAmount;

    @BindView(R.id.rl_proceed)
    RelativeLayout rl_proceed;

    @BindView(R.id.ll_verify_otp)
    LinearLayout ll_verify_otp;

    @BindView(R.id.textNote)
    TextView textNote;

    @BindView(R.id.buttonVerify)
    Button buttonVerify;

    @BindView(R.id.editOtp)
    EditText editOtp;

    @BindView(R.id.textResendOtp)
    TextView textResendOtp;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String PREFERENCE = "KALLAKURI";

    int paymentId = 4;

    String mobileNum, mOtp;

    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_order);

            ButterKnife.bind(this);
            context = this;

            userTableDAO = new UserTableDAO(this);
            productTableDAO = new ProductTableDAO(this);

            mobileNum = userTableDAO.getData().get(0).getPhoneNo();

            sharedpreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();

            name_h.setText(getString(R.string.PaymentOptions));

           textDeliverTo.setText(userTableDAO.getData().get(0).getDeliveryAddress());

        //init SmsVerifyCatcher
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                editOtp.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });

        smsVerifyCatcher.setPhoneNumberFilter("QP-UPDATE");

            left_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            rl_proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(paymentId ==4){
                        if (Network_info.isNetworkAvailable(context)) {

                            sendOTP(mobileNum);

                        } else {

                            Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

                        }
                    }
                }
            });

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validations();

            }
        });

        textResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_info.isNetworkAvailable(context)) {

                    sendOTP(mobileNum);

                } else {

                    Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

                }
            }
        });

            setDataCartAmount();
    }

    void setDataCartAmount(){
        if(sharedpreferences.contains("total_amount") && sharedpreferences.getFloat("total_amount", 0)!=0){
            textCodAmount.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
            subTotalAmount.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
            netPayableAmount.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
            totalAmount.setText(String.valueOf(sharedpreferences.getFloat("total_amount", 0)));
        }
    }

    private void sendOTP(String mobileNumber) {

        Dialogs.ProgressDialog(context);

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

            data.put("reqType", "forgot-password");
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

                    if(response.body().getHeader().getCode() ==  200){
                        OTPResponceModel responceModel = response.body();

                        String responePhoneNo = responceModel.getHeader().getPhoneNo();
                        String otpCode = responceModel.getHeader().getOtpCode();
                        mOtp = otpCode;

                        String phoneNo = responceModel.getHeader().getPhoneNo();
                        if(phoneNo == null) {
                            Dialogs.show_popUp(responceModel.getHeader().getMessage(), context);
                        }else {
                            ll_verify_otp.setVisibility(VISIBLE);
                            rl_proceed.setVisibility(View.GONE);
                            rl_proceed.setBackgroundColor(Color.GRAY);
                            rl_proceed.setClickable(false);
                            rl_proceed.setEnabled(false);
                            Toast.makeText(context, getResources().getString(R.string.otp_request_Sent), Toast.LENGTH_SHORT).hashCode();
                            textNote.setText(responePhoneNo+": Your One Time Password is KK - "+otpCode+" for registering an new Account with KK-GROUPS");

                        }
                    }else {
                        Dialogs.show_popUp(response.body().getHeader().getMessage(), context);
                        new Popup_Class().sendError("Send Otp Payment", response.body().getHeader().getMessage(), userTableDAO.getData().get(0).getId(), userTableDAO.getData().get(0).getPhoneNo());
                    }

                } else {
                    Dialogs.show_popUp(response.message(), context);
                    new Popup_Class().sendError("Send Otp Payment", response.message(), userTableDAO.getData().get(0).getId(), userTableDAO.getData().get(0).getPhoneNo());

                }
            }

            @Override
            public void onFailure(Call<OTPResponceModel> call, Throwable t) {
                Dialogs.Cancel();
                Dialogs.show_popUp(getResources().getString(R.string.error) + ": " + t.getMessage(), context);
                new Popup_Class().sendError("Send Otp Payment",  t.getMessage(), userTableDAO.getData().get(0).getId(), userTableDAO.getData().get(0).getPhoneNo());
            }
        });
    }

    public void validations(){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        verifyOtp(editOtp.getText().toString());

    }

    public void verifyOtp(String otp) {

        if(mOtp.equals(otp)){
           // startActivity(new Intent(OrderPaymentsActivity.this, PaymentSuccessFailure.class));
            if (Network_info.isNetworkAvailable(context)) {

                placeOrder();
            } else {

                Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

            }
        } else {
            Toast.makeText(OrderPaymentsActivity.this, getResources().getString(R.string.otp_not_match), Toast.LENGTH_SHORT).show();
        }
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

    void placeOrder(){
        Dialogs.ProgressDialog(context);
        JSONArray jsonArray = new JSONArray();
        List<ProductDetails> productDetailsList = productTableDAO.getProductsCart();

        for (ProductDetails productDetails:productDetailsList){
            if(!productDetails.getSelectedQty().equalsIgnoreCase("0")){
                JSONObject jsonObject = new JSONObject();
                try {
                jsonObject.put("brand",productDetails.getProductBrand());
                jsonObject.put("brandId",productDetails.getProductBrandId());
                jsonObject.put("catalog",productDetails.getCatalog());
                jsonObject.put("category",productDetails.getProductCategory());
                jsonObject.put("cost",productDetails.getProductCost());
                jsonObject.put("deliveryCharge",productDetails.getDeliveryCharge());
                jsonObject.put("deliveryTime",productDetails.getDeliveryTime());
                jsonObject.put("description",productDetails.getProductDescription());
                jsonObject.put("discount",productDetails.getProductDiscount());
                jsonObject.put("discountAmount",productDetails.getProductDiscountAmount());
                jsonObject.put("finalPrice",productDetails.getProductFinalPrice());
                jsonObject.put("id",String.valueOf(productDetails.getId()));
                jsonObject.put("image",productDetails.getProductImage());
                jsonObject.put("name",productDetails.getProductName());
                jsonObject.put("orderCode","");
                jsonObject.put("orderId",0);
                jsonObject.put("quantity",productDetails.getProductQuantity());
                jsonObject.put("orderedDateTime","");
                jsonObject.put("userPhoneNum",mobileNum);
                jsonObject.put("orderedQunatity", productDetails.getSelectedQty());
                jsonObject.put("deliveryStatus","");
                jsonObject.put("status","");
                jsonObject.put("subCatalog",productDetails.getCatalog());
                jsonObject.put("type",productDetails.getProductType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }
        }

        JsonParser jsonParser = new JsonParser();
        JsonArray mainJsonArray = (JsonArray) jsonParser.parse(jsonArray.toString());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<PlaceOrderDetails> call = apiService.placeOrder(PropertiesFile.baseUrlNew+"v1/placeOrder",/*"application/json",*/ mainJsonArray);

        call.enqueue(new Callback<PlaceOrderDetails>() {
            @Override
            public void onResponse(Call<PlaceOrderDetails> call, Response<PlaceOrderDetails> response) {

                Dialogs.Cancel();

                if (!response.isSuccessful()) {
                    Dialogs.show_popUp(getResources().getString(R.string.try_again), context);
                    return;
                }

                if (response.code() == 200) {
                    //Toast.makeText(context, getResources().getString(R.string.registration_successful), Toast.LENGTH_SHORT).show();
                    for (ProductDetails productDetails:productDetailsList) {
                        if (!productDetails.getSelectedQty().equalsIgnoreCase("0")) {
                            if(response.body().getStatus()==200){
                                ContentValues values = new ContentValues();
                                values.put("selectedQty", "0");
                                productTableDAO.updateRow("ProductDetails", values, "Product_Id", productDetails.getId());

                                ContentValues value1 = new ContentValues();
                                value1.put("selectedPrice", "0");
                                productTableDAO.updateRow("ProductDetails", value1, "Product_Id", productDetails.getId());

                                editor.putString("cart_count", "0");
                                editor.apply();

                                editor.putFloat("total_amount", 0).apply();
                                editor.commit();
                            }else {
                                new Popup_Class().sendError("placeOrder",  response.body().getMessage(), userTableDAO.getData().get(0).getId(), userTableDAO.getData().get(0).getPhoneNo());
                            }

                            startActivity(new Intent(OrderPaymentsActivity.this, PaymentSuccessFailure.class).putExtra("status", response.body().getStatus()).putExtra("message", response.body().getMessage()).putExtra("orderId", response.body().getOrderCode()));

                        }
                    }
                } else {
                    Dialogs.show_popUp(response.message(), context);
                    new Popup_Class().sendError("placeOrder",  response.message(), userTableDAO.getData().get(0).getId(), userTableDAO.getData().get(0).getPhoneNo());
                }

            }

            @Override
            public void onFailure(Call<PlaceOrderDetails> call, Throwable t) {
                Dialogs.Cancel();
                Dialogs.show_popUp(getResources().getString(R.string.error) + ": " + t.getMessage(), context);
                new Popup_Class().sendError("placeOrder",  t.getMessage(), userTableDAO.getData().get(0).getId(), userTableDAO.getData().get(0).getPhoneNo());
            }
        });

    }
}
