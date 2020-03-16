package com.example.appwherekotlin.ui.mapa

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appwherekotlin.R
import com.example.appwherekotlin.api_app_where.ApiAppWhere
import com.example.appwherekotlin.models.POJO_sucursales
import com.example.appwherekotlin.models.Sucursales
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MapaFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var lista_sucursal: ArrayList<POJO_sucursales>? = null

    private var retrofit: Retrofit? = null
    private val TAG = "Mapa"



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_mapa, container, false)


        val mMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mMapFragment!!.getMapAsync(this)

        lista_sucursal = ArrayList()

        retrofit = Retrofit.Builder().baseUrl("http://166.62.33.53:4590/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return root
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!

        obtenerSucursales()
    }

    private fun obtenerSucursales() {
        val service: ApiAppWhere = retrofit!!.create(ApiAppWhere::class.java)
        val sucursalesCall: Call<Sucursales> = service.getSucursales()

        sucursalesCall.enqueue(object : Callback<Sucursales> {
            override fun onResponse(
                call: Call<Sucursales>,
                response: Response<Sucursales>
            ) {
                if (response.body() != null) {
                    val sucursales: Sucursales = response.body()!!
                    for (merchants in sucursales.merchants!!) {
                        lista_sucursal!!.add(
                            POJO_sucursales(
                                merchants.id!!,
                                merchants.merchantName,
                                merchants.merchantAddress,
                                merchants.merchantTelephone,
                                merchants.latitude,
                                merchants.longitude,
                                merchants.registrationDate!!
                            )
                        )
                    }
                    for (i in lista_sucursal!!.indices) {
                        val lugar = LatLng(
                            lista_sucursal!![i].latitude,
                            lista_sucursal!![i].longitude
                        )
                        mMap.addMarker(
                            MarkerOptions()
                                .position(lugar)
                                .title(lista_sucursal!![i].merchantName)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_localization))
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(lugar))
                    }
                } else {
                    noInternet()
                    Log.d(TAG, "onResponseBody")
                }
            }

            override fun onFailure(
                call: Call<Sucursales>,
                t: Throwable
            ) {
                noInternet()
                Log.d(TAG, "onFail: " + t.message)
            }
        })
    }

    fun noInternet(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.no_wifi))
        builder.setMessage(getString(R.string.btn_reintentar))
        builder.setCancelable(false)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            obtenerSucursales()
        }
        builder.show()
    }
}