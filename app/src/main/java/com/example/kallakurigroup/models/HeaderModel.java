package com.example.kallakurigroup.models;

import com.example.kallakurigroup.models.productsmodels.OrderDataModel;
import com.google.gson.annotations.SerializedName;

public class HeaderModel {

    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
