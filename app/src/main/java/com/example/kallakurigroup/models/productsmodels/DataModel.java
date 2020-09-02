package com.example.kallakurigroup.models.productsmodels;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class DataModel {

    @SerializedName("reqType")
    private String reqType;

    @SerializedName("productPricings")
    private Map<String, List<ProductDetails>> productPricings;

    @SerializedName("brands")
    private List<BrandsDetails> brandsList;

    @SerializedName("latestProducts")
    private List<TopBrandsDetails> latestProducts;

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public Map<String, List<ProductDetails>> getProductPricings() {
        return productPricings;
    }

    public void setProductPricings(Map<String, List<ProductDetails>> productPricings) {
        this.productPricings = productPricings;
    }

    public List<BrandsDetails> getBrandsList() {
        return brandsList;
    }

    public void setBrandsList(List<BrandsDetails> brandsList) {
        this.brandsList = brandsList;
    }

    public List<TopBrandsDetails> getLatestProducts() {
        return latestProducts;
    }

    public void setLatestProducts(List<TopBrandsDetails> latestProducts) {
        this.latestProducts = latestProducts;
    }
}
