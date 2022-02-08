package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;

public class DetailKoreksiKehadiranActivity extends AppCompatActivity {

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvDetailRequestDari;
    private TextView tvDetailRequestUntuk;
    private TextView tvDetailMulaiTanggal;
    private TextView tvDetailSelesaiTanggal;
    private RecyclerView rvDetailKoreksiKehadiran;
    private RecyclerView rvDetailMengetahui;
    private Button btnNewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_koreksi_kehadiran);
        getSupportActionBar().hide();
        initView();


    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvDetailRequestDari = findViewById(R.id.tv_detail_request_dari);
        tvDetailRequestUntuk = findViewById(R.id.tv_detail_request_untuk);
        tvDetailMulaiTanggal = findViewById(R.id.tv_detail_mulai_tanggal);
        tvDetailSelesaiTanggal = findViewById(R.id.tv_detail_selesai_tanggal);
        rvDetailKoreksiKehadiran = findViewById(R.id.rv_detail_koreksi_kehadiran);
        rvDetailMengetahui = findViewById(R.id.rv_detail_mengetahui);
        btnNewRequest = findViewById(R.id.btn_new_request);
    }
}