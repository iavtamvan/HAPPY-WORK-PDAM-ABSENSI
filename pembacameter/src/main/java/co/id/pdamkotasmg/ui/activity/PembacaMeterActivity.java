package co.id.pdamkotasmg.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pdamkotasmg.goodday.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.checkPelangganSudahDibaca.CheckPelangganRootModel;
import co.id.pdamkotasmg.model.fileHandler.PostFotoUploadRootModel;
import co.id.pdamkotasmg.model.listGabungan.ListGabunganRootModel;
import co.id.pdamkotasmg.model.listGabungan.StatusMeterItem;
import co.id.pdamkotasmg.model.updatePembacaMeter.UpdatePembacaMeterRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityPembacaMeterBinding;
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

public class PembacaMeterActivity extends AppCompatActivity {
    private final String TAG = "debug";
    private ActivityPembacaMeterBinding binding;

    private List<StatusMeterItem> statusMeterItems = new ArrayList<>();
    private ArrayList<String> arrayStatusMeter = new ArrayList<>();

    private EasyImage easyImage;
    private File compressedImageFile1;

    private ProgressDialog progressDialog;

    private String token;
    private String nolangg;
    private String npp;
    private String lalu;
    private String kodeStatusMeter;

    private String filePathServer;
    private String fileUrlServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPembacaMeterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        npp = sp.getString(Config.SHARED_NPP_PROFILE, "");
        nolangg = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG);

        easyImage = new EasyImage.Builder(PembacaMeterActivity.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("GD-Pembaca-Meter")
                .allowMultiple(true)
                .build();

        progressDialog = new ProgressDialog(PembacaMeterActivity.this);
        progressDialog.setMessage("Mohong tunggu ...");
        progressDialog.setCancelable(false);

        getCheckPelanggan();

        if (kodeStatusMeter == null) {
            kodeStatusMeter = "1";
//            Toast.makeText(this, "Before " + kodeStatusMeter, Toast.LENGTH_SHORT).show();
        }
        binding.spnStatusMeter.setOnItemSelectedListener((view, position, id, item) -> {
            kodeStatusMeter = arrayStatusMeter.get(position).substring(0, 1).trim();
//            Toast.makeText(this, "After " + kodeStatusMeter, Toast.LENGTH_SHORT).show();
        });
        binding.edtKini.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    Toast.makeText(PembacaMeterActivity.this, "Isi meter Kini", Toast.LENGTH_SHORT).show();
                    binding.tvHitungKubik.setText(" = 0m3");
                } else {
                    String hitungm3 = String.valueOf(Integer.parseInt(editable.toString()) - Integer.parseInt(lalu));
                    binding.tvHitungKubik.setText(" = " + hitungm3 + "m3");
                }
            }
        });
        binding.ivCamera.setOnClickListener(view -> {
            easyImage.openChooser(PembacaMeterActivity.this);
        });

        binding.btnBukaData.setOnClickListener(view -> {
            if (compressedImageFile1 == null || binding.edtKini.getText().toString().isEmpty()) {
                Toast.makeText(this, "" + Config.ERROR_DATA_REGISTER, Toast.LENGTH_SHORT).show();
            } else {
                // TODO send to server
                // TODO 1 send picture to server
                // TODO 2 send data to server 3.7
                MaterialDialog mDialog = new MaterialDialog.Builder(PembacaMeterActivity.this)
                        .setTitle("Apaka data Anda sudah benar?")
                        .setCancelable(false)
                        .setNegativeButton("Belum", (dialogInterface, which) -> {
                            dialogInterface.dismiss();
                        })
                        .setPositiveButton("Sudah", (dialogInterface, which) -> {
                            postFotoMeter();
                        })
                        .build();

                mDialog.show();

                // TODO Selesai
            }
        });

    }

    private void postFotoMeter() {
        progressDialog.show();
        Date currentTime = Calendar.getInstance().getTime();
        String timestamp = String.valueOf(currentTime.getTime());
        String year = new SimpleDateFormat("Y", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        RequestBody path = RequestBody.create(MediaType.parse("text/plain"), "/pembaca-meter/foto-pembaca-meter/" + year + "/" + month);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), "pembaca-meter-" + nolangg + "-" + npp + "-" + year + month + "-" + timestamp);

        File imageFileMeter = new File(compressedImageFile1.getPath());
        RequestBody requestFilePhotoKtp = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileMeter);
        MultipartBody.Part bodyFileMeter = MultipartBody.Part.createFormData("photo", imageFileMeter.getName(), requestFilePhotoKtp);


        ApiService apiService = ApiConfig.getApiService(PembacaMeterActivity.this);
        apiService.postUploadFoto(token, path, fileName, bodyFileMeter)
                .enqueue(new Callback<PostFotoUploadRootModel>() {
                    @Override
                    public void onResponse(Call<PostFotoUploadRootModel> call, Response<PostFotoUploadRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "uploadImage " + response.body().getData().getFileurl());
                            filePathServer = response.body().getData().getFilepath();
                            fileUrlServer = response.body().getData().getFileurl();
                            postDataPembacaMeter();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostFotoUploadRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(PembacaMeterActivity.this, "Upload Foto Gagal " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postDataPembacaMeter() {
        ApiService apiService = ApiConfig.getApiService(PembacaMeterActivity.this);
        apiService.postUpdatePembacaMeter(token, nolangg, binding.edtKini.getText().toString().trim(), filePathServer, "192.111.123.1", binding.edtKeterangan.getText().toString().trim())
                .enqueue(new Callback<UpdatePembacaMeterRootModel>() {
                    @Override
                    public void onResponse(Call<UpdatePembacaMeterRootModel> call, Response<UpdatePembacaMeterRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            PembacaMeterActivity.this.finish();
                            Toast.makeText(PembacaMeterActivity.this, "Success update data : " + response.body().getData().getUpdateData(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdatePembacaMeterRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(PembacaMeterActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getListGabungan() {
        ApiService apiService = ApiConfig.getApiService(PembacaMeterActivity.this);
        apiService.getListGabungan(token)
                .enqueue(new Callback<ListGabunganRootModel>() {
                    @Override
                    public void onResponse(Call<ListGabunganRootModel> call, Response<ListGabunganRootModel> response) {
                        if (response.isSuccessful()) {
                            statusMeterItems = response.body().getData().getStatusMeter();
                            for (int i = 0; i < statusMeterItems.size(); i++) {
                                String kode = statusMeterItems.get(i).getKode();
                                String nameStatus = statusMeterItems.get(i).getStatus();
                                arrayStatusMeter.add(kode + " " + nameStatus);
                            }
                            binding.spnStatusMeter.setItems(arrayStatusMeter);
                            progressDialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListGabunganRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(PembacaMeterActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getCheckPelanggan() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(PembacaMeterActivity.this);
        apiService.getCheckPelangganDetail(token, nolangg)
                .enqueue(new Callback<CheckPelangganRootModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<CheckPelangganRootModel> call, Response<CheckPelangganRootModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getKode().contains("0")) {
                                getListGabungan();
                                binding.tvNolangg.setText(response.body().getData().get(0).getNolangg());
                                binding.tvPeriode.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getPeriode());
                                binding.tvDism.setText(response.body().getData().get(0).getDism());
                                binding.tvNama.setText(response.body().getData().get(0).getNama());
                                binding.tvAlamat.setText(response.body().getData().get(0).getAlamat());
                                binding.tvTarif.setText(response.body().getData().get(0).getRlTarif().getKode() + " - " + response.body().getData().get(0).getRlTarif().getNmTarif());
                                binding.tvSumberLain.setText(response.body().getData().get(0).getSumur());
                                binding.tvMerekMeter.setText(response.body().getData().get(0).getMerek() + " / " + response.body().getData().get(0).getNomormtr());
                                lalu = response.body().getData().get(0).getRlTrbaca().get(0).getKini();
                                binding.tvLalu.setText(lalu + " - " + response.body().getData().get(0).getRlTrbaca().get(0).getM3() + "m3");
                                binding.tvStatusData.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getNmStatus());
                            } else {
                                binding.svContainer.setVisibility(View.GONE);
                                binding.btnBukaData.setVisibility(View.GONE);
                                binding.tvSystemUpdate.setText(response.body().getData().get(0).getNolangg() +
                                        " Pelanggan sudah dalam status " +
                                        response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getNmStatus());
                                progressDialog.cancel();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckPelangganRootModel> call, Throwable t) {
                        Toast.makeText(PembacaMeterActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, PembacaMeterActivity.this, new EasyImage.Callbacks() {
            @Override
            public void onMediaFilesPicked(@NonNull MediaFile[] mediaFiles, @NonNull MediaSource mediaSource) {
                // TODO Compress file/image
                try {
                    compressedImageFile1 = new Compressor(PembacaMeterActivity.this)
                            .setMaxHeight(640)
                            .setMaxWidth(480)
                            .setQuality(70)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                            .compressToFile(mediaFiles[0].getFile(), "comp_1_" + mediaFiles[0].getFile().getName());
                    Log.d(TAG, "compressed: " + compressedImageFile1.getPath());
                    Glide.with(PembacaMeterActivity.this).load(compressedImageFile1.getPath()).override(512, 512).into(binding.ivCamera);

                    // TODO delete image
//                    Config.deleteFiles(mediaFiles[0].getFile().getPath(), "ImageOriginal");

                } catch (IOException e) {
                    Log.d(TAG, "failureCOmpressed 111: " + e.getMessage());
                    e.printStackTrace();
                }
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