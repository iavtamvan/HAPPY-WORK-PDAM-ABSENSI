package id.pdamkotasmg.pekerjaan_teknik_feature.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import id.pdamkotasmg.pekerjaan_teknik_feature.R;
import id.pdamkotasmg.pekerjaan_teknik_feature.activity.home.HomeSPKActivity;
import id.pdamkotasmg.pekerjaan_teknik_feature.api.ApiConfig;
import id.pdamkotasmg.pekerjaan_teknik_feature.api.ApiService;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.akun.login.Data;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.akun.login.LoginRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.utils.Config;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSPKActivity extends AppCompatActivity {
    private static final String TAG = "debug";
    private static final int RC_CAMERA_AND_LOCATION = 1;


    private Data dataLogin;

    private String access_token;
    private String token_type;
    private String npp_profile;
    private String name;
    private String avatar;
    private String getHwid;

    private String alamat;
    private String rt;
    private String rw;
    private String kelurahan;
    private String kecamatan;
    private String kota;
    private String jenis_kel;
    private String tmpt_lahir;
    private String tgl_lahir;
    private String tgl_masuk;
    private String satus_peg;
    private String agama;
    private String namasusi;
    private String pktgol;
    private String subsatker;
    private String satker;
    private String jabatan;
    private String ket;
    private String tlp;
    private String pek;
    private String st_data;
    private String satker_formatted;
    private String subsatker_formatted;

    private EditText edtNpp;
    private EditText edtPassword;
    private Button btnMasuk;
    private CardView cvKlikLupaPassword;

    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_spk);
        getSupportActionBar().hide();
        initView();

        methodRequiresTwoPermission();

        getHwid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        btnMasuk.setOnClickListener(v -> {
            if (edtNpp.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Isi akun terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                login();
            }
        });

