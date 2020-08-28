package com.example.kallakurigroup.models.productsmodels;

import com.google.gson.annotations.SerializedName;

public class ProductResponceModel {

    @SerializedName("header")
    private HeaderModel header;

    @SerializedName("data")
    private DataModel data;

    public HeaderModel getHeader() {
        return header;
    }

    public void setHeader(HeaderModel header) {
        this.header = header;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }
}
