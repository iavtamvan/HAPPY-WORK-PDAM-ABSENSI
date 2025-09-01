package com.pdamkotasmg.goodday.fitur.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.dashboard.model.ShfitPegawaiRootModel;
import com.pdamkotasmg.goodday.fitur.dashboard.model.permissionName.PermissionRootModel;
import com.pdamkotasmg.goodday.fitur.feeds.controller.FeedsController;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.activity.KehadiranActivity;
import com.pdamkotasmg.goodday.fitur.menuLainnya.ListWebViewActivity;
import com.pdamkotasmg.goodday.fitur.payslip.SalaryHistoryActivity;
import com.pdamkotasmg.goodday.fitur.permintaan_persetujuan.activity.PermintaanActivity;
import com.pdamkotasmg.goodday.fitur.permintaan_persetujuan.activity.PersetujuanActivity;
import com.pdamkotasmg.goodday.fitur.presensi.CheckLocationActivity;
import com.pdamkotasmg.goodday.fitur.presensi.PresensiActivity;
import com.pdamkotasmg.goodday.fitur.profil.ProfileActivity;
import com.pdamkotasmg.goodday.fitur.profil.controller.ProfileController;
import com.pdamkotasmg.goodday.utils.Config;
import com.pdamkotasmg.goodday.utils.Connectivity;
import com.scottyab.rootbeer.RootBeer;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    private String TAG = "debug";

    private FeedsController feedsController;
    private ProfileController profileController;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean statusExpandedTrue = false;
    private static final int RC_CAMERA_AND_LOCATION = 1;
    private String accessToken;
    private String hello;
    private String nameDashboard;
    private String messageInfo;
    private String satker;

    private String typeCheat;
    private String pageCheat;
    private String countCheat;
    private TextView tvGood;
    private TextView tvNameDashboard;
    private TextView tvSatker;
    private CoordinatorLayout divRekamWaktu;
    private LottieAnimationView ltPulsator;
    private TextView scrollingtext;
    private CardView cvInfoWarning;
    private TextView tvInfoWarning;
    private CardView divPayslip;
    private CardView divKehadiran;
    private CardView divRequest;
    private CardView divOther;
    private CircleImageView divProfil;
    private CircleImageView ivLogout;
    private CardView divApprovals;
    private ImageView ivVerified;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_v2);
        
        initView();

//        Config.isMockSettingsONV2(DashboardActivity.this);
        feedsController = new FeedsController();
        profileController = new ProfileController();
