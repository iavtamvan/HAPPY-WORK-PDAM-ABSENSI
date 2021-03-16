package com.pdamkotasmg.happywork.fitur.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.dashboard.DashboardActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();
        btnMasuk.setOnClickListener(v -> {
            finishAffinity();
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        });
    }

    private void initView() {
        btnMasuk = findViewById(R.id.btn_masuk);
    }
}