package co.id.pdamkotasmg.pekerjaanteknik.adapter.postData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.model.post.postRoot.ListBarangLocalItem;

public class BarangTambahanListAdapter extends RecyclerView.Adapter<BarangTambahanListAdapter.ViewHolder> {
    Context context;
    private List<ListBarangLocalItem> dataItem;

    public BarangTambahanListAdapter(Context context, List<ListBarangLocalItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_barang_tambahan, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvKodeBarang.setText(dataItem.get(position).getKdBrg());
        holder.tvNamaBarang.setText(dataItem.get(position).getNm_brg());
        holder.tvJenisBarang.setText(dataItem.get(position).getJns_brg());
        holder.tvMerek.setText(dataItem.get(position).getMerek());
        holder.tvJumlahBarang.setText(dataItem.get(position).getJml());
        holder.tvSatuan.setText(dataItem.get(position).getSatuan());

        holder.divDeleteData.setOnClickListener(view -> {
            dataItem.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeRemoved(position, dataItem.size());

            Log.d("debug", "size : " + dataItem.size());
            Log.d("debug", "size : " + dataItem);

        });
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvKodeBarang;
        private TextView tvNamaBarang;
        private TextView tvJenisBarang;
        private TextView tvMerek;
        private TextView tvJumlahBarang;
        private TextView tvSatuan;
        private LinearLayout divDeleteData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeBarang = itemView.findViewById(R.id.tv_kode_barang);
            tvNamaBarang = itemView.findViewById(R.id.tv_nama_barang);
            tvJenisBarang = itemView.findViewById(R.id.tv_jenis_barang);
            tvMerek = itemView.findViewById(R.id.tv_merek);
            tvJumlahBarang = itemView.findViewById(R.id.tv_jumlah_barang);
            tvSatuan = itemView.findViewById(R.id.tv_satuan);
            divDeleteData = itemView.findViewById(R.id.div_delete_data);
        }
    }
}
