package com.example.kallakurigroup.models.otpmodels;

import com.google.gson.annotations.SerializedName;

public class OTPHeaderModel {

    @SerializedName("otpCode")
    private String otpCode;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("phoneNo")
    private String phoneNo;

    @SerializedName("status")
    private boolean status;

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
