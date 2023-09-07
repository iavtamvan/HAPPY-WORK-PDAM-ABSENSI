package co.id.pdamkotasmg.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;

import co.id.pdamkotasmg.model.riwayatBacaMeter.DataItem;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.ui.activity.DetailRiwayatPembacaMeterActivity;

public class RiwayatBacaMeterAdapter extends RecyclerView.Adapter<RiwayatBacaMeterAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public RiwayatBacaMeterAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_riwayat_pembaca_meter, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SharedPreferences sp = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        holder.tvListNolangg.setText(dataItems.get(position).getNolangg());
        holder.tvListDism.setText(dataItems.get(position).getDism());
        holder.tvListPeriode.setText(dataItems.get(position).getPeriode());
        holder.tvListNama.setText(dataItems.get(position).getRlPelanggan().getNama());
        holder.tvListTglJamBaca.setText(dataItems.get(position).getTglBaca() + " " + dataItems.get(position).getJamBaca());

        if (dataItems.get(position).getTglBaca().isEmpty()) {
            holder.tvListStatusDibaca.setText("Belum Dibaca");
            holder.tvListStatusDibaca.setTextColor(Color.parseColor("#dc3545"));
        } else {
            holder.tvListStatusDibaca.setText("Sudah Dibaca");
            holder.tvListStatusDibaca.setTextColor(Color.parseColor("#31B057"));
        }

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
        private LinearLayout cvKlik;
        private TextView tvListNolangg;
        private TextView tvListDism;
        private TextView tvListPeriode;
        private TextView tvListNama;
        private TextView tvListAlamat;
        private TextView tvListSt;
        private TextView tvListTglJamBaca;
        private TextView tvListStatusDibaca;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cvKlik);
            cvKlik = itemView.findViewById(R.id.cvKlik);
            tvListNolangg = itemView.findViewById(R.id.tv_list_nolangg);
            tvListDism = itemView.findViewById(R.id.tv_list_dism);
            tvListPeriode = itemView.findViewById(R.id.tv_list_periode);
            tvListNama = itemView.findViewById(R.id.tv_list_nama);
            tvListAlamat = itemView.findViewById(R.id.tv_list_alamat);
            tvListSt = itemView.findViewById(R.id.tv_list_st);
            tvListTglJamBaca = itemView.findViewById(R.id.tv_list_tgl_jam_baca);
            tvListStatusDibaca = itemView.findViewById(R.id.tv_list_status_dibaca);
        }
    }
}
