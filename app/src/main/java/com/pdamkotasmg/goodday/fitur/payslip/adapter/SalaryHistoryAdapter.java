package com.pdamkotasmg.goodday.fitur.payslip.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.payslip.model.DataItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalaryHistoryAdapter extends RecyclerView.Adapter<SalaryHistoryAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public SalaryHistoryAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_payslip, parent, false);
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

        holder.tvListPayslipKeteranganPayslip.setText(dataItems.get(position).getPayrollPeriodRemark());
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
