package com.pdamkotasmg.goodday.fitur.profil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.perangkat.PerangkatActivity;
import com.pdamkotasmg.goodday.fitur.profil.controller.ProfileController;
import com.pdamkotasmg.goodday.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private final String TAG = "debug";
    private ProfileController profileController;

    //token
    private String access_token;
    private String token_type;
    private String avatar;

    //user
    private String npp_profile;
    private String name;

    //rlPegawai
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

    private String urlLogo;
    private String headerProfil;

    private ImageView imageView2;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private LinearLayout divHeaderName;
    private TextView tvProfileName;
    private TextView tvProfileJabatan;
    private CircleImageView ciProfileImage;
    private ScrollView scrollView3;
    private TextView tvProfileNpp;
    private TextView tvPrfileAlamatRtRwKelurahanKecamatanKota;
    private TextView tvProfileGender;
    private TextView tvProfileTtl;
    private TextView tvProfileStatusPegawai;
    private TextView tvProfileReligion;
    private TextView tvProfileGolongan;
    private TextView tvProfileSubSatker;
    private TextView tvProfileSatker;
    private CardView cvKlikPengaturan;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        initView();
        profileController = new ProfileController();
        tvHeaderJudul.setText("Profil");
        ivHeaderInfo.setVisibility(View.GONE);
        ivHeaderBackArrow.setOnClickListener(v -> {
            ProfileActivity.this.finish();
        });
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        access_token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        npp_profile = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        avatar = sharedPreferences.getString(Config.SHARED_AVATAR, "");
        alamat = sharedPreferences.getString(Config.SHARED_ALAMAT, "");
        rt = sharedPreferences.getString(Config.SHARED_RT, "");
        rw = sharedPreferences.getString(Config.SHARED_RW, "");
        kelurahan = sharedPreferences.getString(Config.SHARED_KELURAHAN, "");
        kecamatan = sharedPreferences.getString(Config.SHARED_KECAMATAN, "");
        kota = sharedPreferences.getString(Config.SHARED_KOTA, "");
        jenis_kel = sharedPreferences.getString(Config.SHARED_JENIS_KEL, "");
        tmpt_lahir = sharedPreferences.getString(Config.SHARED_TMPT_LAHIR, "");
        tgl_lahir = sharedPreferences.getString(Config.SHARED_TGL_LAHIR, "");
        tgl_masuk = sharedPreferences.getString(Config.SHARED_TGL_MASUK, "");
        satus_peg = sharedPreferences.getString(Config.SHARED_SATUS_PEG, "");
        agama = sharedPreferences.getString(Config.SHARED_AGAMA, "");
        namasusi = sharedPreferences.getString(Config.SHARED_NAMASUSI, "");
        pktgol = sharedPreferences.getString(Config.SHARED_PKTGOL, "");
        subsatker = sharedPreferences.getString(Config.SHARED_SUBSATKER, "");
        satker = sharedPreferences.getString(Config.SHARED_SATKER, "");
        jabatan = sharedPreferences.getString(Config.SHARED_JABATAN, "");
        ket = sharedPreferences.getString(Config.SHARED_KET, "");
        tlp = sharedPreferences.getString(Config.SHARED_TLP, "");
        pek = sharedPreferences.getString(Config.SHARED_PEK, "");
        st_data = sharedPreferences.getString(Config.SHARED_ST_DATA, "");
        satker_formatted = sharedPreferences.getString(Config.SHARED_SATKER_FORMATTED, "");
        subsatker_formatted = sharedPreferences.getString(Config.SHARED_SUBSATKER_FORMATTED, "");
        urlLogo = sharedPreferences.getString(Config.SHARED_URL_LOGO, "");
        headerProfil = sharedPreferences.getString(Config.SHARED_HEADER_PROFIL, "");

        tvProfileName.setText(name);
        tvProfileJabatan.setText(jabatan);
        tvProfileNpp.setText(npp_profile);
        tvPrfileAlamatRtRwKelurahanKecamatanKota.setText(alamat + ", Rt" + rt + ", Rw " + rw + ", Kel. " + kelurahan + ", Kec. " + kecamatan + ", " + kota);
        tvProfileGender.setText(jenis_kel);
        tvProfileTtl.setText(tmpt_lahir + ", " + tgl_lahir);
        tvProfileStatusPegawai.setText(satus_peg);
        tvProfileReligion.setText(agama);
        tvProfileGolongan.setText(pktgol);
        tvProfileSubSatker.setText(subsatker);
        tvProfileSatker.setText(satker);
        Glide.with(ProfileActivity.this).load(urlLogo).error(R.drawable.im_no_available).into(ciProfileImage);
        Glide.with(ProfileActivity.this).load(headerProfil).error(R.drawable.im_no_available).into(imageView2);

        cvKlikPengaturan.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialogProfile = new BottomSheetDialog(ProfileActivity.this);
            bottomSheetDialogProfile.setContentView(R.layout.bottom_sheet_dialog_pengaturan);

            TextView tvTutupDialog = bottomSheetDialogProfile.findViewById(R.id.tv_tutup_dialog);
            LinearLayout divPerangkatTerhubung = bottomSheetDialogProfile.findViewById(R.id.div_perangkat_terhubung);
            LinearLayout divGantiPassword = bottomSheetDialogProfile.findViewById(R.id.div_ganti_password);
            LinearLayout divLogout = bottomSheetDialogProfile.findViewById(R.id.div_logout);
            LinearLayout divQrCode = bottomSheetDialogProfile.findViewById(R.id.div_qr_code);
            LinearLayout divDigitalSignature = bottomSheetDialogProfile.findViewById(R.id.div_digital_signature);

            tvTutupDialog.setOnClickListener(v1 -> {
                bottomSheetDialogProfile.cancel();
            });

            divPerangkatTerhubung.setOnClickListener(v1 -> {
                startActivity(new Intent(getApplicationContext(), PerangkatActivity.class));
            });

            divLogout.setOnClickListener(v1 -> {
                MaterialDialog mDialog = new MaterialDialog.Builder(ProfileActivity.this)
                        .setMessage("Yakin mau keluar?")
                        .setAnimation("lt_logout.json")
                        .setCancelable(false)
                        .setNegativeButton("Gak", (dialogInterface, which) -> dialogInterface.dismiss())
                        .setPositiveButton("Iya", (dialogInterface, which) -> {
                            profileController.logout(ProfileActivity.this);
                        })
                        .build();

                // Show Dialog
                mDialog.show();
            });

            divQrCode.setOnClickListener(v1 -> {
                editor.putString(Config.SHARED_STATUS_ABSENSI, "qrcode");
                editor.apply();
                Intent intent = new Intent(ProfileActivity.this, QrCodeActivity.class);
                intent.putExtra(Config.BUNDLE_NPP, npp_profile);
                startActivity(intent);
            });

            divGantiPassword.setOnClickListener(v1 -> {
                final BottomSheetDialog bottomSheetDialogProfileGantiPassword = new BottomSheetDialog(ProfileActivity.this);
                bottomSheetDialogProfileGantiPassword.setContentView(R.layout.bottom_sheet_dialog_ganti_password);

                TextView tvTutupDialogs = bottomSheetDialogProfileGantiPassword.findViewById(R.id.tv_tutup_dialog);
                EditText edtPasswordBaru = bottomSheetDialogProfileGantiPassword.findViewById(R.id.edt_password_baru);
                EditText edtPasswordBaruKonfirmasi = bottomSheetDialogProfileGantiPassword.findViewById(R.id.edt_password_baru_konfirmasi);
                Button btnKirim = bottomSheetDialogProfileGantiPassword.findViewById(R.id.btn_kirim);

                tvTutupDialogs.setOnClickListener(v2 -> {
                    bottomSheetDialogProfileGantiPassword.cancel();
                });

                btnKirim.setOnClickListener(v2 -> {
                    if (!edtPasswordBaru.getText().toString().trim().equals(edtPasswordBaruKonfirmasi.getText().toString().trim())) {
                        Toast.makeText(ProfileActivity.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                    } else {
                        ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
                        progressDialog.setMessage("Mohon Tunggu...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        ApiService apiService = ApiConfig.getApiService(this);
                        apiService.updatePassword(access_token, edtPasswordBaru.getText().toString().trim(), edtPasswordBaruKonfirmasi.getText().toString().trim())
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        progressDialog.cancel();
                                        if (response.isSuccessful()) {
                                            String dataRes = response.message();
                                            Log.d(TAG, "dataRes: " + dataRes);
                                            if (dataRes.equalsIgnoreCase("OK")) {
                                                bottomSheetDialogProfileGantiPassword.cancel();
                                                Toast.makeText(ProfileActivity.this, "Sukses ganti password", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ProfileActivity.this, "Gagal ganti password", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(ProfileActivity.this, "Error " + response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        progressDialog.cancel();
                                        Toast.makeText(ProfileActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                });
                bottomSheetDialogProfileGantiPassword.show();
            });

            divDigitalSignature.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), DigitalSignatureActivity.class));
            });

            bottomSheetDialogProfile.show();


        });

    }

    private void initView() {
        imageView2 = findViewById(R.id.imageView2);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divHeaderName = findViewById(R.id.div_header_name);
        tvProfileName = findViewById(R.id.tv_profile_name);
        tvProfileJabatan = findViewById(R.id.tv_profile_jabatan);
        ciProfileImage = findViewById(R.id.ci_profile_image);
        scrollView3 = findViewById(R.id.scrollView3);
        tvProfileNpp = findViewById(R.id.tv_profile_npp);
        tvPrfileAlamatRtRwKelurahanKecamatanKota = findViewById(R.id.tv_prfile_alamat_rt_rw_kelurahan_kecamatan_kota);
        tvProfileGender = findViewById(R.id.tv_profile_gender);
        tvProfileTtl = findViewById(R.id.tv_profile_ttl);
        tvProfileStatusPegawai = findViewById(R.id.tv_profile_status_pegawai);
        tvProfileReligion = findViewById(R.id.tv_profile_religion);
        tvProfileGolongan = findViewById(R.id.tv_profile_golongan);
        tvProfileSubSatker = findViewById(R.id.tv_profile_sub_satker);
        tvProfileSatker = findViewById(R.id.tv_profile_satker);
        cvKlikPengaturan = findViewById(R.id.cv_klik_pengaturan);

    }
}