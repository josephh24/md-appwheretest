package com.example.appwherekotlin.validaciones

import android.content.Context
import com.example.appwherekotlin.R
import java.util.regex.Pattern

class Validaciones {
    private val REGEX_NOMBRE = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ](?!.*([\\s])\\1)([a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*)+[a-zA-ZáéíóúÁÉÍÓÚñÑ]$"
    private val REGEX_EMAIL = "^[a-zA-Z0-9](?!.*([._-])\\1)[a-zA-Z0-9._-]+[a-zA-Z0-9_-]+@(([a-zA-Z0-9]{3,})+\\.+([a-zA-Z]){2,3}){1}$"
    private val REGEX_PASS_LOGIN = "^[a-zA-Z0-9!ñÑ@#$%&*\\-_]+$"
    private val REGEX_NUMEROS = "^[0-9]+$"

    /**
     * Jose Pablo Sanchez
     * Metodo que valida el campo de email
     */
    fun validaEmail(emal: String, context: Context): ResponseOperation {
        val patron = Pattern.compile(REGEX_EMAIL)
        if (emal.isEmpty()) { // validacion de campo vacio
            return ResponseOperation(false, context.getString(R.string.validacion_vacio))
        } else if (!patron.matcher(emal).matches()) { //validacion con regex
            return ResponseOperation(false, context.getString(R.string.validacion_formato))
        }
        return ResponseOperation(true, context.getString(R.string.validacion_correcto))
    }

    fun validaPass(pass: String, context: Context): ResponseOperation {
        val patron = Pattern.compile(REGEX_PASS_LOGIN)
        if (pass.isEmpty()) { // validacion de campo vacio
            return ResponseOperation(false, context.getString(R.string.validacion_vacio))
        } else if (!patron.matcher(pass).matches()) { //validacion con regex
            return ResponseOperation(false, context.getString(R.string.validacion_formato))
        }
        return ResponseOperation(true, context.getString(R.string.validacion_correcto))
    }

    fun validaNombre(nombre: String, context: Context): ResponseOperation {
        val patron = Pattern.compile(REGEX_NOMBRE)
        if (nombre.isEmpty()) { // validacion de campo vacio
            return ResponseOperation(false, context.getString(R.string.validacion_vacio))
        } else if (!patron.matcher(nombre).matches()) { //validacion con regex
            return ResponseOperation(false, context.getString(R.string.validacion_formato))
        }
        return ResponseOperation(true, context.getString(R.string.validacion_correcto))
    }

    fun validaDireccion(direccion: String, context: Context): ResponseOperation {
        return if (direccion.isEmpty()) { // validacion de campo vacio
            ResponseOperation(false, context.getString(R.string.validacion_vacio))
        } else ResponseOperation(true, context.getString(R.string.validacion_correcto))
    }

    fun validaTelefono(telefono: String, context: Context): ResponseOperation {
        val patron = Pattern.compile(REGEX_NUMEROS)
        if (telefono.isEmpty()) { // validacion de campo vacio
            return ResponseOperation(false, context.getString(R.string.validacion_vacio))
        } else if (!patron.matcher(telefono).matches()) { //validacion con regex
            return ResponseOperation(false, context.getString(R.string.validacion_formato))
        }
        return ResponseOperation(true, context.getString(R.string.validacion_correcto))
    }

    fun validaLatitud(latitud: String, context: Context): ResponseOperation {
        return if (latitud.isEmpty()) { // validacion de campo vacio
            ResponseOperation(false, context.getString(R.string.validacion_vacio))
        } else ResponseOperation(true, context.getString(R.string.validacion_correcto))
    }

    fun validaLongitud(longitud: String, context: Context): ResponseOperation {
        return if (longitud.isEmpty()) { // validacion de campo vacio
            ResponseOperation(false, context.getString(R.string.validacion_vacio))
        } else ResponseOperation(true, context.getString(R.string.validacion_correcto))
    }
}