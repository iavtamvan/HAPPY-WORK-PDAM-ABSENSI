package com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdView;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.adapter.RiwayatPerjalananDinasAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.riwayatPerjalananDinas.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.riwayatPerjalananDinas.RiwayatPerjalananDinasRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPerjalananDinasActivity extends AppCompatActivity {

    private List<DataItem> dataItems;
    private RiwayatPerjalananDinasAdapter riwayatPerjalananDinasAdapter;

    private SharedPreferences sharedPreferences;
    private String accesToken;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private LinearLayout divAnimation;
    private LottieAnimationView animationView;
    private RecyclerView rv;
    private AdView adView;
    private Button btnNewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_perjalanan_dinas);
        initView();
        getSupportActionBar().hide();

        Config.ads(RiwayatPerjalananDinasActivity.this, adView);
        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accesToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        tvHeaderJudul.setText("Perjalanan DInas");
        ivHeaderInfo.setVisibility(View.GONE);
        ivHeaderBackArrow.setOnClickListener(v -> {
            RiwayatPerjalananDinasActivity.this.finish();
        });

        getRiwayatPerjalananDinas();

        btnNewRequest.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PerjalananDinasActivity.class));
        });
    }

    private void getRiwayatPerjalananDinas() {
        divAnimation.setVisibility(View.VISIBLE);
        ApiService apiService = ApiConfig.getApiService(this);
        apiService.getRequestHistoryPerjalananDinas(accesToken, "ROD", "1")
                .enqueue(new Callback<RiwayatPerjalananDinasRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatPerjalananDinasRootModel> call, Response<RiwayatPerjalananDinasRootModel> response) {
                        divAnimation.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            dataItems = new ArrayList<>();
                            dataItems = response.body().getData().getData();
                            if (dataItems.isEmpty()) {
                                Toast.makeText(RiwayatPerjalananDinasActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            } else {
                                riwayatPerjalananDinasAdapter = new RiwayatPerjalananDinasAdapter(RiwayatPerjalananDinasActivity.this, dataItems);
                                rv.setLayoutManager(new LinearLayoutManager(RiwayatPerjalananDinasActivity.this));
                                rv.setAdapter(riwayatPerjalananDinasAdapter);
                                riwayatPerjalananDinasAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(RiwayatPerjalananDinasActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatPerjalananDinasRootModel> call, Throwable t) {
                        divAnimation.setVisibility(View.GONE);
                        Toast.makeText(RiwayatPerjalananDinasActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRiwayatPerjalananDinas();
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divAnimation = findViewById(R.id.div_animation);
        animationView = findViewById(R.id.animation_view);
        rv = findViewById(R.id.rv);
        adView = findViewById(R.id.adView);
        btnNewRequest = findViewById(R.id.btn_new_request);
    }

}