package com.pdamkotasmg.happywork.fitur.kehadiran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.feeds.model.DataItem;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load("https://www.pdamkotasmg.co.id/packages/upload/photo/" + dataItems.get(position).getFav()).into(holder.ciListKehadiranMasuk);
        Glide.with(context).load("https://www.pdamkotasmg.co.id/packages/upload/photo/" + dataItems.get(position).getFav()).into(holder.ciListKehadiranKeluar);
        holder.tvListKehadiranDate.setText(dataItems.get(position).getTglPublish());
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
