package com.example.appwhere.ui.mapa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appwhere.R;
import com.example.appwhere.api_app_where.ApiAppWhere;
import com.example.appwhere.models.Merchants;
import com.example.appwhere.models.POJO_sucursales;
import com.example.appwhere.models.Sucursales;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<POJO_sucursales> lista_sucursal;

    private static Retrofit retrofit = null;
    private String TAG = "Mapa";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mapa, container, false);

        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        lista_sucursal = new ArrayList<>();

        retrofit = new Retrofit.Builder().baseUrl("http://166.62.33.53:4590/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        obtenerSucursales();
    }

    private void obtenerSucursales() {
        ApiAppWhere service = retrofit.create(ApiAppWhere.class);
        Call<Sucursales> sucursalesCall = service.getSucursales();

        sucursalesCall.enqueue(new Callback<Sucursales>() {
            @Override
            public void onResponse(Call<Sucursales> call, Response<Sucursales> response) {
                if (response.body() != null) {
                    Sucursales sucursales = response.body();
                    Log.d(TAG, sucursales.getMerchants() + "");

                    for (Merchants merchants : sucursales.getMerchants()) {
                        Log.d(TAG, merchants.getMerchantName() + "");
                        lista_sucursal.add(new POJO_sucursales(merchants.getId(), merchants.getMerchantName(), merchants.getMerchantAddress(),
                                merchants.getMerchantTelephone(), merchants.getLatitude(), merchants.getLongitude(), merchants.getRegistrationDate()));

                    }

                    for (int i = 0; i < lista_sucursal.size(); i++) {
                        LatLng lugar = new LatLng(lista_sucursal.get(i).getLatitude(), lista_sucursal.get(i).getLongitude());
                        mMap.addMarker(new MarkerOptions()
                                .position(lugar)
                                .title(lista_sucursal.get(i).getMerchantName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_localization)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(lugar));
                    }

                } else {
                    Log.d(TAG, "onResponseBody");
                }
            }

            @Override
            public void onFailure(Call<Sucursales> call, Throwable t) {
                Log.d(TAG, "onFail: " + t.getMessage());
            }
        });
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