package com.pdamkotasmg.happywork.fitur.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.authentication.login.LoginActivity;

public class WelcomeAuthActivity extends AppCompatActivity {

    private Button btnPageLogin;
    private TextView tvPageRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_auth);
        getSupportActionBar().hide();
        initView();
        tvPageRegister.setVisibility(View.GONE);
        btnPageLogin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
    }

    private void initView() {
        btnPageLogin = findViewById(R.id.btn_page_login);
        tvPageRegister = findViewById(R.id.tv_page_register);
    }
}