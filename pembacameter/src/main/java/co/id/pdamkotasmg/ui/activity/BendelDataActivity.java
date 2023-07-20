package co.id.pdamkotasmg.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.bendel.BendelRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityBendelDataBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BendelDataActivity extends AppCompatActivity {
    private ActivityBendelDataBinding binding;
    private String codeBendel;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBendelDataBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");

        codeBendel = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_CODE_BENDEL);

        getBendel();

    }

    private void getBendel() {
        ApiService apiService = ApiConfig.getApiService(this);
        apiService.getBendel(token, codeBendel).enqueue(new Callback<BendelRootModel>() {
            @Override
            public void onResponse(Call<BendelRootModel> call, Response<BendelRootModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BendelDataActivity.this, "" + response.body().getData(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BendelRootModel> call, Throwable t) {
                Toast.makeText(BendelDataActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}