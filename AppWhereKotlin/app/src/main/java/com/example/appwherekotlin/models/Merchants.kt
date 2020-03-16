package com.example.appwherekotlin.models

import com.google.gson.annotations.SerializedName

class Merchants(
    @field:SerializedName("merchantName") var merchantName: String,
    @field:SerializedName("merchantAddress") var merchantAddress: String,
    @field:SerializedName("merchantTelephone") var merchantTelephone: String,
    @field:SerializedName("latitude") var latitude: Double,
    @field:SerializedName("longitude") var longitude: Double
) {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("registrationDate")
    var registrationDate: String? = null

}
