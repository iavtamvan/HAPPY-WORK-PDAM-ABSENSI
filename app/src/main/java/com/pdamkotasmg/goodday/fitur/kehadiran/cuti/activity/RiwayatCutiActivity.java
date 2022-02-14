package com.pdamkotasmg.goodday.fitur.kehadiran.cuti.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.CutiActivity;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.adapter.RiwayatCutiAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti.RiwayatCutiRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatCutiActivity extends AppCompatActivity {

    private final String TAG = "debug";
    private RiwayatCutiAdapter riwayatCutiAdapter;
    private List<DataItem> dataItems;

    private SharedPreferences sharedPreferences;

    private String accessToken;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private LinearLayout divAnimation;
    private LottieAnimationView animationView;
    private RecyclerView rvCuti;
    private AdView adView;
    private Button btnNewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_cuti);
        initView();
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        ivHeaderBackArrow.setOnClickListener(v -> {
            RiwayatCutiActivity.this.finish();
        });

        tvHeaderJudul.setText("Riwayat Cuti");

        ivHeaderInfo.setOnClickListener(v -> {
            Toast.makeText(RiwayatCutiActivity.this, "Riwayat cuti", Toast.LENGTH_SHORT).show();
        });

        btnNewRequest.setOnClickListener(v -> {
            startActivity(new Intent(RiwayatCutiActivity.this, CutiActivity.class));
        });
        getRiwayatCuti();

    }

    private void getRiwayatCuti() {
        ProgressDialog progressDialog = new ProgressDialog(RiwayatCutiActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getRequestHistoryCuti(accessToken, "RLV", "1")
                .enqueue(new Callback<RiwayatCutiRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatCutiRootModel> call, Response<RiwayatCutiRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            dataItems = new ArrayList<>();
                            dataItems = response.body().getData().getData();
                            riwayatCutiAdapter = new RiwayatCutiAdapter(RiwayatCutiActivity.this, dataItems);
                            rvCuti.setLayoutManager(new LinearLayoutManager(RiwayatCutiActivity.this));
                            rvCuti.setAdapter(riwayatCutiAdapter);
                            riwayatCutiAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatCutiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(RiwayatCutiActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divAnimation = findViewById(R.id.div_animation);
        animationView = findViewById(R.id.animation_view);
        rvCuti = findViewById(R.id.rv_cuti);
        adView = findViewById(R.id.adView);
        btnNewRequest = findViewById(R.id.btn_new_request);
    }
}