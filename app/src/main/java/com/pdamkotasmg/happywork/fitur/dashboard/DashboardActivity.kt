package com.pdamkotasmg.happywork.fitur.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.marcoscg.fingerauth.FingerAuth
import com.marcoscg.fingerauth.FingerAuth.OnFingerAuthListener
import com.marcoscg.fingerauth.FingerAuthDialog
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.absensi.AbsensiActivity
import com.pdamkotasmg.happywork.fitur.dashboard.DashboardActivity
import com.pdamkotasmg.happywork.fitur.feeds.controller.FeedsController
import com.pdamkotasmg.happywork.fitur.kehadiran.view.KehadiranActivity
import com.pdamkotasmg.happywork.fitur.payslip.PayslipActivity
import com.pdamkotasmg.happywork.fitur.profil.ProfileActivity
import com.pdamkotasmg.happywork.utils.Config
import de.hdodenhof.circleimageview.CircleImageView

class DashboardActivity : AppCompatActivity() {
    private var feedsController: FeedsController? = null
    private var statusExpandedTrue = false
    private var ivTutorialVideo: ImageView? = null
    private var divNamaLengkap: TextView? = null
    private var divRekamWaktu: LinearLayout? = null
    private var divPayslip: LinearLayout? = null
    private var divKehadiran: LinearLayout? = null
    private var divLainnya: LinearLayout? = null
    private var lavThumbUp: LottieAnimationView? = null
    private var ciProfileImage: CircleImageView? = null
    private var rv: RecyclerView? = null
    private var divLainnyaExpanded: CardView? = null
    private var divOvertime: LinearLayout? = null
    private val TAG: String = "debug"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()
        initView()
        feedsController = FeedsController()
        feedsController?.getFeeds(applicationContext, rv!!)
        divLainnyaExpanded?.setVisibility(View.GONE)
        val sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE)
        divNamaLengkap?.setText("Hai, " + sharedPreferences.getString(Config.SHARED_NAME, ""))
        divRekamWaktu?.setOnClickListener(View.OnClickListener { v: View? ->
            val hasFingerprintSupport = FingerAuth.hasFingerprintSupport(this)
            if (hasFingerprintSupport) {
                Log.d(TAG, "fingerprint: enable")
                fingerPrintSHow()
            } else {
                Log.d(TAG, "fingerprint: disable")
                startActivity(Intent(applicationContext, AbsensiActivity::class.java))
            }
        })
        divKehadiran?.setOnClickListener { v: View? -> startActivity(Intent(applicationContext, KehadiranActivity::class.java)) }
        divLainnya?.setOnClickListener { v: View? ->
            statusExpandedTrue = if (!statusExpandedTrue) {
                divLainnyaExpanded?.setVisibility(View.VISIBLE)
                true
            } else {
                divLainnyaExpanded?.setVisibility(View.GONE)
                false
            }
        }
        divPayslip?.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(applicationContext, PayslipActivity::class.java)) })
        divNamaLengkap?.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(applicationContext, ProfileActivity::class.java)) })
    }

    private fun fingerPrintSHow() {
        FingerAuthDialog(this)
                .setTitle("Fingerprint")
                .setCancelable(false)
                .setMaxFailedCount(3) // Number of attemps, default 3
                .setNegativeButton("Batal") { dialogInterface, i -> dialogInterface.dismiss() }
                .setOnFingerAuthListener(object : OnFingerAuthListener {
                    override fun onSuccess() {
                        startActivity(Intent(applicationContext, AbsensiActivity::class.java))
                        Toast.makeText(this@DashboardActivity, "onSuccess ", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure() {
                        Toast.makeText(this@DashboardActivity, "Coba lagi", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError() {
                        Toast.makeText(this@DashboardActivity, "Tidak ada yang sama", Toast.LENGTH_SHORT).show()
                    }
                })
                .show()
    }

    private fun initView() {
        ivTutorialVideo = findViewById(R.id.iv_tutorial_video)
        divNamaLengkap = findViewById(R.id.div_nama_lengkap)
        divRekamWaktu = findViewById(R.id.div_rekam_waktu)
        divPayslip = findViewById(R.id.div_payslip)
        divKehadiran = findViewById(R.id.div_kehadiran)
        divLainnya = findViewById(R.id.div_lainnya)
        lavThumbUp = findViewById(R.id.lav_thumbUp)
        ciProfileImage = findViewById(R.id.ci_profile_image)
        rv = findViewById(R.id.rv)
        divLainnyaExpanded = findViewById(R.id.div_lainnya_expanded)
        divOvertime = findViewById(R.id.div_overtime)
    }

    companion object {
        private const val RC_CAMERA_AND_LOCATION = 1
    }
}