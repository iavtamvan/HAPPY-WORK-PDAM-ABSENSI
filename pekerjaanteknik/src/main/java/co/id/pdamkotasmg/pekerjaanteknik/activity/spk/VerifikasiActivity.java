package co.id.pdamkotasmg.pekerjaanteknik.activity.spk;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.VerifikasiSPKAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.verifikator.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.verifikator.VerifikatorRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.utils.KeadaanDaftar;
import com.pdamkotasmg.goodday.utils.Config;
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
    private KeadaanDaftar keadaan;
    private EditText edtSearch;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);
        initView();
        keadaan = new KeadaanDaftar(findViewById(R.id.div_keadaan_daftar));
        
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
                // Adapter baru dibuat setelah respons server datang, jadi mengetik
                // saat daftar belum termuat sebelumnya menutup aplikasi.
                if (verifikasiSPKAdapter != null) {
                    verifikasiSPKAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getListVerifikasi() {
        keadaan.siapMemuat();
        ProgressDialog progressDialog = new ProgressDialog(VerifikasiActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.listVerifSPKVerifikator(token)
                .enqueue(new Callback<VerifikatorRootModel>() {
                    @Override
                    public void onResponse(Call<VerifikatorRootModel> call, Response<VerifikatorRootModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            progressDialog.cancel();
                            dataItem = response.body().getData();
                            verifikasiSPKAdapter = new VerifikasiSPKAdapter(VerifikasiActivity.this, dataItem);
                            rv.setLayoutManager(new LinearLayoutManager(VerifikasiActivity.this));
                            rv.setAdapter(verifikasiSPKAdapter);
                            verifikasiSPKAdapter.notifyDataSetChanged();
                            // Antrean kosong adalah kabar baik, jadi ditampilkan dengan suasana hijau.
                            keadaan.pantau(verifikasiSPKAdapter, edtSearch, R.drawable.ic_pt_check_circle,
                                    "Semua sudah diverifikasi", "Tidak ada SPK yang menunggu verifikasi saat ini.", true);
                        } else {
                            progressDialog.cancel();
                            // Dulu diam saja, sehingga layar putih tanpa penjelasan.
                            keadaan.tampilkanGagal(KeadaanDaftar.pesanGagalServer(response.code()),
                                    VerifikasiActivity.this::getListVerifikasi);
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifikatorRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        keadaan.tampilkanGagal(KeadaanDaftar.PESAN_GAGAL_KONEKSI, VerifikasiActivity.this::getListVerifikasi);
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