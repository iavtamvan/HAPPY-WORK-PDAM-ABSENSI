package co.id.pdamkotasmg.ui.activity.bendel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.pembacameter.databinding.ActivityInputDataBinding;

public class InputDataActivity extends AppCompatActivity {

    private ActivityInputDataBinding binding;
    private String codeInputData;

    private String cabang;
    private String periode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivityInputDataBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        cabang = sharedPreferences.getString(Config.SHARED_CABANG, "");
        periode = sharedPreferences.getString(Config.SHARED_PERIODE, "");

        codeInputData = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA);
        if (codeInputData.contains("1")) {
            binding.divInputBendel.setVisibility(View.VISIBLE);
            binding.tvBendelCabang.setText(cabang);
            binding.tvBendelPeriode.setText(String.valueOf(periode));
        }

        binding.btnBukaData.setOnClickListener(view -> {
            if (codeInputData.contains("1")) {
                String codeBendel = binding.edtBendel.getText().toString();
                if (codeBendel.isEmpty()) {
                    Toast.makeText(this, "Isi bendel", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, BendelDataActivity.class);
                    intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_BENDEL, codeBendel);
                    startActivity(intent);
                }
            }
        });

    }
}