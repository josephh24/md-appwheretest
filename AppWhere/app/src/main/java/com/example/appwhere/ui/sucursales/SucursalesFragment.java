package com.example.appwhere.ui.sucursales;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appwhere.adaptador.AdaptadorSucursal;
import com.example.appwhere.R;
import com.example.appwhere.api_app_where.ApiAppWhere;
import com.example.appwhere.models.Merchants;
import com.example.appwhere.models.POJO_sucursales;
import com.example.appwhere.models.Sucursales;

import java.security.cert.CertificateException;
import java.util.ArrayList;

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

public class SucursalesFragment extends Fragment {

    private RecyclerView rv_sucursales;
    private LinearLayoutManager layoutManager;
    private AdaptadorSucursal adaptadorSucursal;
    private ProgressBar cargando_sucursal;

    private ArrayList<POJO_sucursales> lista_sucursal;

    private static Retrofit retrofit = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sucursales, container, false);

        rv_sucursales = root.findViewById(R.id.rv_sucursales);
        cargando_sucursal = root.findViewById(R.id.cargando_sucursal);

        lista_sucursal = new ArrayList<>();

        retrofit = new Retrofit.Builder().baseUrl("http://166.62.33.53:4590/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        cargando_sucursal.setVisibility(View.VISIBLE);
        obtenerSucursales();

        return root;
    }

    private void obtenerSucursales() {
        ApiAppWhere service = retrofit.create(ApiAppWhere.class);
        Call<Sucursales> sucursalesCall = service.getSucursales();

        sucursalesCall.enqueue(new Callback<Sucursales>() {
            @Override
            public void onResponse(Call<Sucursales> call, Response<Sucursales> response) {
                if (response.body() != null) {
                    Sucursales sucursales = response.body();
                    Log.d("jijijiji", sucursales.getMerchants() + "");

                    for (Merchants merchants : sucursales.getMerchants()) {
                        Log.d("jijijiji", merchants.getMerchantName() + "");
                        lista_sucursal.add(new POJO_sucursales(merchants.getId(), merchants.getMerchantName(), merchants.getMerchantAddress(),
                                merchants.getMerchantTelephone(), merchants.getLatitude(), merchants.getLongitude(), merchants.getRegistrationDate()));

                    }
                    cargando_sucursal.setVisibility(View.INVISIBLE);
                    lista();

                } else {
                    cargando_sucursal.setVisibility(View.INVISIBLE);
                    Log.d("jijijiji", "onResponseBody");
                }
            }

            @Override
            public void onFailure(Call<Sucursales> call, Throwable t) {
                cargando_sucursal.setVisibility(View.INVISIBLE);
                Log.d("jijijiji", "onFail: " + t.getMessage());
            }
        });
    }

    private void lista() {
        //recycler
        rv_sucursales.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_sucursales.setLayoutManager(layoutManager);

        adaptadorSucursal = new AdaptadorSucursal(lista_sucursal, getContext());

        rv_sucursales.setAdapter(adaptadorSucursal);
        rv_sucursales.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));

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