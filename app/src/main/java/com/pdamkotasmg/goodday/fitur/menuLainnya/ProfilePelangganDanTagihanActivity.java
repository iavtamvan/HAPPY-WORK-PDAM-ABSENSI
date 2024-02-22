package com.pdamkotasmg.goodday.fitur.menuLainnya;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.adapter.AngsuranAdapter;
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.adapter.FlatAdapter;
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.adapter.TunggakanAdapter;
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.AngsuranRekItem;
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.BillingTagihanRootModel;
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.FlatItem;
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.TunggakanItem;
import com.pdamkotasmg.goodday.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePelangganDanTagihanActivity extends AppCompatActivity {
    private List<TunggakanItem> tunggakanItems;
    private List<AngsuranRekItem> angsuranRekItems;
    private List<FlatItem> flatItems;
//    private BillingModel billingModel;
//    private List<BillingModel> billingModelList;

    private String nollang;
    private String token;
    private String statrusRekening;

    private String namaPelanggan;
    private String bulanTahun;
    private String tarif;
    private String pemakaian;
    private String tagihan;

    private Locale localeID;
    private NumberFormat formatRupiah;

    private TextView tvNolangg;
    private TextView tvName;
    private TextView tvAlamat;
    private TextView tvBulanTahun;
    private TextView tvTarif;
    private TextView tvPemakaian;
    private TextView tvTagihan;
    private TextView tvTglBayar;
    private TextView tvTempatBayar;
    private TextView tvStatusRekeningBelumTerbayar;
    private TextView tvStatusRekeningTerbayar;
    private CardView cvKlikAngsuran;
    private CardView cvKlikFlat;
    private TextView tvCountDataAngsuranRek;
    private TextView tvCountDataFlat;

    private ProgressDialog loading;
    private CardView cvKlikTunggakan;
    private TextView tvCountDataTunggakan;

    private TextView tvCountJumlahTagihanAngsuran;
    private TextView tvCountJumlahTagihanFlat;
    private TextView tvCountJumlahTagihanTunggakan;
    private int hargaTotalAngsuran = 0;
    private int hargaTotalFlat = 0;
    private int hargaTotalTunggakan = 0;
    private int hargaTotalJumlahBayar = 0;
    private TextView tvStatusPelanggan;
    private TextView tvJumlahBayar;
    private LinearLayout divBayarYuk;
    private LinearLayout divParrent;
    private LinearLayout divRefresh;
    private RecyclerView rvTunggakan;
    private RecyclerView rvAngusran;
    private ImageView ivFotoRumah;
    private RecyclerView rvFlat;
    private CardView cvKlikFotoRumah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pelanggan_dan_tagihan);
        getSupportActionBar().hide();
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        nollang = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG);

        tunggakanItems = new ArrayList<>();
        angsuranRekItems = new ArrayList<>();
        flatItems = new ArrayList<>();
//        billingModel = new BillingModel();
//        billingModelList = new ArrayList<>();

        localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        divParrent.setVisibility(View.GONE);
        getData(nollang, token);

        divBayarYuk.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
