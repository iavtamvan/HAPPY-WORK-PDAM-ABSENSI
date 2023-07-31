package com.pdamkotasmg.goodday.utils;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.authentication.login.LoginActivity;
import com.pdamkotasmg.goodday.fitur.dashboard.DashboardActivity;
import com.pdamkotasmg.goodday.fitur.presensi.PresensiActivity;
import com.pdamkotasmg.goodday.fitur.splash.SplashScreenActivity;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class Config {
    public static final String APLICATION_NAME = "Happy Work";

    //API KEY
    public static final String BASE_URL_IMAGE = "https://app.pdamkotasmg.co.id/api-gw-dev/portal-pegawai";
    public static final String BASE_URL_IMAGE_HANDLER = "https://gateway.pdamkotasmg.co.id/api-gw-dev/file-handler/foto/?filename=";

    public static final String BASE_URL_NOTIF_FOTO_FAIL = "https://image.freepik.com/free-vector/people-with-sad-angry-emojis-illustration_53876-43293.jpg";
    public static final String BASE_URL_NOTIF_JIKA_TELAT = "https://image.freepik.com/free-vector/people-run-open-door-being-late-men-women-hurry-end-beginning-working-office-day-illustration_80590-9275.jpg";
    public static final String BASE_URL_NOTIF_JIKA_PULANG_AWAL = "https://image.freepik.com/free-vector/businessman-leaving-comfort-zone-flat-illustration_74855-16768.jpg";
    public static final String BASE_URL_NOTIF_NORMAL = "https://image.freepik.com/free-vector/people-jumping-trampoline_74855-4453.jpg";
    public static final String BASE_URL_NOTIF_ERROR = "https://image.freepik.com/free-vector/oops-404-error-with-broken-robot-concept-illustration_114360-1932.jpg";

    public static final String ERROR_MSG = "Pengambilan data Gagal, coba lagi nanti";
    public static final String ERROR_PASSWORD = "Pastikan Kata Sandi Anda Sama";
    public static final String ERROR_DATA_REGISTER = "Pastikan Data Anda Dengan Benar";
    public static final String ERROR_SESSION = "Sesi login anda telah habis, Login Ulang";
    public static final String ERROR_FAKE_GPS_TITLE = "Akun di NONAKTIFKAN \n Berbohong adalah suatu tindakan tidak terpuji \uD83D\uDE0A";
    public static final String ERROR_ANDROID = "Failed to measure fs-verity: Not Supported Android x004423 javLang:unsupportedOsVersion";

    public static final String SHARED_PERMISION_APPS = "permision_apps";

    //data akun
    public static final String SHARED_PREF_NAME = "HAPPY-WORK";
    public static final String SHARED_GETMODEL = "getModel";
    public static final String SHARED_GETPRODUCT = "getProduct";
    public static final String SHARED_GETDEVICE = "getDevice";
    public static final String SHARED_GETBUILDBRAND = "getBuildBrand";
    public static final String SHARED_GETOSVERSION = "getOsVersion";
    public static final String SHARED_GETSDKVERSION = "getSdkVersion";
    public static final String SHARED_GETBUILDNUMBER = "getBuildNumber";
    public static final String SHARED_GETBUILDINCREMENTAL = "getBuildIncremental";
    public static final String SHARED_GETPASSWORD = "getpassword";
    public static final String SHARED_IPADRESS = "ipAdress";
    public static final String SHARED_CONNECTIONTYPE = "connectionType";
    public static final String SHARED_HWID = "hwid";
    public static final String SHARED_SSID = "ssid";
    public static final String SHARED_ADDRESS_GPS = "address_gps";
    public static final String SHARED_CITY = "city";
    public static final String SHARED_STATE = "state";
    public static final String SHARED_COUNTRY = "country";
    public static final String SHARED_POSTALCODE = "postalCode";
    public static final String SHARED_KNOWNNAME = "knownName";
    public static final String SHARED_LATI = "latitude";
    public static final String SHARED_LONGITUDE = "longitude";
    public static final String SHARED_LATI_CHECK_LOCATION = "lati_check_location";
    public static final String SHARED_LONGITUDE_CHECK_LOCATION = "longitude_check_location";
    public static final String SHARED_ACCESS_TOKEN = "access_token";
    public static final String SHARED_NPP_PROFILE = "npp_profile";
    public static final String SHARED_NPP_QR_CODE = "npp_titip_absen";
    public static final String SHARED_NAME = "name";
    public static final String SHARED_AVATAR = "avatar";
    public static final String SHARED_ALAMAT = "alamat";
    public static final String SHARED_RT = "rt";
    public static final String SHARED_RW = "rw";
    public static final String SHARED_KELURAHAN = "kelurahan";
    public static final String SHARED_KECAMATAN = "kecamatan";
    public static final String SHARED_KOTA = "kota";
    public static final String SHARED_JENIS_KEL = "jenis_kel";
    public static final String SHARED_TMPT_LAHIR = "tmpt_lahir";
    public static final String SHARED_TGL_LAHIR = "tgl_lahir";
    public static final String SHARED_TGL_MASUK = "tgl_masuk";
    public static final String SHARED_SATUS_PEG = "satus_peg";
    public static final String SHARED_AGAMA = "agama";
    public static final String SHARED_NAMASUSI = "namasusi";
    public static final String SHARED_PKTGOL = "pktgol";
    public static final String SHARED_SUBSATKER = "subsatker";
    public static final String SHARED_SATKER = "satker";
    public static final String SHARED_JABATAN = "jabatan";
    public static final String SHARED_KET = "ket";
    public static final String SHARED_TLP = "tlp";
    public static final String SHARED_PEK = "pek";
    public static final String SHARED_ST_DATA = "st_data";
    public static final String SHARED_ROLES = "roles";
    public static final String SHARED_SATKER_FORMATTED = "satker_formatted";
    public static final String SHARED_SUBSATKER_FORMATTED = "subsatker_formatted";
    public static final String SHARED_APP_VERSION = "app_version";
    public static final String SHARED_FCM_TOKEN = "fcm_token";
    public static final String SHARED_REMARK = "remark";
    public static final String SHARED_START_TIME = "start_time";
    public static final String SHARED_END_TIME = "end_time";
    public static final String SHARED_SHIFT_DAILY_CODE = "shift_daily_code";
    public static final String SHARED_ID_CABANG = "id_cabang";
    public static final String SHARED_CABANG = "cabang";

    public static final String SHARED_COMPRESED_PHOTO_OFFLINE = "compressed_photo_offline";
    public static final String SHARED_LATI_OFFLINE = "latitude_offline";
    public static final String SHARED_LONGITUDE_OFFLINE = "longitude_offline";
    public static final String SHARED_TANGGAL_OFFLINE = "tanggal_offline";
    public static final String SHARED_TIME_OFFLINE = "waktu_offline";
    public static final String SHARED_GET_PHOTO_SERVER_PHOTO_OFFLINE = "get_photo_server_offline";

    public static final String SHARED_STATUS_TYPE = "status_type";
    public static final String SHARED_STATUS_ABSENSI = "status_absensi";
    public static final String SHARED_STATUS_TYPE_CONNECTION = "status_type_connection";
    public static final String SHARED_STATUS_CONNECTION = "status_connection";
    public static final String SHARED_STATUS_APLIKASI = "status_aplikasi";
    public static final String SHARED_APLIKASI_VERSION = "aplikasi_version";
    public static final String SHARED_PATH_FOTO = "path_foto";
    public static final String SHARED_ABSENSI_MASUK = "shared_absensi_masuk";
    public static final String SHARED_ABSENSI_KELUAR = "shared_absensi_keluar";
    public static final String SHARED_FLAG_SPLASH = "flag_splash";
    public static final String SHARED_ANDROID_TOKEN_1 = "6908321002";
    public static final String SHARED_ANDROID_TOKEN_2 = "6908321001";
    public static final String SHARED_ANDROID_TOKEN_3 = "android_token_3";
    public static final String SHARED_ANDROID_TOKEN_4 = "android_token_4";
    public static final String SHARED_ANDROID_TOKEN_5 = "android_token_5";
    public static final String SHARED_ANDROID_TOKEN_6 = "android_token_6";
    public static final String SHARED_ANDROID_TOKEN_7 = "android_token_7";
    public static final String SHARED_ANDROID_TOKEN_8 = "android_token_8";
    public static final String SHARED_ANDROID_TOKEN_9 = "android_token_9";
    public static final String SHARED_ANDROID_TOKEN_10 = "android_token_1";
    public static final String SHARED_ANDROID_TOKEN_11 = "android_token_1";
    public static final String SHARED_ANDROID_TOKEN_12 = "android_token_1";

    public static final String SHARED_URL_LOGO = "url_logo";
    public static final String SHARED_HEADER_PROFIL = "shared_header_profil";
    public static final String SHARED_NAME_DASHBOARD = "shared_name_dashboard";
    public static final String SHARED_HELLO = "shared_hello";
    public static final String SHARED_IMAGE_HEADER = "shared_image_header";
    public static final String SHARED_MESSAGE_INFO = "shared_message_info";

    public static final String SHARED_ACTION_CHEAT = "shared_action_cheat";
    public static final String SHARED_PAGE_CHEAT = "shared_page_cheat";
    public static final String SHARED_COUNT_CHEAT = "shared_count_cheat";

    public static final String SHARED_TYPE_WEB_vIEW = "shared_type_web_view";

    //Firebase
    // global topic to receive app wide push notifications
    public static final String FIREBASE_NAME = "pdam-tirta-happy-work";
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String FIREBAE_REGISTRATION_COMPLETE = "registrationComplete";
    public static final String FIREBASE_PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int FIREBASE_NOTIFICATION_ID = 100;
    public static final int FIREBASE_NOTIFICATION_ID_BIG_IMAGE = 101;

    //bundle
    public static final String BUNDLE_STATUS_ABSENSI = "status_absensi";
    public static final String BUNDLE_NPP = "npp";
    public static final String BUNDLE_NAME = "name";
    public static final String BUNDLE_JABATAN = "jabatan";
    public static final String BUNDLE_RIWAYAT_ABSENSI = "riwayat_absensi";
    public static final String BUNDLE_NUMBER_REQUEST = "number_request";
    public static final String BUNDLE_NUMBER_APPROVALS = "number_approvals";

    public static final String BUNDLE_DATE_PAYSLIP = "date_payslip";
    public static final String BUNDLE_OPT_PAYROLL_PERIOD = "opt_payroll_period";
    public static final String BUNDLE_OPT_PERIOD_MONTH = "opt_period_month";
    public static final String BUNDLE_OPT_PERIOD_YEAR = "opt_period_year";

    // TODO Mulai EDMS
    public static final String BUNDLE_NUMBER_TRX_SURAT = "number_trx_surat";
    // TODO Selesai EDMS

    // TODO Mulai Pembaca Meter
    public static final String BUNDLE_PEMBACA_METER_CODE_INPUT_DATA = "bundle_pembaca_meter_code_input_data";
    public static final String BUNDLE_PEMBACA_METER_CODE_BENDEL = "bundle_pembaca_meter_code_input_data";

    public static final String BUNDLE_PEMBACA_METER_NOLANGG = "pembaca_meter_nolangg";
    public static final String BUNDLE_PEMBACA_METER_DISM = "pembaca_meter_dism";
    public static final String BUNDLE_PEMBACA_METER_NAMA = "pembaca_meter_nama";
    public static final String BUNDLE_PEMBACA_METER_ALAMAT = "pembaca_meter_alamat";
    public static final String BUNDLE_PEMBACA_METER_TARIF = "pembaca_meter_tarif";
    public static final String BUNDLE_PEMBACA_METER_SUMBER_LAIN = "pembaca_meter_sumber_lain";
    public static final String BUNDLE_PEMBACA_METER_MEREK_METER = "pembaca_meter_merek_meter";
    public static final String BUNDLE_PEMBACA_METER_LALU = "pembaca_meter_lalu";


    public static final String SHARED_PERIODE = "shared_periode";
    public static final String SHARED_PERIODE_BULAN_LALU = "shared_periode_bulan_lalu";
    // TODO Selesai Pembaca Meter


    public static final void changeNoHp(String noHpOriginal) {
        String subString0;
        String subStringNomorhp;
        String noHpFinal;
        //replace 0 menjadi +62
        subString0 = noHpOriginal.substring(0, 1).replace("0", "+62");
        subStringNomorhp = noHpOriginal.substring(1);
        noHpFinal = subString0 + subStringNomorhp;

    }


    public static void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SHARED_GETMODEL, "");
        editor.putString(SHARED_GETPRODUCT, "");
        editor.putString(SHARED_GETDEVICE, "");
        editor.putString(SHARED_GETBUILDBRAND, "");
        editor.putString(SHARED_GETOSVERSION, "");
        editor.putString(SHARED_GETSDKVERSION, "");
        editor.putString(SHARED_GETBUILDNUMBER, "");
        editor.putString(SHARED_GETBUILDINCREMENTAL, "");
        editor.putString(SHARED_IPADRESS, "");
        editor.putString(SHARED_CONNECTIONTYPE, "");
        editor.putString(SHARED_HWID, "");
        editor.putString(SHARED_SSID, "");
        editor.putString(SHARED_ADDRESS_GPS, "");
        editor.putString(SHARED_CITY, "");
        editor.putString(SHARED_STATE, "");
        editor.putString(SHARED_COUNTRY, "");
        editor.putString(SHARED_POSTALCODE, "");
        editor.putString(SHARED_KNOWNNAME, "");
        editor.putString(SHARED_LATI, "");
        editor.putString(SHARED_LONGITUDE, "");
        editor.putString(SHARED_ACCESS_TOKEN, "");
        editor.putString(SHARED_NPP_PROFILE, "");
        editor.putString(SHARED_NPP_QR_CODE, "");
        editor.putString(SHARED_NAME, "");
        editor.putString(SHARED_AVATAR, "");
        editor.putString(SHARED_ALAMAT, "");
        editor.putString(SHARED_RT, "");
        editor.putString(SHARED_RW, "");
        editor.putString(SHARED_KELURAHAN, "");
        editor.putString(SHARED_KECAMATAN, "");
        editor.putString(SHARED_KOTA, "");
        editor.putString(SHARED_JENIS_KEL, "");
        editor.putString(SHARED_TMPT_LAHIR, "");
        editor.putString(SHARED_TGL_LAHIR, "");
        editor.putString(SHARED_TGL_MASUK, "");
        editor.putString(SHARED_SATUS_PEG, "");
        editor.putString(SHARED_AGAMA, "");
        editor.putString(SHARED_NAMASUSI, "");
        editor.putString(SHARED_PKTGOL, "");
        editor.putString(SHARED_SUBSATKER, "");
        editor.putString(SHARED_SATKER, "");
        editor.putString(SHARED_JABATAN, "");
        editor.putString(SHARED_KET, "");
        editor.putString(SHARED_TLP, "");
        editor.putString(SHARED_PEK, "");
        editor.putString(SHARED_ST_DATA, "");
        editor.putString(SHARED_SATKER_FORMATTED, "");
        editor.putString(SHARED_SUBSATKER_FORMATTED, "");
        editor.putString(SHARED_APP_VERSION, "");
        editor.putString(SHARED_FCM_TOKEN, "");
        editor.putString(SHARED_REMARK, "");
        editor.putString(SHARED_START_TIME, "");
        editor.putString(SHARED_END_TIME, "");
        editor.putString(SHARED_SHIFT_DAILY_CODE, "");
        editor.putString(SHARED_COMPRESED_PHOTO_OFFLINE, "");
        editor.putString(SHARED_LATI_OFFLINE, "");
        editor.putString(SHARED_LONGITUDE_OFFLINE, "");
        editor.putString(SHARED_TANGGAL_OFFLINE, "");
        editor.putString(SHARED_TIME_OFFLINE, "");
        editor.putString(SHARED_GET_PHOTO_SERVER_PHOTO_OFFLINE, "");
        editor.putString(SHARED_STATUS_TYPE, "");
        editor.putString(SHARED_STATUS_ABSENSI, "");
        editor.putString(SHARED_STATUS_TYPE_CONNECTION, "");
        editor.putString(SHARED_STATUS_CONNECTION, "");
        editor.putString(SHARED_STATUS_APLIKASI, "");
        editor.putString(SHARED_APLIKASI_VERSION, "");
        editor.putString(SHARED_PATH_FOTO, "");
        editor.putString(SHARED_ABSENSI_MASUK, "");
        editor.putString(SHARED_ABSENSI_KELUAR, "");
        editor.putString(SHARED_FLAG_SPLASH, "");
        editor.putString(SHARED_ANDROID_TOKEN_1, "");
        editor.putString(SHARED_ANDROID_TOKEN_2, "");
        editor.putString(SHARED_ANDROID_TOKEN_3, "");
        editor.putString(SHARED_ANDROID_TOKEN_4, "");
        editor.putString(SHARED_ANDROID_TOKEN_5, "");
        editor.putString(SHARED_ANDROID_TOKEN_6, "");
        editor.putString(SHARED_ANDROID_TOKEN_7, "");
        editor.putString(SHARED_ANDROID_TOKEN_8, "");
        editor.putString(SHARED_ANDROID_TOKEN_9, "");
        editor.putString(SHARED_ANDROID_TOKEN_10, "");
        editor.putString(SHARED_ANDROID_TOKEN_11, "");
        editor.putString(SHARED_ANDROID_TOKEN_12, "");
        editor.putString(SHARED_URL_LOGO, "");
        editor.putString(SHARED_HEADER_PROFIL, "");
        editor.putString(SHARED_NAME_DASHBOARD, "");
        editor.putString(SHARED_HELLO, "");
        editor.putString(SHARED_IMAGE_HEADER, "");
        editor.putString(SHARED_MESSAGE_INFO, "");
        editor.putString(SHARED_GETPASSWORD, "");
        editor.putString(SHARED_ROLES, "");
        editor.putString(SHARED_CABANG, "");
        editor.putString(SHARED_ID_CABANG, "");
        editor.apply();

        ((Activity) context).finishAffinity();
        context.startActivity(new Intent(context, SplashScreenActivity.class));
    }

    public static void deleteFiles(String pathName, String msgLog) {
        // v TODO delete image original
        File file = new File(pathName);
        boolean deleted = file.delete();
        Log.d("debug", msgLog + " Deleted : " + deleted);
    }

    public static void showNotification(Context context, String title, String content) {
        int noificationId = new Random().nextInt(100);
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.notification);
        Log.d("debug", "showNotification: " + sound);
        String channelId = "notification_channel_3";
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), DashboardActivity.class);
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


        builder.setSmallIcon(R.drawable.logo_gd);
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

    public static void dialogAlert(Context context, String tittle, String message, String negativeButton){
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(tittle)
                .setMessage(message)
                .setAnimation("lt_bohong.json")
                .setCancelable(false)
                .setNegativeButton(negativeButton, (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                    ((Activity) context).finishAffinity();
                })
                .setPositiveButton("UNINSTALL aplikasi", (dialogInterface, which) -> {
                    Toast.makeText(context, "Uninstall aplikasi Absensi beraksi...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
                    context.startActivity(intent);
                })
                .build();

        // Show Dialog
        mDialog.show();
    }
    public static void dialogAlertPermission(Context context, String tittle, String message, String negativeButton){
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(tittle)
                .setMessage(message)
                .setAnimation("lt_bohong.json")
                .setCancelable(false)
                .setNegativeButton(negativeButton, (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                    ((Activity) context).finishAffinity();
                    context.startActivity(new Intent(context, LoginActivity.class));
                })
                .setPositiveButton("Hubungi PTI", (dialogInterface, which) -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=6283838191709&text=Halo%20iav..%20ada%20masalah%20permission.%20Isi%20NPP%20:"));
                    context.startActivity(browserIntent);
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    public static void dialogAlertSukses(Context context, String tittle, String message, String positiveButton, Class toActivity){
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(tittle)
                .setMessage(message)
//                .setAnimation("lt_bohong.json")
                .setCancelable(false)
                .setPositiveButton(positiveButton, (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                    ((Activity) context).finish();
//                    context.startActivity(new Intent(context, toActivity));
                })
                .build();

        // Show Dialog
        mDialog.show();
    }
    public static void dialogAlertGagal(Context context, String tittle, String message, String positiveButton){
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(tittle)
                .setMessage(message)
//                .setAnimation("lt_bohong.json")
                .setCancelable(false)
                .setPositiveButton(positiveButton, (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    public static void dialogAlertIntro(Context context, String tittle, String message, String negativeButton){
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(tittle)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(negativeButton, (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton("Sudah", (dialogInterface, which) -> {
                    context.startActivity(new Intent(context, LoginActivity.class));
                })
                .build();

        // Show Dialog
        mDialog.show();
    }
    public static void dialogAlertSplash(Context context, String tittle, String message, String negativeButton){
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(tittle)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(negativeButton, (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                    ((SplashScreenActivity) context).finishAffinity();
                })
                .setPositiveButton("Buka Kembali", (dialogInterface, which) -> {
                    ((SplashScreenActivity) context).finishAffinity();
                    context.startActivity(new Intent(context, SplashScreenActivity.class));
                    dialogInterface.dismiss();
//                    context.startActivity(new Intent(context, SplashScreenActivity.class));
                })
                .build();

        // Show Dialog
        mDialog.show();
    }
    public static void dialogAlertPresensi(Context context, String tittle, String message){
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(tittle)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Foto Sekarang", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                    ((PresensiActivity) context).getFoto();
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    // mock v2 from lis MOCK
    public static String[] requestedPermissions;
    private static final String TAG = "debug";
    private static PackageInfo packageInfo;

    public static boolean isMockSettingsONV2(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
//            areThereMockPermissionApps(context);
            mockV2(context);
            return false;
        } else {
            Toast.makeText(context, "Mock Location Terdeteksi", Toast.LENGTH_SHORT).show();
            mockV2(context);
            return true;
        }
    }

    public static boolean mockV2(Context context) {
        int count = 0;
        boolean finding = false;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages =
                pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS);

                // Get Permissions
                requestedPermissions = packageInfo.requestedPermissions;

                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
//                        if (requestedPermissions[i].equals("android.permission.ACCESS_MOCK_LOCATION") && !applicationInfo.packageName.equals(context.getPackageName())) {
                        if (packageInfo.packageName.contains("fake")
                                || packageInfo.packageName.equalsIgnoreCase("fake")
                                || packageInfo.packageName.contains("mock")
                                || packageInfo.packageName.contains("fakegps")
                                || packageInfo.packageName.equalsIgnoreCase("gpsemulator")
                                || packageInfo.packageName.equalsIgnoreCase("fakegpslocationprofessional")
                                || packageInfo.packageName.equalsIgnoreCase("fakegps_route")
                                || packageInfo.packageName.contains("fakegps_route")
                        ){
                            count++;
                            Log.d("debug_mock", "mockV2: " + packageInfo.packageName);

                            // TODO kirim update ceklis apps nakal
                            MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                                    .setTitle(Config.ERROR_FAKE_GPS_TITLE)
                                    .setMessage("Uninstall fake GPS kamu  Total apps fake : " + requestedPermissions.length +"x install \n Hubungi kepegawaian untuk aktivasi kembali...")
                                    .setAnimation("lt_bohong.json")
                                    .setCancelable(false)
                                    .setNegativeButton("Oke deh, jangan suka bohong ya", (dialogInterface, which) -> {
                                        dialogInterface.dismiss();
                                        ((Activity) context).finishAffinity();
                                    })
                                    .setPositiveButton("Uninstall Aplikasi Absensi", (dialogInterface, which) -> {
                                        Toast.makeText(context, "Uninstall aplikasi Absensi beraksi...", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Intent.ACTION_DELETE);
                                        intent.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
                                        context.startActivity(intent);
                                    })
                                    .build();

                            // Show Dialog
                            mDialog.show();
                            break;
                        }
                    }

//                    if (finding) break;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("Got exception ", e.getMessage());
            }
        }
        if (count > 0)
            return true;
        return false;
    }


    public static String md5(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        Log.d(TAG, "md5: " + md5StrBuff.toString());
        return md5StrBuff.toString().toUpperCase();
    }

    public static InterstitialAd mInterstitialAd;
    public final static String adsInterestialDev = "ca-app-pub-3940256099942544/1033173712";
    public final static String adsInterestialProd = "ca-app-pub-6810772781589252/7755469730";

//    public final static String adsBanner = "ca-app-pub-3940256099942544/6300978111"; // dev
    public final static String adsBanner = "ca-app-pub-6810772781589252/4208134634"; // prod
    public static void ads(Context context, AdView adView){
        MobileAds.initialize(context, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adViews = new AdView(context);
        adViews.setAdSize(AdSize.BANNER);
        adViews.setAdUnitId(adsBanner);
        adView.loadAd(adRequest);
    }

    public static void interestial(Context context) {
        // TODO inisialisasi MobileAds
        MobileAds.initialize(context, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        // TODO definisi InterstitialAd
        InterstitialAd.load(context, adsInterestialProd, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.d(TAG, "onAdLoaded " + mInterstitialAd);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(((Activity) context));
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
                            Log.d("TAG", "The ad was dismissed.");
//                            context.startActivity(new Intent(context, classJava));
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when fullscreen content failed to show.
                            Log.d("TAG", "The ad failed to show.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when fullscreen content is shown.
                            // Make sure to set your reference to null so you don't
                            // show it a second time.
                            mInterstitialAd = null;
                            Log.d("TAG", "The ad was shown.");
                        }
                    });
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    public static void interestialIntent(Context context, Class classJava) {

        // TODO inisialisasi MobileAds
        MobileAds.initialize(context, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        // TODO definisi InterstitialAd
        InterstitialAd.load(context, adsInterestialProd, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.d(TAG, "onAdLoaded " + mInterstitialAd);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(((Activity) context));
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
                            Log.d("TAG", "The ad was dismissed.");
                            ((Activity) context).finishAffinity();
                            context.startActivity(new Intent(context, classJava));
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when fullscreen content failed to show.
                            Log.d("TAG", "The ad failed to show.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when fullscreen content is shown.
                            // Make sure to set your reference to null so you don't
                            // show it a second time.
                            mInterstitialAd = null;
                            Log.d("TAG", "The ad was shown.");
                        }
                    });
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    public static void saveSharedCheat(Context context, String cheat, String page, String countCheat) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SHARED_ACTION_CHEAT, cheat);
        editor.putString(Config.SHARED_PAGE_CHEAT, page);
        editor.putString(Config.SHARED_COUNT_CHEAT, countCheat);
        editor.apply();

    }

    public static void sendCheat(Context context, String authToken, String typeCheat, String page, String countCheat){
        ApiService apiService = ApiConfig.getApiService(context);
        apiService.postActionCheatLog(authToken, typeCheat, page, countCheat).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Berhasil mengirim Cheat", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Gagal mengirim Cheat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static final int RC_CAMERA_AND_LOCATION = 1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    public static void methodRequiresTwoPermission(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SHARED_FLAG_SPLASH, "1");
        editor.apply();
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_WIFI_STATE
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.CAMERA
                , Manifest.permission.WRITE_SECURE_SETTINGS
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.MANAGE_EXTERNAL_STORAGE
                , Manifest.permission.USE_FINGERPRINT};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            Log.d(TAG, "methodRequiresTwoPermission: Sukses");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.app_name) ,
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }
    private static final int RC_LOCATION = 2;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterPermissionGranted(RC_LOCATION)
    public static void methodRequiresTwoPermissionLocation(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SHARED_FLAG_SPLASH, "1");
        editor.apply();
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            Log.d(TAG, "methodRequiresTwoPermission: Sukses");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.app_name) ,
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

}