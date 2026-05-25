package co.id.pdamkotasmg.ui.activity.bendel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pdamkotasmg.goodday.BuildConfig;
import com.pdamkotasmg.goodday.fitur.menuLainnya.ProfilePelangganDanTagihanActivity;
import com.pdamkotasmg.goodday.utils.Config;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.local.SettingsManager;
import co.id.pdamkotasmg.local.db.entity.PendingBacaanEntity;
import co.id.pdamkotasmg.local.repository.BacaanRepository;
import co.id.pdamkotasmg.model.bendel.bendelNext.Data;
import co.id.pdamkotasmg.model.checkPelangganSudahDibaca.CheckPelangganRootModel;
import co.id.pdamkotasmg.model.fileHandler.PostFotoUploadRootModel;
import co.id.pdamkotasmg.model.listGabungan.ListGabunganRootModel;
import co.id.pdamkotasmg.model.listGabungan.StatusMeterItem;
import co.id.pdamkotasmg.model.updatePembacaMeter.UpdatePembacaMeterRootModel;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityBendelPembacaBinding;
import co.id.pdamkotasmg.ui.activity.DetailRiwayatPembacaMeterActivity;
import co.id.pdamkotasmg.ui.activity.RiwayatPembacaMeterActivity;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
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

/**
 * BendelPembacaKhususActivity — Fase 3.
 *
 * Tombol Simpan jadi cabang berdasarkan toggle Mode Offline:
 *   ON  → save ke Room (PendingBacaanEntity + PendingFotoEntity)
 *         + update cache pelanggan jadi sudah-dibaca
 *         + tampilkan toast "Tersimpan offline, akan dikirim saat sync"
 *   OFF → flow lama (upload foto → post bacaan ke API)
 *         + update cache pelanggan jadi sudah-dibaca (post-API)
 *
 * Banner kuning di top tampil saat Mode Offline ON, untuk awareness user.
 */
public class BendelPembacaKhususActivity extends AppCompatActivity {
    private final String TAG = "debug";
    private ActivityBendelPembacaBinding binding;

    private List<StatusMeterItem> statusMeterItems = new ArrayList<>();
    private ArrayList<String> arrayStatusMeter = new ArrayList<>();
    private Data dataItems;

    private FusedLocationProviderClient mFusedLocation;
    private Double lati, longi;
    private String address_gps;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;

    private EasyImage easyImage;
    private File compressedImageFileFotoMeter;
    private SharedPreferences sp;
    private SharedPreferences.Editor editorSp;

    private ExecutorService geocoderExecutor;
    private Handler mainHandler;

    private Date cDate;
    private String currentDateLocal;
    private String currentTimeLocal;

    private String token;
    private String nolangg;
    private String npp;
    private String lalu;
    private String kodeStatusMeter;
    private String codeInputData;
    private String codeBendel;
    private String codeSimpandanLanjut;
    private String action_code;
    private String modelDevice;

    private String filePathServer;
    private String fileUrlServer;
    private String rootPathImage;
    private String pathImageWatermark;

    private String reqCodeFoto;
    private String reqCodeRotateFotoMeter = "0";

    private String getFilePathServerFotoManometer;
    private File compressedImageFileManometer;

    private UUID randomUUID;
    private String randomUUIDString;
    private Date currentTime;
    private String timestamp;

    // FASE 3 ADDITIONS
    private SettingsManager settings;
    private BacaanRepository bacaanRepository;
    private String periode;
    private String cabang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBendelPembacaBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.ivHeaderBackArrow.setOnClickListener(view -> BendelPembacaKhususActivity.this.finish());
        binding.tvHeaderJudul.setText("Input Bacaan Khusus Bendel");

        sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editorSp = sp.edit();
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        npp = sp.getString(Config.SHARED_NPP_PROFILE, "");
        modelDevice = sp.getString(Config.SHARED_GETMODEL, "");
        periode = sp.getString(Config.SHARED_PERIODE, "");
        cabang = sp.getString(Config.SHARED_CABANG, "");
        nolangg = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG);

        // Best-effort: Activity ini biasanya dibuka dari BendelDataActivity, jadi
        // codeBendel kemungkinan tersimpan di SP. Kalau tidak ada, ambil dari Intent
        // atau biarkan null (tidak fatal — cuma cache update yang skip).
        codeBendel = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_CODE_BENDEL);
        if (codeBendel == null) {
            codeBendel = sp.getString(Config.BUNDLE_PEMBACA_METER_CODE_BENDEL, "");
        }

        if (nolangg == null) {
            Toast.makeText(this, "Data tidak valid, silakan buka ulang dari menu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        easyImage = new EasyImage.Builder(BendelPembacaKhususActivity.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("GD-Pembaca-Meter")
                .allowMultiple(true)
                .build();

        geocoderExecutor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        cDate = new Date();
        currentDateLocal = new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).format(cDate);
        currentTimeLocal = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(cDate);

        randomUUID = UUID.randomUUID();
        randomUUIDString = randomUUID.toString();
        currentTime = Calendar.getInstance().getTime();
        timestamp = String.valueOf(currentTime.getTime());

        // FASE 3 init
        settings = SettingsManager.getInstance(this);
        bacaanRepository = new BacaanRepository(this);
        refreshOfflineBanner();

        getListGabungan();

        if (kodeStatusMeter == null) {
            kodeStatusMeter = "1";
        }

        binding.tvLatlongAdress.setOnClickListener(view -> {
            showLoading(true);
            getLocationAdress();
        });

        binding.spnStatusMeter.setOnItemSelectedListener((view, position, id, item) -> {
            if (position >= 0 && position < arrayStatusMeter.size()) {
                String raw = arrayStatusMeter.get(position);
                if (raw != null && raw.length() >= 1) {
                    kodeStatusMeter = raw.substring(0, 1).trim();
                }
            }
        });

        binding.edtKini.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence c, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    Toast.makeText(BendelPembacaKhususActivity.this, "Isi meter Kini", Toast.LENGTH_SHORT).show();
                    binding.tvHitungKubik.setText(" 0m3");
                    binding.tvHitungKubik.setTextColor(getColor(R.color.black));
                } else {
                    int kini = safeParseInt(editable.toString(), -1);
                    if (kini < 0) {
                        binding.tvHitungKubik.setText(" 0m3");
                        binding.tvHitungKubik.setTextColor(getColor(R.color.black));
                        return;
                    }
                    if (lalu == null) {
                        binding.tvHitungKubik.setText("menunggu data pelanggan...");
                        binding.tvHitungKubik.setTextColor(getColor(R.color.black));
                        return;
                    }
                    int prev = safeParseInt(lalu, 0);
                    int hitung = kini - prev;
                    binding.tvHitungKubik.setText(hitung + "m3");
                    if (hitung < 0) {
                        binding.tvHitungKubik.setTextColor(getColor(R.color.red));
                    } else {
                        binding.tvHitungKubik.setTextColor(getColor(R.color.black));
                    }
                }
            }
        });

        binding.photoViewLeft.setOnClickListener(view -> {
            reqCodeFoto = "1";
            easyImage.openChooser(BendelPembacaKhususActivity.this);
        });

        binding.photoViewRight.setOnClickListener(view -> {
            reqCodeFoto = "2";
            easyImage.openChooser(BendelPembacaKhususActivity.this);
        });

        binding.btnSimpanData.setOnClickListener(view -> {
            if (compressedImageFileFotoMeter == null || binding.edtKini.getText().toString().isEmpty()) {
                Toast.makeText(this, Config.ERROR_DATA_REGISTER, Toast.LENGTH_SHORT).show();
            } else {
                MaterialDialog mDialog = new MaterialDialog.Builder(BendelPembacaKhususActivity.this)
                        .setTitle("Apakah data Anda sudah benar?")
                        .setCancelable(false)
                        .setNegativeButton("Belum", (dialogInterface, which) -> dialogInterface.dismiss())
                        .setPositiveButton("Sudah", (dialogInterface, which) -> {
                            dialogInterface.dismiss();
                            codeSimpandanLanjut = "0";

                            // Apply rotate kalau dipakai
                            if (reqCodeRotateFotoMeter != null && reqCodeRotateFotoMeter.equals("1")) {
                                float rotation = binding.photoViewLeft.getRotation();
                                Bitmap rotatedBitmap = getRotatedBitmap(binding.photoViewLeft, rotation);
                                if (rotatedBitmap != null) {
                                    saveImageToExternalStorage(rotatedBitmap, "rotated_image");
                                }
                            }

                            // ============== FASE 3: CABANG BERDASARKAN MODE OFFLINE ==============
                            if (settings.isOfflineModeEnabled()) {
                                Log.d(TAG, "Mode Offline ON → save offline");
                                saveBacaanOffline();
                            } else {
                                Log.d(TAG, "Mode Offline OFF → upload to server");
                                postFotoMeter();
                            }
                        })
                        .build();

                mDialog.show();
            }
        });

        binding.ivHeaderInfo.setOnClickListener(view -> {
            Intent intent = new Intent(BendelPembacaKhususActivity.this, ProfilePelangganDanTagihanActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, nolangg);
            startActivity(intent);
        });

        binding.btnRotate.setOnClickListener(view -> {
            binding.photoViewLeft.setRotation(binding.photoViewLeft.getRotation() + 90);
            reqCodeRotateFotoMeter = "1";
        });
    }

    /**
     * Tampilkan banner kuning di top kalau Mode Offline ON.
     * Pakai tv_system_update yang sudah ada di layout supaya tidak perlu modif XML.
     */
    private void refreshOfflineBanner() {
        if (binding == null) return;
        if (settings != null && settings.isOfflineModeEnabled()) {
            binding.tvSystemUpdate.setText(
                    "⚡ Mode Offline aktif — bacaan akan tersimpan di HP & dikirim saat sync");
            binding.tvSystemUpdate.setTextColor(getColor(R.color.orangeGreatDay));
        } else {
            binding.tvSystemUpdate.setText("Pastikan semua data benar - System v.0.9");
            binding.tvSystemUpdate.setTextColor(getColor(com.pdamkotasmg.goodday.R.color.dash_v2_bluedark));
        }
    }

    // ============== FASE 3 — OFFLINE SAVE ==============

    /**
     * Simpan bacaan ke local Room storage (Mode Offline ON).
     */
    private void saveBacaanOffline() {
        showLoading(true);

        PendingBacaanEntity entity = new PendingBacaanEntity();
        entity.jenis = PendingBacaanEntity.JENIS_KHUSUS_BENDEL;
        entity.nolangg = nolangg;
        entity.periode = periode;
        entity.cabang = cabang;
        entity.bendel = codeBendel;
        entity.kini = binding.edtKini.getText().toString().trim();
        entity.kodeStatusMeter = kodeStatusMeter;
        entity.keterangan = binding.edtKeterangan.getText().toString().trim();
        entity.manometer = binding.edtManometer.getText().toString().trim();
        entity.latitude = lati;
        entity.longitude = longi;
        entity.addressGps = address_gps;
        entity.actionCode = action_code;
        entity.npp = npp;
        entity.versionInfo = BuildConfig.VERSION_CODE + "-" + BuildConfig.VERSION_NAME + " - " + modelDevice;

        String bendelId = (codeBendel != null && !codeBendel.isEmpty())
                ? co.id.pdamkotasmg.local.db.entity.CachedBendelEntity.buildId(codeBendel, periode, cabang)
                : null;

        bacaanRepository.saveOffline(
                entity,
                compressedImageFileFotoMeter,
                compressedImageFileManometer,
                bendelId,
                new BacaanRepository.SaveCallback() {
                    @Override
                    public void onSaved(long pendingBacaanId) {
                        if (isActivityGone()) return;
                        showLoading(false);

                        Toast.makeText(BendelPembacaKhususActivity.this,
                                "Tersimpan offline (#" + pendingBacaanId + "). Data akan dikirim saat sync.",
                                Toast.LENGTH_LONG).show();

                        // Cleanup foto sumber kalau ada (Compressor result di /cache)
                        // Tapi karena BacaanRepository sudah COPY, foto asli boleh dihapus.
                        // Skip dulu untuk safety — Android akan auto-cleanup cache nanti.

                        // Langsung tutup Activity → balik ke BendelDataActivity yang akan
                        // refresh list (pelanggan sudah ter-mark sudah-dibaca di cache).
                        finish();
                    }

                    @Override
                    public void onError(String message, Throwable t) {
                        if (isActivityGone()) return;
                        showLoading(false);
                        Toast.makeText(BendelPembacaKhususActivity.this,
                                message != null ? message : "Gagal menyimpan offline",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // ============== ONLINE SAVE FLOW ==============

    private void postFotoMeter() {
        showLoading(true);
        Date currentTime = Calendar.getInstance().getTime();
        String timestamp = String.valueOf(currentTime.getTime());
        String year = new SimpleDateFormat("y", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        UUID randomUUID = UUID.randomUUID();
        String randomUUIDString = randomUUID.toString();

        if (compressedImageFileFotoMeter == null) {
            showLoading(false);
            Toast.makeText(this, "Foto meter belum diambil", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody path = RequestBody.create(MediaType.parse("text/plain"), "/pembaca-meter/foto-pembaca-meter/" + year + "/" + month);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), "pembaca-meter-" + randomUUIDString + "-" + nolangg + "-" + npp + "-" + year + month + "-" + timestamp);

        File imageFileMeter = new File(compressedImageFileFotoMeter.getPath());
        RequestBody requestFilePhotoKtp = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileMeter);
        MultipartBody.Part bodyFileMeter = MultipartBody.Part.createFormData("photo", imageFileMeter.getName(), requestFilePhotoKtp);

        ApiService apiService = ApiConfig.getApiServiceGWAPI(BendelPembacaKhususActivity.this);
        apiService.postUploadFoto(token, path, fileName, bodyFileMeter)
                .enqueue(new Callback<PostFotoUploadRootModel>() {
                    @Override
                    public void onResponse(Call<PostFotoUploadRootModel> call, Response<PostFotoUploadRootModel> response) {
                        if (isActivityGone()) return;

                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            filePathServer = response.body().getData().getFilepath();
                            fileUrlServer = response.body().getData().getFileurl();

                            if (reqCodeFoto != null && reqCodeFoto.contains("1")) {
                                postDataPembacaMeter();
                            } else if (reqCodeFoto != null && reqCodeFoto.contains("2")) {
                                postFotoManoMeter();
                            } else {
                                showLoading(false);
                                Toast.makeText(BendelPembacaKhususActivity.this, "Kode foto tidak dikenali", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            showLoading(false);
                            Toast.makeText(BendelPembacaKhususActivity.this, "Upload Foto gagal (HTTP " + response.code() + ")", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostFotoUploadRootModel> call, Throwable t) {
                        if (isActivityGone()) return;
                        showLoading(false);
                        Toast.makeText(BendelPembacaKhususActivity.this, "Upload Foto Gagal " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postFotoManoMeter() {
        Date currentTime = Calendar.getInstance().getTime();
        String timestamp = String.valueOf(currentTime.getTime());
        String year = new SimpleDateFormat("y", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        UUID randomUUID = UUID.randomUUID();
        String randomUUIDString = randomUUID.toString();

        if (compressedImageFileManometer == null) {
            showLoading(false);
            Toast.makeText(this, "Foto manometer belum diambil", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody path = RequestBody.create(MediaType.parse("text/plain"), "/pembaca-meter/foto-pembaca-meter/" + year + "/" + month);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), "pembaca-meter-manometer-" + randomUUIDString + "-" + nolangg + "-" + npp + "-" + year + month + "-" + timestamp);

        File imageFileMeter = new File(compressedImageFileManometer.getPath());
        RequestBody requestFilePhotoKtp = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileMeter);
        MultipartBody.Part bodyFileMeter = MultipartBody.Part.createFormData("photo", imageFileMeter.getName(), requestFilePhotoKtp);

        ApiService apiService = ApiConfig.getApiServiceGWAPI(BendelPembacaKhususActivity.this);
        apiService.postUploadFoto(token, path, fileName, bodyFileMeter)
                .enqueue(new Callback<PostFotoUploadRootModel>() {
                    @Override
                    public void onResponse(Call<PostFotoUploadRootModel> call, Response<PostFotoUploadRootModel> response) {
                        if (isActivityGone()) return;

                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            getFilePathServerFotoManometer = response.body().getData().getFilepath();
                            postDataPembacaMeter();
                        } else {
                            showLoading(false);
                            Toast.makeText(BendelPembacaKhususActivity.this, "Upload Foto Manometer gagal (HTTP " + response.code() + ")", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostFotoUploadRootModel> call, Throwable t) {
                        if (isActivityGone()) return;
                        showLoading(false);
                        Toast.makeText(BendelPembacaKhususActivity.this, "Upload Foto Manometer Gagal " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void postDataPembacaMeter() {
        ApiService apiService = ApiConfig.getApiServiceGWAPI(BendelPembacaKhususActivity.this);
        apiService.postUpdatePembacaMeter(token, nolangg, binding.edtKini.getText().toString().trim(), filePathServer, "khusus-bendel",
                        kodeStatusMeter, binding.edtKeterangan.getText().toString().trim(),
                        action_code, String.valueOf(lati), String.valueOf(longi), address_gps, binding.edtManometer.getText().toString().trim(), getFilePathServerFotoManometer,
                        BuildConfig.VERSION_CODE + "-" + BuildConfig.VERSION_NAME + " - " + modelDevice)
                .enqueue(new Callback<UpdatePembacaMeterRootModel>() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onResponse(Call<UpdatePembacaMeterRootModel> call, Response<UpdatePembacaMeterRootModel> response) {
                        if (isActivityGone()) return;

                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            try {
                                Config.deleteFolders(rootPathImage, "deletedFolders");
                                Config.deleteFolders(pathImageWatermark, "ImgWatermarkDelete");
                            } catch (IOException e) {
                                Log.e(TAG, "deleteFolders gagal (non-fatal)", e);
                            }

                            // FASE 6 FIX: Mark cache as read DENGAN CALLBACK supaya
                            // guarantee selesai sebelum user balik ke BendelDataActivity.
                            // Tanpa ini, ada race condition di mana onRestart trigger
                            // sebelum cache ter-update.
                            if (codeBendel != null && !codeBendel.isEmpty()) {
                                bacaanRepository.markPelangganAsRead(codeBendel, nolangg, success -> {
                                    Log.d(TAG, "Cache markAsRead done, success=" + success);
                                });
                            } else {
                                // codeBendel kosong → fallback by nolangg
                                bacaanRepository.markPelangganAsRead("", nolangg, success -> {
                                    Log.d(TAG, "Cache markAsRead (fallback) done, success=" + success);
                                });
                            }

                            Toast.makeText(BendelPembacaKhususActivity.this,
                                    "Success mengirim data : " + response.body().getData().getUpdateData(),
                                    Toast.LENGTH_LONG).show();
                            bottomSheetSuksesSimpanData();
                        } else {
                            Toast.makeText(BendelPembacaKhususActivity.this, "postDataPembacaMeter: Gagal/Error, Coba lagi", Toast.LENGTH_SHORT).show();
                            showLoading(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdatePembacaMeterRootModel> call, Throwable t) {
                        if (isActivityGone()) return;
                        showLoading(false);
                        Toast.makeText(BendelPembacaKhususActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getListGabungan() {
        showLoading(true);
        ApiService apiService = ApiConfig.getApiServiceGWAPI(BendelPembacaKhususActivity.this);
        apiService.getListGabungan(token)
                .enqueue(new Callback<ListGabunganRootModel>() {
                    @Override
                    public void onResponse(Call<ListGabunganRootModel> call, Response<ListGabunganRootModel> response) {
                        if (isActivityGone()) return;

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null
                                && response.body().getData().getStatusMeter() != null) {
                            getCheckPelanggan(nolangg);
                            statusMeterItems = response.body().getData().getStatusMeter();
                            arrayStatusMeter.clear();
                            for (int i = 0; i < statusMeterItems.size(); i++) {
                                String kode = statusMeterItems.get(i).getKode();
                                String nameStatus = statusMeterItems.get(i).getStatus();
                                arrayStatusMeter.add(kode + " " + nameStatus);
                            }
                            binding.spnStatusMeter.setItems(arrayStatusMeter);
                        } else {
                            showLoading(false);
                            Toast.makeText(BendelPembacaKhususActivity.this, "Gagal memuat status meter (HTTP " + response.code() + ")", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListGabunganRootModel> call, Throwable t) {
                        if (isActivityGone()) return;
                        showLoading(false);
                        Toast.makeText(BendelPembacaKhususActivity.this, "Status Meter Null | " + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getCheckPelanggan(String nolangg) {
        ApiService apiService = ApiConfig.getApiServiceGWAPI(BendelPembacaKhususActivity.this);
        apiService.getCheckPelangganDetail(token, nolangg)
                .enqueue(new Callback<CheckPelangganRootModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<CheckPelangganRootModel> call, Response<CheckPelangganRootModel> response) {
                        if (isActivityGone()) return;

                        if (!response.isSuccessful() || response.body() == null) {
                            showLoading(false);
                            Toast.makeText(BendelPembacaKhususActivity.this, "Data pelanggan gagal dimuat (HTTP " + response.code() + ")", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CheckPelangganRootModel body = response.body();
                        if (body.getData() == null || body.getData().isEmpty()) {
                            showLoading(false);
                            Toast.makeText(BendelPembacaKhususActivity.this, "Data pelanggan kosong", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            getLocationAdress();
                            showLoading(false);

                            if (body.getData().get(0).getRlDtBacaPeriodeSkrg() == null
                                    || body.getData().get(0).getRlDtBacaPeriodeSkrg().isEmpty()
                                    || body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca() == null) {
                                Toast.makeText(BendelPembacaKhususActivity.this, "Struktur data pelanggan tidak valid", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String kode = body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getKode();
                            if (kode != null && kode.contains("0")) {
                                if (body.getData().get(0).getRlTrbaca() != null
                                        && !body.getData().get(0).getRlTrbaca().isEmpty()) {
                                    lalu = body.getData().get(0).getRlTrbaca().get(0).getKini();
                                }

                                binding.tvNolangg.setText(body.getData().get(0).getNolangg());
                                binding.tvPeriode.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getPeriode());
                                binding.tvDism.setText(body.getData().get(0).getDism());
                                binding.tvNama.setText(body.getData().get(0).getNama());
                                binding.tvAlamat.setText(body.getData().get(0).getAlamat());

                                if (body.getData().get(0).getRlTarif() != null) {
                                    binding.tvTarif.setText(body.getData().get(0).getRlTarif().getKode() + " - " + body.getData().get(0).getRlTarif().getNmTarif());
                                }

                                if (body.getData().get(0).getRlTrbaca() != null && !body.getData().get(0).getRlTrbaca().isEmpty()) {
                                    binding.tvKeteranganLain.setText("Keterangan " + body.getData().get(0).getRlTrbaca().get(0).getPeriode());
                                    binding.tvKtLain.setText(body.getData().get(0).getRlTrbaca().get(0).getKt());
                                    binding.tvLalu.setText(lalu + " - " + body.getData().get(0).getRlTrbaca().get(0).getM3() + "m3");
                                }

                                binding.tvMerekMeter.setText(body.getData().get(0).getMerek() + " / " + body.getData().get(0).getNomormtr());
                                binding.tvStatusData.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getNmStatus());
                            } else {
                                binding.svContainer.setVisibility(View.GONE);
                                binding.btnSimpanData.setVisibility(View.GONE);
                                binding.btnSimpanLanjut.setVisibility(View.GONE);
                                binding.tvSystemUpdate.setText(body.getData().get(0).getNolangg() +
                                        " Pelanggan sudah dalam status " +
                                        body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getNmStatus());
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing data pelanggan", e);
                            Toast.makeText(BendelPembacaKhususActivity.this, "Gagal memproses data pelanggan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckPelangganRootModel> call, Throwable t) {
                        if (isActivityGone()) return;
                        Toast.makeText(BendelPembacaKhususActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        showLoading(false);
                    }
                });
    }

    private void getLocationAdress() {
        mFusedLocation = LocationServices.getFusedLocationProviderClient(BendelPembacaKhususActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showLoading(false);
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(this, location -> {
            if (location == null) {
                showLoading(false);
                Toast.makeText(this, "Lokasi belum tersedia. Pastikan GPS aktif.", Toast.LENGTH_SHORT).show();
                return;
            }
            lati = location.getLatitude();
            longi = location.getLongitude();

            if (lati == null || longi == null || lati == 0.0 || longi == 0.0) {
                Toast.makeText(this, "Alamat tidak ditemukan", Toast.LENGTH_SHORT).show();
                showLoading(false);
                return;
            }

            resolveAddressAsync(lati, longi);
        }).addOnFailureListener(e -> {
            if (isActivityGone()) return;
            showLoading(false);
            Toast.makeText(this, "Gagal mengambil lokasi: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void resolveAddressAsync(final double latitude, final double longitude) {
        if (geocoderExecutor == null || geocoderExecutor.isShutdown()) return;

        geocoderExecutor.execute(() -> {
            List<Address> addressList = null;
            try {
                Geocoder geocoder = new Geocoder(BendelPembacaKhususActivity.this, Locale.getDefault());
                addressList = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (Exception ignored) {}

            final List<Address> finalList = addressList;
            mainHandler.post(() -> {
                if (isActivityGone()) return;

                if (finalList == null || finalList.isEmpty()) {
                    showLoading(false);
                    Toast.makeText(BendelPembacaKhususActivity.this, "Gagal resolve alamat", Toast.LENGTH_SHORT).show();
                    return;
                }

                Address a = finalList.get(0);
                address_gps = a.getAddressLine(0);
                if (address_gps == null) address_gps = "alamat";
                city = a.getLocality();   if (city == null) city = "kota";
                state = a.getAdminArea(); if (state == null) state = ".";
                country = a.getCountryName(); if (country == null) country = "negara";
                postalCode = a.getPostalCode(); if (postalCode == null) postalCode = "postal";
                knownName = a.getFeatureName(); if (knownName == null) knownName = "name";

                binding.tvLatlongAdress.setText(address_gps + " | lat: " + latitude + " longi: " + longitude + "\nTekan disini untuk refresh Lokasi");

                if (codeSimpandanLanjut == null) {
                    showLoading(false);
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, BendelPembacaKhususActivity.this, new EasyImage.Callbacks() {
            @Override
            public void onMediaFilesPicked(@NonNull MediaFile[] mediaFiles, @NonNull MediaSource mediaSource) {
                if (mediaFiles == null || mediaFiles.length == 0 || mediaFiles[0] == null || mediaFiles[0].getFile() == null) return;

                UUID randomUUID = UUID.randomUUID();
                String randomUUIDString = randomUUID.toString();
                Date currentTime = Calendar.getInstance().getTime();
                String timestamp = String.valueOf(currentTime.getTime());

                if (reqCodeFoto != null && reqCodeFoto.contains("1")) {
                    try {
                        compressedImageFileFotoMeter = new Compressor(BendelPembacaKhususActivity.this)
                                .setMaxHeight(400).setMaxWidth(400).setQuality(50)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                                .compressToFile(mediaFiles[0].getFile(), "comp1_PM_" + nolangg + "_" + timestamp + "_" + randomUUIDString + "_" + mediaFiles[0].getFile().getName());
                        showImageWatermark(compressedImageFileFotoMeter.getPath(), binding.photoViewLeft);
                    } catch (IOException e) {
                        Log.e(TAG, "Compressor foto meter gagal", e);
                        Toast.makeText(BendelPembacaKhususActivity.this, "Gagal mengompres foto meter", Toast.LENGTH_SHORT).show();
                    }
                } else if (reqCodeFoto != null && reqCodeFoto.contains("2")) {
                    try {
                        compressedImageFileManometer = new Compressor(BendelPembacaKhususActivity.this)
                                .setMaxHeight(400).setMaxWidth(400).setQuality(50)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                                .compressToFile(mediaFiles[0].getFile(), "comp1_PM_MM_" + nolangg + "_" + timestamp + "_" + randomUUIDString + "_" + mediaFiles[0].getFile().getName());
                        Glide.with(BendelPembacaKhususActivity.this).load(compressedImageFileManometer.getPath())
                                .error(com.pdamkotasmg.goodday.R.drawable.image_not_available).into(binding.photoViewRight);
                    } catch (IOException e) {
                        Log.e(TAG, "Compressor foto manometer gagal", e);
                        Toast.makeText(BendelPembacaKhususActivity.this, "Gagal mengompres foto manometer", Toast.LENGTH_SHORT).show();
                    }
                }

                rootPathImage = mediaFiles[0].getFile().getParent();
            }

            @Override public void onImagePickerError(@NonNull Throwable throwable, @NonNull MediaSource mediaSource) {
                Log.e(TAG, "EasyImage error", throwable);
            }

            @Override public void onCanceled(@NonNull MediaSource mediaSource) {}
        });
    }

    private void showImageWatermark(String file, PhotoView photoView) {
        Glide.with(BendelPembacaKhususActivity.this)
                .asBitmap().load(file)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        if (isActivityGone()) return;

                        Canvas canvas = new Canvas(resource);
                        canvas.drawBitmap(resource, 0, 0, null);

                        String[] lines = {
                                currentDateLocal + " - " + currentTimeLocal,
                                "Lat: " + lati + " Longi: " + longi,
                                nolangg + " (" + npp + ")"
                        };

                        int yPosition = 50;
                        Paint paint = new Paint();
                        paint.setColor(Color.WHITE);
                        paint.setTextSize(20);
                        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                        paint.setAntiAlias(true);

                        int textHeight = (int) (paint.descent() - paint.ascent());
                        int lineSpacing = 10;
                        for (String line : lines) {
                            canvas.drawText(line, 20, yPosition, paint);
                            yPosition += textHeight + lineSpacing;
                        }

                        photoView.setImageBitmap(resource);
                        saveImageToExternalStorage(resource, "wtrmk_PM_");
                    }

                    @Override public void onLoadCleared(Drawable placeholder) {}
                });
    }

    private void saveImageToExternalStorage(Bitmap bitmap, String nameFile) {
        if (bitmap == null) return;
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/PDAM/PEMBACA-METER/FOTO");
        directory.mkdirs();

        UUID randomUUID = UUID.randomUUID();
        String randomUUIDString = randomUUID.toString();
        String fileName = nameFile + randomUUIDString + ".jpg";
        File destination = new File(directory, fileName);

        try {
            OutputStream outputStream = new FileOutputStream(destination);
            bitmap.compress(Bitmap.CompressFormat.WEBP, 50, outputStream);
            outputStream.flush();
            outputStream.close();

            pathImageWatermark = destination.getParent();
            compressedImageFileFotoMeter = new File(destination.getPath());

            compressedImageFileFotoMeter = new Compressor(BendelPembacaKhususActivity.this)
                    .setMaxHeight(400).setMaxWidth(400).setQuality(50)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(rootPathImage)
                    .compressToFile(destination, "comp2_PM_" + nameFile + "_" + randomUUIDString + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image " + nameFile, Toast.LENGTH_SHORT).show();
        }
    }

    private void bottomSheetSuksesSimpanData() {
        final BottomSheetDialog bottomSheetDialogResultDataBacaan = new BottomSheetDialog(BendelPembacaKhususActivity.this);
        bottomSheetDialogResultDataBacaan.setContentView(R.layout.bottom_sheet_result_data_bacaan);
        bottomSheetDialogResultDataBacaan.setCancelable(false);

        TextView tvTutupDialog = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_tutup_dialog);
        Button btnClose = bottomSheetDialogResultDataBacaan.findViewById(R.id.btn_close);
        TextView tvResultMsgServer = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_result_msg_server);
        TextView tvNolangg = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_nolangg);
        TextView tvDism = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_dism);
        TextView tvNama = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_nama);
        TextView tvPeriode = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_periode);
        TextView tvAlamat = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_alamat);
        TextView tvStatusMeter = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_status_meter);
        TextView tvTarif = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_tarif);
        TextView tvKeteranganLain = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_keterangan_lain);
        TextView tvKtLain = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_kt_lain);
        TextView tvMerekMeter = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_merek_meter);
        TextView tvKini = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_kini);
        TextView tvManometer = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_manometer);
        TextView tvPetugas = bottomSheetDialogResultDataBacaan.findViewById(R.id.tv_petugas);
        PhotoView photoView = bottomSheetDialogResultDataBacaan.findViewById(R.id.photoView);
        ImageView ivRefreshData = bottomSheetDialogResultDataBacaan.findViewById(R.id.iv_refresh);
        LinearLayout divEdit = bottomSheetDialogResultDataBacaan.findViewById(R.id.div_edit);
        LinearLayout divLihatRiwayat = bottomSheetDialogResultDataBacaan.findViewById(R.id.div_lihat_riwayat);

        btnClose.setOnClickListener(view -> {
            bottomSheetDialogResultDataBacaan.dismiss();
            BendelPembacaKhususActivity.this.finish();
            showLoading(false);
        });

        divEdit.setOnClickListener(view -> {
            BendelPembacaKhususActivity.this.finish();
            Intent intent = new Intent(BendelPembacaKhususActivity.this, DetailRiwayatPembacaMeterActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, nolangg);
            startActivity(intent);
        });

        divLihatRiwayat.setOnClickListener(view -> {
            BendelPembacaKhususActivity.this.finish();
            startActivity(new Intent(getApplicationContext(), RiwayatPembacaMeterActivity.class));
        });

        ivRefreshData.setOnClickListener(view -> {
            showLoading(true);
            getDataHasilBacaan(tvTutupDialog, tvResultMsgServer, tvNolangg, tvDism, tvNama, tvPeriode, tvAlamat, tvStatusMeter, tvTarif, tvKtLain, tvMerekMeter, tvKini, tvManometer, tvPetugas, photoView);
        });

        getDataHasilBacaan(tvTutupDialog, tvResultMsgServer, tvNolangg, tvDism, tvNama, tvPeriode, tvAlamat, tvStatusMeter, tvTarif, tvKtLain, tvMerekMeter, tvKini, tvManometer, tvPetugas, photoView);
        bottomSheetDialogResultDataBacaan.show();
    }

    private void getDataHasilBacaan(TextView tvTutupDialog, TextView tvResultMsgServer, TextView tvNolangg,
                                    TextView tvDism, TextView tvNama, TextView tvPeriode, TextView tvAlamat,
                                    TextView tvStatusMeter, TextView tvTarif, TextView tvKtLain, TextView tvMerekMeter,
                                    TextView tvKini, TextView tvManometer, TextView tvPetugas, PhotoView photoView) {
        ApiService apiService = ApiConfig.getApiServiceGWAPI(BendelPembacaKhususActivity.this);
        apiService.getPelanggan(token, nolangg)
                .enqueue(new Callback<CheckPelangganRootModel>() {
                    @Override
                    public void onResponse(Call<CheckPelangganRootModel> call, Response<CheckPelangganRootModel> response) {
                        if (isActivityGone()) return;
                        showLoading(false);

                        if (!response.isSuccessful() || response.body() == null) {
                            Toast.makeText(BendelPembacaKhususActivity.this, "bottomSheetSuksesSimpanData: Error (HTTP " + response.code() + ")", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            CheckPelangganRootModel body = response.body();
                            if (body.getData() == null || body.getData().isEmpty()
                                    || body.getData().get(0).getRlDtBacaPeriodeSkrg() == null
                                    || body.getData().get(0).getRlDtBacaPeriodeSkrg().isEmpty()) {
                                Toast.makeText(BendelPembacaKhususActivity.this, "Data bacaan tidak tersedia", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            tvTutupDialog.setText("Hasil Bacaan " + body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getPeriode());
                            tvResultMsgServer.setText("Berhasil Disimpan " + body.getMessage());
                            tvNolangg.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getNolangg());
                            tvDism.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getDism());
                            tvNama.setText(body.getData().get(0).getNama());
                            tvPeriode.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getPeriode());
                            tvAlamat.setText(body.getData().get(0).getAlamat());

                            if (body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlStMeter() != null) {
                                tvStatusMeter.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlStMeter().getStatus());
                            }
                            if (body.getData().get(0).getRlTarif() != null) {
                                tvTarif.setText(body.getData().get(0).getRlTarif().getNmTarif());
                            }

                            String keterangan = body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getKt();
                            if (keterangan == null) keterangan = "-";
                            tvKtLain.setText(keterangan);
                            tvMerekMeter.setText(body.getData().get(0).getMerek() + " - " + body.getData().get(0).getNomormtr());
                            tvKini.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getKini());
                            tvManometer.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getManometer());
                            tvPetugas.setText(body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getPcEntry() + " - " + body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getIpEntry());

                            Glide.with(BendelPembacaKhususActivity.this)
                                    .load(Config.BASE_URL_IMAGE_HANDLER + body.getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getFile())
                                    .error(com.pdamkotasmg.goodday.R.drawable.image_not_available)
                                    .override(512, 512).into(photoView);
                        } catch (Exception e) {
                            Log.e(TAG, "getDataHasilBacaan parsing error", e);
                            Toast.makeText(BendelPembacaKhususActivity.this, "Gagal memproses hasil bacaan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckPelangganRootModel> call, Throwable t) {
                        if (isActivityGone()) return;
                        showLoading(false);
                        tvResultMsgServer.setText("Failure: Gagal mengambil data");
                        Toast.makeText(BendelPembacaKhususActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Bitmap getRotatedBitmap(ImageView imageView, float rotationAngle) {
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) return null;
        if (!(drawable instanceof BitmapDrawable)) return null;
        Bitmap originalBitmap = ((BitmapDrawable) drawable).getBitmap();
        if (originalBitmap == null) return null;

        Matrix matrix = new Matrix();
        matrix.postRotate(rotationAngle);

        return Bitmap.createBitmap(originalBitmap, 0, 0,
                originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);
    }

    private void showLoading(boolean show) {
        if (binding == null) return;
        binding.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private boolean isActivityGone() {
        return isFinishing() || isDestroyed() || binding == null;
    }

    private int safeParseInt(String s, int fallback) {
        if (s == null) return fallback;
        try { return Integer.parseInt(s.trim()); }
        catch (NumberFormatException e) { return fallback; }
    }

    @Override
    protected void onDestroy() {
        if (geocoderExecutor != null && !geocoderExecutor.isShutdown()) {
            geocoderExecutor.shutdownNow();
        }
        super.onDestroy();
    }
}