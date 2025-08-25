package co.id.pdamkotasmg.pekerjaanteknik.activity.spk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat.RiwayatSpkActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.search.SearchPengawasActivity;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.postData.BarangTambahanListAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.adapter.postData.PekerjaanTambahanAdapter;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.fileHandler.PostFotoUploadRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.listAll.AsalItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.listAll.JenisPekerjaanItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.listAll.ListAllGabunganRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.listAll.PekerjaanDikerjakanItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.listAll.StatusPenyelesaianItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.listAll.TenagaItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.post.postRoot.ListBarangItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.post.postRoot.ListBarangLocalItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.post.postRoot.ListPekerjaanItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.post.responseReuired.ResponseReqRootModel;
import co.id.pdamkotasmg.pekerjaanteknik.model.tagihan.BillingTagihanRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import co.id.pdamkotasmg.pekerjaanteknik.utils.ConfigAds;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import id.zelory.compressor.Compressor;
import im.delight.android.location.SimpleLocation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputSpkActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private final String TAG = "debug";

    private static final int REQUEST_PENGAWAS = 2;
    private static final int REQUEST_KASUB = 3;
    private static final int REQUEST_BARANG_DIKERJAKAN = 4;
    private static final int REQUEST_NO_SPK = 5;
    private static final int REQUEST_BARANG_TAMBAHAN = 6;
    private static final int REQUEST_NO_ADUAN_CC = 7;
    private static final int REQUEST_ZONA = 8;
    private static final int REQUEST_VERIFIKATOR = 9;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FusedLocationProviderClient mFusedLocation;

    private String token;
    private String name;
    private String subSatker;
    private String satker;
    private String flag;
    private String tglPelaksanaan;
    private String tglSpk;

    private String jamMulai;
    private String jamSelesai;

    private SimpleLocation location;
    private Double lati, longi;

    private String address_gps;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;

    private String nppPengawas;
    private String namePengawas;
    private String nppKasub;
    private String nameKasub;
    private String nppVerifikator;
    private String nameVerifikator;

    private String kodeBarangDikerjakan;
    private String namaBarangDikerjakan;
    private String jenisPipaBarangDikerjakan;
    private String diameterBarangDikerjakan;

    private String noSpkSebelumnya;

    private String kodeZona = "-";
    private String ketZona = "-";

    private String kodeBarangTambahan;
    private String namaBarangTambahan;
    private String jenisPipaBarangTambahan;
    private String merekBarangTambahan;
    private String satuanBarangTambahan;

    private String ipAdress;
    private String currentTime;

    private String statusIntentBundle;

    private String kodeLembur;

    private ArrayList<String> arrayJenisPekerjaanString = new ArrayList<>();
    private ArrayList<String> arrayJenisPekerjaanID = new ArrayList<String>();
    private ArrayList<String> arrayJenisPekerjaanKdJns = new ArrayList<String>();
    private String jenisPekerjaanID;
    private String jenisPekerjaanKdJenis;
    private List<JenisPekerjaanItem> jenisPekerjaanItems = new ArrayList<>();

    private ArrayList<String> arrayAsal = new ArrayList<>();
    private ArrayList<String> arrayAsalId = new ArrayList<String>();
    private String asal;
    private List<AsalItem> asalItems = new ArrayList<>();

    private ArrayList<String> arrayPekerjaanYgHarusDikerjakan = new ArrayList<>();
    private ArrayList<String> arrayPekerjaanYgHarusDikerjakanId = new ArrayList<String>();
    private String jenisPekerjaanYgHarusDikerjakanID;
    private String jenisPekerjaanYgHarusDikerjakanKeterangan;
    private List<PekerjaanDikerjakanItem> pekerjaanDikerjakanItems = new ArrayList<>();

    private ArrayList<String> arrayTenaga = new ArrayList<>();
    private ArrayList<String> arraytenagaId = new ArrayList<String>();
    private String tenagaID;
    private String tenagaString;
    private List<TenagaItem> tenagaItems = new ArrayList<>();

    private ArrayList<String> arrayStatusPenyelesaian = new ArrayList<>();
    private ArrayList<String> arrayStatusPenyelesaianID = new ArrayList<String>();
    private ArrayList<String> arrayStatusPenyelesaianKodeCC = new ArrayList<String>();
    private ArrayList<String> arrayStatusPenyelesaianKetCC = new ArrayList<String>();
    private String statusKode;
    private String kodeCC;
    private String keetCC;
    private List<StatusPenyelesaianItem> statusPenyelesaianItems = new ArrayList<>();

    private ArrayList<ListPekerjaanItem> listPekerjaanItem = new ArrayList<>();
    private PekerjaanTambahanAdapter pekerjaanTambahanAdapter;

    private ArrayList<ListBarangItem> listBarangItemToServer = new ArrayList<>();
    private ArrayList<ListBarangLocalItem> listBarangLocalItem = new ArrayList<>();
    private BarangTambahanListAdapter barangTambahanListAdapter;

    private EasyImage easyImage;
    private static int REQUEST_FOTO;
    private File compressedImageFile1;
    private File compressedImageFile2;
    private File compressedImageFile3;
    private File compressedImageFile4;
    private String filePathServer1;
    private String filePathServer2;
    private String filePathServer3;
    private String filePathServer4;

    private LinearLayout divTglPelaksanaan;
    private TextView tvTglPelaksanaan;
    private LinearLayout divTglSpk;
    private TextView tvTglSpk;
    private MaterialSpinner spnJenisPekerjaan;
    private TextInputEditText inputNoSpk;
    private TextInputEditText inputAlamatPerbaikan;
    private LinearLayout divKodeZona;
    private TextView tvKodeZona;
    private TextView tvSubagian;
    private LinearLayout divPengawas;
    private TextView tvPengawas;
    private LinearLayout divKasub;
    private TextView tvKasub;
    private TextInputEditText inputNolangg;
    private Button btnCariNolangg;
    private CardView cvNolangg;
    private TextView tvNamaPelanggan;
    private TextView tvCabang;
    private TextView tvAlamat;
    private MaterialSpinner spnPekerjaanYangHrsDikerjakan;
    private EditText edtPekerjaanLainnya;
    private Button btnTambahPekerjaan;
    private Button btnHapusPekerjaan;
    private RecyclerView rvPekerjaanYangHrsDikerjakan;
    private LinearLayout divJamMulai;
    private TextView tvJamMulai;
    private LinearLayout divJamSelesai;
    private TextView tvJamSelesai;
    private LinearLayout divPekerjaan;
    private TextView tvBarangYangDikerjakan;
    private TextInputEditText inputTekanan;
    private TextInputEditText inputUraian;
    private MaterialSpinner spnTenaga;
    private EditText edtTenagaLainnya;
    private TextInputEditText inputPerkiraanTka;
    private MaterialSpinner spnStatusPenyelesaian;
    private LinearLayout divSpkSebelumnya;
    private TextView tvSpkSebelumnyaDanTanggal;
    private LinearLayout divBarang;
    private CardView cvBarang;
    private TextView tvNamaBarang;
    private TextView tvJenisBarang;
    private TextView tvMerek;
    private EditText edtJumlahBarang;
    private TextView tvSatuan;
    private Button btnTambahBarang;
    private Button btnHapusBarang;
    private RecyclerView rvBarang;
    private Button btnSimpan;
    private MaterialSpinner spnAsal;
    private TextView rvPengawas;
    private AdView adView;
    private CardView cvFoto1;
    private ImageView ivFoto1;
    private CardView cvFoto2;
    private ImageView ivFoto2;
    private CardView cvFoto3;
    private ImageView ivFoto3;
    private CardView cvFoto4;
    private ImageView ivFoto4;
    private TextInputEditText inputNamaPelapor;
    private TextInputEditText inputAlamatPelapor;
    private TextView tvKlikMaps;
    private TextInputEditText inputNoAduanCc;
    private LinearLayout divNoAduanCc;
    private RadioGroup rgLembur;
    private RadioButton rbYaLembur;
    private RadioButton rbTidakLembur;
    private LinearLayout divVerifikator;
    private TextView tvVerifikator;
    private LinearLayout divNoTelpPelaporCc;
    private TextView tvNoPelaporCc;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private LinearLayout divNoPelaporCc;
    private Button btnGunakanLokasiMaps;
    private LinearLayout divDeleteFoto3;
    private LinearLayout divDeleteFoto4;
    private ProgressDialog progressDialog;

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_spk);
        
        initView();

        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, InputSpkActivity.this, "Input Form Perbaikan");

        progressDialog = new ProgressDialog(InputSpkActivity.this);
        progressDialog.setMessage("Mengirim data...");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        satker = sharedPreferences.getString(Config.SHARED_SATKER, "");
        subSatker = sharedPreferences.getString(Config.SHARED_SUBSATKER, "");
        nppPengawas = sharedPreferences.getString(Config.SHARED_NPP_PENGAWAS, "");
        namePengawas = sharedPreferences.getString(Config.SHARED_NAME_PENGAWAS, "");
        nppKasub = sharedPreferences.getString(Config.SHARED_NPP_KASUB, "");
        nameKasub = sharedPreferences.getString(Config.SHARED_NAME_KASUB, "");
        nppVerifikator = sharedPreferences.getString(Config.SHARED_NPP_VERIFIKATOR, "");
        nameVerifikator = sharedPreferences.getString(Config.SHARED_NAME_VERIFIKATOR, "");
        tvSubagian.setText(satker + " - " + subSatker);

        statusIntentBundle = getIntent().getStringExtra(Config.BUNDLE_STATUS_INTENT);

        Log.d(TAG, "onCreate: " + nppPengawas + " - " + namePengawas + " || " + nppKasub + " - " + nameKasub);

