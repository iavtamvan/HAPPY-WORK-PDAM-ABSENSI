package com.pdamkotasmg.happywork.fitur.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.marcoscg.fingerauth.FingerAuth;
import com.marcoscg.fingerauth.FingerAuthDialog;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.absensi.AbsensiV2Activity;
import com.pdamkotasmg.happywork.fitur.absensi.CheckLocationActivity;
import com.pdamkotasmg.happywork.fitur.feeds.controller.FeedsController;
import com.pdamkotasmg.happywork.fitur.kehadiran.view.KehadiranActivity;
import com.pdamkotasmg.happywork.fitur.payslip.PayslipActivity;
import com.pdamkotasmg.happywork.fitur.profil.ProfileActivity;
import com.pdamkotasmg.happywork.utils.Config;
import com.pdamkotasmg.happywork.utils.Connectivity;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    private FeedsController feedsController;
    private boolean statusExpandedTrue = false;
    private static final int RC_CAMERA_AND_LOCATION = 1;
    private String typeConnection;
    private String statusAbsensi;

    private ImageView ivTutorialVideo;
    private TextView divNamaLengkap;
    private LinearLayout divRekamWaktu;
    private LinearLayout divPayslip;
    private LinearLayout divKehadiran;
    private LinearLayout divLainnya;
    private LottieAnimationView lavThumbUp;
    private CircleImageView ciProfileImage;
    private RecyclerView rv;
    private CardView divLainnyaExpanded;
    private LinearLayout divOvertime;
    private String TAG = "debug";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        initView();
        feedsController = new FeedsController();
        feedsController.getFeeds(getApplicationContext(), rv);
        divLainnyaExpanded.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        divNamaLengkap.setText("Hai, " + sharedPreferences.getString(Config.SHARED_NAME, ""));

        if (Connectivity.isConnected(DashboardActivity.this)) {
            Log.d(TAG, "isConnect: Connected");
        } else {
            MaterialDialog mDialog = new MaterialDialog.Builder(DashboardActivity.this)
                    .setTitle("Kamu offline, lanjut absensi?")
                    .setCancelable(false)
                    .setNegativeButton("Tidak", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                    })
                    .setPositiveButton("Lanjut", (dialogInterface, which) -> {
                        startActivity(new Intent(getApplicationContext(), AbsensiV2Activity.class));
                    })
                    .build();

            // Show Dialog
            mDialog.show();
        }

        divRekamWaktu.setOnClickListener(v -> {
//            boolean hasFingerprintSupport = FingerAuth.hasFingerprintSupport(this);
//            if (hasFingerprintSupport) {
//                Log.d(TAG, "fingerprint: enable");
//                fingerPrintSHow();
//            } else {
//                Log.d(TAG, "fingerprint: disable");
//                startActivity(new Intent(getApplicationContext(), CheckLocationActivity.class));
//            }

//            Connectivity.isConnectedFast(DashboardActivity.this);
            // TODO check location shift mboh
            if (Connectivity.isConnected(DashboardActivity.this)) {
                Log.d(TAG, "isConnect: Connected");
                editor.putString(Config.SHARED_STATUS_ABSENSI, "online");
                editor.apply();
                startActivity(new Intent(getApplicationContext(), CheckLocationActivity.class));
            }

        });

        divKehadiran.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), KehadiranActivity.class));
        });
        divLainnya.setOnClickListener(v -> {
            if (!statusExpandedTrue) {
                divLainnyaExpanded.setVisibility(View.VISIBLE);
                statusExpandedTrue = true;
            } else {
                divLainnyaExpanded.setVisibility(View.GONE);
                statusExpandedTrue = false;
            }
        });

        divPayslip.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PayslipActivity.class));
        });

        divNamaLengkap.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        });
    }

    private void fingerPrintSHow() {
        new FingerAuthDialog(this)
                .setTitle("Fingerprint")
                .setCancelable(true)
                .setMaxFailedCount(3) // Number of attemps, default 3
                .setNegativeButton("Batal", (dialogInterface, i) -> dialogInterface.dismiss())
                .setOnFingerAuthListener(new FingerAuth.OnFingerAuthListener() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(getApplicationContext(), CheckLocationActivity.class));
                        Toast.makeText(DashboardActivity.this, "onSuccess ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(DashboardActivity.this, "Coba lagi", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(DashboardActivity.this, "Tidak ada yang sama", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void initView() {
        ivTutorialVideo = findViewById(R.id.iv_tutorial_video);
        divNamaLengkap = findViewById(R.id.div_nama_lengkap);
        divRekamWaktu = findViewById(R.id.div_rekam_waktu);
        divPayslip = findViewById(R.id.div_payslip);
        divKehadiran = findViewById(R.id.div_kehadiran);
        divLainnya = findViewById(R.id.div_lainnya);
        lavThumbUp = findViewById(R.id.lav_thumbUp);
        ciProfileImage = findViewById(R.id.ci_profile_image);
        rv = findViewById(R.id.rv);
        divLainnyaExpanded = findViewById(R.id.div_lainnya_expanded);
        divOvertime = findViewById(R.id.div_overtime);
    }
}