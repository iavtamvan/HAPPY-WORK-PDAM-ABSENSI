package id.pdamkotasmg.pekerjaan_teknik_feature.activity.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;

import id.pdamkotasmg.pekerjaan_teknik_feature.R;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.home.HomeSPKActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.login.LoginSPKActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.utils.Config;

public class SplashScreenSPKActivity extends AppCompatActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_spk_screen);
        getSupportActionBar().hide();
        initView();

        new Handler().postDelayed(() -> {

            SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
            String telepon = sp.getString(Config.SHARED_NAME, "");
            // TODO jika belum masuk ke LoginActivity
            if (telepon.equalsIgnoreCase("") || TextUtils.isEmpty(telepon)) {
                finishAffinity();
                startActivity(new Intent(SplashScreenSPKActivity.this, LoginSPKActivity.class));
            }
            // TODO  jika sudah nantinya akan masuk ke Home
            else {
                finish();
                startActivity(new Intent(SplashScreenSPKActivity.this, HomeSPKActivity.class));
                Log.d("nohp", "run: " + telepon);
            }

        },1000);
    }

    private void initView() {
        adView = findViewById(R.id.adView);
    }
}