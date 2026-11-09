package co.id.pdamkotasmg.pekerjaanteknik.activity.home;

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
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.pdamkotasmg.goodday.fitur.authentication.login.LoginActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.InputSpkActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.VerifikasiActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.callCenter.DaftarCallCenterActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat.RiwayatAmbilPekerjaanActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat.RiwayatSpkActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat.RiwayatVerifikasiActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.webView.WebViewActivity;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.WarningAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.akun.login.LoginRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.progressMandor.ProgressMandorRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.warning.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.warning.WarningRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import co.id.pdamkotasmg.pekerjaanteknik.utils.ConfigAds;
import co.id.pdamkotasmg.pekerjaanteknik.view.AnimasiPipaView;
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

    private CardView cvKlikFacebook;
    private CardView cvKlikGmail;
    private LinearLayout divContainerInputSpk;
    private LinearLayout divContainerRiwayatSpk;
    private TextView toolbarName;
    private TextView toolbarSatkerSubsatker;
    private CardView cvKlikWa;
    private LinearLayout divContainerVerifikasiPerbaikan;
    private LinearLayout divContainerRiwayatVerifikasi;
    //    private LinearLayout divContainerHubungiPti;
    private AdView adView;

    // Kartu utama beranimasi (pipa, aliran air, katup, manometer, perbaikan bocor)
    // dan seluruh gerak halaman yang diatur GerakHome.
    private View divHero;
    private AnimasiPipaView vAnimasiPipa;
    private NestedScrollView svHomeSpk;
    private View divIsiHome;
    private View divTulisanHero;
    private View divHeroAtas;
    private View divLembarHome;
    private TextView tvSapaan;
    private TextView tvNamaPengguna;
    private TextView tvSatkerPengguna;
    private TextView tvTanggalHome;
    private View vBarLatar;
    private View vGarisAliran;
    private View vKembaliKaca;
    private View vKembaliPadat;
    private View divMenuKerangka;
    private View divMenuGagal;
    private View btnMenuCobaLagi;
    private View divJudulProgres;
    private View divBantuan;
    private View divIklanHome;
    private View divSloganHome;
    private GerakHome gerak;
    // Mencegah permintaan ganda: dulu dicegah oleh dialog modal yang menutup layar.
    private boolean sedangMuatAkses = false;
    private boolean sedangMuatProgres = false;
    private LinearLayout divSurveyPelanggan;
    private LinearLayout divContainerCallCenter;
    private TextView tvAkses;
    private LinearLayout divContainerSyncCallCenter;
    private LinearLayout divProgress;
    private TextView tvPekerjaanDiambil;
    private TextView tvPekerjaanCabangSatker;
    private TextView tvPekerjaanSelesai;
    private TextView tvPekerjaanBelumSelesai;
    private ImageView ivRefreshProgress;
    private RecyclerView rv;
    //    private ImageView ivHeader;
    private LinearLayout divContainerRiwayatAmbilPerbaikan;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvNameProgress;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_spkactivity);
        
        initView();

        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, HomeSPKActivity.this, "Home SPK");

//        if (Integer.parseInt(getSdkVersion) > 33) {
//            finishAffinity();
//            Toast.makeText(this, "Not Supported Android x004423 javLang:unsupportedOsVersion " + getSdkVersion, Toast.LENGTH_LONG).show();
//        }

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

        isiSapaan();
        siapkanGerak();

