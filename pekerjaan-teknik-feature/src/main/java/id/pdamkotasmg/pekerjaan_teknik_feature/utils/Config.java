package id.pdamkotasmg.pekerjaan_teknik_feature.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.Random;

import id.pdamkotasmg.pekerjaan_teknik_feature.R;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.login.LoginActivity;

public final class Config {
//    public static final String BASE_URL_PORTAL_PEGAWAI = "https://gateway.pdamkotasmg.co.id/api-gw-dev/portal-pegawai/"; // dev
    public static final String BASE_URL_PORTAL_PEGAWAI = "https://gateway.pdamkotasmg.co.id/api-gw/portal-pegawai/";
//    public static final String BASE_URL = "https://gateway.pdamkotasmg.co.id/api-gw-dev/"; // dev
    public static final String BASE_URL = "https://gateway.pdamkotasmg.co.id/api-gw/";

//    public static final String BASE_URL_IMAGE = "https://gateway.pdamkotasmg.co.id/api-gw-dev/file-handler/foto/?filename="; // dev
    public static final String BASE_URL_IMAGE = "https://gateway.pdamkotasmg.co.id/api-gw/file-handler/foto/?filename=";

    public static final int REQ_ACT_RESULT_CODE_MAPS = 1;

    public static final String SHARED_PREF_NAME = "PEKERJAAN-TEKNIK";
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
    public static final String SHARED_NPP_PENGAWAS = "npp_pengawas";
    public static final String SHARED_NAME_PENGAWAS = "name_pengawas";
    public static final String SHARED_NPP_KASUB = "npp_kasub";
    public static final String SHARED_NAME_KASUB = "name_kasub";
    public static final String SHARED_NPP_VERIFIKATOR = "npp_verifikator";
    public static final String SHARED_NAME_VERIFIKATOR = "name_verifikator";
    public static final String SHARED_TENAGA_LAINNYA = "tenaga_lainnya";
    public static final String SHARED_PERMISION_APPS = "permision_apps";
    public static final String SHARED_FIREBASE_TOKEN = "firebase_token";

    public static final String SHARED_KD_AMBIL_PEKERJAAN = "shared_kd_ambil_pekerjaan";

    public static final String SHARED_STATU_BOOKING = "shared_statu_booking";


    public static final String BUNDLE_NO_SPK = "bundle_no_spk";
    public static final String BUNDLE_NO_CC = "bundle_no_cc";
    public static final String BUNDLE_TGL_SPK = "bundle_tgl_spk";
    public static final String BUNDLE_NM_LAPOR = "bundle_nm_lapor";
    public static final String BUNDLE_ALAMAT_LAPOR = "bundle_alamat_lapor";
    public static final String BUNDLE_NOLANGG_LAPOR = "bundle_nolangg_lapor";
    public static final String BUNDLE_ASAL_LAPOR = "bundle_asal_lapor";
    public static final String BUNDLE_NAMA = "bundle_nama";
    public static final String BUNDLE_ALAMAT = "bundle_alamat";
    public static final String BUNDLE_LOKASI = "bundle_lokasi";
    public static final String BUNDLE_URAIAN = "bundle_uraian";
    public static final String BUNDLE_KASUB = "bundle_kasub";
    public static final String BUNDLE_PENGAWAS = "bundle_pengawas";
    public static final String BUNDLE_VERIFIKATOR = "bundle_verifikator";
    public static final String BUNDLE_USER_ENTRY = "bundle_user_entry";
    public static final String BUNDLE_PC_ENTRY = "bundle_pc_entry";
    public static final String BUNDLE_IP_ENTRY = "bundle_ip_entry";
    public static final String BUNDLE_TANGGAL = "bundle_tanggal";
    public static final String BUNDLE_CABANG = "bundle_cabang";
    public static final String BUNDLE_BAGIAN = "bundle_bagian";
    public static final String BUNDLE_BAGIAN_SUB = "bundle_bagian_sub";
    public static final String BUNDLE_TGL_PELAKSANA = "bundle_tgl_pelaksana";
    public static final String BUNDLE_JAM1 = "bundle_jam1";
    public static final String BUNDLE_JAM2 = "bundle_jam2";
    public static final String BUNDLE_JENIS_PIPA = "bundle_jenis_pipa";
    public static final String BUNDLE_JML_TENAGA = "bundle_jml_tenaga";
    public static final String BUNDLE_JML_TENAGA_KET = "bundle_jml_tenaga_ket";
    public static final String BUNDLE_STATUS = "bundle_status";
    public static final String BUNDLE_NO_SPK_SBL = "bundle_no_spk_sbl";
    public static final String BUNDLE_LATITUDE = "bundle_latitude";
    public static final String BUNDLE_LONGITUDE = "bundle_longitude";
    public static final String BUNDLE_KD_PENANGAN = "bundle_kd_penangan";
    public static final String BUNDLE_TKA = "bundle_tka";
    public static final String BUNDLE_KD_PERBAIKAN = "bundle_kd_perbaikan";
    public static final String BUNDLE_KD_ZONA = "bundle_kd_zona";
    public static final String BUNDLE_TIPE_ZONA = "bundle_tipe_zona";
    public static final String BUNDLE_KET_ZONA = "bundle_ket_zona";
    public static final String BUNDLE_FOTO1 = "bundle_foto1";
    public static final String BUNDLE_FOTO2 = "bundle_foto2";
    public static final String BUNDLE_FOTO3 = "bundle_foto3";
    public static final String BUNDLE_FOTO4 = "bundle_foto4";
    public static final String BUNDLE_STATUS_VERIFIKASI = "bundle_status_verifikasi";
    public static final String BUNDLE_STATUS_INTENT = "bundle_status_intent";
    public static final String BUNDLE_NO_LANGG = "bundle_no_langg";
    public static final String BUNDLE_NAMA_PENGADU = "bundle_nama_pengadu";
    public static final String BUNDLE_ALAMAT_PENGADU = "bundle_alamat_pengadu";
    public static final String BUNDLE_TELP_PENGADU = "bundle_telp_pengadu";
    public static final String BUNDLE_URAIAN_PENGADU = "bundle_uraian_pengadu";
    public static final String BUNDLE_NO_PENGADUAN_CC = "bundle_no_pengaduan_cc";
    public static final String BUNDLE_NO_TELP_PENGADU = "bundle_no_telp_cc";
    public static final String BUNDLE_PETUGAS_ENTRY = "bundle_petugas_entry";
    public static final String BUNDLE_PETUGAS_PROSES = "bundle_petugas_proses";
    public static final String BUNDLE_LEMBUR = "bundle_lembur";

