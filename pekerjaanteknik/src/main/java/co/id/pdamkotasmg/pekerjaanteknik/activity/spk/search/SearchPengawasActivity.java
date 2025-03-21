package co.id.pdamkotasmg.pekerjaanteknik.activity.spk.search;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.BarangDikerjakanAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.BarangTambahanAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.PengawasAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.SPKSebelumnyaAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.ZonaAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.barang.BarangRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.pegawai.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.pegawai.PegawaiRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.spk.SPKSebelumRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.zona.ZonaRootModelItem;
import co.id.pdamkotasmg.pekerjaanteknik.utils.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPengawasActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private String token;
    private int reqCode;

    private List<DataItem> dataItem = new ArrayList<>();
    private PengawasAdapter pengawasAdapter;

    private List<co.id.pdamkotasmg.pekerjaanteknik.model.barang.DataItem> dataItemBarang = new ArrayList<>();
    private BarangDikerjakanAdapter barangDikerjakanAdapter;
    private BarangTambahanAdapter barangTambahanAdapter;

    private List<co.id.pdamkotasmg.pekerjaanteknik.model.spk.DataItem> dataItemSPK = new ArrayList<>();
    private SPKSebelumnyaAdapter spkSebelumnyaAdapter;

    private ArrayList<ZonaRootModelItem> zonaRootModelItems = new ArrayList<>();
    private ZonaAdapter zonaAdapter;

    private Button btnCari;
    private SwipeRefreshLayout refreshCity;
    private RecyclerView rvCity;
    private EditText edtSearch;
    private TextView tvJudul;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pengawas);
        getSupportActionBar().hide();
        initView();
        
        ivHeaderBackArrow.setOnClickListener(view -> {
            SearchPengawasActivity.this.finish();
        });
        
        ivHeaderInfo.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        reqCode = getIntent().getIntExtra("requestCode", 0);

        tvJudul.setVisibility(View.VISIBLE);
        if (reqCode == 8) {
            tvHeaderJudul.setText("Pilih Zona");
            tvJudul.setText("Cari berdasarkan Nama ZOna");
        } else if (reqCode == 2) {
            tvHeaderJudul.setText("Pilih Pengawas");
            tvJudul.setText("Cari berdasarkan Nama");
        } else if (reqCode == 3) {
            tvHeaderJudul.setText("Pilih Kasub");
            tvJudul.setText("Cari berdasarkan Nama");
        } else if (reqCode == 4) {
            tvHeaderJudul.setText("Pilih Barang yang dikerjakan");
            tvJudul.setText("Cari berdasarkan Nama Barang");
        } else if (reqCode == 5) {
            tvHeaderJudul.setText("Pilih No SPK Sebelumnya");
            tvJudul.setText("Cari berdasarkan NO SPK");
        } else if (reqCode == 6) {
            tvHeaderJudul.setText("Pilih Barang Tambahan");
            tvJudul.setText("Cari berdasarkan Nama Barang");
        }

        btnCari.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(SearchPengawasActivity.this);
            progressDialog.setMessage("Mohon tunggu ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            if (reqCode == 2) {
                ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                apiService.searchPegawai(token, "", edtSearch.getText().toString().trim())
                        .enqueue(new Callback<PegawaiRootModel>() {
                            @Override
                            public void onResponse(Call<PegawaiRootModel> call, Response<PegawaiRootModel> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.cancel();
                                    dataItem = response.body().getData();
                                    pengawasAdapter = new PengawasAdapter(SearchPengawasActivity.this, dataItem);
                                    rvCity.setAdapter(pengawasAdapter);
                                    rvCity.setLayoutManager(new LinearLayoutManager(SearchPengawasActivity.this));
                                    pengawasAdapter.notifyDataSetChanged();
                                    if (dataItem.isEmpty()) {
                                        Toast.makeText(SearchPengawasActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SearchPengawasActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                    rvCity.setVisibility(View.GONE);
                                    progressDialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<PegawaiRootModel> call, Throwable t) {
                                progressDialog.cancel();
                                Toast.makeText(SearchPengawasActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (reqCode == 3) {
                ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                apiService.searchPegawai(token, "", edtSearch.getText().toString().trim())
                        .enqueue(new Callback<PegawaiRootModel>() {
                            @Override
                            public void onResponse(Call<PegawaiRootModel> call, Response<PegawaiRootModel> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.cancel();
                                    dataItem = response.body().getData();
                                    pengawasAdapter = new PengawasAdapter(SearchPengawasActivity.this, dataItem);
                                    rvCity.setAdapter(pengawasAdapter);
                                    rvCity.setLayoutManager(new LinearLayoutManager(SearchPengawasActivity.this));
                                    pengawasAdapter.notifyDataSetChanged();
                                    if (dataItem.isEmpty()) {
                                        Toast.makeText(SearchPengawasActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SearchPengawasActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                    rvCity.setVisibility(View.GONE);
                                    progressDialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<PegawaiRootModel> call, Throwable t) {
                                progressDialog.cancel();
                                Toast.makeText(SearchPengawasActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (reqCode == 4) {
                ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                apiService.listBarang(token, edtSearch.getText().toString().trim())
                        .enqueue(new Callback<BarangRootModel>() {
                            @Override
                            public void onResponse(Call<BarangRootModel> call, Response<BarangRootModel> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.cancel();
                                    dataItemBarang = response.body().getData();
                                    barangDikerjakanAdapter = new BarangDikerjakanAdapter(SearchPengawasActivity.this, dataItemBarang);
                                    rvCity.setAdapter(barangDikerjakanAdapter);
                                    rvCity.setLayoutManager(new LinearLayoutManager(SearchPengawasActivity.this));
                                    barangDikerjakanAdapter.notifyDataSetChanged();
                                    if (dataItemBarang.isEmpty()) {
                                        Toast.makeText(SearchPengawasActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    rvCity.setVisibility(View.GONE);
                                    progressDialog.cancel();
                                    Toast.makeText(SearchPengawasActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<BarangRootModel> call, Throwable t) {
                                progressDialog.cancel();
                                Toast.makeText(SearchPengawasActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (reqCode == 5) {
                ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                apiService.searchNoSPK(token, edtSearch.getText().toString().trim())
                        .enqueue(new Callback<SPKSebelumRootModel>() {
                            @Override
                            public void onResponse(Call<SPKSebelumRootModel> call, Response<SPKSebelumRootModel> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.cancel();
                                    dataItemSPK = response.body().getData();
                                    spkSebelumnyaAdapter = new SPKSebelumnyaAdapter(SearchPengawasActivity.this, dataItemSPK);
                                    rvCity.setAdapter(spkSebelumnyaAdapter);
                                    rvCity.setLayoutManager(new LinearLayoutManager(SearchPengawasActivity.this));
                                    spkSebelumnyaAdapter.notifyDataSetChanged();
                                    if (dataItemSPK.isEmpty()) {
                                        Toast.makeText(SearchPengawasActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    rvCity.setVisibility(View.GONE);
                                    progressDialog.cancel();
                                    Toast.makeText(SearchPengawasActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SPKSebelumRootModel> call, Throwable t) {
                                progressDialog.cancel();
                                Toast.makeText(SearchPengawasActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (reqCode == 6) {
                ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                apiService.listBarang(token, edtSearch.getText().toString().trim())
                        .enqueue(new Callback<BarangRootModel>() {
                            @Override
                            public void onResponse(Call<BarangRootModel> call, Response<BarangRootModel> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.cancel();
                                    dataItemBarang = response.body().getData();
                                    barangTambahanAdapter = new BarangTambahanAdapter(SearchPengawasActivity.this, dataItemBarang);
                                    rvCity.setAdapter(barangTambahanAdapter);
                                    rvCity.setLayoutManager(new LinearLayoutManager(SearchPengawasActivity.this));
                                    barangTambahanAdapter.notifyDataSetChanged();
                                    if (dataItemBarang.isEmpty()) {
                                        Toast.makeText(SearchPengawasActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    rvCity.setVisibility(View.GONE);
                                    progressDialog.cancel();
                                    Toast.makeText(SearchPengawasActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<BarangRootModel> call, Throwable t) {
                                progressDialog.cancel();
                                Toast.makeText(SearchPengawasActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (reqCode == 8) {
                ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                apiService.searchZona(token, edtSearch.getText().toString().trim())
                        .enqueue(new Callback<ArrayList<ZonaRootModelItem>>() {
                            @Override
                            public void onResponse(Call<ArrayList<ZonaRootModelItem>> call, Response<ArrayList<ZonaRootModelItem>> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.cancel();
                                    zonaRootModelItems = response.body();
                                    zonaAdapter = new ZonaAdapter(SearchPengawasActivity.this, zonaRootModelItems);
                                    rvCity.setAdapter(zonaAdapter);
                                    rvCity.setLayoutManager(new LinearLayoutManager(SearchPengawasActivity.this));
                                    zonaAdapter.notifyDataSetChanged();
                                } else {
                                    rvCity.setVisibility(View.GONE);
                                    progressDialog.cancel();
                                    Toast.makeText(SearchPengawasActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<ZonaRootModelItem>> call, Throwable t) {
                                progressDialog.cancel();
                                Toast.makeText(SearchPengawasActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } else if (reqCode == 9) {
                ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                apiService.searchPegawai(token, "", edtSearch.getText().toString().trim())
                        .enqueue(new Callback<PegawaiRootModel>() {
                            @Override
                            public void onResponse(Call<PegawaiRootModel> call, Response<PegawaiRootModel> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.cancel();
                                    dataItem = response.body().getData();
                                    pengawasAdapter = new PengawasAdapter(SearchPengawasActivity.this, dataItem);
                                    rvCity.setAdapter(pengawasAdapter);
                                    rvCity.setLayoutManager(new LinearLayoutManager(SearchPengawasActivity.this));
                                    pengawasAdapter.notifyDataSetChanged();
                                    if (dataItem.isEmpty()) {
                                        Toast.makeText(SearchPengawasActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SearchPengawasActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                    rvCity.setVisibility(View.GONE);
                                    progressDialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<PegawaiRootModel> call, Throwable t) {
                                progressDialog.cancel();
                                Toast.makeText(SearchPengawasActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
//            else if (reqCode == 7){
//                ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
//                apiService.getCallCenterAduan(token, edtSearch.getText().toString().trim())
//                        .enqueue(new Callback<CallCenterAduanRootModel>() {
//                            @Override
//                            public void onResponse(Call<CallCenterAduanRootModel> call, Response<CallCenterAduanRootModel> response) {
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<CallCenterAduanRootModel> call, Throwable t) {
//
//                            }
//                        });
//            }

        });

    }

//    @Override
//    public void onBackPressed() {
//        if (reqCode == 1) {
//            Toast.makeText(this, "Anda harus memilih Zona terlebih dahulu", Toast.LENGTH_SHORT).show();
//        } else if (reqCode == 2) {
//            Toast.makeText(this, "Anda harus memilih Pengawas terlebih dahulu", Toast.LENGTH_SHORT).show();
//        } else if (reqCode == 3) {
//            Toast.makeText(this, "Anda harus memilih Kasub terlebih dahulu", Toast.LENGTH_SHORT).show();
//        } else if (reqCode == 4) {
//            Toast.makeText(this, "Anda harus memilih Barang yang dikerjakan terlebih dahulu", Toast.LENGTH_SHORT).show();
//        } else if (reqCode == 5) {
//            Toast.makeText(this, "Anda harus memilih No SPK Sebelumnya terlebih dahulu", Toast.LENGTH_SHORT).show();
//        } else if (reqCode == 6) {
//            Toast.makeText(this, "Anda harus memilih Barang Tambahan terlebih dahulu", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void initView() {
        btnCari = findViewById(R.id.btn_cari);
        refreshCity = findViewById(R.id.refreshCity);
        rvCity = findViewById(R.id.rvCity);
        edtSearch = findViewById(R.id.edt_search);
        tvJudul = findViewById(R.id.tv_judul);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
    }
}