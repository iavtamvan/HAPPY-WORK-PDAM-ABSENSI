package com.pdamkotasmg.happywork.fitur.absensi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.an.deviceinfo.device.model.Device;
import com.an.deviceinfo.permission.PermissionManager;
import com.camerakit.CameraKitView;
import com.krishna.securetimer.SecureTimer;
import com.pdamkotasmg.happywork.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AbsensiActivity extends AppCompatActivity {

    private String currentDate;
    private String nameUser;
    private double getLat, getLongi;
    private String getJaringanWifi, getJaringanPaketData;
    private String getAkurasiLokasi;

    //device info
    private Device device;
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

    private String replaceTimeServer;
    private String timeServer;

    private CameraKitView cameraBack;
    private CameraKitView cameraFront;
    private Button btnJepret;
    private LinearLayout divCamera;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        getSupportActionBar().hide();
        initView();
        nameUser = "Nama_";
        tvHeaderJudul.setText("REKAM RUPAMU");

        Date currentTimeInMillis = SecureTimer.with(AbsensiActivity.this).getCurrentDate();
        timeServer = String.valueOf(currentTimeInMillis);
        replaceTimeServer = timeServer.replace(" GMT+07:00 ", " ");
        Log.d("debug", "timeServer: " + replaceTimeServer);

        Calendar calendar = Calendar.getInstance();
        Date cDate = new Date();
        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        Log.d("debug", "dateNow: " + currentDate);

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wm.getConnectionInfo();
        int ipAddress = connectionInfo.getIpAddress();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        device = new Device(AbsensiActivity.this);
        getModel = device.getModel();
        getProduct = device.getProduct();
        getDevice = device.getDevice();
        getBuildBrand = device.getBuildBrand();
        getOsVersion = device.getOsVersion();
        getSdkVersion = String.valueOf(device.getSdkVersion());
        getBuildNumber = Build.ID;
        getBuildIncremental = Build.VERSION.INCREMENTAL;
        getIpAdress = Formatter.formatIpAddress(ipAddress);
        getNetworkUsing = String.valueOf(cm.getActiveNetworkInfo().getTypeName());
        getHwid  = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        getSSIDWifi = connectionInfo.getSSID().replace("\"", "");

        Log.d("debug", "getModel: " + getModel);
        Log.d("debug", "getProduct: " + getProduct);
        Log.d("debug", "getDevice: " + getDevice);
        Log.d("debug", "getBuildBrand: " + getBuildBrand);
        Log.d("debug", "getOsVersion: " + getOsVersion);
        Log.d("debug", "getSdkVersion: " + getSdkVersion);
        Log.d("debug", "getBuildNumber: " + getBuildNumber);
        Log.d("debug", "getBuildIncremental: " + getBuildIncremental);
        Log.d("debug", "ipAdress: " + getIpAdress);
        Log.d("debug", "wifi: " + getNetworkUsing);
        Log.d("debug", "hwid: " + getHwid);
        Log.d("debug", "ssid: " + getSSIDWifi);

        String DEBUG_TAG = "debug";
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (android.net.Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
                getDevice = networkInfo.getExtraInfo();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);

        cameraBack.setVisibility(View.GONE);
        cameraFront.startVideo();
        divCamera.setOnClickListener(v -> {
            cameraFront.captureImage((cameraKitView, bytes) -> {
                Toast.makeText(this, "Captured", Toast.LENGTH_SHORT).show();
                File makeFile = new File(Environment.getExternalStorageDirectory() + "/PDAM-ABSENSI");
                makeFile.mkdirs();
                File savedPhoto = new File(makeFile, nameUser + currentDate + "_" + timeServer + "_FF.xxkampretnotfailable");
                try {
                    FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                    Log.d("debug", "path: " + savedPhoto);
                    Log.d("debug", "makefile : " + makeFile);
                    outputStream.write(bytes);
                    outputStream.close();
                    cameraFront.onStop();
                    cameraFront.stopVideo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraFront.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraFront.onStart();
    }

    PermissionManager permissionManager = new PermissionManager(this);

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraFront.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.handleResult(requestCode, permissions, grantResults);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Presensi hanya berlaku 1x, jadi tidak bisa keluar", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 10000);
    }


    private void initView() {
        cameraBack = findViewById(R.id.camera_back);
        cameraFront = findViewById(R.id.camera_front);
        divCamera = findViewById(R.id.div_camera);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
    }
}