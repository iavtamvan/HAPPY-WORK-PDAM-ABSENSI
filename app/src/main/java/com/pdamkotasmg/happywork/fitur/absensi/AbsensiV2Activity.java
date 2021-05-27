package com.pdamkotasmg.happywork.fitur.absensi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.api.server.ApiConfig;
import com.pdamkotasmg.happywork.api.server.ApiService;
import com.pdamkotasmg.happywork.fitur.absensi.model.faceDeetectionModel.FaceDetectionRootModel;
import com.pdamkotasmg.happywork.utils.Config;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_v2);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initView();

        tvHeaderJudul.setText("Mengenali Wajah");
        ivHeaderBackArrow.setOnClickListener(v -> {
            finishAffinity();
            startActivity(new Intent(AbsensiV2Activity.this, CheckLocationActivity.class));
        });

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
        tvJabatan.setText(sharedPreferences.getString(Config.SHARED_JABATAN,""));


        ivFotoFront.setOnClickListener(v -> {
//            dispatchTakePictureIntent();
            easyImage.openCameraForImage(AbsensiV2Activity.this);
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
                Glide.with(AbsensiV2Activity.this).load(imageFiles[0].getFile()).override(512, 512).into(ivFotoFront);

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
                    editor.putString(Config.SHARED_COMPRESED_PHOTO_OFFLINE, compressedImageFile.getPath()); // TODO saving OFFLINE PHOTO
                    editor.apply();

                    // TODO delete image
                    File file = new File(imageFiles[0].getFile().getPath());
                    boolean deleted = file.delete();
                    Log.d(TAG, "deletedFilesStatus: " + deleted);

                    // TODO Check Face
                    RequestBody requestFilePhoto = RequestBody.create(MediaType.parse("multipart/form-data"), compressedImageFile.getPath());
                    MultipartBody.Part bodyPhoto = MultipartBody.Part.createFormData("photo", compressedImageFile.getName(), requestFilePhoto);
                    Log.d(TAG, "bodyPhoto: " + bodyPhoto.body());
                    Log.d(TAG, "imageFileCompress: " + compressedImageFile.getName());
                    checkFace(bodyPhoto);


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

    public void checkFace(MultipartBody.Part bodyPhoto) {
        ApiService apiService = ApiConfig.getApiService();
        apiService.checkFace(access_token, bodyPhoto)
                .enqueue(new Callback<FaceDetectionRootModel>() {
                    @Override
                    public void onResponse(Call<FaceDetectionRootModel> call, Response<FaceDetectionRootModel> response) {
                        Log.d(TAG, "onResponse: " + response.body());
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            Toast.makeText(AbsensiV2Activity.this, "Deteksi muka " + response.body().getData().getMatchPercent() + "%, MANTAP", Toast.LENGTH_SHORT).show();
                            editor.putString(Config.SHARED_GET_PHOTO_SERVER_PHOTO_OFFLINE, response.body().getData().getPhoto());
                            editor.apply();
                        } else {
                            Toast.makeText(AbsensiV2Activity.this, "Muka tidak ada", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FaceDetectionRootModel> call, Throwable t) {
                        Toast.makeText(AbsensiV2Activity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
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
    }
}