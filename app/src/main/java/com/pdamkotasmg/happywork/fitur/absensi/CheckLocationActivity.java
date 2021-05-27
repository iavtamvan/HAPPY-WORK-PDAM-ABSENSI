package com.pdamkotasmg.happywork.fitur.absensi;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
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

    private WebView wv;
    private TextView tvResp;

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
        lati = location.getLatitude();
        longi = location.getLongitude();

        access_token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        editor.putString(Config.SHARED_LATI_OFFLINE, String.valueOf(lati));
        editor.putString(Config.SHARED_LONGITUDE_OFFLINE, String.valueOf(longi));
        editor.apply();

        ApiService apiService = ApiConfig.getApiService();
        apiService.checkLocation(access_token, lati, longi)
                .enqueue(new Callback<CheckLocationRootModel>() {
                    @SuppressLint("SetJavaScriptEnabled")
                    @Override
                    public void onResponse(Call<CheckLocationRootModel> call, Response<CheckLocationRootModel> response) {
                        if (response.isSuccessful()){
                            wv.getSettings().setJavaScriptEnabled(true);
                            wv.getSettings().setLoadWithOverviewMode(true);
                            wv.getSettings().setUseWideViewPort(true);
                            wv.getSettings().setBuiltInZoomControls(true);
                            wv.getSettings().setPluginState(WebSettings.PluginState.ON);
                            wv.getSettings().setAppCacheEnabled(false);
                            wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//                            wv.getSettings().setPluginsEnabled(true);
//                            wv.setWebViewClient(new HelloWebViewClient());
                            assert response.body() != null;
                            wv.loadUrl(response.body().getData().getMapUrl());

                            tvResp.setText(response.body().getData().getMapUrl());
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckLocationRootModel> call, Throwable t) {
                        Toast.makeText(CheckLocationActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initView() {
        wv = findViewById(R.id.wv);
        tvResp = findViewById(R.id.tv_resp);
    }
}