package com.pdamkotasmg.happywork.fitur.splash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "debug";
    private static int SPLASH_TIME_OUT = 3000;
    private boolean flag = true;
    private PackageInfo packageInfo;
    int i;

    private List<String> stringslist;
    private List<String> packageString;

    private Location location;

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
//        stringslist.add("com.samsung.android.provider.filterprovider");
        stringslist.add("com.lexa.fakegps");
        stringslist.add("com.reddoorz.app");
        stringslist.add("id.co.bri.brimo");
//        stringslist.add("com.samsung.android.video");
//        stringslist.add("com.samsung.android.tapack.authfw");

        isMockSettingsON(SplashScreenActivity.this);

    }

    public boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
            areThereMockPermissionApps(context);
            return false;
        } else {
            Toast.makeText(context, "Sukses", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean areThereMockPermissionApps(Context context) {
        int count = 0;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//        Log.d(TAG, "packages list : " + packages);
        boolean ketemu = false;
        for (ApplicationInfo applicationInfo : packages) {
            try {
                for (i = 0; i < stringslist.size(); i++) {
                    packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                    if (packageInfo.packageName.equalsIgnoreCase(stringslist.get(i))) {
                        Log.d(TAG, packageInfo.packageName + " : Fuck Moc: Sama");
                        ketemu = true;
                        break;
                    } else {
                        Log.d(TAG, packageInfo.packageName + " : Fuck Moc: Lanjut");
                    }
                }
                if (ketemu) break;

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!ketemu) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
                    finish();
                }
            }, SPLASH_TIME_OUT);

        } else {
            Toast.makeText(context, "Un install fake", Toast.LENGTH_SHORT).show();
        }

//        for (ApplicationInfo applicationInfo : packages) {
//            try {
//                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
//                        PackageManager.GET_PERMISSIONS);
//                packageString.add(packageInfo.packageName);
//                Log.d(TAG, "package: " + packageInfo.packageName);
//                // Get Permissions
//                String[] requestedPermissions = packageInfo.requestedPermissions;
////                Log.d(TAG, "reqPewrmission: " + requestedPermissions);
//
//                if (stringslist.equals(packageString.toString())) {
//                    Log.d(TAG, "Fuck moc : gagal");
//                } else {
//                    Log.d(TAG, "Fuck moc : berhasil");
//                }
//
//
////                if (requestedPermissions != null) {
////                    for (int i = 0; i < requestedPermissions.length; i++) {
////                        if (requestedPermissions[i].equals("android.permission.ACCESS_MOCK_LOCATION") && !applicationInfo.packageName.equals(context.getPackageName())) {
//////                            count++;
////
//////                            for (int j = 0; j < packageString.size(); j++) {
//////                                if (stringslist.get(j).equalsIgnoreCase(packageInfo.packageName)){
//////                                    Toast.makeText(context, "Fake your FUCK", Toast.LENGTH_SHORT).show();
//////                                } else {
//////                                    Toast.makeText(context, "Aman", Toast.LENGTH_SHORT).show();
//////                                }
//////                            }
////
////
////                        }
////                    }
////                }
//            } catch (PackageManager.NameNotFoundException e) {
//                Log.e("Got exception ", e.getMessage());
//                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//
//        }

        if (count > 0)
            return true;
        return false;
    }
}