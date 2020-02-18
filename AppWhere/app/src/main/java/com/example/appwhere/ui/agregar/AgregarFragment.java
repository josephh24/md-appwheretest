package com.example.appwhere.ui.agregar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appwhere.R;
import com.example.appwhere.api_app_where.ApiAppWhere;
import com.example.appwhere.models.Merchants;
import com.example.appwhere.validaciones.ResponseOperation;
import com.example.appwhere.validaciones.Validaciones;
import com.google.android.material.textfield.TextInputEditText;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregarFragment extends Fragment implements View.OnClickListener{


    private Button btn_agregar;
    private TextInputEditText txt_agregar_nombre,txt_agregar_direccion, txt_agregar_telefono, txt_agregar_latitud, txt_agregar_longitud;
    private ProgressBar cargando_agregar;

    private static Retrofit retrofit = null;
    private Validaciones validarCampos = new Validaciones();
    private String TAG = "Agregar";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_agregar, container, false);

        btn_agregar = root.findViewById(R.id.btn_agregar);
        txt_agregar_nombre = root.findViewById(R.id.txt_agregar_nombre);
        txt_agregar_direccion = root.findViewById(R.id.txt_agregar_direccion);
        txt_agregar_telefono = root.findViewById(R.id.txt_agregar_telefono);
        txt_agregar_latitud = root.findViewById(R.id.txt_agregar_latitud);
        txt_agregar_longitud = root.findViewById(R.id.txt_agregar_longitud);
        cargando_agregar = root.findViewById(R.id.cargando_agregar);

        btn_agregar.setOnClickListener(this);

        retrofit = new Retrofit.Builder().baseUrl("http://166.62.33.53:4590/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        return root;
    }

    private void setSucursales(String nombre, String direccion, String telefono, Double latitud, Double longitud){
        Merchants merchants = new Merchants(nombre, direccion, telefono, latitud, longitud);
        ApiAppWhere service = retrofit.create(ApiAppWhere.class);
        Call<Merchants> sucursalesCall = service.setSucursales(merchants);
        sucursalesCall.enqueue(new Callback<Merchants>() {
            @Override
            public void onResponse(Call<Merchants> call, Response<Merchants> response) {
                if (response.isSuccessful()){
                    cargando_agregar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getString(R.string.agregar_correcto), Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    cargando_agregar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), getString(R.string.agregar_incorrecto), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "code: " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Merchants> call, Throwable t) {
                cargando_agregar.setVisibility(View.GONE);
                Log.d(TAG, "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_agregar:
                String nombre, direccion, telefono, latitud, longitud;

                nombre = txt_agregar_nombre.getText().toString();
                direccion = txt_agregar_direccion.getText().toString();
                telefono = txt_agregar_telefono.getText().toString();
                latitud = txt_agregar_latitud.getText().toString();
                longitud = txt_agregar_longitud.getText().toString();

                if (isValidForm(nombre, direccion, telefono, latitud, longitud)){
                    cargando_agregar.setVisibility(View.VISIBLE);
                    setSucursales(nombre, direccion, telefono, Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                break;
        }
    }

    public boolean isValidForm(String nombre, String direccion, String telefono, String latitud, String longitud) {
        boolean isValid = true;
        ResponseOperation responseOperation;

        responseOperation = validarCampos.validaNombre(nombre, getContext());
        if (!responseOperation.getStatus()) {
            isValid = false;
            txt_agregar_nombre.setError(responseOperation.getTexResponse());
        }

        responseOperation = validarCampos.validaDireccion(direccion, getContext());
        if (!responseOperation.getStatus()) {
            isValid = false;
            txt_agregar_direccion.setError(responseOperation.getTexResponse());
        }

        responseOperation = validarCampos.validaTelefono(telefono, getContext());
        if (!responseOperation.getStatus()) {
            isValid = false;
            txt_agregar_telefono.setError(responseOperation.getTexResponse());
        }

        responseOperation = validarCampos.validaLatitud(latitud, getContext());
        if (!responseOperation.getStatus()) {
            isValid = false;
            txt_agregar_latitud.setError(responseOperation.getTexResponse());
        }

        responseOperation = validarCampos.validaLongitud(longitud, getContext());
        if (!responseOperation.getStatus()) {
            isValid = false;
            txt_agregar_longitud.setError(responseOperation.getTexResponse());
        }

        return isValid;
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void limpiar(){
        txt_agregar_nombre.setText("");
        txt_agregar_direccion.setText("");
        txt_agregar_telefono.setText("");
        txt_agregar_latitud.setText("");
        txt_agregar_longitud.setText("");
    }
}