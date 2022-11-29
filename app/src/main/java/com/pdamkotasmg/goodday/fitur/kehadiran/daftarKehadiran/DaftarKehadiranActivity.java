package com.pdamkotasmg.goodday.fitur.kehadiran.daftarKehadiran;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdView;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.adapter.KehadiranAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.RiwayatKehadiranRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKehadiranActivity extends AppCompatActivity {

    private KehadiranAdapter kehadiranAdapter;
    private List<DataItem> dataItems;
    private SharedPreferences.Editor editor;

    private String nama;
    private String accessToken;
    private String formatDate;
    private LocalDate dateFromMinus;
    private LocalDate dateFrom;
    private LocalDate dateEnd;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private LinearLayout divAnimation;
    private LottieAnimationView animationView;
    private RecyclerView rvKehadiran;
    private AdView adView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kehadiran);
        getSupportActionBar().hide();
        initView();

        Config.ads(DaftarKehadiranActivity.this, adView);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        nama = sharedPreferences.getString(Config.SHARED_NAME, "");

//        Date currentTimeInMillis = SecureTimer.with(DaftarKehadiranActivity.this).getCurrentDate();
        Date dates = new Date();
        formatDate = new SimpleDateFormat("yyyy-MM-dd").format(dates);

        getHistoryPresensi();

        tvHeaderJudul.setText("Daftar Kehadiran");
        ivHeaderInfo.setVisibility(View.GONE);
        ivHeaderBackArrow.setOnClickListener(v -> {
            DaftarKehadiranActivity.this.finish();
        });

    }

    private void getHistoryPresensi() {
        divAnimation.setVisibility(View.VISIBLE);
        ApiService apiService = ApiConfig.getApiService(this);
        apiService.getHistoryPresensi(accessToken, "",formatDate,"7", "1") // tanggal dari, dan tanggal selesai
                .enqueue(new Callback<RiwayatKehadiranRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatKehadiranRootModel> call, Response<RiwayatKehadiranRootModel> response) {
                        if (response.isSuccessful()) {
                            dataItems = new ArrayList<>();
                            dataItems = response.body().getData();
                            divAnimation.setVisibility(View.GONE);
                            rvKehadiran.setVisibility(View.VISIBLE);
                            if (dataItems.isEmpty()) {
                                Toast.makeText(DaftarKehadiranActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            } else {
                                kehadiranAdapter = new KehadiranAdapter(DaftarKehadiranActivity.this, dataItems);
                                rvKehadiran.setLayoutManager(new LinearLayoutManager(DaftarKehadiranActivity.this));
                                rvKehadiran.setAdapter(kehadiranAdapter);
                                kehadiranAdapter.notifyDataSetChanged();
                            }
                        } else {
                            divAnimation.setVisibility(View.GONE);
                            Toast.makeText(DaftarKehadiranActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatKehadiranRootModel> call, Throwable t) {
                        divAnimation.setVisibility(View.GONE);
                        Toast.makeText(DaftarKehadiranActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divAnimation = findViewById(R.id.div_animation);
        animationView = findViewById(R.id.animation_view);
        rvKehadiran = findViewById(R.id.rv_kehadiran);
        adView = findViewById(R.id.adView);
    }
}