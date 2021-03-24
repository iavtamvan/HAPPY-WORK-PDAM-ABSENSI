package com.pdamkotasmg.happywork.fitur.perangkat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.perangkat.controller.PerangkatController;

public class PerangkatActivity extends AppCompatActivity {

    private PerangkatController perangkatController;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvVersionAplication;
    private TextView tvNameNamaHp;
    private TextView tvIpCity;
    private TextView tvStatusOnline;
    private RecyclerView rvPerangkatTersambung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perangkat);
        getSupportActionBar().hide();
        initView();
        perangkatController = new PerangkatController();
        perangkatController.perangkatOnline(PerangkatActivity.this, tvVersionAplication, tvNameNamaHp, tvIpCity);
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvVersionAplication = findViewById(R.id.tv_version_aplication);
        tvNameNamaHp = findViewById(R.id.tv_name_nama_hp);
        tvIpCity = findViewById(R.id.tv_ip_city);
        tvStatusOnline = findViewById(R.id.tv_status_online);
        rvPerangkatTersambung = findViewById(R.id.rv_perangkat_tersambung);
    }
}