//        feedsController.getFeeds(getApplicationContext(), rv);
//        divLainnyaExpanded.setVisibility(View.GONE);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 11) {
            tvGood.setText("Good Morning \uD83C\uDF04");
            Config.showNotification(DashboardActivity.this, "Pekerjaan Sudah Siap", "Semangat kerja !!!", DashboardActivity.class);
        } else if (timeOfDay >= 11 && timeOfDay < 15) {
            tvGood.setText("Good Afternoon \uD83C\uDF1E");
            Config.showNotification(DashboardActivity.this, "Sudah Waktunya Istirahat", "Semangat!!!", DashboardActivity.class);
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            Config.showNotification(DashboardActivity.this, "Saatnya Istirahat Sejenak", "Kalau lembur, jangan lupa klik LEMBUR YA!", DashboardActivity.class);
            tvGood.setText("Good Evening \uD83C\uDF25");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            Config.showNotification(DashboardActivity.this, "Saatnya Tidur", "Kalau lembur, jangan lupa klik LEMBUR YA!", DashboardActivity.class);
            tvGood.setText("Good Night \uD83D\uDECC \uD83D\uDCA4");
        }

        ivVerified.setImageResource(R.drawable.verified);

        tvGood.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClassName("com.pdamkotasmg.happywork", "id.pdamkotasmg.edms.fitur.suratMasuk.activity.EDMSHomeActivity");
            startActivity(intent);
        });

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        hello = sharedPreferences.getString(Config.SHARED_HELLO, "");
        nameDashboard = sharedPreferences.getString(Config.SHARED_NAME_DASHBOARD, "");
        satker = sharedPreferences.getString(Config.SHARED_SATKER, "") + " - " + sharedPreferences.getString(Config.SHARED_SUBSATKER_FORMATTED, "");
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        messageInfo = sharedPreferences.getString(Config.SHARED_MESSAGE_INFO, "");

        Log.d(TAG, "tokenFirebase: " + sharedPreferences.getString(Config.SHARED_FCM_TOKEN, ""));

        typeCheat = sharedPreferences.getString(Config.SHARED_ACTION_CHEAT, "");
        pageCheat = sharedPreferences.getString(Config.SHARED_PAGE_CHEAT, "");
        countCheat = sharedPreferences.getString(Config.SHARED_COUNT_CHEAT, "");

        scrollingtext.setText(hello);
        scrollingtext.setSelected(true);
        tvNameDashboard.setText(sharedPreferences.getString(Config.SHARED_NAME, ""));
        tvSatker.setText(satker);

        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/PDAM");
        path.mkdir();

        if (!messageInfo.isEmpty()) {
            cvInfoWarning.setVisibility(View.VISIBLE);
            tvInfoWarning.setText(messageInfo);
        }

        StrictMode.ThreadPolicy
                policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.d(TAG, "policy: " + policy);
        methodRequiresTwoPermission();

        if (!typeCheat.isEmpty()) {
            Config.sendCheat(DashboardActivity.this, accessToken, typeCheat, pageCheat, countCheat);
            editor.putString(Config.SHARED_ACTION_CHEAT, "");
            editor.apply();
            Toast.makeText(this, "Sending Cheats ...", Toast.LENGTH_SHORT).show();
        }

        // TODO Check Rooted
        RootBeer rootBeer = new RootBeer(DashboardActivity.this);
        if (rootBeer.isRooted()) {
            //we found indication of root
            Toast.makeText(this, "Rooted Detected" + rootBeer, Toast.LENGTH_SHORT).show();
            Config.sendCheat(DashboardActivity.this, accessToken, "root", "Dashboard Good Day", "");
            Config.dialogAlert(DashboardActivity.this, "Rooted Deteksi", "Segera ganti HP atau flash ulang. Hubungi PTI", "Tidak");
        }

        // TODO getPermissionName
        getPermissionName();

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
                if (!pageCheat.isEmpty()) {
                    Toast.makeText(this, "Anda melakukan kecurangan pada aplikasi, konfirmasi jujur ke atasan anda kemudian Hubungi PTI", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "isConnect: Connected");
                    editor.putString(Config.SHARED_STATUS_ABSENSI, "online");
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), CheckLocationActivity.class));
                }
            }

        });

        divKehadiran.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, KehadiranActivity.class);
            intent.putExtra(Config.BUNDLE_RIWAYAT_ABSENSI, "0");
            startActivity(intent);
        });
        divOther.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ListWebViewActivity.class));
        });

        divPayslip.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SalaryHistoryActivity.class));
        });

        divRequest.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PermintaanActivity.class));
        });

        divProfil.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        });

        ivLogout.setOnClickListener(v -> {
            MaterialDialog mDialog = new MaterialDialog.Builder(DashboardActivity.this)
                    .setMessage("Yakin mau keluar?")
                    .setAnimation("lt_logout.json")
                    .setCancelable(true)
                    .setNegativeButton("Gak", (dialogInterface, which) -> dialogInterface.dismiss())
                    .setPositiveButton("Iya", (dialogInterface, which) -> {
                        profileController.logout(DashboardActivity.this);
                        dialogInterface.dismiss();
                    })
                    .build();

            // Show Dialog
            mDialog.show();
        });

        divApprovals.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), PersetujuanActivity.class));
        });
    }

    private void getPermissionName() {
        ApiService apiService = ApiConfig.getApiService(this);
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

                    getShiftPegawai();

                    if (!list.contains("portal-pegawai.attendance.user.record")) {
                        divRekamWaktu.setVisibility(View.GONE);
                    }
//                    if (!list.contains("portal-pegawai.employee-request.admin.approval") || !list.contains("portal-pegawai.employee-request.user.approval")) {
//                        Toast.makeText(DashboardActivity.this, "portal-pegawai.employee-request.user.approval", Toast.LENGTH_SHORT).show();
//                        divApprovals.setVisibility(View.GONE);
//                    }
                    if (list.contains("portal-pegawai.employee-request.admin.approval") || list.contains("portal-pegawai.employee-request.user.approval")) {
                        divApprovals.setVisibility(View.VISIBLE);
                    } else {
                        divApprovals.setVisibility(View.GONE);
                    }


                } else {
                    // TODO GONE Fitur
                    Config.dialogAlertPermission(DashboardActivity.this, "Masalah Permission", "Permission ditolak, silahkan Login Kembali", "Login");
                }
            }

            @Override
            public void onFailure(Call<PermissionRootModel> call, Throwable t) {

            }
        });
    }

    public void getShiftPegawai() {
        ApiService apiService = ApiConfig.getApiService(this);
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


    @Override
    public void onBackPressed() {
        MaterialDialog mDialog = new MaterialDialog.Builder(DashboardActivity.this)
                .setTitle("Keluar dari aplikasi?")
                .setCancelable(false)
                .setNegativeButton("Tidak", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton("Ya", (dialogInterface, which) -> {
                    finishAffinity();
                })
                .build();

        mDialog.show();
    }

    private void initView() {
        tvGood = findViewById(R.id.tv_good);
        tvNameDashboard = findViewById(R.id.tv_name_dashboard);
        tvSatker = findViewById(R.id.tv_satker);
        divRekamWaktu = findViewById(R.id.div_rekam_waktu);
        ltPulsator = findViewById(R.id.lt_pulsator);
        scrollingtext = findViewById(R.id.scrollingtext);
        cvInfoWarning = findViewById(R.id.cv_info_warning);
        tvInfoWarning = findViewById(R.id.tv_info_warning);
        divPayslip = findViewById(R.id.div_payslip);
        divKehadiran = findViewById(R.id.div_kehadiran);
        divRequest = findViewById(R.id.div_request);
        divOther = findViewById(R.id.div_other);
        divProfil = findViewById(R.id.div_profil);
        ivLogout = findViewById(R.id.iv_logout);
        divApprovals = findViewById(R.id.div_approvals);
        ivVerified = findViewById(R.id.iv_verified);
    }
}