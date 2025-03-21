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
import android.widget.Toast;

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
import co.id.pdamkotasmg.pekerjaanteknik.utils.Config;
import co.id.pdamkotasmg.pekerjaanteknik.utils.ConfigAds;
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
    private EditText edtSearch;
    private AdView adView;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_call_center);
        getSupportActionBar().hide();
        initView();

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
                daftarPerbaikanCCAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void getDataDisposisi() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.getCallCenterAduanByDisposisi(accessToken, satkerCabangID, kategoriID)
                .enqueue(new Callback<DisposisiRootModel>() {
                    @Override
                    public void onResponse(Call<DisposisiRootModel> call, Response<DisposisiRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            dataItemsDisposisi = new ArrayList<>();
                            dataItemsDisposisi = response.body().getData();
                            daftarPerbaikanCCAdapter = new DaftarPerbaikanCCAdapter(DaftarCallCenterActivity.this, dataItemsDisposisi);
                            rv.setLayoutManager(new LinearLayoutManager(DaftarCallCenterActivity.this));
                            rv.setAdapter(daftarPerbaikanCCAdapter);
                            daftarPerbaikanCCAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(Call<DisposisiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DaftarCallCenterActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getSpinner() {
        Log.d(TAG, "token : " + accessToken);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.getMastCCSatkerdanKategori(accessToken)
                .enqueue(new Callback<MastSatkerdanKategoriRootModel>() {
                    @Override
                    public void onResponse(Call<MastSatkerdanKategoriRootModel> call, Response<MastSatkerdanKategoriRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
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
                        }
                    }

                    @Override
                    public void onFailure(Call<MastSatkerdanKategoriRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DaftarCallCenterActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataDisposisi();
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
}