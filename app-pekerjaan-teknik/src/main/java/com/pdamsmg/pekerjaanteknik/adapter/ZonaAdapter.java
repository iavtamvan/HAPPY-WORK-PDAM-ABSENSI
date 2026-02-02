package com.pdamsmg.pekerjaanteknik.adapter;

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

import com.pdamsmg.pekerjaanteknik.R;
import com.pdamsmg.pekerjaanteknik.model.pegawai.DataItem;
import com.pdamsmg.pekerjaanteknik.model.zona.ZonaRootModelItem;

import java.util.List;

public class ZonaAdapter extends RecyclerView.Adapter<ZonaAdapter.ViewHolder> {
    Context context;
    private List<ZonaRootModelItem> dataItem;

    public ZonaAdapter(Context context, List<ZonaRootModelItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_zona, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvKodeZona.setText(dataItem.get(position).getKDZONA());
        holder.tvNamaZona.setText(dataItem.get(position).getNMZONA());
        holder.tvJenisZona.setText(dataItem.get(position).getKET());
        holder.cvKlik.setOnClickListener(v -> {
            Intent source = new Intent();
            source.putExtra("kode_zona", holder.tvKodeZona.getText().toString().trim());
            source.putExtra("ket_zona", holder.tvNamaZona.getText().toString().trim());
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
        private TextView tvKodeZona;
        private TextView tvNamaZona;
        private TextView tvJenisZona;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvKodeZona = itemView.findViewById(R.id.tv_kode_zona);
            tvNamaZona = itemView.findViewById(R.id.tv_nama_zona);
            tvJenisZona = itemView.findViewById(R.id.tv_jenis_zona);
        }
    }
}
