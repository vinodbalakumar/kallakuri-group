package com.example.kallakurigroup.retrofit;

import com.example.kallakurigroup.models.MainModel;
import com.example.kallakurigroup.models.ResetPinResponse;
import com.example.kallakurigroup.models.loginmodel.LoginResponceModel;
import com.example.kallakurigroup.models.otpmodels.OTPResponceModel;
import com.example.kallakurigroup.models.productsmodels.PlaceOrderDetails;
import com.example.kallakurigroup.models.productsmodels.ProductResponceModel;
import com.example.kallakurigroup.models.rolesmodels.RolesResponceModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("v1/get-roles")
    Call<RolesResponceModel> getRoles();

    @POST("v1/otp-validating")
    Call<OTPResponceModel> getOTP(
            @Header("Content-Type") String contentType,
            @Body JsonObject jsonObject
    );

    @POST("v1/create-profile")
    Call<LoginResponceModel> createUser(
            @Header("Content-Type") String contentType,
            @Body JsonObject jsonObject
    );

    @POST("v1/sign-in")
    Call<LoginResponceModel> loginUser(
            @Header("Content-Type") String contentType,
            @Body JsonObject jsonObject
    );

    @POST("v1/forgot-password")
    Call<ResetPinResponse> resetPass(
            @Header("Content-Type") String contentType,
            @Body JsonObject jsonObject
    );

    @POST
    Call<ProductResponceModel> getProducts(@Url String url, @Body JsonObject jsonObject);

    @POST
    Call<PlaceOrderDetails> placeOrder(@Url String url,
                                       @Body JsonArray jsonArray
    );

    @POST
    Call<MainModel> getOrders(@Url String url,
                              @Body JsonObject jsonObject
    );

    @GET("v1/referral/{phoneNo}")
    Call<JsonObject> getReferral(
            @Path("phoneNo") String phoneNo
    );

    @POST("v1/error")
    Call<JsonElement> sendError(
            @Body JsonObject jsonObject
    );
}
