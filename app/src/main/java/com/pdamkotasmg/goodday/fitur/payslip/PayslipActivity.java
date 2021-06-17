package com.pdamkotasmg.goodday.fitur.payslip;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.goodday.R;

public class PayslipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payslip);
        getSupportActionBar().hide();
    }
}