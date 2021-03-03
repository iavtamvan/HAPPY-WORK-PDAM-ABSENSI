package com.pdamkotasmg.happywork.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public final class Config {

    //API KEY
    public static final String GOOGLE_API_KEY_CAPTCHA_SITE_KEY = "6Lf0J0kaAAAAAAiSo7blYknl2sipxzpGN8B8ubjz";
    public static final String GOOGLE_API_KEY_CAPTCHA_SECRET_KEY = "6Lf0J0kaAAAAAP1jT3WCu3pvVzJBOwAM9N8vK4mY";
    public static final String BASE_URL_IMAGE = "https://app.pdamkotasmg.co.id:58080/pdam-pengaduan-api-laravel/public";

    public static final String ERROR_MSG = "Koneksi kamu lagi jelek";
    public static final String ERROR_PASSWORD = "Pastikan Kata Sandi Anda Sama";
    public static final String ERROR_DATA_REGISTER = "Pastikan Data Anda Dengan Benar";


    //data akun
    public static final String SHARED_PREF_NAME = "PUBLIC-PDAM";

    public static final String SHARED_ACCES_TOKEN = "token";
    public static final String SHARED_PREF_DISM = "dism";
    public static final String SHARED_PREF_NO_ANGG = "nolangg";
    public static final String SHARED_PREF_NAMA = "nama";
    public static final String SHARED_PREF_A_AMAT = "alamat";
    public static final String SHARED_PREF_TE_EPON = "telepon";
    public static final String SHARED_PREF_EMAIL = "email";
    public static final String SHARED_PREF_C_BANG = "cabang";
    public static final String SHARED_PREF__ARIF = "tarif";
    public static final String SHARED_PREF_KELU_AHAN = "kelurahan";
    public static final String SHARED_PREF_KECA_ATAN = "kecamatan";
    public static final String SHARED_PREF_S_ATUS = "status";
    public static final String SHARED_PREF_STATU__KET = "status_ket";
    public static final String SHARED_PEMBAYARAN_BULAN = "bulan";
    public static final String SHARED_PEMBAYARAN_TAGIHAN = "tagihan";
    public static final String SHARED_PEMBAYARAN_STATUS_REKENING = "status_rekening";

    public static final String SHARED_STATUS_PELANGGAN_DASHBOARD = "status_pelanggan_pdam";

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

        editor.putString(SHARED_ACCES_TOKEN, "");
        editor.putString(SHARED_PREF_DISM, "");
        editor.putString(SHARED_PREF_NO_ANGG, "");
        editor.putString(SHARED_PREF_NAMA, "");
        editor.putString(SHARED_PREF_A_AMAT, "");
        editor.putString(SHARED_PREF_TE_EPON, "");
        editor.putString(SHARED_PREF_EMAIL, "");
        editor.putString(SHARED_PREF_C_BANG, "");
        editor.putString(SHARED_PREF__ARIF, "");
        editor.putString(SHARED_PREF_KELU_AHAN, "");
        editor.putString(SHARED_PREF_KECA_ATAN, "");
        editor.putString(SHARED_PREF_S_ATUS, "");
        editor.putString(SHARED_PREF_STATU__KET, "");
        editor.putString(SHARED_PEMBAYARAN_BULAN, "");
        editor.putString(SHARED_PEMBAYARAN_TAGIHAN, "");
        editor.putString(SHARED_PEMBAYARAN_STATUS_REKENING, "");
        editor.putString(SHARED_STATUS_PELANGGAN_DASHBOARD, "");
//        editor.putString("regId", "");
        editor.apply();

//        context.startActivity(new Intent(context, WelcomeActivity.class));
    }


}