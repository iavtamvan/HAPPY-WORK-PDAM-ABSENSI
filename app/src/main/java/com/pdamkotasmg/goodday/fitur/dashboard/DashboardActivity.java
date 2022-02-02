package com.pdamkotasmg.goodday.fitur.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.marcoscg.fingerauth.FingerAuth;
import com.marcoscg.fingerauth.FingerAuthDialog;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.dashboard.model.ShfitPegawaiRootModel;
import com.pdamkotasmg.goodday.fitur.dashboard.model.permissionName.PermissionRootModel;
import com.pdamkotasmg.goodday.fitur.feeds.controller.FeedsController;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.activity.KehadiranActivity;
import com.pdamkotasmg.goodday.fitur.payslip.PayslipActivity;
import com.pdamkotasmg.goodday.fitur.permintaan.activity.PermintaanActivity;
import com.pdamkotasmg.goodday.fitur.presensi.CheckLocationActivity;
import com.pdamkotasmg.goodday.fitur.presensi.PresensiActivity;
import com.pdamkotasmg.goodday.fitur.profil.ProfileActivity;
import com.pdamkotasmg.goodday.utils.Config;
import com.pdamkotasmg.goodday.utils.Connectivity;
import com.scottyab.rootbeer.RootBeer;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    private String TAG = "debug";

    private FeedsController feedsController;
    private SharedPreferences sharedPreferences;
    ;
    private SharedPreferences.Editor editor;

    private boolean statusExpandedTrue = false;
    private static final int RC_CAMERA_AND_LOCATION = 1;
    private String accessToken;
    private String typeConnection;
    private String statusPresensi;

    private TextView divNamaLengkap;
    private LinearLayout divRekamWaktu;
    private LinearLayout divPayslip;
    private LinearLayout divKehadiran;
    private LinearLayout divLainnya;
    private LottieAnimationView lavThumbUp;
    private CircleImageView ciProfileImage;
    private RecyclerView rv;
    private CardView divLainnyaExpanded;
    private LinearLayout divOvertime;
    private LottieAnimationView ltProfil;
    private LinearLayout divRequest;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        initView();
        feedsController = new FeedsController();
        feedsController.getFeeds(getApplicationContext(), rv);
        divLainnyaExpanded.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        divNamaLengkap.setText("Hai, " + sharedPreferences.getString(Config.SHARED_NAME, ""));
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        StrictMode.ThreadPolicy
                policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.d(TAG, "policy: " + policy);
        methodRequiresTwoPermission();

        // TODO Check Rooted
        RootBeer rootBeer = new RootBeer(DashboardActivity.this);
        if (rootBeer.isRooted()) {
            //we found indication of root
            Toast.makeText(this, "Rooted Detected" + rootBeer, Toast.LENGTH_SHORT).show();
            Config.dialogAlert(DashboardActivity.this, "Rooted Deteksi", "Segera ganti HP, karena harus Flash Ulang android. Hubungi PTI", "Gakmau");
        }

        // TODO getPermissionName
        getPermissionName();
        getShiftPegawai();

        if (Connectivity.isConnected(DashboardActivity.this)) {
            Log.d(TAG, "isConnect: Connected");
        } else {
            MaterialDialog mDialog = new MaterialDialog.Builder(DashboardActivity.this)
                    .setTitle("Kamu offline, lanjut absensi?")
                    .setCancelable(false)
                    .setNegativeButton("Tidak", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                    })
                    .setPositiveButton("Lanjut", (dialogInterface, which) -> {
                        startActivity(new Intent(getApplicationContext(), PresensiActivity.class));
                    })
                    .build();

            // Show Dialog
            mDialog.show();
        }

        divRekamWaktu.setOnClickListener(v -> {
//            boolean hasFingerprintSupport = FingerAuth.hasFingerprintSupport(this);
//            if (hasFingerprintSupport) {
//                Log.d(TAG, "fingerprint: enable");
//                fingerPrintSHow();
//            } else {
//                Log.d(TAG, "fingerprint: disable");
//                startActivity(new Intent(getApplicationContext(), CheckLocationActivity.class));
//            }

//            Connectivity.isConnectedFast(DashboardActivity.this);
            // TODO check location shift location DONE
            if (Connectivity.isConnected(DashboardActivity.this)) {
                Log.d(TAG, "isConnect: Connected");
                editor.putString(Config.SHARED_STATUS_ABSENSI, "online");
                editor.apply();
                startActivity(new Intent(getApplicationContext(), CheckLocationActivity.class));
            }

        });

        divKehadiran.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, KehadiranActivity.class);
            intent.putExtra(Config.BUNDLE_RIWAYAT_ABSENSI, "0");
            startActivity(intent);