//        inputNamaPelapor.setText(name);

        if (nppPengawas != null || namePengawas != null) {
            tvPengawas.setText(nppPengawas + " - " + namePengawas);
        } else {
            Toast.makeText(InputSpkActivity.this, "Input 1x Pengawas", Toast.LENGTH_SHORT).show();
        }

        if (nppKasub != null || nameKasub != null) {
            tvKasub.setText(nppKasub + " - " + nameKasub);
        } else {
            Toast.makeText(InputSpkActivity.this, "Input 1x Kasub", Toast.LENGTH_SHORT).show();
        }

        if (nppVerifikator != null || nameVerifikator != null) {
            tvVerifikator.setText(nppVerifikator + " - " + nameVerifikator);
        } else {
            Toast.makeText(InputSpkActivity.this, "Input 1x Verifikator", Toast.LENGTH_SHORT).show();
        }

        String no_langg = getIntent().getStringExtra(Config.BUNDLE_NO_LANGG);
        String nama_pengadu = getIntent().getStringExtra(Config.BUNDLE_NAMA_PENGADU);
        String alamat_pengadu = getIntent().getStringExtra(Config.BUNDLE_ALAMAT_PENGADU);
        String no_pengaduan_cc = getIntent().getStringExtra(Config.BUNDLE_NO_PENGADUAN_CC);
        String noTelpPengaduCC = getIntent().getStringExtra(Config.BUNDLE_NO_TELP_PENGADU);
//        String uraian_pengadu = getIntent().getStringExtra(Config.BUNDLE_URAIAN_PENGADU);

        if (statusIntentBundle == null) {
//            Toast.makeText(this, "Membuat SPK Baru", Toast.LENGTH_SHORT).show();
            divNoAduanCc.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "nolangg : " + getIntent().getStringExtra(Config.BUNDLE_NO_LANGG));
            inputNolangg.setText(no_langg);
            inputNamaPelapor.setText(nama_pengadu);
            inputAlamatPelapor.setText(alamat_pengadu);

            divNoAduanCc.setVisibility(View.VISIBLE);
            if (no_pengaduan_cc == null) {
                inputNoAduanCc.setText("-");
            } else {
                inputNoAduanCc.setText(no_pengaduan_cc);
            }

            if (noTelpPengaduCC == null || noTelpPengaduCC.isEmpty()) {
                tvNoPelaporCc.setText("-");
            } else {
                tvNoPelaporCc.setText(noTelpPengaduCC);
            }

            divNoTelpPelaporCc.setOnClickListener(view -> {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + noTelpPengaduCC));
                startActivity(callIntent);
            });

//            edtPekerjaanLainnya.setText(uraian_pengadu);
            btnCariNolangg.post(() -> btnCariNolangg.performClick());
