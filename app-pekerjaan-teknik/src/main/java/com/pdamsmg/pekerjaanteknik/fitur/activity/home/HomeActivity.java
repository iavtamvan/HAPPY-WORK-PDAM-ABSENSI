package com.pdamsmg.pekerjaanteknik.fitur.activity.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.pdamsmg.pekerjaanteknik.BuildConfig;
import com.pdamsmg.pekerjaanteknik.R;
import com.pdamsmg.pekerjaanteknik.adapter.WarningAdapter;
import com.pdamsmg.pekerjaanteknik.api.ApiConfig;
import com.pdamsmg.pekerjaanteknik.api.ApiService;
import com.pdamsmg.pekerjaanteknik.model.akun.login.LoginRootModel;
import com.pdamsmg.pekerjaanteknik.model.monitoring.MonitoringSPKRootModel;
import com.pdamsmg.pekerjaanteknik.model.progressMandor.ProgressMandorRootModel;
import com.pdamsmg.pekerjaanteknik.model.warning.DataItem;
import com.pdamsmg.pekerjaanteknik.model.warning.WarningRootModel;
import com.pdamsmg.pekerjaanteknik.utils.Config;
import com.pdamsmg.pekerjaanteknik.utils.ConfigAds;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String token;
    private String name;
    private String npp;
    private String satker;
    private String subSatker;

    private ProgressDialog progressDialog;

    private List<DataItem> data;
    private WarningAdapter warningAdapter;

    private LinearLayout divMenuAll;
    private LinearLayout divContainerHubungiPti;
    private LinearLayout divSpk;
    private LinearLayout divAld;
    //    private ImageView ivHeader;
    //    private NestedScrollView divMonitoring;
    private CardView cvMenuHome;
    //    private TextView tvHello;
    private TextView tvPekerjaanBelumSelesai;
    private TextView tvPekerjaanSelesai;
    private TextView tvPekerjaanRehap;
    private TextView tvPekerjaanVerifikasiBlmverifikasi;
    private TextView tvPekerjaanPekerjaanSemua;
    private CardView cvKlikInstagram;
    private CardView cvKlikWa;
    private ImageView ivRefresh;
    private TextView tvPoint;
    private TextView tvNamePegawai;
    private TextView tvSatker;
    private LottieAnimationView lavThumbUp;
    private TextView tvAkses;
    private ImageView ivLogout;
    private TextView scrollingtext;
    private TextView tvGood;
    private TextView tvTotalWorkTrd;
    private TextView tvTotalWorkPka;
    private TextView tvTotalWorkCabBarat;
    private TextView tvTotalWorkCabUtara;
    private TextView tvTotalWorkCabSelatan;
    private TextView tvTotalWorkCabTimur;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        satker = sharedPreferences.getString(Config.SHARED_SATKER, "");
        subSatker = sharedPreferences.getString(Config.SHARED_SUBSATKER, "");
        Log.d(TAG, "token: " + token);
        ConfigAds.banner(HomeActivity.this, adView);

        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setCancelable(false);

        scrollingtext.setSelected(true);

        getAuthMe();
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 11) {
            tvGood.setText("Good Morning \uD83C\uDF04");
            Config.showNotification(HomeActivity.this, "Pekerjaan Sudah Siap", "Semangat kerja !!!");
        } else if (timeOfDay >= 11 && timeOfDay < 15) {
            tvGood.setText("Good Afternoon \uD83C\uDF1E");
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            Config.showNotification(HomeActivity.this, "Saatnya Istirahat Sejenak", "Kalau lembur, jangan lupa klik LEMBUR YA!");
            tvGood.setText("Good Evening \uD83C\uDF25");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            Config.showNotification(HomeActivity.this, "Saatnya Tidur", "Kalau lembur, jangan lupa klik LEMBUR YA!");
            tvGood.setText("Good Night \uD83D\uDECC \uD83D\uDCA4");
        }
        tvNamePegawai.setText(name + " \uD83D\uDC4B");
        tvSatker.setText(satker + " - " + subSatker);

        ivLogout.setOnClickListener(v -> {
            MaterialDialog mDialog = new MaterialDialog.Builder(HomeActivity.this)
                    .setTitle("Logout dari aplikasi?")
                    .setCancelable(false)
                    .setNegativeButton("Tidak", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                    })
                    .setPositiveButton("Ya", (dialogInterface, which) -> {
                        ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Mohon tunggu");
                        progressDialog.show();
                        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL_PORTAL_PEGAWAI);
                        apiService.logout(token)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            progressDialog.cancel();
                                            dialogInterface.dismiss();
                                            Config.logout(HomeActivity.this);
                                            finishAffinity();
                                        } else {
                                            dialogInterface.dismiss();
                                            progressDialog.cancel();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(HomeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    })
                    .build();

            mDialog.show();
        });

        divSpk.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), HomeSPKActivity.class));
        });

        ivRefresh.setOnClickListener(view -> {
            getAuthMe();
        });

        cvKlikInstagram.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/iav_ariav/"));
            startActivity(browserIntent);
        });

        cvKlikWa.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=6283838191709&text=Halo%20iav..."));
            startActivity(browserIntent);
        });
    }

    private void getAuthMe() {
        progressDialog.setMessage("Mohon Tunggu ...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL_PORTAL_PEGAWAI);
        apiService.me(token)
                .enqueue(new Callback<LoginRootModel>() {
                    @Override
                    public void onResponse(Call<LoginRootModel> call, Response<LoginRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().getData().getPermissions());
                            List<String> list = response.body().getData().getPermissions();

                            // TODO save permission to SharedPref Array
                            Gson gson = new Gson();
                            String json = gson.toJson(list);
                            editor.putString(Config.SHARED_PERMISION_APPS, json);
                            editor.commit();

                            String sets = sharedPreferences.getString(Config.SHARED_PERMISION_APPS, null);
                            Log.d(TAG, "====== Permission Dashboard ====== ");
                            Log.d(TAG, "array :: " + sets);

                            getWarning();
                            getProgres();
                            getMonitoring();

                            if (list.contains("pekerjaan-teknik.menu-hidden-all-monitoring")) { // hanya untuk monitoring
                                divMenuAll.setVisibility(View.GONE);
                                cvMenuHome.setVisibility(View.GONE);
                            } else {
                                cvMenuHome.setVisibility(View.VISIBLE);
                                divMenuAll.setVisibility(View.VISIBLE);
                            }

                            if (list.contains("pekerjaan-teknik.menu_all")) {
                                divMenuAll.setVisibility(View.VISIBLE);
                                cvMenuHome.setVisibility(View.VISIBLE);
                                divContainerHubungiPti.setVisibility(View.GONE);

                                if (list.contains("pekerjaan-teknik.menu_spk")) {
                                    divSpk.setVisibility(View.VISIBLE);
                                } else {
                                    divSpk.setVisibility(View.GONE);
                                }

                            } else {
                                cvMenuHome.setVisibility(View.GONE);
                                divMenuAll.setVisibility(View.GONE);
                                divContainerHubungiPti.setVisibility(View.VISIBLE);
                            }

                        } else {
                            progressDialog.cancel();
                            Config.logout(HomeActivity.this);
                            finishAffinity();
                            Toast.makeText(HomeActivity.this, "Token kadaluarsa, lakukan login kembali", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Fail : " + response.code() + " | " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(HomeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getWarning() {
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.warning().enqueue(new Callback<WarningRootModel>() {
            @Override
            public void onResponse(Call<WarningRootModel> call, Response<WarningRootModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.cancel();
                    data = response.body().getData();
                    String image = data.get(0).getImage_header();
//                    Glide.with(HomeActivity.this).load(image).error(R.drawable.doc).override(512, 512).into(ivHeader);
                    scrollingtext.setText(data.get(0).getHello());

                    if (data.get(0).getUpdateApk().isEmpty()) {
                        Log.d(TAG, "update APK: empty");
                    } else {
                        ArrayList<String> updateApkList = new ArrayList<>();
                        for (int i = 0; i < data.get(0).getUpdateApk().size(); i++) {
                            Log.d(TAG, "update APK: " + data.get(0).getUpdateApk().get(i));
                            updateApkList.add(data.get(0).getUpdateApk().get(i));
                        }
                        String resultUpdateApk = TextUtils.join(", \n", updateApkList);
                        Log.d(TAG, "update APK: " + resultUpdateApk);

//                        if (!npp.contains("6908321002")) {
                        if (!resultUpdateApk.equals(BuildConfig.VERSION_NAME)) {
                            MaterialDialog mDialog = new MaterialDialog.Builder(HomeActivity.this)
                                    .setTitle("Aplikasi harus di update ke versi " + resultUpdateApk + ", \nsekarang memakai aplikasi versi " + BuildConfig.VERSION_NAME)
                                    .setMessage(data.get(0).getMessaggeUpdate().toString().replace("[", "").replace("]", "").replace(",", "\n"))
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
                            Log.d(TAG, "Apk Action: OK");
                        }
//                        }


                    }

                }
            }

            @Override
            public void onFailure(Call<WarningRootModel> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMonitoring() {
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.getMonitoring(token).enqueue(new Callback<MonitoringSPKRootModel>() {
            @Override
            public void onResponse(Call<MonitoringSPKRootModel> call, Response<MonitoringSPKRootModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.cancel();
                    String mBelumSelesai = response.body().getData().getMonitoringStatusBelumSelesai();
                    String mSelesai = response.body().getData().getMonitoringStatusSelesai();
                    String mRehap = response.body().getData().getMonitoringStatusRehap();
                    String mVerifikasiDanBelumDiverifikasi = response.body().getData().getMonitoringJumlahPekerjaanDiverifikasi() +
                            "/"
                            + response.body().getData().getMonitoringJumlahPekerjaanBelumDiverifikasi();
                    String mTotalPekerjaan = response.body().getData().getMonitoringJumlahPekerjaanSemua();
                    String mJumlahPekerjaanSemuaTRD = response.body().getData().getMonitoring_jumlah_pekerjaan_semua_TRD();
                    String mJumlahPekerjaanSemuaPKA = response.body().getData().getMonitoring_jumlah_pekerjaan_semua_PKA();
                    String mJumlahPekerjaanSemuaSLT = response.body().getData().getMonitoring_jumlah_pekerjaan_semua_SLT();
                    String mJumlahPekerjaanSemuaBRT = response.body().getData().getMonitoring_jumlah_pekerjaan_semua_BRT();
                    String mJumlahPekerjaanSemuaUTR = response.body().getData().getMonitoring_jumlah_pekerjaan_semua_UTR();
                    String mJumlahPekerjaanSemuaTMR = response.body().getData().getMonitoring_jumlah_pekerjaan_semua_TMR();

                    tvTotalWorkTrd.setText(mJumlahPekerjaanSemuaTRD + " Work");
                    tvTotalWorkPka.setText(mJumlahPekerjaanSemuaPKA + " Work");
                    tvTotalWorkCabSelatan.setText(mJumlahPekerjaanSemuaSLT + " Work");
                    tvTotalWorkCabBarat.setText(mJumlahPekerjaanSemuaBRT + " Work");
                    tvTotalWorkCabUtara.setText(mJumlahPekerjaanSemuaUTR + " Work");
                    tvTotalWorkCabTimur.setText(mJumlahPekerjaanSemuaTMR + " Work");

//                    tvPekerjaanBelumSelesai.setText(mBelumSelesai);
//                    tvPekerjaanSelesai.setText(mSelesai);
//                    tvPekerjaanRehap.setText(mRehap);
//                    tvPekerjaanVerifikasiBlmverifikasi.setText(mVerifikasiDanBelumDiverifikasi);
//                    tvPekerjaanPekerjaanSemua.setText(mTotalPekerjaan);

                }
            }

            @Override
            public void onFailure(Call<MonitoringSPKRootModel> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(HomeActivity.this, "Gagal mengambil data Monitoring", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProgres() {
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.progressMandor(token)
                .enqueue(new Callback<ProgressMandorRootModel>() {
                    @Override
                    public void onResponse(Call<ProgressMandorRootModel> call, Response<ProgressMandorRootModel> response) {
                        if (response.isSuccessful()) {
                            tvPoint.setText("\uD83C\uDF1F " + response.body().getData().getPoint() + " Points \uD83C\uDF1F");
                        }
                    }

                    @Override
                    public void onFailure(Call<ProgressMandorRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(HomeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        divMenuAll = findViewById(R.id.div_menu_all);
        divContainerHubungiPti = findViewById(R.id.div_container_hubungi_pti);
        divSpk = findViewById(R.id.div_spk);
        divAld = findViewById(R.id.div_ald);
//        divMonitoring = findViewById(R.id.div_monitoring);
        cvMenuHome = findViewById(R.id.cv_menu_home);
        tvPekerjaanBelumSelesai = findViewById(R.id.tv_pekerjaan_belum_selesai);
        tvPekerjaanSelesai = findViewById(R.id.tv_pekerjaan_selesai);
        tvPekerjaanRehap = findViewById(R.id.tv_pekerjaan_rehap);
        tvPekerjaanVerifikasiBlmverifikasi = findViewById(R.id.tv_pekerjaan_verifikasi_blmverifikasi);
        tvPekerjaanPekerjaanSemua = findViewById(R.id.tv_pekerjaan_pekerjaan_semua);
        cvKlikInstagram = findViewById(R.id.cv_klik_instagram);
        cvKlikWa = findViewById(R.id.cv_klik_wa);
        ivRefresh = findViewById(R.id.iv_refresh);
        tvPoint = findViewById(R.id.tv_point);
        tvNamePegawai = findViewById(R.id.tv_name_pegawai);
        tvSatker = findViewById(R.id.tv_satker);
        lavThumbUp = findViewById(R.id.lav_thumbUp);
        tvAkses = findViewById(R.id.tv_akses);
        ivLogout = findViewById(R.id.iv_logout);
        scrollingtext = findViewById(R.id.scrollingtext);
        tvGood = findViewById(R.id.tv_good);
        tvTotalWorkTrd = findViewById(R.id.tv_total_work_trd);
        tvTotalWorkPka = findViewById(R.id.tv_total_work_pka);
        tvTotalWorkCabBarat = findViewById(R.id.tv_total_work_cab_barat);
        tvTotalWorkCabUtara = findViewById(R.id.tv_total_work_cab_utara);
        tvTotalWorkCabSelatan = findViewById(R.id.tv_total_work_cab_selatan);
        tvTotalWorkCabTimur = findViewById(R.id.tv_total_work_cab_timur);
        adView = findViewById(R.id.adView);
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        MaterialDialog mDialog = new MaterialDialog.Builder(HomeActivity.this)
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
}