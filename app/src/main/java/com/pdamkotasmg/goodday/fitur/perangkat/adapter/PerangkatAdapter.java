package com.pdamkotasmg.goodday.fitur.perangkat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.perangkat.model.DataItem;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;

public class PerangkatAdapter extends RecyclerView.Adapter<PerangkatAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public PerangkatAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_riwayat_perangkat, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvListPerangkatVersionAplication.setText(Config.APLICATION_NAME + " v." + dataItems.get(position).getAppVersion());
        holder.tvListPerangkatNameNamaHp.setText(dataItems.get(position).getBuildBrand() + " , Android (" + dataItems.get(position).getOsVersion() + ")");
        holder.tvListPerangkatIpCity.setText(dataItems.get(position).getIpAddress() + " - " + dataItems.get(position).getLocationCity());
        holder.tvListPerangkatStatusTgl.setText(dataItems.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvListPerangkatVersionAplication;
        private TextView tvListPerangkatNameNamaHp;
        private TextView tvListPerangkatIpCity;
        private TextView tvListPerangkatStatusTgl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListPerangkatVersionAplication = itemView.findViewById(R.id.tv_list_perangkat_version_aplication);
            tvListPerangkatNameNamaHp = itemView.findViewById(R.id.tv_list_perangkat_name_nama_hp);
            tvListPerangkatIpCity = itemView.findViewById(R.id.tv_list_perangkat_ip_city);
            tvListPerangkatStatusTgl = itemView.findViewById(R.id.tv_list_perangkat_status_tgl);
        }
    }
}
