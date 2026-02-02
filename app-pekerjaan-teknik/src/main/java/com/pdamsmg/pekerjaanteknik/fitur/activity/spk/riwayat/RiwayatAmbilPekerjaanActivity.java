package com.pdamsmg.pekerjaanteknik.fitur.activity.spk.riwayat;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamsmg.pekerjaanteknik.R;
import com.pdamsmg.pekerjaanteknik.adapter.DaftarPerbaikanCCAdapter;
import com.pdamsmg.pekerjaanteknik.api.ApiConfig;
import com.pdamsmg.pekerjaanteknik.api.ApiService;
import com.pdamsmg.pekerjaanteknik.model.callCenter.diposisi.DataItem;
import com.pdamsmg.pekerjaanteknik.model.callCenter.diposisi.DisposisiRootModel;
import com.pdamsmg.pekerjaanteknik.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatAmbilPekerjaanActivity extends AppCompatActivity {
    private List<DataItem> dataItemsDisposisi;
    private DaftarPerbaikanCCAdapter daftarPerbaikanCCAdapter;

    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    public String accessToken;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_ambil_pekerjaan);
        getSupportActionBar().hide();

        initView();
        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, RiwayatAmbilPekerjaanActivity.this, "Riwayat Ambil Pekerjaan");

        progressDialog = new ProgressDialog(RiwayatAmbilPekerjaanActivity.this);
        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        getRiwayatAmbilPekerjaan();
    }

    public void getRiwayatAmbilPekerjaan() {
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.getDataCallCenterAduanByTenagaNPP(accessToken)
                .enqueue(new Callback<DisposisiRootModel>() {
                    @Override
                    public void onResponse(Call<DisposisiRootModel> call, Response<DisposisiRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            dataItemsDisposisi = new ArrayList<>();
                            dataItemsDisposisi = response.body().getData();
                            daftarPerbaikanCCAdapter = new DaftarPerbaikanCCAdapter(RiwayatAmbilPekerjaanActivity.this, dataItemsDisposisi);
                            rv.setLayoutManager(new LinearLayoutManager(RiwayatAmbilPekerjaanActivity.this));
                            rv.setAdapter(daftarPerbaikanCCAdapter);
                            daftarPerbaikanCCAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<DisposisiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(RiwayatAmbilPekerjaanActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        rv = findViewById(R.id.rv);
    }
}