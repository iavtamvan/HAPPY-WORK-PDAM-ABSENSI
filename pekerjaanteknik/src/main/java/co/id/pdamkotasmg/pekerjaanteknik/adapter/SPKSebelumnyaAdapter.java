package co.id.pdamkotasmg.pekerjaanteknik.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.model.spk.DataItem;

public class SPKSebelumnyaAdapter extends RecyclerView.Adapter<SPKSebelumnyaAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItem;

    public SPKSebelumnyaAdapter(Context context, List<DataItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_spk_sebelumnya, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNoSpk.setText(dataItem.get(position).getNoSpk());
        holder.tvLokasi.setText(dataItem.get(position).getAlamat());
        holder.tvTglPelaksanaan.setText(dataItem.get(position).getRlPelaksana().getTglPelaksana());
        holder.tvUraianPekerjaan.setText(dataItem.get(position).getUraian());

        String statusPekerjaan = dataItem.get(position).getRlPelaksana().getStatus();
        if (statusPekerjaan.equalsIgnoreCase("1")) {
            statusPekerjaan = "Selesai";
        } else {
            statusPekerjaan = "Blm Selesai";
        }
        holder.tvSatuan.setText(statusPekerjaan);
        holder.cvKlik.setOnClickListener(v -> {
            Intent source = new Intent();
            source.putExtra("nospk", holder.tvNoSpk.getText().toString().trim());
            source.putExtra("tgl", holder.tvTglPelaksanaan.getText().toString().trim());
            ((Activity) context).setResult(Activity.RESULT_OK, source);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private TextView tvNoSpk;
        private TextView tvLokasi;
        private TextView tvTglPelaksanaan;
        private TextView tvUraianPekerjaan;
        private TextView tvSatuan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvNoSpk = itemView.findViewById(R.id.tv_no_spk);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvTglPelaksanaan = itemView.findViewById(R.id.tv_tgl_pelaksanaan);
            tvUraianPekerjaan = itemView.findViewById(R.id.tv_uraian_pekerjaan);
            tvSatuan = itemView.findViewById(R.id.tv_satuan);
        }
    }
}
