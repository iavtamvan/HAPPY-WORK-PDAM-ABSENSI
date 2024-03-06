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

import co.id.pdamkotasmg.adapter.RiwayatBacaMeterAdapter;
import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.riwayatBacaMeter.RiwayatBacaMeterRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityRiwayatPembacaMeterBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPembacaMeterActivity extends AppCompatActivity {

    private ActivityRiwayatPembacaMeterBinding binding;
//    private ContentHeaderBinding contentHeaderBinding;

    private RiwayatBacaMeterAdapter riwayatBacaMeterAdapter;
    private ProgressDialog progressDialog;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityRiwayatPembacaMeterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.ivHeaderBackArrow.setOnClickListener(view -> RiwayatPembacaMeterActivity.this.finish());
        binding.tvHeaderJudul.setText("Riwayat Bacaan");
        binding.ivHeaderInfo.setVisibility(View.GONE);

        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");

        progressDialog = new ProgressDialog(RiwayatPembacaMeterActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);

        binding.btnCari.setOnClickListener(view -> {
            getDataRiwayatBacaMeter();
        });

        getDataRiwayatBacaMeter();
    }

    private void getDataRiwayatBacaMeter() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(RiwayatPembacaMeterActivity.this);
        apiService.getRiwayatBacaMeter(token, binding.edtBendel.getText().toString().trim(), binding.edtNolangg.getText().toString().trim())
                .enqueue(new Callback<RiwayatBacaMeterRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatBacaMeterRootModel> call, Response<RiwayatBacaMeterRootModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData().isEmpty()) {
                                progressDialog.cancel();
                                Toast.makeText(RiwayatPembacaMeterActivity.this, Config.ERROR_NULL_DATA, Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.cancel();
                                binding.tvTotalDataBendel.setText(response.body().getData().size() + " Data");
                                riwayatBacaMeterAdapter = new RiwayatBacaMeterAdapter(RiwayatPembacaMeterActivity.this, response.body().getData());
                                binding.rv.setLayoutManager(new LinearLayoutManager(RiwayatPembacaMeterActivity.this));
                                binding.rv.setAdapter(riwayatBacaMeterAdapter);
                                riwayatBacaMeterAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatBacaMeterRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(RiwayatPembacaMeterActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}