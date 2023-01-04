package id.pdamkotasmg.pekerjaan_teknik_feature.activity.home;

import static maes.tech.intentanim.CustomIntent.customType;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.an.deviceinfo.device.model.Device;
import com.google.android.gms.ads.AdView;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import id.pdamkotasmg.pekerjaan_teknik_feature.R;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.login.LoginSPKActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.spk.InputSpkActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.spk.VerifikasiActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.spk.callCenter.DaftarCallCenterActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.spk.riwayat.RiwayatAmbilPekerjaanActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.spk.riwayat.RiwayatSpkActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.spk.riwayat.RiwayatVerifikasiActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.webView.WebViewActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.adapter.WarningAdapter;
import id.pdamkotasmg.pekerjaan_teknik_feature.api.ApiConfig;
import id.pdamkotasmg.pekerjaan_teknik_feature.api.ApiService;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.akun.login.LoginRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.monitoring.MonitoringSPKRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.progressMandor.ProgressMandorRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.warning.DataItem;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.warning.WarningRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.utils.Config;
import id.pdamkotasmg.pekerjaan_teknik_feature.utils.ConfigAds;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeSPKActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private final String TAG = "debug";
    private static final int RC_CAMERA_AND_LOCATION = 1;
    private String firebaseToken;

    private String token;
    private String name;
    private String satker;
    private String subSatker;
    private String flag;
    private String tglSyncCallCenter;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private List<DataItem> data;
    private WarningAdapter warningAdapter;

    private AppUpdateManager appUpdateManager;

    private Device device;
    private String getOsVersion;
    private String getSdkVersion;

    private CardView cvKlikInstagram;
    private LinearLayout divContainerInputSpk;
    private LinearLayout divContainerRiwayatSpk;
    private CardView cvKlikWa;
    private LinearLayout divContainerVerifikasiPerbaikan;
    private LinearLayout divContainerRiwayatVerifikasi;
    //    private LinearLayout divContainerHubungiPti;
    private AdView adView;
    private LinearLayout divSurveyPelanggan;
    private LinearLayout divContainerCallCenter;
    private TextView tvAkses;
    private LinearLayout divContainerSyncCallCenter;
    private LinearLayout divProgress;
    private TextView tvPekerjaanDiambil;
    private TextView tvPekerjaanCabangSatker;
    private TextView tvPekerjaanSelesai;
    private TextView tvPekerjaanBelumSelesai;
    private RecyclerView rv;
    //    private ImageView ivHeader;
    private LinearLayout divContainerRiwayatAmbilPerbaikan;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvNameProgress;

    private ImageView ivRefresh;
    private TextView tvPoint;
    private TextView tvGood;
    private TextView tvNamePegawai;
    private TextView tvSatker;
    private TextView tvTotalWorkTrd;
    private TextView tvTotalWorkPka;
    private TextView tvTotalWorkCabBarat;
    private TextView tvTotalWorkCabUtara;
    private TextView tvTotalWorkCabSelatan;
    private TextView tvTotalWorkCabTimur;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_spkactivity);
        getSupportActionBar().hide();
        initView();

        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, HomeSPKActivity.this, "Home SPK");

        device = new Device(HomeSPKActivity.this);
        getOsVersion = device.getOsVersion();
        getSdkVersion = String.valueOf(device.getSdkVersion());
        Log.d(TAG, "getOsVersion: " + getOsVersion);

        if (Integer.parseInt(getSdkVersion) > 33) {
            finishAffinity();
            Toast.makeText(this, "Not Supported Android x004423 javLang:unsupportedOsVersion " + getSdkVersion, Toast.LENGTH_LONG).show();
        }

        methodRequiresTwoPermission();

        getTokenFirebase();
        ConfigAds.banner(HomeSPKActivity.this, adView);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        satker = sharedPreferences.getString(Config.SHARED_SATKER, "");
        subSatker = sharedPreferences.getString(Config.SHARED_SUBSATKER, "");
        Log.d(TAG, "token: " + token);

        tvNameProgress.setText("Progres per bulan " + name);

        progressDialog = new ProgressDialog(HomeSPKActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        getAuthMe();
        getProgres();
        getWarning();
        getMonitoring();

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 11) {
            tvGood.setText("Good Morning \uD83C\uDF04");
        } else if (timeOfDay >= 11 && timeOfDay < 15) {
            tvGood.setText("Good Afternoon \uD83C\uDF1E");
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            tvGood.setText("Good Evening \uD83C\uDF25");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            tvGood.setText("Good Night \uD83D\uDECC \uD83D\uDCA4");
        }
        tvNamePegawai.setText(name + " \uD83D\uDC4B");
        tvSatker.setText(satker + " - " + subSatker);

        divContainerInputSpk.setOnClickListener(v -> {
            String statusBooking = sharedPreferences.getString(Config.SHARED_STATU_BOOKING, "");
            if (statusBooking.contains("1")) {
                Toast.makeText(HomeSPKActivity.this, "Selesai/Batalkan Pekerjaan yang di Booking Sebelumnya", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(HomeSPKActivity.this, InputSpkActivity.class));
                customType(HomeSPKActivity.this, "fadein-to-fadeout");
            }
        });

        divContainerRiwayatSpk.setOnClickListener(v -> {
            startActivity(new Intent(HomeSPKActivity.this, RiwayatSpkActivity.class));
            customType(HomeSPKActivity.this, "fadein-to-fadeout");
        });

        divSurveyPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(HomeSPKActivity.this, WebViewActivity.class));
        });

        divContainerVerifikasiPerbaikan.setOnClickListener(v -> {
            startActivity(new Intent(HomeSPKActivity.this, VerifikasiActivity.class));
            customType(HomeSPKActivity.this, "fadein-to-fadeout");
        });

        divContainerRiwayatVerifikasi.setOnClickListener(v -> {
            startActivity(new Intent(HomeSPKActivity.this, RiwayatVerifikasiActivity.class));
            customType(HomeSPKActivity.this, "fadein-to-fadeout");
        });

        cvKlikInstagram.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/iav_ariav/"));
            startActivity(browserIntent);
        });

        cvKlikWa.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=6283838191709&text=Halo%20iav..."));
            startActivity(browserIntent);
        });

        divContainerCallCenter.setOnClickListener(v -> {
//            startActivity(new Intent(HomeSPKActivity.this, DaftarCallCenterActivity.class));
            Intent intent = new Intent(getApplicationContext(), DaftarCallCenterActivity.class);
            customType(HomeSPKActivity.this, "fadein-to-fadeout");
            editor.putString(Config.SHARED_KD_AMBIL_PEKERJAAN, "0");
            editor.apply();
            startActivity(intent);
        });

        divContainerSyncCallCenter.setOnClickListener(v -> {
//            getSyncPengaduan();
            flag = "syncCallCenter";
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    HomeSPKActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setThemeDark(true);
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });
        ivRefresh.setOnClickListener(v -> {
            getAuthMe();
            getProgres();
            getWarning();
            getMonitoring();
//            showNotification(HomeSPKActivity.this, "Berhasil singkron", "Mantappss");
//            showNotificationMessage(HomeSPKActivity.this, "Berhasil singkron", "Mantappss");
        });

        divContainerRiwayatAmbilPerbaikan.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RiwayatAmbilPekerjaanActivity.class);
            editor.putString(Config.SHARED_KD_AMBIL_PEKERJAAN, "1");
            editor.apply();
            startActivity(intent);
        });
    }

    public static void showNotification(Context context, String title, String content) {
        int noificationId = new Random().nextInt(100);
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.notif);
        Log.d("debug", "showNotification: " + sound);
        String channelId = "notification_channel_3";
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), LoginSPKActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),
                0, intent, PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context.getApplicationContext(), channelId
        );

