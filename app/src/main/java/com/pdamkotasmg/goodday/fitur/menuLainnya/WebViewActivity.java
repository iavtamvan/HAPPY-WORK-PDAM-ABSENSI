package com.pdamkotasmg.goodday.fitur.menuLainnya;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.utils.Config;

public class WebViewActivity extends AppCompatActivity {

    private String typeWebView;
    private String token;

    private WebView wv;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getSupportActionBar().hide();
        initView();

        ivHeaderBackArrow.setOnClickListener(view -> {
            WebViewActivity.this.finish();
        });
        ivHeaderInfo.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        token = token.replace("Bearer ", "");
        Log.d("debug", "onCreate: " + token);
        typeWebView = sharedPreferences.getString(Config.SHARED_TYPE_WEB_vIEW, "");

        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat melakukan zoom.
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.getSettings().setCacheMode(WebSettings.LOAD_NORMAL);

        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.setWebViewClient(new WebViewClient());

        if (typeWebView.contains("gistirta")) {
            tvHeaderJudul.setText("Gis Tirta Moedal");
            wv.loadUrl("https://gateway.pdamkotasmg.co.id/gistirtamoedal/#/oauth?token=" + token);
            Toast.makeText(this, "gistirtamoedal", Toast.LENGTH_SHORT).show();
        } else if (typeWebView.contains("sambungbaru")) {
            tvHeaderJudul.setText("Sambung Baru");
            wv.loadUrl("https://gateway.pdamkotasmg.co.id/sambung-baru/");
            Toast.makeText(this, "sambung-baru", Toast.LENGTH_SHORT).show();
        } else if (typeWebView.contains("wablast")) {
            tvHeaderJudul.setText("Wa Blast");
            wv.loadUrl("https://gateway.pdamkotasmg.co.id/wablast/#/oauth?token=" + token);
            Toast.makeText(this, "wablast", Toast.LENGTH_SHORT).show();
        } else if (typeWebView.contains("surveyplg")) {
            tvHeaderJudul.setText("Survey Pelanggan");
            wv.loadUrl("https://gateway.pdamkotasmg.co.id/surveyplg/#/oauth?token=" + token);
            Toast.makeText(this, "surveyplg", Toast.LENGTH_SHORT).show();
        } else if (typeWebView.contains("moonfish")) {
            tvHeaderJudul.setText("Moon Fish");
            wv.loadUrl("https://gateway.pdamkotasmg.co.id/moonfish/#/oauth?token=" + token);
            Toast.makeText(this, "moonfish", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        wv = findViewById(R.id.wv);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
    }
}