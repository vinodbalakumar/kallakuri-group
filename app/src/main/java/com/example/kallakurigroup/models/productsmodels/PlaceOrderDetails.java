package com.example.kallakurigroup.models.productsmodels;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class PlaceOrderDetails/* implements Parcelable */{

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("orderCode")
    private String orderCode;


    public PlaceOrderDetails(){

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getOrderCode() {
        return orderCode;
    }
}
