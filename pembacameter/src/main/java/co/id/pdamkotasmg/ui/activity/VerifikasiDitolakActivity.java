package co.id.pdamkotasmg.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.adapter.VerifikasiDitolakBacaMeterAdapter;
import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.riwayatBacaMeter.RiwayatBacaMeterRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityVerifikasiDitolakBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiDitolakActivity extends AppCompatActivity {

    private ActivityVerifikasiDitolakBinding binding;
//    private ContentHeaderBinding contentHeaderBinding;
    private VerifikasiDitolakBacaMeterAdapter verifikasiDitolakBacaMeterAdapter;

    private ProgressDialog progressDialog;

    private String token;
    private String nolangg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityVerifikasiDitolakBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

//        contentHeaderBinding = ContentHeaderBinding.inflate(getLayoutInflater());
//        View contentRoot = contentHeaderBinding.getRoot();
//        setContentView(contentRoot);
//        contentHeaderBinding.tvHeaderJudul.setText("Verifikasi Ditolak");
//        contentHeaderBinding.ivHeaderBackArrow.setOnClickListener(view -> VerifikasiDitolakActivity.this.finish());
//        contentHeaderBinding.ivHeaderInfo.setOnClickListener(view -> Toast.makeText(this, "Verifikasi Ditolak", Toast.LENGTH_SHORT).show());

        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        nolangg = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG);

        progressDialog = new ProgressDialog(VerifikasiDitolakActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);

        getDataVerifikasiDitolak();

    }

    private void getDataVerifikasiDitolak() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(VerifikasiDitolakActivity.this);
        apiService.getVerifikasiDitolak(token)
                .enqueue(new Callback<RiwayatBacaMeterRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatBacaMeterRootModel> call, Response<RiwayatBacaMeterRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            verifikasiDitolakBacaMeterAdapter = new VerifikasiDitolakBacaMeterAdapter(VerifikasiDitolakActivity.this, response.body().getData());
                            binding.rv.setAdapter(verifikasiDitolakBacaMeterAdapter);
                            binding.rv.setLayoutManager(new LinearLayoutManager(VerifikasiDitolakActivity.this));
                            verifikasiDitolakBacaMeterAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatBacaMeterRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(VerifikasiDitolakActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataVerifikasiDitolak();
    }
}