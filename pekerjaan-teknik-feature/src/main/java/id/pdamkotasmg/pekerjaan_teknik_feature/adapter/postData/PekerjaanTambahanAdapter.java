package id.pdamkotasmg.pekerjaan_teknik_feature.adapter.postData;

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

import id.pdamkotasmg.pekerjaan_teknik_feature.R;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.post.postRoot.ListPekerjaanItem;

public class PekerjaanTambahanAdapter extends RecyclerView.Adapter<PekerjaanTambahanAdapter.ViewHolder> {
    Context context;
    private List<ListPekerjaanItem> dataItem;

    public PekerjaanTambahanAdapter(Context context, List<ListPekerjaanItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_pekerjaan, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = 1;
        holder.tvNo.setVisibility(View.GONE);
        holder.tvKodePekerjaanTambahan.setText(dataItem.get(position).getJnsPekrj());
        holder.tvKeteranganPekerjaanTambahan.setText(dataItem.get(position).getJnsPekrjLain());

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
        private TextView tvNo;
        private TextView tvKodePekerjaanTambahan;
        private TextView tvKeteranganPekerjaanTambahan;
        private LinearLayout divDeleteData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNo = itemView.findViewById(R.id.tv_no);
            tvKodePekerjaanTambahan = itemView.findViewById(R.id.tv_kode_pekerjaan_tambahan);
            tvKeteranganPekerjaanTambahan = itemView.findViewById(R.id.tv_keterangan_pekerjaan_tambahan);
            divDeleteData = itemView.findViewById(R.id.div_delete_data);
        }
    }
}
