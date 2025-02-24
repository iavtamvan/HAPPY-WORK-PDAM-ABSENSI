package com.pdamkotasmg.goodday.fitur.profil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.presensi.CheckLocationActivity;
import com.pdamkotasmg.goodday.utils.Config;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class ScanQRCodeActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;

    private CodeScannerView scannerView;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r_code);
        getSupportActionBar().hide();
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ivHeaderBackArrow.setOnClickListener(v -> {
            ScanQRCodeActivity.this.finish();
        });
        tvHeaderJudul.setText("Scan QR Code");
        ivHeaderInfo.setVisibility(View.GONE);

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            Toast.makeText(ScanQRCodeActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
            MaterialDialog mDialog = new MaterialDialog.Builder(ScanQRCodeActivity.this)
                    .setTitle("Yakin dengan npp " + result.getText() + " ?")
                    .setCancelable(false)
                    .setNegativeButton("Tidak, Ulangi!", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                        mCodeScanner.startPreview();
                    })
                    .setPositiveButton("Ya", (dialogInterface, which) -> {
                        editor.putString(Config.SHARED_NPP_QR_CODE, result.getText());
                        editor.apply();
                        finishAffinity();
                        startActivity(new Intent(ScanQRCodeActivity.this, CheckLocationActivity.class));
                    })
                    .build();

            // Show Dialog
            mDialog.show();
        }));
        scannerView.post(() -> {
            scannerView.performClick();
        });
        scannerView.setOnClickListener(v -> {
            mCodeScanner.startPreview();
        });
    }

    private void initView() {
        scannerView = findViewById(R.id.scanner_view);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
    }
}