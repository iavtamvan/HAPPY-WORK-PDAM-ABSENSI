package com.pdamkotasmg.goodday.fitur.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.an.deviceinfo.device.model.Device;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.pdamkotasmg.goodday.BuildConfig;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.dashboard.DashboardActivity;
import com.pdamkotasmg.goodday.fitur.splash.model.updateAplikasi.UdpateAplikasiRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    // TODO (splash) getting data Update APlikasi DONE
    // TODO (splash) getting data ListFake GPS DONE
    private static final int RC_CAMERA_AND_LOCATION = 1;

    private String TAG = "debug";
    private String androidVersionDevice;
    private String androidVersionDeviceServer;
    private String typeCheat;
    private static int SPLASH_TIME_OUT = 1000;
    private boolean flag = true;
    private PackageInfo packageInfo;
    private String[] requestedPermissions;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
    private String address_gps;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;
    private Double lati, longi;

    private String flagSplash;

    // android token
    private String androidToken1;
    private String androidToken2;
    private String androidToken3;
    private String androidToken4;
    private String androidToken5;
    private String androidToken6;
    private String androidToken7;
    private String androidToken8;
    private String androidToken9;
    private String androidToken10;

    private String urlLogo;

    private List<String> stringslist;

    private FusedLocationProviderClient mFusedLocation;
    private AdView adView;
    private ImageView logo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        initView();
        Config.methodRequiresTwoPermission(SplashScreenActivity.this);
        androidVersionDevice = BuildConfig.VERSION_NAME;
        Log.d(TAG, "onCreate: " + androidVersionDevice);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        flagSplash = sharedPreferences.getString(Config.SHARED_FLAG_SPLASH, "");
        Log.d(TAG, "flagSplash: " + flagSplash);
        if (flagSplash.equalsIgnoreCase("")) {
            Config.dialogAlertSplash(SplashScreenActivity.this, "Buka kembali aplikasinya", "Apabila tidak di izinkan mengakibatkan error pada aplikasi", "Tidak");
        } else {
            editor.putString(Config.SHARED_FLAG_SPLASH, "1");
            editor.apply();
            Log.d(TAG, "Masuk Apps good day");
            // TODO harusnya unComent pada mode Production
//            if (Settings.Secure.getInt(getApplicationContext().getContentResolver(),
//                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0) {
//                typeCheat = "Dev-Mode";
//                Config.saveSharedCheat(SplashScreenActivity.this, typeCheat, "Tampilan Awal", "1");
//                Toast.makeText(this, "Matikan mode debugging", Toast.LENGTH_SHORT).show();
//                Config.dialogAlert(SplashScreenActivity.this, "Developer mode atau opsi developer ON", "Akun di BEKUKAN oleh sistem Android, hubungi kepegawaian dan PTI", "OKE");
//                // TODO bekukan akun yang nakal.
//            } else {

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    showGPSDisabledAlertToUser();
                    Log.d(TAG, "onCreate: masuk If locaton");
                } else {
                    Log.d(TAG, "onCreate: masuk else locaton");
                    mFusedLocation = LocationServices.getFusedLocationProviderClient(SplashScreenActivity.this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Config.dialogAlertSplash(SplashScreenActivity.this, "Buka kembali aplikasi", "Apabila tidak di izinkan mengakibatkan error pada aplikasi", "Tidak");
                        Log.d(TAG, "onCreate: masuk activity compact");
                        return;
                    } else {
                        Log.d(TAG, "onCreate: masuk else activity compact");
                        mFusedLocation.getLastLocation().addOnSuccessListener(SplashScreenActivity.this, location -> {
                            if (location != null) {
                                Log.d(TAG, "onSuccessgetLatitude: " + location.getLatitude());
                                Log.d(TAG, "onSuccessgetLongitude: " + location.getLongitude());
                                lati = location.getLatitude();
                                longi = location.getLongitude();
                                gettingUpdateAplikasi();
                            } else {
                                gettingUpdateAplikasi();
                                Toast.makeText(this, "Lokasi tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

//            }
        }

        stringslist = new ArrayList<>();
//        Config.ads(SplashScreenActivity.this, adView);


//        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto);

//        TextView appname = findViewById(R.id.appname);
//        appname.setTypeface(typeface);

        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .playOn(findViewById(R.id.logo));

        YoYo.with(Techniques.FadeInUp)
                .duration(3000)
                .playOn(findViewById(R.id.appname));


        // TODO 1 getAndroidVersion DONE
        // TODO 2 getPackageNameFromServer DONE
        // TODO 3 getDeviceInfo Done
        // TODO 4 MockLocation Matching DONE
    }

    private void showGPSDisabledAlertToUser() {
        MaterialDialog mDialog = new MaterialDialog.Builder(SplashScreenActivity.this)
                .setTitle("Pergi ke Pengaturan untuk meng-Aktifkan GPS kamu")
                .setCancelable(false)
                .setNegativeButton("Gak mau", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                    finishAffinity();
                })
                .setPositiveButton("Aktifkan", (dialogInterface, which) -> {
                    finishAffinity();
                    Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(callGPSSettingIntent);
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    private void gettingUpdateAplikasi() {
        editor.putString(Config.SHARED_FLAG_SPLASH, "1");
        editor.apply();
        ApiService apiService = ApiConfig.getApiService(this);
        apiService.getUpdateAplikasi().enqueue(new Callback<UdpateAplikasiRootModel>() {
            @Override
            public void onResponse(Call<UdpateAplikasiRootModel> call, Response<UdpateAplikasiRootModel> response) {
                if (response.isSuccessful()) {
                    String nameDashboard = response.body().getData().get(0).getNameDashboard();
                    String logoApps = response.body().getData().get(0).getLogoApps();
                    String headerProfil = response.body().getData().get(0).getHeaderProfil();
                    String updateApk = response.body().getData().get(0).getUpdateApk();
                    String hello = response.body().getData().get(0).getHello();
                    String imageHeader = response.body().getData().get(0).getImageHeader();
                    String messageInfo = String.valueOf(response.body().getData().get(0).getInfo()).replace("[", "").replace("]", "").replace(",", "\n");

                    editor.putString(Config.SHARED_URL_LOGO, logoApps);
                    editor.putString(Config.SHARED_HEADER_PROFIL, headerProfil);
                    editor.putString(Config.SHARED_NAME_DASHBOARD, nameDashboard);
                    editor.putString(Config.SHARED_HELLO, hello);
                    editor.putString(Config.SHARED_IMAGE_HEADER, imageHeader);
                    editor.putString(Config.SHARED_MESSAGE_INFO, messageInfo);
                    editor.apply();

                    Log.d(TAG, "updateApk: " + updateApk);
                    Log.d(TAG, "imageLogo: " + logoApps);
                    Glide.with(SplashScreenActivity.this).load(logoApps).override(512, 512).into(logo);
                    if (!updateApk.equals(BuildConfig.VERSION_NAME)) {
                        MaterialDialog mDialog = new MaterialDialog.Builder(SplashScreenActivity.this)
                                .setTitle("Aplikasi harus di update ke versi " + updateApk + ", \nsekarang memakai aplikasi versi " + BuildConfig.VERSION_NAME)
                                .setMessage(response.body().getData().get(0).getMessageUpdate().toString().replace("[", "").replace("]", "").replace(",", "\n"))
                                .setCancelable(false)
                                .setPositiveButton("Update Sekarang", (dialogInterface, which) -> {
//                            dialogInterface.dismiss();
                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                })
                                .build();

                        mDialog.show();
                    } else {
                        gettingDataDeviceInfo();
                    }

                }
            }

            @Override
            public void onFailure(Call<UdpateAplikasiRootModel> call, Throwable t) {
                Toast.makeText(SplashScreenActivity.this, "Error, Coba Buka Kembali (#001S778J)", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        });
    }

    @SuppressLint("HardwareIds")
    private void gettingDataDeviceInfo() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wm.getConnectionInfo();
        int ipAddress = connectionInfo.getIpAddress();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        device = new Device(SplashScreenActivity.this);
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
        getHwid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        getSSIDWifi = connectionInfo.getSSID().replace("\"", "");

        Log.d(TAG, "SDKInteger: " + getSdkVersion);

        // TODO check Android WAJIBBBBBBBBBBBBBBBBBBBBB 32
        if (Integer.parseInt(getSdkVersion) > 34) {
            finishAffinity();
            Toast.makeText(this, Config.ERROR_ANDROID + " " + getSdkVersion, Toast.LENGTH_SHORT).show();
        } else {

            Log.d(TAG, "onSuccessgetLatitude || gettingDataDeviceInfo : " + lati);
            Log.d(TAG, "onSuccessgetLongitude || gettingDataDeviceInfo : " + longi);

            if (lati == 0.0 || longi == 0.0 || lati == null || longi == null) {
                Toast.makeText(SplashScreenActivity.this, "Lokasi tidak ditemukan (2)", Toast.LENGTH_SHORT).show();
            } else {
                Geocoder geocoder;
                List<Address> addressList = null;
                geocoder = new Geocoder(SplashScreenActivity.this, Locale.getDefault());
                try {
                    addressList = geocoder.getFromLocation(lati, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    if (addressList == null) {
                        Toast.makeText(this, "Lokasi tidak ditemukan", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "loc: " + address_gps + " ");
                    } else {
                        address_gps = addressList.get(0).getAddressLine(0); // If any additional address_gps line present than only, check with max available address_gps lines by getMaxAddressLineIndex()
                        city = addressList.get(0).getLocality();
//                        state = addressList.get(0).getAdminArea();
                        country = addressList.get(0).getCountryName();
                        postalCode = addressList.get(0).getPostalCode();
                        knownName = addressList.get(0).getFeatureName(); // Only if available else return NULL
                        Log.d(TAG, "loc: " + address_gps + " ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//        Log.d(TAG, "getModel: " + getModel);
//        Log.d(TAG, "getProduct: " + getProduct);
//        Log.d(TAG, "getDevice: " + getDevice);
//        Log.d(TAG, "getBuildBrand: " + getBuildBrand);
//        Log.d(TAG, "getOsVersion: " + getOsVersion);
//        Log.d(TAG, "getSdkVersion: " + getSdkVersion);
//        Log.d(TAG, "getBuildNumber: " + getBuildNumber);
//        Log.d(TAG, "getBuildIncremental: " + getBuildIncremental);
//        Log.d(TAG, "ipAdress: " + getIpAdress);
//        Log.d(TAG, "connectionType: " + getNetworkUsing);
//        Log.d(TAG, "hwid: " + getHwid);
//        Log.d(TAG, "ssid: " + getSSIDWifi);
//        Log.d(TAG, "address_gps: " + address_gps);
//        Log.d(TAG, "city: " + city);
//        Log.d(TAG, "state: " + state);
//        Log.d(TAG, "country: " + country);
//        Log.d(TAG, "postalCode: " + postalCode);
//        Log.d(TAG, "knownName: " + knownName);

            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(Config.SHARED_GETMODEL, getModel);
            editor.putString(Config.SHARED_GETPRODUCT, getProduct);
            editor.putString(Config.SHARED_GETDEVICE, getDevice);
            editor.putString(Config.SHARED_GETBUILDBRAND, getBuildBrand);
            editor.putString(Config.SHARED_GETOSVERSION, getOsVersion);
            editor.putString(Config.SHARED_GETSDKVERSION, getSdkVersion);
            editor.putString(Config.SHARED_GETBUILDNUMBER, getBuildNumber);
            editor.putString(Config.SHARED_GETBUILDINCREMENTAL, getBuildIncremental);
            editor.putString(Config.SHARED_IPADRESS, getIpAdress);
            editor.putString(Config.SHARED_CONNECTIONTYPE, getNetworkUsing);
            editor.putString(Config.SHARED_HWID, getHwid);
            editor.putString(Config.SHARED_SSID, getSSIDWifi);
            editor.putString(Config.SHARED_ADDRESS_GPS, address_gps);
            editor.putString(Config.SHARED_CITY, city);
//            editor.putString(Config.SHARED_STATE, state);
            editor.putString(Config.SHARED_COUNTRY, country);
            editor.putString(Config.SHARED_POSTALCODE, postalCode);
            editor.putString(Config.SHARED_KNOWNNAME, knownName);
            editor.putString(Config.SHARED_LATI, String.valueOf(lati));
            editor.putString(Config.SHARED_LONGITUDE, String.valueOf(longi));
            editor.apply();

            Log.d(TAG, "getHwid: " + getHwid);

            if (getHwid.equals("ccbbf1c6c6329d11") || getHwid.equals("8b903c7b6694e672")) {
                toHome();
            } else {
                isMockSettingsON(SplashScreenActivity.this);
            }
        }
    }

    // TODO Mulai cek Fake GPS
    public boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
//            areThereMockPermissionApps(context);
            mockV2(SplashScreenActivity.this);
            return false;
        } else {
            Toast.makeText(context, "Mock Location Terdeteksi", Toast.LENGTH_SHORT).show();
            mockV2(SplashScreenActivity.this);
            return true;
        }
    }

    public boolean mockV2(Context context) {
        int count = 0;
        boolean finding = false;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages =
                pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS);
                Log.d(TAG, "Package info : " + packageInfo);
                // Get Permissions
                requestedPermissions = packageInfo.requestedPermissions;
//                Log.d(TAG, "reqPermission: " + requestedPermissions);

                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
//                        if (requestedPermissions[i].equals("android.permission.ACCESS_MOCK_LOCATION") && !applicationInfo.packageName.equals(context.getPackageName())) {
//                            count++;
//                            Log.d(TAG, "mockV2: " + packageInfo.packageName);
//                            finding = true;
//                            break;
//                        }
                        if (
                                packageInfo.packageName.contains("fake")
                                        || packageInfo.packageName.equalsIgnoreCase("fake")
                                        || packageInfo.packageName.contains("mock")
                                        || packageInfo.packageName.contains("fakegps")
                                        || packageInfo.packageName.contains("fake.location")
                                        || packageInfo.packageName.contains("fake.gps")
                                        || packageInfo.packageName.contains("gpsemulator")
                                        || packageInfo.packageName.contains("locationchanger")
                                        || packageInfo.packageName.contains("mocklocations")
                                        || packageInfo.packageName.contains("gpsfake")
                                        || packageInfo.packageName.contains("fakelivelocation")
                                        || packageInfo.packageName.contains("fakelocation")
                                        || packageInfo.packageName.contains("virtualphonenavigation")
                                        || packageInfo.packageName.contains("fakegpslocationprofessional")
                                        || packageInfo.packageName.equalsIgnoreCase("gpsemulator")
                                        || packageInfo.packageName.equalsIgnoreCase("fakegpslocationprofessional")
                                        || packageInfo.packageName.equalsIgnoreCase("fakegps_route")
                                        || packageInfo.packageName.contains("fakegps_route")
                        ) {
                            count++;
                            Log.d(TAG, "mockV2: " + packageInfo.packageName);
                            finding = true;
                            break;
                        }
                    }

//                    if (finding) break;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("Got exception ", e.getMessage());
            }
        }

        if (!finding) {
            toHome();
        } else {
            typeCheat = "fake-gps";
            Config.saveSharedCheat(SplashScreenActivity.this, typeCheat, "Tampilan Awal", String.valueOf(requestedPermissions.length));
            // TODO kirim update ceklis apps nakal
            MaterialDialog mDialog = new MaterialDialog.Builder(SplashScreenActivity.this)
                    .setTitle(Config.ERROR_FAKE_GPS_TITLE)
                    .setMessage("Uninstall fake GPS kamu  Total apps fake : " + requestedPermissions.length + "x install \n Hubungi kepegawaian untuk aktivasi kembali...")
                    .setAnimation("lt_bohong.json")
                    .setCancelable(false)
                    .setNegativeButton("Oke deh, jangan suka bohong ya", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                        finishAffinity();
                    })
                    .setPositiveButton("Uninstall Aplikasi Absensi", (dialogInterface, which) -> {
                        Toast.makeText(context, "Uninstall aplikasi Absensi beraksi...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                        startActivity(intent);
                    })
                    .build();

            // Show Dialog
            mDialog.show();
        }
        if (count > 0)
            return true;
        return false;
    }

    private void toHome() {
        new Handler().postDelayed(() -> {
            // TODO intent ke WelcomeActivity.class
            Log.d(TAG, "Status Fack: Tidak ada fake gps");
            SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
            String name = sp.getString(Config.SHARED_NAME, "");
            String roles = sp.getString(Config.SHARED_ROLES, "");
            // TODO jika belum masuk ke welcome
            if (name.equalsIgnoreCase("") || TextUtils.isEmpty(name)) {
                finishAffinity();
                startActivity(new Intent(getApplicationContext(), IntroActivity.class));
            }
            // TODO jika sudah nantinya akan masuk ke dashboard
            else {
                finishAffinity();
                if (roles.contains("petugas-baca-meter")){
                    Intent intent = new Intent();
                    intent.setClassName(BuildConfig.APPLICATION_ID, "co.id.pdamkotasmg.HomeActivity");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                }
                Log.d(TAG, "run: " + name);

            }

        }, SPLASH_TIME_OUT);
    }

    // TODO Selesai cek Fake GPS

    private void initView() {
        adView = findViewById(R.id.adView);
        logo = findViewById(R.id.logo);
    }
}