//            btnTambahPekerjaan.post(() -> {
//                btnTambahPekerjaan.performClick();
//                edtPekerjaanLainnya.setText("");
//            });
        }

        ConfigAds.banner(InputSpkActivity.this, adView);
        easyImage = new EasyImage.Builder(InputSpkActivity.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("Si-AGAN")
                .allowMultiple(true)
                .build();

        getListGabungan();

//        location = new SimpleLocation(InputSpkActivity.this);
//        if (!location.hasLocationEnabled()) {
//            Toast.makeText(this, "Aktifkan Lokasimu", Toast.LENGTH_SHORT).show();
//            SimpleLocation.openSettings(InputSpkActivity.this);
//        }
//        lati = location.getLatitude();
//        longi = location.getLongitude();


        mFusedLocation = LocationServices.getFusedLocationProviderClient(InputSpkActivity.this);
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
                    Toast.makeText(this, "Alamat tidak ditemukan, input manual!", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder geocoder;
                    List<Address> addressList = new ArrayList<>();
                    if (addressList == null) {
                        Log.d("debug", "adress list : Null");
                    } else {
                        geocoder = new Geocoder(InputSpkActivity.this, Locale.getDefault());
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
                    inputAlamatPerbaikan.setText(address_gps);
//                    inputAlamatPelapor.setText(address_gps);
                }
            }
        });

        btnGunakanLokasiMaps.setOnClickListener(view -> {
            inputAlamatPelapor.setText(inputAlamatPerbaikan.getText().toString().trim());
        });


        // TODO get IP Adress
        Context context = getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        ipAdress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        if (ipAdress.contains("0.0.0.0")) {
            ipAdress = "192.123.12.123";
        }

        // TODO get DateTimeNow
        currentTime = String.valueOf(Calendar.getInstance().getTime());
        Log.d(TAG, "ipadress: " + ipAdress);
        Log.d(TAG, "currentTime: " + currentTime);

        divTglPelaksanaan.setOnClickListener(v -> {
            flag = "pelaksanaan";
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    InputSpkActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setThemeDark(true);
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

        divTglSpk.setOnClickListener(v -> {
            flag = "spk";
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    InputSpkActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setThemeDark(true);
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

        divJamMulai.setOnClickListener(v -> {
            flag = "jammulai";
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(InputSpkActivity.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, true);
            timePickerDialog.show(getSupportFragmentManager(), "Timepickerdialog");
        });

        divJamSelesai.setOnClickListener(v -> {
            flag = "jamselesai";
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(InputSpkActivity.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, true);
            timePickerDialog.show(getSupportFragmentManager(), "Timepickerdialog");
        });

        divKodeZona.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchPengawasActivity.class);
            intent.putExtra("requestCode", REQUEST_ZONA);
            startActivityForResult(intent, REQUEST_ZONA);
        });

        divPengawas.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchPengawasActivity.class);
            intent.putExtra("requestCode", REQUEST_PENGAWAS);
            startActivityForResult(intent, REQUEST_PENGAWAS);
        });

        divKasub.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchPengawasActivity.class);
            intent.putExtra("requestCode", REQUEST_KASUB);
            startActivityForResult(intent, REQUEST_KASUB);
        });

        divVerifikator.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchPengawasActivity.class);
            intent.putExtra("requestCode", REQUEST_VERIFIKATOR);
            startActivityForResult(intent, REQUEST_VERIFIKATOR);
        });

        btnCariNolangg.setOnClickListener(v -> {
            if (inputNolangg.getText().toString().contains("-")) {
                Toast.makeText(this, "Isi Nolangg dengan benar", Toast.LENGTH_SHORT).show();
            } else {
                cekNolangg();
            }
        });

        divPekerjaan.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchPengawasActivity.class);
            intent.putExtra("requestCode", REQUEST_BARANG_DIKERJAKAN);
            startActivityForResult(intent, REQUEST_BARANG_DIKERJAKAN);
        });

        divSpkSebelumnya.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchPengawasActivity.class);
            intent.putExtra("requestCode", REQUEST_NO_SPK);
            startActivityForResult(intent, REQUEST_NO_SPK);
        });

        divBarang.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchPengawasActivity.class);
            intent.putExtra("requestCode", REQUEST_BARANG_TAMBAHAN);
            startActivityForResult(intent, REQUEST_BARANG_TAMBAHAN);
        });

        // TODO pekerjaan tambahan
        spnPekerjaanYangHrsDikerjakan.setOnItemSelectedListener((view, position, id, item) -> {
            edtPekerjaanLainnya.setEnabled(true);
            jenisPekerjaanYgHarusDikerjakanID = arrayPekerjaanYgHarusDikerjakanId.get(position);
            jenisPekerjaanYgHarusDikerjakanKeterangan = arrayPekerjaanYgHarusDikerjakan.get(position);
            edtPekerjaanLainnya.setText(jenisPekerjaanYgHarusDikerjakanKeterangan);
            if (jenisPekerjaanYgHarusDikerjakanID.contains("99")) {
                edtPekerjaanLainnya.setEnabled(true);
                edtPekerjaanLainnya.setText("");
            }
        });

        btnTambahPekerjaan.setOnClickListener(v -> {
            if (edtPekerjaanLainnya.getText().toString().isEmpty()) {
                edtPekerjaanLainnya.setError("Pilih pekerjaan");
            } else if (jenisPekerjaanYgHarusDikerjakanID == null) {
                Toast.makeText(context, "Null lainnya", Toast.LENGTH_SHORT).show();
                jenisPekerjaanYgHarusDikerjakanID = "99";
                ListPekerjaanItem listBarangDataItems = new ListPekerjaanItem();
                listBarangDataItems.setJnsPekrj(jenisPekerjaanYgHarusDikerjakanID);
                listBarangDataItems.setJnsPekrjLain(edtPekerjaanLainnya.getText().toString().trim());
                listPekerjaanItem.add(listBarangDataItems);
                pekerjaanTambahanAdapter = new PekerjaanTambahanAdapter(InputSpkActivity.this, listPekerjaanItem);
                rvPekerjaanYangHrsDikerjakan.setVisibility(View.VISIBLE);
                rvPekerjaanYangHrsDikerjakan.setLayoutManager(new LinearLayoutManager(InputSpkActivity.this));
                rvPekerjaanYangHrsDikerjakan.setAdapter(pekerjaanTambahanAdapter);
                pekerjaanTambahanAdapter.notifyDataSetChanged();

                Log.d(TAG, "Array Pekerjaan dikerjakan : " + listPekerjaanItem.toString());
            } else {
                boolean finding = false;
                for (int i = 0; i < listPekerjaanItem.size(); i++) {
                    if (listPekerjaanItem.get(i).getJnsPekrj().equals(jenisPekerjaanYgHarusDikerjakanID)) {
                        Toast.makeText(context, "Error : Pekerjaaan sudah ada", Toast.LENGTH_SHORT).show();
                        finding = true;
                        break;
                    }
                }

                if (!finding) {
//                    Toast.makeText(context, "! finding", Toast.LENGTH_SHORT).show();
                    ListPekerjaanItem listBarangDataItems = new ListPekerjaanItem();
                    listBarangDataItems.setJnsPekrj(jenisPekerjaanYgHarusDikerjakanID);
                    listBarangDataItems.setJnsPekrjLain(edtPekerjaanLainnya.getText().toString().trim());
                    listPekerjaanItem.add(listBarangDataItems);
                    pekerjaanTambahanAdapter = new PekerjaanTambahanAdapter(InputSpkActivity.this, listPekerjaanItem);
                    rvPekerjaanYangHrsDikerjakan.setVisibility(View.VISIBLE);
                    rvPekerjaanYangHrsDikerjakan.setLayoutManager(new LinearLayoutManager(InputSpkActivity.this));
                    rvPekerjaanYangHrsDikerjakan.setAdapter(pekerjaanTambahanAdapter);
                    pekerjaanTambahanAdapter.notifyDataSetChanged();

                    Log.d(TAG, "Array Pekerjaan dikerjakan : " + listPekerjaanItem.toString());
                }
            }
        });

        btnHapusPekerjaan.setOnClickListener(v -> {
            listPekerjaanItem.clear();
            pekerjaanTambahanAdapter = new PekerjaanTambahanAdapter(InputSpkActivity.this, listPekerjaanItem);
            edtPekerjaanLainnya.setText("");
            rvPekerjaanYangHrsDikerjakan.setVisibility(View.GONE);
            pekerjaanTambahanAdapter.notifyDataSetChanged();
        });

        // TODO selesai Pekerjaan yang dikerjakan

        // TODO Tenaga
        spnTenaga.setOnItemSelectedListener((view, position, id, item) -> {
            edtTenagaLainnya.setEnabled(false);
            tenagaID = arraytenagaId.get(position);
            tenagaString = arrayTenaga.get(position);
            edtTenagaLainnya.setText(tenagaString);
            if (tenagaID.contains("1200Z")) {
                String dataTenagaLainnya = sharedPreferences.getString(Config.SHARED_TENAGA_LAINNYA, "");
                if (dataTenagaLainnya.isEmpty()) {
                    edtTenagaLainnya.setEnabled(true);
                    edtTenagaLainnya.setText("");
                    edtTenagaLainnya.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            editor.putString(Config.SHARED_TENAGA_LAINNYA, s.toString());
                            editor.apply();
                        }
                    });
                    Log.d(TAG, "Lainnya if : " + dataTenagaLainnya);
                } else {
                    edtTenagaLainnya.setEnabled(true);
                    edtTenagaLainnya.setText(dataTenagaLainnya);
                }

            }
        });
        // TODO tenaga selesai

        // TODO Lembur

        rgLembur.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_ya_lembur) {
                kodeLembur = "1";
            } else if (checkedId == R.id.rb_tidak_lembur) {
                kodeLembur = "0";
            }
        });

        // TODO jenis pekerjaan
        if (jenisPekerjaanID == null) {
            jenisPekerjaanID = "0";
            jenisPekerjaanKdJenis = "LL";

            Log.d(TAG, "ID " + jenisPekerjaanID + " Kd Jenis " + jenisPekerjaanKdJenis);
        }
        spnJenisPekerjaan.setOnItemSelectedListener((view, position, id, item) -> {
            jenisPekerjaanID = arrayJenisPekerjaanID.get(position);
            jenisPekerjaanKdJenis = arrayJenisPekerjaanKdJns.get(position);
        });

        // TODO jenis pekerjaan selesao

        if (asal == null) {
            asal = "0";
            Log.d(TAG, "asal " + asal);
        }
        spnAsal.setOnItemSelectedListener((view, position, id, item) -> {
            asal = arrayAsal.get(position);
            if (asal.equalsIgnoreCase("Call Center")) {
                divNoAduanCc.setVisibility(View.VISIBLE);
            } else {
                divNoAduanCc.setVisibility(View.GONE);
            }
        });

        if (statusKode == null) {
            statusKode = "00";
            Log.d(TAG, "status code " + statusKode);
        }
        spnStatusPenyelesaian.setOnItemSelectedListener((view, position, id, item) -> {
            statusKode = arrayStatusPenyelesaianID.get(position);
            kodeCC = arrayStatusPenyelesaianKodeCC.get(position);
            keetCC = arrayStatusPenyelesaianKetCC.get(position);
        });

        // TODO tambah Barang
        btnTambahBarang.setOnClickListener(v -> {
            if (edtJumlahBarang.getText().toString().isEmpty()) {
                Toast.makeText(this, "Isi jumlah barang", Toast.LENGTH_SHORT).show();
            } else {
//                // server
//                ListBarangItem listBarangItemToServers = new ListBarangItem();
//                listBarangItemToServers.setKdBrg(kodeBarangTambahan);
//                listBarangItemToServers.setJml(edtJumlahBarang.getText().toString().trim());
//                listBarangItemToServers.setSatuan(tvSatuan.getText().toString().trim());
//                listBarangItemToServer.add(listBarangItemToServers); // ini yang dikirim ke server

                // local
//                ListBarangLocalItem listBarangLocalItems = new ListBarangLocalItem();
//                listBarangLocalItems.setKdBrg(kodeBarangTambahan);
//                listBarangLocalItems.setNm_brg(namaBarangTambahan);
//                listBarangLocalItems.setJns_brg(jenisPipaBarangTambahan);
//                listBarangLocalItems.setMerek(merekBarangTambahan);
//                listBarangLocalItems.setJml(edtJumlahBarang.getText().toString().trim());
//                listBarangLocalItems.setSatuan(tvSatuan.getText().toString().trim());
//                listBarangLocalItem.add(listBarangLocalItems);

                boolean finding = false;
                for (int i = 0; i < listBarangLocalItem.size(); i++) {
                    if (listBarangLocalItem.get(i).getKdBrg().equals(kodeBarangTambahan)) {
                        Toast.makeText(context, "Error : Barang sudah ada", Toast.LENGTH_SHORT).show();
                        finding = true;
                        break;
                    }
                }

                if (!finding) {
                    // server
                    ListBarangItem listBarangItemToServers = new ListBarangItem();
                    listBarangItemToServers.setKdBrg(kodeBarangTambahan);
                    listBarangItemToServers.setJml(edtJumlahBarang.getText().toString().trim());
                    listBarangItemToServers.setSatuan(tvSatuan.getText().toString().trim());
                    listBarangItemToServer.add(listBarangItemToServers); // ini yang dikirim ke server

                    // local
                    ListBarangLocalItem listBarangLocalItems = new ListBarangLocalItem();
                    listBarangLocalItems.setKdBrg(kodeBarangTambahan);
                    listBarangLocalItems.setNm_brg(namaBarangTambahan);
                    listBarangLocalItems.setJns_brg(jenisPipaBarangTambahan);
                    listBarangLocalItems.setMerek(merekBarangTambahan);
                    listBarangLocalItems.setJml(edtJumlahBarang.getText().toString().trim());
                    listBarangLocalItems.setSatuan(tvSatuan.getText().toString().trim());
                    listBarangLocalItem.add(listBarangLocalItems);

                    barangTambahanListAdapter = new BarangTambahanListAdapter(InputSpkActivity.this, listBarangLocalItem);
                    rvBarang.setVisibility(View.VISIBLE);
                    rvBarang.setAdapter(barangTambahanListAdapter);
                    rvBarang.setLayoutManager(new LinearLayoutManager(InputSpkActivity.this));
                    barangTambahanListAdapter.notifyDataSetChanged();

                    cvBarang.setVisibility(View.GONE);
                    btnHapusBarang.setVisibility(View.VISIBLE);
                    edtJumlahBarang.setText("");

                    Log.d(TAG, "listBarangItemToServer: " + listBarangItemToServer.toString());
                    Log.d(TAG, "listBarangLocalItem: " + listBarangLocalItem.toString());
                }


            }

        });

        btnHapusBarang.setOnClickListener(v -> {
            listBarangItemToServer.clear();
            listBarangLocalItem.clear();
            barangTambahanListAdapter = new BarangTambahanListAdapter(InputSpkActivity.this, listBarangLocalItem);
            rvBarang.setVisibility(View.VISIBLE);
            rvBarang.setAdapter(barangTambahanListAdapter);
            rvBarang.setLayoutManager(new LinearLayoutManager(InputSpkActivity.this));
            barangTambahanListAdapter.notifyDataSetChanged();
            btnHapusBarang.setVisibility(View.GONE);
        });

        cvFoto1.setOnClickListener(v -> {
            REQUEST_FOTO = 1;
            easyImage.openChooser(InputSpkActivity.this);
        });

        cvFoto2.setOnClickListener(v -> {
            REQUEST_FOTO = 2;
            easyImage.openChooser(InputSpkActivity.this);
        });

        cvFoto3.setOnClickListener(v -> {
            REQUEST_FOTO = 3;
            easyImage.openChooser(InputSpkActivity.this);
        });

        cvFoto4.setOnClickListener(v -> {
            REQUEST_FOTO = 4;
            easyImage.openChooser(InputSpkActivity.this);
        });


        btnSimpan.setOnClickListener(v -> {
            postUploadFoto1();
//            // TODO 1. Cek kosong atau tidak editText pekerjaan lainya dan tenaga sudah
//            // TODO 2. cek sudah di isi semua atau belum sudah
//            if (tvTglPelaksanaan.getText().toString().equalsIgnoreCase("-") || tvTglSpk.getText().toString().equalsIgnoreCase("-")) {
//                Toast.makeText(this, "Isi tanggal pelaksanaan/SPK", Toast.LENGTH_SHORT).show();
//            } else if (asal == null || asal.contains("0")) {
//                Toast.makeText(this, "Isi Asal", Toast.LENGTH_SHORT).show();
//            } else if (inputAlamatPerbaikan.getText().toString().isEmpty()) {
//                Toast.makeText(this, "Isi alamat perbaikan", Toast.LENGTH_SHORT).show();
//            } else if (tvPengawas.getText().toString().equalsIgnoreCase("-") || tvKasub.getText().toString().equalsIgnoreCase("-")) {
//                Toast.makeText(this, "Cek NPP Pengawas, Kasub,dan Verifikator", Toast.LENGTH_SHORT).show();
//            } else if (edtPekerjaanLainnya.getText().toString().isEmpty() || listPekerjaanItem.isEmpty()) {
//                Toast.makeText(this, "Tambahkan pekerjaan yang harus dikerjakan", Toast.LENGTH_SHORT).show();
//            } else if (tvBarangYangDikerjakan.getText().toString().equalsIgnoreCase("-")) {
//                Toast.makeText(this, "Isi aksesoris tambahan", Toast.LENGTH_SHORT).show();
//            } else if (edtTenagaLainnya.getText().toString().isEmpty()) {
//                Toast.makeText(this, "Isi tenaga", Toast.LENGTH_SHORT).show();
//            } else if (listBarangItemToServer.isEmpty()) {
//                Toast.makeText(this, "Isi Aksesoris tambahan", Toast.LENGTH_SHORT).show();
//            } else if (statusKode == null || statusKode.isEmpty() || statusKode.contains("00")) {
//                Toast.makeText(this, "Isi status", Toast.LENGTH_SHORT).show();
//            } else if (compressedImageFile1 == null || compressedImageFile2 == null) {
//                Toast.makeText(this, "Lengkapi minimal 2/4 Foto", Toast.LENGTH_SHORT).show();
//            } else if (kodeLembur == null) {
//                Toast.makeText(this, "Isi Lembur Ya/Tidak", Toast.LENGTH_SHORT).show();
//            }
////            else if (inputNoAduanCc.getText().toString().equalsIgnoreCase("")) {
////                Toast.makeText(this, "Isi No Aduan dari Call Center", Toast.LENGTH_SHORT).show();
////            }
//            else {
//                MaterialDialog mDialog = new MaterialDialog.Builder(InputSpkActivity.this)
//                        .setTitle("Apaka data Anda sudah benar?")
//                        .setCancelable(false)
//                        .setNegativeButton("Belum", (dialogInterface, which) -> {
//                            dialogInterface.dismiss();
//                        })
//                        .setPositiveButton("Sudah", (dialogInterface, which) -> {
//                            postUploadFoto1();
//                        })
//                        .build();
//
//                mDialog.show();
//            }
        });

        tvKlikMaps.setOnClickListener(v -> {
            Intent i = new Intent(InputSpkActivity.this, MapsAdressActivity.class);
            startActivityForResult(i, Config.REQ_ACT_RESULT_CODE_MAPS);
        });

        divNoAduanCc.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchPengawasActivity.class);
            intent.putExtra("requestCode", REQUEST_NO_ADUAN_CC);
            startActivityForResult(intent, REQUEST_NO_ADUAN_CC);
        });

        divDeleteFoto3.setOnClickListener(view -> {
            Log.d(TAG, "isi compres: " + compressedImageFile3);
            compressedImageFile3 = null;
            Log.d(TAG, "null compres: " + compressedImageFile3);
            Glide.with(context).load(com.pdamkotasmg.goodday.R.drawable.image_not_available).into(ivFoto3);
            Log.d(TAG, "get ulang compres: " + compressedImageFile3);
        });

        divDeleteFoto4.setOnClickListener(view -> {
            Log.d(TAG, "isi compres: " + compressedImageFile4);
            compressedImageFile4 = null;
            Log.d(TAG, "null compres: " + compressedImageFile4);
            Glide.with(context).load(com.pdamkotasmg.goodday.R.drawable.image_not_available).into(ivFoto4);
            Log.d(TAG, "get ulang compres: " + compressedImageFile4);
        });

    }

    private void postUploadFoto1() {
        progressDialog.show();
        Date currentTime = Calendar.getInstance().getTime();
        String timestamp = String.valueOf(currentTime.getTime());
        String year = new SimpleDateFormat("Y", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        RequestBody path = RequestBody.create(MediaType.parse("text/plain"), "/pekerjaan-teknik/foto/" + year + "/" + month);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), "ft-1-" + nppPengawas + "-" + year + month + "-" + timestamp);

        File imageFileTeknik = new File(compressedImageFile1.getPath());
        RequestBody requestFilePhotoKtp = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileTeknik);
        MultipartBody.Part bodyFileTeknik = MultipartBody.Part.createFormData("photo", imageFileTeknik.getName(), requestFilePhotoKtp);

        ApiService apiService = ApiConfig.getApiService(InputSpkActivity.this, Config.BASE_URL);
        apiService.postUploadFoto(token, path, fileName, bodyFileTeknik)
                .enqueue(new Callback<PostFotoUploadRootModel>() {
                    @Override
                    public void onResponse(Call<PostFotoUploadRootModel> call, Response<PostFotoUploadRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "uploadImage " + response.body().getData().getFileurl());
                            filePathServer1 = response.body().getData().getFilepath();
                            postUploadFoto2(month, year, timestamp);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostFotoUploadRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(InputSpkActivity.this, "Upload Foto 1 Gagal " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postUploadFoto2(String month, String year, String timestamp) {
        RequestBody path = RequestBody.create(MediaType.parse("text/plain"), "/pekerjaan-teknik/foto/" + year + "/" + month);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), "ft-2-" + nppPengawas + "-" + year + month + "-" + timestamp);

        File imageFileTeknik = new File(compressedImageFile2.getPath());
        RequestBody requestFilePhotoKtp = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileTeknik);
        MultipartBody.Part bodyFileTeknik = MultipartBody.Part.createFormData("photo", imageFileTeknik.getName(), requestFilePhotoKtp);

        ApiService apiService = ApiConfig.getApiService(InputSpkActivity.this, Config.BASE_URL);
        apiService.postUploadFoto(token, path, fileName, bodyFileTeknik)
                .enqueue(new Callback<PostFotoUploadRootModel>() {
                    @Override
                    public void onResponse(Call<PostFotoUploadRootModel> call, Response<PostFotoUploadRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "uploadImage " + response.body().getData().getFileurl());
                            filePathServer2 = response.body().getData().getFilepath();

                            if (compressedImageFile3 != null || compressedImageFile4 != null) {
                                postUploadFoto3(month, year, timestamp);
                            } else {
//                                Toast.makeText(InputSpkActivity.this, "Post Data 2", Toast.LENGTH_SHORT).show();
                                postDataSPK2();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<PostFotoUploadRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(InputSpkActivity.this, "Upload Foto 2 Gagal " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postUploadFoto3(String month, String year, String timestamp) {
        RequestBody path = RequestBody.create(MediaType.parse("text/plain"), "/pekerjaan-teknik/foto/" + year + "/" + month);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), "ft-3-" + nppPengawas + "-" + year + month + "-" + timestamp);

        File imageFileTeknik = new File(compressedImageFile3.getPath());
        RequestBody requestFilePhotoKtp = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileTeknik);
        MultipartBody.Part bodyFileTeknik = MultipartBody.Part.createFormData("photo", imageFileTeknik.getName(), requestFilePhotoKtp);

        ApiService apiService = ApiConfig.getApiService(InputSpkActivity.this, Config.BASE_URL);
        apiService.postUploadFoto(token, path, fileName, bodyFileTeknik)
                .enqueue(new Callback<PostFotoUploadRootModel>() {
                    @Override
                    public void onResponse(Call<PostFotoUploadRootModel> call, Response<PostFotoUploadRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "uploadImage " + response.body().getData().getFileurl());
                            filePathServer3 = response.body().getData().getFilepath();
                            postUploadFoto4(month, year, timestamp);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostFotoUploadRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(InputSpkActivity.this, "Upload Foto 3 Gagal " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postUploadFoto4(String month, String year, String timestamp) {
        RequestBody path = RequestBody.create(MediaType.parse("text/plain"), "/pekerjaan-teknik/foto/" + year + "/" + month);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), "ft-4-" + nppPengawas + "-" + year + month + "-" + timestamp);

        File imageFileTeknik = new File(compressedImageFile4.getPath());
        RequestBody requestFilePhotoKtp = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileTeknik);
        MultipartBody.Part bodyFileTeknik = MultipartBody.Part.createFormData("photo", imageFileTeknik.getName(), requestFilePhotoKtp);

        ApiService apiService = ApiConfig.getApiService(InputSpkActivity.this, Config.BASE_URL);
        apiService.postUploadFoto(token, path, fileName, bodyFileTeknik)
                .enqueue(new Callback<PostFotoUploadRootModel>() {
                    @Override
                    public void onResponse(Call<PostFotoUploadRootModel> call, Response<PostFotoUploadRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "uploadImage " + response.body().getData().getFileurl());
                            filePathServer4 = response.body().getData().getFilepath();
                            Toast.makeText(InputSpkActivity.this, "Post Data 4", Toast.LENGTH_SHORT).show();
                            postDataSPK2();
                            Log.d(TAG, "file1: " + filePathServer1);
                            Log.d(TAG, "file2: " + filePathServer2);
                            Log.d(TAG, "file3: " + filePathServer3);
                            Log.d(TAG, "file4: " + filePathServer4);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostFotoUploadRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(InputSpkActivity.this, "Upload Foto 4 Gagal " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postDataSPK2() {

//        File foto1 = new File(compressedImageFile1.getPath());
//        RequestBody reqFoto1 = RequestBody.create(MediaType.parse("multipart/form-data"), foto1);
//        MultipartBody.Part bodyFoto1 = MultipartBody.Part.createFormData("foto1", foto1.getName(), reqFoto1);
//
//        File foto2 = new File(compressedImageFile2.getPath());
//        RequestBody reqFoto2 = RequestBody.create(MediaType.parse("multipart/form-data"), foto2);
//        MultipartBody.Part bodyFoto2 = MultipartBody.Part.createFormData("foto2", foto2.getName(), reqFoto2);
//
//        MultipartBody.Part bodyFoto3 = null;
//        MultipartBody.Part bodyFoto4 = null;
//        if (compressedImageFile3 != null || compressedImageFile4 != null) {
//            File foto3 = new File(compressedImageFile3.getPath());
//            RequestBody reqFoto3 = RequestBody.create(MediaType.parse("multipart/form-data"), foto3);
//            bodyFoto3 = MultipartBody.Part.createFormData("foto3", foto3.getName(), reqFoto3);
//
//            File foto4 = new File(compressedImageFile4.getPath());
//            RequestBody reqFoto4 = RequestBody.create(MediaType.parse("multipart/form-data"), foto4);
//            bodyFoto4 = MultipartBody.Part.createFormData("foto4", foto4.getName(), reqFoto4);
//        }

        if (inputNolangg.getText().toString().isEmpty()) {
            inputNolangg.setText("-");
        }
        if (inputNamaPelapor.getText().toString().isEmpty()) {
            inputNamaPelapor.setText("-");
        }
        if (inputAlamatPelapor.getText().toString().isEmpty()) {
            inputAlamatPelapor.setText("-");
        }

        RequestBody no_spk = RequestBody.create(MediaType.parse("text/plain"), inputNoSpk.getText().toString().trim());
        RequestBody tgl_pelaksana = RequestBody.create(MediaType.parse("text/plain"), tvTglPelaksanaan.getText().toString().trim());
        RequestBody jam1 = RequestBody.create(MediaType.parse("text/plain"), tvJamMulai.getText().toString().trim());
        RequestBody jam2 = RequestBody.create(MediaType.parse("text/plain"), tvJamSelesai.getText().toString().trim());
        RequestBody jenis_pipa = RequestBody.create(MediaType.parse("text/plain"), kodeBarangDikerjakan);
        RequestBody diameter = RequestBody.create(MediaType.parse("text/plain"), diameterBarangDikerjakan);
        RequestBody tekanan = RequestBody.create(MediaType.parse("text/plain"), inputTekanan.getText().toString().trim());
        RequestBody uraian = RequestBody.create(MediaType.parse("text/plain"), edtPekerjaanLainnya.getText().toString().trim());
        RequestBody jml_tenaga = RequestBody.create(MediaType.parse("text/plain"), tenagaString);
        RequestBody jml_tenaga_ket = RequestBody.create(MediaType.parse("text/plain"), edtTenagaLainnya.getText().toString().trim());
        RequestBody kasub = RequestBody.create(MediaType.parse("text/plain"), nppKasub);
        RequestBody pengawas = RequestBody.create(MediaType.parse("text/plain"), nppPengawas);
        RequestBody ip_entry = RequestBody.create(MediaType.parse("text/plain"), ipAdress);
        RequestBody pc_entry = RequestBody.create(MediaType.parse("text/plain"), "Android");
//        RequestBody tanggal = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(currentTime));
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), statusKode);
        RequestBody no_spk_sbl = RequestBody.create(MediaType.parse("text/plain"), tvSpkSebelumnyaDanTanggal.getText().toString().trim());
        RequestBody x = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lati));
        RequestBody y = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longi));
        RequestBody kd_penangan = RequestBody.create(MediaType.parse("text/plain"), "0");
        RequestBody tka = RequestBody.create(MediaType.parse("text/plain"), inputPerkiraanTka.getText().toString().trim());
        RequestBody kd_perbaikan = RequestBody.create(MediaType.parse("text/plain"), jenisPekerjaanID);
        RequestBody kd_zona = RequestBody.create(MediaType.parse("text/plain"), kodeZona);
        RequestBody tipe_zona = RequestBody.create(MediaType.parse("text/plain"), "-");
        RequestBody ket_zona = RequestBody.create(MediaType.parse("text/plain"), ketZona);
        RequestBody nm_lapor = RequestBody.create(MediaType.parse("text/plain"), inputNamaPelapor.getText().toString().trim());
        RequestBody alamat_lapor = RequestBody.create(MediaType.parse("text/plain"), inputAlamatPelapor.getText().toString().trim());
        RequestBody nolangg_lapor = RequestBody.create(MediaType.parse("text/plain"), inputNolangg.getText().toString().trim());
        RequestBody asal_lapor = RequestBody.create(MediaType.parse("text/plain"), asal);
        RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), tvNamaPelanggan.getText().toString().trim());
        RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), tvAlamat.getText().toString().trim());
        RequestBody lokasi = RequestBody.create(MediaType.parse("text/plain"), inputAlamatPerbaikan.getText().toString().trim());
        RequestBody kd_jns = RequestBody.create(MediaType.parse("text/plain"), jenisPekerjaanKdJenis);
        RequestBody st_lembur = RequestBody.create(MediaType.parse("text/plain"), kodeLembur);
        RequestBody user_ver = RequestBody.create(MediaType.parse("text/plain"), "-");

        RequestBody no_pengaduan_cc = RequestBody.create(MediaType.parse("text/plain"), inputNoAduanCc.getText().toString());
        RequestBody no_aduan = RequestBody.create(MediaType.parse("text/plain"), inputNoAduanCc.getText().toString());
        RequestBody penyelesaian = RequestBody.create(MediaType.parse("text/plain"), keetCC);
