package com.example.kallakurigroup.models.productsmodels;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class OrderDetails/* implements Parcelable */{

    @SerializedName("orderId")
    @DatabaseField(id = true,index = true,columnName = "orderId")
    private int orderId;

    @SerializedName("brand")
    @DatabaseField
    private String brand;

    @SerializedName("brandId")
    @DatabaseField
    private String brandId;

    @SerializedName("catalog")
    @DatabaseField
    private String catalog;

    @SerializedName("category")
    @DatabaseField
    private String category;

    @SerializedName("cost")
    @DatabaseField
    private String cost;

    @SerializedName("deliveryCharge")
    @DatabaseField
    private String deliveryCharge;

    @SerializedName("deliveryTime")
    @DatabaseField
    private String deliveryTime;

    @SerializedName("description")
    @DatabaseField
    private String description;

    @SerializedName("discount")
    @DatabaseField
    private String discount;

    @SerializedName("discountAmount")
    @DatabaseField
    private String discountAmount;

    @SerializedName("finalPrice")
    @DatabaseField
    private String finalPrice;

    @SerializedName("id")
    @DatabaseField
    private String id;

    @SerializedName("image")
    @DatabaseField
    private String image;

    @SerializedName("name")
    @DatabaseField
    private String name;

    @SerializedName("orderCode")
    @DatabaseField
    private String orderCode;

    @SerializedName("quantity")
    @DatabaseField
    private String quantity;

    @SerializedName("orderedDateTime")
    @DatabaseField
    private String orderedDateTime;

    @SerializedName("userPhoneNum")
    @DatabaseField
    private String userPhoneNum;

    @SerializedName("orderedQunatity")
    @DatabaseField
    private String orderedQunatity;

    @SerializedName("deliveryStatus")
    @DatabaseField
    private String deliveryStatus;

    @SerializedName("status")
    @DatabaseField
    private String status;

    @SerializedName("subCatalog")
    @DatabaseField
    private String subCatalog;

    @SerializedName("type")
    @DatabaseField
    private String type;


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderedDateTime() {
        return orderedDateTime;
    }

    public void setOrderedDateTime(String orderedDateTime) {
        this.orderedDateTime = orderedDateTime;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getOrderedQunatity() {
        return orderedQunatity;
    }

    public void setOrderedQunatity(String orderedQunatity) {
        this.orderedQunatity = orderedQunatity;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(String subCatalog) {
        this.subCatalog = subCatalog;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrderDetails(){

    }

    public OrderDetails(int orderId, String brand, String brandId, String catalog, String category, String cost, String deliveryCharge, String deliveryTime, String description, String discount, String discountAmount, String finalPrice, String id, String image, String name, String orderCode, String quantity, String orderedDateTime, String userPhoneNum, String orderedQunatity, String deliveryStatus, String status, String subCatalog, String type) {
        this.orderId = orderId;
        this.brand = brand;
        this.brandId = brandId;
        this.catalog = catalog;
        this.category = category;
        this.cost = cost;
        this.deliveryCharge = deliveryCharge;
        this.deliveryTime = deliveryTime;
        this.description = description;
        this.discount = discount;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
        this.id = id;
        this.image = image;
        this.name = name;
        this.orderCode = orderCode;
        this.quantity = quantity;
        this.orderedDateTime = orderedDateTime;
        this.userPhoneNum = userPhoneNum;
        this.orderedQunatity = orderedQunatity;
        this.deliveryStatus = deliveryStatus;
        this.status = status;
        this.subCatalog = subCatalog;
        this.type = type;
    }

    @BindingAdapter("android:productImageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
