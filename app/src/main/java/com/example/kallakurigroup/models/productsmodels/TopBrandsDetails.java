package com.example.kallakurigroup.models.productsmodels;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class TopBrandsDetails {

    @SerializedName("id")
    @DatabaseField(id = true,index = true,columnName = "Brand_Id")
    private int id;

    @SerializedName("brandName")
    @DatabaseField
    private String brandName;

    @SerializedName("description")
    @DatabaseField
    private String description;

    @SerializedName("imagePath")
    @DatabaseField
    private String imagePath;

    @SerializedName("types")
    @DatabaseField
    private String types;

    @SerializedName("status")
    @DatabaseField
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @BindingAdapter("android:imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

}
