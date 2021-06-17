package com.pdamkotasmg.goodday.fitur.authentication.login.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.authentication.login.LoginActivity;
import com.pdamkotasmg.goodday.fitur.authentication.login.model.AkunRootModel;
import com.pdamkotasmg.goodday.fitur.authentication.login.model.Data;
import com.pdamkotasmg.goodday.fitur.dashboard.DashboardActivity;
import com.pdamkotasmg.goodday.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {
    private Data dataLogin;
    //token
    private String access_token;
    private String token_type;
    private String avatar;

    //user
    private String npp_profile;
    private String name;

    //rlPegawai
    private String alamat;
    private String rt;
    private String rw;
    private String kelurahan;
    private String kecamatan;
    private String kota;
    private String jenis_kel;
    private String tmpt_lahir;
    private String tgl_lahir;
    private String tgl_masuk;
    private String satus_peg;
    private String agama;
    private String namasusi;
    private String pktgol;
    private String subsatker;
    private String satker;
    private String jabatan;
    private String ket;
    private String tlp;
    private String pek;
    private String st_data;
    private String satker_formatted;
    private String subsatker_formatted;
    private String app_version;

    public void login(
            Context context,
            String npp,
            String password,
            String hwid,
            String model,
            String product,
            String device,
            String build_brand,
            String os_version,
            String sdk_version,
            String build_number,
            String build_incremental,
            String ip_address,
            String connection_type,
            String ssid,
            String location_city,
            String latitude,
            String longitude,
            String appVersion,
            String fcmToken
    ) {
        ApiService apiService = ApiConfig.getApiService();
        apiService.login(npp, password, hwid, model, product, device, build_brand, os_version, sdk_version, build_number, build_incremental, ip_address, connection_type, ssid, location_city, latitude, longitude
                , appVersion, fcmToken).enqueue(new Callback<AkunRootModel>() {
            @Override
            public void onResponse(Call<AkunRootModel> call, Response<AkunRootModel> response) {
                if (response.isSuccessful()) {
                    dataLogin = response.body().getData();

                    access_token = dataLogin.getAccessToken();
                    Log.d("debug", "token: " + access_token);
                    token_type = dataLogin.getTokenType();
                    npp_profile = dataLogin.getUser().getNpp();
                    name = dataLogin.getUser().getName();
                    avatar = dataLogin.getUser().getAvatar();

                    if (dataLogin.getUser().getRlPegawai() == null){
                        Log.d("debug ", "RL Pegawai: Kosong");
                    } else {
                        alamat = dataLogin.getUser().getRlPegawai().getAlamat();
                        rt = dataLogin.getUser().getRlPegawai().getRt();
                        rw = dataLogin.getUser().getRlPegawai().getRw();
                        kelurahan = dataLogin.getUser().getRlPegawai().getKelurahan();
                        kecamatan = dataLogin.getUser().getRlPegawai().getKecamatan();
                        kota = dataLogin.getUser().getRlPegawai().getKota();
                        jenis_kel = dataLogin.getUser().getRlPegawai().getJenisKel();
                        tmpt_lahir = dataLogin.getUser().getRlPegawai().getTmptLahir();
                        tgl_lahir = dataLogin.getUser().getRlPegawai().getTglLahir();
                        tgl_masuk = dataLogin.getUser().getRlPegawai().getTglMasuk();
                        satus_peg = dataLogin.getUser().getRlPegawai().getSatusPeg();
                        agama = dataLogin.getUser().getRlPegawai().getAgama();
                        namasusi = dataLogin.getUser().getRlPegawai().getNamasusi();
                        pktgol = dataLogin.getUser().getRlPegawai().getPktgol();
                        subsatker = dataLogin.getUser().getRlPegawai().getSubsatker();
                        satker = dataLogin.getUser().getRlPegawai().getSatker();
                        jabatan = dataLogin.getUser().getRlPegawai().getJabatan();
                        ket = dataLogin.getUser().getRlPegawai().getKet();
                        tlp = dataLogin.getUser().getRlPegawai().getTlp();
                        pek = dataLogin.getUser().getRlPegawai().getPek();
                        st_data = dataLogin.getUser().getRlPegawai().getStData();
                        satker_formatted = dataLogin.getUser().getRlPegawai().getSatkerFormatted();
                        subsatker_formatted = dataLogin.getUser().getRlPegawai().getSubsatkerFormatted();
                    }

                    app_version = dataLogin.getDevice().getAppVersion();

                    app_version = !dataLogin.getDevice().getAppVersion().isEmpty() ? dataLogin.getDevice().getAppVersion(): "";


                    SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(Config.SHARED_ACCESS_TOKEN, token_type + " " + access_token);
                    editor.putString(Config.SHARED_NPP_PROFILE, npp_profile);
                    editor.putString(Config.SHARED_NAME, name);
                    editor.putString(Config.SHARED_AVATAR, avatar);
                    editor.putString(Config.SHARED_ALAMAT, alamat);
                    editor.putString(Config.SHARED_RT, rt);
                    editor.putString(Config.SHARED_RW, rw);
                    editor.putString(Config.SHARED_KELURAHAN, kelurahan);
                    editor.putString(Config.SHARED_KECAMATAN, kecamatan);
                    editor.putString(Config.SHARED_KOTA, kota);
                    editor.putString(Config.SHARED_JENIS_KEL, jenis_kel);
                    editor.putString(Config.SHARED_TMPT_LAHIR, tmpt_lahir);
                    editor.putString(Config.SHARED_TGL_LAHIR, tgl_lahir);
                    editor.putString(Config.SHARED_TGL_MASUK, tgl_masuk);
                    editor.putString(Config.SHARED_SATUS_PEG, satus_peg);
                    editor.putString(Config.SHARED_AGAMA, agama);
                    editor.putString(Config.SHARED_NAMASUSI, namasusi);
                    editor.putString(Config.SHARED_PKTGOL, pktgol);
                    editor.putString(Config.SHARED_SUBSATKER, subsatker);
                    editor.putString(Config.SHARED_SATKER, satker);
                    editor.putString(Config.SHARED_JABATAN, jabatan);
                    editor.putString(Config.SHARED_KET, ket);
                    editor.putString(Config.SHARED_TLP, tlp);
                    editor.putString(Config.SHARED_PEK, pek);
                    editor.putString(Config.SHARED_ST_DATA, st_data);
                    editor.putString(Config.SHARED_SATKER_FORMATTED, satker_formatted);
                    editor.putString(Config.SHARED_SUBSATKER_FORMATTED, subsatker_formatted);
                    editor.putString(Config.SHARED_APP_VERSION, app_version);
                    editor.putString(Config.SHARED_FCM_TOKEN, fcmToken);

                    editor.apply();

                    Toast.makeText(context, "Sukses Login " + appVersion, Toast.LENGTH_SHORT).show();
                    ((LoginActivity) context).finishAffinity();
                    context.startActivity(new Intent(context, DashboardActivity.class));
                } else {
                    Log.d("debug login res server", "errorBody: " + response.errorBody());
                    Log.d("debug login res server", "contentLength: " + response.errorBody().contentLength());
                    Log.d("debug login res server", "code: " + response.code());
                    Log.d("debug login res server", "message: " + response.message());
                    Toast.makeText(context, "[Else] Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AkunRootModel> call, Throwable t) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
