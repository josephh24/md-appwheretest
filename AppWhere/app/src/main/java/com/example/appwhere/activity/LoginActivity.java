package com.example.appwhere.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appwhere.R;
import com.example.appwhere.api_app_where.ApiAppWhere;
import com.example.appwhere.models.Login;
import com.example.appwhere.validaciones.ResponseOperation;
import com.example.appwhere.validaciones.Validaciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText txt_correo_login, txt_pass_login;
    private FloatingActionButton btn_login_entrar;
    private ProgressBar cargando;

    private static Retrofit retrofit = null;

    private Validaciones validarCampos = new Validaciones();
    private String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_correo_login = findViewById(R.id.txt_correo_login);
        txt_pass_login = findViewById(R.id.txt_pass_login);
        btn_login_entrar = findViewById(R.id.btn_login_entrar);
        cargando = findViewById(R.id.cargando);

        btn_login_entrar.setOnClickListener(this);

        retrofit = new Retrofit.Builder().baseUrl("http://166.62.33.53:4590/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_entrar:
                String correo, pass;
                correo = txt_correo_login.getText().toString().trim();
                pass = txt_pass_login.getText().toString().trim();
                if (isValidForm(correo, pass)) {
                    cargando.setVisibility(View.VISIBLE);
                    obtenerSUser(correo, pass);
                }

                break;

        }
    }

    private void obtenerSUser(String email, String pass) {
        ApiAppWhere service = retrofit.create(ApiAppWhere.class);
        Call<Login> loginCall = service.getLogin(email, pass);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                if (response.body() != null) {
                    Login login = response.body();

                    if (login.isSuccessful()) {
                        cargando.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        cargando.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), getString(R.string.login_incorrecto), Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, login.getUserId() + "");
                } else {
                    cargando.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "onResponseBody" + response.raw().request().url());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                cargando.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onFail: " + t.getMessage());
            }
        });
    }

    public boolean isValidForm(String email, String pass) {
        boolean isValid = true;
        ResponseOperation responseOperation;

        responseOperation = validarCampos.validaEmail(email, this);
        if (!responseOperation.getStatus()) {
            isValid = false;
            txt_correo_login.setError(responseOperation.getTexResponse());
        }

        responseOperation = validarCampos.validaPass(pass, this);
        if (!responseOperation.getStatus()) {
            isValid = false;
            txt_pass_login.setError(responseOperation.getTexResponse());
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
}
