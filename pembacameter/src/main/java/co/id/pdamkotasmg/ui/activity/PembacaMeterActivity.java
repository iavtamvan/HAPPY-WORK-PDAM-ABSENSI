package co.id.pdamkotasmg.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.pdamkotasmg.goodday.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.checkPelangganSudahDibaca.CheckPelangganRootModel;
import co.id.pdamkotasmg.model.fileHandler.PostFotoUploadRootModel;
import co.id.pdamkotasmg.model.listGabungan.ListGabunganRootModel;
import co.id.pdamkotasmg.model.listGabungan.StatusMeterItem;
import co.id.pdamkotasmg.model.updatePembacaMeter.UpdatePembacaMeterRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityPembacaMeterBinding;
import co.id.pdamkotasmg.pembacameter.databinding.ContentHeaderBinding;
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
    private ContentHeaderBinding contentHeaderBinding;

    private List<StatusMeterItem> statusMeterItems = new ArrayList<>();
    private ArrayList<String> arrayStatusMeter = new ArrayList<>();

    private FusedLocationProviderClient mFusedLocation;
    private Double lati, longi;
    private String address_gps;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;

    private EasyImage easyImage;
    private File compressedImageFile1;

    private ProgressDialog progressDialog;

    private String token;
    private String nolangg;
    private String npp;
    private String lalu;
    private String kodeStatusMeter;
    private String codeInputData;
    private String action_code;

    private String filePathServer;
    private String fileUrlServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityPembacaMeterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        contentHeaderBinding = ContentHeaderBinding.inflate(getLayoutInflater());
        View contentRoot = contentHeaderBinding.getRoot();
        setContentView(contentRoot);
        contentHeaderBinding.tvHeaderJudul.setText("Form Pembaca Meter");
        contentHeaderBinding.ivHeaderBackArrow.setOnClickListener(view -> PembacaMeterActivity.this.finish());
        contentHeaderBinding.ivHeaderInfo.setOnClickListener(view -> Toast.makeText(this, "Form Pembaca Meter", Toast.LENGTH_SHORT).show());

        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        npp = sp.getString(Config.SHARED_NPP_PROFILE, "");
        nolangg = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG);
        codeInputData = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA);

        easyImage = new EasyImage.Builder(PembacaMeterActivity.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("GD-Pembaca-Meter")
                .allowMultiple(true)
                .build();

        progressDialog = new ProgressDialog(PembacaMeterActivity.this);
        progressDialog.setMessage("Mohong tunggu ...");
        progressDialog.setCancelable(false);

        if (codeInputData.contains("5")) {
            action_code = "";
            getCheckPelanggan(nolangg);
            binding.tvSystemUpdate.setText("DATA KOREKSI");
        } else if (codeInputData.contains("6")) {
            getCheckPelanggan(nolangg);
            action_code = "1";
            binding.tvSystemUpdate.setText("DATA VERIFIKASI TAPI DITOLAK");
        }

        getCheckPelanggan(nolangg);

        if (kodeStatusMeter == null) {
            kodeStatusMeter = "1";
        }

        mFusedLocation = LocationServices.getFusedLocationProviderClient(PembacaMeterActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                // Do it all with location
                Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                // Display in Toast
                lati = location.getLatitude();
                longi = location.getLongitude();
                Log.d(TAG, "lat: " + lati);
                Log.d(TAG, "long: " + longi);

                if (lati == null || longi == null || lati == 0.0 || longi == 0.0) {
                    Toast.makeText(this, "Alamat tidak ditemukan", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder geocoder;
                    List<Address> addressList = new ArrayList<>();
                    if (addressList == null) {
                        Log.d("debug", "adress list : Null");
                    } else {
                        geocoder = new Geocoder(PembacaMeterActivity.this, Locale.getDefault());
                        try {
                            Log.d(TAG, "Lati: " + lati + " longi" + longi);
                            addressList = geocoder.getFromLocation(lati, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            address_gps = addressList.get(0).getAddressLine(0); // If any additional address_gps line present than only, check with max available address_gps lines by getMaxAddressLineIndex()
                            Log.d(TAG, "onCreate: " + address_gps);
                            if (address_gps == null) {
                                address_gps = "alamat";
                            }
                            city = addressList.get(0).getLocality();
                            if (city == null) {
                                city = "kota";
                            }
                            state = addressList.get(0).getAdminArea();
                            if (state == null) {
                                state = ".";
                            }
                            country = addressList.get(0).getCountryName();
                            if (country == null) {
                                country = "negara";
                            }
                            postalCode = addressList.get(0).getPostalCode();
                            if (postalCode == null) {
                                postalCode = "postal";
                            }
                            knownName = addressList.get(0).getFeatureName(); // Only if available else return NULL
                            if (knownName == null) {
                                knownName = "name";
                            }
                            Log.d("debug", "loc: " + address_gps + " ");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

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
            if (codeInputData.contains("1")) {
                easyImage.openCameraForImage(PembacaMeterActivity.this);
            } else if (codeInputData.contains("2")) {
                easyImage.openCameraForImage(PembacaMeterActivity.this);
            } else if (codeInputData.contains("3")) {
                easyImage.openGallery(PembacaMeterActivity.this);
            } else if (codeInputData.contains("5")) {
                easyImage.openCameraForImage(PembacaMeterActivity.this);
            } else if (codeInputData.contains("6")) {
                easyImage.openCameraForImage(PembacaMeterActivity.this);
            }
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
                            dialogInterface.dismiss();
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
        apiService.postUpdatePembacaMeter(token, nolangg, binding.edtKini.getText().toString().trim(), filePathServer, "192.111.123.1", kodeStatusMeter, binding.edtKeterangan.getText().toString().trim(), action_code)
                .enqueue(new Callback<UpdatePembacaMeterRootModel>() {
                    @Override
                    public void onResponse(Call<UpdatePembacaMeterRootModel> call, Response<UpdatePembacaMeterRootModel> response) {
                        if (response.isSuccessful()) {
                            Config.deleteFiles(compressedImageFile1.getPath(), "ImageCompressDel");
                            progressDialog.cancel();
                            PembacaMeterActivity.this.finish();
                            Toast.makeText(PembacaMeterActivity.this, "Success mengirim data : " + response.body().getData().getUpdateData(), Toast.LENGTH_SHORT).show();
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

    private void getCheckPelanggan(String nolangg) {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(PembacaMeterActivity.this);
        apiService.getCheckPelangganDetail(token, nolangg)
                .enqueue(new Callback<CheckPelangganRootModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<CheckPelangganRootModel> call, Response<CheckPelangganRootModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getKode().contains("0") ||
                                    response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getKode().contains("5") || codeInputData.contains("6")) {
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
                // TODO Cepret,Compress. delete file/image
                showImageWatermark(mediaFiles);
                // TODO delete image
//                    Config.deleteFiles(mediaFiles[0].getFile().getPath(), "ImageOriginal");
            }

            @Override
            public void onImagePickerError(@NonNull Throwable throwable, @NonNull MediaSource mediaSource) {
            }

            @Override
            public void onCanceled(@NonNull MediaSource mediaSource) {
            }
        });
    }

    private void showImageWatermark(MediaFile[] mediaFiles) {
        Glide.with(PembacaMeterActivity.this)
                .asBitmap()
                .load(mediaFiles[0].getFile())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        // Create a Canvas to draw the watermark on the image
                        Canvas canvas = new Canvas(resource);

                        String[] lines = {
                                address_gps,
                                "Lat: " + lati,
                                "Longi: " + longi,
                                nolangg + " (" + npp + ")"
                        };

                        // Customize the watermark text with address and latitude/longitude
                        String watermarkText = address_gps + ",\nLat: " + lati + "Long: " + longi + "\n" + nolangg + "\n" + npp;

                        // Calculate the position to place the watermark (you can adjust this)
                        int xPosition = 20; // X-coordinate
                        int yPosition = 50; // Y-coordinate

                        // Membuat objek Paint
                        Paint paint = new Paint();

                        // Mengatur warna teks
                        paint.setColor(Color.WHITE); // Ganti dengan warna yang Anda inginkan

                        // Mengatur ukuran font
                        paint.setTextSize(40); // Ganti dengan ukuran font yang Anda inginkan

                        // Mengatur gaya teks (contoh: bold)
                        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                        // Mengatur antialiasing (opsional)
                        paint.setAntiAlias(true);

                        // Add the watermark text to the image
                        int textHeight = (int) (paint.descent() - paint.ascent());
                        int lineSpacing = 10; // Adjust the line spacing as needed
                        for (String line : lines) {
                            canvas.drawText(line, 20, yPosition, paint);
                            yPosition += textHeight + lineSpacing; // Move to the next line
                        }

                        // Set the final image with watermark to the ImageView
                        binding.ivCamera.setImageBitmap(resource);

                        saveImageToExternalStorage(resource, mediaFiles);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });
    }

    private void saveImageToExternalStorage(Bitmap bitmap, MediaFile[] mediaFiles) {
        // Define the directory where the image will be saved
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Generate a random UUID
        UUID randomUUID = UUID.randomUUID();
        // Convert the UUID to a string
        String randomUUIDString = randomUUID.toString();

        // Create a unique file name based on the current date
        String fileName = "wtrmk_PM_" + randomUUIDString + ".jpg";

        // Create the destination file
        File destination = new File(directory, fileName);

        try {
            // Create an OutputStream to write the image to the destination file
            OutputStream outputStream = new FileOutputStream(destination);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Add the image to the MediaStore (gallery)
//            MediaStore.Images.Media.insertImage(getContentResolver(), destination.getAbsolutePath(), fileName, null);

            compressedImageFile1 = new File(destination.getPath());

            compressedImageFile1 = new Compressor(PembacaMeterActivity.this)
                    .setMaxHeight(640)
                    .setMaxWidth(480)
                    .setQuality(70)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                    .compressToFile(destination, "comp_PM_" + mediaFiles[0].getFile().getName());

            // Show a toast message indicating that the image has been saved
            Log.d(TAG, "Compresseddd: " + compressedImageFile1);
            Log.d(TAG, "ImageOriginal: " + mediaFiles[0].getFile().getPath());
            Log.d(TAG, "ImageImageSavedWatrermark: " + destination.getPath());

            Config.deleteFiles(mediaFiles[0].getFile().getPath(), "ImageOriginal");
            Config.deleteFiles(destination.getPath(), "ImageSavedStream");
            Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

}