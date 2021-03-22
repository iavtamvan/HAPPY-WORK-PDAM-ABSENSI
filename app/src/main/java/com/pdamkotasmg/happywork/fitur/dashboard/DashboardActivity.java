package com.pdamkotasmg.happywork.fitur.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.absensi.AbsensiActivity;
import com.pdamkotasmg.happywork.fitur.feeds.controller.FeedsController;
import com.pdamkotasmg.happywork.fitur.kehadiran.view.KehadiranActivity;
import com.pdamkotasmg.happywork.fitur.payslip.PayslipActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    private FeedsController feedsController;
    private boolean statusExpandedTrue = false;
    private static final int RC_CAMERA_AND_LOCATION = 1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        initView();
        feedsController = new FeedsController();
        feedsController.getFeeds(getApplicationContext(), rv);
        divLainnyaExpanded.setVisibility(View.GONE);

        divRekamWaktu.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AbsensiActivity.class));
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