//        cvKlikLupaPassword.setOnClickListener(v -> {
//            if (edtNpp.getText().toString().isEmpty()) {
//                Toast.makeText(LoginActivity.this, "Isi NPP dahulu", Toast.LENGTH_SHORT).show();
//            } else {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=6283838191709&text=Halo%20iav,%20password%20dari%20npp%20" + edtNpp.getText().toString()));
//                startActivity(browserIntent);
//            }
//        });

    }

    private void login() {
        ProgressDialog progressDialog = new ProgressDialog(LoginSPKActivity.this);
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(Config.BASE_URL_PORTAL_PEGAWAI);
        apiService.login(edtNpp.getText().toString().trim(), edtPassword.getText().toString().trim(), getHwid)
                .enqueue(new Callback<LoginRootModel>() {
                    @Override
                    public void onResponse(Call<LoginRootModel> call, Response<LoginRootModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.message());
                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            dataLogin = response.body().getData();

                            access_token = dataLogin.getAccessToken();
                            Log.d("debug", "token: " + access_token);
                            token_type = dataLogin.getTokenType();

                            npp_profile = dataLogin.getUser().getNpp();
                            name = dataLogin.getUser().getName();
                            avatar = dataLogin.getUser().getAvatar();

                            alamat = dataLogin.getUser().getRlPegawai().getAlamat();
                            rt = dataLogin.getUser().getRlPegawai().getRt();
                            rw = dataLogin.getUser().getRlPegawai().getRw();
                            kelurahan = dataLogin.getUser().getRlPegawai().getKelurahan();
                            kecamatan = dataLogin.getUser().getRlPegawai().getKecamatan();
                            kota = dataLogin.getUser().getRlPegawai().getKota();
                            jenis_kel = dataLogin.getUser().getRlPegawai().getJenisKel();
                            tmpt_lahir = dataLogin.getUser().getRlPegawai().getTmptLahir();
                            tgl_lahir = dataLogin.getUser().getRlPegawai().getTglLahir();
                            tgl_masuk = dataLogin.getUser().getRlPegawai().getTglMasuk();
                            satus_peg = dataLogin.getUser().getRlPegawai().getSatusPeg();
                            agama = dataLogin.getUser().getRlPegawai().getAgama();
                            namasusi = dataLogin.getUser().getRlPegawai().getNamasusi();
                            pktgol = dataLogin.getUser().getRlPegawai().getPktgol();
                            subsatker = dataLogin.getUser().getRlPegawai().getSubsatker();
                            satker = dataLogin.getUser().getRlPegawai().getSatker();
                            jabatan = dataLogin.getUser().getRlPegawai().getJabatan();
                            ket = dataLogin.getUser().getRlPegawai().getKet();
                            tlp = dataLogin.getUser().getRlPegawai().getTlp();
                            pek = dataLogin.getUser().getRlPegawai().getPek();
                            st_data = dataLogin.getUser().getRlPegawai().getStData();
                            satker_formatted = dataLogin.getUser().getRlPegawai().getSatkerFormatted();
                            subsatker_formatted = dataLogin.getUser().getRlPegawai().getSubsatkerFormatted();

                            Log.d(TAG, "\n ====== Respon AKUN ======= \n" +
                                    "alamat : " + alamat + " \n" +
                                    "rt : " + rt + " \n" +
                                    "rw : " + rw + " \n" +
                                    "kelurahan : " + kelurahan + " \n" +
                                    "kecamatan : " + kecamatan + " \n" +
                                    "kota : " + kota + " \n" +
                                    "jenis_kel : " + jenis_kel + " \n" +
                                    "tmpt_lahir : " + tmpt_lahir + " \n" +
                                    "tgl_lahir : " + tgl_lahir + " \n" +
                                    "tgl_masuk : " + tgl_masuk + " \n" +
                                    "satus_peg : " + satus_peg + " \n" +
                                    "agama : " + agama + " \n" +
                                    "namasusi : " + namasusi + " \n" +
                                    "pktgol : " + pktgol + " \n" +
                                    "subsatker : " + subsatker + " \n" +
                                    "satker : " + satker + " \n" +
                                    "jabatan : " + jabatan + " \n" +
                                    "ket : " + ket + " \n" +
                                    "tlp : " + tlp + " \n" +
                                    "pek : " + pek + " \n" +
                                    "st_data : " + st_data + " \n" +
                                    "satker_formatted : " + satker_formatted + " \n" +
                                    "subsatker_formatted : " + subsatker_formatted);

                            editor.putString(Config.SHARED_ACCESS_TOKEN, token_type + " " + access_token);
                            editor.putString(Config.SHARED_NPP_PROFILE, npp_profile);
                            editor.putString(Config.SHARED_NAME, name);
                            editor.putString(Config.SHARED_AVATAR, avatar);

                            editor.putString(Config.SHARED_ALAMAT, alamat);
                            editor.putString(Config.SHARED_RT, rt);
                            editor.putString(Config.SHARED_RW, rw);
                            editor.putString(Config.SHARED_KELURAHAN, kelurahan);
                            editor.putString(Config.SHARED_KECAMATAN, kecamatan);
                            editor.putString(Config.SHARED_KOTA, kota);
                            editor.putString(Config.SHARED_JENIS_KEL, jenis_kel);
                            editor.putString(Config.SHARED_TMPT_LAHIR, tmpt_lahir);
                            editor.putString(Config.SHARED_TGL_LAHIR, tgl_lahir);
                            editor.putString(Config.SHARED_TGL_MASUK, tgl_masuk);
                            editor.putString(Config.SHARED_SATUS_PEG, satus_peg);
                            editor.putString(Config.SHARED_AGAMA, agama);
                            editor.putString(Config.SHARED_NAMASUSI, namasusi);
                            editor.putString(Config.SHARED_PKTGOL, pktgol);
                            editor.putString(Config.SHARED_SUBSATKER, subsatker);
                            editor.putString(Config.SHARED_SATKER, satker);
                            editor.putString(Config.SHARED_JABATAN, jabatan);
                            editor.putString(Config.SHARED_KET, ket);
                            editor.putString(Config.SHARED_TLP, tlp);
                            editor.putString(Config.SHARED_PEK, pek);
                            editor.putString(Config.SHARED_ST_DATA, st_data);
                            editor.putString(Config.SHARED_SATKER_FORMATTED, satker_formatted);
                            editor.putString(Config.SHARED_SUBSATKER_FORMATTED, subsatker_formatted);

                            editor.apply();
                            progressDialog.cancel();
                            finishAffinity();
                            startActivity(new Intent(getApplicationContext(), HomeSPKActivity.class));
                        } else {
                            progressDialog.cancel();
                            Toast.makeText(LoginSPKActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(LoginSPKActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initView() {
        edtNpp = findViewById(R.id.edt_npp);
        edtPassword = findViewById(R.id.edt_password);
        btnMasuk = findViewById(R.id.btn_masuk);
        cvKlikLupaPassword = findViewById(R.id.cv_klik_lupa_password);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA
                , Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_WIFI_STATE
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.WRITE_SECURE_SETTINGS
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(LoginSPKActivity.this, perms)) {
            // Already have permission, do the thing
            Log.d(TAG, "methodRequiresTwoPermission: Sukses");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }
}