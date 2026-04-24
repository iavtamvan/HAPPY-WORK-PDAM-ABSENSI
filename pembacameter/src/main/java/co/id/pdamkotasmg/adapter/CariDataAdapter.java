package co.id.pdamkotasmg.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;

import co.id.pdamkotasmg.model.cariData.DataItem;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.ui.activity.DetailRiwayatPembacaMeterActivity;

public class CariDataAdapter extends RecyclerView.Adapter<CariDataAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public CariDataAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_table_cari_data, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvListNolangg.setText(dataItems.get(position).getNolangg());
        holder.tvListDism.setText(dataItems.get(position).getDism());
        holder.tvListNama.setText(dataItems.get(position).getNama());
        holder.tvAlamat.setText(dataItems.get(position).getAlamat());

        holder.tvListMerek.setText(dataItems.get(position).getMerek());
        holder.tvListNoMeter.setText(dataItems.get(position).getNomormtr());
        holder.tvStatusPelanggan.setText(dataItems.get(position).getRlStatusPelanggan().getNmStplg());
        holder.tvCabang.setText(dataItems.get(position).getRlCabang().getNmCabang());

        holder.cvKlik.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailRiwayatPembacaMeterActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, dataItems.get(position).getNolangg());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private TextView tvListNolangg;
        private TextView tvListDism;
        private TextView tvListMerek;
        private TextView tvListNoMeter;
        private TextView tvListNama;
        private TextView tvAlamat;
        private TextView tvStatusPelanggan;
        private TextView tvCabang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvListNolangg = itemView.findViewById(R.id.tv_list_nolangg);
            tvListDism = itemView.findViewById(R.id.tv_list_dism);
            tvListMerek = itemView.findViewById(R.id.tv_list_merek);
            tvListNoMeter = itemView.findViewById(R.id.tv_list_no_meter);
            tvListNama = itemView.findViewById(R.id.tv_list_nama);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvStatusPelanggan = itemView.findViewById(R.id.tv_status_pelanggan);
            tvCabang = itemView.findViewById(R.id.tv_cabang);

        }
    }
}
