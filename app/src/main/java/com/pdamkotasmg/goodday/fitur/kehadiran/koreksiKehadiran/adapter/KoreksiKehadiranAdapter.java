package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.riwayatKoreksiKehadiran.DataItem;

import java.util.List;

public class KoreksiKehadiranAdapter extends RecyclerView.Adapter<KoreksiKehadiranAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    private String reason;

    public KoreksiKehadiranAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_koreksi_kehadiran, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        for (int i = 0; i < dataItems.get(position).getRequestDetails().size(); i++) {
//            reason = dataItems.get(position).getRequestDetails().get(i).getReason();
//            Log.d("debug", "onBindViewHolder: " + reason);
//        }
//        holder.tvListKoreksiKehadiranReason.setText(reason);
        holder.tvListKoreksiKehadiranRequestAt.setText("Tanggal: " + dataItems.get(position).getRequestedAt());
        holder.tvListKoreksiKehadiranRequestBy.setText("Permintaan dari: " + dataItems.get(position).getRequestedByName());
        holder.tvListKoreksiKehadiranRequestFor.setText("Permintaan untuk: " + dataItems.get(position).getRequestedForName());
        holder.tvListKoreksiKehadiranRequestNumber.setText(dataItems.get(position).getRequestNumber());
        holder.tvListKoreksiKehadiranRequestStatus.setText(dataItems.get(position).getRequestStatus());
        if (holder.tvListKoreksiKehadiranRequestStatus.getText().toString().equalsIgnoreCase("Waiting")){
            holder.tvListKoreksiKehadiranRequestStatus.setTextColor(context.getResources().getColor(R.color.yellowPortal));
        } else if (holder.tvListKoreksiKehadiranRequestStatus.getText().toString().equalsIgnoreCase("Approved")){
            holder.tvListKoreksiKehadiranRequestStatus.setTextColor(context.getResources().getColor(R.color.greenPortal));
        } else {
            holder.tvListKoreksiKehadiranRequestStatus.setTextColor(context.getResources().getColor(R.color.redPortal));
        }

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvListKoreksiKehadiranReason;
        private TextView tvListKoreksiKehadiranRequestAt;
        private TextView tvListKoreksiKehadiranRequestFor;
        private TextView tvListKoreksiKehadiranRequestBy;
        private TextView tvListKoreksiKehadiranRequestNumber;
        private TextView tvListKoreksiKehadiranRequestStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvListKoreksiKehadiranReason = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_reason);
            tvListKoreksiKehadiranRequestAt = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_request_at);
            tvListKoreksiKehadiranRequestFor = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_request_for);
            tvListKoreksiKehadiranRequestBy = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_request_by);
            tvListKoreksiKehadiranRequestNumber = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_request_number);
            tvListKoreksiKehadiranRequestStatus = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_request_status);
        }
    }
}
