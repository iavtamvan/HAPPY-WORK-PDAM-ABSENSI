package com.pdamkotasmg.goodday.fitur.menuLainnya;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.pdamkotasmg.goodday.BuildConfig;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.utils.Config;

public class ListWebViewActivity extends AppCompatActivity {

    private String typeWebView;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private CardView divGisTirta;
    private CardView divSambungBaru;
    private CardView divWablast;
    private CardView divSurveyPelanggan;
    private CardView divPekerjaanTeknik;
    private CardView divDataPelanggan;
    private String nolangg;
    private String TAG = "debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_web_view);
        
        initView();

        tvHeaderJudul.setText("Menu Lainnya");
        ivHeaderBackArrow.setOnClickListener(view -> {
            ListWebViewActivity.this.finish();
        });
        ivHeaderInfo.setVisibility(View.GONE);

        divGisTirta.setOnClickListener(view -> {
            typeWebView = "gistirta";
            saveShared(typeWebView);
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        });

        divSambungBaru.setOnClickListener(view -> {
            typeWebView = "sambungbaru";
            saveShared(typeWebView);
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        });

        divWablast.setOnClickListener(view -> {
            typeWebView = "wablast";
            saveShared(typeWebView);
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        });

        divSurveyPelanggan.setOnClickListener(view -> {
            typeWebView = "surveyplg";
            saveShared(typeWebView);
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        });

        divPekerjaanTeknik.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClassName(this, "co.id.pdamkotasmg.pekerjaanteknik.activity.home.HomeSPKActivity");
            startActivity(intent);
            Log.d(TAG, "onCreate: klik");
        });

        divDataPelanggan.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListWebViewActivity.this);
            builder.setMessage("Nolangg");

            final EditText input = new EditText(ListWebViewActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                nolangg = input.getText().toString();
                if (nolangg.isEmpty()) {
                    Toast.makeText(ListWebViewActivity.this, "Isi nolangg", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ListWebViewActivity.this, ProfilePelangganDanTagihanActivity.class);
                    intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, nolangg);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

    }

    private void saveShared(String typeWebView) {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SHARED_TYPE_WEB_vIEW, typeWebView);
        editor.apply();
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divGisTirta = findViewById(R.id.div_gis_tirta);
        divSambungBaru = findViewById(R.id.div_sambung_baru);
        divWablast = findViewById(R.id.div_wablast);
        divSurveyPelanggan = findViewById(R.id.div_survey_pelanggan);
        divPekerjaanTeknik = findViewById(R.id.div_pekerjaan_teknik);
        divDataPelanggan = findViewById(R.id.div_data_pelanggan);
    }
}