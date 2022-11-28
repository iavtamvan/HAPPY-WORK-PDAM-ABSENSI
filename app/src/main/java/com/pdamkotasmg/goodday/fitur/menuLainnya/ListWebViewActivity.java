package com.pdamkotasmg.goodday.fitur.menuLainnya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdView;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.utils.Config;

public class ListWebViewActivity extends AppCompatActivity {

    private String typeWebView;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private CardView divGisTirta;
    private CardView divSambungBaru;
    private CardView divWablast;
    private CardView divSurveyPelanggan;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_web_view);
        getSupportActionBar().hide();
        initView();

        Config.ads(ListWebViewActivity.this, adView);

        tvHeaderJudul.setText("Menu Lainnya");
        ivHeaderBackArrow.setOnClickListener(view -> {
            ListWebViewActivity.this.finish();
        });
        ivHeaderInfo.setVisibility(View.GONE);

        divGisTirta.setOnClickListener(view -> {
            typeWebView = "gistirta";
            saveShared(typeWebView);
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        });

        divSambungBaru.setOnClickListener(view -> {
            typeWebView = "sambungbaru";
            saveShared(typeWebView);
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        });

        divWablast.setOnClickListener(view -> {
            typeWebView = "wablast";
            saveShared(typeWebView);
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        });

        divSurveyPelanggan.setOnClickListener(view -> {
            typeWebView = "surveyplg";
            saveShared(typeWebView);
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        });

    }

    private void saveShared(String typeWebView) {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SHARED_TYPE_WEB_vIEW, typeWebView);
        editor.apply();
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divGisTirta = findViewById(R.id.div_gis_tirta);
        divSambungBaru = findViewById(R.id.div_sambung_baru);
        divWablast = findViewById(R.id.div_wablast);
        divSurveyPelanggan = findViewById(R.id.div_survey_pelanggan);
        adView = findViewById(R.id.adView);
    }
}