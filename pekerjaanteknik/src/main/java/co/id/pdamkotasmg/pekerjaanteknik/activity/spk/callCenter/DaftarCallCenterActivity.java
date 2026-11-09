package co.id.pdamkotasmg.pekerjaanteknik.activity.spk.callCenter;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.DaftarPerbaikanCCAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.diposisi.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.diposisi.DisposisiRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.mastCCSatker.MCcSatkerItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.mastCCSatker.MKategoriItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.mastCCSatker.MastSatkerdanKategoriRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import co.id.pdamkotasmg.pekerjaanteknik.utils.ConfigAds;
import co.id.pdamkotasmg.pekerjaanteknik.utils.KeadaanDaftar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarCallCenterActivity extends AppCompatActivity {

    private final String TAG = "debug";
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    public String accessToken;

    private List<DataItem> dataItemsDisposisi;
    private DaftarPerbaikanCCAdapter daftarPerbaikanCCAdapter;

    private ArrayList<String> arrayTipeMastSatker = new ArrayList<>();
    private ArrayList<String> arrayTipeMastSatkerId = new ArrayList<String>();
    private String satkerCabangID;
    private String satkerCabangString;
    private List<MCcSatkerItem> dataItemsMCcSatkerItems;
    private MaterialSpinner spnCabang;
    private String kategoriID;
    private String kategoriString;
    private List<MKategoriItem> dataitemMcKategori;
    private MaterialSpinner spnKategori;
    private ArrayList<String> arrayTipeMastkategori = new ArrayList<>();
    private ArrayList<String> arrayTipeMastkategoriId = new ArrayList<String>();
    private RecyclerView rv;
    private KeadaanDaftar keadaan;
    private EditText edtSearch;
    private AdView adView;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_call_center);
        
        initView();
        keadaan = new KeadaanDaftar(findViewById(R.id.div_keadaan_daftar));

        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, DaftarCallCenterActivity.this, "Data Call Center");

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        progressDialog = new ProgressDialog(DaftarCallCenterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu...");

        ConfigAds.banner(DaftarCallCenterActivity.this, adView);

        getSpinner();

        spnCabang.setOnItemSelectedListener((view, position, id, item) -> {
            satkerCabangID = dataItemsMCcSatkerItems.get(position).getIdsatkerCbg();
            satkerCabangString = dataItemsMCcSatkerItems.get(position).getNamaCc();
            Log.d(TAG, "Sakter : " + satkerCabangString + " id " + satkerCabangID);

            getDataDisposisi();
        });

        spnKategori.setOnItemSelectedListener((view, position, id, item) -> {
            kategoriID = dataitemMcKategori.get(position).getId();
            kategoriString = dataitemMcKategori.get(position).getNamaKat();
            Log.d(TAG, "Sakter : " + kategoriID + " id " + kategoriString);

            getDataDisposisi();
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Adapter baru dibuat setelah respons server datang, jadi mengetik
                // saat daftar belum termuat sebelumnya menutup aplikasi.
                if (daftarPerbaikanCCAdapter != null) {
                    daftarPerbaikanCCAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void getDataDisposisi() {
        keadaan.siapMemuat();
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.getCallCenterAduanByDisposisi(accessToken, satkerCabangID, kategoriID)
                .enqueue(new Callback<DisposisiRootModel>() {
                    @Override
                    public void onResponse(Call<DisposisiRootModel> call, Response<DisposisiRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful() && response.body() != null) {
                            dataItemsDisposisi = new ArrayList<>();
                            dataItemsDisposisi = response.body().getData();
                            daftarPerbaikanCCAdapter = new DaftarPerbaikanCCAdapter(DaftarCallCenterActivity.this, dataItemsDisposisi);
                            rv.setLayoutManager(new LinearLayoutManager(DaftarCallCenterActivity.this));
                            rv.setAdapter(daftarPerbaikanCCAdapter);
                            daftarPerbaikanCCAdapter.notifyDataSetChanged();
                            keadaan.pantau(daftarPerbaikanCCAdapter, edtSearch, R.drawable.ic_pt_headset,
                                    "Tidak ada aduan", "Belum ada aduan Call Center untuk work order dan kategori ini.");
                        } else {
                            // Dulu diam saja, sehingga daftar kosong tanpa penjelasan.
                            keadaan.tampilkanGagal(KeadaanDaftar.pesanGagalServer(response.code()),
                                    DaftarCallCenterActivity.this::getDataDisposisi);
                        }
                    }

                    @Override
                    public void onFailure(Call<DisposisiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        keadaan.tampilkanGagal(KeadaanDaftar.PESAN_GAGAL_KONEKSI, DaftarCallCenterActivity.this::getDataDisposisi);
                    }
                });
    }

    /** Aduan baru dimuat setelah filter dipilih, jadi keadaan awalnya berupa petunjuk. */
    private void tampilkanPetunjukFilter() {
        keadaan.tampilkan(R.drawable.ic_pt_headset, "Pilih filter dulu",
                "Pilih work order (disposisi) dan kategori untuk menampilkan aduan Call Center.");
    }

    private void getSpinner() {
        Log.d(TAG, "token : " + accessToken);
        keadaan.siapMemuat();
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.getMastCCSatkerdanKategori(accessToken)
                .enqueue(new Callback<MastSatkerdanKategoriRootModel>() {
                    @Override
                    public void onResponse(Call<MastSatkerdanKategoriRootModel> call, Response<MastSatkerdanKategoriRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            dataItemsMCcSatkerItems = new ArrayList<>();
                            dataItemsMCcSatkerItems = response.body().getData().getMCcSatker();
                            for (int i = 0; i < dataItemsMCcSatkerItems.size(); i++) {
                                String idsatkerCbg = dataItemsMCcSatkerItems.get(i).getIdsatkerCbg();
                                String satkerCbg = dataItemsMCcSatkerItems.get(i).getNamaCc();
                                arrayTipeMastSatkerId.add(idsatkerCbg);
                                arrayTipeMastSatker.add(satkerCbg);
                            }
                            Log.d(TAG, "onResponse: " + arrayTipeMastSatker);
                            spnCabang.setItems(arrayTipeMastSatker);

                            dataitemMcKategori = new ArrayList<>();
                            dataitemMcKategori = response.body().getData().getMKategori();
                            for (int i = 0; i < dataitemMcKategori.size(); i++) {
                                String idsatkerCbg = dataitemMcKategori.get(i).getId();
                                String satkerCbg = dataitemMcKategori.get(i).getNamaKat();
                                arrayTipeMastkategoriId.add(idsatkerCbg);
                                arrayTipeMastkategori.add(satkerCbg);
                            }
                            Log.d(TAG, "onResponse: " + arrayTipeMastkategori);
                            spnKategori.setItems(arrayTipeMastkategori);
                            tampilkanPetunjukFilter();
                        } else {
                            // Tanpa daftar work order dan kategori, layar ini tidak bisa dipakai.
                            keadaan.tampilkanGagal(KeadaanDaftar.pesanGagalServer(response.code()),
                                    DaftarCallCenterActivity.this::getSpinner);
                        }
                    }

                    @Override
                    public void onFailure(Call<MastSatkerdanKategoriRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        keadaan.tampilkanGagal(KeadaanDaftar.PESAN_GAGAL_KONEKSI, DaftarCallCenterActivity.this::getSpinner);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        // Dulu selalu memanggil ulang walau filter belum dipilih, sehingga saat
        // layar dibuka ada dua permintaan sekaligus dan satu di antaranya tanpa filter.
        if (satkerCabangID != null) {
            getDataDisposisi();
        }
    }

    private void initView() {
        spnCabang = findViewById(R.id.spn_cabang);
        rv = findViewById(R.id.rv);
        edtSearch = findViewById(R.id.edt_search);
        adView = findViewById(R.id.adView);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        spnKategori = findViewById(R.id.spn_kategori);
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
