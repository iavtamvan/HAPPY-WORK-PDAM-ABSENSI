package com.pdamkotasmg.goodday.fitur.permintaan_persetujuan.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.permintaan_persetujuan.adapter.PersetujuanAdapter;
import com.pdamkotasmg.goodday.fitur.permintaan_persetujuan.model.DataItem;
import com.pdamkotasmg.goodday.fitur.permintaan_persetujuan.model.PermintaanRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersetujuanActivity extends AppCompatActivity {

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private RecyclerView rvRequest;

    private PersetujuanAdapter persetujuanAdapter;
    private List<DataItem> dataItems;

    private SharedPreferences sharedPreferences;
    private String token;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persetujuan);
        
        initView();

        ivHeaderBackArrow.setOnClickListener(view -> {
            PersetujuanActivity.this.finish();
        });

        tvHeaderJudul.setText("Persetujuan");

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        progressDialog = new ProgressDialog(PersetujuanActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);

        getNeedApprovals();

    }

    private void getNeedApprovals() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(PersetujuanActivity.this);
        apiService.getNeedApprovals(token, "1")
                .enqueue(new Callback<PermintaanRootModel>() {
                    @Override
                    public void onResponse(Call<PermintaanRootModel> call, Response<PermintaanRootModel> response) {
                        if (response.isSuccessful()){
                            progressDialog.cancel();
                            dataItems = new ArrayList<>();
                            dataItems = response.body().getData().getData();
                            persetujuanAdapter = new PersetujuanAdapter(PersetujuanActivity.this, dataItems);
                            rvRequest.setLayoutManager(new LinearLayoutManager(PersetujuanActivity.this));
                            rvRequest.setAdapter(persetujuanAdapter);
                            persetujuanAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<PermintaanRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(PersetujuanActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        rvRequest = findViewById(R.id.rv_request);
    }
}