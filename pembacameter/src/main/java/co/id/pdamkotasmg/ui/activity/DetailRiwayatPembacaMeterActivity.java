package co.id.pdamkotasmg.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

    private String token;
    private String nolangg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailRiwayatPembacaMeterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        nolangg = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG);

        getDataDetailRwiayat();

    }

    private void getDataDetailRwiayat() {
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
                            } else if (statusTransferKode.contains("2")) {
                                binding.ivTrfVerifikasi.setVisibility(View.VISIBLE);
                            } else if (statusTransferKode.contains("3")) {
                                binding.ivTrfTransfer.setVisibility(View.VISIBLE);
                            } else if (statusTransferKode.contains("4")) {
                                binding.ivTrfPde.setVisibility(View.VISIBLE);
                            } else if (statusTransferKode.contains("5")) {
                                binding.ivTrfKoreksi.setVisibility(View.VISIBLE);
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
                            binding.tvPemakaianBlnLalu.setText(response.body().getData().get(0).getRlTrbaca().get(0).getKini() + " - " + response.body().getData().get(0).getRlTrbaca().get(0).getM3());
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
                                    + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getM3());
                            binding.tvDataDiperbaruiKeBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getKe());
                            binding.tvPetugasBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlPetugas().getNmPetugas());


                            String userVer = String.valueOf(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlUserVer());
                            if (userVer.contains("null")) {
                                userVer = "null";
                                binding.tvVerfikatorJamWaktuBlnSekarang.setText(userVer
                                        + " | " + userVer + " - "
                                        + userVer);
                                Toast.makeText(DetailRiwayatPembacaMeterActivity.this, "Masuk NULL userver", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(DetailRiwayatPembacaMeterActivity.this, "Masuk NULL userPindahData", Toast.LENGTH_SHORT).show();
                            } else {
                                binding.tvPindahDataBlnSekarang.setText(response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getRlUserTransfer().getNmPetugas()
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
                                Toast.makeText(DetailRiwayatPembacaMeterActivity.this, "Masuk NULL filePeriodeSekarang", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DetailRiwayatPembacaMeterActivity.this, "Masuk ke ftmtr skrg", Toast.LENGTH_SHORT).show();
                                Glide.with(DetailRiwayatPembacaMeterActivity.this)
                                        .load(Config.BASE_URL_IMAGE_HANDLER + response.body().getData().get(0).getRlDtBacaPeriodeSkrg().get(0).getFile())
                                        .override(512, 512)
                                        .error(R.drawable.im_good_day)
                                        .into(binding.ivFotoMeterBlnSekarang);
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<CheckPelangganRootModel> call, Throwable t) {
                        Toast.makeText(DetailRiwayatPembacaMeterActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}