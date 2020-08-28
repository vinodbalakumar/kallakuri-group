package com.example.kallakurigroup.models.productsmodels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class DataModel {

    @SerializedName("reqType")
    private String reqType;

    @SerializedName("productPricings")
    private Map<String, ArrayList<ProductDetails>> productPricings;

    @SerializedName("brands")
    private ArrayList<BrandsDetails> brandsList;

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public Map<String, ArrayList<ProductDetails>> getProductPricings() {
        return productPricings;
    }

    public void setProductPricings(Map<String, ArrayList<ProductDetails>> productPricings) {
        this.productPricings = productPricings;
    }

    public ArrayList<BrandsDetails> getBrandsList() {
        return brandsList;
    }

    public void setBrandsList(ArrayList<BrandsDetails> brandsList) {
        this.brandsList = brandsList;
    }
}
