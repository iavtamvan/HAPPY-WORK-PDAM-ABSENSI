package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.KoreksiKehadiranAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.riwayatKoreksiKehadiran.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.riwayatKoreksiKehadiran.RiwayatKoreksiKehadiranRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatKoreksiKehadiranActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private List<DataItem> dataItems;
    private KoreksiKehadiranAdapter koreksiKehadiranAdapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String accesToken;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private LinearLayout divAnimation;
    private LottieAnimationView animationView;
    private RecyclerView rvKoreksiKehadiran;
    private AdView adView;
    private Button btnNewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_koreksi_kehadiran);
        initView();
        getSupportActionBar().hide();

        Config.ads(RiwayatKoreksiKehadiranActivity.this, adView);
        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accesToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        Log.d(TAG, "token: " + accesToken);

        tvHeaderJudul.setText("Koreksi Kehadiran");
        ivHeaderInfo.setVisibility(View.GONE);
        ivHeaderBackArrow.setOnClickListener(v -> {
            RiwayatKoreksiKehadiranActivity.this.finish();
        });

        btnNewRequest.setOnClickListener(v -> {
            // Todo new Request
            startActivity(new Intent(RiwayatKoreksiKehadiranActivity.this, KoreksiKehadiranActivity.class));
        });

        getHistoryKoreksiKehadiran();

    }

    private void getHistoryKoreksiKehadiran() {
        divAnimation.setVisibility(View.VISIBLE);
        ApiService apiService = ApiConfig.getApiService();
        apiService.getHistoryKoreksiKehadiran(accesToken,"RAC", "1")
                .enqueue(new Callback<RiwayatKoreksiKehadiranRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatKoreksiKehadiranRootModel> call, Response<RiwayatKoreksiKehadiranRootModel> response) {
                        Log.d(TAG, "onResponse: " + response.body().getData());
                        divAnimation.setVisibility(View.GONE);
                        if (!response.isSuccessful()){
                            Toast.makeText(RiwayatKoreksiKehadiranActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                        } else {
                            dataItems = new ArrayList<>();
                            dataItems = response.body().getData().getData();
                            if (dataItems.isEmpty()) {
                                Toast.makeText(RiwayatKoreksiKehadiranActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            } else {
                                koreksiKehadiranAdapter = new KoreksiKehadiranAdapter(RiwayatKoreksiKehadiranActivity.this, dataItems);
                                rvKoreksiKehadiran.setLayoutManager(new LinearLayoutManager(RiwayatKoreksiKehadiranActivity.this));
                                rvKoreksiKehadiran.setAdapter(koreksiKehadiranAdapter);
                                koreksiKehadiranAdapter.notifyDataSetChanged();
                            }
                        }


                    }
                    @Override
                    public void onFailure(Call<RiwayatKoreksiKehadiranRootModel> call, Throwable t) {
                        divAnimation.setVisibility(View.GONE);
                        Toast.makeText(RiwayatKoreksiKehadiranActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistoryKoreksiKehadiran();
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divAnimation = findViewById(R.id.div_animation);
        animationView = findViewById(R.id.animation_view);
        rvKoreksiKehadiran = findViewById(R.id.rv_koreksi_kehadiran);
        adView = findViewById(R.id.adView);
        btnNewRequest = findViewById(R.id.btn_new_request);
    }
}