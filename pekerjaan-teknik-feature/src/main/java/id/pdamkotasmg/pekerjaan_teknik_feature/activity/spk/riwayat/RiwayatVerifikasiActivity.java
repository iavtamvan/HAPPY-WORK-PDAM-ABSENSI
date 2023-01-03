package id.pdamkotasmg.pekerjaan_teknik_feature.activity.spk.riwayat;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import id.pdamkotasmg.pekerjaan_teknik_feature.R;
import id.pdamkotasmg.pekerjaan_teknik_feature.adapter.RiwayatVerifikasiSPKAdapter;
import id.pdamkotasmg.pekerjaan_teknik_feature.api.ApiConfig;
import id.pdamkotasmg.pekerjaan_teknik_feature.api.ApiService;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.riwayatSpk.verifikator.DataItem;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.riwayatSpk.verifikator.VerifikatorRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.utils.Config;
import id.pdamkotasmg.pekerjaan_teknik_feature.utils.ConfigAds;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatVerifikasiActivity extends AppCompatActivity {

    private String token;
    private String npp;

    private RiwayatVerifikasiSPKAdapter riwayatVerifikasiSPKAdapter;
    private List<DataItem> dataItem = new ArrayList<>();

    private EditText edtSearch;
    private RecyclerView rv;
    private Button btnHalamanUtama;
    private AdView adView;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_verifikasi);
        getSupportActionBar().hide();
        initView();
        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, RiwayatVerifikasiActivity.this, "Riwayat Verifikasi");

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");

        ConfigAds.banner(RiwayatVerifikasiActivity.this, adView);

        getRiwayatSPK();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                riwayatVerifikasiSPKAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getRiwayatSPK() {
        ProgressDialog progressDialog = new ProgressDialog(RiwayatVerifikasiActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.riwayatSPKKasub(token, npp)
                .enqueue(new Callback<VerifikatorRootModel>() {
                    @Override
                    public void onResponse(Call<VerifikatorRootModel> call, Response<VerifikatorRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            dataItem = response.body().getData();
                            riwayatVerifikasiSPKAdapter = new RiwayatVerifikasiSPKAdapter(RiwayatVerifikasiActivity.this, dataItem);
                            rv.setLayoutManager(new LinearLayoutManager(RiwayatVerifikasiActivity.this));
                            rv.setAdapter(riwayatVerifikasiSPKAdapter);
                            riwayatVerifikasiSPKAdapter.notifyDataSetChanged();
                        } else {
                            progressDialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifikatorRootModel> call, Throwable t) {
                        progressDialog.cancel();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RiwayatVerifikasiActivity.this.finish();
    }

    private void initView() {
        edtSearch = findViewById(R.id.edt_search);
        rv = findViewById(R.id.rv);
        btnHalamanUtama = findViewById(R.id.btn_halaman_utama);
        adView = findViewById(R.id.adView);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
    }
}