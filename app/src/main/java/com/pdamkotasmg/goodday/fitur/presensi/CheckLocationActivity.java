package com.pdamkotasmg.goodday.fitur.presensi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.view.KehadiranActivity;
import com.pdamkotasmg.goodday.fitur.presensi.model.checkLocationModel.CheckLocationRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckLocationActivity extends AppCompatActivity {
    private static final String TAG = "debug";

    private Double lati, longi;

    private FusedLocationProviderClient mFusedLocation;

    private String access_token;
    private String token_type;

    private String statusPresensi;
    private String npp;

    private ProgressDialog loading;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private WebView wv;
    private TextView tvResp;
    private TextView tvDistance;
    private LinearLayout divRefresh;
    private LinearLayout divLanjut;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_location);
        initView();
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        divLanjut.setVisibility(View.GONE);
        access_token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        statusPresensi = sharedPreferences.getString(Config.SHARED_STATUS_ABSENSI, "");

        if (statusPresensi.equalsIgnoreCase("qrcode")) {
            npp = sharedPreferences.getString(Config.SHARED_NPP_QR_CODE, "");
        } else {
            npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        }

        mFusedLocation = LocationServices.getFusedLocationProviderClient(CheckLocationActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                // Do it all with location
                Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                // Display in Toast
                lati = location.getLatitude();
                longi = location.getLongitude();
                Log.d(TAG, "lat: " + lati);
                Log.d(TAG, "long: " + longi);

                editor.putString(Config.SHARED_LATI_OFFLINE, String.valueOf(lati));
                editor.putString(Config.SHARED_LONGITUDE_OFFLINE, String.valueOf(longi));
                editor.apply();
                checkLocation();
            }
        });

        divRefresh.setOnClickListener(v -> {
            checkLocation();
        });

    }

    private void checkLocation() {
        Config.getPackageNameFromServer(CheckLocationActivity.this);
        loading = new ProgressDialog(CheckLocationActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Mohon Tunggu...");
        loading.show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(CheckLocationActivity.this, location -> {
            if (location != null) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                Log.d(TAG, "latCheck: " + lati);
                Log.d(TAG, "longCheck: " + longi);
                editor.putString(Config.SHARED_LATI_OFFLINE, String.valueOf(lati));
                editor.putString(Config.SHARED_LONGITUDE_OFFLINE, String.valueOf(longi));
                editor.apply();

                ApiService apiService = ApiConfig.getApiService();
                apiService.checkLocation(access_token, statusPresensi, npp, lati, longi)
                        .enqueue(new Callback<CheckLocationRootModel>() {
                            @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
                            @Override
                            public void onResponse(Call<CheckLocationRootModel> call, Response<CheckLocationRootModel> response) {
                                if (response.isSuccessful()) {
                                    // deteksi lokasi absen false
                                    if (!response.body().getData().getAppliesShiftSetting().isLocationDetection()) {
                                        startActivity(new Intent(CheckLocationActivity.this, PresensiActivity.class));
                                    } else if (response.body().getData().getAppliesShiftSetting().getShiftDailyCode().equalsIgnoreCase("OFF")) {
                                        Toast.makeText(CheckLocationActivity.this, "Shift Off", Toast.LENGTH_SHORT).show();
                                        finishAffinity();
                                        startActivity(new Intent(CheckLocationActivity.this, KehadiranActivity.class));
                                    } else {
                                        wv.getSettings().setJavaScriptEnabled(true);
                                        wv.getSettings().setLoadWithOverviewMode(true);
                                        wv.getSettings().setUseWideViewPort(true);
                                        wv.getSettings().setBuiltInZoomControls(true);
                                        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
//                                wv.getSettings().setAppCacheEnabled(true);
//                                wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                                        assert response.body() != null;
                                        wv.loadUrl(response.body().getData().getMapUrl());
                                        tvDistance.setText("Akurasi " + response.body().getData().getCheckResult().getDistanceM() + " Meter \n" + response.body().getData().getAppliesLocationSetting().getCode() +
                                                " - " + response.body().getData().getAppliesLocationSetting().getName());
                                        if (!response.body().getData().getCheckResult().isIsInRadius()) {
                                            loading.cancel();
                                            Toast.makeText(CheckLocationActivity.this, "Anda tidak dalam radius Absensi", Toast.LENGTH_SHORT).show();
                                            divLanjut.setVisibility(View.GONE);
                                        } else {
                                            loading.cancel();
                                            divLanjut.setVisibility(View.VISIBLE);
                                            divLanjut.setOnClickListener(v -> {
                                                if (statusPresensi.equalsIgnoreCase("qrcode")) {
                                                    npp = sharedPreferences.getString(Config.SHARED_NPP_QR_CODE, "");
                                                    Intent intent = new Intent(CheckLocationActivity.this, PresensiActivity.class);
//                                                intent.putExtra(Config.BUNDLE_NPP, response.body().getData().getNpp());
                                                    intent.putExtra(Config.BUNDLE_NAME, response.body().getData().getName());
                                                    intent.putExtra(Config.BUNDLE_JABATAN, response.body().getData().getJabatan());
                                                    startActivity(intent);
                                                } else {
                                                    npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
                                                    startActivity(new Intent(CheckLocationActivity.this, PresensiActivity.class));
                                                }
                                            });
                                        }
                                        loading.cancel();
                                    }


                                } else {
                                    loading.cancel();
                                    Toast.makeText(CheckLocationActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CheckLocationRootModel> call, Throwable t) {
                                loading.cancel();
                                Toast.makeText(CheckLocationActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void initView() {
        wv = findViewById(R.id.wv);
        tvResp = findViewById(R.id.tv_resp);
        tvDistance = findViewById(R.id.tv_distance);
        divRefresh = findViewById(R.id.div_refresh);
        divLanjut = findViewById(R.id.div_lanjut);
    }
}