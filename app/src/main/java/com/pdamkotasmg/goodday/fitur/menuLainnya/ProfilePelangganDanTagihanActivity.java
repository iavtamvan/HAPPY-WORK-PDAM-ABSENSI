package com.pdamkotasmg.goodday.fitur.menuLainnya;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
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
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.kardek.DataBacaanKardekRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
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
    private LinearLayout divDataBacaan;
    private CardView cvDataBacaan;
    private EditText edtDataBacaanTahun;
    private TextView tvTrTahunLalu;
    private TextView tvTrTahunSekarang;
    private TextView tvTrThnLaluStand1;
    private TextView tvTrThnLaluM31;
    private TextView tvTrThnSkrgStand1;
    private TextView tvTrThnSkrgM31;
    private TextView tvTrThnSkrgKondisi1;
    private TextView tvTrThnSkrgData1;
    private TextView tvTrThnSkrgKeterangan1;
    private TextView tvTrThnLaluStand2;
    private TextView tvTrThnLaluM32;
    private TextView tvTrThnSkrgStand2;
    private TextView tvTrThnSkrgM32;
    private TextView tvTrThnSkrgKondisi2;
    private TextView tvTrThnSkrgData2;
    private TextView tvTrThnSkrgKeterangan2;
    private TextView tvTrThnLaluStand3;
    private TextView tvTrThnLaluM33;
    private TextView tvTrThnSkrgStand3;
    private TextView tvTrThnSkrgM33;
    private TextView tvTrThnSkrgKondisi3;
    private TextView tvTrThnSkrgData3;
    private TextView tvTrThnSkrgKeterangan3;
    private TextView tvTrThnLaluStand4;
    private TextView tvTrThnLaluM34;
    private TextView tvTrThnSkrgStand4;
    private TextView tvTrThnSkrgM34;
    private TextView tvTrThnSkrgKondisi4;
    private TextView tvTrThnSkrgData4;
    private TextView tvTrThnSkrgKeterangan4;
    private TextView tvTrThnLaluStand5;
    private TextView tvTrThnLaluM35;
    private TextView tvTrThnSkrgStand5;
    private TextView tvTrThnSkrgM35;
    private TextView tvTrThnSkrgKondisi5;
    private TextView tvTrThnSkrgData5;
    private TextView tvTrThnSkrgKeterangan5;
    private TextView tvTrThnLaluStand6;
    private TextView tvTrThnLaluM36;
    private TextView tvTrThnSkrgStand6;
    private TextView tvTrThnSkrgM36;
    private TextView tvTrThnSkrgKondisi6;
    private TextView tvTrThnSkrgData6;
    private TextView tvTrThnSkrgKeterangan6;
    private TextView tvTrThnLaluStand7;
    private TextView tvTrThnLaluM37;
    private TextView tvTrThnSkrgStand7;
    private TextView tvTrThnSkrgM37;
    private TextView tvTrThnSkrgKondisi7;
    private TextView tvTrThnSkrgData7;
    private TextView tvTrThnSkrgKeterangan7;
    private TextView tvTrThnLaluStand8;
    private TextView tvTrThnLaluM38;
    private TextView tvTrThnSkrgStand8;
    private TextView tvTrThnSkrgM38;
    private TextView tvTrThnSkrgKondisi8;
    private TextView tvTrThnSkrgData8;
    private TextView tvTrThnSkrgKeterangan8;
    private TextView tvTrThnLaluStand9;
    private TextView tvTrThnLaluM39;
    private TextView tvTrThnSkrgStand9;
    private TextView tvTrThnSkrgM39;
    private TextView tvTrThnSkrgKondisi9;
    private TextView tvTrThnSkrgData9;
    private TextView tvTrThnSkrgKeterangan9;
    private TextView tvTrThnLaluStand10;
    private TextView tvTrThnLaluM310;
    private TextView tvTrThnSkrgStand10;
    private TextView tvTrThnSkrgM310;
    private TextView tvTrThnSkrgKondisi10;
    private TextView tvTrThnSkrgData10;
    private TextView tvTrThnSkrgKeterangan10;
    private TextView tvTrThnLaluStand11;
    private TextView tvTrThnLaluM311;
    private TextView tvTrThnSkrgStand11;
    private TextView tvTrThnSkrgM311;
    private TextView tvTrThnSkrgKondisi11;
    private TextView tvTrThnSkrgData11;
    private TextView tvTrThnSkrgKeterangan11;
    private TextView tvTrThnLaluStand12;
    private TextView tvTrThnLaluM312;
    private TextView tvTrThnSkrgStand12;
    private TextView tvTrThnSkrgM312;
    private TextView tvTrThnSkrgKondisi12;
    private TextView tvTrThnSkrgData12;
    private TextView tvTrThnSkrgKeterangan12;
    private Button btnCariBacaanTahun;
    private TextView tvFotoMeterTahun;
    private TextView tvBulanStandM31;
    private PhotoView ivFotoMeter1;
    private TextView tvBulanStandM32;
    private PhotoView ivFotoMeter2;
    private TextView tvBulanStandM33;
    private PhotoView ivFotoMeter3;
    private TextView tvBulanStandM34;
    private PhotoView ivFotoMeter4;
    private TextView tvBulanStandM35;
    private PhotoView ivFotoMeter5;
    private TextView tvBulanStandM36;
    private PhotoView ivFotoMeter6;
    private TextView tvBulanStandM37;
    private PhotoView ivFotoMeter7;
    private TextView tvBulanStandM38;
    private PhotoView ivFotoMeter8;
    private TextView tvBulanStandM39;
    private PhotoView ivFotoMeter9;
    private TextView tvBulanStandM310;
    private PhotoView ivFotoMeter10;
    private TextView tvBulanStandM311;
    private PhotoView ivFotoMeter11;
    private TextView tvBulanStandM312;
    private PhotoView ivFotoMeter12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pelanggan_dan_tagihan);
        
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        nollang = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG);

        loading = new ProgressDialog(ProfilePelangganDanTagihanActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Mohon tunggu...");

        tunggakanItems = new ArrayList<>();
        angsuranRekItems = new ArrayList<>();
        flatItems = new ArrayList<>();
//        billingModel = new BillingModel();
//        billingModelList = new ArrayList<>();

        localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        divParrent.setVisibility(View.GONE);
        getData(nollang, token);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        getDataBacaanKardek(token, nollang, String.valueOf(year));

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

        btnCariBacaanTahun.setOnClickListener(view -> {
            loading.show();
            getDataBacaanKardek(token, nollang, edtDataBacaanTahun.getText().toString().trim());
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
                            Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getPelanggan().getFotoRumah()).override(512, 512).error(R.drawable.image_not_available).into(ivFotoRumah);

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

    public void getDataBacaanKardek(String token, String nolangg, String tahun) {
        ApiService apiService = ApiConfig.getApiService(ProfilePelangganDanTagihanActivity.this);
        apiService.getDataBacaan(nolangg, tahun, token).enqueue(new Callback<DataBacaanKardekRootModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DataBacaanKardekRootModel> call, Response<DataBacaanKardekRootModel> response) {
                loading.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getData().getQueryKardekDataBacaanTahunLalu() == null || response.body().getData().getQueryKardekDataBacaan() == null) {
                        Toast.makeText(ProfilePelangganDanTagihanActivity.this, "Data tidak ditemukan (IF)", Toast.LENGTH_SHORT).show();
                    } else {

                        // kardek tahun lalu
                        tvTrThnLaluStand1.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl1());
                        tvTrThnLaluStand2.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl2());
                        tvTrThnLaluStand3.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl3());
                        tvTrThnLaluStand4.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl4());
                        tvTrThnLaluStand5.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl5());
                        tvTrThnLaluStand6.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl6());
                        tvTrThnLaluStand7.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl7());
                        tvTrThnLaluStand8.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl8());
                        tvTrThnLaluStand9.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl9());
                        tvTrThnLaluStand10.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl10());
                        tvTrThnLaluStand11.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl11());
                        tvTrThnLaluStand12.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getBl12());
                        tvTrThnLaluM31.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM31());
                        tvTrThnLaluM32.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM32());
                        tvTrThnLaluM33.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM33());
                        tvTrThnLaluM34.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM34());
                        tvTrThnLaluM35.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM35());
                        tvTrThnLaluM36.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM36());
                        tvTrThnLaluM37.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM37());
                        tvTrThnLaluM38.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM38());
                        tvTrThnLaluM39.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM39());
                        tvTrThnLaluM310.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM310());
                        tvTrThnLaluM311.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM311());
                        tvTrThnLaluM312.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getM312());
                        tvTrTahunLalu.setText(response.body().getData().getQueryKardekDataBacaanTahunLalu().getTahun());

                        // kardek tahun sekarang (sesuai request)
                        tvTrThnSkrgStand1.setText(response.body().getData().getQueryKardekDataBacaan().getBl1());
                        tvTrThnSkrgStand2.setText(response.body().getData().getQueryKardekDataBacaan().getBl2());
                        tvTrThnSkrgStand3.setText(response.body().getData().getQueryKardekDataBacaan().getBl3());
                        tvTrThnSkrgStand4.setText(response.body().getData().getQueryKardekDataBacaan().getBl4());
                        tvTrThnSkrgStand5.setText(response.body().getData().getQueryKardekDataBacaan().getBl5());
                        tvTrThnSkrgStand6.setText(response.body().getData().getQueryKardekDataBacaan().getBl6());
                        tvTrThnSkrgStand7.setText(response.body().getData().getQueryKardekDataBacaan().getBl7());
                        tvTrThnSkrgStand8.setText(response.body().getData().getQueryKardekDataBacaan().getBl8());
                        tvTrThnSkrgStand9.setText(response.body().getData().getQueryKardekDataBacaan().getBl9());
                        tvTrThnSkrgStand10.setText(response.body().getData().getQueryKardekDataBacaan().getBl10());
                        tvTrThnSkrgStand11.setText(response.body().getData().getQueryKardekDataBacaan().getBl11());
                        tvTrThnSkrgStand12.setText(response.body().getData().getQueryKardekDataBacaan().getBl12());
                        tvTrThnSkrgM31.setText(response.body().getData().getQueryKardekDataBacaan().getM31());
                        tvTrThnSkrgM32.setText(response.body().getData().getQueryKardekDataBacaan().getM32());
                        tvTrThnSkrgM33.setText(response.body().getData().getQueryKardekDataBacaan().getM33());
                        tvTrThnSkrgM34.setText(response.body().getData().getQueryKardekDataBacaan().getM34());
                        tvTrThnSkrgM35.setText(response.body().getData().getQueryKardekDataBacaan().getM35());
                        tvTrThnSkrgM36.setText(response.body().getData().getQueryKardekDataBacaan().getM36());
                        tvTrThnSkrgM37.setText(response.body().getData().getQueryKardekDataBacaan().getM37());
                        tvTrThnSkrgM38.setText(response.body().getData().getQueryKardekDataBacaan().getM38());
                        tvTrThnSkrgM39.setText(response.body().getData().getQueryKardekDataBacaan().getM39());
                        tvTrThnSkrgM310.setText(response.body().getData().getQueryKardekDataBacaan().getM310());
                        tvTrThnSkrgM311.setText(response.body().getData().getQueryKardekDataBacaan().getM311());
                        tvTrThnSkrgM312.setText(response.body().getData().getQueryKardekDataBacaan().getM312());
                        tvTrThnSkrgKondisi1.setText(response.body().getData().getQueryKardekDataBacaan().getNmst1());
                        tvTrThnSkrgKondisi2.setText(response.body().getData().getQueryKardekDataBacaan().getNmst2());
                        tvTrThnSkrgKondisi3.setText(response.body().getData().getQueryKardekDataBacaan().getNmst3());
                        tvTrThnSkrgKondisi4.setText(response.body().getData().getQueryKardekDataBacaan().getNmst4());
                        tvTrThnSkrgKondisi5.setText(response.body().getData().getQueryKardekDataBacaan().getNmst5());
                        tvTrThnSkrgKondisi6.setText(response.body().getData().getQueryKardekDataBacaan().getNmst6());
                        tvTrThnSkrgKondisi7.setText(response.body().getData().getQueryKardekDataBacaan().getNmst7());
                        tvTrThnSkrgKondisi8.setText(response.body().getData().getQueryKardekDataBacaan().getNmst8());
                        tvTrThnSkrgKondisi9.setText(response.body().getData().getQueryKardekDataBacaan().getNmst9());
                        tvTrThnSkrgKondisi10.setText(response.body().getData().getQueryKardekDataBacaan().getNmst10());
                        tvTrThnSkrgKondisi11.setText(response.body().getData().getQueryKardekDataBacaan().getNmst11());
                        tvTrThnSkrgKondisi12.setText(response.body().getData().getQueryKardekDataBacaan().getNmst12());
                        tvTrThnSkrgData1.setText(response.body().getData().getQueryKardekDataBacaan().getSc1());
                        tvTrThnSkrgData2.setText(response.body().getData().getQueryKardekDataBacaan().getSc2());
                        tvTrThnSkrgData3.setText(response.body().getData().getQueryKardekDataBacaan().getSc3());
                        tvTrThnSkrgData4.setText(response.body().getData().getQueryKardekDataBacaan().getSc4());
                        tvTrThnSkrgData5.setText(response.body().getData().getQueryKardekDataBacaan().getSc5());
                        tvTrThnSkrgData6.setText(response.body().getData().getQueryKardekDataBacaan().getSc6());
                        tvTrThnSkrgData7.setText(response.body().getData().getQueryKardekDataBacaan().getSc7());
                        tvTrThnSkrgData8.setText(response.body().getData().getQueryKardekDataBacaan().getSc8());
                        tvTrThnSkrgData9.setText(response.body().getData().getQueryKardekDataBacaan().getSc9());
                        tvTrThnSkrgData10.setText(response.body().getData().getQueryKardekDataBacaan().getSc10());
                        tvTrThnSkrgData11.setText(response.body().getData().getQueryKardekDataBacaan().getSc11());
                        tvTrThnSkrgData12.setText(response.body().getData().getQueryKardekDataBacaan().getSc12());
                        tvTrThnSkrgKeterangan1.setText(response.body().getData().getQueryKardekDataBacaan().getKt1());
                        tvTrThnSkrgKeterangan2.setText(response.body().getData().getQueryKardekDataBacaan().getKt2());
                        tvTrThnSkrgKeterangan3.setText(response.body().getData().getQueryKardekDataBacaan().getKt3());
                        tvTrThnSkrgKeterangan4.setText(response.body().getData().getQueryKardekDataBacaan().getKt4());
                        tvTrThnSkrgKeterangan5.setText(response.body().getData().getQueryKardekDataBacaan().getKt5());
                        tvTrThnSkrgKeterangan6.setText(response.body().getData().getQueryKardekDataBacaan().getKt6());
                        tvTrThnSkrgKeterangan7.setText(response.body().getData().getQueryKardekDataBacaan().getKt7());
                        tvTrThnSkrgKeterangan8.setText(response.body().getData().getQueryKardekDataBacaan().getKt8());
                        tvTrThnSkrgKeterangan9.setText(response.body().getData().getQueryKardekDataBacaan().getKt9());
                        tvTrThnSkrgKeterangan10.setText(response.body().getData().getQueryKardekDataBacaan().getKt10());
                        tvTrThnSkrgKeterangan11.setText(response.body().getData().getQueryKardekDataBacaan().getKt11());
                        tvTrThnSkrgKeterangan12.setText(response.body().getData().getQueryKardekDataBacaan().getKt12());
                        tvTrTahunSekarang.setText("Tahun " + response.body().getData().getQueryKardekDataBacaan().getTahun());
                        tvFotoMeterTahun.setText("Foto Meter Tahun " + response.body().getData().getQueryKardekDataBacaan().getTahun());

                        // get foto meter tahun sekarang
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getJanuari().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter1);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getFebruari().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter2);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getMaret().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter3);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getApril().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter4);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getMei().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter5);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getJuni().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter6);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getJuli().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter7);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getAgustus().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter8);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getSeptember().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter9);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getOktober().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter10);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getNovember().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter11);
                        Glide.with(ProfilePelangganDanTagihanActivity.this).load(response.body().getData().getFotoMeter().getDesember().getFoto()).error(R.drawable.image_not_available).into(ivFotoMeter12);

                        tvBulanStandM31.setText("Januari : " + response.body().getData().getFotoMeter().getJanuari().getStand() + " - " + response.body().getData().getFotoMeter().getJanuari().getM3());
                        tvBulanStandM32.setText("Februari : " + response.body().getData().getFotoMeter().getFebruari().getStand() + " - " + response.body().getData().getFotoMeter().getFebruari().getM3());
                        tvBulanStandM33.setText("Maret : " + response.body().getData().getFotoMeter().getMaret().getStand() + " - " + response.body().getData().getFotoMeter().getMaret().getM3());
                        tvBulanStandM34.setText("April : " + response.body().getData().getFotoMeter().getApril().getStand() + " - " + response.body().getData().getFotoMeter().getApril().getM3());
                        tvBulanStandM35.setText("Mei : " + response.body().getData().getFotoMeter().getMei().getStand() + " - " + response.body().getData().getFotoMeter().getMei().getM3());
                        tvBulanStandM36.setText("Juni : " + response.body().getData().getFotoMeter().getJuni().getStand() + " - " + response.body().getData().getFotoMeter().getJuni().getM3());
                        tvBulanStandM37.setText("Juli : " + response.body().getData().getFotoMeter().getJuli().getStand() + " - " + response.body().getData().getFotoMeter().getJuli().getM3());
                        tvBulanStandM38.setText("Agustus : " + response.body().getData().getFotoMeter().getAgustus().getStand() + " - " + response.body().getData().getFotoMeter().getAgustus().getM3());
                        tvBulanStandM39.setText("September : " + response.body().getData().getFotoMeter().getSeptember().getStand() + " - " + response.body().getData().getFotoMeter().getSeptember().getM3());
                        tvBulanStandM310.setText("Oktober : " + response.body().getData().getFotoMeter().getOktober().getStand() + " - " + response.body().getData().getFotoMeter().getOktober().getM3());
                        tvBulanStandM311.setText("November : " + response.body().getData().getFotoMeter().getNovember().getStand() + " - " + response.body().getData().getFotoMeter().getNovember().getM3());
                        tvBulanStandM312.setText("Desember : " + response.body().getData().getFotoMeter().getDesember().getStand() + " - " + response.body().getData().getFotoMeter().getDesember().getM3());
                    }
                } else {
                    Toast.makeText(ProfilePelangganDanTagihanActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataBacaanKardekRootModel> call, Throwable t) {
                loading.dismiss();
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
        divDataBacaan = findViewById(R.id.div_data_bacaan);
        cvDataBacaan = findViewById(R.id.cv_data_bacaan);
        edtDataBacaanTahun = findViewById(R.id.edt_data_bacaan_tahun);
        tvTrTahunLalu = findViewById(R.id.tv_tr_tahun_lalu);
        tvTrTahunSekarang = findViewById(R.id.tv_tr_tahun_sekarang);
        tvTrThnLaluStand1 = findViewById(R.id.tv_tr_thn_lalu_stand1);
        tvTrThnLaluM31 = findViewById(R.id.tv_tr_thn_lalu_m31);
        tvTrThnSkrgStand1 = findViewById(R.id.tv_tr_thn_skrg_stand1);
        tvTrThnSkrgM31 = findViewById(R.id.tv_tr_thn_skrg_m31);
        tvTrThnSkrgKondisi1 = findViewById(R.id.tv_tr_thn_skrg_kondisi1);
        tvTrThnSkrgData1 = findViewById(R.id.tv_tr_thn_skrg_data1);
        tvTrThnSkrgKeterangan1 = findViewById(R.id.tv_tr_thn_skrg_keterangan1);
        tvTrThnLaluStand2 = findViewById(R.id.tv_tr_thn_lalu_stand2);
        tvTrThnLaluM32 = findViewById(R.id.tv_tr_thn_lalu_m32);
        tvTrThnSkrgStand2 = findViewById(R.id.tv_tr_thn_skrg_stand2);
        tvTrThnSkrgM32 = findViewById(R.id.tv_tr_thn_skrg_m32);
        tvTrThnSkrgKondisi2 = findViewById(R.id.tv_tr_thn_skrg_kondisi2);
        tvTrThnSkrgData2 = findViewById(R.id.tv_tr_thn_skrg_data2);
        tvTrThnSkrgKeterangan2 = findViewById(R.id.tv_tr_thn_skrg_keterangan2);
        tvTrThnLaluStand3 = findViewById(R.id.tv_tr_thn_lalu_stand3);
        tvTrThnLaluM33 = findViewById(R.id.tv_tr_thn_lalu_m33);
        tvTrThnSkrgStand3 = findViewById(R.id.tv_tr_thn_skrg_stand3);
        tvTrThnSkrgM33 = findViewById(R.id.tv_tr_thn_skrg_m33);
        tvTrThnSkrgKondisi3 = findViewById(R.id.tv_tr_thn_skrg_kondisi3);
        tvTrThnSkrgData3 = findViewById(R.id.tv_tr_thn_skrg_data3);
        tvTrThnSkrgKeterangan3 = findViewById(R.id.tv_tr_thn_skrg_keterangan3);
        tvTrThnLaluStand4 = findViewById(R.id.tv_tr_thn_lalu_stand4);
        tvTrThnLaluM34 = findViewById(R.id.tv_tr_thn_lalu_m34);
        tvTrThnSkrgStand4 = findViewById(R.id.tv_tr_thn_skrg_stand4);
        tvTrThnSkrgM34 = findViewById(R.id.tv_tr_thn_skrg_m34);
        tvTrThnSkrgKondisi4 = findViewById(R.id.tv_tr_thn_skrg_kondisi4);
        tvTrThnSkrgData4 = findViewById(R.id.tv_tr_thn_skrg_data4);
        tvTrThnSkrgKeterangan4 = findViewById(R.id.tv_tr_thn_skrg_keterangan4);
        tvTrThnLaluStand5 = findViewById(R.id.tv_tr_thn_lalu_stand5);
        tvTrThnLaluM35 = findViewById(R.id.tv_tr_thn_lalu_m35);
        tvTrThnSkrgStand5 = findViewById(R.id.tv_tr_thn_skrg_stand5);
        tvTrThnSkrgM35 = findViewById(R.id.tv_tr_thn_skrg_m35);
        tvTrThnSkrgKondisi5 = findViewById(R.id.tv_tr_thn_skrg_kondisi5);
        tvTrThnSkrgData5 = findViewById(R.id.tv_tr_thn_skrg_data5);
        tvTrThnSkrgKeterangan5 = findViewById(R.id.tv_tr_thn_skrg_keterangan5);
        tvTrThnLaluStand6 = findViewById(R.id.tv_tr_thn_lalu_stand6);
        tvTrThnLaluM36 = findViewById(R.id.tv_tr_thn_lalu_m36);
        tvTrThnSkrgStand6 = findViewById(R.id.tv_tr_thn_skrg_stand6);
        tvTrThnSkrgM36 = findViewById(R.id.tv_tr_thn_skrg_m36);
        tvTrThnSkrgKondisi6 = findViewById(R.id.tv_tr_thn_skrg_kondisi6);
        tvTrThnSkrgData6 = findViewById(R.id.tv_tr_thn_skrg_data6);
        tvTrThnSkrgKeterangan6 = findViewById(R.id.tv_tr_thn_skrg_keterangan6);
        tvTrThnLaluStand7 = findViewById(R.id.tv_tr_thn_lalu_stand7);
        tvTrThnLaluM37 = findViewById(R.id.tv_tr_thn_lalu_m37);
        tvTrThnSkrgStand7 = findViewById(R.id.tv_tr_thn_skrg_stand7);
        tvTrThnSkrgM37 = findViewById(R.id.tv_tr_thn_skrg_m37);
        tvTrThnSkrgKondisi7 = findViewById(R.id.tv_tr_thn_skrg_kondisi7);
        tvTrThnSkrgData7 = findViewById(R.id.tv_tr_thn_skrg_data7);
        tvTrThnSkrgKeterangan7 = findViewById(R.id.tv_tr_thn_skrg_keterangan7);
        tvTrThnLaluStand8 = findViewById(R.id.tv_tr_thn_lalu_stand8);
        tvTrThnLaluM38 = findViewById(R.id.tv_tr_thn_lalu_m38);
        tvTrThnSkrgStand8 = findViewById(R.id.tv_tr_thn_skrg_stand8);
        tvTrThnSkrgM38 = findViewById(R.id.tv_tr_thn_skrg_m38);
        tvTrThnSkrgKondisi8 = findViewById(R.id.tv_tr_thn_skrg_kondisi8);
        tvTrThnSkrgData8 = findViewById(R.id.tv_tr_thn_skrg_data8);
        tvTrThnSkrgKeterangan8 = findViewById(R.id.tv_tr_thn_skrg_keterangan8);
        tvTrThnLaluStand9 = findViewById(R.id.tv_tr_thn_lalu_stand9);
        tvTrThnLaluM39 = findViewById(R.id.tv_tr_thn_lalu_m39);
        tvTrThnSkrgStand9 = findViewById(R.id.tv_tr_thn_skrg_stand9);
        tvTrThnSkrgM39 = findViewById(R.id.tv_tr_thn_skrg_m39);
        tvTrThnSkrgKondisi9 = findViewById(R.id.tv_tr_thn_skrg_kondisi9);
        tvTrThnSkrgData9 = findViewById(R.id.tv_tr_thn_skrg_data9);
        tvTrThnSkrgKeterangan9 = findViewById(R.id.tv_tr_thn_skrg_keterangan9);
        tvTrThnLaluStand10 = findViewById(R.id.tv_tr_thn_lalu_stand10);
        tvTrThnLaluM310 = findViewById(R.id.tv_tr_thn_lalu_m310);
        tvTrThnSkrgStand10 = findViewById(R.id.tv_tr_thn_skrg_stand10);
        tvTrThnSkrgM310 = findViewById(R.id.tv_tr_thn_skrg_m310);
        tvTrThnSkrgKondisi10 = findViewById(R.id.tv_tr_thn_skrg_kondisi10);
        tvTrThnSkrgData10 = findViewById(R.id.tv_tr_thn_skrg_data10);
        tvTrThnSkrgKeterangan10 = findViewById(R.id.tv_tr_thn_skrg_keterangan10);
        tvTrThnLaluStand11 = findViewById(R.id.tv_tr_thn_lalu_stand11);
        tvTrThnLaluM311 = findViewById(R.id.tv_tr_thn_lalu_m311);
        tvTrThnSkrgStand11 = findViewById(R.id.tv_tr_thn_skrg_stand11);
        tvTrThnSkrgM311 = findViewById(R.id.tv_tr_thn_skrg_m311);
        tvTrThnSkrgKondisi11 = findViewById(R.id.tv_tr_thn_skrg_kondisi11);
        tvTrThnSkrgData11 = findViewById(R.id.tv_tr_thn_skrg_data11);
        tvTrThnSkrgKeterangan11 = findViewById(R.id.tv_tr_thn_skrg_keterangan11);
        tvTrThnLaluStand12 = findViewById(R.id.tv_tr_thn_lalu_stand12);
        tvTrThnLaluM312 = findViewById(R.id.tv_tr_thn_lalu_m312);
        tvTrThnSkrgStand12 = findViewById(R.id.tv_tr_thn_skrg_stand12);
        tvTrThnSkrgM312 = findViewById(R.id.tv_tr_thn_skrg_m312);
        tvTrThnSkrgKondisi12 = findViewById(R.id.tv_tr_thn_skrg_kondisi12);
        tvTrThnSkrgData12 = findViewById(R.id.tv_tr_thn_skrg_data12);
        tvTrThnSkrgKeterangan12 = findViewById(R.id.tv_tr_thn_skrg_keterangan12);
        btnCariBacaanTahun = findViewById(R.id.btn_cari_bacaan_tahun);
        tvFotoMeterTahun = findViewById(R.id.tv_foto_meter_tahun);
        tvBulanStandM31 = findViewById(R.id.tv_bulan_stand_m31);
        ivFotoMeter1 = findViewById(R.id.iv_foto_meter_1);
        tvBulanStandM32 = findViewById(R.id.tv_bulan_stand_m3_2);
        ivFotoMeter2 = findViewById(R.id.iv_foto_meter_2);
        tvBulanStandM33 = findViewById(R.id.tv_bulan_stand_m3_3);
        ivFotoMeter3 = findViewById(R.id.iv_foto_meter_3);
        tvBulanStandM34 = findViewById(R.id.tv_bulan_stand_m3_4);
        ivFotoMeter4 = findViewById(R.id.iv_foto_meter_4);
        tvBulanStandM35 = findViewById(R.id.tv_bulan_stand_m3_5);
        ivFotoMeter5 = findViewById(R.id.iv_foto_meter_5);
        tvBulanStandM36 = findViewById(R.id.tv_bulan_stand_m3_6);
        ivFotoMeter6 = findViewById(R.id.iv_foto_meter_6);
        tvBulanStandM37 = findViewById(R.id.tv_bulan_stand_m3_7);
        ivFotoMeter7 = findViewById(R.id.iv_foto_meter_7);
        tvBulanStandM38 = findViewById(R.id.tv_bulan_stand_m3_8);
        ivFotoMeter8 = findViewById(R.id.iv_foto_meter_8);
        tvBulanStandM39 = findViewById(R.id.tv_bulan_stand_m3_9);
        ivFotoMeter9 = findViewById(R.id.iv_foto_meter_9);
        tvBulanStandM310 = findViewById(R.id.tv_bulan_stand_m3_10);
        ivFotoMeter10 = findViewById(R.id.iv_foto_meter_10);
        tvBulanStandM311 = findViewById(R.id.tv_bulan_stand_m3_11);
        ivFotoMeter11 = findViewById(R.id.iv_foto_meter_11);
        tvBulanStandM312 = findViewById(R.id.tv_bulan_stand_m3_12);
        ivFotoMeter12 = findViewById(R.id.iv_foto_meter_12);
    }
}