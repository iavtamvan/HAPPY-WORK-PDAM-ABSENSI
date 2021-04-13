package com.pdamkotasmg.happywork.fitur.absensi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.an.deviceinfo.device.model.Device;
import com.krishna.securetimer.SecureTimer;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Audio;
import com.pdamkotasmg.happywork.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import id.zelory.compressor.Compressor;
import im.delight.android.location.SimpleLocation;

public class AbsensiActivity extends AppCompatActivity {
    // TODO  Biometrck fingerprint
    private String debug = "debug";

    private String currentDate;
    private String nameUser;

    //device info
    private Device device;
    private String getModel;
    private String getProduct;
    private String getDevice;
    private String getBuildBrand;
    private String getOsVersion;
    private String getSdkVersion;
    private String getBuildNumber;
    private String getBuildIncremental;
    private String getIpAdress;
    private String getNetworkUsing;
    private String getHwid;
    private String getSSIDWifi;
    private String address_gps;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;
    private Double lati, longi;
    private SimpleLocation location;

    private String replaceTimeServer;
    private String replaceTimeServerFinal;
    private String timeServer;

    private String imageFrontPath;
    private String imageBackPath;
    private Random random;
    private Integer randomInteger;

    private File compressedImageFile;

    // TODO kamera front
    // TODO kamera back
    private CameraView cameraBack;
    private CameraView cameraFront;
    private Button btnJepret;
    private LinearLayout divCamera;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private ImageView ivCameraBack;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        getSupportActionBar().hide();
        initView();
        nameUser = "Nama";
        tvHeaderJudul.setText("REKAM RUPAMU");

        random = new Random();
        randomInteger = random.nextInt(80 - 65) + 65;
        Log.d("deug", "random Number: " + randomInteger);

        cameraFront.setLifecycleOwner(AbsensiActivity.this);
        cameraFront.setAudio(Audio.OFF);
        cameraFront.setVisibility(View.VISIBLE);

        Date currentTimeInMillis = SecureTimer.with(AbsensiActivity.this).getCurrentDate();
        timeServer = String.valueOf(currentTimeInMillis);
        replaceTimeServer = timeServer.replace(" GMT+07:00 ", "");
        replaceTimeServerFinal = replaceTimeServer.replaceAll(" ", "");
        Log.d("debug", "timeServer: " + replaceTimeServerFinal);

        Calendar calendar = Calendar.getInstance();
        Date cDate = new Date();
        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        Log.d("debug", "dateNow: " + currentDate);

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wm.getConnectionInfo();
        int ipAddress = connectionInfo.getIpAddress();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        device = new Device(AbsensiActivity.this);
        getModel = device.getModel();
        getProduct = device.getProduct();
        getDevice = device.getDevice();
        getBuildBrand = device.getBuildBrand();
        getOsVersion = device.getOsVersion();
        getSdkVersion = String.valueOf(device.getSdkVersion());
        getBuildNumber = Build.ID;
        getBuildIncremental = Build.VERSION.INCREMENTAL;
        getIpAdress = Formatter.formatIpAddress(ipAddress);
        getNetworkUsing = String.valueOf(cm.getActiveNetworkInfo().getTypeName());
        getHwid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        getSSIDWifi = connectionInfo.getSSID().replace("\"", "");

