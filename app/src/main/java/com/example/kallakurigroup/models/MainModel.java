package com.example.kallakurigroup.models;

import com.example.kallakurigroup.models.productsmodels.OrderDataModel;
import com.google.gson.annotations.SerializedName;

public class MainModel {

    @SerializedName("header")
    private HeaderModel headerModel;

    @SerializedName("data")
    private OrderDataModel data;

    public OrderDataModel getData() {
        return data;
    }

    public HeaderModel getHeaderModel() {
        return headerModel;
    }
}
