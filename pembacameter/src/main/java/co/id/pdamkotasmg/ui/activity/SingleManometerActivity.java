package co.id.pdamkotasmg.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pdamkotasmg.goodday.utils.Config;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.fileHandler.PostFotoUploadRootModel;
import co.id.pdamkotasmg.model.singleManometer.SingleManomterRoot;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.pembacameter.databinding.ActivitySingleManometerBinding;
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

public class SingleManometerActivity extends AppCompatActivity {
    private ActivitySingleManometerBinding binding;
    private final String TAG = "debug";

    private EasyImage easyImage;
    private String rootPathImage;
    private File compressedImageFile1;
    private SharedPreferences sp;
    private SharedPreferences.Editor editorSp;

    private ProgressDialog progressDialog;

    private Date cDate;
    private String currentDateLocal;

    private String token;
    private String name;
    private String npp;
    private String filePathServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivitySingleManometerBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.ivHeaderBackArrow.setOnClickListener(view -> SingleManometerActivity.this.finish());
        binding.tvHeaderJudul.setText("Input Bacaan Manometer");

        sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sp.getString(Config.SHARED_NAME_DASHBOARD, "");
        npp = sp.getString(Config.BUNDLE_NPP, "");

        cDate = new Date();
        currentDateLocal = new SimpleDateFormat("yyyyMM").format(cDate);

        easyImage = new EasyImage.Builder(SingleManometerActivity.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("GD-Pembaca-Meter")
                .allowMultiple(true)
                .build();

        progressDialog = new ProgressDialog(SingleManometerActivity.this);
        progressDialog.setMessage("Mohong tunggu ...");
        progressDialog.setCancelable(false);

        binding.tvPeriodeManometer.setText(currentDateLocal);
        binding.tvEksekutorManometer.setText(name);

        binding.btnSimpanData.setOnClickListener(view -> {

            if (binding.edtNolanggManometer.getText().toString().isEmpty()) {
                binding.edtNolanggManometer.setError("Nolangg tidak boleh kosong");
                return;
            }

            if (binding.edtTekananManometer.getText().toString().isEmpty()) {
                binding.edtTekananManometer.setError("Tekanan Manometer tidak boleh kosong");
                return;
            }

            if (compressedImageFile1 == null) {
                binding.photoViewManometer.setImageResource(R.drawable.image_not_found);
                Toast.makeText(this, "Pilih foto manometer", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO proses Upload Foto
            progressDialog.show();

            Date currentTime = Calendar.getInstance().getTime();
            String timestamp = String.valueOf(currentTime.getTime());
            String year = new SimpleDateFormat("y", Locale.getDefault()).format(new Date());
            String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());

            RequestBody path = RequestBody.create(MediaType.parse("text/plain"), "/pembaca-meter/foto-pembaca-meter/" + year + "/" + month);
            RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), "pembaca-meter-manometer-single" + binding.edtNolanggManometer.getText().toString() + "-" + npp + "-" + year + month + "-" + timestamp);

            File imageFileMeter = new File(compressedImageFile1.getPath());
            RequestBody requestFileManometer = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileMeter);
            MultipartBody.Part bodyFileManometer = MultipartBody.Part.createFormData("photo", imageFileMeter.getName(), requestFileManometer);

            ApiService apiService = ApiConfig.getApiServiceGWAPI(SingleManometerActivity.this);
            apiService.postUploadFoto(token, path, fileName, bodyFileManometer)
                    .enqueue(new Callback<PostFotoUploadRootModel>() {
                        @Override
                        public void onResponse(Call<PostFotoUploadRootModel> call, Response<PostFotoUploadRootModel> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "uploadImage " + response.body().getData().getFileurl());
                                filePathServer = response.body().getData().getFilepath();
                                // TODO proses Sending database
                                postDataManometer();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostFotoUploadRootModel> call, Throwable t) {
                            progressDialog.cancel();
                            Toast.makeText(SingleManometerActivity.this, "Upload Foto Gagal " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        });

        binding.cvFotoManometer.setOnClickListener(view -> {
            easyImage.openChooser(SingleManometerActivity.this);
        });

        binding.btnBatal.setOnClickListener(view -> {
            binding.edtNolanggManometer.setText("");
            binding.edtTekananManometer.setText("");
            compressedImageFile1 = null;
            binding.photoViewManometer.setVisibility(View.GONE);
            binding.imageViewManometer.setVisibility(View.VISIBLE);
        });

    }

    private void postDataManometer() {
        ApiService apiService = ApiConfig.getApiServiceGWAPI(SingleManometerActivity.this);
        apiService.postUpdateManometerSingle(token, binding.edtNolanggManometer.getText().toString(), binding.edtTekananManometer.getText().toString(), filePathServer)
                .enqueue(new Callback<SingleManomterRoot>() {
                    @Override
                    public void onResponse(Call<SingleManomterRoot> call, Response<SingleManomterRoot> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            Toast.makeText(SingleManometerActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            binding.edtNolanggManometer.setText("");
                            binding.edtTekananManometer.setText("");
                            SingleManometerActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleManomterRoot> call, Throwable t) {
                        Toast.makeText(SingleManometerActivity.this, "Gagal tersimpan - " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, SingleManometerActivity.this, new EasyImage.Callbacks() {
            @Override
            public void onMediaFilesPicked(@NonNull MediaFile[] mediaFiles, @NonNull MediaSource mediaSource) {

                try {
                    binding.photoViewManometer.setVisibility(View.VISIBLE);
                    compressedImageFile1 = new Compressor(SingleManometerActivity.this)
                            .setMaxHeight(400)
                            .setMaxWidth(400)
                            .setQuality(50)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                            .compressToFile(mediaFiles[0].getFile(), "comp1_PM_Manometer_single_" + mediaFiles[0].getFile().getName());
//                    showImageWatermark(compressedImageFile1.getPath());
                    Glide.with(SingleManometerActivity.this).load(compressedImageFile1).error(R.drawable.manometer).into(binding.photoViewManometer);
                    binding.imageViewManometer.setVisibility(View.GONE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // TODO Cepret,Compress. delete file/image
                // TODO delete image
//                    Config.deleteFiles(mediaFiles[0].getFile().getPath(), "ImageOriginal");
                rootPathImage = mediaFiles[0].getFile().getParent();
            }

            @Override
            public void onImagePickerError(@NonNull Throwable throwable, @NonNull MediaSource mediaSource) {
            }

            @Override
            public void onCanceled(@NonNull MediaSource mediaSource) {
            }
        });
    }

}