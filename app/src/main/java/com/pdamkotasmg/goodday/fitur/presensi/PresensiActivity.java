package com.pdamkotasmg.goodday.fitur.presensi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.krishna.securetimer.SecureTimer;
import com.pdamkotasmg.goodday.BuildConfig;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.activity.KehadiranActivity;
import com.pdamkotasmg.goodday.fitur.presensi.model.faceDeetectionModel.FaceDetectionRootModel;
import com.pdamkotasmg.goodday.fitur.presensi.model.savePresensiModel.SavePresensiRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import com.pdamkotasmg.goodday.utils.Connectivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresensiActivity extends AppCompatActivity {
    private static final String TAG = "debug";
    private String btnPresensi = "Simpan Absensi Android";
    private File compressedImageFile;
    private String access_token;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FusedLocationProviderClient mFusedLocation;

    private Date cDate;

    private String currentDateLocal;
    private String currentDateLocalSendServer;
    private String currentTimeLocal;
    private String currentTimeLocalSendServer;

    private MultipartBody.Part bodyPhoto;
    private Double lati, longi;

    private String timeServer;
    private String dateServer;

    private String connectionType;
    private String getPathPhotoFaceServer;

    private String statusPresensi;
    private String npp;

    // android token
    private String noToken;
    private String androidToken1;
    private String androidToken2;
    private String androidToken3;
    private String androidToken4;
    private String androidToken5;
    private String androidToken6;
    private String androidToken7;
    private String androidToken8;
    private String androidToken9;
    private String androidToken10;

    private CircleImageView ivFotoFront;
    private EasyImage easyImage;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvName;
    private TextView tvJabatan;
    private TextView tvTanggal;
    private TextView tvWaktu;
    private TextView tvPersenFace;
    private TextView tvVersiApps;
    private Button btnKirimPresensi;
    private LinearLayout divMencariMuka;
    private LottieAnimationView animationView;
    private TextView tvMencariMuka;
    private Button btnKirimPresensi2;
    private AdView adView;

    @SuppressLint({"SimpleDateFormat", "SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_v2);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initView();
        // TODO 1 preview camera Done
        // TODO 2 face Detection Done
        // TODO 3 Save Absensi done
        Config.ads(PresensiActivity.this, adView);
        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        access_token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        Log.d(TAG, "token: " + access_token);

        if (!npp.equals(Config.SHARED_ANDROID_TOKEN_1)){
            Config.isMockSettingsONV2(PresensiActivity.this);
        }
        tvHeaderJudul.setText("Mengenali Wajah");
        animationView.setVisibility(View.GONE);
        tvMencariMuka.setText("Ayo foto...");
        tvVersiApps.setText("versi " + BuildConfig.VERSION_NAME);
        ivFotoFront.post(() -> {
            ivFotoFront.performClick();
        });

        ivHeaderBackArrow.setOnClickListener(v -> {
            finishAffinity();
            startActivity(new Intent(PresensiActivity.this, CheckLocationActivity.class));
        });
        ivHeaderInfo.setVisibility(View.GONE);

        if (Connectivity.isConnected(PresensiActivity.this)) {
            Log.d(TAG, "isConnect: Connected");
            connectionType = Connectivity.isConnectionFast(PresensiActivity.this).getConnectionType();
        }

        mFusedLocation = LocationServices.getFusedLocationProviderClient(PresensiActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            LocalDate cDate = LocalDate.now(); // Added 1.8
////            currentDateLocal = new SimpleDateFormat("EEEE, dd MMM yyyy").format(cDate);
//            currentTimeLocal = new SimpleDateFormat("HH:mm").format(cDate);
//        } else {
        cDate = new Date();
        currentDateLocal = new SimpleDateFormat("EEEE, dd MMM yyyy").format(cDate);
        currentTimeLocal = new SimpleDateFormat("HH:mm").format(cDate);
//        }

        Date currentTimeInMillis = SecureTimer.with(PresensiActivity.this).getCurrentDate();
        Log.d("debug", "dateServer: " + currentTimeInMillis);
        timeServer = String.valueOf(currentTimeInMillis);
        timeServer = new SimpleDateFormat("HH:mm:ss").format(currentTimeInMillis);
        Log.d("debug", "timeServer: " + timeServer);
        dateServer = new SimpleDateFormat("yyyy-MM-dd").format(currentTimeInMillis);
        Log.d("debug", "dateServerFix: " + dateServer);

        easyImage = new EasyImage.Builder(PresensiActivity.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("PDAM-KOTA-SMG")
                .allowMultiple(true)
                .build();

        tvTanggal.setText(currentDateLocal); // TODO tanggal local
        tvWaktu.setText(currentTimeLocal); // TODO time local

        androidToken1 = sharedPreferences.getString(Config.SHARED_ANDROID_TOKEN_1, "");
        androidToken2 = sharedPreferences.getString(Config.SHARED_ANDROID_TOKEN_2, "");
        androidToken3 = sharedPreferences.getString(Config.SHARED_ANDROID_TOKEN_3, "");
        androidToken5 = sharedPreferences.getString(Config.SHARED_ANDROID_TOKEN_5, "");

        Log.d(TAG, "onCreateS: " + npp);
        String md5Hash = Config.md5(npp);
        Log.d(TAG, "md5: " + md5Hash);
        currentDateLocalSendServer = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        currentTimeLocalSendServer = new SimpleDateFormat("HH:mm:ss").format(cDate);

        btnKirimPresensi2.setVisibility(View.GONE);
        if (androidToken1.equalsIgnoreCase(md5Hash)) {
            noToken = androidToken1;
            btnKirimPresensi.setEnabled(true);
            btnKirimPresensi2.setEnabled(true);
            btnKirimPresensi2.setText(btnPresensi);
            btnKirimPresensi2.setVisibility(View.GONE); // Harusnya Visible
            Log.d(TAG, "npp Enkrip 1: " + npp + " ? " + noToken);
        } else if (androidToken2.equalsIgnoreCase(md5Hash)) {
            noToken = androidToken2;
            btnKirimPresensi.setEnabled(true);
            btnKirimPresensi2.setEnabled(true);
            btnKirimPresensi2.setText(btnPresensi);
            btnKirimPresensi2.setVisibility(View.GONE); // Harusnya Visible
            Log.d(TAG, "npp Enkrip 2: " + npp + " ? " + noToken);
        } else if (androidToken3.equalsIgnoreCase(md5Hash)) {
            noToken = androidToken3;
            btnKirimPresensi.setEnabled(true);
            btnKirimPresensi2.setEnabled(true);
            btnKirimPresensi2.setText(btnPresensi);
            btnKirimPresensi2.setVisibility(View.GONE); // Harusnya Visible
            Log.d(TAG, "npp Enkrip 3: " + npp + " ? " + noToken);
        } else if (androidToken5.equalsIgnoreCase(md5Hash)) {
            noToken = androidToken5;
            btnKirimPresensi.setEnabled(true);
            btnKirimPresensi2.setEnabled(true);
            btnKirimPresensi2.setText(btnPresensi);
            btnKirimPresensi2.setVisibility(View.GONE); // Harusnya Visible
            Log.d(TAG, "npp Enkrip 5: " + npp + " ? " + noToken);
        } else {
            btnKirimPresensi2.setEnabled(false);
            btnKirimPresensi2.setVisibility(View.GONE);
        }

        // TODO status presensi Online / QRCode / Offline
        statusPresensi = sharedPreferences.getString(Config.SHARED_STATUS_ABSENSI, "");
        if (statusPresensi.equalsIgnoreCase("qrcode")) {
            npp = sharedPreferences.getString(Config.SHARED_NPP_QR_CODE, "");
            tvName.setText(getIntent().getStringExtra(Config.BUNDLE_NAME));
            tvJabatan.setText(getIntent().getStringExtra(Config.BUNDLE_JABATAN));
            Log.d(TAG, "npp qrcode: " + npp);
        } else if (statusPresensi.equalsIgnoreCase("online")) {
            npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
            tvName.setText(sharedPreferences.getString(Config.SHARED_NAME, ""));
            tvJabatan.setText(sharedPreferences.getString(Config.SHARED_JABATAN, ""));
            Log.d(TAG, "npp online: " + npp);
        }

        Log.d(TAG, "statusPresensi: " + statusPresensi);
        Log.d(TAG, "npp log: " + npp);

        lati = Double.valueOf(sharedPreferences.getString(Config.SHARED_LATI, ""));
        longi = Double.valueOf(sharedPreferences.getString(Config.SHARED_LONGITUDE, ""));
        Log.d(TAG, "latShared: " + lati);
        Log.d(TAG, "longShared: " + longi);

        ivFotoFront.setOnClickListener(v -> {
            Camera.CameraInfo info = new Camera.CameraInfo();

            easyImage.openCameraForImage(PresensiActivity.this);
        });

        btnKirimPresensi.setOnClickListener(v -> {
            // TODO kirim server
            statusPresensi = sharedPreferences.getString(Config.SHARED_STATUS_ABSENSI, "");
            if (getPathPhotoFaceServer == null) {
                Toast.makeText(this, "Lakukan cepret foto dahulu", Toast.LENGTH_SHORT).show();
            } else {
                if (statusPresensi.equalsIgnoreCase("qrcode")) {
                    npp = sharedPreferences.getString(Config.SHARED_NPP_QR_CODE, "");
                    Log.d(TAG, "npp normal qrcode: " + npp);
                    savePresensi();
                } else if (statusPresensi.equalsIgnoreCase("online")) {
                    npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
                    Log.d(TAG, "npp normal online: " + npp);
                    savePresensi();
                }
            }

        });

        btnKirimPresensi2.setOnClickListener(v -> {
            mFusedLocation.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    // Do it all with location
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                    // Display in Toast
                    lati = location.getLatitude();
                    longi = location.getLongitude();
                    Log.d(TAG, "lat: " + lati);
                    Log.d(TAG, "long: " + longi);
                }
            });
            npp = noToken;
            currentDateLocalSendServer = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
            currentTimeLocalSendServer = new SimpleDateFormat("HH:mm:ss").format(cDate);
            statusPresensi = "offline";
            Log.d(TAG, "npp fake offline: " + npp + " date : " + currentDateLocalSendServer + " time " + currentTimeLocalSendServer);
            savePresensi();
        });
    }


    @SuppressLint("SetTextI18n")
    public void checkFace() {
        // TODO Check Face
        divMencariMuka.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
        tvMencariMuka.setText("Mencari Wajah");
        tvPersenFace.setVisibility(View.GONE);

        RequestBody statusPresensiBody = RequestBody.create(MediaType.parse("text/plain"), statusPresensi);
        RequestBody nppBody = RequestBody.create(MediaType.parse("text/plain"), npp);

        File imageFile = new File(compressedImageFile.getAbsolutePath());
        RequestBody requestFilePhoto = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        bodyPhoto = MultipartBody.Part.createFormData("photo", imageFile.getName(), requestFilePhoto);

        Log.d(TAG, "bodyPhoto: " + bodyPhoto.body());
        Log.d(TAG, "imageFileCompress: " + imageFile.getName());
        Log.d(TAG, "status: " + statusPresensiBody.toString());
        Log.d(TAG, "nppBody: " + nppBody.toString());
        ApiService apiService = ApiConfig.getApiService();
        apiService.checkFace(access_token, statusPresensiBody, nppBody, bodyPhoto)
                .enqueue(new Callback<FaceDetectionRootModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<FaceDetectionRootModel> call, Response<FaceDetectionRootModel> response) {
                        Log.d(TAG, "onResponseFaces: " + response.body());
                        if (response.isSuccessful()) {
                            if (!npp.equals(Config.SHARED_ANDROID_TOKEN_1)){
                                Config.isMockSettingsONV2(PresensiActivity.this);
                            }
                            assert response.body() != null;
                            boolean faceDetected = response.body().getData().isFaceDetected();
                            if (!faceDetected) {
                                Log.d(TAG, "onResponseerrorBody: " + response.raw());
                                Log.d(TAG, "onResponsecode: " + response.code());
                                Log.d(TAG, "onResponseheaders: " + response.headers());
                                Log.d(TAG, "onResponseheaders: " + response.message());
                                Toast.makeText(PresensiActivity.this, "Wajah tidak ada", Toast.LENGTH_SHORT).show();
                                divMencariMuka.setVisibility(View.GONE);
                                tvPersenFace.setVisibility(View.VISIBLE);
                                tvPersenFace.setTextColor(Color.RED);
                                tvPersenFace.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                                tvPersenFace.setText("Wajah tidak ada, ulangi dengan menekan foto diatas");
                                btnKirimPresensi.setVisibility(View.GONE);
                                btnKirimPresensi.setText("\uD83E\uDD11");
                                btnKirimPresensi2.setVisibility(View.GONE);
                                Config.showNotification(PresensiActivity.this, "AKU SEDIH KARENA....", "Foto ngawur, mau potong TKK ???????");
                            } else {
                                Toast.makeText(PresensiActivity.this, "Deteksi Wajah " + response.body().getData().getMatchPercent() + "%", Toast.LENGTH_SHORT).show();
                                divMencariMuka.setVisibility(View.VISIBLE);
                                animationView.setVisibility(View.GONE);
                                tvMencariMuka.setText("Sukses Deteksi");
                                tvPersenFace.setVisibility(View.VISIBLE);
                                btnKirimPresensi.setVisibility(View.VISIBLE);
                                btnKirimPresensi.setEnabled(true);
                                tvPersenFace.setTextColor(Color.GREEN);
                                tvPersenFace.setText("Deteksi Wajah " + response.body().getData().getMatchPercent() + " % [Match]");
                                getPathPhotoFaceServer = response.body().getData().getPhoto();
                                Log.d(TAG, "getPhoto Server : " + response.body().getData().getPhoto());
                                editor.putString(Config.SHARED_GET_PHOTO_SERVER_PHOTO_OFFLINE, response.body().getData().getPhoto());
                                editor.apply();
                                Config.deleteFiles(compressedImageFile.getAbsolutePath(), "ImageCompressed"); // (2)
                            }
                        } else {
                            if (!npp.equals(Config.SHARED_ANDROID_TOKEN_1)){
                                Config.isMockSettingsONV2(PresensiActivity.this);
                            }
                            divMencariMuka.setVisibility(View.GONE);
                            tvPersenFace.setTextColor(Color.RED);
                            tvPersenFace.setVisibility(View.VISIBLE);
                            tvPersenFace.setText("Wajah tidak ada, ulangi dengan menekan foto diatas\n error : " + response.message());
                            Toast.makeText(PresensiActivity.this, "Fail : " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FaceDetectionRootModel> call, Throwable t) {
                        Toast.makeText(PresensiActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Log.d(TAG, "onFailure: " + t.getCause());
                        Log.d(TAG, "onFailure: " + Arrays.toString(t.getStackTrace()));
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, PresensiActivity.this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(@NonNull Throwable throwable, @NonNull MediaSource mediaSource) {
                Toast.makeText(PresensiActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
//                onPhotosReturned(imageFiles);

                // TODO jepret foto
                Log.d(TAG, "onMediaFilesPickedPath: " + imageFiles[0].getFile().getPath());
                Log.d(TAG, "onMediaFilesPickedAbsoluthPath: " + imageFiles[0].getFile().getAbsolutePath());
                Log.d(TAG, "onMediaFilesPickedGetName: " + imageFiles[0].getFile().getName());
                Log.d(TAG, "onMediaFilesPickedGetParent: " + imageFiles[0].getFile().getParent());

                // TODO Compress file/image
                try {
                    compressedImageFile = new Compressor(PresensiActivity.this)
                            .setMaxHeight(512)
                            .setMaxWidth(512)
                            .setQuality(80)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(imageFiles[0].getFile().getParent())
                            .compressToFile(imageFiles[0].getFile(), "comp_" + imageFiles[0].getFile().getName());
                    Log.d(TAG, "compressed: " + compressedImageFile.getPath());
                    Glide.with(PresensiActivity.this).load(compressedImageFile.getPath()).override(512, 512).into(ivFotoFront);
                    editor.putString(Config.SHARED_COMPRESED_PHOTO_OFFLINE, compressedImageFile.getPath()); // TODO saving OFFLINE PHOTO
                    editor.apply();

                    // TODO delete image
                    Config.deleteFiles(imageFiles[0].getFile().getPath(), "ImageOriginal");

                    checkFace(); // (1)

                } catch (IOException e) {
                    Log.d(TAG, "failureCOmpressed: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void savePresensi() {
        animationView.setVisibility(View.VISIBLE);
        tvMencariMuka.setText("Mengirim Absensi");
        ApiService apiService = ApiConfig.getApiService();
        apiService.savePresensi(access_token, lati, longi, statusPresensi, npp, "0", getPathPhotoFaceServer, connectionType, currentDateLocalSendServer, currentTimeLocalSendServer)
                .enqueue(new Callback<SavePresensiRootModel>() {
                    @Override
                    public void onResponse(Call<SavePresensiRootModel> call, Response<SavePresensiRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "savePresensi: " + response.body().getData());
                            animationView.setVisibility(View.GONE);
                            tvMencariMuka.setText("Selesai Mengirim");
                            btnKirimPresensi.setEnabled(false);

                            Config.showNotification(PresensiActivity.this, "Horeeeeeeeeeee", "Presensi tersimpan dengan baik!");
                            finish();
//                                startActivity(new Intent(PresensiActivity.this, KehadiranActivity.class));
                            Intent intent = new Intent(PresensiActivity.this, KehadiranActivity.class);
                            intent.putExtra(Config.BUNDLE_RIWAYAT_ABSENSI, "1");
                            startActivity(intent);

//                            if (response.body().getData().isIsShiftIn() && response.body().getData().isIsTelat()) {
//                                Config.showNotification(PresensiActivity.this, "AKU SEDIH KARENA....", "Telat absensi " + response.body().getData().getAttendanceDiffMinutes() + " menit , potong TKK deh :((",
//                                        Config.BASE_URL_NOTIF_JIKA_TELAT);
//                                finish();
////                                startActivity(new Intent(PresensiActivity.this, KehadiranActivity.class));
//                                Intent intent = new Intent(PresensiActivity.this, KehadiranActivity.class);
//                                intent.putExtra(Config.BUNDLE_RIWAYAT_ABSENSI, "1");
//                                startActivity(intent);
//                            } else {
//                                Config.showNotification(PresensiActivity.this, "AKU SENANG PRESENSI JAM ...." + tvWaktu.getText().toString().trim(), "Yee, gak dipotong TKK nya hehehe :) ", "" +
//                                        Config.BASE_URL_NOTIF_NORMAL);
//                                finish();
////                                startActivity(new Intent(PresensiActivity.this, KehadiranActivity.class));
//                                Intent intent = new Intent(PresensiActivity.this, KehadiranActivity.class);
//                                intent.putExtra(Config.BUNDLE_RIWAYAT_ABSENSI, "1");
//                                startActivity(intent);
//                            }

//                            if (!response.body().getData().isIsShiftIn() && response.body().getData().isIsTelat()) { // jika shift in true
//                                Config.showNotification(PresensiActivity.this, "AKU SEDIH KARENA....", "Telat absensi " + response.body().getData().getAttendanceDiffMinutes() + " menit , potong TKK deh :((",
//                                        Config.BASE_URL_NOTIF_JIKA_TELAT);
//                                finishAffinity();
//                                startActivity(new Intent(PresensiActivity.this, KehadiranActivity.class));
//                            } else if (response.body().getData().isIsPulangAwal()) { // jika pulang awal TRUE
//                                Config.showNotification(PresensiActivity.this, "AKU SEDIH KARENA....", "Pulang awal kerja, TKK ga aman, FIX :((",
//                                        Config.BASE_URL_NOTIF_JIKA_PULANG_AWAL);
//                                finishAffinity();
//                                startActivity(new Intent(PresensiActivity.this, KehadiranActivity.class));
//                            } else { // jika tidak memenuhi kriteria keduanya FALSE
//                                Config.showNotification(PresensiActivity.this, "AKU SENANG PRESENSI JAM ...." + tvWaktu.getText().toString().trim(), "Yee, gak dipotong TKK nya hehehe :) ", "" +
//                                        Config.BASE_URL_NOTIF_NORMAL);
//                                finishAffinity();
//                                startActivity(new Intent(PresensiActivity.this, KehadiranActivity.class));
//                            }
                            // TODO activity kehadiran (history) DONE
                        } else {
                            Log.d(TAG, "onResponse: " + response.code());
                            Log.d(TAG, "onResponse: " + response.headers());
                            Log.d(TAG, "onResponse: " + response.raw());
                            Log.d(TAG, "onResponse: " + response.message());
                            animationView.setVisibility(View.GONE);
                            tvMencariMuka.setText("Gagal Mengirim");
                            Toast.makeText(PresensiActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                            Config.showNotification(PresensiActivity.this, "" + response.code(), "Error, hubungi PTI ");
                        }
                    }

                    @Override
                    public void onFailure(Call<SavePresensiRootModel> call, Throwable t) {
                        Toast.makeText(PresensiActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivFotoFront = findViewById(R.id.iv_foto_front);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvName = findViewById(R.id.tv_name);
        tvJabatan = findViewById(R.id.tv_jabatan);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvWaktu = findViewById(R.id.tv_waktu);
        tvPersenFace = findViewById(R.id.tv_persen_face);
        tvVersiApps = findViewById(R.id.tv_versi_apps);
        btnKirimPresensi = findViewById(R.id.btn_kirim_presensi);
        divMencariMuka = findViewById(R.id.div_mencari_muka);
        animationView = findViewById(R.id.animation_view);
        tvMencariMuka = findViewById(R.id.tv_mencari_muka);
        btnKirimPresensi2 = findViewById(R.id.btn_kirim_presensi_2);
        adView = findViewById(R.id.adView);
    }
}