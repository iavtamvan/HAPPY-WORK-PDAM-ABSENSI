package com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.TunggakanItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TunggakanAdapter extends RecyclerView.Adapter<TunggakanAdapter.ViewHolder> {
    private Context context;
    private List<TunggakanItem> dataItems;

    public TunggakanAdapter(Context context, List<TunggakanItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_tunggakan, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        int incrementedNumber = position + 1; // Increment starting from 1
        holder.tvUrutTunggakan.setText("" + incrementedNumber + ". ");
        holder.tvPeriodeTunggakan.setText(dataItems.get(position).getPeriode() + " - " + dataItems.get(position).getBulanName() + " - " + dataItems.get(position).getPemakaian());
        holder.tvTagihanTunggakan.setText(formatRupiah.format((double) Integer.parseInt(String.valueOf(dataItems.get(position).getTagihan()))));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUrutTunggakan;
        private TextView tvPeriodeTunggakan;
        private TextView tvTagihanTunggakan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUrutTunggakan = itemView.findViewById(R.id.tv_urut_tunggakan);
            tvPeriodeTunggakan = itemView.findViewById(R.id.tv_periode_tunggakan);
            tvTagihanTunggakan = itemView.findViewById(R.id.tv_tagihan_tunggakan);
        }
    }
}