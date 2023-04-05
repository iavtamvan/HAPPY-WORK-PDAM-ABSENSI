package com.pdamkotasmg.goodday.fitur.payslip;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;

public class PaySlipActivity extends AppCompatActivity {

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