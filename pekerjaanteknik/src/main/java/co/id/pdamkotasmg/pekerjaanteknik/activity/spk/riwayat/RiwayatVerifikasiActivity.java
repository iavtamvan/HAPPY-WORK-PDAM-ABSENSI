package co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat;

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

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.RiwayatVerifikasiSPKAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.verifikator.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.verifikator.VerifikatorRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import co.id.pdamkotasmg.pekerjaanteknik.utils.ConfigAds;
import co.id.pdamkotasmg.pekerjaanteknik.utils.KeadaanDaftar;
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
    private KeadaanDaftar keadaan;
    private Button btnHalamanUtama;
    private AdView adView;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_verifikasi);
        
        initView();
        keadaan = new KeadaanDaftar(findViewById(R.id.div_keadaan_daftar));
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
                // Adapter baru dibuat setelah respons server datang, jadi mengetik
                // saat daftar belum termuat sebelumnya menutup aplikasi.
                if (riwayatVerifikasiSPKAdapter != null) {
                    riwayatVerifikasiSPKAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getRiwayatSPK() {
        keadaan.siapMemuat();
        ProgressDialog progressDialog = new ProgressDialog(RiwayatVerifikasiActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.riwayatSPKKasub(token, npp)
                .enqueue(new Callback<VerifikatorRootModel>() {
                    @Override
                    public void onResponse(Call<VerifikatorRootModel> call, Response<VerifikatorRootModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            progressDialog.cancel();
                            dataItem = response.body().getData();
                            riwayatVerifikasiSPKAdapter = new RiwayatVerifikasiSPKAdapter(RiwayatVerifikasiActivity.this, dataItem);
                            rv.setLayoutManager(new LinearLayoutManager(RiwayatVerifikasiActivity.this));
                            rv.setAdapter(riwayatVerifikasiSPKAdapter);
                            riwayatVerifikasiSPKAdapter.notifyDataSetChanged();
                            keadaan.pantau(riwayatVerifikasiSPKAdapter, edtSearch, R.drawable.ic_pt_clipboard_check,
                                    "Belum ada riwayat verifikasi", "SPK yang sudah diverifikasi akan muncul di sini.");
                        } else {
                            progressDialog.cancel();
                            // Dulu diam saja, sehingga layar putih tanpa penjelasan.
                            keadaan.tampilkanGagal(KeadaanDaftar.pesanGagalServer(response.code()),
                                    RiwayatVerifikasiActivity.this::getRiwayatSPK);
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifikatorRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        keadaan.tampilkanGagal(KeadaanDaftar.PESAN_GAGAL_KONEKSI, RiwayatVerifikasiActivity.this::getRiwayatSPK);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // Tanpa destroy(), AdView terus menyegarkan iklan di latar dan
        // menahan Activity beserta WebView internalnya di memori.
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
