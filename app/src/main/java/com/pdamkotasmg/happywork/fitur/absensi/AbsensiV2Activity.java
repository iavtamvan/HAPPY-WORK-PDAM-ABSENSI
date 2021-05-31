package com.pdamkotasmg.happywork.fitur.absensi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.krishna.securetimer.SecureTimer;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.api.server.ApiConfig;
import com.pdamkotasmg.happywork.api.server.ApiService;
import com.pdamkotasmg.happywork.fitur.absensi.model.faceDeetectionModel.FaceDetectionRootModel;
import com.pdamkotasmg.happywork.fitur.absensi.model.saveAbsensiModel.SaveAbsensiRootModel;
import com.pdamkotasmg.happywork.utils.Config;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import im.delight.android.location.SimpleLocation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsensiV2Activity extends AppCompatActivity {
    private static final String TAG = "debug";
    private File compressedImageFile;
    private String access_token;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String currentDate;
    private String currentTime;

    private MultipartBody.Part bodyPhoto;
    private Double lati, longi;
    private SimpleLocation location;

    private String timeServer;
    private String dateServer;

    private String photoPathOffline;
    private String latiOffline;
    private String longitudeOffline;
    private String tanggalOffline;
    private String timeOffline;
    private String get_photo_server_photoOffline;
    private String statusTypeOffline;

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
    private Button btnKirimAbsensi;
    private LinearLayout divMencariMuka;
    private LottieAnimationView animationView;
    private TextView tvMencariMuka;

    @SuppressLint({"SimpleDateFormat", "SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_v2);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initView();

        tvHeaderJudul.setText("Mengenali Wajah");
        animationView.setVisibility(View.GONE);
        tvMencariMuka.setText("Ayo foto...");
        ivFotoFront.post(() -> {
            ivFotoFront.performClick();
        });

        ivHeaderBackArrow.setOnClickListener(v -> {
            finishAffinity();
            startActivity(new Intent(AbsensiV2Activity.this, CheckLocationActivity.class));
        });

        Date cDate = new Date();
        currentDate = new SimpleDateFormat("EEEE, dd MMM yyyy").format(cDate);
        currentTime = new SimpleDateFormat("HH:mm").format(cDate);

        Date currentTimeInMillis = SecureTimer.with(AbsensiV2Activity.this).getCurrentDate();
        Log.d("debug", "dateServer: " + currentTimeInMillis);
        timeServer = String.valueOf(currentTimeInMillis);
        timeServer = new SimpleDateFormat("HH:mm:ss").format(currentTimeInMillis);
        Log.d("debug", "timeServer: " + timeServer);
        dateServer = new SimpleDateFormat("yyyy-MM-dd").format(currentTimeInMillis);
        Log.d("debug", "dateServerFix: " + dateServer);

        easyImage = new EasyImage.Builder(AbsensiV2Activity.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("PDAM-KOTA-SMG")
                .allowMultiple(true)
                .build();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        access_token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        Log.d(TAG, "token: " + access_token);
        tvName.setText(sharedPreferences.getString(Config.SHARED_NAME, ""));
        tvJabatan.setText(sharedPreferences.getString(Config.SHARED_JABATAN, ""));
        tvTanggal.setText(currentDate);
        tvWaktu.setText(currentTime);

        location = new SimpleLocation(AbsensiV2Activity.this);
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(AbsensiV2Activity.this);
        }
        lati = location.getLatitude();
        longi = location.getLongitude();
        editor.putString(Config.SHARED_LATI_OFFLINE, String.valueOf(lati));
        editor.putString(Config.SHARED_LONGITUDE_OFFLINE, String.valueOf(longi));
        editor.putString(Config.SHARED_TANGGAL_OFFLINE, dateServer);
        editor.putString(Config.SHARED_TIME_OFFLINE, timeServer);
        editor.apply();

        ivFotoFront.setOnClickListener(v -> {
            easyImage.openCameraForImage(AbsensiV2Activity.this);
        });

        btnKirimAbsensi.setOnClickListener(v -> {
            // TODO kirim server
            saveAbsensi();
        });
    }

    private void getSavingOffline() {
        photoPathOffline = sharedPreferences.getString(Config.SHARED_COMPRESED_PHOTO_OFFLINE, "");
        latiOffline = sharedPreferences.getString(Config.SHARED_LATI_OFFLINE, "");
        longitudeOffline = sharedPreferences.getString(Config.SHARED_LONGITUDE_OFFLINE, "");
        tanggalOffline = sharedPreferences.getString(Config.SHARED_TANGGAL_OFFLINE, "");
        timeOffline = sharedPreferences.getString(Config.SHARED_TIME_OFFLINE, "");
        get_photo_server_photoOffline = sharedPreferences.getString(Config.SHARED_GET_PHOTO_SERVER_PHOTO_OFFLINE, "");
        statusTypeOffline = sharedPreferences.getString(Config.SHARED_STATUS_TYPE, "offline");
    }

    @SuppressLint("SetTextI18n")
    private void saveAbsensi() {
        animationView.setVisibility(View.VISIBLE);
        tvMencariMuka.setText("Mengirim Absensi");
        getSavingOffline();
        ApiService apiService = ApiConfig.getApiService();
        apiService.saveAbsensi(access_token, latiOffline, longitudeOffline, "online", "0", get_photo_server_photoOffline)
                .enqueue(new Callback<SaveAbsensiRootModel>() {
                    @Override
                    public void onResponse(Call<SaveAbsensiRootModel> call, Response<SaveAbsensiRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "saveAbsensi: " + response.body().getData());
                            animationView.setVisibility(View.GONE);
                            tvMencariMuka.setText("Selesai Mengirim");
                            Config.showNotification(AbsensiV2Activity.this, "AKU SENANG ABSEN JAM ...." + tvWaktu.getText().toString().trim(), "Yee, gak dipotong TPP nya hehehe :) "); // (3)
                            // TODO activity kehadiran (history)
                        } else {
                            Toast.makeText(AbsensiV2Activity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                            Config.showNotification(AbsensiV2Activity.this, "" + response.code(), "Error, hubungi PTI ");
                        }
                    }

                    @Override
                    public void onFailure(Call<SaveAbsensiRootModel> call, Throwable t) {
                        Toast.makeText(AbsensiV2Activity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
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
                    compressedImageFile = new Compressor(AbsensiV2Activity.this)
                            .setMaxHeight(640)
                            .setMaxWidth(480)
                            .setQuality(70)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(imageFiles[0].getFile().getParent())
                            .compressToFile(imageFiles[0].getFile(), "comp_" + imageFiles[0].getFile().getName());
                    Log.d(TAG, "compressed: " + compressedImageFile.getPath());
                    Glide.with(AbsensiV2Activity.this).load(compressedImageFile.getPath()).override(512, 512).into(ivFotoFront);
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
    public void checkFace() {
        // TODO Check Face
        divMencariMuka.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
        tvMencariMuka.setText("Mencari Muka");
        tvPersenFace.setVisibility(View.GONE);
        File imageFile = new File(compressedImageFile.getAbsolutePath());
        RequestBody requestFilePhoto = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        bodyPhoto = MultipartBody.Part.createFormData("photo", imageFile.getName(), requestFilePhoto);
        Log.d(TAG, "bodyPhoto: " + bodyPhoto.body());
        Log.d(TAG, "imageFileCompress: " + imageFile.getName());
        ApiService apiService = ApiConfig.getApiService();
        apiService.checkFace(access_token, bodyPhoto)
                .enqueue(new Callback<FaceDetectionRootModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<FaceDetectionRootModel> call, Response<FaceDetectionRootModel> response) {
                        Log.d(TAG, "onResponse: " + response.body());
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            boolean faceDetected = response.body().getData().isFaceDetected();
                            if (!faceDetected) {
                                Log.d(TAG, "onResponseerrorBody: " + response.raw());
                                Log.d(TAG, "onResponsecode: " + response.code());
                                Log.d(TAG, "onResponseheaders: " + response.headers());
                                Log.d(TAG, "onResponseheaders: " + response.message());
                                Toast.makeText(AbsensiV2Activity.this, "Muka tidak ada", Toast.LENGTH_SHORT).show();
                                divMencariMuka.setVisibility(View.GONE);
                                tvPersenFace.setVisibility(View.VISIBLE);
                                tvPersenFace.setTextColor(Color.RED);
                                tvPersenFace.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                                tvPersenFace.setText("Muka tidak ada, ULANGI.......");
                                Config.showNotification(AbsensiV2Activity.this, "AKU SEDIH KARENA....", "Foto ngawur, mau potong TPP ???????"); // (3)
                            } else {
                                Toast.makeText(AbsensiV2Activity.this, "Deteksi muka " + response.body().getData().getMatchPercent() + "%, MANTAP", Toast.LENGTH_SHORT).show();
                                divMencariMuka.setVisibility(View.VISIBLE);
                                animationView.setVisibility(View.GONE);
                                tvMencariMuka.setText("Sukses Deteksi");
                                tvPersenFace.setVisibility(View.VISIBLE);
                                btnKirimAbsensi.setEnabled(true);
                                tvPersenFace.setTextColor(Color.GRAY);
                                tvPersenFace.setText("Deteksi muka " + response.body().getData().getMatchPercent() + "%, MANTAP");
                                Log.d(TAG, "getPhoto Server : " + response.body().getData().getPhoto());
                                editor.putString(Config.SHARED_GET_PHOTO_SERVER_PHOTO_OFFLINE, response.body().getData().getPhoto());
                                editor.apply();
                                Config.deleteFiles(compressedImageFile.getAbsolutePath(), "ImageCompressed"); // (2)
                            }
                        } else {
                            divMencariMuka.setVisibility(View.GONE);
                            Toast.makeText(AbsensiV2Activity.this, "Fail : " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FaceDetectionRootModel> call, Throwable t) {
                        Toast.makeText(AbsensiV2Activity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Log.d(TAG, "onFailure: " + t.getCause());
                        Log.d(TAG, "onFailure: " + Arrays.toString(t.getStackTrace()));
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
        btnKirimAbsensi = findViewById(R.id.btn_kirim_absensi);
        divMencariMuka = findViewById(R.id.div_mencari_muka);
        animationView = findViewById(R.id.animation_view);
        tvMencariMuka = findViewById(R.id.tv_mencari_muka);
    }
}