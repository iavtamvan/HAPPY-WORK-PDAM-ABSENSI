package com.pdamkotasmg.happywork.fitur.profil;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.pdamkotasmg.happywork.R;

public class ScanQRCodeActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;

    private CodeScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r_code);
        initView();
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> Toast.makeText(ScanQRCodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show()));
        scannerView.post(() -> {
            scannerView.performClick();
        });
        scannerView.setOnClickListener(v -> {
            mCodeScanner.startPreview();
        });
    }

    private void initView() {
        scannerView = findViewById(R.id.scanner_view);
    }
}