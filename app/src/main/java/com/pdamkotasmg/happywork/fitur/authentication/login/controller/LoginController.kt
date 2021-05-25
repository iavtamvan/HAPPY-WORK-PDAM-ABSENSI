package com.pdamkotasmg.happywork.fitur.authentication.login.controller

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.pdamkotasmg.happywork.api.server.ApiConfig
import com.pdamkotasmg.happywork.fitur.authentication.login.LoginActivity
import com.pdamkotasmg.happywork.fitur.authentication.login.model.AkunRootModel
import com.pdamkotasmg.happywork.fitur.authentication.login.model.Data
import com.pdamkotasmg.happywork.fitur.dashboard.DashboardActivity
import com.pdamkotasmg.happywork.utils.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginController {
    lateinit var dataLogin: Data

    //token
    private var access_token: String? = null
    private var token_type: String? = null
    private var avatar: String? = null

    //user
    private var npp_profile: String? = null
    private var name: String? = null

    //rlPegawai
    private var alamat: String? = null
    private var rt: String? = null
    private var rw: String? = null
    private var kelurahan: String? = null
    private var kecamatan: String? = null
    private var kota: String? = null
    private var jenis_kel: String? = null
    private var tmpt_lahir: String? = null
    private var tgl_lahir: String? = null
    private var tgl_masuk: String? = null
    private var satus_peg: String? = null
    private var agama: String? = null
    private var namasusi: String? = null
    private var pktgol: String? = null
    private var subsatker: String? = null
    private var satker: String? = null
    private var jabatan: String? = null
    private var ket: String? = null
    private var tlp: String? = null
    private var pek: String? = null
    private var st_data: String? = null
    private var satker_formatted: String? = null
    private var subsatker_formatted: String? = null
    private var app_version: String? = null
    fun login(
            context: Context?,
            npp: String?,
            password: String?,
            hwid: String?,
            model: String?,
            product: String?,
            device: String?,
            build_brand: String?,
            os_version: String?,
            sdk_version: String?,
            build_number: String?,
            build_incremental: String?,
            ip_address: String?,
            connection_type: String?,
            ssid: String?,
            location_city: String?,
            latitude: String?,
            longitude: String?,
            appVersion: String?
    ) {
        val apiService = ApiConfig.getApiService()
        apiService?.login(npp, password, hwid, model, product, device, build_brand, os_version, sdk_version, build_number,
                build_incremental, ip_address, connection_type, ssid, location_city, latitude, longitude, appVersion)
                ?.enqueue(object : Callback<AkunRootModel> {
                    override fun onResponse(call: Call<AkunRootModel?>?, response: Response<AkunRootModel?>?) {
                        if (response?.isSuccessful == true) {
                            dataLogin = response.body()?.data!!
                            access_token = dataLogin.accessToken
                            Log.d("debug", "token: $access_token")
                            token_type = dataLogin.tokenType
                            npp_profile = dataLogin.user.npp
                            name = dataLogin.user.name
                            avatar = dataLogin.user.avatar
                            if (dataLogin.user.rlPegawai == null) {
                                Log.d("debug ", "RL Pegawai: Kosong")
                            } else {
                                alamat = dataLogin.user.rlPegawai.alamat
                                rt = dataLogin.user.rlPegawai.rt
                                rw = dataLogin.user.rlPegawai.rw
                                kelurahan = dataLogin.user.rlPegawai.kelurahan
                                kecamatan = dataLogin.user.rlPegawai.kecamatan
                                kota = dataLogin.user.rlPegawai.kota
                                jenis_kel = dataLogin.user.rlPegawai.jenisKel
                                tmpt_lahir = dataLogin.user.rlPegawai.tmptLahir
                                tgl_lahir = dataLogin.user.rlPegawai.tglLahir
                                tgl_masuk = dataLogin.user.rlPegawai.tglMasuk
                                satus_peg = dataLogin.user.rlPegawai.satusPeg
                                agama = dataLogin.user.rlPegawai.agama
                                namasusi = dataLogin.user.rlPegawai.namasusi
                                pktgol = dataLogin.user.rlPegawai.pktgol
                                subsatker = dataLogin.user.rlPegawai.subsatker
                                satker = dataLogin.user.rlPegawai.satker
                                jabatan = dataLogin.user.rlPegawai.jabatan
                                ket = dataLogin.user.rlPegawai.ket
                                tlp = dataLogin.user.rlPegawai.tlp
                                pek = dataLogin.user.rlPegawai.pek
                                st_data = dataLogin.user.rlPegawai.stData.toString()
                                satker_formatted = dataLogin.user.rlPegawai.satkerFormatted
                                subsatker_formatted = dataLogin.user.rlPegawai.subsatkerFormatted
                            }
                            app_version = dataLogin.device.appVersion
                            app_version = if (dataLogin.device.appVersion.isEmpty() == true) dataLogin.device.appVersion else ""

                            val sharedPreferences = context?.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                            val editor = sharedPreferences?.edit()
                            editor?.putString(Config.SHARED_ACCESS_TOKEN, "$token_type $access_token")
                            editor?.putString(Config.SHARED_NPP_PROFILE, npp_profile)
                            editor?.putString(Config.SHARED_NAME, name)
                            editor?.putString(Config.SHARED_AVATAR, avatar)
                            editor?.putString(Config.SHARED_ALAMAT, alamat)
                            editor?.putString(Config.SHARED_RT, rt)
                            editor?.putString(Config.SHARED_RW, rw)
                            editor?.putString(Config.SHARED_KELURAHAN, kelurahan)
                            editor?.putString(Config.SHARED_KECAMATAN, kecamatan)
                            editor?.putString(Config.SHARED_KOTA, kota)
                            editor?.putString(Config.SHARED_JENIS_KEL, jenis_kel)
                            editor?.putString(Config.SHARED_TMPT_LAHIR, tmpt_lahir)
                            editor?.putString(Config.SHARED_TGL_LAHIR, tgl_lahir)
                            editor?.putString(Config.SHARED_TGL_MASUK, tgl_masuk)
                            editor?.putString(Config.SHARED_SATUS_PEG, satus_peg)
                            editor?.putString(Config.SHARED_AGAMA, agama)
                            editor?.putString(Config.SHARED_NAMASUSI, namasusi)
                            editor?.putString(Config.SHARED_PKTGOL, pktgol)
                            editor?.putString(Config.SHARED_SUBSATKER, subsatker)
                            editor?.putString(Config.SHARED_SATKER, satker)
                            editor?.putString(Config.SHARED_JABATAN, jabatan)
                            editor?.putString(Config.SHARED_KET, ket)
                            editor?.putString(Config.SHARED_TLP, tlp)
                            editor?.putString(Config.SHARED_PEK, pek)
                            editor?.putString(Config.SHARED_ST_DATA, st_data)
                            editor?.putString(Config.SHARED_SATKER_FORMATTED, satker_formatted)
                            editor?.putString(Config.SHARED_SUBSATKER_FORMATTED, subsatker_formatted)
                            editor?.putString(Config.SHARED_APP_VERSION, app_version)
                            editor?.apply()
                            Toast.makeText(context, "Sukses Login $appVersion", Toast.LENGTH_SHORT).show()
                            (context as LoginActivity?)?.finishAffinity()
                            context?.startActivity(Intent(context, DashboardActivity::class.java))
                        } else {
                            Log.d("debug login res server", "errorBody: " + response?.errorBody())
                            Log.d("debug login res server", "contentLength: " + response?.errorBody()?.contentLength())
                            Log.d("debug login res server", "code: " + response?.code())
                            Log.d("debug login res server", "message: " + response?.message())
                            Toast.makeText(context, "[Else] Login Gagal", Toast.LENGTH_SHORT).show()
                        }



                    }

                    override fun onFailure(call: Call<AkunRootModel?>?, t: Throwable?) {
                        Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}