package com.pdamkotasmg.happywork.fitur.kehadiran.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.securetimer.SecureTimer;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.api.server.ApiConfig;
import com.pdamkotasmg.happywork.api.server.ApiService;
import com.pdamkotasmg.happywork.fitur.kehadiran.adapter.KehadiranAdapter;
import com.pdamkotasmg.happywork.fitur.kehadiran.model.DataItem;
import com.pdamkotasmg.happywork.fitur.kehadiran.model.RiwayatKehadiranRootModel;
import com.pdamkotasmg.happywork.utils.Config;

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

    private String nama;
    private String remark;
    private String starTime;
    private String endTime;
    private String shiftDailyCode;

    private String token;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);
        getSupportActionBar().hide();
        initView();
        tvHeaderJudul.setText("Riwayat Rekam Kehadiran");

        dataItems = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);

        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        nama = sharedPreferences.getString(Config.SHARED_NAME, "");
        remark = sharedPreferences.getString(Config.SHARED_REMARK, "");
        starTime = sharedPreferences.getString(Config.SHARED_START_TIME, "");
        endTime = sharedPreferences.getString(Config.SHARED_END_TIME, "");
        shiftDailyCode = sharedPreferences.getString(Config.SHARED_SHIFT_DAILY_CODE, "");

        Date currentTimeInMillis = SecureTimer.with(KehadiranActivity.this).getCurrentDate();
        String dateServer = new SimpleDateFormat("EEEE, dd MMM yyyy").format(currentTimeInMillis);
        tvListKehadiranNama.setText(nama);
        tvDate.setText(dateServer);
        tvListKehadiranShift.setText(remark + " - " + shiftDailyCode + " [" + starTime + " - " + endTime + "]");

        formatDate = new SimpleDateFormat("yyyy-MM-dd").format(currentTimeInMillis);
        dateFrom = LocalDate.parse(formatDate);
        dateFromMinus = dateFrom.minusDays(5);
        dateEnd = dateFrom.plusDays(1);
        getHistoryAbsensi();

    }

    private void getHistoryAbsensi() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getHistoryAbsensi(token, String.valueOf(dateFromMinus), String.valueOf(dateFrom), "1")
                .enqueue(new Callback<RiwayatKehadiranRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatKehadiranRootModel> call, Response<RiwayatKehadiranRootModel> response) {
                        if (response.isSuccessful()) {
                            dataItems = response.body().getData();
                            if (dataItems.isEmpty()) {
                                Toast.makeText(KehadiranActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            } else {
                                kehadiranAdapter = new KehadiranAdapter(KehadiranActivity.this, dataItems);
                                rvKehadiran.setLayoutManager(new LinearLayoutManager(KehadiranActivity.this));
                                rvKehadiran.setAdapter(kehadiranAdapter);
                                kehadiranAdapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            Toast.makeText(KehadiranActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatKehadiranRootModel> call, Throwable t) {
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
    }
}