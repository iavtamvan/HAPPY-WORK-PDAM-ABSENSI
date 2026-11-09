package co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.DaftarPerbaikanCCAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.diposisi.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.diposisi.DisposisiRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import co.id.pdamkotasmg.pekerjaanteknik.utils.KeadaanDaftar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatAmbilPekerjaanActivity extends AppCompatActivity {
    private List<DataItem> dataItemsDisposisi;
    private DaftarPerbaikanCCAdapter daftarPerbaikanCCAdapter;

    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    public String accessToken;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private RecyclerView rv;
    private KeadaanDaftar keadaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_ambil_pekerjaan);
        

        initView();
        keadaan = new KeadaanDaftar(findViewById(R.id.div_keadaan_daftar));
        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, RiwayatAmbilPekerjaanActivity.this, "Riwayat Ambil Pekerjaan");

        progressDialog = new ProgressDialog(RiwayatAmbilPekerjaanActivity.this);
        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        getRiwayatAmbilPekerjaan();
    }

    public void getRiwayatAmbilPekerjaan() {
        keadaan.siapMemuat();
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.getDataCallCenterAduanByTenagaNPP(accessToken)
                .enqueue(new Callback<DisposisiRootModel>() {
                    @Override
                    public void onResponse(Call<DisposisiRootModel> call, Response<DisposisiRootModel> response) {
                        // Ditutup lebih dulu. Sebelumnya hanya ditutup pada jalur sukses,
                        // sehingga respons gagal membuat dialog menggantung di layar.
                        progressDialog.cancel();
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            dataItemsDisposisi = new ArrayList<>();
                            dataItemsDisposisi = response.body().getData();
                            daftarPerbaikanCCAdapter = new DaftarPerbaikanCCAdapter(RiwayatAmbilPekerjaanActivity.this, dataItemsDisposisi);
                            rv.setLayoutManager(new LinearLayoutManager(RiwayatAmbilPekerjaanActivity.this));
                            rv.setAdapter(daftarPerbaikanCCAdapter);
                            daftarPerbaikanCCAdapter.notifyDataSetChanged();
                            keadaan.pantau(daftarPerbaikanCCAdapter, null, R.drawable.ic_pt_bookmark,
                                    "Belum ada pekerjaan yang diambil",
                                    "Aduan Call Center yang Anda ambil (booking) akan muncul di sini.");
                        } else if (response.isSuccessful()) {
                            // Server menjawab tanpa isi data: sama dengan belum ada pekerjaan.
                            keadaan.tampilkan(R.drawable.ic_pt_bookmark, "Belum ada pekerjaan yang diambil",
                                    "Aduan Call Center yang Anda ambil (booking) akan muncul di sini.");
                        } else {
                            keadaan.tampilkanGagal(KeadaanDaftar.pesanGagalServer(response.code()),
                                    RiwayatAmbilPekerjaanActivity.this::getRiwayatAmbilPekerjaan);
                        }
                    }

                    @Override
                    public void onFailure(Call<DisposisiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        keadaan.tampilkanGagal(KeadaanDaftar.PESAN_GAGAL_KONEKSI,
                                RiwayatAmbilPekerjaanActivity.this::getRiwayatAmbilPekerjaan);
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        rv = findViewById(R.id.rv);
    }
}