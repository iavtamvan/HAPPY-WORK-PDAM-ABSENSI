package co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import co.id.pdamkotasmg.pekerjaanteknik.activity.home.HomeSPKActivity;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.RiwayatSPKAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.mandor.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.mandor.RiwayatSPKMandorRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import co.id.pdamkotasmg.pekerjaanteknik.utils.ConfigAds;
import co.id.pdamkotasmg.pekerjaanteknik.utils.KeadaanDaftar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatSpkActivity extends AppCompatActivity {

    private String token;
    private String npp;

    private String kdRiwayat;

    private RiwayatSPKAdapter riwayatSPKAdapter;
    private List<DataItem> dataItem = new ArrayList<>();
    private EditText edtSearch;
    private RecyclerView rv;
    private KeadaanDaftar keadaan;
    private AdView adView;
    private Button btnHalamanUtama;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_spk);
        initView();
        keadaan = new KeadaanDaftar(findViewById(R.id.div_keadaan_daftar));
        
        ConfigAds.banner(RiwayatSpkActivity.this, adView);
        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, RiwayatSpkActivity.this, "Riwayat SPK");

        kdRiwayat = getIntent().getStringExtra(Config.BUNDLE_KD_RIWAYAT);
        if (kdRiwayat == null) {
            btnHalamanUtama.setVisibility(View.GONE);
        } else {
//            Config.popUpSucces(RiwayatSpkActivity.this, "Data berhasil disimpan");
            btnHalamanUtama.setVisibility(View.VISIBLE);
        }

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");

        btnHalamanUtama.setOnClickListener(v -> {
//            ConfigAds.interestialIntent(RiwayatSpkActivity.this, HomeActivity.class);
            finishAffinity();
            startActivity(new Intent(RiwayatSpkActivity.this, HomeSPKActivity.class));
        });

        getRiwayatSPK();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Adapter baru dibuat setelah respons server datang, jadi mengetik
                // saat daftar belum termuat sebelumnya menutup aplikasi.
                if (riwayatSPKAdapter != null) {
                    riwayatSPKAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getRiwayatSPK() {
        keadaan.siapMemuat();
        ProgressDialog progressDialog = new ProgressDialog(RiwayatSpkActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.riwayatSPK(token)
                .enqueue(new Callback<RiwayatSPKMandorRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatSPKMandorRootModel> call, Response<RiwayatSPKMandorRootModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            progressDialog.cancel();
                            dataItem = response.body().getData();
                            riwayatSPKAdapter = new RiwayatSPKAdapter(RiwayatSpkActivity.this, dataItem);
                            rv.setLayoutManager(new LinearLayoutManager(RiwayatSpkActivity.this));
                            rv.setAdapter(riwayatSPKAdapter);
                            riwayatSPKAdapter.notifyDataSetChanged();
                            keadaan.pantau(riwayatSPKAdapter, edtSearch, R.drawable.ic_pt_history,
                                    "Belum ada riwayat SPK", "SPK yang Anda simpan akan muncul di sini.");
                        } else {
                            progressDialog.cancel();
                            // Dulu menampilkan isi header HTTP mentah sebagai toast.
                            keadaan.tampilkanGagal(KeadaanDaftar.pesanGagalServer(response.code()),
                                    RiwayatSpkActivity.this::getRiwayatSPK);
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatSPKMandorRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        keadaan.tampilkanGagal(KeadaanDaftar.PESAN_GAGAL_KONEKSI, RiwayatSpkActivity.this::getRiwayatSPK);
                    }
                });
    }

    private void initView() {
        edtSearch = findViewById(R.id.edt_search);
        rv = findViewById(R.id.rv);
        adView = findViewById(R.id.adView);
        btnHalamanUtama = findViewById(R.id.btn_halaman_utama);
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
