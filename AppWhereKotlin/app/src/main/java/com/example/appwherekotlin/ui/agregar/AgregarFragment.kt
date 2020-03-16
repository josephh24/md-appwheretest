package com.example.appwherekotlin.ui.agregar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appwherekotlin.R
import com.example.appwherekotlin.api_app_where.ApiAppWhere
import com.example.appwherekotlin.models.Merchants
import com.example.appwherekotlin.validaciones.ResponseOperation
import com.example.appwherekotlin.validaciones.Validaciones
import kotlinx.android.synthetic.main.fragment_agregar.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class AgregarFragment : Fragment() {

    private val validarCampos: Validaciones = Validaciones()
    private var retrofit: Retrofit? = null
    private var cargando_agregar: ProgressBar? = null
    private var btn_agregar: Button? = null
    private val TAG = "Agregar"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_agregar, container, false)

        cargando_agregar = root.findViewById(R.id.cargando_agregar) as ProgressBar
        btn_agregar = root.findViewById(R.id.btn_agregar) as Button

        retrofit = Retrofit.Builder().baseUrl("http://166.62.33.53:4590/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getUnsafeOkHttpClient())
            .build()

        btn_agregar!!.setOnClickListener {
            val nombre: String = txt_agregar_nombre.text.toString()
            val direccion: String = txt_agregar_direccion.text.toString()
            val telefono: String = txt_agregar_telefono.text.toString()
            val latitud: String = txt_agregar_latitud.text.toString()
            val longitud: String = txt_agregar_longitud.text.toString()

            if (isValidForm(nombre, direccion, telefono, latitud, longitud)) {
                cargando_agregar!!.visibility = View.VISIBLE
                setSucursales(
                    nombre,
                    direccion,
                    telefono,
                    java.lang.Double.parseDouble(latitud),
                    java.lang.Double.parseDouble(longitud)
                )
            }
        }
        return root
    }

    private fun setSucursales(
        nombre: String,
        direccion: String,
        telefono: String,
        latitud: Double,
        longitud: Double
    ) {
        val datos = Merchants(nombre, direccion, telefono, longitud, latitud)
        val service: ApiAppWhere = retrofit!!.create(ApiAppWhere::class.java)
        val sucursalesCall = service.setSucursales(datos)

        sucursalesCall.enqueue(object : Callback<Merchants> {
            override fun onResponse(call: Call<Merchants>, response: Response<Merchants>) {
                if (response.isSuccessful) {
                    cargando_agregar!!.visibility = View.GONE
                    Toast.makeText(context, getString(R.string.agregar_correcto), Toast.LENGTH_LONG)
                        .show()
                    limpiar()
                } else {
                    cargando_agregar!!.visibility = View.GONE
                    Toast.makeText(
                        context,
                        getString(R.string.agregar_incorrecto),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d(TAG, "code: " + response.code())

                    return
                }
            }

            override fun onFailure(call: Call<Merchants>, t: Throwable) {
                cargando_agregar!!.visibility = View.GONE
                Log.d(TAG, "Error: " + t.message)
                Toast.makeText(
                    context,
                    getString(R.string.error_comunicacion) + " " + t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun isValidForm(
        nombre: String,
        direccion: String,
        telefono: String,
        latitud: String,
        longitud: String
    ): Boolean {
        var isValid = true
        var responseOperation: ResponseOperation = validarCampos.validaNombre(nombre, context!!)

        if (!responseOperation.status) {
            isValid = false
            txt_agregar_nombre.error = responseOperation.texResponse
        }

        responseOperation = validarCampos.validaDireccion(direccion, context!!)
        if (!responseOperation.status) {
            isValid = false
            txt_agregar_direccion.error = responseOperation.texResponse
        }

        responseOperation = validarCampos.validaTelefono(telefono, context!!)
        if (!responseOperation.status) {
            isValid = false
            txt_agregar_telefono.error = responseOperation.texResponse
        }

        responseOperation = validarCampos.validaLatitud(latitud, context!!)
        if (!responseOperation.status) {
            isValid = false
            txt_agregar_latitud.error = responseOperation.texResponse
        }

        responseOperation = validarCampos.validaLongitud(longitud, context!!)
        if (!responseOperation.status) {
            isValid = false
            txt_agregar_longitud.error = responseOperation.texResponse
        }

        return isValid
    }

    private fun limpiar() {
        txt_agregar_nombre.setText("")
        txt_agregar_direccion.setText("")
        txt_agregar_telefono.setText("")
        txt_agregar_latitud.setText("")
        txt_agregar_longitud.setText("")
    }

    fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(
                sslSocketFactory,
                trustAllCerts[0] as X509TrustManager
            )
            builder.hostnameVerifier { hostname: String?, session: SSLSession? -> true }
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}