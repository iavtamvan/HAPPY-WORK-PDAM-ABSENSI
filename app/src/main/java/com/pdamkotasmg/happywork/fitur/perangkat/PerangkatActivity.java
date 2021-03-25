package com.pdamkotasmg.happywork.fitur.perangkat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.happywork.BuildConfig;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.perangkat.controller.PerangkatController;
import com.pdamkotasmg.happywork.fitur.profil.ProfileActivity;
import com.pdamkotasmg.happywork.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class PerangkatActivity extends AppCompatActivity {

    private PerangkatController perangkatController;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvVersionAplication;
    private TextView tvNameNamaHp;
    private TextView tvIpCity;
    private TextView tvStatusOnline;
    private TextView tvDeleteAllSession;
    private RecyclerView rvPerangkatTersambung;
    private String TAG = "debug";
    private String city;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perangkat);
        getSupportActionBar().hide();
        initView();
        tvHeaderJudul.setText("Perangkat");
        perangkatController = new PerangkatController();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        ivHeaderBackArrow.setOnClickListener(v -> {
            finishAffinity();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        });
        ivHeaderInfo.setVisibility(View.GONE);
        tvDeleteAllSession.setOnClickListener(v -> {
            MaterialDialog mDialog = new MaterialDialog.Builder(PerangkatActivity.this)
                    .setTitle("Yakin mau hapus seluruh sesi?")
                    .setMessage("Kamu akan menghapus seluruh sesi versi dulu dan sekarang")
                    .setAnimation("lt_delete.json")
                    .setCancelable(false)
                    .setNegativeButton("Gak jadi", new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("Hapus aja", new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            perangkatController.deleteAllSession(PerangkatActivity.this);
                            // TODO (perangkat) LOGOUT SEMUA SESI TERMASUK YG ONLINE KEMUDIAN KEMBALI KE APLIKASI LAGI
                        }
                    })
                    .build();

            // Show Dialog
            mDialog.show();
            
        });
        tvVersionAplication.setText(Config.APLICATION_NAME + " v." + BuildConfig.VERSION_NAME);
        tvNameNamaHp.setText(sharedPreferences.getString(Config.SHARED_GETBUILDBRAND, "").toUpperCase() + ", Android (" + sharedPreferences.getString(Config.SHARED_GETOSVERSION, "") + ")");
        city = sharedPreferences.getString(Config.SHARED_CITY, "");
        Log.d(TAG, "onCreate: " + city);
//        if (city.equalsIgnoreCase(null)){
//            city = "Tidak diketahui";
        tvIpCity.setText(sharedPreferences.getString(Config.SHARED_IPADRESS, "") + " - " + sharedPreferences.getString(Config.SHARED_CITY, ""));
//        }
        perangkatController.perangkatOnline(PerangkatActivity.this, rvPerangkatTersambung);
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvVersionAplication = findViewById(R.id.tv_version_aplication);
        tvNameNamaHp = findViewById(R.id.tv_name_nama_hp);
        tvIpCity = findViewById(R.id.tv_ip_city);
        tvStatusOnline = findViewById(R.id.tv_status_online);
        rvPerangkatTersambung = findViewById(R.id.rv_perangkat_tersambung);
        tvDeleteAllSession = findViewById(R.id.tv_delete_all_session);
    }
}