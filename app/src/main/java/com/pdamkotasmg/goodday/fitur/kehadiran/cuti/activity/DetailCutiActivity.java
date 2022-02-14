package com.pdamkotasmg.goodday.fitur.kehadiran.cuti.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.adapter.DetailsListApprovalAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti.Data;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti.ListOfApprovalsItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti.RiwayatCutiRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCutiActivity extends AppCompatActivity {

    private final String TAG = "debug";

    private String accessToken;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Data data;
    private DetailsListApprovalAdapter detailsListApprovalAdapter;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvDetailRequestNumber;
    private TextView tvDetailRequestDari;
    private TextView tvDetailRequestUntuk;
    private TextView tvDetailMulaiTanggal;
    private TextView tvDetailTotalDay;
    private TextView tvDetailTipe;
    private TextView tvDetailSisaCuti;
    private TextView tvDetailAlasan;
    private RecyclerView rvDetailMengetahui;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cuti);
        initView();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        getDetail();
    }

    private void getDetail() {
        ProgressDialog progressDialog = new ProgressDialog(DetailCutiActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getRequestHistoryCutiByNumber(accessToken, "RLV", "RLV-202202-000001", "1")
                .enqueue(new Callback<RiwayatCutiRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatCutiRootModel> call, Response<RiwayatCutiRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            data = new Data();
                            data = response.body().getData();


                            for (int i = 0; i < data.getData().size(); i++) {
                                List<ListOfApprovalsItem> dataList = new ArrayList<>();
                                dataList = data.getData().get(i).getListOfApprovals();
                                detailsListApprovalAdapter = new DetailsListApprovalAdapter(DetailCutiActivity.this, dataList);
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatCutiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DetailCutiActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvDetailRequestNumber = findViewById(R.id.tv_detail_request_number);
        tvDetailRequestDari = findViewById(R.id.tv_detail_request_dari);
        tvDetailRequestUntuk = findViewById(R.id.tv_detail_request_untuk);
        tvDetailMulaiTanggal = findViewById(R.id.tv_detail_mulai_tanggal);
        tvDetailTotalDay = findViewById(R.id.tv_detail_total_day);
        tvDetailTipe = findViewById(R.id.tv_detail_tipe);
        tvDetailSisaCuti = findViewById(R.id.tv_detail_sisa_cuti);
        tvDetailAlasan = findViewById(R.id.tv_detail_alasan);
        rvDetailMengetahui = findViewById(R.id.rv_detail_mengetahui);
        btnCancel = findViewById(R.id.btn_cancel);
    }
}