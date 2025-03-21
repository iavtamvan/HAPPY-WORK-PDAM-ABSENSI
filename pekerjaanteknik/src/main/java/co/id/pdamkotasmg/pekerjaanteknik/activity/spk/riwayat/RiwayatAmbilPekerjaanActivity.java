package co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import co.id.pdamkotasmg.pekerjaanteknik.utils.Config;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_ambil_pekerjaan);
        getSupportActionBar().hide();

        initView();
        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, RiwayatAmbilPekerjaanActivity.this, "Riwayat Ambil Pekerjaan");

        progressDialog = new ProgressDialog(RiwayatAmbilPekerjaanActivity.this);
        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        getRiwayatAmbilPekerjaan();
    }

    public void getRiwayatAmbilPekerjaan() {
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.getDataCallCenterAduanByTenagaNPP(accessToken)
                .enqueue(new Callback<DisposisiRootModel>() {
                    @Override
                    public void onResponse(Call<DisposisiRootModel> call, Response<DisposisiRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            dataItemsDisposisi = new ArrayList<>();
                            dataItemsDisposisi = response.body().getData();
                            daftarPerbaikanCCAdapter = new DaftarPerbaikanCCAdapter(RiwayatAmbilPekerjaanActivity.this, dataItemsDisposisi);
                            rv.setLayoutManager(new LinearLayoutManager(RiwayatAmbilPekerjaanActivity.this));
                            rv.setAdapter(daftarPerbaikanCCAdapter);
                            daftarPerbaikanCCAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<DisposisiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(RiwayatAmbilPekerjaanActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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