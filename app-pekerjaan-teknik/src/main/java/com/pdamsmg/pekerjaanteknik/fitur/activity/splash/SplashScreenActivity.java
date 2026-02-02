package com.pdamsmg.pekerjaanteknik.fitur.activity.splash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.pdamsmg.pekerjaanteknik.R;
import com.pdamsmg.pekerjaanteknik.utils.ConfigAds;

public class SplashScreenActivity extends AppCompatActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        initView();
        ConfigAds.bannerIntentSharedPref(SplashScreenActivity.this, adView);

    }

    private void initView() {
        adView = findViewById(R.id.adView);
    }
}