package com.pdamsmg.pekerjaanteknik.fitur.activity.spk;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamsmg.pekerjaanteknik.R;
import com.pdamsmg.pekerjaanteknik.adapter.VerifikasiSPKAdapter;
import com.pdamsmg.pekerjaanteknik.api.ApiConfig;
import com.pdamsmg.pekerjaanteknik.api.ApiService;
import com.pdamsmg.pekerjaanteknik.model.riwayatSpk.verifikator.DataItem;
import com.pdamsmg.pekerjaanteknik.model.riwayatSpk.verifikator.VerifikatorRootModel;
import com.pdamsmg.pekerjaanteknik.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiActivity extends AppCompatActivity {
    private final String TAG = "debug";
    private String token;
    private String npp;

    private VerifikasiSPKAdapter verifikasiSPKAdapter;
    private List<DataItem> dataItem = new ArrayList<>();

    private RecyclerView rv;
    private EditText edtSearch;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);
        initView();
        getSupportActionBar().hide();
        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, VerifikasiActivity.this, "Verifikasi Perbaikan");

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        getListVerifikasi();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifikasiSPKAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getListVerifikasi() {
        ProgressDialog progressDialog = new ProgressDialog(VerifikasiActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.listVerifSPKVerifikator(token)
                .enqueue(new Callback<VerifikatorRootModel>() {
                    @Override
                    public void onResponse(Call<VerifikatorRootModel> call, Response<VerifikatorRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            dataItem = response.body().getData();
                            verifikasiSPKAdapter = new VerifikasiSPKAdapter(VerifikasiActivity.this, dataItem);
                            rv.setLayoutManager(new LinearLayoutManager(VerifikasiActivity.this));
                            rv.setAdapter(verifikasiSPKAdapter);
                            verifikasiSPKAdapter.notifyDataSetChanged();
                        } else {
                            progressDialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifikatorRootModel> call, Throwable t) {
                        Toast.makeText(VerifikasiActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        edtSearch = findViewById(R.id.edt_search);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        VerifikasiActivity.this.finish();
    }
}