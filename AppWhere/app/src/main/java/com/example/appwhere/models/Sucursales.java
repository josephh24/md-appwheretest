package com.example.appwhere.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sucursales {

    @SerializedName("status")
    @Expose
    public Integer status;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("merchants")
    @Expose
    public List<Merchants> merchants = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Merchants> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchants> merchants) {
        this.merchants = merchants;
    }
}
