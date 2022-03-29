package com.pdamkotasmg.goodday.fitur.kehadiran.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.krishna.securetimer.SecureTimer;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.dashboard.DashboardActivity;
import com.pdamkotasmg.goodday.fitur.dashboard.model.ShfitPegawaiRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.activity.RiwayatCutiActivity;
import com.pdamkotasmg.goodday.fitur.kehadiran.daftarKehadiran.DaftarKehadiranActivity;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.adapter.KehadiranAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.RiwayatKehadiranRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.activity.RiwayatKoreksiKehadiranActivity;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.activity.RiwayatPerjalananDinasActivity;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KehadiranActivity extends AppCompatActivity {
    private KehadiranAdapter kehadiranAdapter;
    private List<DataItem> dataItems;
    private SharedPreferences.Editor editor;
    private String nama;
    private String remark;
    private String starTime;
    private String endTime;
    private String shiftDailyCode;
    private String riwayatAbsensiCode;

    private String accessToken;
    private String formatDate;
    private LocalDate dateFromMinus;
    private LocalDate dateFrom;
    private LocalDate dateEnd;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private CircleImageView ciListKehadiranProfileImage;
    private TextView tvListKehadiranNama;
    private TextView tvDate;
    private TextView tvListKehadiranShift;
    private RecyclerView rvKehadiran;
    private LinearLayout divAnimation;
    private LottieAnimationView animationView;
    private Button btnHome;
    private AdView adView;
    private LinearLayout divDaftarKehadiran;
    private LinearLayout divKoreksiKehadiran;
    private LinearLayout divCuti;
    private LinearLayout divLembur;
    private LinearLayout divPerjalananDinas;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);
        getSupportActionBar().hide();
        initView();
        tvHeaderJudul.setText("Riwayat Rekam Kehadiran");
        ivHeaderInfo.setVisibility(View.GONE);
        ivHeaderBackArrow.setOnClickListener(v -> {
            KehadiranActivity.this.finish();
        });

        Config.ads(KehadiranActivity.this, adView);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        nama = sharedPreferences.getString(Config.SHARED_NAME, "");

        riwayatAbsensiCode = getIntent().getStringExtra(Config.BUNDLE_RIWAYAT_ABSENSI);
        if (riwayatAbsensiCode.equalsIgnoreCase("1")) {
            btnHome.setVisibility(View.VISIBLE);
        } else {
            btnHome.setVisibility(View.GONE);
        }

        btnHome.setOnClickListener(v -> {
            finishAffinity();
//            Config.interestialIntent(KehadiranActivity.this, DashboardActivity.class);
            startActivity(new Intent(KehadiranActivity.this, DashboardActivity.class));
        });

        Date currentTimeInMillis = SecureTimer.with(KehadiranActivity.this).getCurrentDate();
        String dateServer = new SimpleDateFormat("EEEE, dd MMM yyyy").format(currentTimeInMillis);
        tvListKehadiranNama.setText(nama);
        tvDate.setText(dateServer);

        formatDate = new SimpleDateFormat("yyyy-MM-dd").format(currentTimeInMillis);
        dateFrom = LocalDate.parse(formatDate);
        dateFromMinus = dateFrom.minusDays(2);
        dateEnd = dateFrom.plusDays(1);
        // TODO getShift pegawai DONE
        // TODO getHistory presensi DONE
        getShiftPegawai();

        divDaftarKehadiran.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DaftarKehadiranActivity.class));
        });

        divKoreksiKehadiran.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RiwayatKoreksiKehadiranActivity.class));
        });

        divCuti.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RiwayatCutiActivity.class));
        });

        divPerjalananDinas.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RiwayatPerjalananDinasActivity.class));
        });
    }

    private void getHistoryPresensi() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getHistoryPresensi(accessToken, String.valueOf(dateFromMinus), String.valueOf(dateFrom), "1") // tanggal dari, dan tanggal selesai
                .enqueue(new Callback<RiwayatKehadiranRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatKehadiranRootModel> call, Response<RiwayatKehadiranRootModel> response) {
                        if (response.isSuccessful()) {
                            dataItems = new ArrayList<>();
                            dataItems = response.body().getData();
                            divAnimation.setVisibility(View.GONE);
                            rvKehadiran.setVisibility(View.VISIBLE);
                            if (dataItems.isEmpty()) {
                                Toast.makeText(KehadiranActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            } else {
                                kehadiranAdapter = new KehadiranAdapter(KehadiranActivity.this, dataItems);
                                rvKehadiran.setLayoutManager(new LinearLayoutManager(KehadiranActivity.this));
                                rvKehadiran.setAdapter(kehadiranAdapter);
                                kehadiranAdapter.notifyDataSetChanged();
                            }
                        } else {
                            divAnimation.setVisibility(View.GONE);
                            Toast.makeText(KehadiranActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatKehadiranRootModel> call, Throwable t) {
                        divAnimation.setVisibility(View.GONE);
                        Toast.makeText(KehadiranActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getShiftPegawai() {
        rvKehadiran.setVisibility(View.GONE);
        divAnimation.setVisibility(View.VISIBLE);
        ApiService apiService = ApiConfig.getApiService();
        apiService.getShiftPegawai(accessToken).enqueue(new Callback<ShfitPegawaiRootModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ShfitPegawaiRootModel> call, Response<ShfitPegawaiRootModel> response) {
                if (response.isSuccessful()) {
                    editor.putString(Config.SHARED_SHIFT_DAILY_CODE, response.body().getData().getShiftDailyCode());
                    editor.putString(Config.SHARED_START_TIME, response.body().getData().getStartTime());
                    editor.putString(Config.SHARED_END_TIME, response.body().getData().getEndTime());
                    editor.putString(Config.SHARED_REMARK, response.body().getData().getRemark());
                    editor.apply();
                    remark = response.body().getData().getRemark();
                    starTime = response.body().getData().getStartTime();
                    endTime = response.body().getData().getEndTime();
                    shiftDailyCode = response.body().getData().getShiftDailyCode();
                    tvListKehadiranShift.setText(remark + " - " + shiftDailyCode + " [" + starTime + " - " + endTime + "]");
                    getHistoryPresensi();
                } else {
                    Toast.makeText(KehadiranActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShfitPegawaiRootModel> call, Throwable t) {
                Toast.makeText(KehadiranActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
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
        divAnimation = findViewById(R.id.div_animation);
        animationView = findViewById(R.id.animation_view);
        btnHome = findViewById(R.id.btnHome);
        adView = findViewById(R.id.adView);
        divDaftarKehadiran = findViewById(R.id.div_daftar_kehadiran);
        divKoreksiKehadiran = findViewById(R.id.div_koreksi_kehadiran);
        divCuti = findViewById(R.id.div_cuti);
        divLembur = findViewById(R.id.div_lembur);
        divPerjalananDinas = findViewById(R.id.div_perjalanan_dinas);
    }
}