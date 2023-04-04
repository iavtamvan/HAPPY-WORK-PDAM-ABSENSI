package com.pdamkotasmg.goodday.fitur.payslip;

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

public class PayslipActivity extends AppCompatActivity {

    private List<DataItem> dataItems;
    private SalaryHistoryAdapter salaryHistoryAdapter;

    private ProgressDialog progressDialog;

    private String accessToken;
    private String getPass;


    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private RecyclerView rvSalaryHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payslip);
        getSupportActionBar().hide();
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        getPass = sharedPreferences.getString(Config.SHARED_GETPASSWORD, "");

        Toast.makeText(this, "" + getPass, Toast.LENGTH_LONG).show();

        progressDialog = new ProgressDialog(PayslipActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengambil uang Anda...");

        getSalaryHistory();
    }

    private void getSalaryHistory() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(PayslipActivity.this);
        apiService.getSalaryHistory(accessToken, "MOBILE")
                .enqueue(new Callback<SalaryHistoryRootModel>() {
                    @Override
                    public void onResponse(Call<SalaryHistoryRootModel> call, Response<SalaryHistoryRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            dataItems = response.body().getData();
                            salaryHistoryAdapter = new SalaryHistoryAdapter(PayslipActivity.this, dataItems);
                            rvSalaryHistory.setLayoutManager(new LinearLayoutManager(PayslipActivity.this));
                            rvSalaryHistory.setAdapter(salaryHistoryAdapter);
                            salaryHistoryAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<SalaryHistoryRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(PayslipActivity.this, "Payslip: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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