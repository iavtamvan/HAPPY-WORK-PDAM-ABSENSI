package co.id.pdamkotasmg.ui.activity.bendel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;

import co.id.pdamkotasmg.adapter.BendelAdapter;
import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.bendel.BendelRootModel;
import co.id.pdamkotasmg.model.bendel.DataItem;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityBendelDataBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BendelDataActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private ActivityBendelDataBinding binding;
    private String codeBendel;
    private String token;
    private String periode;
    private String cabang;
    private SharedPreferences sp;
    private SharedPreferences.Editor editorSp;

    private BendelAdapter bendelAdapter;
    private List<DataItem> dataItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityBendelDataBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editorSp = sp.edit();
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        periode = sp.getString(Config.SHARED_PERIODE, "");
        cabang = sp.getString(Config.SHARED_CABANG, "");

        codeBendel = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_CODE_BENDEL);
        binding.tvBendel.setText("DAFTAR BACAAN METER CABANG " + cabang + " BENDEL " + codeBendel + " PERIODE " + periode);

        binding.btnCari.setOnClickListener(view -> {
            if (binding.edtNolangg.toString().isEmpty()) {
                Toast.makeText(this, "Isi ID Pelanggan", Toast.LENGTH_SHORT).show();
            } else {
                getBendel();
            }
        });
        getBendel();
    }

    private void getBendel() {
        ProgressDialog progressDialog = new ProgressDialog(BendelDataActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this);
        apiService.getBendel(token, codeBendel, binding.edtNolangg.getText().toString().trim()).enqueue(new Callback<BendelRootModel>() {
            @Override
            public void onResponse(Call<BendelRootModel> call, Response<BendelRootModel> response) {
                if (response.isSuccessful()) {
                    dataItems = response.body().getData();
                    binding.tvTotalDataBendel.setText("Data : " + dataItems.size() + " Pelanggan");
                    bendelAdapter = new BendelAdapter(BendelDataActivity.this, dataItems);
                    binding.rv.setAdapter(bendelAdapter);
                    binding.rv.setLayoutManager(new LinearLayoutManager(BendelDataActivity.this));
                    bendelAdapter.notifyDataSetChanged();


//                    Gson gson = new Gson();
//                    String json = gson.toJson(dataItems);
//                    editorSp.putString(Config.SHARED_ARRAY_BENDEL, json);
//                    editorSp.apply();

                    progressDialog.cancel();

                }
            }

            @Override
            public void onFailure(Call<BendelRootModel> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(BendelDataActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBendel();
    }
}