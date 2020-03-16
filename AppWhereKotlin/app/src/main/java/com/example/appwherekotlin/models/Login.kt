package com.example.appwherekotlin.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("successful")
    @Expose
    var isSuccessful = false

}