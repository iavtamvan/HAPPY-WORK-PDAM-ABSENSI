package com.pdamkotasmg.goodday.fitur.payslip;

import android.annotation.SuppressLint;
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
import com.pdamkotasmg.goodday.fitur.payslip.adapter.SalaryHistoryAdapter;
import com.pdamkotasmg.goodday.fitur.payslip.model.DataItem;
import com.pdamkotasmg.goodday.fitur.payslip.model.SalaryHistoryRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaryHistoryActivity extends AppCompatActivity {

    private List<DataItem> dataItems;
    private SalaryHistoryAdapter salaryHistoryAdapter;

    private ProgressDialog progressDialog;

    private String accessToken;
    private String getPass;


    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private RecyclerView rvSalaryHistory;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_history);
        
        initView();

        ivHeaderBackArrow.setOnClickListener(v -> {
            SalaryHistoryActivity.this.finish();
        });
        tvHeaderJudul.setText("Riwayat PaySlip");
        ivHeaderInfo.setOnClickListener(v -> {
            Toast.makeText(SalaryHistoryActivity.this, "Cek gaji kamu, kurang atau nambah?", Toast.LENGTH_SHORT).show();
        });

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        getPass = sharedPreferences.getString(Config.SHARED_GETPASSWORD, "");

        progressDialog = new ProgressDialog(SalaryHistoryActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengambil uang Anda...");

        getSalaryHistory();
    }

    private void getSalaryHistory() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(SalaryHistoryActivity.this);
        apiService.getSalaryHistory(accessToken, "MOBILE")
                .enqueue(new Callback<SalaryHistoryRootModel>() {
                    @Override
                    public void onResponse(Call<SalaryHistoryRootModel> call, Response<SalaryHistoryRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            dataItems = response.body().getData();
                            if (dataItems == null || dataItems.isEmpty()) {
                                Toast.makeText(SalaryHistoryActivity.this, "Data kosong", Toast.LENGTH_SHORT).show();
                            } else {
                                salaryHistoryAdapter = new SalaryHistoryAdapter(SalaryHistoryActivity.this, dataItems);
                                rvSalaryHistory.setLayoutManager(new LinearLayoutManager(SalaryHistoryActivity.this));
                                rvSalaryHistory.setAdapter(salaryHistoryAdapter);
                                salaryHistoryAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SalaryHistoryRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(SalaryHistoryActivity.this, "Payslip: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        rvSalaryHistory = findViewById(R.id.rv_salary_history);
    }
}