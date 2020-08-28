package com.example.kallakurigroup.models.localdbmodels;

public class UserTableModel {

    private int uno;
    private String phoneNo;
    private String roleName;
    private String roleNumber;
    private String name;
    private String email;
    private String password;
    private String village;
    private String town;
    private String district;
    private String state;
    private String pincode;

    public UserTableModel() {
    }

    public UserTableModel(int uno, String phoneNo, String roleName, String roleNumber, String name, String email, String password, String village, String town, String district, String state, String pincode) {
        this.uno = uno;
        this.phoneNo = phoneNo;
        this.roleName = roleName;
        this.roleNumber = roleNumber;
        this.name = name;
        this.email = email;
        this.password = password;
        this.village = village;
        this.town = town;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
    }

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

}
