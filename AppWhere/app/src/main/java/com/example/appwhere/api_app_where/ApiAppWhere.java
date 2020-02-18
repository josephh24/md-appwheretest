package com.example.appwhere.api_app_where;

import com.example.appwhere.models.Login;
import com.example.appwhere.models.Merchants;
import com.example.appwhere.models.Sucursales;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiAppWhere {

    String INICIO_SESION = "session/login";
    String SURCURSALES = "get-merchants";
    String AGREGAR_SUCURSAL = "register-merchant";


    @GET(INICIO_SESION)
    Call<Login> getLogin(@Query("email") String email, @Query("password") String password);


    @GET(SURCURSALES)
    Call<Sucursales> getSucursales();


    @POST(AGREGAR_SUCURSAL)
    Call<Merchants> setSucursales(@Body Merchants merchants);

}
