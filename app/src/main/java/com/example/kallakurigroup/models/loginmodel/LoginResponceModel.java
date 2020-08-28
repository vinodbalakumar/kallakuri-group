package com.example.kallakurigroup.models.loginmodel;

import com.google.gson.annotations.SerializedName;

public class LoginResponceModel {

    @SerializedName("data")
    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
