package co.id.pdamkotasmg.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.checkPelangganSudahDibaca.CheckPelangganRootModel;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityDetailRiwayatPembacaMeterBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRiwayatPembacaMeterActivity extends AppCompatActivity {

    private ActivityDetailRiwayatPembacaMeterBinding binding;
//    private ContentHeaderBinding contentHeaderBinding;

    private ProgressDialog progressDialog;

    private String token;
    private String nolangg;
    private String codeinputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailRiwayatPembacaMeterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
//        contentHeaderBinding = ContentHeaderBinding.inflate(getLayoutInflater());
//        View contentRoot = contentHeaderBinding.getRoot();
        setContentView(root);
//        setContentView(contentRoot);

        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        nolangg = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG);

//        contentHeaderBinding.tvHeaderJudul.setText("Detail Riwayat");
//        contentHeaderBinding.ivHeaderBackArrow.setOnClickListener(view -> DetailRiwayatPembacaMeterActivity.this.finish());
//        contentHeaderBinding.ivHeaderInfo.setOnClickListener(view -> Toast.makeText(this, "Detail Riwayat Pembaca Meter", Toast.LENGTH_SHORT).show());

        progressDialog = new ProgressDialog(DetailRiwayatPembacaMeterActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setCancelable(false);

        getDataDetailRwiayat();

        binding.cvCallIav.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone=6283838191709&text=Halo%20iav,%20ada%20kesalahan%20data%20dari%20HPM,%20tolong%20di%20cek.%20Sertakan%20nolangg,%20periode,%20dan%20bendel%20nya......"));
            startActivity(browserIntent);
        });

        binding.btnKoreksi.setOnClickListener(view -> {
            codeinputData = "5"; // koreksi
            Intent intent = new Intent(DetailRiwayatPembacaMeterActivity.this, PembacaMeterActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA, codeinputData);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, nolangg);
            startActivity(intent);
        });

        binding.btnUbahData.setOnClickListener(view -> {
            codeinputData = "6"; // verifikasi tapi ditolak
            Intent intent = new Intent(DetailRiwayatPembacaMeterActivity.this, PembacaMeterActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA, codeinputData);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, nolangg);
            startActivity(intent);
        });

    }

    private void getDataDetailRwiayat() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(DetailRiwayatPembacaMeterActivity.this);
        apiService.getCheckPelangganDetail(token, nolangg)
                .enqueue(new Callback<CheckPelangganRootModel>() {
                    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                    @Override
                    public void onResponse(Call<CheckPelangganRootModel> call, Response<CheckPelangganRootModel> response) {
                        if (response.isSuccessful()) {
                            binding.tvTitleTransfer.setText("Status | Periode " + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getPeriode());

                            String statusTransferKode = response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getKode();
                            binding.tvNmTransfer.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlDtBaca().getNmStatus());
                            if (statusTransferKode.contains("0")) {
                                binding.ivTrfKosong.setVisibility(View.VISIBLE);
                            } else if (statusTransferKode.contains("1")) {
                                binding.ivTrfSiap.setVisibility(View.VISIBLE);
                                binding.ivTrfVerifikasi.setVisibility(View.GONE);
                                binding.ivTrfTransfer.setVisibility(View.GONE);
                                binding.ivTrfKoreksi.setVisibility(View.GONE);
                                binding.btnKoreksi.setVisibility(View.GONE);
                            } else if (statusTransferKode.contains("2")) {
                                binding.ivTrfVerifikasi.setVisibility(View.VISIBLE);
                                binding.ivTrfSiap.setVisibility(View.GONE);
                                binding.ivTrfTransfer.setVisibility(View.GONE);
                                binding.ivTrfKoreksi.setVisibility(View.GONE);
                                binding.btnKoreksi.setVisibility(View.GONE);
                            } else if (statusTransferKode.contains("3")) {
                                binding.ivTrfTransfer.setVisibility(View.VISIBLE);
                            } else if (statusTransferKode.contains("4")) {
                                binding.ivTrfPde.setVisibility(View.VISIBLE);
                            } else if (statusTransferKode.contains("5")) {
                                binding.ivTrfKoreksi.setVisibility(View.VISIBLE);
                                binding.btnKoreksi.setVisibility(View.VISIBLE);
                            }
                            binding.tvNolanggDism.setText(response.body().getData().get(0).getNolangg() + " | " + response.body().getData().get(0).getDism());

                            String statusPlg = response.body().getData().get(0).getRlStatusPelanggan().getKode();
                            if (statusPlg.contains("2")){
                                binding.tvStatusPlg.setText("Aktif");
                                binding.tvStatusPlg.setBackgroundColor(com.pdamkotasmg.goodday.R.color.greenGojek);
                                binding.tvStatusPlg.setTextColor(Color.WHITE);
                            } else {
                                binding.tvStatusPlg.setText("Tutup/Blokir");
                                binding.tvStatusPlg.setBackgroundColor(com.pdamkotasmg.goodday.R.color.red);
                                binding.tvStatusPlg.setTextColor(Color.WHITE);
                            }

                            binding.tvNmPlg.setText(response.body().getData().get(0).getNama());
                            binding.tvAlamatPlg.setText(response.body().getData().get(0).getAlamat());
                            binding.tvTarifPlg.setText(response.body().getData().get(0).getRlTarif().getNmTarif() + " (" + response.body().getData().get(0).getRlTarif().getNmGol() + ")");
                            binding.tvNmMtrNoMtr.setText(response.body().getData().get(0).getMerek() + " | " + response.body().getData().get(0).getNomormtr());
                            binding.tvSumur.setText(response.body().getData().get(0).getSumur());
                            binding.tvCabang.setText(response.body().getData().get(0).getRlCabang().getNmCabang());


                            binding.tvPeriodeBlnLalu.setText(response.body().getData().get(0).getRlTrbaca().get(0).getPeriode());
                            binding.tvTglJamBacaBlnLalu.setText(response.body().getData().get(0).getRlTrbaca().get(0).getTglBaca() + " | " + response.body().getData().get(0).getRlTrbaca().get(0).getJamBaca());
                            binding.tvPemakaianBlnLalu.setText(response.body().getData().get(0).getRlTrbaca().get(0).getKini() + " - " + response.body().getData().get(0).getRlTrbaca().get(0).getM3() + "m3");
                            binding.tvPetugasBlnLalu.setText(response.body().getData().get(0).getRlTrbaca().get(0).getRlPetugas().getNmPetugas());
                            Glide.with(DetailRiwayatPembacaMeterActivity.this)
                                    .load(response.body().getData().get(0).getRlTrbaca().get(0).getFile())
                                    .override(512, 512)
                                    .error(R.drawable.im_good_day)
                                    .into(binding.ivFotoMeterBlnLalu);


                            binding.tvPeriodeBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getPeriode());
                            binding.tvTglJamBacaBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getTglBaca() + " | "
                                    + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getJamBaca());
                            binding.tvPemakaianBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getKini() + " - "
                                    + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getM3() + "m3");
                            binding.tvDataDiperbaruiKeBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getKe());
                            binding.tvStMeter.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlStMeter().getStatus());
                            binding.tvKeterangan.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getKt());

                            binding.tvPetugasBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlPetugas().getNmPetugas());


                            String userVer = String.valueOf(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlUserVer());
                            if (userVer.contains("null")) {
                                userVer = "null";
                                binding.tvVerfikatorJamWaktuBlnSekarang.setText(userVer
                                        + " | " + userVer + " - "
                                        + userVer);
                            } else {
                                binding.tvVerfikatorJamWaktuBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlUserVer().getNmPetugas()
                                        + " | " + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getTglVer() + " - "
                                        + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getJamVer());

                            }

                            String userPindahData = String.valueOf(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlUserTransfer());
                            if (userPindahData.contains("null")) {
                                userPindahData = "null";
                                binding.tvPindahDataBlnSekarang.setText(userPindahData
                                        + " | " + userPindahData);
                            } else {
                                binding.tvPindahDataBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlUserTransfer().getNama()
                                        + " | " + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getTglTransfer());
                            }

                            String filePeriodeSekarang = response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getFile();
                            if (filePeriodeSekarang.contains("null")) {
                                filePeriodeSekarang = "null";
                                Glide.with(DetailRiwayatPembacaMeterActivity.this)
                                        .load(filePeriodeSekarang)
                                        .override(512, 512)
                                        .error(R.drawable.im_good_day)
                                        .into(binding.ivFotoMeterBlnSekarang);
                            } else {
                                Glide.with(DetailRiwayatPembacaMeterActivity.this)
                                        .load(Config.BASE_URL_IMAGE_HANDLER + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getFile())
                                        .override(512, 512)
                                        .error(R.drawable.im_good_day)
                                        .into(binding.ivFotoMeterBlnSekarang);
                            }

                            String statusVerifikasi = response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getStver();
                            if (statusVerifikasi.contains("2")) {
                                binding.tvStatusVerifikasi.setText("DITOLAK / CU");
                                binding.tvStatusVerifikasi.setTextColor(Color.parseColor("#C30000"));
                                binding.btnUbahData.setVisibility(View.VISIBLE);
                            } else if (statusVerifikasi.contains("3")) {
                                binding.tvStatusVerifikasi.setText("DITOLAK FOTO TDK ADA");
                                binding.tvStatusVerifikasi.setTextColor(Color.parseColor("#C30000"));
                                binding.btnUbahData.setVisibility(View.VISIBLE);
                            } else if (statusVerifikasi.contains("4")) {
                                binding.tvStatusVerifikasi.setText("ST PLG TUTUP");
                                binding.tvStatusVerifikasi.setTextColor(Color.parseColor("#C30000"));
                                binding.btnUbahData.setVisibility(View.VISIBLE);
                            } else if (statusVerifikasi.contains("0")) {
                                binding.tvStatusVerifikasi.setText("BELUM VERIFIKASI");
                                binding.tvStatusVerifikasi.setTextColor(Color.parseColor("#D5840D"));
                                binding.btnUbahData.setVisibility(View.GONE);
                            } else {
                                binding.tvStatusVerifikasi.setText("DISETUJUI");
                                binding.btnUbahData.setVisibility(View.GONE);
                                binding.tvStatusVerifikasi.setTextColor(Color.parseColor("#31B057"));
                            }

                            progressDialog.cancel();


                        }
                    }

                    @Override
                    public void onFailure(Call<CheckPelangganRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(DetailRiwayatPembacaMeterActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataDetailRwiayat();
    }
}