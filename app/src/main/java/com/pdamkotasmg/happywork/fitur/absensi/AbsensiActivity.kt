package com.pdamkotasmg.happywork.fitur.absensi

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.text.format.Formatter
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.an.deviceinfo.device.model.Device
import com.krishna.securetimer.SecureTimer
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Audio
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.absensi.AbsensiActivity
import id.zelory.compressor.Compressor
import im.delight.android.location.SimpleLocation
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class AbsensiActivity : AppCompatActivity() {
    // TODO  Biometrck fingerprint
    private val debug: String? = "debug"
    private var currentDate: String? = null
    private var nameUser: String? = null

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
    private var replaceTimeServer: String? = null
    private var replaceTimeServerFinal: String? = null
    private var timeServer: String? = null
    private var imageFrontPath: String? = null
    private val imageBackPath: String? = null
    private var random: Random? = null
    private var randomInteger: Int? = null
    private var compressedImageFile: File? = null

    // TODO kamera front
    // TODO kamera back
    private var cameraBack: CameraView? = null
    private var cameraFront: CameraView? = null
    private val btnJepret: Button? = null
    private var divCamera: LinearLayout? = null
    private var ivHeaderBackArrow: ImageView? = null
    private var tvHeaderJudul: TextView? = null
    private var ivHeaderInfo: ImageView? = null
    private var ivCameraBack: ImageView? = null
    @SuppressLint("HardwareIds", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absensi)
        supportActionBar?.hide()
        initView()
        nameUser = "Nama"
        tvHeaderJudul?.text = "REKAM RUPAMU"
        random = Random()
        randomInteger = random!!.nextInt(80 - 65) + 65
        Log.d("deug", "random Number: $randomInteger")
        cameraFront?.setLifecycleOwner(this@AbsensiActivity)
        cameraFront?.audio = Audio.OFF
        cameraFront?.visibility = View.VISIBLE
        val currentTimeInMillis = SecureTimer.with(this@AbsensiActivity).currentDate
        timeServer = currentTimeInMillis.toString()
        replaceTimeServer = timeServer?.replace(" GMT+07:00 ", "")
        replaceTimeServerFinal = replaceTimeServer?.replace(" ".toRegex(), "")
        Log.d("debug", "timeServer: $replaceTimeServerFinal")
        val calendar = Calendar.getInstance()
        val cDate = Date()
        currentDate = SimpleDateFormat("yyyy-MM-dd").format(cDate)
        Log.d("debug", "dateNow: $currentDate")
        val wm = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val connectionInfo = wm.connectionInfo
        val ipAddress = connectionInfo.ipAddress
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        device = Device(this@AbsensiActivity)
        getModel = device?.model
        getProduct = device?.product
        getDevice = device?.device
        getBuildBrand = device?.buildBrand
        getOsVersion = device?.osVersion
        getSdkVersion = device?.sdkVersion.toString()
        getBuildNumber = Build.ID
        getBuildIncremental = Build.VERSION.INCREMENTAL
        getIpAdress = Formatter.formatIpAddress(ipAddress)
        getNetworkUsing = cm.activeNetworkInfo?.typeName.toString()
        getHwid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        getSSIDWifi = connectionInfo.ssid.replace("\"", "")
        location = SimpleLocation(this@AbsensiActivity)
        if (!location!!.hasLocationEnabled()) {
            SimpleLocation.openSettings(this@AbsensiActivity)
        }
        lati = location?.latitude
        longi = location?.longitude
        val geocoder: Geocoder
        val addressList: MutableList<Address?>?
        geocoder = Geocoder(this@AbsensiActivity, Locale.getDefault())
        try {
            addressList = geocoder.getFromLocation(lati!!, longi!!, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address_gps = addressList[0]?.getAddressLine(0) // If any additional address_gps line present than only, check with max available address_gps lines by getMaxAddressLineIndex()
            city = addressList[0]?.locality
            state = addressList[0]?.adminArea
            country = addressList[0]?.countryName
            postalCode = addressList[0]?.postalCode
            knownName = addressList[0]?.featureName // Only if available else return NULL
            Log.d("debug", "loc: $address_gps ")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d(debug, "getModel: $getModel")
        Log.d(debug, "getProduct: $getProduct")
        Log.d(debug, "getDevice: $getDevice")
        Log.d(debug, "getBuildBrand: $getBuildBrand")
        Log.d(debug, "getOsVersion: $getOsVersion")
        Log.d(debug, "getSdkVersion: $getSdkVersion")
        Log.d(debug, "getBuildNumber: $getBuildNumber")
        Log.d(debug, "getBuildIncremental: $getBuildIncremental")
        Log.d(debug, "ipAdress: $getIpAdress")
        Log.d(debug, "connectionType: $getNetworkUsing")
        Log.d(debug, "hwid: $getHwid")
        Log.d(debug, "ssid: $getSSIDWifi")
        Log.d(debug, "address_gps: $address_gps")
        Log.d(debug, "city: $city")
        Log.d(debug, "state: $state")
        Log.d(debug, "country: $country")
        Log.d(debug, "postalCode: $postalCode")
        Log.d(debug, "knownName: $knownName")
        val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiConn = false
        var isMobileConn = false
        for (network in connMgr.allNetworks) {
            val networkInfo = connMgr.getNetworkInfo(network)
            if (networkInfo?.type == ConnectivityManager.TYPE_WIFI) {
                isWifiConn = isWifiConn or networkInfo.isConnected
                getDevice = networkInfo.extraInfo
            }
            if (networkInfo?.type == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn = isMobileConn or networkInfo.isConnected
            }
        }
        Log.d(debug, "Wifi connected: $isWifiConn")
        Log.d(debug, "Mobile connected: $isMobileConn")

//        cameraBack.setVisibility(View.GONE);
        divCamera?.setOnClickListener { v: View? ->
            cameraFront?.takePicture()
            cameraFront?.addCameraListener(object : CameraListener() {
                override fun onPictureTaken(result: PictureResult) {
                    var mainbitmapFronnt: Bitmap? = null
                    mainbitmapFronnt = decodeSampledBitmapFromResource(result.data, 640, 480)
                    mainbitmapFronnt = RotateBitmap(mainbitmapFronnt, 270f)
                    Log.d("debug", "mainbitmapFronnt back $mainbitmapFronnt")
                    cameraFront?.visibility = View.GONE
                    ivCameraBack?.setImageBitmap(mainbitmapFronnt)
                    ivCameraBack?.isDrawingCacheEnabled = true
                    val makeFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/PDAM-ABSENSI")
                    makeFile.mkdirs()
                    val savedPhoto = File(makeFile, nameUser + "_" + replaceTimeServerFinal + "_" + randomInteger + "_FF.xxfotokampret") // TODO FF artinya Foto Front
                    Log.d("debug", "saved Photo: $savedPhoto")
                    try {
                        val outputStream = FileOutputStream(savedPhoto.path)
                        Log.d("debug", "path aduan: $savedPhoto")
                        Log.d("debug", "nameFile aduan: " + savedPhoto.name)
                        Log.d("debug", "directory aduan : $makeFile")
                        outputStream.write(result.data)
                        outputStream.close()
                        compressedImageFile = Compressor(this@AbsensiActivity)
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).absolutePath)
                                .compressToFile(savedPhoto)
                        Log.d("debug", "compressed (FF): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).name)
                        imageFrontPath = compressedImageFile?.path
                        Log.d("debug", "compressed path (FF): $imageFrontPath")
                        //                        tvPerformClick.post(() -> ivResult.performClick());
//                        divAmbilGambar.setVisibility(View.GONE);
//                        divUlangiAmbilGambar.setVisibility(View.VISIBLE);
                        cameraFront?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            })
        }
        //        cameraFront.startVideo();
//        divCamera.setOnClickListener(v -> {
//            cameraFront.captureImage((cameraKitView, bytes) -> {
//                Toast.makeText(this, "Captured", Toast.LENGTH_SHORT).show();
//                File makeFile = new File(Environment.getExternalStorageDirectory() + "/PDAM-ABSENSI");
//                makeFile.mkdirs();
//                File savedPhoto = new File(makeFile, nameUser + "_" + replaceTimeServer + "_FF.xxkampretnotfailable");
//                try {
//                    FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
//                    Log.d(debug, "path: " + savedPhoto);
//                    Log.d(debug, "nameFile: " + savedPhoto.getName());
//                    Log.d(debug, "directory : " + makeFile);
//                    outputStream.write(bytes);
//                    outputStream.close();
//                    cameraFront.onStop();
//                    cameraFront.stopVideo();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        });
    }

    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Presensi hanya berlaku 1x, jadi tidak bisa keluar", Toast.LENGTH_LONG).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 10000)
    }

    private fun initView() {
        cameraBack = findViewById(R.id.camera_back)
        cameraFront = findViewById(R.id.camera_front)
        divCamera = findViewById(R.id.div_camera)
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow)
        tvHeaderJudul = findViewById(R.id.tv_header_judul)
        ivHeaderInfo = findViewById(R.id.iv_header_info)
        ivCameraBack = findViewById(R.id.iv_camera_back)
    }

    companion object {
        fun decodeSampledBitmapFromResource(data: ByteArray?,
                                            reqWidth: Int, reqHeight: Int): Bitmap? {

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            // BitmapFactory.decodeResource(res, resId, options);
            data?.let { BitmapFactory.decodeByteArray(data, 0, it.size, options) }

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth,
                    reqHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            return data?.let { BitmapFactory.decodeByteArray(data, 0, it.size, options) }
        }

        fun calculateInSampleSize(options: BitmapFactory.Options?,
                                  reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val height = options?.outHeight
            val width = options?.outWidth
            var inSampleSize = 1
            if (height!! > reqHeight || width!! > reqWidth) {
                inSampleSize = if (width!! > height) {
                    Math.round(height as Float / reqHeight as Float)
                } else {
                    Math.round(width as Float / reqWidth as Float)
                }
            }
            return inSampleSize
        }

        // rotate the bitmap to portrait
        fun RotateBitmap(source: Bitmap?, angle: Float): Bitmap? {
            val matrix = Matrix()
            matrix.postRotate(angle)
            return Bitmap.createBitmap(source!!, 0, 0, source.getWidth(),
                    source.getHeight(), matrix, true)
        }
    }
}