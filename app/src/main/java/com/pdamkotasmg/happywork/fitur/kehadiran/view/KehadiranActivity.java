package com.pdamkotasmg.happywork.fitur.kehadiran.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.kehadiran.controller.KehadiranController;

import de.hdodenhof.circleimageview.CircleImageView;

public class KehadiranActivity extends AppCompatActivity {

    private KehadiranController kehadiranController;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private CircleImageView ciListKehadiranProfileImage;
    private TextView tvListKehadiranNama;
    private TextView tvDate;
    private TextView tvListKehadiranShift;
    private RecyclerView rvKehadiran;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);
        getSupportActionBar().hide();
        initView();
        tvHeaderJudul.setText("Rekam Kehadiran");

        kehadiranController = new KehadiranController();
        kehadiranController.getKehadiran(getApplicationContext(), rvKehadiran);

    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        ciListKehadiranProfileImage = findViewById(R.id.ci_list_kehadiran_profile_image);
        tvListKehadiranNama = findViewById(R.id.tv_list_kehadiran_nama);
        tvDate = findViewById(R.id.tv_date);
        tvListKehadiranShift = findViewById(R.id.tv_list_kehadiran_shift);
        rvKehadiran = findViewById(R.id.rv_kehadiran);
    }
}