//        getSyncPengaduan();
        getAuthMe();

        btnMenuCobaLagi.setOnClickListener(v -> getAuthMe());

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
        ivRefreshProgress.setOnClickListener(v -> {
            // Notifikasi hanya untuk penyegaran manual, bukan tiap layar dibuka.
            getProgres(true);
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
        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
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


        builder.setSmallIcon(com.pdamkotasmg.goodday.R.drawable.ic_launcher_foreground);
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
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
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
                            // tv_akses hanya ada di layout HomeActivity, tidak di layar ini,
                            // sehingga baris ini dulu menutup aplikasi setiap sinkron gagal.
                            if (tvAkses != null) {
                                tvAkses.setText("Sinkronisasi Pengaduan Gagal, Hubungi PTI");
                            }
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
        if (sedangMuatAkses) {
            return;
        }
        // Dulu memakai dialog modal "Mohon Tunggu" yang meredupkan seluruh layar,
        // sehingga animasi Home baru terlihat saat pengguna kembali ke layar ini.
        // Sekarang kerangka menu berdenyut di tempatnya.
        siapMuatMenu();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL_PORTAL_PEGAWAI);
        apiService.me(token)
                .enqueue(new Callback<LoginRootModel>() {
                    @Override
                    public void onResponse(Call<LoginRootModel> call, Response<LoginRootModel> response) {
                        // Tanpa dialog modal pengguna bisa keluar sebelum jawaban datang.
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().getData().getPermissions());
                            List<String> list = response.body().getData().getPermissions();

                            // TODO save permission to SharedPref Array
                            Gson gson = new Gson();
                            String json = gson.toJson(list);
                            editor.putString(Config.SHARED_PERMISION_APPS, json);
                            // apply() menulis ke memori seketika lalu menyimpan ke disk di latar,
                            // commit() menahan main thread sampai proses tulis disk selesai.
                            editor.apply();

                            String sets = sharedPreferences.getString(Config.SHARED_PERMISION_APPS, null);
                            Log.d(TAG, "====== Permission Dashboard ====== ");
                            Log.d(TAG, "array :: " + sets);

//                            if (list.contains("pekerjaan-teknik.menu_spk")) {
//                                divSpk.setVisibility(View.VISIBLE);
                            // TODO ADS/IKLAN
                            if (list.contains("pekerjaan-teknik.ads-banner")) {
                                adView.setVisibility(View.VISIBLE);
                            } else {
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
                                getProgres(false);
                                getWarning();

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
                            selesaiMuatMenu(true);
//                            } else {
//                                progressDialog.cancel();
//                                divSpk.setVisibility(View.GONE);
//                                Toast.makeText(HomeSPKActivity.this, "Acces Denied", Toast.LENGTH_SHORT).show();
//                            }
                        } else {
                            divContainerVerifikasiPerbaikan.setVisibility(View.GONE);
                            selesaiMuatMenu(false);
                            Config.logout(HomeSPKActivity.this);
                            finishAffinity();
                            Toast.makeText(HomeSPKActivity.this, "Token kadaluarsa, lakukan login kembali", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Fail : " + response.code() + " | " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRootModel> call, Throwable t) {
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        divContainerVerifikasiPerbaikan.setVisibility(View.GONE);
                        selesaiMuatMenu(false);
                        // Pengguna bisa mencoba lagi tanpa keluar lalu membuka ulang layar.
                        divMenuGagal.setAlpha(0f);
                        divMenuGagal.setVisibility(View.VISIBLE);
                        divMenuGagal.animate().alpha(1f).setDuration(250).start();
                        Toast.makeText(HomeSPKActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getWarning() {
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.warning().enqueue(new Callback<WarningRootModel>() {
            @Override
            public void onResponse(Call<WarningRootModel> call, Response<WarningRootModel> response) {
                if (response.isSuccessful()) {
                    data = response.body().getData();
                    warningAdapter = new WarningAdapter(HomeSPKActivity.this, data);
                    rv.setAdapter(warningAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(HomeSPKActivity.this));
                    warningAdapter.notifyDataSetChanged();
                    // Kartu info muncul berurutan (android:layoutAnimation di content_spk).
                    rv.scheduleLayoutAnimation();
                }
            }

            @Override
            public void onFailure(Call<WarningRootModel> call, Throwable t) {
                Toast.makeText(HomeSPKActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProgres(boolean tampilkanNotifikasi) {
        if (sedangMuatProgres) {
            return;
        }
        // Dulu dialog modal "Ambil data progress". Sekarang ikon segarkan berputar
        // dan angka berdenyut di tempatnya, tanpa menutup layar.
        mulaiMuatProgres();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.progressMandor(token)
                .enqueue(new Callback<ProgressMandorRootModel>() {
                    @Override
                    public void onResponse(Call<ProgressMandorRootModel> call, Response<ProgressMandorRootModel> response) {
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        selesaiMuatProgres();
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            // Angka menghitung naik; teks akhirnya tetap persis nilai dari server.
                            gerak.hitungNaik(tvPekerjaanDiambil, response.body().getData().getPekerjaanNpp());
                            gerak.hitungNaik(tvPekerjaanCabangSatker, response.body().getData().getCabBlnIni());
                            gerak.hitungNaik(tvPekerjaanSelesai, response.body().getData().getJmlBulanSelesai());
                            gerak.hitungNaik(tvPekerjaanBelumSelesai, response.body().getData().getJmlBulanBlmSelesai());
                            // getWarning() sudah dipanggil dari getAuthMe, pemanggilan kedua di
                            // sini membuat permintaan ganda setiap kali layar dibuka.
                            if (!tampilkanNotifikasi) {
                                return;
                            }
                            showNotification(HomeSPKActivity.this, "Berhasil singkron SPK", "Ayo Kerja \uD83E\uDD38\uD83C\uDFFD\u200D♂️⛹\uD83C\uDFFD\u200D♀️ ⛹\uD83C\uDFFD ⛹\uD83C\uDFFD\u200D♂️ \uD83C\uDFCB\uD83C\uDFFD\u200D♀️ \uD83C\uDFCB\uD83C\uDFFD \uD83C\uDFCB\uD83C\uDFFD\u200D♂️");
                        } else {
                            Toast.makeText(HomeSPKActivity.this, "Gagal mengambil data progress", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProgressMandorRootModel> call, Throwable t) {
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        selesaiMuatProgres();
                        Toast.makeText(HomeSPKActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ---------------------------------------------------------------------
    // Tampilan Home: sapaan, gerak, dan keadaan pemuatan (tidak mengubah data)
    // ---------------------------------------------------------------------

    /** Sapaan sesuai jam, nama petugas, unit kerja, dan tanggal hari ini. */
    private void isiSapaan() {
        tvSapaan.setText(sapaanWaktu() + ",");
        String nama = name == null ? "" : name.trim();
        tvNamaPengguna.setText(nama.isEmpty() ? "Petugas Teknik" : nama);
        String unit = gabungUnit(satker, subSatker);
        if (unit.isEmpty()) {
            tvSatkerPengguna.setVisibility(View.GONE);
        } else {
            tvSatkerPengguna.setText(unit);
        }
        tvTanggalHome.setText(new SimpleDateFormat("EEE, d MMM yyyy", new Locale("id", "ID")).format(new Date()));
    }

    private String sapaanWaktu() {
        int jam = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (jam >= 4 && jam < 11) {
            return "Selamat pagi";
        } else if (jam >= 11 && jam < 15) {
            return "Selamat siang";
        } else if (jam >= 15 && jam < 18) {
            return "Selamat sore";
        }
        return "Selamat malam";
    }

    private String gabungUnit(String a, String b) {
        String kiri = a == null ? "" : a.trim();
        String kanan = b == null ? "" : b.trim();
        if (kiri.isEmpty()) {
            return kanan;
        }
        if (kanan.isEmpty()) {
            return kiri;
        }
        return kiri + " · " + kanan;
    }

    /** Delapan ubin menu, urut sesuai tampilan. */
    private LinearLayout[] daftarUbin() {
        return new LinearLayout[]{
                divContainerInputSpk, divContainerRiwayatSpk, divContainerRiwayatAmbilPerbaikan,
                divContainerCallCenter, divContainerSyncCallCenter, divSurveyPelanggan,
                divContainerVerifikasiPerbaikan, divContainerRiwayatVerifikasi};
    }

    /** Kartu pembungkus tiap ubin (yang dianimasikan). */
    private View[] kartuUbin() {
        LinearLayout[] ubin = daftarUbin();
        View[] kartu = new View[ubin.length];
        for (int i = 0; i < ubin.length; i++) {
            kartu[i] = ubin[i] == null ? null : (View) ubin[i].getParent();
        }
        return kartu;
    }

    private void siapkanGerak() {
        gerak = new GerakHome(svHomeSpk, divIsiHome, divHero, divTulisanHero, vAnimasiPipa,
                vBarLatar, vGarisAliran, tvHeaderJudul,
                ivHeaderBackArrow, vKembaliKaca, vKembaliPadat,
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.pdam_blue_dark));
        gerak.pasang();
        gerak.animasiPembuka(divLembarHome, divHeroAtas, tvSapaan, tvNamaPengguna, tvSatkerPengguna);
        gerak.daftarkanMuncul(divJudulProgres, divProgress, divBantuan, divIklanHome, divSloganHome);

        for (LinearLayout ubin : daftarUbin()) {
            if (ubin != null) {
                gerak.pasangEfekTekan(ubin, (View) ubin.getParent());
            }
        }
        gerak.pasangEfekTekan(cvKlikWa, cvKlikWa);

        // Mengetuk judul bar menggulir halus kembali ke atas.
        tvHeaderJudul.setOnClickListener(v -> gerak.gulirKeAtas());
    }

    private void siapMuatMenu() {
        sedangMuatAkses = true;
        divMenuGagal.setVisibility(View.GONE);
        divMenuKerangka.animate().cancel();
        divMenuKerangka.setAlpha(1f);
        divMenuKerangka.setVisibility(View.VISIBLE);
        gerak.mulaiDenyut(divMenuKerangka);
    }

    private void selesaiMuatMenu(boolean berhasil) {
        sedangMuatAkses = false;
        // Kerangka dilepas seketika, tidak memudar: bila ubin yang tampil lebih sedikit
        // dari delapan kerangka (misal pengguna kasub), isi di bawahnya tidak "melompat"
        // setelah kerangka hilang. Ubin sendiri masuk berurutan dengan pantulan kecil.
        gerak.hentikanDenyut(divMenuKerangka, false);
        divMenuKerangka.setVisibility(View.GONE);
        if (berhasil) {
            gerak.masukkanMenu(kartuUbin());
        }
    }

    private void mulaiMuatProgres() {
        sedangMuatProgres = true;
        gerak.mulaiPutar(ivRefreshProgress);
        gerak.mulaiDenyut(tvPekerjaanDiambil);
        gerak.mulaiDenyut(tvPekerjaanCabangSatker);
        gerak.mulaiDenyut(tvPekerjaanSelesai);
        gerak.mulaiDenyut(tvPekerjaanBelumSelesai);
    }

    private void selesaiMuatProgres() {
        sedangMuatProgres = false;
        gerak.hentikanPutar(ivRefreshProgress);
        gerak.hentikanDenyut(tvPekerjaanDiambil, false);
        gerak.hentikanDenyut(tvPekerjaanCabangSatker, false);
        gerak.hentikanDenyut(tvPekerjaanSelesai, false);
        gerak.hentikanDenyut(tvPekerjaanBelumSelesai, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        if (gerak != null) {
            gerak.setLayarAktif(true);
        }
    }

    @Override
    protected void onPause() {
        // Animasi dijeda saat layar lain terbuka; dulu terus berjalan di latar.
        if (gerak != null) {
            gerak.setLayarAktif(false);
        }
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // Tanpa pembersihan ini, animasi dan AdView terus berjalan serta menahan Activity.
        if (gerak != null) {
            gerak.lepas();
        }
        if (adView != null) {
            adView.destroy();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    private void initView() {
        divContainerInputSpk = findViewById(R.id.div_container_input_spk);
        divContainerRiwayatSpk = findViewById(R.id.div_container_riwayat_spk);
        cvKlikWa = findViewById(R.id.cv_klik_wa);
        divContainerVerifikasiPerbaikan = findViewById(R.id.div_container_verifikasi_perbaikan);
        divContainerRiwayatVerifikasi = findViewById(R.id.div_container_riwayat_verifikasi);
//        divContainerHubungiPti = findViewById(R.id.div_container_hubungi_pti);
        adView = findViewById(R.id.adView);
        divHero = findViewById(R.id.div_hero);
        vAnimasiPipa = findViewById(R.id.v_animasi_pipa);
        svHomeSpk = findViewById(R.id.sv_home_spk);
        divIsiHome = findViewById(R.id.div_isi_home);
        divTulisanHero = findViewById(R.id.div_tulisan_hero);
        divHeroAtas = findViewById(R.id.div_hero_atas);
        divLembarHome = findViewById(R.id.div_lembar_home);
        tvSapaan = findViewById(R.id.tv_sapaan);
        tvNamaPengguna = findViewById(R.id.tv_nama_pengguna);
        tvSatkerPengguna = findViewById(R.id.tv_satker_pengguna);
        tvTanggalHome = findViewById(R.id.tv_tanggal_home);
        vBarLatar = findViewById(R.id.v_bar_latar);
        vGarisAliran = findViewById(R.id.v_garis_aliran);
        vKembaliKaca = findViewById(R.id.v_kembali_kaca);
        vKembaliPadat = findViewById(R.id.v_kembali_padat);
        divMenuKerangka = findViewById(R.id.div_menu_kerangka);
        divMenuGagal = findViewById(R.id.div_menu_gagal);
        btnMenuCobaLagi = findViewById(R.id.btn_menu_coba_lagi);
        divJudulProgres = findViewById(R.id.div_judul_progres);
        divBantuan = findViewById(R.id.div_bantuan);
        divIklanHome = findViewById(R.id.div_iklan_home);
        divSloganHome = findViewById(R.id.div_slogan_home);
        divSurveyPelanggan = findViewById(R.id.div_survey_pelanggan);
        divContainerCallCenter = findViewById(R.id.div_container_call_center);
        tvAkses = findViewById(R.id.tv_akses);
        divContainerSyncCallCenter = findViewById(R.id.div_container_sync_call_center);
        divProgress = findViewById(R.id.div_progress);
        tvPekerjaanDiambil = findViewById(R.id.tv_pekerjaan_diambil);
        tvPekerjaanCabangSatker = findViewById(R.id.tv_pekerjaan_cabang_satker);
        tvPekerjaanSelesai = findViewById(R.id.tv_pekerjaan_selesai);
        tvPekerjaanBelumSelesai = findViewById(R.id.tv_pekerjaan_belum_selesai);
        ivRefreshProgress = findViewById(R.id.iv_refresh_progress);
        rv = findViewById(R.id.rv);
//        ivHeader = findViewById(R.id.iv_header);
        divContainerRiwayatAmbilPerbaikan = findViewById(R.id.div_container_riwayat_ambil_perbaikan);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvNameProgress = findViewById(R.id.tv_name_progress);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        // Daftar lama memuat izin yang tidak pernah bisa diberikan ke aplikasi biasa
        // (WRITE_SECURE_SETTINGS) dan izin penyimpanan yang tidak berlaku lagi di
        // Android 13+. Akibatnya hasil pengecekan selalu gagal dan kotak izin muncul
        // setiap kali layar dibuka. Sekarang hanya izin yang benar-benar dipakai.
        List<String> daftarIzin = new ArrayList<>();
        daftarIzin.add(Manifest.permission.CAMERA);
        daftarIzin.add(Manifest.permission.ACCESS_FINE_LOCATION);
        daftarIzin.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Tanpa izin ini notifikasi tidak pernah muncul di Android 13+.
            daftarIzin.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        String[] perms = daftarIzin.toArray(new String[0]);
        if (EasyPermissions.hasPermissions(HomeSPKActivity.this, perms)) {
            // Already have permission, do the thing
            Log.d(TAG, "methodRequiresTwoPermission: Sukses");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(com.pdamkotasmg.goodday.R.string.app_name),
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
}