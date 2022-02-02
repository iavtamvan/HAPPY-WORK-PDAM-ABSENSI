package com.pdamkotasmg.goodday.fitur.permintaan;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.permintaan.adapter.PermintaanAdapter;
import com.pdamkotasmg.goodday.fitur.permintaan.model.DataItem;
import com.pdamkotasmg.goodday.fitur.permintaan.model.PermintaanRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermintaanActivity extends AppCompatActivity {

    private static final String TAG = "debug";
    private String accesToken;
    private SharedPreferences sharedPreferences;
    private List<DataItem> dataItems;
    private PermintaanAdapter permintaanAdapter;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private RecyclerView rvRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan);
        getSupportActionBar().hide();
        initView();

        ivHeaderBackArrow.setOnClickListener(v -> {
            PermintaanActivity.this.finish();
        });
        tvHeaderJudul.setText("Permintaan");
        ivHeaderInfo.setOnClickListener(v -> {
            Toast.makeText(PermintaanActivity.this, "Permintaan untuk merindukan Liburan", Toast.LENGTH_SHORT).show();
        });

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accesToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        Log.d(TAG, "token: " + accesToken);

        getHistoryRequest();

    }

    private void getHistoryRequest() {
        ProgressDialog progressDialog = new ProgressDialog(PermintaanActivity.this);
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getRequestHistoryAll(accesToken, "ALL", "1")
                .enqueue(new Callback<PermintaanRootModel>() {
                    @Override
                    public void onResponse(Call<PermintaanRootModel> call, Response<PermintaanRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()){
                            dataItems = new ArrayList<>();
                            dataItems = response.body().getData().getData();
                            permintaanAdapter = new PermintaanAdapter(PermintaanActivity.this, dataItems);
                            rvRequest.setLayoutManager(new LinearLayoutManager(PermintaanActivity.this));
                            rvRequest.setAdapter(permintaanAdapter);
                            permintaanAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(PermintaanActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PermintaanRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(PermintaanActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
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