package com.pdamkotasmg.happywork.fitur.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.an.deviceinfo.device.model.Device;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.authentication.WelcomeAuthActivity;
import com.pdamkotasmg.happywork.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import im.delight.android.location.SimpleLocation;

public class SplashScreenActivity extends AppCompatActivity {
    // TODO (splash) getting data Update APlikasi
    // TODO (splash) getting data ListFake GPS

    private String TAG = "debug";
    private static int SPLASH_TIME_OUT = 3000;
    private boolean flag = true;
    private PackageInfo packageInfo;
    int i;

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
    private SimpleLocation location;

    private List<String> stringslist;
    private List<String> packageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        //This is where we change our app name font to blacklist font
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto);

        TextView appname = findViewById(R.id.appname);
        appname.setTypeface(typeface);

        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .playOn(findViewById(R.id.logo));

        YoYo.with(Techniques.FadeInUp)
                .duration(3000)
                .playOn(findViewById(R.id.appname));

        packageString = new ArrayList<>();
        stringslist = new ArrayList<>();
        stringslist.add("com.lexa.fakegps");
        stringslist.add("com.fakegps.mock");
        stringslist.add("com.blogspot.newapphorizons.fakegps");
        stringslist.add("com.gsmartstudio.fakegps");
        stringslist.add("org.hola.gpslocation");
        stringslist.add("com.incorporateapps.fakegps.fre");
        stringslist.add("com.rosteam.gpsemulator");
        stringslist.add("com.usefullapps.fakegpslocationpro");
        stringslist.add("ru.gavrikov.mocklocations");
        stringslist.add("com.locationchanger");
        gettingDataDeviceInfo();
        
    }

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
        getHwid  = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        getSSIDWifi = connectionInfo.getSSID().replace("\"", "");

        location = new SimpleLocation(SplashScreenActivity.this);
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(SplashScreenActivity.this);
        }
        lati = location.getLatitude();
        longi = location.getLongitude();
        Geocoder geocoder;
        List<Address> addressList;
        geocoder = new Geocoder(SplashScreenActivity.this, Locale.getDefault());
        try {
            addressList = geocoder.getFromLocation(lati, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address_gps = addressList.get(0).getAddressLine(0); // If any additional address_gps line present than only, check with max available address_gps lines by getMaxAddressLineIndex()
            city = addressList.get(0).getLocality();
            state = addressList.get(0).getAdminArea();
            country = addressList.get(0).getCountryName();
            postalCode = addressList.get(0).getPostalCode();
            knownName = addressList.get(0).getFeatureName(); // Only if available else return NULL
            Log.d("TAG", "loc: " + address_gps + " ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getModel: " + getModel);
        Log.d(TAG, "getProduct: " + getProduct);
        Log.d(TAG, "getDevice: " + getDevice);
        Log.d(TAG, "getBuildBrand: " + getBuildBrand);
        Log.d(TAG, "getOsVersion: " + getOsVersion);
        Log.d(TAG, "getSdkVersion: " + getSdkVersion);
        Log.d(TAG, "getBuildNumber: " + getBuildNumber);
        Log.d(TAG, "getBuildIncremental: " + getBuildIncremental);
        Log.d(TAG, "ipAdress: " + getIpAdress);
        Log.d(TAG, "connectionType: " + getNetworkUsing);
        Log.d(TAG, "hwid: " + getHwid);
        Log.d(TAG, "ssid: " + getSSIDWifi);
        Log.d(TAG, "address_gps: " + address_gps);
        Log.d(TAG, "city: " + city);
        Log.d(TAG, "state: " + state);
        Log.d(TAG, "country: " + country);
        Log.d(TAG, "postalCode: " + postalCode);
        Log.d(TAG, "knownName: " + knownName);

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
        editor.putString(Config.SHARED_STATE, state);
        editor.putString(Config.SHARED_COUNTRY, country);
        editor.putString(Config.SHARED_POSTALCODE, postalCode);
        editor.putString(Config.SHARED_KNOWNNAME, knownName);
        editor.putString(Config.SHARED_LATI, String.valueOf(lati));
        editor.putString(Config.SHARED_LONGITUDE, String.valueOf(longi));
        editor.apply();

        isMockSettingsON(SplashScreenActivity.this);

    }

    public boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
            areThereMockPermissionApps(context);
            return false;
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean areThereMockPermissionApps(Context context) {
        int count = 0;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        boolean finding = false;
        for (ApplicationInfo applicationInfo : packages) {
            try {
                for (i = 0; i < stringslist.size(); i++) {
                    packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                    Log.d(TAG, "packages : " + packageInfo.packageName);
                    if (packageInfo.packageName.equalsIgnoreCase(stringslist.get(i))) {
                        Log.d(TAG, packageInfo.packageName + " : Fuck Moc: Sama");
                        finding = true;
                        break;
                    } else {
                        Log.d(TAG, packageInfo.packageName + " : Fuck Moc: Lanjut");
                    }
                }
                if (finding) break;

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!finding) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, WelcomeAuthActivity.class));
                finishAffinity();
            }, SPLASH_TIME_OUT);

        } else {
            MaterialDialog mDialog = new MaterialDialog.Builder(this)
                    .setTitle("Haayyoooooooo kamu" + " ....?")
                    .setMessage("Uninstall fake GPS kamu " + packageInfo.packageName + "\n\n Hubungi kepegawaian untuk aktivasi kembali...")
                    .setAnimation("lt_bohong.json")
                    .setCancelable(false)
                    .setNegativeButton("Oke deh, jangan suka bohong ya", new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                            finishAffinity();
                        }
                    })
                    .setPositiveButton("Uninstall Aplikasi Presensi", new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Toast.makeText(context, "Uninstall aplikasi presensi beraksi...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_DELETE);
                            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                            startActivity(intent);
                        }
                    })
                    .build();

            // Show Dialog
            mDialog.show();
        }
        if (count > 0)
            return true;
        return false;
    }
}