package com.example.kallakurigroup.models.rolesmodels;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RolesData {

    @SerializedName("reqType")
    private String reqType;

    @SerializedName("roles")
    private Map<String, Integer> roles;

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public Map<String, Integer> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Integer> roles) {
        this.roles = roles;
    }
}
