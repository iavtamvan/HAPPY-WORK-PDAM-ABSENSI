package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.detailKoreksiKehadiran.Data;

import java.text.SimpleDateFormat;
import java.util.List;

public class DetailsKehadiranAdapter extends RecyclerView.Adapter<DetailsKehadiranAdapter.ViewHolder> {
    Context context;
    private List<Data> dataItems;
    private String dateServer;

    private final String TAG = "debug";

    public DetailsKehadiranAdapter(Context context, List<Data> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_koreksi_kehadiran_details, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dateServer = new SimpleDateFormat("EEE, dd MMM yyyy").format(dataItems.get(position).getRequestedAt());

        holder.tvDetailsTanggal.setText(dateServer);
        holder.tvDetailsStatus.setText(dataItems.get(position).getRequestStatus());

        // Before
        holder.tvDetailsShiftDanWaktuInWaktuOutBefore.setText(dataItems.get(position).getRequestDetails().get(position).getRecordDateBefore());
        holder.tvDetailsTimeInBefore.setText(dataItems.get(position).getRequestDetails().get(position).getTimeInBefore());
        holder.tvDetailsTimeOutBefore.setText(dataItems.get(position).getRequestDetails().get(position).getTimeOutBefore());

        // After
        holder.tvDetailsShiftDanWaktuInWaktuOutAfter.setText(dataItems.get(position).getRequestDetails().get(position).getRecordDateAfter());
        holder.tvDetailsTimeInAter.setText(dataItems.get(position).getRequestDetails().get(position).getTimeInAfter());
        holder.tvDetailsTimeOutAfter.setText(dataItems.get(position).getRequestDetails().get(position).getTimeOutAfter());

        holder.tvDetailsReason.setText(dataItems.get(position).getRequestDetails().get(position).getReason());

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private TextView tvDetailsTanggal;
        private TextView tvDetailsShiftDanWaktuInWaktuOutBefore;
        private TextView tvDetailsTimeInBefore;
        private TextView tvDetailsTimeOutBefore;
        private TextView tvDetailsShiftDanWaktuInWaktuOutAfter;
        private TextView tvDetailsTimeInAter;
        private TextView tvDetailsTimeOutAfter;
        private TextView tvDetailsReason;
        private TextView tvDetailsStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvDetailsTanggal = itemView.findViewById(R.id.tv_details_tanggal);
            tvDetailsShiftDanWaktuInWaktuOutBefore = itemView.findViewById(R.id.tv_details_shift_dan_waktuIn_waktuOut_before);
            tvDetailsTimeInBefore = itemView.findViewById(R.id.tv_details_time_in_before);
            tvDetailsTimeOutBefore = itemView.findViewById(R.id.tv_details_time_out_before);
            tvDetailsShiftDanWaktuInWaktuOutAfter = itemView.findViewById(R.id.tv_details_shift_dan_waktuIn_waktuOut_after);
            tvDetailsTimeInAter = itemView.findViewById(R.id.tv_details_time_in_ater);
            tvDetailsTimeOutAfter = itemView.findViewById(R.id.tv_details_time_out_after);
            tvDetailsReason = itemView.findViewById(R.id.tv_details_reason);
            tvDetailsStatus = itemView.findViewById(R.id.tv_details_status);
        }
    }
}