    public static final String BUNDLE_KD_RIWAYAT = "bundle_kd_riwayat";

    //Firebase
    // global topic to receive app wide push notifications
    public static final String FIREBASE_NAME = "pdam-tirta-siagan";
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String FIREBAE_REGISTRATION_COMPLETE = "registrationComplete";
    public static final String FIREBASE_PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int FIREBASE_NOTIFICATION_ID = 100;
    public static final int FIREBASE_NOTIFICATION_ID_BIG_IMAGE = 101;

    public static void logout(Context context){
        SharedPreferences sharedPreferences  = context.getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Config.SHARED_ACCESS_TOKEN, "");
        editor.putString(Config.SHARED_NPP_PROFILE, "");
        editor.putString(Config.SHARED_NAME, "");
        editor.putString(Config.SHARED_AVATAR, "");
        editor.putString(Config.SHARED_ALAMAT, "");
        editor.putString(Config.SHARED_RT, "");
        editor.putString(Config.SHARED_RW, "");
        editor.putString(Config.SHARED_KELURAHAN, "");
        editor.putString(Config.SHARED_KECAMATAN, "");
        editor.putString(Config.SHARED_KOTA, "");
        editor.putString(Config.SHARED_JENIS_KEL, "");
        editor.putString(Config.SHARED_TMPT_LAHIR, "");
        editor.putString(Config.SHARED_TGL_LAHIR, "");
        editor.putString(Config.SHARED_TGL_MASUK, "");
        editor.putString(Config.SHARED_SATUS_PEG, "");
        editor.putString(Config.SHARED_AGAMA, "");
        editor.putString(Config.SHARED_NAMASUSI, "");
        editor.putString(Config.SHARED_PKTGOL, "");
        editor.putString(Config.SHARED_SUBSATKER, "");
        editor.putString(Config.SHARED_SATKER, "");
        editor.putString(Config.SHARED_JABATAN, "");
        editor.putString(Config.SHARED_KET, "");
        editor.putString(Config.SHARED_TLP, "");
        editor.putString(Config.SHARED_PEK, "");
        editor.putString(Config.SHARED_ST_DATA, "");
        editor.putString(Config.SHARED_SATKER_FORMATTED, "");
        editor.putString(Config.SHARED_SUBSATKER_FORMATTED, "");
        editor.putString(Config.SHARED_NPP_PENGAWAS, "");
        editor.putString(Config.SHARED_NAME_PENGAWAS, "");
        editor.putString(Config.SHARED_NPP_KASUB, "");
        editor.putString(Config.SHARED_NAME_KASUB, "");
        editor.putString(Config.SHARED_NPP_VERIFIKATOR, "");
        editor.putString(Config.SHARED_NAME_VERIFIKATOR, "");
        editor.putString(Config.SHARED_TENAGA_LAINNYA, "");
        editor.putString(Config.SHARED_PERMISION_APPS, "");
        editor.putString(Config.SHARED_FIREBASE_TOKEN, "");
//        editor.putString("regId", "");
        editor.apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void popUpSuccesIntent(Activity activity, String message, Class toActivity) {
        MaterialDialog mDialog = new MaterialDialog.Builder(activity)
                .setTitle("Peringatan")
                .setMessage(message)
                .setAnimation("succes.json")
                .setCancelable(false)
                .setPositiveButton("Ok deh", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                    activity.finish();
                    activity.startActivity(new Intent(activity, toActivity));
                })
                .build();
        // Show Dialog
        mDialog.show();
    }

    public static void popUpSucces(Activity activity, String message) {
        MaterialDialog mDialog = new MaterialDialog.Builder(activity)
                .setTitle("Peringatan")
                .setMessage(message)
                .setAnimation("succes.json")
                .setCancelable(false)
                .setPositiveButton("Ok deh", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                })
                .build();
        // Show Dialog
        mDialog.show();
    }

    public static void popUpUpdateApps(Activity activity, String message) {
        MaterialDialog mDialog = new MaterialDialog.Builder(activity)
                .setTitle("Peringatan")
                .setMessage(message)
                .setAnimation("update.json")
                .setCancelable(false)
                .setPositiveButton("Update Aplikasi Sekarang", (dialogInterface, which) -> {
//                    dialogInterface.dismiss();
                    // TODO Link Playstore (done)
                    final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                })
                .build();
        // Show Dialog
        mDialog.show();
    }

    public static void header(ImageView ivHeaderBackArrow, ImageView ivHeaderInfo, TextView tvHeaderJudul, Activity activitys, String textHeaderJudul){
        ivHeaderBackArrow.setOnClickListener(view -> {
            activitys.finish();
        });
        tvHeaderJudul.setText(textHeaderJudul);
        ivHeaderInfo.setVisibility(View.GONE);
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

}