package com.pdamkotasmg.happywork.utils;

import android.annotation.SuppressLint;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.api.server.ApiConfig;
import com.pdamkotasmg.happywork.api.server.ApiService;
import com.pdamkotasmg.happywork.fitur.dashboard.DashboardActivity;
import com.pdamkotasmg.happywork.fitur.splash.SplashScreenActivity;
import com.pdamkotasmg.happywork.fitur.splash.model.packageName.Data;
import com.pdamkotasmg.happywork.fitur.splash.model.packageName.DataItem;
import com.pdamkotasmg.happywork.fitur.splash.model.packageName.PackageNameRootModel;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public final class Config {
    public static final String APLICATION_NAME = "Happy Work";

    //API KEY
    public static final String BASE_URL_IMAGE = "https://app.pdamkotasmg.co.id/api-gw-dev/portal-pegawai";

    public static final String BASE_URL_NOTIF_FOTO_FAIL = "https://image.freepik.com/free-vector/people-with-sad-angry-emojis-illustration_53876-43293.jpg";
    public static final String BASE_URL_NOTIF_JIKA_TELAT = "https://image.freepik.com/free-vector/people-run-open-door-being-late-men-women-hurry-end-beginning-working-office-day-illustration_80590-9275.jpg";
    public static final String BASE_URL_NOTIF_JIKA_PULANG_AWAL = "https://image.freepik.com/free-vector/businessman-leaving-comfort-zone-flat-illustration_74855-16768.jpg";
    public static final String BASE_URL_NOTIF_NORMAL = "https://image.freepik.com/free-vector/people-jumping-trampoline_74855-4453.jpg";
    public static final String BASE_URL_NOTIF_ERROR = "https://image.freepik.com/free-vector/oops-404-error-with-broken-robot-concept-illustration_114360-1932.jpg";

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
    public static final String SHARED_SATKER_FORMATTED = "satker_formatted";
    public static final String SHARED_SUBSATKER_FORMATTED = "subsatker_formatted";
    public static final String SHARED_APP_VERSION = "app_version";
    public static final String SHARED_FCM_TOKEN = "fcm_token";
    public static final String SHARED_REMARK = "remark";
    public static final String SHARED_START_TIME = "start_time";
    public static final String SHARED_END_TIME = "end_time";
    public static final String SHARED_SHIFT_DAILY_CODE = "shift_daily_code";
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
    public static final String SHARED_ANDROID_TOKEN_1 = "android_token_1";
    public static final String SHARED_ANDROID_TOKEN_2 = "android_token_2";
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
    public static final String BUNDLE_RIWAYAT_ABSENSI = "riwayat_absensi";


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
        editor.apply();

        context.startActivity(new Intent(context, SplashScreenActivity.class));
    }

    public static void deleteFiles(String pathName, String log) {
        // TODO delete image original
        File file = new File(pathName);
        boolean deleted = file.delete();
        Log.d("debug", log + " Deleted : " + deleted);
    }

    public static void showNotification(Context context, String title, String content, String resourceBitmapLink) {
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

//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceBitmap);
        Bitmap bitmap = null;
        try {
            URL url = new URL(resourceBitmapLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
        }


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
        builder.setStyle(new NotificationCompat.BigPictureStyle().
                bigPicture(bitmap));
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

    public static void dialogAlert(Context context, String tittle, String message, String negativeButton, String positiveButton){
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(tittle)
                .setMessage(message)
                .setAnimation("lt_bohong.json")
                .setCancelable(false)
                .setNegativeButton(negativeButton, (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                    ((Activity) context).finishAffinity();
                })
                .setPositiveButton(positiveButton, (dialogInterface, which) -> {
                    Toast.makeText(context, "Uninstall aplikasi Presensi beraksi...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
                    context.startActivity(intent);
                })
                .build();

        // Show Dialog
        mDialog.show();
    }





    private static final String TAG = "debug";
    private static PackageInfo packageInfo;
    private static Data dataItem;
    private static List<DataItem> dataItemList;
    private static List<String> stringslist = new ArrayList<>();
    public static void getPackageNameFromServer(Context context) {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getPackageName().enqueue(new Callback<PackageNameRootModel>() {
            @Override
            public void onResponse(Call<PackageNameRootModel> call, Response<PackageNameRootModel> response) {
                if (response.isSuccessful()) {
                    dataItem = response.body().getData();
                    dataItemList = dataItem.getData();
                    for (int j = 0; j < dataItemList.size(); j++) {
                        stringslist.add(dataItemList.get(j).getPackageName());
                    }
                    Log.d(TAG, "listPackage: " + stringslist);
                    isMockSettingsON(context);
                }
            }

            @Override
            public void onFailure(Call<PackageNameRootModel> call, Throwable t) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
            areThereMockPermissionApps(context);
            return false;
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public static boolean areThereMockPermissionApps(Context context) {
        int count = 0;

        PackageManager pm = context.getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        boolean finding = false;
        for (ApplicationInfo applicationInfo : packages) {
            try {
                for (int i = 0; i < stringslist.size(); i++) {
                    packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                    if (packageInfo.packageName.equalsIgnoreCase(stringslist.get(i))) {
                        Log.d(TAG, packageInfo.packageName + " : Fuck Moc: Sama");
                        finding = true;
                        Toast.makeText(context, "Finding", Toast.LENGTH_SHORT).show();
                        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                                .setTitle("Haayyoooooooo kamu" + " ....?")
                                .setMessage("Uninstall fake GPS kamu " + packageInfo.packageName + "\n\n Hubungi kepegawaian untuk aktivasi kembali...")
                                .setAnimation("lt_bohong.json")
                                .setCancelable(false)
                                .setNegativeButton("Oke deh, jangan suka bohong ya", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                        ((Activity) context).finishAffinity();
                                    }
                                })
                                .setPositiveButton("Uninstall Aplikasi Presensi", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        Toast.makeText(context, "Uninstall aplikasi Presensi beraksi...", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Intent.ACTION_DELETE);
                                        intent.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
                                        context.startActivity(intent);
                                    }
                                })
                                .build();

                        // Show Dialog
                        mDialog.show();
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

}