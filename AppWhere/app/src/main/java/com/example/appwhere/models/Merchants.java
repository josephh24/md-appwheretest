package com.example.appwhere.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Merchants {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("merchantName")
    @Expose
    public String merchantName;

    @SerializedName("merchantAddress")
    @Expose
    public String merchantAddress;

    @SerializedName("merchantTelephone")
    @Expose
    public String merchantTelephone;

    @SerializedName("latitude")
    @Expose
    public Double latitude;

    @SerializedName("longitude")
    @Expose
    public Double longitude;

    @SerializedName("registrationDate")
    @Expose
    public String registrationDate;

    public Merchants(String merchantName, String merchantAddress, String merchantTelephone, Double latitude, Double longitude) {
        this.merchantName = merchantName;
        this.merchantAddress = merchantAddress;
        this.merchantTelephone = merchantTelephone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantTelephone() {
        return merchantTelephone;
    }

    public void setMerchantTelephone(String merchantTelephone) {
        this.merchantTelephone = merchantTelephone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
