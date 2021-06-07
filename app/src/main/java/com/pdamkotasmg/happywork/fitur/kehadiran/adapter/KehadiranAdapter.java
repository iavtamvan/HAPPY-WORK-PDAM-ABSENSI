package com.pdamkotasmg.happywork.fitur.kehadiran.adapter;

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
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.kehadiran.model.DataItem;
import com.pdamkotasmg.happywork.utils.Config;

import java.text.SimpleDateFormat;
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
//        try {
//            @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy");
//            Date date = fmt.parse(dataItems.get(position).getRecordDate());
//            holder.tvListKehadiranDate.setText(String.valueOf(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        String dateServer = new SimpleDateFormat("EEE, dd MMM yyyy").format(dataItems.get(position).getRecordDate());
        holder.tvListKehadiranDate.setText(dateServer);

        Log.d("debug", "Presensi In : " + dataItems.get(position).getIn());
        Log.d("debug", String.valueOf("Presensi Out : " + dataItems.get(position).getOut() == null));

        if (dataItems.get(position).getIn() != null) {
            // masuk
            Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getIn().getPhoto()).into(holder.ciListKehadiranMasuk);
            holder.tvListKehadiranMasuk.setText(dataItems.get(position).getIn().getRecordTime());
        }
        if (dataItems.get(position).getOut() != null) {
            // keluar
            Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getOut().getPhoto()).into(holder.ciListKehadiranKeluar);
            holder.tvListKehadiranKeluar.setText(dataItems.get(position).getOut().getRecordTime());
        }

//        if (!dataItems.get(position).getIn().equals(null) && !dataItems.get(position).getOut().equals(null)) {
//            Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
//        } else {
//            // masuk
//            Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getIn().getPhoto()).into(holder.ciListKehadiranMasuk);
//            holder.tvListKehadiranMasuk.setText(dataItems.get(position).getIn().getRecordTime());
//
//            // keluar
//            Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getOut().getPhoto()).into(holder.ciListKehadiranKeluar);
//            holder.tvListKehadiranKeluar.setText(dataItems.get(position).getOut().getRecordTime());
//        }

//        if (dataItems.get(position).getIsShiftIn() == 1) { // masuk
//
//            Log.d("debug", "Status absen: masuk");
//            Log.d("debug", "Status absen Jam: " + dataItems.get(position).getRecordDate());
//        } else { // keluar
//
//            Log.d("debug", "Status absen: keluar");
//            Log.d("debug", "Status absen Jam: " + dataItems.get(position).getRecordDate());
//        }

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
