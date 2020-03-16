package com.example.appwherekotlin.api_app_where

import com.example.appwherekotlin.models.Login
import com.example.appwherekotlin.models.Merchants
import com.example.appwherekotlin.models.Sucursales
import retrofit2.Call
import retrofit2.http.*

interface ApiAppWhere {

    companion object {
        const val INICIO_SESION = "session/login"
        const val SURCURSALES = "get-merchants"
        const val AGREGAR_SUCURSAL = "register-merchant"
    }

    @GET(INICIO_SESION)
    fun getLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<Login?>

    @GET(SURCURSALES)
    fun getSucursales(): Call<Sucursales>

    @POST(AGREGAR_SUCURSAL)
    fun setSucursales(@Body merchant : Merchants): Call<Merchants>

    /*
    @FormUrlEncoded
    @POST(AGREGAR_SUCURSAL)
    fun setSucursales(
        @Field("merchantName") merchantName: String,
        @Field("merchantAddress") merchantAddress: String,
        @Field("merchantTelephone") merchantTelephone: String,
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double
    ): Call<Merchants>

     */


}