package com.pdamkotasmg.happywork.fitur.kehadiran.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.krishna.securetimer.SecureTimer;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.kehadiran.model.DataItem;
import com.pdamkotasmg.happywork.utils.Config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KehadiranAdapter extends RecyclerView.Adapter<KehadiranAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public KehadiranAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_kehadiran, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date currentTimeInMillis = SecureTimer.with(context).getCurrentDate();
        @SuppressLint("SimpleDateFormat") String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(currentTimeInMillis);
        LocalDate dateFrom = LocalDate.parse(formatDate);

        if (dataItems.get(position).getRecordDate().equals("2021-06-05")){
            Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getPhoto()).into(holder.ciListKehadiranMasuk);
            holder.tvListKehadiranDate.setText(dataItems.get(position).getRecordDate());
            holder.tvListKehadiranMasuk.setText(dataItems.get(position).getRecordTime());

            Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getPhoto()).into(holder.ciListKehadiranKeluar);
            holder.tvListKehadiranDate.setText(dataItems.get(position).getRecordDate());
            holder.tvListKehadiranKeluar.setText(dataItems.get(position).getRecordTime());
        }

//        if (dataItems.get(position).getIsShiftIn() == 1) { // masuk
//
//            Log.d("debug", "Status absen: masuk");
//            Log.d("debug", "Status absen Jam: " + dataItems.get(position).getRecordDate());
//        } else { // keluar
//
//            Log.d("debug", "Status absen: keluar");
//            Log.d("debug", "Status absen Jam: " + dataItems.get(position).getRecordDate());
//        }
        Log.d("debug", "Log getID: " + dataItems.get(position).getId());
        Log.d("debug", "Log getCoord: " + dataItems.get(position).getCoord());
        Log.d("debug", "Log getIsShiftIn: " + dataItems.get(position).getIsShiftIn());
        Log.d("debug", "Log getPhoto: " + dataItems.get(position).getPhoto());
        Log.d("debug", "Log getRecordTime: " + dataItems.get(position).getRecordTime());
        Log.d("debug", "Log getRemark: " + dataItems.get(position).getRemark());
        Log.d("debug", "Log getRecordDate: " + dataItems.get(position).getRecordDate());
//        Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getPhoto()).into(holder.ciListKehadiranKeluar);
//        holder.tvListKehadiranDate.setText(dataItems.get(position).getTglPublish());

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private CircleImageView ciListKehadiranMasuk;
        private TextView tvListKehadiranMasuk;
        private CircleImageView ciListKehadiranKeluar;
        private TextView tvListKehadiranKeluar;
        private TextView tvListKehadiranDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            ciListKehadiranMasuk = itemView.findViewById(R.id.ci_list_kehadiran_masuk);
            tvListKehadiranMasuk = itemView.findViewById(R.id.tv_list_kehadiran_masuk);
            ciListKehadiranKeluar = itemView.findViewById(R.id.ci_list_kehadiran_keluar);
            tvListKehadiranKeluar = itemView.findViewById(R.id.tv_list_kehadiran_keluar);
            tvListKehadiranDate = itemView.findViewById(R.id.tv_list_kehadiran_date);
        }
    }
}
