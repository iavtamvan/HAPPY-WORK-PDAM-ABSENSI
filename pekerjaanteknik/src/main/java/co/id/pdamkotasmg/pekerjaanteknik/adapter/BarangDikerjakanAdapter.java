package co.id.pdamkotasmg.pekerjaanteknik.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.model.barang.DataItem;

public class BarangDikerjakanAdapter extends RecyclerView.Adapter<BarangDikerjakanAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItem;

    public BarangDikerjakanAdapter(Context context, List<DataItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_barang_dikerjakan, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvKodeBarang.setText(dataItem.get(position).getKdBrg());
//        if (dataItem.get(position).getNmBrg().contains("�")){
        String data = dataItem.get(position).getNmBrg();
        String rpl = data.replace("�", "Ø").replace("º", "°");
        Log.d("debug", "onBindViewHolder: " + rpl);
        holder.tvNamaBarang.setText(rpl);
//        }
//        holder.tvNamaBarang.setText(dataItem.get(position).getNmBrg());
//        holder.tvJenisBarang.setText(dataItem.get(position).getRlBarangJenis1().getNmJns1());
//        holder.tvMerek.setText("-");
//        holder.tvSatuan.setText(dataItem.get(position).getSatuan());
        holder.cvKlik.setOnClickListener(v -> {
            Intent source = new Intent();
            source.putExtra("kode", holder.tvKodeBarang.getText().toString().trim());
            source.putExtra("nm_brg", holder.tvNamaBarang.getText().toString().trim());
            source.putExtra("jns_pipa", dataItem.get(position).getRlBarangJenis1().getNmJns1());
            source.putExtra("diameter", dataItem.get(position).getDiameter());
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
        private TextView tvKodeBarang;
        private TextView tvNamaBarang;
        private TextView tvJenisBarang;
        private TextView tvMerek;
        private TextView tvSatuan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvKodeBarang = itemView.findViewById(R.id.tv_kode_barang);
            tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
            tvJenisBarang = itemView.findViewById(R.id.tv_jenis_barang);
            tvMerek = itemView.findViewById(R.id.tv_merek);
            tvSatuan = itemView.findViewById(R.id.tv_satuan);
        }
    }
}
