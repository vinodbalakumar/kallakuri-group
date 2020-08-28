package com.example.kallakurigroup.models.productsmodels;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;


import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class ProductDetails implements Parcelable {

    @SerializedName("int")
    private int id;

    @SerializedName("productName")
    private String productName;

    @SerializedName("productCost")
    private String productCost;

    @SerializedName("productDescription")
    private String productDescription;

    @SerializedName("productImage")
    private String productImage;

    @SerializedName("catalog")
    private String catalog;

    @SerializedName("subCatalog")
    private String subCatalog;

    @SerializedName("status")
    private String status;

    @SerializedName("productDiscount")
    private String productDiscount;

    @SerializedName("productFinalPrice")
    private String productFinalPrice;

    @SerializedName("productType")
    private String productType;

    @SerializedName("productCategory")
    private String productCategory;

    @SerializedName("productBrand")
    private String productBrand;

    @SerializedName("productBrandId")
    private String productBrandId;

    @SerializedName("productQuantity")
    private String productQuantity;

    @SerializedName("productDiscountAmount")
    private String productDiscountAmount;

    @SerializedName("deliveryTime")
    private String deliveryTime;

    @SerializedName("deliveryCharge")
    private String deliveryCharge;

    protected ProductDetails(Parcel in) {
        id = in.readInt();
        productName = in.readString();
        productCost = in.readString();
        productDescription = in.readString();
        productImage = in.readString();
        catalog = in.readString();
        subCatalog = in.readString();
        status = in.readString();
        productDiscount = in.readString();
        productFinalPrice = in.readString();
        productType = in.readString();
        productCategory = in.readString();
        productBrand = in.readString();
        productBrandId = in.readString();
        productQuantity = in.readString();
        productDiscountAmount = in.readString();
        deliveryTime = in.readString();
        deliveryCharge = in.readString();
    }

    public static final Creator<ProductDetails> CREATOR = new Creator<ProductDetails>() {
        @Override
        public ProductDetails createFromParcel(Parcel in) {
            return new ProductDetails(in);
        }

        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCost() {
        return productCost;
    }

    public void setProductCost(String productCost) {
        this.productCost = productCost;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(String subCatalog) {
        this.subCatalog = subCatalog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductFinalPrice() {
        return productFinalPrice;
    }

    public void setProductFinalPrice(String productFinalPrice) {
        this.productFinalPrice = productFinalPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(String productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductDiscountAmount() {
        return productDiscountAmount;
    }

    public void setProductDiscountAmount(String productDiscountAmount) {
        this.productDiscountAmount = productDiscountAmount;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(productName);
        parcel.writeString(productCost);
        parcel.writeString(productDescription);
        parcel.writeString(productImage);
        parcel.writeString(catalog);
        parcel.writeString(subCatalog);
        parcel.writeString(status);
        parcel.writeString(productDiscount);
        parcel.writeString(productFinalPrice);
        parcel.writeString(productType);
        parcel.writeString(productCategory);
        parcel.writeString(productBrand);
        parcel.writeString(productBrandId);
        parcel.writeString(productQuantity);
        parcel.writeString(productDiscountAmount);
        parcel.writeString(deliveryTime);
        parcel.writeString(deliveryCharge);
    }

    public static final Creator creater = new Creator(){
        @Override
        public Object createFromParcel(Parcel parcel) {
            return parcel;
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[i];
        }
    };

    @BindingAdapter("android:productImageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView).load(imageUrl).into(imageView);
    }
}
