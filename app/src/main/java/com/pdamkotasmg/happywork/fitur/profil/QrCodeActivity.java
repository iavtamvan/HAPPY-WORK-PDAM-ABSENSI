package com.pdamkotasmg.happywork.fitur.profil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.utils.Config;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrCodeActivity extends AppCompatActivity {

    private String npp;
    private String nama;
    private String jabatan;

    private static final String TAG = "debug";
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private ImageView ivQrCode;
    private LinearLayout divHeaderName;
    private TextView tvName;
    private TextView tvJabatan;
    private TextView tvNpp;
    private LottieAnimationView animationView;
    private ImageView ivScan;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        getSupportActionBar().hide();
        initView();
        ivHeaderInfo.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        nama = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        jabatan = sharedPreferences.getString(Config.SHARED_JABATAN, "");
        tvHeaderJudul.setText("QR Code " + npp);
        tvName.setText(nama);
        tvNpp.setText(npp);
// Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(npp, null, QRGContents.Type.TEXT, 999);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);
        // Getting QR-Code as Bitmap
        Bitmap bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        ivQrCode.setImageBitmap(bitmap);

        ivScan.setOnClickListener(v -> {
            startActivity(new Intent(QrCodeActivity.this, ScanQRCodeActivity.class));
        });
        ivHeaderBackArrow.setOnClickListener(v -> {
            finishAffinity();
            startActivity(new Intent(QrCodeActivity.this, ProfileActivity.class));
        });

    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        ivQrCode = findViewById(R.id.iv_qr_code);
        divHeaderName = findViewById(R.id.div_header_name);
        tvName = findViewById(R.id.tv_name);
        tvNpp = findViewById(R.id.tv_npp);
        animationView = findViewById(R.id.animation_view);
        ivScan = findViewById(R.id.iv_scan);
    }
}