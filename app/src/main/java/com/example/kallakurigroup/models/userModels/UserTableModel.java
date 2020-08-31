package com.example.kallakurigroup.models.userModels;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class UserTableModel {

    @SerializedName("id")
    @DatabaseField(id = true,index = true,columnName = "user_id")
    private int id;

    @SerializedName("imeiNo")
    @DatabaseField
    private String imeiNo;

    @SerializedName("phoneNo")
    @DatabaseField
    private String phoneNo;

    @SerializedName("role")
    @DatabaseField
    private String role;

    @SerializedName("roleNumber")
    @DatabaseField
    private String roleNumber;

    @SerializedName("name")
    @DatabaseField
    private String name;

    @SerializedName("email")
    @DatabaseField
    private String email;

    @SerializedName("password")
    @DatabaseField
    private String password;

    @SerializedName("village")
    @DatabaseField
    private String village;

    @SerializedName("town")
    @DatabaseField
    private String town;

    @SerializedName("city")
    @DatabaseField
    private String city;

    @SerializedName("district")
    @DatabaseField
    private String district;

    @SerializedName("productName")
    @DatabaseField
    private String state;

    @SerializedName("pincode")
    @DatabaseField
    private String pincode;

    @SerializedName("deliveryAddress")
    @DatabaseField
    private String deliveryAddress;

    public UserTableModel() {
    }

    public UserTableModel(int id, String imeiNo, String phoneNo, String role, String roleNumber, String name, String email, String password, String village, String town, String city, String district, String state, String pincode, String deliveryAddress, String geoLocation) {
        this.id = id;
        this.imeiNo = imeiNo;
        this.phoneNo = phoneNo;
        this.role = role;
        this.roleNumber = roleNumber;
        this.name = name;
        this.email = email;
        this.password = password;
        this.village = village;
        this.town = town;
        this.city = city;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.deliveryAddress = deliveryAddress;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleNumber() {
        return roleNumber;
    }

    public void setRoleNumber(String roleNumber) {
        this.roleNumber = roleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    @SerializedName("geoLocation")
    @DatabaseField
    private String geoLocation;

}
