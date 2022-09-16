package com.pdamkotasmg.goodday.fitur.splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.authentication.login.LoginActivity;

public class IntroActivity extends AppCompatActivity {

    private static final int MAX_STEP = 6;

    private Button btnGotIt;
    private Button btnSkip;
    private String[] title_array = {
            "Developer Mode", "Fake GPS",
            "Face Detection", "Technology",
            "Log","Utility"
    };
    private String[] description_array = {
            "GoodDaY tidak bisa berjalan jika Mode Developer/Opsi Pengembang aktif. GoodDaY tidak bisa menyimpan Presensi dan harus ke PTI",
            "GoodDaY dapat mendeteksi Fake GPS, apabila terindikasi dan tidak bisa presensi harus ke Kepegawaian dan PTI",
            "Wajah tidak sesuai data, GoodDay tidak dapat menerima Presensi",
            "Penggunaan teknologi canggih untuk menunjang GoodDaY",
            "Seluruh log pengguna tersimpan pada sistem GoodDaY",
            "GoodDaY akan terus dikembangkan dengan tingkat kompleksitas yang tinggi"
    };
    private int[] about_images_array = {
            R.drawable.developer, R.drawable.fakegps,
            R.drawable.facedetection1, R.drawable.tecno1,
            R.drawable.history, R.drawable.utility
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();

//        divDaftarPage.setOnClickListener(v -> {
//            Config.dialogAlertIntro(IntroActivity.this, "Sudah membaca seluruh konten?", "Membaca memberikanmu pengetahuan yang lebih banyak", "Belum");
//        });

        initComponent();

    }


    private void initComponent() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        btnGotIt = findViewById(R.id.btn_got_it);
        btnSkip = findViewById(R.id.btn_skip);

        bottomProgressDots(0);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnGotIt.setVisibility(View.GONE);
        btnGotIt.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
        });


        btnSkip.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void bottomProgressDots(int index) {
        LinearLayout dotsLayout = findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 20;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.dot);
            dots[i].setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        dots[index].setImageResource(R.drawable.dot);
        dots[index].setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
            if (position == title_array.length - 1) {
                btnGotIt.setVisibility(View.VISIBLE);
                btnSkip.setVisibility(View.GONE);
            } else {
                btnGotIt.setVisibility(View.GONE);
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    public class MyViewPagerAdapter extends PagerAdapter {

        MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_intro, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(description_array[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return title_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}