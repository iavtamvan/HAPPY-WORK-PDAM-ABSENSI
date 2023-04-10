package com.pdamkotasmg.goodday.fitur.payslip;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.payslip.model.paySlip.AllowancesItem;
import com.pdamkotasmg.goodday.fitur.payslip.model.paySlip.DeductionsItem;
import com.pdamkotasmg.goodday.fitur.payslip.model.paySlip.PaySlipRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaySlipActivity extends AppCompatActivity {


    private String accessToken;
    private String name, npp;
    private List<AllowancesItem> allowancesItems;
    private List<DeductionsItem> deductionsItems;

    private NumberFormat formatRupiah;

    private String date, period, month, year;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvNamePegawai;
    private TextView tvTanggal;
    private TextView tvNetPayTop;
    private TextView tvSubtotalEarnings;
    private RecyclerView rvAllowances;
    private TextView tvSubtotalDeductions;
    private RecyclerView rvDeductions;
    private TextView tvTotalEarnings;
    private TextView tvTax;
    private TextView tvNetPayBottom;
    private TextView tvTerbilang;
    private TextView tvTransferedTo;
    private LinearLayout divPendapatan;
    private LinearLayout divPotongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_slip);
        getSupportActionBar().hide();
        initView();

        ivHeaderBackArrow.setOnClickListener(v -> {
            PaySlipActivity.this.finish();
        });
        tvHeaderJudul.setText("Payslip");
        ivHeaderInfo.setOnClickListener(v -> {
            Toast.makeText(PaySlipActivity.this, "Horeeee, ayo liburan!", Toast.LENGTH_SHORT).show();
        });

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");

        date = getIntent().getStringExtra(Config.BUNDLE_DATE_PAYSLIP);
        period = getIntent().getStringExtra(Config.BUNDLE_OPT_PAYROLL_PERIOD);
        month = getIntent().getStringExtra(Config.BUNDLE_OPT_PERIOD_MONTH);
        year = getIntent().getStringExtra(Config.BUNDLE_OPT_PERIOD_YEAR);

        tvNamePegawai.setText(name + " (" + npp + ")");
        tvTanggal.setText(date);

        getPaySlip();

    }

    private void getPaySlip() {
        ProgressDialog progressDialog = new ProgressDialog(PaySlipActivity.this);
        progressDialog.setMessage("Menghitung Gaji...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(PaySlipActivity.this);
        apiService.getPaySlip(accessToken, "MOBILE", period, month, year).enqueue(new Callback<PaySlipRootModel>() {
            @Override
            public void onResponse(Call<PaySlipRootModel> call, Response<PaySlipRootModel> response) {
                if (response.isSuccessful()) {

                    if (response.body().getData() == null) {
                        Toast.makeText(PaySlipActivity.this, "Data kosong", Toast.LENGTH_SHORT).show();
                    } else {

                        tvNetPayTop.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getNetPay())));
                        tvNetPayBottom.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getNetPay())));
                        tvTotalEarnings.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getTotalEarnings())));
                        tvTerbilang.setText(response.body().getData().getTerbilang());
                        tvTransferedTo.setText(response.body().getData().getTransferedTo().replace(" - ", "\n"));

                        tvSubtotalEarnings.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getSubtotalEarnings())));
                        tvSubtotalDeductions.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getSubtotalDeductions())));
                        tvTax.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getTax())));

                        allowancesItems = response.body().getData().getAllowances();
                        for (int i = 0; i < allowancesItems.size(); i++) {

                            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.list_item_payslip, null);

                            TextView tvComponentName = view.findViewById(R.id.tv_component_name);
                            TextView tvNominal = view.findViewById(R.id.tv_nominal);

                            tvComponentName.setText(allowancesItems.get(i).getComponentName());
                            tvNominal.setText(formatRupiah.format((double) Double.parseDouble(allowancesItems.get(i).getNominal())));

                            divPendapatan.addView(view);
                        }

                        deductionsItems = response.body().getData().getDeductions();
                        for (int i = 0; i < deductionsItems.size(); i++) {

                            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.list_item_payslip, null);

                            TextView tvComponentName = view.findViewById(R.id.tv_component_name);
                            TextView tvNominal = view.findViewById(R.id.tv_nominal);

                            tvComponentName.setText(deductionsItems.get(i).getComponentName());
                            tvNominal.setText(formatRupiah.format((double) Double.parseDouble(deductionsItems.get(i).getNominal())));

                            divPotongan.addView(view);
                        }

                    }

                    progressDialog.cancel();

                }
            }

            @Override
            public void onFailure(Call<PaySlipRootModel> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(PaySlipActivity.this, "Payslip: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvNamePegawai = findViewById(R.id.tv_name_pegawai);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvNetPayTop = findViewById(R.id.tv_net_pay_top);
        tvSubtotalEarnings = findViewById(R.id.tv_subtotal_earnings);
        rvAllowances = findViewById(R.id.rv_allowances);
        tvSubtotalDeductions = findViewById(R.id.tv_subtotal_deductions);
        rvDeductions = findViewById(R.id.rv_deductions);
        tvTotalEarnings = findViewById(R.id.tv_total_earnings);
        tvTax = findViewById(R.id.tv_tax);
        tvNetPayBottom = findViewById(R.id.tv_net_pay_bottom);
        tvTerbilang = findViewById(R.id.tv_terbilang);
        tvTransferedTo = findViewById(R.id.tv_transfered_to);
        divPendapatan = findViewById(R.id.div_pendapatan);
        divPotongan = findViewById(R.id.div_potongan);
    }
}