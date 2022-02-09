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
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.detailKoreksiKehadiran.RequestDetailsItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailsKehadiranAdapter extends RecyclerView.Adapter<DetailsKehadiranAdapter.ViewHolder> {
    Context context;
    private List<RequestDetailsItem> dataItems;
    private String dateServer;

    private final String TAG = "debug";

    public DetailsKehadiranAdapter(Context context, List<RequestDetailsItem> dataItems) {
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

//        dateServer = new SimpleDateFormat("EEE, dd MMM yyyy").format(dataItems.getRequestedAt());

//        holder.tvDetailsTanggal.setText(dateServer);
//        holder.tvDetailsStatus.setText(dataItems.get(position).getRequestStatus());

        // TODO convert Tanggal dari server
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date getRecordDateBefore = date.parse(dataItems.get(position).getRecordDateBefore());
            Date getRecordDateAfter = date.parse(dataItems.get(position).getRecordDateAfter());

            String dateRecordDateBeforeFix = new SimpleDateFormat("EEE, dd MMM yyyy").format(getRecordDateBefore);
            String dateRecordDateAfterFix = new SimpleDateFormat("EEE, dd MMM yyyy").format(getRecordDateAfter);

            holder.tvDetailsShiftDanWaktuInWaktuOutBefore.setText(dateRecordDateBeforeFix);
            holder.tvDetailsShiftDanWaktuInWaktuOutAfter.setText(dateRecordDateAfterFix);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Before
//        holder.tvDetailsShiftDanWaktuInWaktuOutBefore.setText(dataItems.get(position).getRecordDateBefore());
        holder.tvDetailsTimeInBefore.setText(dataItems.get(position).getTimeInBefore());
        holder.tvDetailsTimeOutBefore.setText(dataItems.get(position).getTimeOutBefore());

        // After
//        holder.tvDetailsShiftDanWaktuInWaktuOutAfter.setText(dataItems.get(position).getRecordDateAfter());
        holder.tvDetailsTimeInAter.setText(dataItems.get(position).getTimeInAfter());
        holder.tvDetailsTimeOutAfter.setText(dataItems.get(position).getTimeOutAfter());

        holder.tvDetailsReason.setText(dataItems.get(position).getReason());

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private TextView tvDetailsShiftDanWaktuInWaktuOutBefore;
        private TextView tvDetailsTimeInBefore;
        private TextView tvDetailsTimeOutBefore;
        private TextView tvDetailsShiftDanWaktuInWaktuOutAfter;
        private TextView tvDetailsTimeInAter;
        private TextView tvDetailsTimeOutAfter;
        private TextView tvDetailsReason;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvDetailsShiftDanWaktuInWaktuOutBefore = itemView.findViewById(R.id.tv_details_shift_dan_waktuIn_waktuOut_before);
            tvDetailsTimeInBefore = itemView.findViewById(R.id.tv_details_time_in_before);
            tvDetailsTimeOutBefore = itemView.findViewById(R.id.tv_details_time_out_before);
            tvDetailsShiftDanWaktuInWaktuOutAfter = itemView.findViewById(R.id.tv_details_shift_dan_waktuIn_waktuOut_after);
            tvDetailsTimeInAter = itemView.findViewById(R.id.tv_details_time_in_ater);
            tvDetailsTimeOutAfter = itemView.findViewById(R.id.tv_details_time_out_after);
            tvDetailsReason = itemView.findViewById(R.id.tv_details_reason);
        }
    }
}