//        RequestBody petugas = RequestBody.create(MediaType.parse("text/plain"), namePengawas);
//        RequestBody tgl_selesai = RequestBody.create(MediaType.parse("text/plain"), tvTglPelaksanaan.getText().toString().trim());
        RequestBody status_kode_cc = RequestBody.create(MediaType.parse("text/plain"), kodeCC);

        RequestBody file1 = RequestBody.create(MediaType.parse("text/plain"), filePathServer1);
        RequestBody file2 = RequestBody.create(MediaType.parse("text/plain"), filePathServer2);

//        filePathServer3 = null;
//        filePathServer4 = null;
        RequestBody file3 = null;
        RequestBody file4 = null;
        if (compressedImageFile3 != null || compressedImageFile4 != null) {
            file3 = RequestBody.create(MediaType.parse("text/plain"), filePathServer3);
            file4 = RequestBody.create(MediaType.parse("text/plain"), filePathServer4);
        }

        Gson gson = new Gson();
        String arrayListBarang = gson.toJson(listBarangItemToServer);
        String arrayListPekerjaan = gson.toJson(listPekerjaanItem);

        RequestBody list_barang = RequestBody.create(MediaType.parse("text/plain"), arrayListBarang);
        RequestBody list_pekerjaan = RequestBody.create(MediaType.parse("text/plain"), arrayListPekerjaan);

        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.postDatasSPK2(token, tgl_pelaksana, jam1, jam2, jenis_pipa, diameter, tekanan, uraian, jml_tenaga, jml_tenaga_ket, kasub, pengawas, ip_entry, pc_entry, status,
                        no_spk_sbl, x, y, kd_penangan, tka, kd_perbaikan, kd_zona, tipe_zona, ket_zona, nm_lapor, alamat_lapor, nolangg_lapor, asal_lapor, nama, alamat, lokasi, list_barang,
                        list_pekerjaan, file1, file2, file3, file4, kd_jns, no_pengaduan_cc, no_aduan, penyelesaian, status_kode_cc, st_lembur, user_ver)
                .enqueue(new Callback<ResponseReqRootModel>() {
                    @Override
                    public void onResponse(Call<ResponseReqRootModel> call, Response<ResponseReqRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "file1 send postDataSPK2: " + filePathServer1);
                            Log.d(TAG, "file2 send postDataSPK2: " + filePathServer2);
                            progressDialog.cancel();
                            Toast.makeText(InputSpkActivity.this, "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(InputSpkActivity.this, RiwayatSpkActivity.class);
                            intent.putExtra(Config.BUNDLE_KD_RIWAYAT, "1");
                            startActivity(intent);
                            finish();
//                            startActivity(new Intent(getApplicationContext(), RiwayatSpkActivity.class));
                        } else {
                            progressDialog.cancel();
                            Log.d(TAG, "onResponse: " + response.body());
                            Toast.makeText(InputSpkActivity.this, "Error : " + response.body(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseReqRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(InputSpkActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cekNolangg() {
        ProgressDialog progressDialog = new ProgressDialog(InputSpkActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.getBillingTagihan(inputNolangg.getText().toString().trim(), token)
                .enqueue(new Callback<BillingTagihanRootModel>() {
                    @Override
                    public void onResponse(Call<BillingTagihanRootModel> call, Response<BillingTagihanRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            cvNolangg.setVisibility(View.VISIBLE);
                            tvNamaPelanggan.setText(response.body().getData().getPelanggan().getNama());
                            tvAlamat.setText(response.body().getData().getPelanggan().getAlamat());
                            tvCabang.setText(response.body().getData().getPelanggan().getCabang());
                            inputNamaPelapor.setText(response.body().getData().getPelanggan().getNama());
                            inputAlamatPelapor.setText(response.body().getData().getPelanggan().getAlamat());
                        } else {
                            progressDialog.cancel();
                            cvNolangg.setVisibility(View.GONE);
                            Toast.makeText(InputSpkActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BillingTagihanRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        cvNolangg.setVisibility(View.GONE);
                        Toast.makeText(InputSpkActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        assert data != null;

        // maps
        if (requestCode == Config.REQ_ACT_RESULT_CODE_MAPS) {
            if (resultCode == Activity.RESULT_OK) {
                String alamat = data.getStringExtra("alamat");
                String kode_pos = data.getStringExtra("kode_pos");
                lati = data.getDoubleExtra("lat", 0);
                longi = data.getDoubleExtra("long", 0);
                Log.d(TAG, "Alamat: " + alamat
                        + " kode pos: " + kode_pos
                        + " lati: " + lati + " longi: " + longi
                );
                inputAlamatPerbaikan.setText(alamat);
            }
        }

        if (requestCode == REQUEST_PENGAWAS && resultCode == RESULT_OK) {
            nppPengawas = data.getStringExtra("npp");
            namePengawas = data.getStringExtra("name");
            if (nppPengawas == null || namePengawas == null) {
                nppPengawas = "000000";
                namePengawas = "null";
            }
            editor.putString(Config.SHARED_NPP_PENGAWAS, nppPengawas);
            editor.putString(Config.SHARED_NAME_PENGAWAS, namePengawas);
            editor.apply();
            tvPengawas.setText(nppPengawas + " - " + namePengawas);
        } else if (requestCode == REQUEST_KASUB && resultCode == RESULT_OK) {
            nppKasub = data.getStringExtra("npp");
            nameKasub = data.getStringExtra("name");
            if (nppKasub == null || nameKasub == null) {
                nppKasub = "000000";
                nameKasub = "null";
            }
            editor.putString(Config.SHARED_NPP_KASUB, nppKasub);
            editor.putString(Config.SHARED_NAME_KASUB, nameKasub);
            editor.apply();
            tvKasub.setText(nppKasub + " - " + nameKasub);
        } else if (requestCode == REQUEST_VERIFIKATOR && resultCode == RESULT_OK) {
            nppVerifikator = data.getStringExtra("npp");
            nameVerifikator = data.getStringExtra("name");
            if (nppVerifikator == null || nameVerifikator == null) {
                nppVerifikator = "000000";
                nameVerifikator = "null";
            }
            editor.putString(Config.SHARED_NPP_VERIFIKATOR, nppVerifikator);
            editor.putString(Config.SHARED_NAME_VERIFIKATOR, nameVerifikator);
            editor.apply();
            tvVerifikator.setText(nppVerifikator + " - " + nameVerifikator);
        } else if (requestCode == REQUEST_BARANG_DIKERJAKAN && resultCode == RESULT_OK) {
            kodeBarangDikerjakan = data.getStringExtra("kode"); // DB jenis pipa (0010000007)
            namaBarangDikerjakan = data.getStringExtra("nm_brg");
            jenisPipaBarangDikerjakan = data.getStringExtra("jns_pipa"); // DB bukan ini jenis pipa (Air Valve)
            diameterBarangDikerjakan = data.getStringExtra("diameter");
            if (kodeBarangDikerjakan == null || namaBarangDikerjakan == null || jenisPipaBarangDikerjakan == null || diameterBarangDikerjakan == null) {
                kodeBarangDikerjakan = "null";
                namaBarangDikerjakan = "null";
                jenisPipaBarangDikerjakan = "null";
                diameterBarangDikerjakan = "null";
            }
            tvBarangYangDikerjakan.setText(kodeBarangDikerjakan + " - " + namaBarangDikerjakan);
        } else if (requestCode == REQUEST_NO_SPK && resultCode == RESULT_OK) {
            noSpkSebelumnya = data.getStringExtra("nospk");
            String tglSpk = data.getStringExtra("tgl");
            tvSpkSebelumnyaDanTanggal.setText(noSpkSebelumnya + " - " + tglSpk);
        } else if (requestCode == REQUEST_BARANG_TAMBAHAN && resultCode == RESULT_OK) {
            cvBarang.setVisibility(View.VISIBLE);
            kodeBarangTambahan = data.getStringExtra("kode");
            namaBarangTambahan = data.getStringExtra("nm_brg");
            jenisPipaBarangTambahan = data.getStringExtra("jns_brg");
            merekBarangTambahan = data.getStringExtra("merek");
            satuanBarangTambahan = data.getStringExtra("satuan");

            if (kodeBarangTambahan == null || namaBarangTambahan == null || jenisPipaBarangTambahan == null || merekBarangTambahan == null || satuanBarangTambahan == null) {
                kodeBarangTambahan = "null";
                namaBarangTambahan = "null";
                jenisPipaBarangTambahan = "null";
                merekBarangTambahan = "null";
                satuanBarangTambahan = "null";
            }

            tvNamaBarang.setText(namaBarangTambahan);
            tvJenisBarang.setText(jenisPipaBarangTambahan);
            tvMerek.setText(merekBarangTambahan);
            tvSatuan.setText(satuanBarangTambahan);

        } else if (requestCode == REQUEST_ZONA && resultCode == RESULT_OK) {
            kodeZona = data.getStringExtra("kode_zona");
            ketZona = data.getStringExtra("ket_zona");
            if (kodeZona == null || ketZona == null) {
                kodeZona = "null";
                ketZona = "null";
            }
            tvKodeZona.setText(kodeZona + " - " + ketZona);
        }

//        easyImage.handleActivityResult(requestCode, resultCode, data, InputSpkActivity.this, new DefaultCallback() {
//        });

        easyImage.handleActivityResult(requestCode, resultCode, data, InputSpkActivity.this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(@NonNull Throwable throwable, @NonNull MediaSource mediaSource) {
                Toast.makeText(InputSpkActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMediaFilesPicked(@NonNull MediaFile[] mediaFiles, @NonNull MediaSource mediaSource) {
                if (REQUEST_FOTO == 1) {
                    // TODO jepret foto
                    Log.d(TAG, "onMediaFilesPickedPath: " + mediaFiles[0].getFile().getPath());
                    Log.d(TAG, "onMediaFilesPickedAbsoluthPath: " + mediaFiles[0].getFile().getAbsolutePath());
                    Log.d(TAG, "onMediaFilesPickedGetName: " + mediaFiles[0].getFile().getName());
                    Log.d(TAG, "onMediaFilesPickedGetParent: " + mediaFiles[0].getFile().getParent());

                    // TODO Compress file/image
                    try {
                        compressedImageFile1 = new Compressor(InputSpkActivity.this)
                                .setMaxHeight(640)
                                .setMaxWidth(480)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                                .compressToFile(mediaFiles[0].getFile(), "comp_1_" + mediaFiles[0].getFile().getName());
                        Log.d(TAG, "compressed: " + compressedImageFile1.getPath());
                        Glide.with(InputSpkActivity.this).load(compressedImageFile1.getPath()).override(512, 512).into(ivFoto1);

                        // TODO delete image
//                    Config.deleteFiles(mediaFiles[0].getFile().getPath(), "ImageOriginal");

                    } catch (IOException e) {
                        Log.d(TAG, "failureCOmpressed 111: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else if (REQUEST_FOTO == 2) {
                    // TODO jepret foto
                    Log.d(TAG, "onMediaFilesPickedPath 222: " + mediaFiles[0].getFile().getPath());
                    Log.d(TAG, "onMediaFilesPickedAbsoluthPath 222: " + mediaFiles[0].getFile().getAbsolutePath());
                    Log.d(TAG, "onMediaFilesPickedGetName 222: " + mediaFiles[0].getFile().getName());
                    Log.d(TAG, "onMediaFilesPickedGetParent 222: " + mediaFiles[0].getFile().getParent());

                    // TODO Compress file/image
                    try {
                        compressedImageFile2 = new Compressor(InputSpkActivity.this)
                                .setMaxHeight(640)
                                .setMaxWidth(480)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                                .compressToFile(mediaFiles[0].getFile(), "comp_2_" + mediaFiles[0].getFile().getName());
                        Log.d(TAG, "compressed 222: " + compressedImageFile2.getPath());
                        Glide.with(InputSpkActivity.this).load(compressedImageFile2.getPath()).override(512, 512).into(ivFoto2);

                        // TODO delete image
//                    Config.deleteFiles(mediaFiles[0].getFile().getPath(), "ImageOriginal");

                    } catch (IOException e) {
                        Log.d(TAG, "failureCOmpressed: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else if (REQUEST_FOTO == 3) {
                    // TODO jepret foto
                    Log.d(TAG, "onMediaFilesPickedPath 333: " + mediaFiles[0].getFile().getPath());
                    Log.d(TAG, "onMediaFilesPickedAbsoluthPath 333: " + mediaFiles[0].getFile().getAbsolutePath());
                    Log.d(TAG, "onMediaFilesPickedGetName 333: " + mediaFiles[0].getFile().getName());
                    Log.d(TAG, "onMediaFilesPickedGetParent 333: " + mediaFiles[0].getFile().getParent());

                    // TODO Compress file/image
                    try {
                        compressedImageFile3 = new Compressor(InputSpkActivity.this)
                                .setMaxHeight(640)
                                .setMaxWidth(480)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                                .compressToFile(mediaFiles[0].getFile(), "comp_3_" + mediaFiles[0].getFile().getName());
                        Log.d(TAG, "compressed 333: " + compressedImageFile3.getPath());
                        Glide.with(InputSpkActivity.this).load(compressedImageFile3.getPath()).override(512, 512).into(ivFoto3);

                        // TODO delete image
//                    Config.deleteFiles(mediaFiles[0].getFile().getPath(), "ImageOriginal");

                    } catch (IOException e) {
                        Log.d(TAG, "failureCOmpressed: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else if (REQUEST_FOTO == 4) {
                    // TODO jepret foto
                    Log.d(TAG, "onMediaFilesPickedPath 444: " + mediaFiles[0].getFile().getPath());
                    Log.d(TAG, "onMediaFilesPickedAbsoluthPath 444: " + mediaFiles[0].getFile().getAbsolutePath());
                    Log.d(TAG, "onMediaFilesPickedGetName 444: " + mediaFiles[0].getFile().getName());
                    Log.d(TAG, "onMediaFilesPickedGetParent 444: " + mediaFiles[0].getFile().getParent());

                    // TODO Compress file/image
                    try {
                        compressedImageFile4 = new Compressor(InputSpkActivity.this)
                                .setMaxHeight(640)
                                .setMaxWidth(480)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(mediaFiles[0].getFile().getParent())
                                .compressToFile(mediaFiles[0].getFile(), "comp_4_" + mediaFiles[0].getFile().getName());
                        Log.d(TAG, "compressed 444: " + compressedImageFile4.getPath());
                        Glide.with(InputSpkActivity.this).load(compressedImageFile4.getPath()).override(512, 512).into(ivFoto4);

                        // TODO delete image
//                    Config.deleteFiles(mediaFiles[0].getFile().getPath(), "ImageOriginal");

                    } catch (IOException e) {
                        Log.d(TAG, "failureCOmpressed: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCanceled(@NonNull MediaSource mediaSource) {

            }
        });

    }

    private void getListGabungan() {
        ProgressDialog progressDialog = new ProgressDialog(InputSpkActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this, Config.BASE_URL);
        apiService.listAll(token).enqueue(new Callback<ListAllGabunganRootModel>() {
            @Override
            public void onResponse(Call<ListAllGabunganRootModel> call, Response<ListAllGabunganRootModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.cancel();
                    jenisPekerjaanItems = response.body().getData().getJenisPekerjaan();
                    for (int i = 0; i < jenisPekerjaanItems.size(); i++) {
                        String kd = String.valueOf(jenisPekerjaanItems.get(i).getKd());
                        String nm_jns = jenisPekerjaanItems.get(i).getNmJns();
                        String kd_jns = jenisPekerjaanItems.get(i).getKd_jns();
                        arrayJenisPekerjaanID.add(kd);
                        arrayJenisPekerjaanString.add(nm_jns);
                        arrayJenisPekerjaanKdJns.add(kd_jns);
                    }
                    spnJenisPekerjaan.setItems(arrayJenisPekerjaanString);

                    asalItems = response.body().getData().getAsal();
                    for (int i = 0; i < asalItems.size(); i++) {
                        String kd_asal = String.valueOf(asalItems.get(i).getKdAsal());
                        String nm_asal = asalItems.get(i).getNmAsal();
                        arrayAsalId.add(kd_asal);
                        arrayAsal.add(nm_asal);
                    }
                    spnAsal.setItems(arrayAsal);

                    pekerjaanDikerjakanItems = response.body().getData().getPekerjaanDikerjakan();
                    for (int i = 0; i < pekerjaanDikerjakanItems.size(); i++) {
                        String kd = String.valueOf(pekerjaanDikerjakanItems.get(i).getKd());
                        String keterangan = pekerjaanDikerjakanItems.get(i).getKeterangan();
                        arrayPekerjaanYgHarusDikerjakanId.add(kd);
                        arrayPekerjaanYgHarusDikerjakan.add(keterangan);
                    }
                    spnPekerjaanYangHrsDikerjakan.setItems(arrayPekerjaanYgHarusDikerjakan);

                    tenagaItems = response.body().getData().getTenaga();
                    for (int i = 0; i < tenagaItems.size(); i++) {
                        String kode = String.valueOf(tenagaItems.get(i).getKode());
                        String ket = tenagaItems.get(i).getKet();
                        arraytenagaId.add(kode);
                        arrayTenaga.add(ket);
                    }
                    spnTenaga.setItems(arrayTenaga);

                    statusPenyelesaianItems = response.body().getData().getStatusPenyelesaian();
                    for (int i = 0; i < statusPenyelesaianItems.size(); i++) {
                        String kode = String.valueOf(statusPenyelesaianItems.get(i).getKode());
                        String status = statusPenyelesaianItems.get(i).getStatus();
                        arrayStatusPenyelesaianID.add(kode);
                        arrayStatusPenyelesaian.add(status);

                        arrayStatusPenyelesaianKodeCC.add(statusPenyelesaianItems.get(i).getKode_cc());
                        arrayStatusPenyelesaianKetCC.add(statusPenyelesaianItems.get(i).getKet());
                    }
                    spnStatusPenyelesaian.setItems(arrayStatusPenyelesaian);

                } else {
                    progressDialog.cancel();
                    Toast.makeText(InputSpkActivity.this, "Mengambil data gagal, ulangi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListAllGabunganRootModel> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(InputSpkActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        divTglPelaksanaan = findViewById(R.id.div_tgl_pelaksanaan);
        tvTglPelaksanaan = findViewById(R.id.tv_tgl_pelaksanaan);
        divTglSpk = findViewById(R.id.div_tgl_spk);
        tvTglSpk = findViewById(R.id.tv_tgl_spk);
        spnJenisPekerjaan = findViewById(R.id.spn_jenis_pekerjaan);
        inputNoSpk = findViewById(R.id.input_no_spk);
        inputAlamatPerbaikan = findViewById(R.id.input_alamat_perbaikan);
        divKodeZona = findViewById(R.id.div_kode_zona);
        tvKodeZona = findViewById(R.id.tv_kode_zona);
        tvSubagian = findViewById(R.id.tv_subagian);
        divPengawas = findViewById(R.id.div_pengawas);
        tvPengawas = findViewById(R.id.rv_pengawas);
        divKasub = findViewById(R.id.div_kasub);
        tvKasub = findViewById(R.id.tv_kasub);
        inputNolangg = findViewById(R.id.input_nolangg);
        btnCariNolangg = findViewById(R.id.btn_cari_nolangg);
        cvNolangg = findViewById(R.id.cv_nolangg);
        tvNamaPelanggan = findViewById(R.id.tv_nama_pelanggan);
        tvCabang = findViewById(R.id.tv_cabang);
        tvAlamat = findViewById(R.id.tv_alamat);
        spnPekerjaanYangHrsDikerjakan = findViewById(R.id.spn_pekerjaan_yang_hrs_dikerjakan);
        edtPekerjaanLainnya = findViewById(R.id.edt_pekerjaan_lainnya);
        btnTambahPekerjaan = findViewById(R.id.btn_tambah_pekerjaan);
        btnHapusPekerjaan = findViewById(R.id.btn_hapus_pekerjaan);
        rvPekerjaanYangHrsDikerjakan = findViewById(R.id.rv_pekerjaan_yang_hrs_dikerjakan);
        divJamMulai = findViewById(R.id.div_jam_mulai);
        tvJamMulai = findViewById(R.id.tv_jam_mulai);
        divJamSelesai = findViewById(R.id.div_jam_selesai);
        tvJamSelesai = findViewById(R.id.tv_jam_selesai);
        divPekerjaan = findViewById(R.id.div_pekerjaan);
        tvBarangYangDikerjakan = findViewById(R.id.tv_barang_yang_dikerjakan);
        inputTekanan = findViewById(R.id.input_tekanan);
        inputUraian = findViewById(R.id.input_uraian);
        spnTenaga = findViewById(R.id.spn_tenaga);
        edtTenagaLainnya = findViewById(R.id.edt_tenaga_lainnya);
        inputPerkiraanTka = findViewById(R.id.input_perkiraan_tka);
        spnStatusPenyelesaian = findViewById(R.id.spn_status_penyelesaian);
        divSpkSebelumnya = findViewById(R.id.div_spk_sebelumnya);
        tvSpkSebelumnyaDanTanggal = findViewById(R.id.tv_spk_sebelumnya_dan_tanggal);
        divBarang = findViewById(R.id.div_barang);
        cvBarang = findViewById(R.id.cv_barang);
        tvNamaBarang = findViewById(R.id.tv_nama_barang);
        tvJenisBarang = findViewById(R.id.tv_jenis_barang);
        tvMerek = findViewById(R.id.tv_merek);
        edtJumlahBarang = findViewById(R.id.edt_jumlah_barang);
        tvSatuan = findViewById(R.id.tv_satuan);
        btnTambahBarang = findViewById(R.id.btn_tambah_barang);
        btnHapusBarang = findViewById(R.id.btn_hapus_barang);
        rvBarang = findViewById(R.id.rv_barang);
        btnSimpan = findViewById(R.id.btn_simpan);
        spnAsal = findViewById(R.id.spn_asal);
        adView = findViewById(R.id.adView);
        cvFoto1 = findViewById(R.id.cv_foto1);
        ivFoto1 = findViewById(R.id.iv_foto1);
        cvFoto2 = findViewById(R.id.cv_foto2);
        ivFoto2 = findViewById(R.id.iv_foto2);
        cvFoto3 = findViewById(R.id.cv_foto3);
        ivFoto3 = findViewById(R.id.iv_foto3);
        cvFoto4 = findViewById(R.id.cv_foto4);
        ivFoto4 = findViewById(R.id.iv_foto4);
        inputNamaPelapor = findViewById(R.id.input_nama_pelapor);
        inputAlamatPelapor = findViewById(R.id.input_alamat_pelapor);
        tvKlikMaps = findViewById(R.id.tv_klik_maps);
        inputNoAduanCc = findViewById(R.id.input_no_aduan_cc);
        divNoAduanCc = findViewById(R.id.div_no_aduan_cc);
        rgLembur = findViewById(R.id.rg_lembur);
        rbYaLembur = findViewById(R.id.rb_ya_lembur);
        rbTidakLembur = findViewById(R.id.rb_tidak_lembur);
        divVerifikator = findViewById(R.id.div_verifikator);
        tvVerifikator = findViewById(R.id.tv_verifikator);
        divNoTelpPelaporCc = findViewById(R.id.div_no_pelapor_cc);
        tvNoPelaporCc = findViewById(R.id.tv_no_pelapor_cc);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divNoPelaporCc = findViewById(R.id.div_no_pelapor_cc);
        btnGunakanLokasiMaps = findViewById(R.id.btn_gunakan_lokasi_maps);
        divDeleteFoto3 = findViewById(R.id.div_delete_foto_3);
        divDeleteFoto4 = findViewById(R.id.div_delete_foto_4);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (flag.equals("jammulai")) {
            String time = hourOfDay + ":" + minute + ":" + second;
            jamMulai = time;
            tvJamMulai.setText(jamMulai);
        } else {
            String time = hourOfDay + ":" + minute + ":" + second;
            jamSelesai = time;
            tvJamSelesai.setText(jamSelesai);
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (flag.equals("pelaksanaan")) {
//            String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            tglPelaksanaan = date;
            tvTglPelaksanaan.setText(tglPelaksanaan);
        } else {
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            tglSpk = date;
            tvTglSpk.setText(tglSpk);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MaterialDialog mDialog = new MaterialDialog.Builder(InputSpkActivity.this)
                .setTitle("Kembali ke halaman utama?")
                .setCancelable(false)
                .setNegativeButton("Tidak", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton("Ya", (dialogInterface, which) -> {
                    InputSpkActivity.this.finish();
                })
                .build();

        mDialog.show();
    }
}