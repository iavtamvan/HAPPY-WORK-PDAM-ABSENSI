package com.pdamkotasmg.goodday.fitur.authentication.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.pdamkotasmg.goodday.BuildConfig;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.authentication.login.controller.LoginController;
import com.pdamkotasmg.goodday.utils.Config;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "debug_login";
    private String getModel;
    private String getProduct;
    private String getDevice;
    private String getBuildBrand;
    private String getOsVersion;
    private String getSdkVersion;
    private String getBuildNumber;
    private String getBuildIncremental;
    private String getIpAdress;
    private String getNetworkUsing;
    private String getHwid;
    private String getSSIDWifi;
    private String address_gps;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;
    private Double lati, longi;
    private String appVersion;
    private LoginController loginController;

    private String firebaseToken;

    private Button btnMasuk;
    private EditText edtNpp;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        getModel = sharedPreferences.getString(Config.SHARED_GETMODEL, "");
        getProduct = sharedPreferences.getString(Config.SHARED_GETPRODUCT, "");
        getDevice = sharedPreferences.getString(Config.SHARED_GETDEVICE, "");
        getBuildBrand = sharedPreferences.getString(Config.SHARED_GETBUILDBRAND, "");
        getOsVersion = sharedPreferences.getString(Config.SHARED_GETOSVERSION, "");
        getSdkVersion = sharedPreferences.getString(Config.SHARED_GETSDKVERSION, "");
        getBuildNumber = sharedPreferences.getString(Config.SHARED_GETBUILDNUMBER, "");
        getBuildIncremental = sharedPreferences.getString(Config.SHARED_GETBUILDINCREMENTAL, "");
        getIpAdress = sharedPreferences.getString(Config.SHARED_IPADRESS, "");
        getNetworkUsing = sharedPreferences.getString(Config.SHARED_CONNECTIONTYPE, "");
        getHwid = sharedPreferences.getString(Config.SHARED_HWID, "");
        getSSIDWifi = sharedPreferences.getString(Config.SHARED_SSID, "");
        address_gps = sharedPreferences.getString(Config.SHARED_ADDRESS_GPS, "");
        city = sharedPreferences.getString(Config.SHARED_CITY, "");
        state = sharedPreferences.getString(Config.SHARED_STATE, "");
        country = sharedPreferences.getString(Config.SHARED_COUNTRY, "");
        postalCode = sharedPreferences.getString(Config.SHARED_POSTALCODE, "");
        knownName = sharedPreferences.getString(Config.SHARED_KNOWNNAME, "");
        lati = Double.valueOf(sharedPreferences.getString(Config.SHARED_LATI, ""));
        longi = Double.valueOf(sharedPreferences.getString(Config.SHARED_LONGITUDE, ""));
        appVersion = BuildConfig.VERSION_NAME;

        Log.d(TAG, "getModel: " + getModel);
        Log.d(TAG, "getProduct: " + getProduct);
        Log.d(TAG, "getDevice: " + getDevice);
        Log.d(TAG, "getBuildBrand: " + getBuildBrand);
        Log.d(TAG, "getOsVersion: " + getOsVersion);
        Log.d(TAG, "getSdkVersion: " + getSdkVersion);
        Log.d(TAG, "getBuildNumber: " + getBuildNumber);
        Log.d(TAG, "getBuildIncremental: " + getBuildIncremental);
        Log.d(TAG, "getIpAdress: " + getIpAdress);
        Log.d(TAG, "getNetworkUsing: " + getNetworkUsing);
        Log.d(TAG, "getHwid: " + getHwid);
        Log.d(TAG, "getSSIDWifi: " + getSSIDWifi);
        Log.d(TAG, "address_gps: " + address_gps);
        Log.d(TAG, "city: " + city);
        Log.d(TAG, "state: " + state);
        Log.d(TAG, "country: " + country);
        Log.d(TAG, "postalCode: " + postalCode);
        Log.d(TAG, "knownName: " + knownName);
        Log.d(TAG, "lati: " + lati);
        Log.d(TAG, "longi: " + longi);
        getTokenFirebase();
        loginController = new LoginController();
        btnMasuk.setOnClickListener(v -> {
            if (edtNpp.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Lengkapi akun anda terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                loginController.login(LoginActivity.this, edtNpp.getText().toString().trim(), edtPassword.getText().toString().trim(),
                        getHwid, getModel, getProduct, getDevice, getBuildBrand, getOsVersion, getSdkVersion, getBuildNumber, getBuildIncremental,
                        getIpAdress, getNetworkUsing, getSSIDWifi, city, String.valueOf(lati), String.valueOf(longi), appVersion, firebaseToken);
            }
        });
    }

    private void getTokenFirebase() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d("debug", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    firebaseToken = task.getResult();

                    // Log and toast
                    Log.d("debug", "getTokenFirebase: " + firebaseToken);
                });
    }

    private void initView() {
        btnMasuk = findViewById(R.id.btn_masuk);
        edtNpp = findViewById(R.id.edt_npp);
        edtPassword = findViewById(R.id.edt_password);
    }
}