package com.pdamkotasmg.goodday.fitur.payslip;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
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

        tvNamePegawai.setText(name + " (" + npp + ")");

        getPaySlip();

    }

    private void getPaySlip() {
        ApiService apiService = ApiConfig.getApiService(PaySlipActivity.this);
        apiService.getPaySlip(accessToken, "MOBILE", "MTH01", "11", "2022")
                .enqueue(new Callback<PaySlipRootModel>() {
                    @Override
                    public void onResponse(Call<PaySlipRootModel> call, Response<PaySlipRootModel> response) {
                        if (response.isSuccessful()){

                            tvNetPayTop.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getNetPay())));
                            tvNetPayBottom.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getNetPay())));
                            tvTotalEarnings.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getTotalEarnings())));
                            tvTerbilang.setText(response.body().getData().getTerbilang());
                            tvTransferedTo.setText(response.body().getData().getTransferedTo().replace(" - ", "\n"));

                            tvSubtotalEarnings.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getSubtotalEarnings())));
                            tvSubtotalDeductions.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getSubtotalDeductions())));
                            tvTax.setText(formatRupiah.format((double) Double.parseDouble(response.body().getData().getTax())));

                        }
                    }

                    @Override
                    public void onFailure(Call<PaySlipRootModel> call, Throwable t) {
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
    }
}