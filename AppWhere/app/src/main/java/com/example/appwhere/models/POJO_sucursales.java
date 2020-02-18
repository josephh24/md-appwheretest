package com.example.appwhere.models;

public class POJO_sucursales {

    public String id;

    public String merchantName;

    public String merchantAddress;

    public String merchantTelephone;

    public Double latitude;

    public Double longitude;

    public String registrationDate;

    public POJO_sucursales(String id, String merchantName, String merchantAddress, String merchantTelephone, Double latitude, Double longitude, String registrationDate) {
        this.id = id;
        this.merchantName = merchantName;
        this.merchantAddress = merchantAddress;
        this.merchantTelephone = merchantTelephone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.registrationDate = registrationDate;
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