        location = new SimpleLocation(AbsensiActivity.this);
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(AbsensiActivity.this);
        }
        lati = location.getLatitude();
        longi = location.getLongitude();
        Geocoder geocoder;
        List<Address> addressList;
        geocoder = new Geocoder(AbsensiActivity.this, Locale.getDefault());
        try {
            addressList = geocoder.getFromLocation(lati, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address_gps = addressList.get(0).getAddressLine(0); // If any additional address_gps line present than only, check with max available address_gps lines by getMaxAddressLineIndex()
            city = addressList.get(0).getLocality();
            state = addressList.get(0).getAdminArea();
            country = addressList.get(0).getCountryName();
            postalCode = addressList.get(0).getPostalCode();
            knownName = addressList.get(0).getFeatureName(); // Only if available else return NULL
            Log.d("debug", "loc: " + address_gps + " ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(debug, "getModel: " + getModel);
        Log.d(debug, "getProduct: " + getProduct);
        Log.d(debug, "getDevice: " + getDevice);
        Log.d(debug, "getBuildBrand: " + getBuildBrand);
        Log.d(debug, "getOsVersion: " + getOsVersion);
        Log.d(debug, "getSdkVersion: " + getSdkVersion);
        Log.d(debug, "getBuildNumber: " + getBuildNumber);
        Log.d(debug, "getBuildIncremental: " + getBuildIncremental);
        Log.d(debug, "ipAdress: " + getIpAdress);
        Log.d(debug, "connectionType: " + getNetworkUsing);
        Log.d(debug, "hwid: " + getHwid);
        Log.d(debug, "ssid: " + getSSIDWifi);
        Log.d(debug, "address_gps: " + address_gps);

        Log.d(debug, "city: " + city);
        Log.d(debug, "state: " + state);
        Log.d(debug, "country: " + country);
        Log.d(debug, "postalCode: " + postalCode);
        Log.d(debug, "knownName: " + knownName);

        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
                getDevice = networkInfo.getExtraInfo();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d(debug, "Wifi connected: " + isWifiConn);
        Log.d(debug, "Mobile connected: " + isMobileConn);

//        cameraBack.setVisibility(View.GONE);
        divCamera.setOnClickListener(v -> {
            cameraFront.takePicture();
            cameraFront.addCameraListener(new CameraListener() {
                @Override
                public void onPictureTaken(@NonNull PictureResult result) {
                    Bitmap mainbitmapFronnt = null;
                    mainbitmapFronnt = decodeSampledBitmapFromResource(result.getData(), 640, 480);
                    mainbitmapFronnt = RotateBitmap(mainbitmapFronnt, 270);
                    Log.d("debug", "mainbitmapFronnt back " + mainbitmapFronnt);
                    cameraFront.setVisibility(View.GONE);
                    ivCameraBack.setImageBitmap(mainbitmapFronnt);
                    ivCameraBack.setDrawingCacheEnabled(true);

                    File makeFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/PDAM-ABSENSI");
                    makeFile.mkdirs();
                    File savedPhoto = new File(makeFile, nameUser + "_" + replaceTimeServerFinal + "_" + randomInteger + "_FF.xxfotokampret"); // TODO FF artinya Foto Front
                    Log.d("debug", "saved Photo: " + savedPhoto);
                    try {
                        FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                        Log.d("debug", "path aduan: " + savedPhoto);
                        Log.d("debug", "nameFile aduan: " + savedPhoto.getName());
                        Log.d("debug", "directory aduan : " + makeFile);
                        outputStream.write(result.getData());
                        outputStream.close();
                        compressedImageFile = new Compressor(AbsensiActivity.this)
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToFile(savedPhoto);
                        Log.d("debug", "compressed (FF): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getName());
                        imageFrontPath = compressedImageFile.getPath();
                        Log.d("debug", "compressed path (FF): " + imageFrontPath);
//                        tvPerformClick.post(() -> ivResult.performClick());
//                        divAmbilGambar.setVisibility(View.GONE);
//                        divUlangiAmbilGambar.setVisibility(View.VISIBLE);
                        cameraFront.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        });
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

    public static Bitmap decodeSampledBitmapFromResource(byte[] data,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    // rotate the bitmap to portrait
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Presensi hanya berlaku 1x, jadi tidak bisa keluar", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 10000);
    }

    private void initView() {
        cameraBack = findViewById(R.id.camera_back);
        cameraFront = findViewById(R.id.camera_front);
        divCamera = findViewById(R.id.div_camera);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        ivCameraBack = findViewById(R.id.iv_camera_back);
    }
}