//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceBitmap);

//        Bitmap bitmap = null;
//        try {
//            URL url = new URL(resourceBitmapLink);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            bitmap = BitmapFactory.decodeStream(input);
//        } catch (IOException e) {
//            // Log exception
//        }


        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setDefaults(NotificationCompat.PRIORITY_MAX);
        builder.setContentTitle(title); // make suer change the channel for image
        builder.setContentText(content);
        builder.setSound(sound);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        //notification for image
//        Glide.with(context).asBitmap().load("https://www.google.es/images/srpr/logo11w.png").into(new CustomTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//
//            }
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//            }
//        });

//        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.
                    getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId, "Notification channel 1",
                        NotificationManager.IMPORTANCE_HIGH
                );
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
                notificationChannel.setDescription(content);
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationChannel.setSound(sound, attributes); // This is IMPORTANT
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(noificationId, notification);
        }
    }

    private void getSyncPengaduan() {
        progressDialog.setMessage("Sinkronisasi Call Center ....");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.syncPengaduan(tglSyncCallCenter, "1")
                .enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            Toast.makeText(HomeSPKActivity.this, "Sinkronisasi Berhasil", Toast.LENGTH_SHORT).show();
//                            getAuthMe();
                        } else {
                            Toast.makeText(HomeSPKActivity.this, "Sinkronisasi Pengaduan Gagal, Hubungi PTI " + response.message(), Toast.LENGTH_SHORT).show();
                            tvAkses.setText("Sinkronisasi Pengaduan Gagal, Hubungi PTI");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(HomeSPKActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    editor.putString(Config.SHARED_FIREBASE_TOKEN, firebaseToken);
                    editor.apply();

                    // Log and toast
                    Log.d("debug", "getTokenFirebase: " + firebaseToken);
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

//                            if (list.contains("pekerjaan-teknik.menu_spk")) {
//                                divSpk.setVisibility(View.VISIBLE);
                            // TODO ADS/IKLAN
                            if (list.contains("pekerjaan-teknik.ads-banner")) {
                                Log.d(TAG, "pekerjaan-teknik.ads-banner: true ");
                                adView.setVisibility(View.VISIBLE);
                            } else {
                                Log.d(TAG, "fail: ");
                                adView.setVisibility(View.GONE);
                            }

                            // TODO input SPK
                            if (list.contains("pekerjaan-teknik.input-riwayat-spk")) {
                                Log.d(TAG, "pekerjaan-teknik.input-riwayat-spkk: true ");
                                divContainerInputSpk.setVisibility(View.VISIBLE);
                                divContainerRiwayatSpk.setVisibility(View.VISIBLE);
                                divSurveyPelanggan.setVisibility(View.VISIBLE);
                                divContainerCallCenter.setVisibility(View.VISIBLE);
                                divContainerSyncCallCenter.setVisibility(View.VISIBLE);
                                divContainerRiwayatAmbilPerbaikan.setVisibility(View.VISIBLE);

                                divProgress.setVisibility(View.VISIBLE);
                                getProgres();

//                                    divContainerHubungiPti.setVisibility(View.GONE);
                            } else {
                                Log.d(TAG, "fail: ");
                                divContainerInputSpk.setVisibility(View.GONE);
                                divContainerRiwayatSpk.setVisibility(View.GONE);
                                divSurveyPelanggan.setVisibility(View.GONE);
                                divContainerCallCenter.setVisibility(View.GONE);
                                divContainerSyncCallCenter.setVisibility(View.GONE);
                                divContainerRiwayatAmbilPerbaikan.setVisibility(View.GONE);
                                divProgress.setVisibility(View.GONE);
//                                    divContainerHubungiPti.setVisibility(View.VISIBLE);
                            }

                            // TODO Verifikasi SPK
                            if (list.contains("pekerjaan-teknik.verifikasi-spk-kasub")) {
                                Log.d(TAG, "pekerjaan-teknik.verifikasi-spk-kasub: true ");
                                divContainerVerifikasiPerbaikan.setVisibility(View.VISIBLE);
//                                    divContainerHubungiPti.setVisibility(View.GONE);
                            } else {
                                Log.d(TAG, "fail: ");
                                divContainerVerifikasiPerbaikan.setVisibility(View.GONE);
                            }

                            // TODO Riwayat verifikasi SPK
                            if (list.contains("pekerjaan-teknik.riwayat-spk-kasub")) {
                                Log.d(TAG, "pekerjaan-teknik.riwayat-spk-kasub: true ");
                                divContainerRiwayatVerifikasi.setVisibility(View.VISIBLE);
//                                    divContainerHubungiPti.setVisibility(View.GONE);
                            } else {
                                Log.d(TAG, "fail: ");
                                divContainerRiwayatVerifikasi.setVisibility(View.GONE);
                            }
                            progressDialog.cancel();
//                            } else {
//                                progressDialog.cancel();
//                                divSpk.setVisibility(View.GONE);
//                                Toast.makeText(HomeSPKActivity.this, "Acces Denied", Toast.LENGTH_SHORT).show();
//                            }
                        } else {
                            divContainerVerifikasiPerbaikan.setVisibility(View.GONE);
                            progressDialog.cancel();
                            Config.logout(HomeSPKActivity.this);
                            finishAffinity();
                            Toast.makeText(HomeSPKActivity.this, "Token kadaluarsa, lakukan login kembali", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Fail : " + response.code() + " | " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRootModel> call, Throwable t) {
                        divContainerVerifikasiPerbaikan.setVisibility(View.GONE);
                        progressDialog.cancel();
                        Toast.makeText(HomeSPKActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getWarning() {
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.warning().enqueue(new Callback<WarningRootModel>() {
            @Override
            public void onResponse(Call<WarningRootModel> call, Response<WarningRootModel> response) {
                if (response.isSuccessful()) {
                    data = response.body().getData();
                    warningAdapter = new WarningAdapter(HomeSPKActivity.this, data);
                    rv.setAdapter(warningAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(HomeSPKActivity.this));
                    warningAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WarningRootModel> call, Throwable t) {
                Toast.makeText(HomeSPKActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProgres() {
        progressDialog.setMessage("Ambil data progress");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL);
        apiService.progressMandor(token)
                .enqueue(new Callback<ProgressMandorRootModel>() {
                    @Override
                    public void onResponse(Call<ProgressMandorRootModel> call, Response<ProgressMandorRootModel> response) {
                        if (response.isSuccessful()) {
                            tvPekerjaanDiambil.setText(response.body().getData().getPekerjaanNpp());
                            tvPekerjaanCabangSatker.setText(response.body().getData().getCabBlnIni());
                            tvPekerjaanSelesai.setText(response.body().getData().getJmlBulanSelesai());
                            tvPekerjaanBelumSelesai.setText(response.body().getData().getJmlBulanBlmSelesai());
                            tvPoint.setText("\uD83C\uDF1F " + response.body().getData().getPoint() + " Points \uD83C\uDF1F");
                            progressDialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProgressMandorRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(HomeSPKActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HomeSPKActivity.this, "Gagal mengambil data Monitoring", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        cvKlikInstagram = findViewById(R.id.cv_klik_instagram);
        divContainerInputSpk = findViewById(R.id.div_container_input_spk);
        divContainerRiwayatSpk = findViewById(R.id.div_container_riwayat_spk);
        cvKlikWa = findViewById(R.id.cv_klik_wa);
        divContainerVerifikasiPerbaikan = findViewById(R.id.div_container_verifikasi_perbaikan);
        divContainerRiwayatVerifikasi = findViewById(R.id.div_container_riwayat_verifikasi);
//        divContainerHubungiPti = findViewById(R.id.div_container_hubungi_pti);
        adView = findViewById(R.id.adView);
        divSurveyPelanggan = findViewById(R.id.div_survey_pelanggan);
        divContainerCallCenter = findViewById(R.id.div_container_call_center);
        tvAkses = findViewById(R.id.tv_akses);
        divContainerSyncCallCenter = findViewById(R.id.div_container_sync_call_center);
        divProgress = findViewById(R.id.div_progress);
        tvPekerjaanDiambil = findViewById(R.id.tv_pekerjaan_diambil);
        tvPekerjaanCabangSatker = findViewById(R.id.tv_pekerjaan_cabang_satker);
        tvPekerjaanSelesai = findViewById(R.id.tv_pekerjaan_selesai);
        tvPekerjaanBelumSelesai = findViewById(R.id.tv_pekerjaan_belum_selesai);
        rv = findViewById(R.id.rv);
//        ivHeader = findViewById(R.id.iv_header);
        divContainerRiwayatAmbilPerbaikan = findViewById(R.id.div_container_riwayat_ambil_perbaikan);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvNameProgress = findViewById(R.id.tv_name_progress);
        ivRefresh = findViewById(R.id.iv_refresh);
        tvPoint = findViewById(R.id.tv_point);
        tvGood = findViewById(R.id.tv_good);
        tvNamePegawai = findViewById(R.id.tv_name_pegawai);
        tvSatker = findViewById(R.id.tv_satker);
        tvTotalWorkTrd = findViewById(R.id.tv_total_work_trd);
        tvTotalWorkPka = findViewById(R.id.tv_total_work_pka);
        tvTotalWorkCabBarat = findViewById(R.id.tv_total_work_cab_barat);
        tvTotalWorkCabUtara = findViewById(R.id.tv_total_work_cab_utara);
        tvTotalWorkCabSelatan = findViewById(R.id.tv_total_work_cab_selatan);
        tvTotalWorkCabTimur = findViewById(R.id.tv_total_work_cab_timur);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA
                , Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_WIFI_STATE
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.WRITE_SECURE_SETTINGS
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(HomeSPKActivity.this, perms)) {
            // Already have permission, do the thing
            Log.d(TAG, "methodRequiresTwoPermission: Sukses");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (flag.equals("syncCallCenter")) {
//            String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            tglSyncCallCenter = date;
            Log.d(TAG, "tglSyncCallCenter: " + tglSyncCallCenter);
            getSyncPengaduan();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        MaterialDialog mDialog = new MaterialDialog.Builder(HomeSPKActivity.this)
                .setTitle("Kembali ke GOOD DAY?")
                .setCancelable(false)
                .setNegativeButton("Tidak", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton("Ya", (dialogInterface, which) -> {
                    finish();
                })
                .build();

        mDialog.show();
    }
}