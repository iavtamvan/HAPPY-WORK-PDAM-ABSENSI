package com.pdamkotasmg.happywork.fitur.profil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.utils.Config;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrCodeActivity extends AppCompatActivity {

    private String npp;

    private static final String TAG = "debug";
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private ImageView ivQrCode;
    private Button btnScanQrcode;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        getSupportActionBar().hide();
        initView();


        npp = getIntent().getStringExtra(Config.BUNDLE_NPP);
        tvHeaderJudul.setText("QR Code " + npp);
// Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(npp, null, QRGContents.Type.TEXT, 222);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);
        // Getting QR-Code as Bitmap
        Bitmap bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        ivQrCode.setImageBitmap(bitmap);

        btnScanQrcode.setOnClickListener(v -> {
            startActivity(new Intent(QrCodeActivity.this, ScanQRCodeActivity.class));
        });

    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        ivQrCode = findViewById(R.id.iv_qr_code);
        btnScanQrcode = findViewById(R.id.btn_scan_qrcode);
    }
}