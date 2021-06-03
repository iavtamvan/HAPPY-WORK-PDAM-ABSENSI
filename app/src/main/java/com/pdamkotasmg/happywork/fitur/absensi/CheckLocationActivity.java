package com.pdamkotasmg.happywork.fitur.absensi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.api.server.ApiConfig;
import com.pdamkotasmg.happywork.api.server.ApiService;
import com.pdamkotasmg.happywork.fitur.absensi.model.checkLocationModel.CheckLocationRootModel;
import com.pdamkotasmg.happywork.utils.Config;

import im.delight.android.location.SimpleLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckLocationActivity extends AppCompatActivity {

    private Double lati, longi;
    private SimpleLocation location;

    private String access_token;
    private String token_type;

    private String statusAbsensi;
    private String npp;

    private ProgressDialog loading;

    private WebView wv;
    private TextView tvResp;
    private TextView tvDistance;
    private LinearLayout divRefresh;
    private LinearLayout divLanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_location);
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        location = new SimpleLocation(CheckLocationActivity.this);
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(CheckLocationActivity.this);
        }
        divLanjut.setVisibility(View.GONE);
        access_token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        statusAbsensi = sharedPreferences.getString(Config.SHARED_STATUS_ABSENSI, "");

        if (statusAbsensi.equalsIgnoreCase("qrcode")) {
            npp = sharedPreferences.getString(Config.SHARED_NPP_QR_CODE, "");
        } else {
            npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        }

        editor.putString(Config.SHARED_LATI_OFFLINE, String.valueOf(lati));
        editor.putString(Config.SHARED_LONGITUDE_OFFLINE, String.valueOf(longi));
        editor.apply();
        checkLocation();

        divRefresh.setOnClickListener(v -> {
            checkLocation();
        });

    }

    private void checkLocation() {
        loading = new ProgressDialog(CheckLocationActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Mohon Tunggu...");
        loading.show();
        lati = location.getLatitude();
        longi = location.getLongitude();
        ApiService apiService = ApiConfig.getApiService();
        apiService.checkLocation(access_token, statusAbsensi, npp, lati, longi)
                .enqueue(new Callback<CheckLocationRootModel>() {
                    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
                    @Override
                    public void onResponse(Call<CheckLocationRootModel> call, Response<CheckLocationRootModel> response) {
                        if (response.isSuccessful()) {
                            // deteksi lokasi absen false
                            if (!response.body().getData().getAppliesShiftSetting().isLocationDetection()){
                                startActivity(new Intent(CheckLocationActivity.this, AbsensiV2Activity.class));
                            } else {
                                wv.getSettings().setJavaScriptEnabled(true);
                                wv.getSettings().setLoadWithOverviewMode(true);
                                wv.getSettings().setUseWideViewPort(true);
                                wv.getSettings().setBuiltInZoomControls(true);
                                wv.getSettings().setPluginState(WebSettings.PluginState.ON);
                                wv.getSettings().setAppCacheEnabled(true);
                                wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
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
                                        startActivity(new Intent(CheckLocationActivity.this, AbsensiV2Activity.class));
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

    private void initView() {
        wv = findViewById(R.id.wv);
        tvResp = findViewById(R.id.tv_resp);
        tvDistance = findViewById(R.id.tv_distance);
        divRefresh = findViewById(R.id.div_refresh);
        divLanjut = findViewById(R.id.div_lanjut);
    }
}