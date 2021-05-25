package com.pdamkotasmg.happywork.fitur.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.format.Formatter
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.an.deviceinfo.device.model.Device
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.pdamkotasmg.happywork.BuildConfig
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.api.server.ApiConfig
import com.pdamkotasmg.happywork.fitur.authentication.WelcomeAuthActivity
import com.pdamkotasmg.happywork.fitur.splash.model.androidVersion.AndroidVersionModel
import com.pdamkotasmg.happywork.fitur.splash.model.packageName.Data
import com.pdamkotasmg.happywork.fitur.splash.model.packageName.DataItem
import com.pdamkotasmg.happywork.fitur.splash.model.packageName.PackageNameRootModel
import com.pdamkotasmg.happywork.utils.Config
import com.shreyaspatil.MaterialDialog.MaterialDialog
import im.delight.android.location.SimpleLocation
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private val TAG: String = "debug"
    // TODO (splash) getting data Update APlikasi DONE
    // TODO (splash) getting data ListFake GPS DONE
    private val RC_CAMERA_AND_LOCATION = 1
    private val SPLASH_TIME_OUT = 3000

    private var androidVersionDevice: String? = null
    private var androidVersionDeviceServer: String? = null
    private val flag = true
    private var packageInfo: PackageInfo? = null
    var i = 0
    private var dataItem: Data? = null
    private var dataItemList: List<DataItem?>? = null

    //device info
    private var device: Device? = null
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
    private var location: SimpleLocation? = null
    private var stringslist: MutableList<String?>? = null
    private var packageString: MutableList<String?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        androidVersionDevice = BuildConfig.VERSION_NAME
        Log.d(TAG, "onCreate: $androidVersionDevice")
        val typeface = ResourcesCompat.getFont(this, R.font.roboto)
        val appname = findViewById<TextView?>(R.id.appname)
        appname.setTypeface(typeface)
        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .playOn(findViewById(R.id.logo))
        YoYo.with(Techniques.FadeInUp)
                .duration(3000)
                .playOn(findViewById(R.id.appname))
        packageString = ArrayList()
        stringslist = ArrayList()
        // TODO 1 getAndroidVersion DONE
        // TODO 2 getPackageNameFromServer DONE
        // TODO 3 getDeviceInfo Done
        // TODO 4 MockLocation Matching DONE
        methodRequiresTwoPermission()
    }

    private fun getAplicationVersionFromServer() {
        val apiService = ApiConfig.getApiService()
        apiService?.getAndroidVersion()?.enqueue(object : Callback<AndroidVersionModel?> {
            override fun onResponse(call: Call<AndroidVersionModel?>?, response: Response<AndroidVersionModel?>?) {
                if (response?.isSuccessful() == true) {
                    androidVersionDeviceServer = response.body()?.data?.androidVersionLatest
                    Log.d(TAG, "Android Version Latest : $androidVersionDeviceServer")
                    if (!androidVersionDevice.equals(androidVersionDeviceServer, ignoreCase = true)) {
                        Toast.makeText(this@SplashScreenActivity, "Perbarui aplikasi kamu", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Update Aplikasi : update aplikasi kamu")
                        val mDialog = MaterialDialog.Builder(this@SplashScreenActivity)
                                .setTitle("Perbarui aplikas kamu") //                                .setMessage("Uninstall fake GPS kamu " + packageInfo.packageName + "\n\n Hubungi kepegawaian untuk aktivasi kembali...")
                                .setAnimation("lt_update.json")
                                .setCancelable(false)
                                .setNegativeButton("Gak mau") { dialogInterface, which ->
                                    dialogInterface.dismiss()
                                    finishAffinity()
                                }
                                .setPositiveButton("Perbarui") { dialogInterface, which -> Toast.makeText(this@SplashScreenActivity, "Link Playstore", Toast.LENGTH_SHORT).show() }
                                .build()

                        // Show Dialog
                        mDialog.show()
                    } else {
                        Log.d(TAG, "Update Aplikasi : Updated")
                        getPackageNameFromServer()
                        Handler().postDelayed({ gettingDataDeviceInfo() }, 2000)
                    }
                }
            }

            override fun onFailure(call: Call<AndroidVersionModel?>?, t: Throwable?) {
                Toast.makeText(this@SplashScreenActivity, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getPackageNameFromServer() {
        val apiService = ApiConfig.getApiService()
        apiService?.getPackageName()?.enqueue(object : Callback<PackageNameRootModel?> {
            override fun onResponse(call: Call<PackageNameRootModel?>?, response: Response<PackageNameRootModel?>?) {
                if (response?.isSuccessful() == true) {
                    dataItem = response.body()?.data
                    dataItemList = dataItem?.data
                    for (j in dataItemList!!.indices) {
                        stringslist?.add(dataItemList?.get(j)?.packageName)
                    }
                    Log.d(TAG, "listPackage: $stringslist")
                }
            }

            override fun onFailure(call: Call<PackageNameRootModel?>?, t: Throwable?) {
                Toast.makeText(this@SplashScreenActivity, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun gettingDataDeviceInfo() {
        val wm = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val connectionInfo = wm.connectionInfo
        val ipAddress = connectionInfo.ipAddress
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        device = Device(this)
        getModel = device?.getModel()
        getProduct = device?.getProduct()
        getDevice = device?.getDevice()
        getBuildBrand = device?.getBuildBrand()
        getOsVersion = device?.getOsVersion()
        getSdkVersion = device?.getSdkVersion().toString()
        getBuildNumber = Build.ID
        getBuildIncremental = Build.VERSION.INCREMENTAL
        getIpAdress = Formatter.formatIpAddress(ipAddress)
        getNetworkUsing = cm.activeNetworkInfo?.getTypeName().toString()
        getHwid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        getSSIDWifi = connectionInfo.ssid.replace("\"", "")
        location = SimpleLocation(this@SplashScreenActivity)
        if (location?.hasLocationEnabled() == false) {
            SimpleLocation.openSettings(this)
        }
        lati = location?.getLatitude()
        longi = location?.getLongitude()
        val geocoder: Geocoder
        val addressList: MutableList<Address?>?
        geocoder = Geocoder(this@SplashScreenActivity, Locale.getDefault())
        try {
            addressList = geocoder.getFromLocation(lati!!, longi!!, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address_gps = addressList[0]?.getAddressLine(0) // If any additional address_gps line present than only, check with max available address_gps lines by getMaxAddressLineIndex()
            city = addressList[0]?.getLocality()
            state = addressList[0]?.getAdminArea()
            country = addressList[0]?.getCountryName()
            postalCode = addressList[0]?.getPostalCode()
            knownName = addressList[0]?.getFeatureName() // Only if available else return NULL
            Log.d("TAG", "loc: $address_gps ")
        } catch (e: IOException) {
            e.printStackTrace()
        }

//        Log.d(TAG, "getModel: " + getModel);
//        Log.d(TAG, "getPFroduct: " + getProduct);
//        Log.d(TAG, "getDevice: " + getDevice);
//        Log.d(TAG, "getBuildBrand: " + getBuildBrand);
//        Log.d(TAG, "getOsVersion: " + getOsVersion);
//        Log.d(TAG, "getSdkVersion: " + getSdkVersion);
//        Log.d(TAG, "getBuildNumber: " + getBuildNumber);
//        Log.d(TAG, "getBuildIncremental: " + getBuildIncremental);
//        Log.d(TAG, "ipAdress: " + getIpAdress);
//        Log.d(TAG, "connectionType: " + getNetworkUsing);
//        Log.d(TAG, "hwid: " + getHwid);
//        Log.d(TAG, "ssid: " + getSSIDWifi);
//        Log.d(TAG, "address_gps: " + address_gps);
//        Log.d(TAG, "city: " + city);
//        Log.d(TAG, "state: " + state);
//        Log.d(TAG, "country: " + country);
//        Log.d(TAG, "postalCode: " + postalCode);
//        Log.d(TAG, "knownName: " + knownName);
        val sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Config.SHARED_GETMODEL, getModel)
        editor.putString(Config.SHARED_GETPRODUCT, getProduct)
        editor.putString(Config.SHARED_GETDEVICE, getDevice)
        editor.putString(Config.SHARED_GETBUILDBRAND, getBuildBrand)
        editor.putString(Config.SHARED_GETOSVERSION, getOsVersion)
        editor.putString(Config.SHARED_GETSDKVERSION, getSdkVersion)
        editor.putString(Config.SHARED_GETBUILDNUMBER, getBuildNumber)
        editor.putString(Config.SHARED_GETBUILDINCREMENTAL, getBuildIncremental)
        editor.putString(Config.SHARED_IPADRESS, getIpAdress)
        editor.putString(Config.SHARED_CONNECTIONTYPE, getNetworkUsing)
        editor.putString(Config.SHARED_HWID, getHwid)
        editor.putString(Config.SHARED_SSID, getSSIDWifi)
        editor.putString(Config.SHARED_ADDRESS_GPS, address_gps)
        editor.putString(Config.SHARED_CITY, city)
        editor.putString(Config.SHARED_STATE, state)
        editor.putString(Config.SHARED_COUNTRY, country)
        editor.putString(Config.SHARED_POSTALCODE, postalCode)
        editor.putString(Config.SHARED_KNOWNNAME, knownName)
        editor.putString(Config.SHARED_LATI, lati.toString())
        editor.putString(Config.SHARED_LONGITUDE, longi.toString())
        editor.apply()
        isMockSettingsON(this@SplashScreenActivity)
    }

    fun isMockSettingsON(context: Context?): Boolean {
        // returns true if mock location enabled, false if not enabled.
        return if (Settings.Secure.getString(context?.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION) == "0") {
            areThereMockPermissionApps(context)
            false
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun areThereMockPermissionApps(context: Context?): Boolean {
        val count = 0
        val pm = context?.getPackageManager()
        val packages = pm?.getInstalledApplications(PackageManager.GET_META_DATA)
        var finding = false
        for (applicationInfo in packages!!) {
            try {
                i = 0
                while (i < stringslist!!.size) {
                    packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS)
                    //                    Log.d(TAG, "packages : " + packageInfo.packageName);
                    if (packageInfo?.packageName.equals(stringslist?.get(i), ignoreCase = true)) {
                        Log.d(TAG, packageInfo?.packageName + " : Fuck Moc: Sama")
                        finding = true
                        break
                    }
                    i++
                }
                if (finding) break
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }
        if (!finding) {
            Handler().postDelayed({

                // TODO intent ke WelcomeActivity.class
                Log.d(TAG, "Status Fack: Tidak ada fake gps")
                startActivity(Intent(this@SplashScreenActivity, WelcomeAuthActivity::class.java))
                finishAffinity()
            }, SPLASH_TIME_OUT.toLong())
        } else {
            val mDialog = MaterialDialog.Builder(this@SplashScreenActivity)
                    .setTitle("Haayyoooooooo kamu" + " ....?")
                    .setMessage("""Uninstall fake GPS kamu ${packageInfo?.packageName} Hubungi kepegawaian untuk aktivasi kembali...""")
                    .setAnimation("lt_bohong.json")
                    .setCancelable(false)
                    .setNegativeButton("Oke deh, jangan suka bohong ya") { dialogInterface, which ->
                        dialogInterface.dismiss()
                        finishAffinity()
                    }
                    .setPositiveButton("Uninstall Aplikasi Presensi") { dialogInterface, which ->
                        Toast.makeText(context, "Uninstall aplikasi presensi beraksi...", Toast.LENGTH_SHORT).show()
                        val intent = Intent(Intent.ACTION_DELETE)
                        intent.data = Uri.parse("package:" + applicationContext.packageName)
                        startActivity(intent)
                    }
                    .build()

            // Show Dialog
            mDialog.show()
        }
        return if (count > 0) true else false
    }

    @AfterPermissionGranted(1)
    private fun methodRequiresTwoPermission() {
        val perms = arrayOf<String?>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
            getAplicationVersionFromServer()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                    RC_CAMERA_AND_LOCATION, *perms)
            Toast.makeText(this, "Izinkan semua permisi yang diberikan", Toast.LENGTH_SHORT).show()
        }
    }
}