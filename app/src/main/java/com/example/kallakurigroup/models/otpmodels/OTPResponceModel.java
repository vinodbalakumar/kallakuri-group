package com.example.kallakurigroup.models.otpmodels;

import com.google.gson.annotations.SerializedName;

public class OTPResponceModel {

    @SerializedName("header")
    private OTPHeaderModel header;

    @SerializedName("data")
    private OTPDataModel data;

    public OTPHeaderModel getHeader() {
        return header;
    }

    public void setHeader(OTPHeaderModel header) {
        this.header = header;
    }

    public OTPDataModel getData() {
        return data;
    }

    public void setData(OTPDataModel data) {
        this.data = data;
    }
}
