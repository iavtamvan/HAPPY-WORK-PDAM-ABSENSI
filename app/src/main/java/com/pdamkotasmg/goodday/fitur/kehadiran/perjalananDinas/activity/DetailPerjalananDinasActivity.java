package com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.activity;

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
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.adapter.DetailsListApprovalAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.detailPerjalananDinas.Data;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.detailPerjalananDinas.DetailPerjalananDinasRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPerjalananDinasActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private SharedPreferences sharedPreferences;
    private String accessToken;
    private String numberReq;

    private ProgressDialog progressDialog;

    private Data dataList;
    private DetailsListApprovalAdapter detailsListApprovalAdapter;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvDetailRequestNumber;
    private TextView tvDetailRequestDari;
    private TextView tvDetailRequestUntuk;
    private TextView tvDetailMulaiTanggal;
    private TextView tvDetailSelesaiTanggal;
    private TextView tvDetailAlasan;
    private RecyclerView rvDetail;
    private RecyclerView rvDetailMengetahui;
    private Button btnCancel;
    private TextView tvDetailsStatus;
    private TextView tvDetailsTanggalReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_perjalanan_dinas);
        getSupportActionBar().hide();
        initView();

        ivHeaderInfo.setOnClickListener(v -> {
            Toast.makeText(DetailPerjalananDinasActivity.this, "Details Perjalanan Dinas", Toast.LENGTH_SHORT).show();
        });

        ivHeaderBackArrow.setOnClickListener(v -> {
            DetailPerjalananDinasActivity.this.finish();
        });

        tvHeaderJudul.setText("Details Perjalanan Dinas");

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        numberReq = getIntent().getStringExtra(Config.BUNDLE_NUMBER_REQUEST);

        progressDialog = new ProgressDialog(DetailPerjalananDinasActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu...");

        getDetailsPerjalananDinas();

        btnCancel.setOnClickListener(v -> {
            reqCancel();
        });

    }

    private void reqCancel() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getRequestEdit(accessToken, "RAC", numberReq, "CANCELLED")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            String header = response.message();
                            Log.d(TAG, "onResponse: " + header);
                            Toast.makeText(DetailPerjalananDinasActivity.this, "Pembatalan berhasil", Toast.LENGTH_SHORT).show();
                            getDetailsPerjalananDinas();
                        } else {
                            Toast.makeText(DetailPerjalananDinasActivity.this, "Pembatalan gagal", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error Else message: " + response.message());
                            Log.d(TAG, "Error Else body: " + response.body());
                            Log.d(TAG, "Error Else errorBody: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DetailPerjalananDinasActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDetailsPerjalananDinas() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getRequestByNumberPerjalananDinas(accessToken, "ROD", numberReq, "1")
                .enqueue(new Callback<DetailPerjalananDinasRootModel>() {
                    @Override
                    public void onResponse(Call<DetailPerjalananDinasRootModel> call, Response<DetailPerjalananDinasRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            dataList = new Data();
                            dataList = response.body().getData();

                            tvDetailRequestNumber.setText(dataList.getRequestNumber());

                            // TODO convert Tanggal dari server
                            SimpleDateFormat dateNtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date dateRequestAt = dateNtime.parse(dataList.getRequestedAt());
                                Date dateRequestStartDate = date.parse(dataList.getRequestDetails().get(0).getDateStart());
                                Date dateRequestEndDate = date.parse(dataList.getRequestDetails().get(0).getDateEnd());

                                String dateRequestAtFix = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss").format(dateRequestAt);
                                String dateRequestStartDateFix = new SimpleDateFormat("EEE, dd MMM yyyy").format(dateRequestStartDate);
                                String dateRequestEndDateFix = new SimpleDateFormat("EEE, dd MMM yyyy").format(dateRequestEndDate);

                                tvDetailsTanggalReq.setText(dateRequestAtFix);
                                tvDetailMulaiTanggal.setText(dateRequestStartDateFix);
                                tvDetailSelesaiTanggal.setText(dateRequestEndDateFix);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String status = dataList.getRequestStatus();
                            tvDetailsStatus.setText(dataList.getRequestStatus());
                            if (status.equalsIgnoreCase("Waiting")) {
                                tvDetailsStatus.setTextColor(getResources().getColor(R.color.yellowPortal));
                            } else if (status.equalsIgnoreCase("Approved")) {
                                btnCancel.setVisibility(View.GONE);
                                tvDetailsStatus.setTextColor(getResources().getColor(R.color.greenPortal));
                            } else if (status.equalsIgnoreCase("Cancelled")) {
                                btnCancel.setVisibility(View.GONE);
                                tvDetailsStatus.setTextColor(getResources().getColor(R.color.redPortal));
                            } else {
                                btnCancel.setVisibility(View.GONE);
                                tvDetailsStatus.setTextColor(getResources().getColor(R.color.redPortal));
                            }

                            tvDetailAlasan.setText(dataList.getRemark());
                            tvDetailRequestDari.setText(dataList.getRequestedByName());
                            tvDetailRequestUntuk.setText(dataList.getRequestedForName());

                            detailsListApprovalAdapter = new DetailsListApprovalAdapter(DetailPerjalananDinasActivity.this, dataList.getListOfApprovals());
                            rvDetailMengetahui.setLayoutManager(new LinearLayoutManager(DetailPerjalananDinasActivity.this));
                            rvDetailMengetahui.setAdapter(detailsListApprovalAdapter);
                            detailsListApprovalAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailPerjalananDinasRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DetailPerjalananDinasActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
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
        tvDetailSelesaiTanggal = findViewById(R.id.tv_detail_selesai_tanggal);
        tvDetailAlasan = findViewById(R.id.tv_detail_alasan);
        rvDetail = findViewById(R.id.rv_detail);
        rvDetailMengetahui = findViewById(R.id.rv_detail_mengetahui);
        btnCancel = findViewById(R.id.btn_cancel);
        tvDetailsStatus = findViewById(R.id.tv_details_status);
        tvDetailsTanggalReq = findViewById(R.id.tv_details_tanggal_req);
    }
}