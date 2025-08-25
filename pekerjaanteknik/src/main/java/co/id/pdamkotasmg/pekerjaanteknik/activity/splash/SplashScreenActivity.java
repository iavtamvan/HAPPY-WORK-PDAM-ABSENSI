package co.id.pdamkotasmg.pekerjaanteknik.activity.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.pdamkotasmg.goodday.fitur.authentication.login.LoginActivity;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.activity.home.HomeActivity;
import com.pdamkotasmg.goodday.utils.Config;

public class SplashScreenActivity extends AppCompatActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screens);
        
        initView();

        new Handler().postDelayed(() -> {

            SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
            String telepon = sp.getString(Config.SHARED_NAME, "");
            // TODO jika belum masuk ke LoginActivity
            if (telepon.equalsIgnoreCase("") || TextUtils.isEmpty(telepon)) {
                finishAffinity();
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }
            // TODO  jika sudah nantinya akan masuk ke Home
            else {
                finishAffinity();
                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                Log.d("nohp", "run: " + telepon);
            }

        },1000);
    }

    private void initView() {
        adView = findViewById(R.id.adView);
    }
}