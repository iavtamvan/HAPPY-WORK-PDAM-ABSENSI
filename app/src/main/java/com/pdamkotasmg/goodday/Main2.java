package com.pdamkotasmg.goodday;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Main2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
}