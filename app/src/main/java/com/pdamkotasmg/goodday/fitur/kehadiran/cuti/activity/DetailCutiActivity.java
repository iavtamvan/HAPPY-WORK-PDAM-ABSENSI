package com.pdamkotasmg.goodday.fitur.kehadiran.cuti.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.adapter.DetailsListApprovalAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.detailCuti.Data;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.detailCuti.DetailCutiRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCutiActivity extends AppCompatActivity {

    private final String TAG = "debug";

    private String accessToken;
    private String numberReq;

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
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        numberReq = getIntent().getStringExtra(Config.BUNDLE_NUMBER_REQUEST);

        ivHeaderBackArrow.setOnClickListener(v -> {
            DetailCutiActivity.this.finish();
        });

        tvHeaderJudul.setText("Detail Cuti");

        ivHeaderInfo.setOnClickListener(v -> {
            Toast.makeText(DetailCutiActivity.this, "Detail cuti", Toast.LENGTH_SHORT).show();
        });

        btnCancel.setOnClickListener(v -> {
            postCancel();
        });

        getDetail();
    }

    private void postCancel() {
        ProgressDialog progressDialog = new ProgressDialog(DetailCutiActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getRequestEdit(accessToken, "RLV", numberReq, "CANCELLED")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            String header = response.message();
                            Log.d(TAG, "onResponse: " + header);
                            Toast.makeText(DetailCutiActivity.this, "Pembatalan berhasil", Toast.LENGTH_SHORT).show();
                            getDetail();
                        } else {
                            Toast.makeText(DetailCutiActivity.this, "Pembatalan gagal", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error Else message: " + response.message());
                            Log.d(TAG, "Error Else body: " + response.body());
                            Log.d(TAG, "Error Else errorBody: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DetailCutiActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDetail() {
        ProgressDialog progressDialog = new ProgressDialog(DetailCutiActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getRequestHistoryCutiByNumber(accessToken, "RLV", numberReq, "1")
                .enqueue(new Callback<DetailCutiRootModel>() {
                    @Override
                    public void onResponse(Call<DetailCutiRootModel> call, Response<DetailCutiRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            data = new Data();
                            data = response.body().getData();

                            tvDetailRequestNumber.setText(data.getRequestNumber());
                            tvDetailRequestDari.setText(data.getRequestedByName());
                            tvDetailRequestUntuk.setText(data.getRequestedForName());
                            tvDetailMulaiTanggal.setText(data.getRequestedAt());
                            tvDetailTipe.setText(data.getRequestType());
                            tvDetailAlasan.setText(data.getRemark());

                            String status = data.getRequestStatus();
                            if (status.equalsIgnoreCase("Rejected")) {
                                btnCancel.setVisibility(View.GONE);
                            } else if (status.equalsIgnoreCase("Approved")) {
                                btnCancel.setVisibility(View.GONE);
                            } else if (status.equalsIgnoreCase("Cancelled")) {
                                btnCancel.setVisibility(View.GONE);
                            } else {
                                btnCancel.setVisibility(View.GONE);
                            }

                            detailsListApprovalAdapter = new DetailsListApprovalAdapter(DetailCutiActivity.this, data.getListOfApprovals());
                            rvDetailMengetahui.setLayoutManager(new LinearLayoutManager(DetailCutiActivity.this));
                            rvDetailMengetahui.setAdapter(detailsListApprovalAdapter);
                            detailsListApprovalAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailCutiRootModel> call, Throwable t) {
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