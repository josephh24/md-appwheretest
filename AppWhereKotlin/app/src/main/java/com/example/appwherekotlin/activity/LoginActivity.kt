package com.example.appwherekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appwherekotlin.api_app_where.ApiAppWhere
import com.example.appwherekotlin.R
import com.example.appwherekotlin.models.Login
import com.example.appwherekotlin.validaciones.ResponseOperation
import com.example.appwherekotlin.validaciones.Validaciones
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private val validarCampos: Validaciones = Validaciones()
    private var retrofit: Retrofit? = null
    private val TAG = "Login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        retrofit = Retrofit.Builder().baseUrl("http://166.62.33.53:4590/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        btn_login_entrar.setOnClickListener {
            val correo: String = txt_correo_login!!.text.toString()
            val pass: String = txt_pass_login!!.text.toString()
            if (isValidForm(correo, pass)) {
                cargando!!.visibility = View.VISIBLE
                obtenerSUser(correo, pass)
            }
        }
    }

    private fun obtenerSUser(email: String, pass: String) {
        val service: ApiAppWhere = retrofit!!.create(
            ApiAppWhere::class.java
        )
        service.getLogin(email, pass).enqueue(object : Callback<Login?> {
            override fun onResponse(call: Call<Login?>, response: Response<Login?>) {
                if (response.body() != null) {
                    val login: Login? = response.body()

                    if (login?.isSuccessful!!) {
                        cargando.visibility = View.INVISIBLE
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    } else {
                        cargando.visibility = View.INVISIBLE
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.login_incorrecto),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    cargando.visibility = View.INVISIBLE
                    Log.d(TAG, "onResponseBody" + response.raw().request().url())
                }
            }

            override fun onFailure(call: Call<Login?>, t: Throwable) {
                cargando.visibility = View.INVISIBLE
                Log.d(TAG, "onFail: " + t.message)
                Toast.makeText(
                    applicationContext,
                    getString(R.string.error_comunicacion) + " " + t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun isValidForm(email: String, pass: String): Boolean {
        var isValid = true
        var responseOperation: ResponseOperation = validarCampos.validaEmail(email, this)
        if (!responseOperation.status) {
            isValid = false
            txt_correo_login!!.error = responseOperation.texResponse
        }
        responseOperation = validarCampos.validaPass(pass, this)
        if (!responseOperation.status) {
            isValid = false
            txt_pass_login!!.error = responseOperation.texResponse
        }
        return isValid
    }
}
