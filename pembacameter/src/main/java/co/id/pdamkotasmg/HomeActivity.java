package co.id.pdamkotasmg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Date currentTime = Calendar.getInstance().getTime();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String periode = new SimpleDateFormat("YMM", Locale.getDefault()).format(new Date());
        Integer periodeBulanLalu = Integer.valueOf(new SimpleDateFormat("YMM", Locale.getDefault()).format(new Date()));

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SHARED_PERIODE, periode);
        editor.putString(Config.SHARED_PERIODE_BULAN_LALU, String.valueOf(periodeBulanLalu));
        editor.apply();


        Log.d(TAG, "Current time " + currentTime);
        Log.d(TAG, "Current date " + currentDate);
//        Log.d(TAG, "Current periode " + periode);
//        Log.d(TAG, "Current periodeBulanLalu " + (periodeBulanLalu - 1));

    }

}