//            intent.putExtra("nollang", nollang);
//            intent.putExtra("tarif", tarif);
//            intent.putExtra("nama_pelanggan", namaPelanggan);
//            intent.putExtra("tagihan", tagihan);
//            intent.putExtra("bulantahun", bulanTahun);
//            startActivity(intent);
        });

        divRefresh.setOnClickListener(v -> {
            getData(nollang, token);
        });

    }


    public void getData(String nollang, String token) {
        MaterialDialog mDialog = new MaterialDialog.Builder(ProfilePelangganDanTagihanActivity.this)
                .setMessage("Mengambil data")
                .setAnimation("water_loading.json")
                .build();
        // Show Dialog
        mDialog.show();
        ApiService apiService = ApiConfig.getApiService(ProfilePelangganDanTagihanActivity.this);
        apiService.getBillingTagihan(nollang, "Bearer " + token)
                .enqueue(new Callback<BillingTagihanRootModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<BillingTagihanRootModel> call, Response<BillingTagihanRootModel> response) {
                        if (response.isSuccessful()) {
                            divParrent.setVisibility(View.VISIBLE);

                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                            tvNolangg.setText(response.body().getData().getPelanggan().getNolangg());
                            namaPelanggan = response.body().getData().getPelanggan().getNama();
                            tvName.setText(namaPelanggan);
                            tvAlamat.setText(response.body().getData().getPelanggan().getAlamat() + " Kel. " + response.body().getData().getPelanggan().getKelurahan() + " Kec. " + response.body().getData().getPelanggan().getKecamatan());
                            tvStatusPelanggan.setText(response.body().getData().getPelanggan().getStatusKet());
                            Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getPelanggan().getFotoRumah()).override(512, 512).error(R.drawable.image_not_found).into(ivFotoRumah);

                            cvKlikFotoRumah.setOnClickListener(view -> {
                                if (ivFotoRumah.getVisibility() == View.GONE) {
                                    ivFotoRumah.setVisibility(View.VISIBLE);
                                } else {
                                    ivFotoRumah.setVisibility(View.GONE);
                                }
                            });

//                            if (db.billingDao().findByNollangg(nollang) == null) {
//                                billingModel.setName(namaPelanggan);
//                                billingModel.setNollangg(tvNolangg.getText().toString().trim());
//                                billingModel.setAlamat(tvAlamat.getText().toString().trim());
//                                billingModel.setStatus(tvStatusPelanggan.getText().toString().trim());
//                                db.billingDao().insertAll(billingModel);
////                                Toast.makeText(ProfilePelangganDanTagihanActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                            }

                            if (response.body().getData().getRutin() == null) {
//                                cvKlikTunggakan.setVisibility(View.GONE);
//                                cvKlikAngsuran.setVisibility(View.GONE);
//                                cvKlikFlat.setVisibility(View.GONE);
                                tvStatusRekeningBelumTerbayar.setText("-");

                                tunggakanItems = response.body().getData().getTunggakan();
                                angsuranRekItems = response.body().getData().getAngsuranRek();
                                flatItems = response.body().getData().getFlat();
                                Log.d("billing", "onResponse tunggakan : " + tunggakanItems);
                                Log.d("billing", "onResponse angsuran: " + angsuranRekItems);
                                Log.d("billing", "onResponse flat: " + flatItems);

                                if (tunggakanItems.isEmpty() && angsuranRekItems.isEmpty() && flatItems.isEmpty()) {
                                    cvKlikTunggakan.setVisibility(View.GONE);
                                    cvKlikAngsuran.setVisibility(View.GONE);
                                    cvKlikFlat.setVisibility(View.GONE);
                                } else {
                                    divBayarYuk.setVisibility(View.GONE);
                                    cvKlikTunggakan.setOnClickListener(view -> {
                                        if (rvTunggakan.getVisibility() == View.GONE) {
                                            rvTunggakan.setVisibility(View.VISIBLE);
                                        } else {
                                            rvTunggakan.setVisibility(View.GONE);
                                        }
                                    });
                                    cvKlikAngsuran.setOnClickListener(view -> {
                                        if (rvAngusran.getVisibility() == View.GONE) {
                                            rvAngusran.setVisibility(View.VISIBLE);
                                        } else {
                                            rvAngusran.setVisibility(View.GONE);
                                        }
                                    });
                                    cvKlikFlat.setOnClickListener(view -> {
                                        if (rvFlat.getVisibility() == View.GONE) {
                                            rvFlat.setVisibility(View.VISIBLE);
                                        } else {
                                            rvFlat.setVisibility(View.GONE);
                                        }
                                    });
                                    tvCountDataTunggakan.setText("(" + tunggakanItems.size() + " data)");
                                    for (int i = 0; i < tunggakanItems.size(); i++) {
                                        Log.d("debug", "dataTunggakan: " + tunggakanItems.get(i).getTagihan());
                                        hargaTotalTunggakan += tunggakanItems.get(i).getTagihan();
                                    }
                                    Log.d("debug", "Total Tunggakan: " + hargaTotalTunggakan);
                                    tvCountJumlahTagihanTunggakan.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(hargaTotalTunggakan))));
                                    TunggakanAdapter tunggakanAdapter = new TunggakanAdapter(ProfilePelangganDanTagihanActivity.this, tunggakanItems);
                                    rvTunggakan.setLayoutManager(new LinearLayoutManager(ProfilePelangganDanTagihanActivity.this));
                                    rvTunggakan.setAdapter(tunggakanAdapter);
                                    tunggakanAdapter.notifyDataSetChanged();

                                    tvCountDataAngsuranRek.setText("(" + angsuranRekItems.size() + " data)");
                                    for (int i = 0; i < angsuranRekItems.size(); i++) {
                                        Log.d("debug", "dataAngsuran: " + angsuranRekItems.get(i).getTagihan());
                                        hargaTotalAngsuran += Integer.parseInt(angsuranRekItems.get(i).getTagihan());
                                    }
                                    tvCountJumlahTagihanAngsuran.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(hargaTotalAngsuran))));
                                    Log.d("debug", "Total Angsuran: " + hargaTotalAngsuran);
                                    AngsuranAdapter angsuranAdapter = new AngsuranAdapter(ProfilePelangganDanTagihanActivity.this, angsuranRekItems);
                                    rvAngusran.setLayoutManager(new LinearLayoutManager(ProfilePelangganDanTagihanActivity.this));
                                    rvAngusran.setAdapter(angsuranAdapter);
                                    angsuranAdapter.notifyDataSetChanged();

                                    tvCountDataFlat.setText("(" + flatItems.size() + " data)");
                                    for (int i = 0; i < flatItems.size(); i++) {
                                        Log.d("debug", "dataFlat: " + flatItems.get(i).getTagihan());
                                        hargaTotalFlat += flatItems.get(i).getTagihan();
                                    }
                                    Log.d("debug", "Total Flat: " + hargaTotalFlat);
                                    tvCountJumlahTagihanFlat.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(hargaTotalFlat))));
                                    FlatAdapter flatAdapter = new FlatAdapter(ProfilePelangganDanTagihanActivity.this, flatItems);
                                    rvFlat.setLayoutManager(new LinearLayoutManager(ProfilePelangganDanTagihanActivity.this));
                                    rvFlat.setAdapter(flatAdapter);
                                    flatAdapter.notifyDataSetChanged();

                                    hargaTotalJumlahBayar = (hargaTotalTunggakan + hargaTotalFlat);
                                    Log.d("debug", "Harga total bayar : " + hargaTotalJumlahBayar);
                                    tvJumlahBayar.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(hargaTotalJumlahBayar))));
                                }
                                mDialog.dismiss();
                            } else {
                                bulanTahun = response.body().getData().getRutin().getBulanName() + " " + response.body().getData().getRutin().getTahun();
                                tarif = response.body().getData().getRutin().getTarif();
                                pemakaian = response.body().getData().getRutin().getPemakaian();
                                tagihan = response.body().getData().getRutin().getTagihan();

                                tvBulanTahun.setText(bulanTahun);
                                tvTarif.setText(tarif);
                                tvPemakaian.setText(pemakaian);
                                tvTagihan.setText(formatRupiah.format((double) Integer.parseInt(tagihan)));

                                tvTglBayar.setText(response.body().getData().getRutin().getTanggalBayar());
                                tvTempatBayar.setText(response.body().getData().getRutin().getTempatBayar());
                                statrusRekening = response.body().getData().getRutin().getStatusRekening();
                                Log.d("debug", "statrusRekening: " + statrusRekening);

                                Log.d("debug", "Bayar rutin : " + response.body().getData().getRutin().getTagihan());
                                tvJumlahBayar.setText(formatRupiah.format((double) Integer.parseInt(response.body().getData().getRutin().getTagihan())));


                                if (statrusRekening.equals("Belum Terbayar")) {
                                    tvStatusRekeningBelumTerbayar.setVisibility(View.VISIBLE);
//                                    divBayarYuk.setVisibility(View.VISIBLE);
                                    tvStatusRekeningTerbayar.setVisibility(View.GONE);
                                } else {
                                    divBayarYuk.setVisibility(View.GONE);
                                    tvStatusRekeningBelumTerbayar.setVisibility(View.GONE);
                                    tvStatusRekeningTerbayar.setVisibility(View.VISIBLE);
                                }

                                tunggakanItems = response.body().getData().getTunggakan();
                                angsuranRekItems = response.body().getData().getAngsuranRek();
                                flatItems = response.body().getData().getFlat();
                                Log.d("debug billing", "onResponse tunggakan : " + tunggakanItems);
                                Log.d("debug billing", "onResponse angsuran: " + angsuranRekItems);
                                Log.d("debug billing", "onResponse flat: " + flatItems);

                                if (tunggakanItems.isEmpty() && angsuranRekItems.isEmpty() && flatItems.isEmpty()) {
                                    cvKlikTunggakan.setVisibility(View.GONE);
                                    cvKlikAngsuran.setVisibility(View.GONE);
                                    cvKlikFlat.setVisibility(View.GONE);
                                } else {
                                    divBayarYuk.setVisibility(View.GONE);
                                    cvKlikTunggakan.setOnClickListener(view -> {
                                        if (rvTunggakan.getVisibility() == View.GONE) {
                                            rvTunggakan.setVisibility(View.VISIBLE);
                                        } else {
                                            rvTunggakan.setVisibility(View.GONE);
                                        }
                                    });
                                    cvKlikAngsuran.setOnClickListener(view -> {
                                        if (rvAngusran.getVisibility() == View.GONE) {
                                            rvAngusran.setVisibility(View.VISIBLE);
                                        } else {
                                            rvAngusran.setVisibility(View.GONE);
                                        }
                                    });
                                    cvKlikFlat.setOnClickListener(view -> {
                                        if (rvFlat.getVisibility() == View.GONE) {
                                            rvFlat.setVisibility(View.VISIBLE);
                                        } else {
                                            rvFlat.setVisibility(View.GONE);
                                        }
                                    });


                                    tvCountDataTunggakan.setText("(" + tunggakanItems.size() + " data)");
                                    for (int i = 0; i < tunggakanItems.size(); i++) {
                                        Log.d("debug", "dataTunggakan: " + tunggakanItems.get(i).getTagihan());
                                        hargaTotalTunggakan += tunggakanItems.get(i).getTagihan();
                                    }
                                    tvCountJumlahTagihanTunggakan.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(hargaTotalTunggakan))));
                                    Log.d("debug", "Total Tunggakan: " + hargaTotalTunggakan);
                                    TunggakanAdapter tunggakanAdapter = new TunggakanAdapter(ProfilePelangganDanTagihanActivity.this, tunggakanItems);
                                    rvTunggakan.setLayoutManager(new LinearLayoutManager(ProfilePelangganDanTagihanActivity.this));
                                    rvTunggakan.setAdapter(tunggakanAdapter);
                                    tunggakanAdapter.notifyDataSetChanged();

                                    tvCountDataAngsuranRek.setText("(" + angsuranRekItems.size() + " data)");
                                    for (int i = 0; i < angsuranRekItems.size(); i++) {
                                        Log.d("debug", "dataAngsuran: " + angsuranRekItems.get(i).getTagihan());
                                        hargaTotalAngsuran += Integer.parseInt(angsuranRekItems.get(i).getTagihan());
                                    }
                                    tvCountJumlahTagihanAngsuran.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(hargaTotalAngsuran))));
                                    Log.d("debug", "Total Angsuran: " + hargaTotalAngsuran);
                                    AngsuranAdapter angsuranAdapter = new AngsuranAdapter(ProfilePelangganDanTagihanActivity.this, angsuranRekItems);
                                    rvAngusran.setLayoutManager(new LinearLayoutManager(ProfilePelangganDanTagihanActivity.this));
                                    rvAngusran.setAdapter(angsuranAdapter);
                                    angsuranAdapter.notifyDataSetChanged();

                                    tvCountDataFlat.setText("(" + flatItems.size() + " data)");
                                    for (int i = 0; i < flatItems.size(); i++) {
                                        Log.d("debug", "dataFlat: " + flatItems.get(i).getTagihan());
                                        hargaTotalFlat += flatItems.get(i).getTagihan();
                                    }
                                    Log.d("debug", "Total Flat: " + hargaTotalFlat);
                                    tvCountJumlahTagihanFlat.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(hargaTotalFlat))));
                                    FlatAdapter flatAdapter = new FlatAdapter(ProfilePelangganDanTagihanActivity.this, flatItems);
                                    rvFlat.setLayoutManager(new LinearLayoutManager(ProfilePelangganDanTagihanActivity.this));
                                    rvFlat.setAdapter(flatAdapter);
                                    flatAdapter.notifyDataSetChanged();

                                    int tagihanRutin = Integer.parseInt(response.body().getData().getRutin().getTagihan());
                                    hargaTotalJumlahBayar = (tagihanRutin + hargaTotalTunggakan + hargaTotalFlat);
                                    Log.d("debug", "Harga total bayar : " + hargaTotalJumlahBayar);
                                    tvJumlahBayar.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(hargaTotalJumlahBayar))));
                                }
                                mDialog.dismiss();
                            }


