package com.example.kallakurigroup.models.loginmodel;

import com.google.gson.annotations.SerializedName;

public class LoginProfileModel {

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("district")
    private String district;

    @SerializedName("email")
    private String email;

    @SerializedName("geoLocation")
    private String geoLocation;

    @SerializedName("id")
    private int id;

    @SerializedName("imeiNo")
    private String imeiNo;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("phoneNo")
    private String phoneNo;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("reference")
    private String reference;

    @SerializedName("role")
    private String role;

    @SerializedName("state")
    private String state;

    @SerializedName("status")
    private String status;

    @SerializedName("town")
    private String town;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("village")
    private String village;

    @SerializedName("city")
    private String city;

    @SerializedName("deliveryAddress")
    private String deliveryAddress;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
