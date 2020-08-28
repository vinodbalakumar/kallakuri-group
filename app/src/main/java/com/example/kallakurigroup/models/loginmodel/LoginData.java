package com.example.kallakurigroup.models.loginmodel;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("userProfile")
    private LoginProfileModel loginProfileModel;

    public LoginProfileModel getLoginProfileModel() {
        return loginProfileModel;
    }

    public void setLoginProfileModel(LoginProfileModel loginProfileModel) {
        this.loginProfileModel = loginProfileModel;
    }

}
