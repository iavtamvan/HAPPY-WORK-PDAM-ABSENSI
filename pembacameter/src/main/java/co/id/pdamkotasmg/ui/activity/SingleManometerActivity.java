package co.id.pdamkotasmg.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.goodday.utils.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.id.pdamkotasmg.pembacameter.databinding.ActivitySingleManometerBinding;
import pl.aprilapps.easyphotopicker.EasyImage;

public class SingleManometerActivity extends AppCompatActivity {
    private ActivitySingleManometerBinding binding;
    private final String TAG = "debug";

    private EasyImage easyImage;
    private File compressedImageFile1;
    private SharedPreferences sp;
    private SharedPreferences.Editor editorSp;

    private Date cDate;
    private String currentDateLocal;

    private String token;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivitySingleManometerBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.ivHeaderBackArrow.setOnClickListener(view -> SingleManometerActivity.this.finish());
        binding.tvHeaderJudul.setText("Input Bacaan Manometer");

        sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sp.getString(Config.SHARED_NAME_DASHBOARD, "");

        cDate = new Date();
        currentDateLocal = new SimpleDateFormat("yyyyMM").format(cDate);

        binding.tvPeriodeManometer.setText(currentDateLocal);
        binding.tvEksekutorManometer.setText(name);

    }
}