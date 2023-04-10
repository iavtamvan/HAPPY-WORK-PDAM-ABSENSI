package com.pdamkotasmg.goodday.fitur.payslip.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.payslip.PaySlipActivity;
import com.pdamkotasmg.goodday.fitur.payslip.model.DataItem;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalaryHistoryAdapter extends RecyclerView.Adapter<SalaryHistoryAdapter.ViewHolder> {
    Context context;
    private String getPass;
    private List<DataItem> dataItems;

    public SalaryHistoryAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_salary_history, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO convert Tanggal dari server
        SimpleDateFormat dateNtime = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateRequestAt = dateNtime.parse(dataItems.get(position).getPayDate());

            String dateRequestAtFix = new SimpleDateFormat("EEE, dd MMM yyyy").format(dateRequestAt);
            holder.tvListPayslipDate.setText(dateRequestAtFix);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getPass = sharedPreferences.getString(Config.SHARED_GETPASSWORD, "");

        holder.tvListPayslipKeteranganPayslip.setText(dataItems.get(position).getPayrollPeriodRemark());
        holder.cvKlik.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialogPaySlip = new BottomSheetDialog(context);
            bottomSheetDialogPaySlip.setContentView(R.layout.bottom_sheet_dialog_open_payslip);
            TextView tvKeteranganMembukaPayslip = bottomSheetDialogPaySlip.findViewById(R.id.tv_keterangan_membuka_payslip);
            EditText edtBottomDialogInputPass = bottomSheetDialogPaySlip.findViewById(R.id.edt_bottom_dialog_input_pass);
            Button btnSendMatchPassword = bottomSheetDialogPaySlip.findViewById(R.id.btn_send_match_password);

            tvKeteranganMembukaPayslip.setText(dataItems.get(position).getPayrollPeriod() + " - " + holder.tvListPayslipDate.getText().toString().trim());

            btnSendMatchPassword.setOnClickListener(view1 -> {
                if (!edtBottomDialogInputPass.getText().toString().equals(getPass)){
                    Toast.makeText(context, "Password salah", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, PaySlipActivity.class);
                    intent.putExtra(Config.BUNDLE_DATE_PAYSLIP, holder.tvListPayslipDate.getText().toString().trim());
                    intent.putExtra(Config.BUNDLE_OPT_PAYROLL_PERIOD, dataItems.get(position).getPayrollPeriod());
                    intent.putExtra(Config.BUNDLE_OPT_PERIOD_MONTH, dataItems.get(position).getPeriodMonth());
                    intent.putExtra(Config.BUNDLE_OPT_PERIOD_YEAR, dataItems.get(position).getPeriodYear());
                    context.startActivity(intent);
                }
            });


            bottomSheetDialogPaySlip.show();
        });
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvListPayslipDate;
        private TextView tvListPayslipKeteranganPayslip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvListPayslipDate = itemView.findViewById(R.id.tv_list_payslip_date);
            tvListPayslipKeteranganPayslip = itemView.findViewById(R.id.tv_list_payslip_keterangan_payslip);
        }
    }
}
