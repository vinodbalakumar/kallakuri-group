package com.example.kallakurigroup.models.rolesmodels;

import com.google.gson.annotations.SerializedName;

public class RolesHeader {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("success")
    private String success;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