//            startActivity(new Intent(getApplicationContext(), KehadiranActivity.class));
        });
        divLainnya.setOnClickListener(v -> {
            if (!statusExpandedTrue) {
                divLainnyaExpanded.setVisibility(View.VISIBLE);
                statusExpandedTrue = true;
            } else {
                divLainnyaExpanded.setVisibility(View.GONE);
                statusExpandedTrue = false;
            }
        });

        divPayslip.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PayslipActivity.class));
        });

        divNamaLengkap.setOnClickListener(v -> {

        });

        divRequest.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PermintaanActivity.class));
        });

        ltProfil.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        });


    }

    private void getPermissionName() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getPermissionNames(accessToken).enqueue(new Callback<PermissionRootModel>() {
            @Override
            public void onResponse(Call<PermissionRootModel> call, Response<PermissionRootModel> response) {
                if (response.isSuccessful()) {
                    // TODO IF Aman
                    List<String> list = response.body().getData().getPermissions();

                    // TODO save permission to SharedPref Array
                    Gson gson = new Gson();
                    String json = gson.toJson(list);
                    editor.putString(Config.SHARED_PERMISION_APPS, json);
                    editor.commit();

                    String sets = sharedPreferences.getString(Config.SHARED_PERMISION_APPS, null);
                    Log.d(TAG, "====== Start Permission Dashboard Good Day ====== ");
                    Log.d(TAG, "array :: " + sets);
                    Log.d(TAG, "====== End Permission Dashboard Good Day ====== ");

                    if (!list.contains("portal-pegawai.attendance.user.record")) {
                        divRekamWaktu.setVisibility(View.GONE);
                    }

                } else {
                    // TODO GONE Fitur
                    Config.dialogAlert(DashboardActivity.this, "Masalah Permission", "Permission ditolak, hubungi PTI/Kepegawaian", "Oke");
                }
            }

            @Override
            public void onFailure(Call<PermissionRootModel> call, Throwable t) {

            }
        });
    }

    public void getShiftPegawai() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getShiftPegawai(accessToken).enqueue(new Callback<ShfitPegawaiRootModel>() {
            @Override
            public void onResponse(Call<ShfitPegawaiRootModel> call, Response<ShfitPegawaiRootModel> response) {
                if (response.isSuccessful()) {
                    editor.putString(Config.SHARED_SHIFT_DAILY_CODE, response.body().getData().getShiftDailyCode());
                    editor.putString(Config.SHARED_START_TIME, response.body().getData().getStartTime());
                    editor.putString(Config.SHARED_END_TIME, response.body().getData().getEndTime());
                    editor.putString(Config.SHARED_REMARK, response.body().getData().getRemark());
                    editor.apply();
                } else {
                    Toast.makeText(DashboardActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShfitPegawaiRootModel> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fingerPrintSHow() {
        new FingerAuthDialog(this)
                .setTitle("Fingerprint")
                .setCancelable(true)
                .setMaxFailedCount(3) // Number of attemps, default 3
                .setNegativeButton("Batal", (dialogInterface, i) -> dialogInterface.dismiss())
                .setOnFingerAuthListener(new FingerAuth.OnFingerAuthListener() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(getApplicationContext(), CheckLocationActivity.class));
                        Toast.makeText(DashboardActivity.this, "onSuccess ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(DashboardActivity.this, "Coba lagi", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(DashboardActivity.this, "Tidak ada yang sama", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA, Manifest.permission.USE_FINGERPRINT, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE,};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    private void initView() {
        divNamaLengkap = findViewById(R.id.div_nama_lengkap);
        divRekamWaktu = findViewById(R.id.div_rekam_waktu);
        divPayslip = findViewById(R.id.div_payslip);
        divKehadiran = findViewById(R.id.div_kehadiran);
        divLainnya = findViewById(R.id.div_lainnya);
        lavThumbUp = findViewById(R.id.lav_thumbUp);
        ciProfileImage = findViewById(R.id.ci_profile_image);
        rv = findViewById(R.id.rv);
        divLainnyaExpanded = findViewById(R.id.div_lainnya_expanded);
        divOvertime = findViewById(R.id.div_overtime);
        ltProfil = findViewById(R.id.lt_profil);
        divRequest = findViewById(R.id.div_request);
    }
}