package com.pdamkotasmg.happywork.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.dashboard.DashboardActivity;

import java.io.File;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public final class Config {
    public static final String APLICATION_NAME = "Happy Work";

    //API KEY
    public static final String GOOGLE_API_KEY_CAPTCHA_SITE_KEY = "6Lf0J0kaAAAAAAiSo7blYknl2sipxzpGN8B8ubjz";
    public static final String GOOGLE_API_KEY_CAPTCHA_SECRET_KEY = "6Lf0J0kaAAAAAP1jT3WCu3pvVzJBOwAM9N8vK4mY";
    public static final String BASE_URL_IMAGE = "https://app.pdamkotasmg.co.id:58080/pdam-pengaduan-api-laravel/public";

    public static final String ERROR_MSG = "Koneksi kamu lagi jelek";
    public static final String ERROR_PASSWORD = "Pastikan Kata Sandi Anda Sama";
    public static final String ERROR_DATA_REGISTER = "Pastikan Data Anda Dengan Benar";
    public static final String ERROR_SESSION = "Sesi login anda telah habis";

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

    public static final String SHARED_ACCESS_TOKEN = "access_token";
    public static final String SHARED_NPP_PROFILE = "npp_profile";
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
    public static final String SHARED_SATKER_FORMATTED = "satker_formatted";
    public static final String SHARED_SUBSATKER_FORMATTED = "subsatker_formatted";
    public static final String SHARED_APP_VERSION = "app_version";

    // TODO SharedPref Offline Saving
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

    //bundle


    public static final String BUNDLE_LINK_NEWS = "link_news";
    public static final String BUNDLE_NOHP = "nohp";
    public static final String BUNDLE_NOLANGG = "nomor_langganan";

    public static final String BUNDLE_NO_PENGADUAN_UUID = "no_pengaduan_uuid";
    public static final String BUNDLE_NO_PENGADUAN_ANDRO = "no_pengaduan_andro";
    public static final String BUNDLE_FOTO_ADUAN = "foto_aduan";
    public static final String BUNDLE_FOTO_PENGADU = "foto_pengadu";
    public static final String BUNDLE_NAMA_PENGADU = "nama_pengadu";
    public static final String BUNDLE_TELP_PENGADU = "telp_pengadu";
    public static final String BUNDLE_ALAMAT_PENGADU = "alamat_pengadu";
    public static final String BUNDLE_URAIAN = "uraian";
    public static final String BUNDLE_STATUS = "status";
    public static final String BUNDLE_ALASAN_DITOLAK = "alasan_ditolak";
    public static final String BUNDLE_LATITUDE = "latitude";
    public static final String BUNDLE_LONGITUDE = "longitude";
    public static final String BUNDLE_CREATED_AT = "created_at";

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

//        editor.putString(SHARED_ACCES_TOKEN, "");
//        editor.putString(SHARED_PREF_DISM, "");
//        editor.putString(SHARED_PREF_NO_ANGG, "");
//        editor.putString(SHARED_PREF_NAMA, "");
//        editor.putString(SHARED_PREF_A_AMAT, "");
//        editor.putString(SHARED_PREF_TE_EPON, "");
//        editor.putString(SHARED_PREF_EMAIL, "");
//        editor.putString(SHARED_PREF_C_BANG, "");
//        editor.putString(SHARED_PREF__ARIF, "");
//        editor.putString(SHARED_PREF_KELU_AHAN, "");
//        editor.putString(SHARED_PREF_KECA_ATAN, "");
//        editor.putString(SHARED_PREF_S_ATUS, "");
//        editor.putString(SHARED_PREF_STATU__KET, "");
//        editor.putString(SHARED_PEMBAYARAN_BULAN, "");
//        editor.putString(SHARED_PEMBAYARAN_TAGIHAN, "");
//        editor.putString(SHARED_PEMBAYARAN_STATUS_REKENING, "");
//        editor.putString(SHARED_STATUS_PELANGGAN_DASHBOARD, "");
//        editor.putString("regId", "");

        editor.apply();

//        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

    public static void deleteFiles(String pathName, String log) {
        // TODO delete image original
        File file = new File(pathName);
        boolean deleted = file.delete();
        Log.d("debug", log + " Deleted : " + deleted);
    }

    public static void showNotification(Context context, String title, String content) {
        int noificationId = new Random().nextInt(100);
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.notif);
        Log.d("debug", "showNotification: " + sound);
        String channelId = "notification_channel_3";
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context.getApplicationContext(), channelId
        );
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle(title); // make suer change the channel for image
        builder.setContentText(content);
        builder.setSound(sound);
        //notification for image
//        builder.setStyle(new NotificationCompat.BigPictureStyle().
//                bigPicture(bitmap));
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


}