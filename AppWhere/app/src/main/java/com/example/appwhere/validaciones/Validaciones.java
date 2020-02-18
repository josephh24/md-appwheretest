package com.example.appwhere.validaciones;

import android.content.Context;

import com.example.appwhere.R;

import java.util.regex.Pattern;

public class Validaciones {

    private String REGEX_NOMBRE = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ](?!.*([\\s])\\1)([a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*)+[a-zA-ZáéíóúÁÉÍÓÚñÑ]$";
    private String REGEX_EMAIL = "^[a-zA-Z0-9](?!.*([._-])\\1)[a-zA-Z0-9._-]+[a-zA-Z0-9_-]+@(([a-zA-Z0-9]{3,})+\\.+([a-zA-Z]){2,3}){1}$";
    private String REGEX_PASS_LOGIN = "^[a-zA-Z0-9!ñÑ@#$%&*\\-_]+$";
    private String REGEX_NUMEROS = "^[0-9]+$";

    /**
     * Jose Pablo Sanchez
     * Metodo que valida el campo de email
     */
    public ResponseOperation validaEmail(String emal, Context context) {
        Pattern patron = Pattern.compile(REGEX_EMAIL);
        if (emal.isEmpty()) {// validacion de campo vacio
            return new ResponseOperation(false, context.getString(R.string.validacion_vacio));
        } else if (!patron.matcher(emal).matches()) {//validacion con regex
            return new ResponseOperation(false, context.getString(R.string.validacion_formato));
        }
        return new ResponseOperation(true, context.getString(R.string.validacion_correcto));
    }

    public ResponseOperation validaPass(String pass, Context context) {
        Pattern patron = Pattern.compile(REGEX_PASS_LOGIN);
        if (pass.isEmpty()) {// validacion de campo vacio
            return new ResponseOperation(false, context.getString(R.string.validacion_vacio));
        } else if (!patron.matcher(pass).matches()) {//validacion con regex
            return new ResponseOperation(false, context.getString(R.string.validacion_formato));
        }
        return new ResponseOperation(true, context.getString(R.string.validacion_correcto));
    }

    public ResponseOperation validaNombre(String nombre, Context context) {
        Pattern patron = Pattern.compile(REGEX_NOMBRE);
        if (nombre.isEmpty()) {// validacion de campo vacio
            return new ResponseOperation(false, context.getString(R.string.validacion_vacio));
        } else if (!patron.matcher(nombre).matches()) {//validacion con regex
            return new ResponseOperation(false, context.getString(R.string.validacion_formato));
        }
        return new ResponseOperation(true, context.getString(R.string.validacion_correcto));
    }

    public ResponseOperation validaDireccion(String direccion, Context context) {
        if (direccion.isEmpty()) {// validacion de campo vacio
            return new ResponseOperation(false, context.getString(R.string.validacion_vacio));
        }
        return new ResponseOperation(true, context.getString(R.string.validacion_correcto));
    }

    public ResponseOperation validaTelefono(String telefono, Context context) {
        Pattern patron = Pattern.compile(REGEX_NUMEROS);
        if (telefono.isEmpty()) {// validacion de campo vacio
            return new ResponseOperation(false, context.getString(R.string.validacion_vacio));
        } else if (!patron.matcher(telefono).matches()) {//validacion con regex
            return new ResponseOperation(false, context.getString(R.string.validacion_formato));
        }
        return new ResponseOperation(true, context.getString(R.string.validacion_correcto));
    }

    public ResponseOperation validaLatitud(String latitud, Context context) {
        if (latitud.isEmpty()) {// validacion de campo vacio
            return new ResponseOperation(false, context.getString(R.string.validacion_vacio));
        }
        return new ResponseOperation(true, context.getString(R.string.validacion_correcto));
    }

    public ResponseOperation validaLongitud(String longitud, Context context) {
        if (longitud.isEmpty()) {// validacion de campo vacio
            return new ResponseOperation(false, context.getString(R.string.validacion_vacio));
        }
        return new ResponseOperation(true, context.getString(R.string.validacion_correcto));
    }
}
