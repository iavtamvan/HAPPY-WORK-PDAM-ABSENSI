package com.pdamkotasmg.happywork.fitur.authentication.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pdamkotasmg.happywork.BuildConfig
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.authentication.login.LoginActivity
import com.pdamkotasmg.happywork.fitur.authentication.login.controller.LoginController
import com.pdamkotasmg.happywork.utils.Config

class LoginActivity : AppCompatActivity() {
    private val TAG: String = "debug_login"

    private var getModel: String? = null
    private var getProduct: String? = null
    private var getDevice: String? = null
    private var getBuildBrand: String? = null
    private var getOsVersion: String? = null
    private var getSdkVersion: String? = null
    private var getBuildNumber: String? = null
    private var getBuildIncremental: String? = null
    private var getIpAdress: String? = null
    private var getNetworkUsing: String? = null
    private var getHwid: String? = null
    private var getSSIDWifi: String? = null
    private var address_gps: String? = null
    private var city: String? = null
    private var state: String? = null
    private var country: String? = null
    private var postalCode: String? = null
    private var knownName: String? = null
    private var lati: Double? = null
    private var longi: Double? = null
    private var appVersion: String? = null

    private var loginController: LoginController? = null
    private var btnMasuk: Button? = null
    private var edtNpp: EditText? = null
    private var edtPassword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        initView()
        val sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE)
        getModel = sharedPreferences.getString(Config.SHARED_GETMODEL, "")
        getProduct = sharedPreferences.getString(Config.SHARED_GETPRODUCT, "")
        getDevice = sharedPreferences.getString(Config.SHARED_GETDEVICE, "")
        getBuildBrand = sharedPreferences.getString(Config.SHARED_GETBUILDBRAND, "")
        getOsVersion = sharedPreferences.getString(Config.SHARED_GETOSVERSION, "")
        getSdkVersion = sharedPreferences.getString(Config.SHARED_GETSDKVERSION, "")
        getBuildNumber = sharedPreferences.getString(Config.SHARED_GETBUILDNUMBER, "")
        getBuildIncremental = sharedPreferences.getString(Config.SHARED_GETBUILDINCREMENTAL, "")
        getIpAdress = sharedPreferences.getString(Config.SHARED_IPADRESS, "")
        getNetworkUsing = sharedPreferences.getString(Config.SHARED_CONNECTIONTYPE, "")
        getHwid = sharedPreferences.getString(Config.SHARED_HWID, "")
        getSSIDWifi = sharedPreferences.getString(Config.SHARED_SSID, "")
        address_gps = sharedPreferences.getString(Config.SHARED_ADDRESS_GPS, "")
        city = sharedPreferences.getString(Config.SHARED_CITY, "")
        state = sharedPreferences.getString(Config.SHARED_STATE, "")
        country = sharedPreferences.getString(Config.SHARED_COUNTRY, "")
        postalCode = sharedPreferences.getString(Config.SHARED_POSTALCODE, "")
        knownName = sharedPreferences.getString(Config.SHARED_KNOWNNAME, "")
        lati = java.lang.Double.valueOf(sharedPreferences.getString(Config.SHARED_LATI, ""))
        longi = java.lang.Double.valueOf(sharedPreferences.getString(Config.SHARED_LONGITUDE, ""))
        appVersion = BuildConfig.VERSION_NAME
        Log.d(TAG, "getModel: $getModel")
        Log.d(TAG, "getProduct: $getProduct")
        Log.d(TAG, "getDevice: $getDevice")
        Log.d(TAG, "getBuildBrand: $getBuildBrand")
        Log.d(TAG, "getOsVersion: $getOsVersion")
        Log.d(TAG, "getSdkVersion: $getSdkVersion")
        Log.d(TAG, "getBuildNumber: $getBuildNumber")
        Log.d(TAG, "getBuildIncremental: $getBuildIncremental")
        Log.d(TAG, "getIpAdress: $getIpAdress")
        Log.d(TAG, "getNetworkUsing: $getNetworkUsing")
        Log.d(TAG, "getHwid: $getHwid")
        Log.d(TAG, "getSSIDWifi: $getSSIDWifi")
        Log.d(TAG, "address_gps: $address_gps")
        Log.d(TAG, "city: $city")
        Log.d(TAG, "state: $state")
        Log.d(TAG, "country: $country")
        Log.d(TAG, "postalCode: $postalCode")
        Log.d(TAG, "knownName: $knownName")
        Log.d(TAG, "lati: $lati")
        Log.d(TAG, "longi: $longi")
        loginController = LoginController()
        btnMasuk?.setOnClickListener { v: View? ->
            loginController?.login(this@LoginActivity, edtNpp?.getText().toString().trim { it <= ' ' }, edtPassword?.getText().toString().trim { it <= ' ' },
                    getHwid, getModel, getProduct, getDevice, getBuildBrand, getOsVersion, getSdkVersion, getBuildNumber, getBuildIncremental,
                    getIpAdress, getNetworkUsing, getSSIDWifi, city, lati.toString(), longi.toString(), appVersion)
        }
    }

    private fun initView() {
        btnMasuk = findViewById(R.id.btn_masuk)
        edtNpp = findViewById(R.id.edt_npp)
        edtPassword = findViewById(R.id.edt_password)
    }

}