package com.example.kallakurigroup.models.otpmodels;

import com.google.gson.annotations.SerializedName;

public class OTPDataModel {

    @SerializedName("reqType")
    private String reqType;

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
}
