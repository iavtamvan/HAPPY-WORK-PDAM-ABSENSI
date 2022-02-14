package com.pdamkotasmg.goodday.fitur.kehadiran.cuti.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdView;
import com.pdamkotasmg.goodday.R;

public class RiwayatCutiActivity extends AppCompatActivity {

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private LinearLayout divAnimation;
    private LottieAnimationView animationView;
    private RecyclerView rvCuti;
    private AdView adView;
    private Button btnNewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_cuti);
        initView();



    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divAnimation = findViewById(R.id.div_animation);
        animationView = findViewById(R.id.animation_view);
        rvCuti = findViewById(R.id.rv_cuti);
        adView = findViewById(R.id.adView);
        btnNewRequest = findViewById(R.id.btn_new_request);
    }
}