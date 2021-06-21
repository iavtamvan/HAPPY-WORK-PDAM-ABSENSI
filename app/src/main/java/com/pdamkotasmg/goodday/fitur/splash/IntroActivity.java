package com.pdamkotasmg.goodday.fitur.splash;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.slider.library.SliderLayout;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.utils.Config;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private SliderLayout slider;
    private LinearLayout divDaftarPage;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        initView();

        fragmentManager = getSupportFragmentManager();

        final PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment);
        fragmentTransaction.commit();

        divDaftarPage.setOnClickListener(v -> {
            Config.dialogAlertIntro(IntroActivity.this, "Sudah membaca seluruh konten?", "Membaca memberikanmu pengetahuan yang lebih banyak", "Belum");
        });

    }

    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Opsi Developer Mode", "Pegawai yang terdeteksi mengaktifkan Developer Mode, aplikasi akan berjalan tidak semestinya",
                Color.parseColor("#FFFFFFFF"), R.drawable.im_dev_mode, R.drawable.programming);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Fake GPS", "Pegawai yang terdeteksi menggunakan Fake GPS, aplikasi akan memberikan pemborosan battery",
                Color.parseColor("#FFFFFFFF"), R.drawable.im_fake_gps, R.drawable.ic_fake_gps);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Face Detection", "Pegawai yang terdeteksi melakukan foto tidak sesuai, aplikasi akan menerima absen anda",
                Color.parseColor("#FFFFFFFF"), R.drawable.im_face_detection, R.drawable.ic_face_detection);
        PaperOnboardingPage scr4 = new PaperOnboardingPage("Teknologi", "Menggunakan teknologi paling mutakhir untuk menghindari segala kecurangan",
                Color.parseColor("#FFFFFFFF"), R.drawable.im_cloud_computing, R.drawable.ic_cloud_computing);
        PaperOnboardingPage scr5 = new PaperOnboardingPage("Riwayat", "Segala kecurangan akan otomatis menyimpan pada sistem Absensi",
                Color.parseColor("#FFFFFFFF"), R.drawable.im_riwayat, R.drawable.ic_history);
        PaperOnboardingPage scr6 = new PaperOnboardingPage("Kegunaan", "Aplikasi absensi ini akan terus dikembangkan dengan tingkat kompleksitas yang tinggi sesuai kegunaannya",
                Color.parseColor("#FFFFFFFF"), R.drawable.im_kegunaan, R.drawable.ic_kegunaan);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        elements.add(scr4);
        elements.add(scr5);
        elements.add(scr6);
        return elements;
    }

    private void initView() {
        fragmentContainer = findViewById(R.id.fragment_container);
        slider = findViewById(R.id.slider);
        divDaftarPage = findViewById(R.id.div_daftar_page);
    }
}