package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.details.DetailsKehadiranAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.details.DetailsListApprovalKehadiranAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.detailKoreksiKehadiran.Data;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.detailKoreksiKehadiran.DetailKoreksiKehadiranRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKoreksiKehadiranActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private SharedPreferences sharedPreferences;
    private String accessToken;

    private Data dataList;
    private DetailsKehadiranAdapter detailsKehadiranAdapter;
    private DetailsListApprovalKehadiranAdapter detailsListApprovalKehadiranAdapter;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvDetailRequestDari;
    private TextView tvDetailRequestUntuk;
    private TextView tvDetailMulaiTanggal;
    private TextView tvDetailSelesaiTanggal;
    private RecyclerView rvDetailKoreksiKehadiran;
    private RecyclerView rvDetailMengetahui;
    private Button btnNewRequest;
    private TextView tvDetailsTanggalReq;
    private TextView tvDetailsStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_koreksi_kehadiran);
        getSupportActionBar().hide();
        initView();

        ivHeaderInfo.setOnClickListener(v -> {
            Toast.makeText(DetailKoreksiKehadiranActivity.this, "Info", Toast.LENGTH_SHORT).show();
        });

        ivHeaderBackArrow.setOnClickListener(v -> {
            DetailKoreksiKehadiranActivity.this.finish();
        });

        tvHeaderJudul.setText("Details Koreksi Kehadiran");

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        getDetailsKoreksiKehadiran();

    }

    private void getDetailsKoreksiKehadiran() {
        ProgressDialog progressDialog = new ProgressDialog(DetailKoreksiKehadiranActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getRequestByNumber(accessToken, "RAC", "RAC-202202-000001", "1")
                .enqueue(new Callback<DetailKoreksiKehadiranRootModel>() {
                    @Override
                    public void onResponse(Call<DetailKoreksiKehadiranRootModel> call, Response<DetailKoreksiKehadiranRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            dataList = new Data();
                            dataList = response.body().getData();

                            tvDetailsTanggalReq.setText(dataList.getRequestedAt());

                            tvDetailsStatus.setText(dataList.getRequestStatus());
                            if (dataList.getRequestStatus().equalsIgnoreCase("Waiting")) {
                                tvDetailsStatus.setTextColor(getResources().getColor(R.color.yellowPortal));
                            } else if (dataList.getRequestStatus().equalsIgnoreCase("Approved")) {
                                tvDetailsStatus.setTextColor(getResources().getColor(R.color.greenPortal));
                            } else {
                                tvDetailsStatus.setTextColor(getResources().getColor(R.color.redPortal));
                            }

                            tvDetailRequestDari.setText(dataList.getRequestedByName());
                            tvDetailRequestUntuk.setText(dataList.getRequestedForName());
                            tvDetailMulaiTanggal.setText(dataList.getRequestStartDate());
                            tvDetailSelesaiTanggal.setText(dataList.getRequestEndDate());

                            Log.d(TAG, "datList: " + dataList.getRequestedByName());
                            detailsKehadiranAdapter = new DetailsKehadiranAdapter(DetailKoreksiKehadiranActivity.this, dataList.getRequestDetails());
                            Log.d(TAG, "detailsKehadiranAdapter: " + detailsKehadiranAdapter.getItemCount());
                            rvDetailKoreksiKehadiran.setLayoutManager(new LinearLayoutManager(DetailKoreksiKehadiranActivity.this));
                            rvDetailKoreksiKehadiran.setAdapter(detailsKehadiranAdapter);
                            detailsKehadiranAdapter.notifyDataSetChanged();

                            detailsListApprovalKehadiranAdapter = new DetailsListApprovalKehadiranAdapter(DetailKoreksiKehadiranActivity.this, dataList.getListOfApprovals());
                            rvDetailMengetahui.setLayoutManager(new LinearLayoutManager(DetailKoreksiKehadiranActivity.this));
                            rvDetailMengetahui.setAdapter(detailsListApprovalKehadiranAdapter);
                            detailsListApprovalKehadiranAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailKoreksiKehadiranRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DetailKoreksiKehadiranActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvDetailRequestDari = findViewById(R.id.tv_detail_request_dari);
        tvDetailRequestUntuk = findViewById(R.id.tv_detail_request_untuk);
        tvDetailMulaiTanggal = findViewById(R.id.tv_detail_mulai_tanggal);
        tvDetailSelesaiTanggal = findViewById(R.id.tv_detail_selesai_tanggal);
        rvDetailKoreksiKehadiran = findViewById(R.id.rv_detail_koreksi_kehadiran);
        rvDetailMengetahui = findViewById(R.id.rv_detail_mengetahui);
        btnNewRequest = findViewById(R.id.btn_new_request);
        tvDetailsTanggalReq = findViewById(R.id.tv_details_tanggal_req);
        tvDetailsStatus = findViewById(R.id.tv_details_status);
    }
}