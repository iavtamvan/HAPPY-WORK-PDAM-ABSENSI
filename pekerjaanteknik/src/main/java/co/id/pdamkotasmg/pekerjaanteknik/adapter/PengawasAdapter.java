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
import co.id.pdamkotasmg.pekerjaanteknik.model.pegawai.DataItem;

public class PengawasAdapter extends RecyclerView.Adapter<PengawasAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItem;

    public PengawasAdapter(Context context, List<DataItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_pegawai, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNpp.setText(dataItem.get(position).getNpp());
        holder.tvNamePegawai.setText(dataItem.get(position).getName());
        holder.cvKlik.setOnClickListener(v -> {
            Intent source = new Intent();
            source.putExtra("npp", holder.tvNpp.getText().toString().trim());
            source.putExtra("name",holder.tvNamePegawai.getText().toString().trim());
            ((Activity) context).setResult(Activity.RESULT_OK, source);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNpp;
        private TextView tvNamePegawai;
        private CardView cvKlik;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNpp = itemView.findViewById(R.id.tv_npp);
            tvNamePegawai = itemView.findViewById(R.id.tv_name_pegawai);
            cvKlik = itemView.findViewById(R.id.cv_klik);
        }
    }
}
