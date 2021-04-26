package com.pdamkotasmg.happywork.utils

import android.content.Context

object Config {
    val APLICATION_NAME: String = "Happy Work"

    //API KEY
    val GOOGLE_API_KEY_CAPTCHA_SITE_KEY: String = "6Lf0J0kaAAAAAAiSo7blYknl2sipxzpGN8B8ubjz"
    val GOOGLE_API_KEY_CAPTCHA_SECRET_KEY: String = "6Lf0J0kaAAAAAP1jT3WCu3pvVzJBOwAM9N8vK4mY"
    val BASE_URL_IMAGE: String = "https://app.pdamkotasmg.co.id:58080/pdam-pengaduan-api-laravel/public"
    val ERROR_MSG: String = "Koneksi kamu lagi jelek"
    val ERROR_PASSWORD: String = "Pastikan Kata Sandi Anda Sama"
    val ERROR_DATA_REGISTER: String = "Pastikan Data Anda Dengan Benar"
    val ERROR_SESSION: String = "Sesi login anda telah habis"

    //data akun
    val SHARED_PREF_NAME: String = "HAPPY-WORK"
    val SHARED_GETMODEL: String = "getModel"
    val SHARED_GETPRODUCT: String = "getProduct"
    val SHARED_GETDEVICE: String = "getDevice"
    val SHARED_GETBUILDBRAND: String = "getBuildBrand"
    val SHARED_GETOSVERSION: String = "getOsVersion"
    val SHARED_GETSDKVERSION: String = "getSdkVersion"
    val SHARED_GETBUILDNUMBER: String = "getBuildNumber"
    val SHARED_GETBUILDINCREMENTAL: String = "getBuildIncremental"
    val SHARED_IPADRESS: String = "ipAdress"
    val SHARED_CONNECTIONTYPE: String = "connectionType"
    val SHARED_HWID: String = "hwid"
    val SHARED_SSID: String = "ssid"
    val SHARED_ADDRESS_GPS: String = "address_gps"
    val SHARED_CITY: String = "city"
    val SHARED_STATE: String = "state"
    val SHARED_COUNTRY: String = "country"
    val SHARED_POSTALCODE: String = "postalCode"
    val SHARED_KNOWNNAME: String = "knownName"
    val SHARED_LATI: String = "latitude"
    val SHARED_LONGITUDE: String = "longitude"
    val SHARED_ACCESS_TOKEN: String = "access_token"
    val SHARED_NPP_PROFILE: String = "npp_profile"
    val SHARED_NAME: String = "name"
    val SHARED_AVATAR: String = "avatar"
    val SHARED_ALAMAT: String = "alamat"
    val SHARED_RT: String = "rt"
    val SHARED_RW: String = "rw"
    val SHARED_KELURAHAN: String = "kelurahan"
    val SHARED_KECAMATAN: String = "kecamatan"
    val SHARED_KOTA: String = "kota"
    val SHARED_JENIS_KEL: String = "jenis_kel"
    val SHARED_TMPT_LAHIR: String = "tmpt_lahir"
    val SHARED_TGL_LAHIR: String = "tgl_lahir"
    val SHARED_TGL_MASUK: String = "tgl_masuk"
    val SHARED_SATUS_PEG: String = "satus_peg"
    val SHARED_AGAMA: String = "agama"
    val SHARED_NAMASUSI: String = "namasusi"
    val SHARED_PKTGOL: String = "pktgol"
    val SHARED_SUBSATKER: String = "subsatker"
    val SHARED_SATKER: String = "satker"
    val SHARED_JABATAN: String = "jabatan"
    val SHARED_KET: String = "ket"
    val SHARED_TLP: String = "tlp"
    val SHARED_PEK: String = "pek"
    val SHARED_ST_DATA: String = "st_data"
    val SHARED_SATKER_FORMATTED: String = "satker_formatted"
    val SHARED_SUBSATKER_FORMATTED: String = "subsatker_formatted"
    val SHARED_APP_VERSION: String = "app_version"
    val SHARED_STATUS_APLIKASI: String = "status_aplikasi"
    val SHARED_APLIKASI_VERSION: String = "aplikasi_version"
    val SHARED_PATH_FOTO: String = "path_foto"
    val SHARED_ABSENSI_MASUK: String = "shared_absensi_masuk"
    val SHARED_ABSENSI_KELUAR: String = "shared_absensi_keluar"

    //bundle
    val BUNDLE_LINK_NEWS: String = "link_news"
    val BUNDLE_NOHP: String = "nohp"
    val BUNDLE_NOLANGG: String = "nomor_langganan"
    val BUNDLE_NO_PENGADUAN_UUID: String = "no_pengaduan_uuid"
    val BUNDLE_NO_PENGADUAN_ANDRO: String = "no_pengaduan_andro"
    val BUNDLE_FOTO_ADUAN: String = "foto_aduan"
    val BUNDLE_FOTO_PENGADU: String = "foto_pengadu"
    val BUNDLE_NAMA_PENGADU: String = "nama_pengadu"
    val BUNDLE_TELP_PENGADU: String = "telp_pengadu"
    val BUNDLE_ALAMAT_PENGADU: String = "alamat_pengadu"
    val BUNDLE_URAIAN: String = "uraian"
    val BUNDLE_STATUS: String = "status"
    val BUNDLE_ALASAN_DITOLAK: String = "alasan_ditolak"
    val BUNDLE_LATITUDE: String = "latitude"
    val BUNDLE_LONGITUDE: String = "longitude"
    val BUNDLE_CREATED_AT: String = "created_at"
    fun changeNoHp(noHpOriginal: String) {
        val subString0: String
        val subStringNomorhp: String
        val noHpFinal: String
        //replace 0 menjadi +62
        subString0 = noHpOriginal.substring(0, 1).replace("0", "+62")
        subStringNomorhp = noHpOriginal.substring(1)
        noHpFinal = subString0 + subStringNomorhp
    }

    fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

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
        editor.apply()

//        context.startActivity(new Intent(context, WelcomeActivity.class));
    }
}