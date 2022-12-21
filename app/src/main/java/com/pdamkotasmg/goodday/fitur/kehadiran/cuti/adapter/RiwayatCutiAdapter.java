package com.pdamkotasmg.goodday.fitur.kehadiran.cuti.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.activity.DetailCutiActivity;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti.DataItem;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RiwayatCutiAdapter extends RecyclerView.Adapter<RiwayatCutiAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    private String reason;

    public RiwayatCutiAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_riwayat_rac_rlv_rot_rod, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO convert Tanggal dari server
        SimpleDateFormat dateNtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dateRequestAt = dateNtime.parse(dataItems.get(position).getRequestedAt());

            String dateRequestAtFix = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss").format(dateRequestAt);
            holder.tvListRequestAt.setText("Tanggal: " + dateRequestAtFix);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvListRequestBy.setText("Permintaan dari: " + dataItems.get(position).getRequestedByName());
        holder.tvListRequestFor.setText("Permintaan untuk: " + dataItems.get(position).getRequestedForName());
        holder.tvListRequestNumber.setText(dataItems.get(position).getRequestNumber());
        holder.tvListRequestStatus.setText(dataItems.get(position).getRequestStatus());
        if (holder.tvListRequestStatus.getText().toString().equalsIgnoreCase("Waiting")) {
            holder.tvListRequestStatus.setTextColor(context.getResources().getColor(R.color.yellowPortal));
        } else if (holder.tvListRequestStatus.getText().toString().equalsIgnoreCase("Approved")) {
            holder.tvListRequestStatus.setTextColor(context.getResources().getColor(R.color.greenPortal));
        } else {
            holder.tvListRequestStatus.setTextColor(context.getResources().getColor(R.color.redPortal));
        }

        holder.cvKlik.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailCutiActivity.class);
            intent.putExtra(Config.BUNDLE_NUMBER_REQUEST, dataItems.get(position).getRequestNumber());
            intent.putExtra(Config.BUNDLE_NUMBER_APPROVALS, "2");
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvListRequestNumber;
        private TextView tvListRequestAt;
        private TextView tvListRequestBy;
        private TextView tvListRequestFor;
        private TextView tvListRequestStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvListRequestNumber = itemView.findViewById(R.id.tv_list_request_number);
            tvListRequestAt = itemView.findViewById(R.id.tv_list_request_at);
            tvListRequestBy = itemView.findViewById(R.id.tv_list_request_by);
            tvListRequestFor = itemView.findViewById(R.id.tv_list_request_for);
            tvListRequestStatus = itemView.findViewById(R.id.tv_list_request_status);
        }
    }
}
