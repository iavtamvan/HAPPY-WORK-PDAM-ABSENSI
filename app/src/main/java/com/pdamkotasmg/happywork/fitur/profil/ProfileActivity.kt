package com.pdamkotasmg.happywork.fitur.profil

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.dashboard.DashboardActivity
import com.pdamkotasmg.happywork.fitur.perangkat.PerangkatActivity
import com.pdamkotasmg.happywork.fitur.profil.ProfileActivity
import com.pdamkotasmg.happywork.fitur.profil.controller.ProfileController
import com.pdamkotasmg.happywork.utils.Config
import com.shreyaspatil.MaterialDialog.MaterialDialog
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {
    private var profileController: ProfileController? = null

    //token
    private var access_token: String? = null
    private val token_type: String? = null
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
    private var imageView2: ImageView? = null
    private var ivHeaderBackArrow: ImageView? = null
    private var tvHeaderJudul: TextView? = null
    private var ivHeaderInfo: ImageView? = null
    private var divHeaderName: LinearLayout? = null
    private var tvProfileName: TextView? = null
    private var tvProfileJabatan: TextView? = null
    private var ciProfileImage: CircleImageView? = null
    private var scrollView3: ScrollView? = null
    private var tvProfileNpp: TextView? = null
    private var tvPrfileAlamatRtRwKelurahanKecamatanKota: TextView? = null
    private var tvProfileGender: TextView? = null
    private var tvProfileTtl: TextView? = null
    private var tvProfileStatusPegawai: TextView? = null
    private var tvProfileReligion: TextView? = null
    private var tvProfileGolongan: TextView? = null
    private var tvProfileSubSatker: TextView? = null
    private var tvProfileSatker: TextView? = null
    private var cvKlikPerangkat: CardView? = null
    private var cvKlikKeluar: CardView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        initView()
        profileController = ProfileController()
        tvHeaderJudul?.setText("Profil")
        ivHeaderInfo?.setVisibility(View.GONE)
        ivHeaderBackArrow?.setOnClickListener { v: View? ->
            finishAffinity()
            startActivity(Intent(applicationContext, DashboardActivity::class.java))
        }
        val sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE)
        access_token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "")
        npp_profile = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "")
        name = sharedPreferences.getString(Config.SHARED_NAME, "")
        avatar = sharedPreferences.getString(Config.SHARED_AVATAR, "")
        alamat = sharedPreferences.getString(Config.SHARED_ALAMAT, "")
        rt = sharedPreferences.getString(Config.SHARED_RT, "")
        rw = sharedPreferences.getString(Config.SHARED_RW, "")
        kelurahan = sharedPreferences.getString(Config.SHARED_KELURAHAN, "")
        kecamatan = sharedPreferences.getString(Config.SHARED_KECAMATAN, "")
        kota = sharedPreferences.getString(Config.SHARED_KOTA, "")
        jenis_kel = sharedPreferences.getString(Config.SHARED_JENIS_KEL, "")
        tmpt_lahir = sharedPreferences.getString(Config.SHARED_TMPT_LAHIR, "")
        tgl_lahir = sharedPreferences.getString(Config.SHARED_TGL_LAHIR, "")
        tgl_masuk = sharedPreferences.getString(Config.SHARED_TGL_MASUK, "")
        satus_peg = sharedPreferences.getString(Config.SHARED_SATUS_PEG, "")
        agama = sharedPreferences.getString(Config.SHARED_AGAMA, "")
        namasusi = sharedPreferences.getString(Config.SHARED_NAMASUSI, "")
        pktgol = sharedPreferences.getString(Config.SHARED_PKTGOL, "")
        subsatker = sharedPreferences.getString(Config.SHARED_SUBSATKER, "")
        satker = sharedPreferences.getString(Config.SHARED_SATKER, "")
        jabatan = sharedPreferences.getString(Config.SHARED_JABATAN, "")
        ket = sharedPreferences.getString(Config.SHARED_KET, "")
        tlp = sharedPreferences.getString(Config.SHARED_TLP, "")
        pek = sharedPreferences.getString(Config.SHARED_PEK, "")
        st_data = sharedPreferences.getString(Config.SHARED_ST_DATA, "")
        satker_formatted = sharedPreferences.getString(Config.SHARED_SATKER_FORMATTED, "")
        subsatker_formatted = sharedPreferences.getString(Config.SHARED_SUBSATKER_FORMATTED, "")
        tvProfileName?.setText(name)
        tvProfileJabatan?.setText(jabatan)
        tvProfileNpp?.setText(npp_profile)
        tvPrfileAlamatRtRwKelurahanKecamatanKota?.setText("$alamat, Rt$rt, Rw $rw, Kel. $kelurahan, Kec. $kecamatan, $kota")
        tvProfileGender?.setText(jenis_kel)
        tvProfileTtl?.setText("$tmpt_lahir, $tgl_lahir")
        tvProfileStatusPegawai?.setText(satus_peg)
        tvProfileReligion?.setText(agama)
        tvProfileGolongan?.setText(pktgol)
        tvProfileSubSatker?.setText(subsatker)
        tvProfileSatker?.setText(satker)

        cvKlikPerangkat?.setOnClickListener { v: View? -> startActivity(Intent(applicationContext, PerangkatActivity::class.java)) }
        cvKlikKeluar?.setOnClickListener(View.OnClickListener { v: View? ->
            val mDialog = MaterialDialog.Builder(this@ProfileActivity)
                    .setMessage("Yakin mau keluar?")
                    .setAnimation("lt_logout.json")
                    .setCancelable(false)
                    .setNegativeButton("Gak") { dialogInterface, which -> dialogInterface.dismiss() }
                    .setPositiveButton("Iya") { dialogInterface, which ->
                        profileController?.logout(this@ProfileActivity)
                        finishAffinity()
                    }
                    .build()

            // Show Dialog
            mDialog.show()
        })
    }

    private fun initView() {
        imageView2 = findViewById(R.id.imageView2)
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow)
        tvHeaderJudul = findViewById(R.id.tv_header_judul)
        ivHeaderInfo = findViewById(R.id.iv_header_info)
        divHeaderName = findViewById(R.id.div_header_name)
        tvProfileName = findViewById(R.id.tv_profile_name)
        tvProfileJabatan = findViewById(R.id.tv_profile_jabatan)
        ciProfileImage = findViewById(R.id.ci_profile_image)
        scrollView3 = findViewById(R.id.scrollView3)
        tvProfileNpp = findViewById(R.id.tv_profile_npp)
        tvPrfileAlamatRtRwKelurahanKecamatanKota = findViewById(R.id.tv_prfile_alamat_rt_rw_kelurahan_kecamatan_kota)
        tvProfileGender = findViewById(R.id.tv_profile_gender)
        tvProfileTtl = findViewById(R.id.tv_profile_ttl)
        tvProfileStatusPegawai = findViewById(R.id.tv_profile_status_pegawai)
        tvProfileReligion = findViewById(R.id.tv_profile_religion)
        tvProfileGolongan = findViewById(R.id.tv_profile_golongan)
        tvProfileSubSatker = findViewById(R.id.tv_profile_sub_satker)
        tvProfileSatker = findViewById(R.id.tv_profile_satker)
        cvKlikPerangkat = findViewById(R.id.cv_klik_perangkat)
        cvKlikKeluar = findViewById(R.id.cv_klik_keluar)
    }
}