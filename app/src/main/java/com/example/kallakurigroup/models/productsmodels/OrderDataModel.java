package com.example.kallakurigroup.models.productsmodels;

import com.example.kallakurigroup.models.productsmodels.BrandsDetails;
import com.example.kallakurigroup.models.productsmodels.OrderDetails;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDataModel {

    @SerializedName("orders")
    private List<OrderDetails> ordersModel;

    public List<OrderDetails> getOrdersModel() {
        return ordersModel;
    }

}
