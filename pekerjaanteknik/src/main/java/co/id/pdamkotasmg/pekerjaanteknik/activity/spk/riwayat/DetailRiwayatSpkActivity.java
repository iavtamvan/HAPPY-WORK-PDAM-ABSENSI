package co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.verifikasiSPK.statusVerifikasi.StatusVerifikasiRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import co.id.pdamkotasmg.pekerjaanteknik.utils.ConfigAds;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRiwayatSpkActivity extends AppCompatActivity {
    private final String TAG = "debug";
    private String token;
    private FusedLocationProviderClient mFusedLocation;
    private GoogleMap mMap;
    private Double lati, longi;

    private String no_spk;
    private String tgl_spk;
    private String nm_lapor;
    private String alamat_lapor;
    private String asal_lapor;
    private String nolangg_lapor;
    private String nama;
    private String alamat;
    private String alamatGps;
    private String uraian;
    private String kasub;
    private String pengawas;
    private String pc_entry;
    private String tgl_pelaksana;
    private String jam1;
    private String jam2;
    private String jenis_pipa;
    private String jml_tenaga;
    private String no_spk_sbl;
    private String status;
    private String namePerbaikan;
    private String ketZona;
    private String tka;
    private String foto1;
    private String foto2;
    private String foto3;
    private String foto4;
    private String status_verifikasi;
    private String nocc;
    private String nppVerifikator;
    private String bagianPelapor;
    private String lembur;

    private TextView tvNoSpk;
    private TextView tvTglSpk;
    private TextView tvNamaPelapor;
    private TextView tvAlamatPelapor;
    private TextView tvAsalPelapor;
    private TextView tvNolangg;
    private TextView tvNamaPelanggan;
    private TextView tvAlamatPelanggan;
    private TextView tvUraianPekerjaan;
    private TextView tvNppKasub;
    private TextView tvNppPengawas;
    private TextView tvPcEntry;
    private TextView tvTglSpkPelaksana;
    private TextView tvJamMulaiSelesai;
    private TextView tvJenisPipa;
    private TextView tvUraianPekerjaanPelaksana;
    private TextView tvJmlTenagaJmlTenagaKet;
    private TextView tvNoSpkSebelumnya;
    private TextView tvStatus;
    private CardView cvFoto1;
    private ImageView ivFoto1;
    private CardView cvFoto2;
    private ImageView ivFoto2;
    private CardView cvFoto3;
    private ImageView ivFoto3;
    private CardView cvFoto4;
    private ImageView ivFoto4;
    private TextView tvKetZona;
    private TextView tvTka;
    private TextView tvBagianPelapor;
    private TextView tvStatusVerifikasi;
    private Button btnVerifikasi;
    private AdView adView;
    private Button btnHapus;
    private TextView tvNoCc;
    private TextView tvNppVerifikator;
    private TextView tvLembur;
    private TextView tvAlamatGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_spk);
        
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        bagianPelapor = sharedPreferences.getString(Config.SHARED_SATKER, "");
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        ConfigAds.banner(DetailRiwayatSpkActivity.this, adView);

        no_spk = getIntent().getStringExtra(Config.BUNDLE_NO_SPK);
        tgl_spk = getIntent().getStringExtra(Config.BUNDLE_TGL_SPK);
        nm_lapor = getIntent().getStringExtra(Config.BUNDLE_NM_LAPOR);
        alamat_lapor = getIntent().getStringExtra(Config.BUNDLE_ALAMAT_LAPOR);
        asal_lapor = getIntent().getStringExtra(Config.BUNDLE_ASAL_LAPOR);
        nolangg_lapor = getIntent().getStringExtra(Config.BUNDLE_NOLANGG_LAPOR);
        nama = getIntent().getStringExtra(Config.BUNDLE_NAMA);
        alamat = getIntent().getStringExtra(Config.BUNDLE_ALAMAT);
        alamatGps = getIntent().getStringExtra(Config.BUNDLE_LOKASI);
        uraian = getIntent().getStringExtra(Config.BUNDLE_URAIAN);
        kasub = getIntent().getStringExtra(Config.BUNDLE_KASUB);
        pengawas = getIntent().getStringExtra(Config.BUNDLE_PENGAWAS);
        pc_entry = getIntent().getStringExtra(Config.BUNDLE_PC_ENTRY);
        tgl_pelaksana = getIntent().getStringExtra(Config.BUNDLE_TGL_PELAKSANA);
        jam1 = getIntent().getStringExtra(Config.BUNDLE_JAM1);
        jam2 = getIntent().getStringExtra(Config.BUNDLE_JAM2);
        jenis_pipa = getIntent().getStringExtra(Config.BUNDLE_JENIS_PIPA);
        jml_tenaga = getIntent().getStringExtra(Config.BUNDLE_JML_TENAGA);
        no_spk_sbl = getIntent().getStringExtra(Config.BUNDLE_NO_SPK_SBL);
        status = getIntent().getStringExtra(Config.BUNDLE_STATUS);
        namePerbaikan = getIntent().getStringExtra(Config.BUNDLE_KD_PERBAIKAN);
        ketZona = getIntent().getStringExtra(Config.BUNDLE_KET_ZONA);
        tka = getIntent().getStringExtra(Config.BUNDLE_TKA);
        foto1 = getIntent().getStringExtra(Config.BUNDLE_FOTO1);
        foto2 = getIntent().getStringExtra(Config.BUNDLE_FOTO2);
        foto3 = getIntent().getStringExtra(Config.BUNDLE_FOTO3);
        foto4 = getIntent().getStringExtra(Config.BUNDLE_FOTO4);
        status_verifikasi = getIntent().getStringExtra(Config.BUNDLE_STATUS_VERIFIKASI);
        nocc = getIntent().getStringExtra(Config.BUNDLE_NO_CC);
        nppVerifikator = getIntent().getStringExtra(Config.BUNDLE_VERIFIKATOR);
        lembur = getIntent().getStringExtra(Config.BUNDLE_LEMBUR);
        lati = Double.parseDouble(getIntent().getStringExtra(Config.BUNDLE_LATITUDE));
        longi = Double.parseDouble(getIntent().getStringExtra(Config.BUNDLE_LONGITUDE));

        if (lati == null || longi == null || lati.isNaN() || longi.isNaN()) {
            lati = 0.0;
            longi = 0.0;
        }

        getMaps();

        Log.d("debug", "onCreate: " + foto1 + foto2);

        if (status.equalsIgnoreCase("0")) {
            status = "Belum Selesai";
        } else if (status.equalsIgnoreCase("1")) {
            status = "Selesai";
        } else if (status.equalsIgnoreCase("2")) {
            status = "Usulan Rehap";
        }

        String list = sharedPreferences.getString(Config.SHARED_PERMISION_APPS, "");
        Log.d(TAG, "Arrayperms: " + list);

        if (list.contains("pekerjaan-teknik.verifikasi-spk")) {
            btnVerifikasi.setVisibility(View.VISIBLE);
        } else {
            btnVerifikasi.setVisibility(View.GONE);
        }

        if (list.contains("pekerjaan-teknik.delete-spk")) {
            if (status_verifikasi == null) {
                tvStatusVerifikasi.setText("Tidak ada status Verifikasi");
                btnVerifikasi.setVisibility(View.GONE);
            } else if (status_verifikasi.equalsIgnoreCase("0")) {
                tvStatusVerifikasi.setText("Belum Terverifikasi");
                btnHapus.setVisibility(View.VISIBLE);
            } else {
                tvStatusVerifikasi.setText("Terverifikasi");
                btnVerifikasi.setVisibility(View.GONE);
                btnHapus.setVisibility(View.GONE);
            }
        } else {
            btnHapus.setVisibility(View.GONE);
        }

        if (status.equalsIgnoreCase("Selesai")) {
//            btnHapus.setVisibility(View.GONE);
//            btnVerifikasi.setVisibility(View.GONE);
            // TODO no action status selesai
        }

        tvNoSpk.setText(no_spk);
        tvTglSpk.setText(tgl_spk);
        tvNamaPelapor.setText(nm_lapor);
        tvAsalPelapor.setText(asal_lapor);
        tvNolangg.setText(nolangg_lapor);
        tvNamaPelanggan.setText(nama);
        tvAlamatPelanggan.setText(alamat);
        tvAlamatGps.setText(alamatGps);
        tvUraianPekerjaan.setText(uraian);
        tvUraianPekerjaanPelaksana.setText(namePerbaikan);
        tvNppKasub.setText(kasub);
        tvNppPengawas.setText(pengawas);
        tvPcEntry.setText(pc_entry);
        tvTglSpkPelaksana.setText(tgl_pelaksana);
        tvJamMulaiSelesai.setText(jam1 + " - " + jam2);
        tvJenisPipa.setText(jenis_pipa);
        tvJmlTenagaJmlTenagaKet.setText(jml_tenaga);
        tvNoSpkSebelumnya.setText(no_spk_sbl);
        tvStatus.setText(status);
        tvKetZona.setText(ketZona);
        tvTka.setText(tka);
        tvBagianPelapor.setText(bagianPelapor);
        tvAlamatPelapor.setText(alamat_lapor);
        tvNoCc.setText(nocc);
        tvNppVerifikator.setText(nppVerifikator);
        tvLembur.setText(lembur);

        Glide.with(DetailRiwayatSpkActivity.this).load(foto1).override(512, 512).error(com.pdamkotasmg.goodday.R.drawable.image_not_available).into(ivFoto1);
        Glide.with(DetailRiwayatSpkActivity.this).load(foto2).override(512, 512).error(com.pdamkotasmg.goodday.R.drawable.image_not_available).into(ivFoto2);
        Glide.with(DetailRiwayatSpkActivity.this).load(foto3).override(512, 512).error(com.pdamkotasmg.goodday.R.drawable.image_not_available).into(ivFoto3);
        Glide.with(DetailRiwayatSpkActivity.this).load(foto4).override(512, 512).error(com.pdamkotasmg.goodday.R.drawable.image_not_available).into(ivFoto4);

        btnVerifikasi.setOnClickListener(v -> {
            MaterialDialog mDialog = new MaterialDialog.Builder(DetailRiwayatSpkActivity.this)
                    .setTitle("Peringatan")
                    .setMessage("Anda yakin verifikasi SPK No \n" + no_spk + " ?")
                    .setAnimation("question.json")
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                        ProgressDialog progressDialog = new ProgressDialog(DetailRiwayatSpkActivity.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Mohon tunggu");
                        progressDialog.show();
                        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                        apiService.updateStatusVerifikasi(token, no_spk)
                                .enqueue(new Callback<StatusVerifikasiRootModel>() {
                                    @Override
                                    public void onResponse(Call<StatusVerifikasiRootModel> call, Response<StatusVerifikasiRootModel> response) {
                                        if (response.isSuccessful()) {
                                            progressDialog.cancel();
                                            Config.popUpSuccesIntent(DetailRiwayatSpkActivity.this, "Data berhasil diverifikasi", RiwayatVerifikasiActivity.class);
                                        } else {
                                            progressDialog.cancel();
                                            Toast.makeText(DetailRiwayatSpkActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<StatusVerifikasiRootModel> call, Throwable t) {
                                        progressDialog.cancel();
                                        Toast.makeText(DetailRiwayatSpkActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    })
                    .setNegativeButton("Batal", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                    })
                    .build();
            // Show Dialog
            mDialog.show();

        });

        btnHapus.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(DetailRiwayatSpkActivity.this);
            progressDialog.setMessage("Mohon tunggu ...");
            progressDialog.setCancelable(false);
//            progressDialog.show();
            MaterialDialog mDialog = new MaterialDialog.Builder(DetailRiwayatSpkActivity.this)
                    .setTitle("Peringatan")
                    .setMessage("Anda yakin ingin menghapus dengan \n" + no_spk + " ?")
                    .setAnimation("question.json")
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialogInterface, which) -> {
                        progressDialog.show();
                        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
                        apiService.deleteSPK(token, no_spk)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            progressDialog.cancel();
                                            String status = response.message();
                                            Log.d(TAG, "onResponseMessage: " + status);
                                            if (status.equalsIgnoreCase("ok")) {
                                                progressDialog.cancel();
                                                dialogInterface.dismiss();
                                                Toast.makeText(DetailRiwayatSpkActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(DetailRiwayatSpkActivity.this, RiwayatSpkActivity.class);
                                                intent.putExtra(Config.BUNDLE_KD_RIWAYAT, "1");
                                                startActivity(intent);
                                                finish();
//                                                Config.popUpSuccesIntent(DetailRiwayatSpkActivity.this, "Data berhasil dihapus", RiwayatSpkActivity.class);

                                            } else {
                                                progressDialog.cancel();
                                                Toast.makeText(DetailRiwayatSpkActivity.this, "Data Gagal dihapus", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            progressDialog.cancel();
                                            Toast.makeText(DetailRiwayatSpkActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        progressDialog.cancel();
                                        Toast.makeText(DetailRiwayatSpkActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    })
                    .setNegativeButton("Batal", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                    })
                    .build();
            // Show Dialog
            mDialog.show();
        });

    }

    private void getMaps() {
        Log.d(TAG, "lat: " + lati);
        Log.d(TAG, "long: " + longi);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);

        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            LatLng sydney = new LatLng(lati, longi);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(sydney)      // Sets the center of the map to Mountain View
                    .zoom(17)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        });
//            }
//        });

    }

    private void initView() {
        tvNoSpk = findViewById(R.id.tv_no_spk);
        tvTglSpk = findViewById(R.id.tv_tgl_spk);
        tvNamaPelapor = findViewById(R.id.tv_nama_pelapor);
        tvAlamatPelapor = findViewById(R.id.tv_alamat_pelapor);
        tvAsalPelapor = findViewById(R.id.tv_asal_pelapor);
        tvNolangg = findViewById(R.id.tv_nolangg);
        tvNamaPelanggan = findViewById(R.id.tv_nama_pelanggan);
        tvAlamatPelanggan = findViewById(R.id.tv_alamat_pelanggan);
        tvUraianPekerjaan = findViewById(R.id.tv_uraian_pekerjaan);
        tvNppKasub = findViewById(R.id.tv_npp_kasub);
        tvNppPengawas = findViewById(R.id.tv_npp_pengawas);
        tvPcEntry = findViewById(R.id.tv_pc_entry);
        tvTglSpkPelaksana = findViewById(R.id.tv_tgl_spk_pelaksana);
        tvJamMulaiSelesai = findViewById(R.id.tv_jam_mulai_selesai);
        tvJenisPipa = findViewById(R.id.tv_jenis_pipa);
        tvUraianPekerjaanPelaksana = findViewById(R.id.tv_uraian_pekerjaan_pelaksana);
        tvJmlTenagaJmlTenagaKet = findViewById(R.id.tv_jml_tenaga_jml_tenaga_ket);
        tvNoSpkSebelumnya = findViewById(R.id.tv_no_spk_sebelumnya);
        tvStatus = findViewById(R.id.tv_status);
        cvFoto1 = findViewById(R.id.cv_foto1);
        ivFoto1 = findViewById(R.id.iv_foto1);
        cvFoto2 = findViewById(R.id.cv_foto2);
        ivFoto2 = findViewById(R.id.iv_foto2);
        cvFoto3 = findViewById(R.id.cv_foto3);
        ivFoto3 = findViewById(R.id.iv_foto3);
        cvFoto4 = findViewById(R.id.cv_foto4);
        ivFoto4 = findViewById(R.id.iv_foto4);
        tvKetZona = findViewById(R.id.tv_ket_zona);
        tvTka = findViewById(R.id.tv_tka);
        tvBagianPelapor = findViewById(R.id.tv_bagian_pelapor);
        tvStatusVerifikasi = findViewById(R.id.tv_status_verifikasi);
        btnVerifikasi = findViewById(R.id.btn_verifikasi);
        adView = findViewById(R.id.adView);
        btnHapus = findViewById(R.id.btn_hapus);
        tvNoCc = findViewById(R.id.tv_no_cc);
        tvNppVerifikator = findViewById(R.id.tv_npp_verifikator);
        tvLembur = findViewById(R.id.tv_lembur);
        tvAlamatGps = findViewById(R.id.tv_alamat_gps);
    }
}