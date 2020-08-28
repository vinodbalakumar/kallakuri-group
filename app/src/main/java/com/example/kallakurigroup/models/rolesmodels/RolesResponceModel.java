package com.example.kallakurigroup.models.rolesmodels;

import com.google.gson.annotations.SerializedName;

public class RolesResponceModel {

    @SerializedName("header")
    private RolesHeader header;

    @SerializedName("data")
    private RolesData rolesData;


    public RolesHeader getHeader() {
        return header;
    }

    public void setHeader(RolesHeader header) {
        this.header = header;
    }

    public RolesData getRolesData() {
        return rolesData;
    }

    public void setRolesData(RolesData rolesData) {
        this.rolesData = rolesData;
    }
}
