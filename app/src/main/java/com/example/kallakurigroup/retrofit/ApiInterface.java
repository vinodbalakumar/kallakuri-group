package com.example.kallakurigroup.retrofit;

import com.example.kallakurigroup.models.ResetPinResponse;
import com.example.kallakurigroup.models.loginmodel.LoginResponceModel;
import com.example.kallakurigroup.models.otpmodels.OTPResponceModel;
import com.example.kallakurigroup.models.productsmodels.ProductResponceModel;
import com.example.kallakurigroup.models.rolesmodels.RolesResponceModel;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("v1/products/{id}")
    Call<ProductResponceModel> getProducts(
            @Path("id") String roleId
    );

}
