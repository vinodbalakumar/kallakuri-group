package com.example.kallakurigroup.models.loginmodel;

import com.example.kallakurigroup.models.HeaderModel;
import com.google.gson.annotations.SerializedName;

public class LoginResponceModel {

    @SerializedName("data")
    private LoginData data;

    @SerializedName("header")
    private HeaderModel headerModel;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public HeaderModel getHeaderModel() {
        return headerModel;
    }

    public void setHeaderModel(HeaderModel headerModel) {
        this.headerModel = headerModel;
    }
}
