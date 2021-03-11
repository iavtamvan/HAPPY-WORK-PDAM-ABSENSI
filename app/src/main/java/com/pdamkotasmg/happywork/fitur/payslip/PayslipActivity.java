package com.pdamkotasmg.happywork.fitur.payslip;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.happywork.R;

public class PayslipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payslip);
        getSupportActionBar().hide();
    }
}