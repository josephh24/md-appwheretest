package com.example.appwherekotlin.models

import com.example.appwherekotlin.models.Merchants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sucursales {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("merchants")
    @Expose
    var merchants: List<Merchants>? = null

}