//                            if (tunggakanItems.isEmpty()) {
//                                Log.d("billing count", "onResponse tunggakan cv: kosong");
//                                cvKlikTunggakan.setVisibility(View.GONE);
//                            } else if (angsuranRekItems.isEmpty()) {
//                                Log.d("billing count", "onResponse angsuran cv : kosong");
//                                cvKlikAngsuran.setVisibility(View.GONE);
//                            } else if (flatItems.isEmpty()) {
//                                Log.d("billing count", "onResponse flat cv: kosong");
//                                cvKlikFlat.setVisibility(View.GONE);
//                            } else {
//                                cvKlikTunggakan.setVisibility(View.GONE);
//                                cvKlikAngsuran.setVisibility(View.GONE);
//                                cvKlikFlat.setVisibility(View.GONE);
//                            }

//                            for (int i = 0; i < tunggakanItems.size(); i++) {
//                                Toast.makeText(ProfilePelangganDanTagihanActivity.this, "Array Tunggakan : " + tunggakanItems.get(i).getTagihan(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(ProfilePelangganDanTagihanActivity.this, "Array Tunggakan : " + tunggakanItems.get(i).getTagihan(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(ProfilePelangganDanTagihanActivity.this, "Array Tunggakan : " + tunggakanItems.get(i).getTagihan(), Toast.LENGTH_SHORT).show();
//                            }

                        } else {
                            mDialog.dismiss();
                            tvStatusRekeningBelumTerbayar.setText("-");
                            Toast.makeText(ProfilePelangganDanTagihanActivity.this, "Data gak ada", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BillingTagihanRootModel> call, Throwable t) {
                        mDialog.dismiss();
                        Toast.makeText(ProfilePelangganDanTagihanActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void initView() {
        tvNolangg = findViewById(R.id.tv_nolangg);
        tvName = findViewById(R.id.tv_name);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvBulanTahun = findViewById(R.id.tv_bulan_tahun);
        tvTarif = findViewById(R.id.tv_tarif);
        tvPemakaian = findViewById(R.id.tv_pemakaian);
        tvTagihan = findViewById(R.id.tv_tagihan);
        tvTglBayar = findViewById(R.id.tv_tgl_bayar);
        tvTempatBayar = findViewById(R.id.tv_tempat_bayar);
        tvStatusRekeningBelumTerbayar = findViewById(R.id.tv_status_rekening_belum_terbayar);
        tvStatusRekeningTerbayar = findViewById(R.id.tv_status_rekening_terbayar);
        cvKlikAngsuran = findViewById(R.id.cv_klik_angsuran);
        cvKlikFlat = findViewById(R.id.cv_klik_flat);
        tvCountDataAngsuranRek = findViewById(R.id.tv_count_data_angsuran_rek);
        tvCountDataFlat = findViewById(R.id.tv_count_data_flat);
        cvKlikTunggakan = findViewById(R.id.cv_klik_tunggakan);
        tvCountDataTunggakan = findViewById(R.id.tv_count_data_tunggakan);
        tvCountJumlahTagihanAngsuran = findViewById(R.id.tv_count_jumlah_tagihan_angsuran);
        tvCountJumlahTagihanFlat = findViewById(R.id.tv_count_jumlah_tagihan_flat);
        tvCountJumlahTagihanTunggakan = findViewById(R.id.tv_count_jumlah_tagihan_tunggakan);
        tvStatusPelanggan = findViewById(R.id.tv_status_pelanggan);
        tvJumlahBayar = findViewById(R.id.tv_jumlah_bayar);
        divBayarYuk = findViewById(R.id.div_bayar_yuk);
        divParrent = findViewById(R.id.div_parrent);
        divRefresh = findViewById(R.id.div_refresh);
        rvTunggakan = findViewById(R.id.rv_tunggakan);
        rvAngusran = findViewById(R.id.rv_angusran);
        ivFotoRumah = findViewById(R.id.iv_foto_rumah);
        rvFlat = findViewById(R.id.rv_flat);
        cvKlikFotoRumah = findViewById(R.id.cv_klik_foto_rumah);
    }
}