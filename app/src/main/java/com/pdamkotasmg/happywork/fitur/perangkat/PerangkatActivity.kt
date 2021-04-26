package com.pdamkotasmg.happywork.fitur.perangkat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.pdamkotasmg.happywork.BuildConfig
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.perangkat.PerangkatActivity
import com.pdamkotasmg.happywork.fitur.perangkat.controller.PerangkatController
import com.pdamkotasmg.happywork.fitur.profil.ProfileActivity
import com.pdamkotasmg.happywork.utils.Config
import com.shreyaspatil.MaterialDialog.MaterialDialog

class PerangkatActivity : AppCompatActivity() {
    private var perangkatController: PerangkatController? = null
    private var ivHeaderBackArrow: ImageView? = null
    private var tvHeaderJudul: TextView? = null
    private var ivHeaderInfo: ImageView? = null
    private var tvVersionAplication: TextView? = null
    private var tvNameNamaHp: TextView? = null
    private var tvIpCity: TextView? = null
    private var tvStatusOnline: TextView? = null
    private var tvDeleteAllSession: TextView? = null
    private var rvPerangkatTersambung: RecyclerView? = null
    private val TAG: String = "debug"
    private var city: String? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perangkat)
        supportActionBar?.hide()
        initView()
        tvHeaderJudul?.setText("Perangkat")
        perangkatController = PerangkatController()
        val sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE)
        ivHeaderBackArrow?.setOnClickListener(View.OnClickListener { v: View? ->
            finishAffinity()
            startActivity(Intent(applicationContext, ProfileActivity::class.java))
        })
        ivHeaderInfo?.setVisibility(View.GONE)
        tvDeleteAllSession?.setOnClickListener(View.OnClickListener { v: View? ->
            val mDialog = MaterialDialog.Builder(this@PerangkatActivity)
                    .setTitle("Yakin mau hapus seluruh sesi?")
                    .setMessage("Kamu akan menghapus seluruh sesi versi dulu dan sekarang")
                    .setAnimation("lt_delete.json")
                    .setCancelable(false)
                    .setNegativeButton("Gak jadi") { dialogInterface, which -> dialogInterface.dismiss() }
                    .setPositiveButton("Hapus aja") { dialogInterface, which ->
                        perangkatController?.deleteAllSession(this@PerangkatActivity)
                        // TODO (perangkat) LOGOUT SEMUA SESI TERMASUK YG ONLINE KEMUDIAN KEMBALI KE APLIKASI LAGI
                    }
                    .build()

            // Show Dialog
            mDialog.show()
        })
        tvVersionAplication?.setText(Config.APLICATION_NAME + " v." + BuildConfig.VERSION_NAME)
        tvNameNamaHp?.setText(sharedPreferences.getString(Config.SHARED_GETBUILDBRAND, "")?.toUpperCase() + ", Android (" + sharedPreferences.getString(Config.SHARED_GETOSVERSION, "") + ")")
        city = sharedPreferences.getString(Config.SHARED_CITY, "")
        Log.d(TAG, "onCreate: $city")
        //        if (city.equalsIgnoreCase(null)){
//            city = "Tidak diketahui";
        tvIpCity?.setText(sharedPreferences.getString(Config.SHARED_IPADRESS, "") + " - " + sharedPreferences.getString(Config.SHARED_CITY, ""))
        //        }
        perangkatController?.perangkatOnline(this@PerangkatActivity, rvPerangkatTersambung)
    }

    private fun initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow)
        tvHeaderJudul = findViewById(R.id.tv_header_judul)
        ivHeaderInfo = findViewById(R.id.iv_header_info)
        tvVersionAplication = findViewById(R.id.tv_version_aplication)
        tvNameNamaHp = findViewById(R.id.tv_name_nama_hp)
        tvIpCity = findViewById(R.id.tv_ip_city)
        tvStatusOnline = findViewById(R.id.tv_status_online)
        rvPerangkatTersambung = findViewById(R.id.rv_perangkat_tersambung)
        tvDeleteAllSession = findViewById(R.id.tv_delete_all_session)
    }
}