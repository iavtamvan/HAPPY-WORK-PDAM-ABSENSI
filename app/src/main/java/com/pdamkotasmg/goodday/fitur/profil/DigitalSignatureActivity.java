package com.pdamkotasmg.goodday.fitur.profil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.presensi.model.faceDeetectionModel.FaceDetectionRootModel;
import com.pdamkotasmg.goodday.fitur.profil.model.tte.TTERootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
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

public class DigitalSignatureActivity extends AppCompatActivity {

    public final String TAG = "debug";
    private String npp;
    private String name;
    private SharedPreferences sharedPreferences;
    private String fileTTE;
    private String fileKtp;
    private String access_token;
    private String codeTTE;

    private ProgressDialog progressDialog;

    private EasyImage easyImage;
    private File compressedImageFile;
    private MultipartBody.Part bodyPhotoKtp;
    private MultipartBody.Part bodyPhotoTTE;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private ImageView ivTte;
    private Button btnUpdateTTE;
    private LinearLayout divTtePengguna;
    private LinearLayout divBtnAction;
    private TextView tvNppName;
    private LinearLayout divTteCreate;
    private LinearLayout divUploadKtp;
    private ImageView ivFotoKtp;
    private LinearLayout divDigitalSignatureCreate;
    private TextView tvTutupDialog;
    private SignaturePad signaturePad;
    private Button btnReset;
    private Button btnSave;
    private Button btnSaveTte;
    private Button btnBatal;
    private LinearLayout divSimpanData;
    private ImageView ivFotoResuiltDrawTte;
    private Button btnSelesaiTte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_signature);
        initView();
        getSupportActionBar().hide();

        ivHeaderBackArrow.setOnClickListener(view -> {
            DigitalSignatureActivity.this.finish();
        });

        tvHeaderJudul.setText("Tanda Tangan Elektronik (TTE)");
        ivHeaderInfo.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        access_token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        tvNppName.setText(name + "\n" + npp);

        progressDialog = new ProgressDialog(DigitalSignatureActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        getDocumentTTE();

        divDigitalSignatureCreate.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialogDS = new BottomSheetDialog(DigitalSignatureActivity.this);
            bottomSheetDialogDS.setContentView(R.layout.bottom_sheet_signature_pad);

            tvTutupDialog = bottomSheetDialogDS.findViewById(R.id.tv_tutup_dialog);
            signaturePad = bottomSheetDialogDS.findViewById(R.id.signature_pad);
            btnReset = bottomSheetDialogDS.findViewById(R.id.btnReset);
            btnSave = bottomSheetDialogDS.findViewById(R.id.btnSave);
            divBtnAction = bottomSheetDialogDS.findViewById(R.id.div_btn_action);

            btnReset.setOnClickListener(view1 -> {
                signaturePad.clear();
            });

            btnSave.setOnClickListener(view1 -> {
//                if (signaturePad.isEmpty() || compressedImageFile == null) {
//                    Toast.makeText(this, "Unggah KTP Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                } else {

                Log.d(TAG, "getSignatureBitmap: " + signaturePad.getSignatureBitmap());
                Log.d(TAG, "getTransparentSignatureBitmap: " + signaturePad.getTransparentSignatureBitmap());

                // Assume block needs to be inside a Try/Catch block.
                Date currentTime = Calendar.getInstance().getTime();
                Log.d(TAG, "currentTime: " + currentTime.getTime());

//                    File path = Environment.getExternalStorageDirectory();
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/PDAM");
//                path.mkdir();
                OutputStream fOut = null;
                Integer counter = 0;
                File file = new File(path, "files_DS_" + npp + "_" + currentTime.getTime() + ".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                try {
                    fOut = new FileOutputStream(file);
                    Bitmap pictureBitmap = signaturePad.getSignatureBitmap(); // obtaining the Bitmap
                    pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                    fOut.flush(); // Not really required
                    fOut.close(); // do not forget to close the stream

                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                    fileTTE = file.getAbsolutePath();
                    Log.d(TAG, "getAbsolutePath: " + file.getAbsolutePath());
                    Log.d(TAG, "getName: " + file.getName());
                    codeTTE = "1";

                    bottomSheetDialogDS.dismiss();

                    Glide.with(DigitalSignatureActivity.this).load(fileTTE).into(ivTte);

                    Glide.with(DigitalSignatureActivity.this).load(fileTTE).into(ivFotoResuiltDrawTte);
                    ivFotoResuiltDrawTte.setVisibility(View.VISIBLE);
                    divDigitalSignatureCreate.setVisibility(View.GONE);

//                        Config.deleteFiles(compressedImageFile.getPath(), "ImageCompressed");

                } catch (IOException e) {
                    e.printStackTrace();
                }

//                }

            });

            bottomSheetDialogDS.show();
        });

        // upload KTP drom camera or files
        divUploadKtp.setOnClickListener(view -> {
            easyImage.openChooser(DigitalSignatureActivity.this);
        });


        easyImage = new EasyImage.Builder(DigitalSignatureActivity.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("PDAM-SMG-DIGITAL-SIGNATURE")
                .allowMultiple(true)
                .build();

        btnUpdateTTE.setOnClickListener(view1 -> {
            fileTTE = null;
            fileKtp = null;
            divTtePengguna.setVisibility(View.GONE);
            btnUpdateTTE.setVisibility(View.GONE);
            btnSelesaiTte.setVisibility(View.GONE);
            ivFotoKtp.setVisibility(View.GONE);
            ivFotoResuiltDrawTte.setVisibility(View.GONE);
            divUploadKtp.setVisibility(View.VISIBLE);
            divTteCreate.setVisibility(View.VISIBLE);
            divDigitalSignatureCreate.setVisibility(View.VISIBLE);
            divSimpanData.setVisibility(View.VISIBLE);
            codeTTE = "0";
        });

        btnSelesaiTte.setOnClickListener(view -> {
            DigitalSignatureActivity.this.finish();
        });

        divUploadKtp.setOnClickListener(view -> {
            easyImage.openChooser(DigitalSignatureActivity.this);
        });


        divSimpanData.setVisibility(View.VISIBLE);
        btnBatal.setOnClickListener(view -> {
            fileTTE = null;
            fileKtp = null;
            divTtePengguna.setVisibility(View.GONE);
            btnUpdateTTE.setVisibility(View.GONE);
            ivFotoKtp.setVisibility(View.GONE);
            ivFotoResuiltDrawTte.setVisibility(View.GONE);
            divUploadKtp.setVisibility(View.VISIBLE);
            divTteCreate.setVisibility(View.VISIBLE);
            divDigitalSignatureCreate.setVisibility(View.VISIBLE);
            divSimpanData.setVisibility(View.VISIBLE);
        });

        btnSaveTte.setOnClickListener(view -> {
            if (fileTTE == null || fileKtp == null) {
                Toast.makeText(this, "Lengkapi Form di Atas", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Menigirim ke server...", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "fileTTE: " + fileTTE);
                Log.d(TAG, "fileKtp: " + fileKtp);
                divSimpanData.setVisibility(View.GONE);
                btnUpdateTTE.setVisibility(View.VISIBLE);
                divTteCreate.setVisibility(View.GONE);
                divTtePengguna.setVisibility(View.VISIBLE);

                postUploadTTE();

            }
        });

    }

    private void getDocumentTTE() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(DigitalSignatureActivity.this);
        apiService.getDocumentTTE(access_token, npp).enqueue(new Callback<TTERootModel>() {
            @Override
            public void onResponse(Call<TTERootModel> call, Response<TTERootModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.cancel();

                    String fileKTPServer = response.body().getData().getFotoKtp();
                    String fileTTEServer = response.body().getData().getFotoTtd();

                    if (fileKTPServer == null || fileTTEServer == null) {
                        divTtePengguna.setVisibility(View.GONE);
                        btnUpdateTTE.setVisibility(View.GONE);
                        ivFotoKtp.setVisibility(View.GONE);
                        ivFotoResuiltDrawTte.setVisibility(View.GONE);
                        divUploadKtp.setVisibility(View.VISIBLE);
                        divTteCreate.setVisibility(View.VISIBLE);
                        divDigitalSignatureCreate.setVisibility(View.VISIBLE);
                        divSimpanData.setVisibility(View.VISIBLE);
                    } else {
                        divSimpanData.setVisibility(View.GONE);
//                        btnUpdateTTE.setVisibility(View.VISIBLE);
                        divTteCreate.setVisibility(View.GONE);
                        divTtePengguna.setVisibility(View.VISIBLE);

                        if (codeTTE.equals("1")) {
                            btnUpdateTTE.setVisibility(View.VISIBLE);
                            btnSelesaiTte.setVisibility(View.VISIBLE);
                        } else {
                            btnSelesaiTte.setVisibility(View.GONE);
                            btnUpdateTTE.setVisibility(View.VISIBLE);
                        }

                        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
                        QRGEncoder qrgEncoder = new QRGEncoder(Config.BASE_URL_IMAGE_HANDLER + response.body().getData().getFotoTtd(), null, QRGContents.Type.TEXT, 999);
                        qrgEncoder.setColorBlack(Color.BLACK);
                        qrgEncoder.setColorWhite(Color.WHITE);
                        // Getting QR-Code as Bitmap
                        Bitmap bitmap = qrgEncoder.getBitmap();
                        // Setting Bitmap to ImageView
                        ivTte.setImageBitmap(bitmap);
                    }


                }
            }

            @Override
            public void onFailure(Call<TTERootModel> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(DigitalSignatureActivity.this, "Error mengambil data " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postUploadTTE() {
        progressDialog.show();
        RequestBody nppBody = RequestBody.create(MediaType.parse("text/plain"), npp);

        File imageFileKtp = new File(fileKtp);
        RequestBody requestFilePhotoKtp = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileKtp);
        bodyPhotoKtp = MultipartBody.Part.createFormData("foto_ktp", imageFileKtp.getName(), requestFilePhotoKtp);

        File imageFileTTE = new File(fileTTE);
        RequestBody requestFilePhotoTTE = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileTTE);
        bodyPhotoTTE = MultipartBody.Part.createFormData("foto_ttd", imageFileTTE.getName(), requestFilePhotoTTE);

        ApiService apiService = ApiConfig.getApiService(DigitalSignatureActivity.this);
        apiService.postUploadTTE(access_token, nppBody, bodyPhotoKtp, bodyPhotoTTE)
                .enqueue(new Callback<FaceDetectionRootModel>() {
                    @Override
                    public void onResponse(Call<FaceDetectionRootModel> call, Response<FaceDetectionRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            Toast.makeText(DigitalSignatureActivity.this, "Sukses mengirim data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FaceDetectionRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DigitalSignatureActivity.this, "Error mengirim data TTE", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, DigitalSignatureActivity.this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(@NonNull Throwable throwable, @NonNull MediaSource mediaSource) {
                Toast.makeText(DigitalSignatureActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                // TODO jepret foto
                Log.d(TAG, "onMediaFilesPickedPath: " + imageFiles[0].getFile().getPath());
                Log.d(TAG, "onMediaFilesPickedAbsoluthPath: " + imageFiles[0].getFile().getAbsolutePath());
                Log.d(TAG, "onMediaFilesPickedGetName: " + imageFiles[0].getFile().getName());
                Log.d(TAG, "onMediaFilesPickedGetParent: " + imageFiles[0].getFile().getParent());

                // TODO Compress file/image
                try {
                    compressedImageFile = new Compressor(DigitalSignatureActivity.this)
                            .setMaxHeight(512)
                            .setMaxWidth(512)
                            .setQuality(80)
                            .setCompressFormat(Bitmap.CompressFormat.JPEG)
                            .setDestinationDirectoryPath(imageFiles[0].getFile().getParent())
                            .compressToFile(imageFiles[0].getFile(), "comp_ktp_" + imageFiles[0].getFile().getName());

                    fileKtp = compressedImageFile.getPath();

                    Log.d(TAG, "compressed: " + compressedImageFile.getPath());
                    ivFotoKtp.setVisibility(View.VISIBLE);
                    divUploadKtp.setVisibility(View.GONE);
                    Glide.with(DigitalSignatureActivity.this).load(compressedImageFile.getPath()).override(256, 256).into(ivFotoKtp);

                    // TODO delete image original
                    Config.deleteFiles(imageFiles[0].getFile().getPath(), "ImageOriginal");
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

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        ivTte = findViewById(R.id.iv_tte);
        divTtePengguna = findViewById(R.id.div_tte_pengguna);
        btnUpdateTTE = findViewById(R.id.btnUpdateTTE);
        tvNppName = findViewById(R.id.tv_npp_name);
        divTteCreate = findViewById(R.id.div_tte_create);
        divUploadKtp = findViewById(R.id.div_upload_ktp);
        ivFotoKtp = findViewById(R.id.iv_foto_ktp);
        divDigitalSignatureCreate = findViewById(R.id.div_digital_signature_create);
        btnSaveTte = findViewById(R.id.btn_save_tte);
        ivFotoResuiltDrawTte = findViewById(R.id.iv_foto_resuilt_draw_tte);
        btnBatal = findViewById(R.id.btn_batal);
        divSimpanData = findViewById(R.id.div_simpan_data);
    }
}