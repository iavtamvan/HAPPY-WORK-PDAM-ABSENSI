package com.pdamkotasmg.goodday.fitur.kehadiran.lembur.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.goodday.R;

public class DetailLemburActivity extends AppCompatActivity {
    private final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lembur);
        getSupportActionBar().hide();


    }


}