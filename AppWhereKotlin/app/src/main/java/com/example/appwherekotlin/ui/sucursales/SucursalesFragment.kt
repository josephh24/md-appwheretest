package com.example.appwherekotlin.ui.sucursales

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.appwherekotlin.R
import com.example.appwherekotlin.adaptador.AdaptadorSucursal
import com.example.appwherekotlin.api_app_where.ApiAppWhere
import com.example.appwherekotlin.models.POJO_sucursales
import com.example.appwherekotlin.models.Sucursales

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class SucursalesFragment : Fragment() {
    private var layoutManager: LinearLayoutManager? = null
    private var adaptadorSucursal: AdaptadorSucursal? = null
    private var lista_sucursal: ArrayList<POJO_sucursales>? = null
    private var retrofit: Retrofit? = null
    private var cargando_sucursal: ProgressBar? = null
    private var content_no_wifi_sucursal: RelativeLayout? = null
    private var btn_reintentar_sucursal: Button? = null
    private var rv_sucursales: RecyclerView? = null
    private  var sf_screen: SwipeRefreshLayout? = null
    private val TAG = "Sucursales_lista"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_sucursales, container, false)

        cargando_sucursal = root.findViewById(R.id.cargando_sucursal) as ProgressBar
        content_no_wifi_sucursal = root.findViewById(R.id.content_no_wifi_sucursal) as RelativeLayout
        btn_reintentar_sucursal = root.findViewById(R.id.btn_reintentar_sucursal) as Button
        rv_sucursales = root.findViewById(R.id.rv_sucursales) as RecyclerView
        sf_screen = root.findViewById(R.id.sf_screen) as SwipeRefreshLayout

        lista_sucursal = ArrayList()
        retrofit = Retrofit.Builder().baseUrl("http://166.62.33.53:4590/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        obtenerSucursales()

        btn_reintentar_sucursal!!.setOnClickListener {
            obtenerSucursales()
        }

        sf_screen!!.setOnRefreshListener {
            obtenerSucursales()
        }

        return root
    }

    private fun obtenerSucursales() {
        cargando_sucursal!!.visibility = View.VISIBLE
        content_no_wifi_sucursal!!.visibility = View.GONE
        val service: ApiAppWhere = retrofit!!.create(ApiAppWhere::class.java)
        service.getSucursales().enqueue(object : Callback<Sucursales?> {
            override fun onResponse(call: Call<Sucursales?>, response: Response<Sucursales?>) {
                sf_screen!!.isRefreshing = false
                if (response.body() != null) {
                    val sucursales: Sucursales? = response.body()
                    for (merchants in sucursales?.merchants!!) {
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
                    cargando_sucursal!!.visibility = View.INVISIBLE
                    lista()
                } else {
                    cargando_sucursal!!.visibility = View.INVISIBLE
                    content_no_wifi_sucursal!!.visibility = View.VISIBLE
                    Log.d(TAG, "onResponseBody")
                }
            }

            override fun onFailure(call: Call<Sucursales?>, t: Throwable) {
                sf_screen!!.isRefreshing = false
                cargando_sucursal!!.visibility = View.INVISIBLE
                content_no_wifi_sucursal!!.visibility = View.VISIBLE
                Log.d(TAG, "onFail: " + t.message)
            }
        })
    }

    private fun lista() {
        //recycler
        rv_sucursales!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        rv_sucursales!!.layoutManager = layoutManager
        adaptadorSucursal =
            lista_sucursal?.let { context?.let { it1 -> AdaptadorSucursal(it, it1) } }
        rv_sucursales!!.adapter = adaptadorSucursal
        rv_sucursales!!.addItemDecoration(
            DividerItemDecoration(context, layoutManager!!.orientation)
        )
    }
}