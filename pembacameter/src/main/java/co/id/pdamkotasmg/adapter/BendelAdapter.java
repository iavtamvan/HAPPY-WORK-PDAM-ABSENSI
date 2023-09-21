package co.id.pdamkotasmg.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import co.id.pdamkotasmg.model.bendel.DataItem;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.ui.activity.PembacaMeterActivity;

public class BendelAdapter extends RecyclerView.Adapter<BendelAdapter.ViewHolder> {
    Context context;
    private String periode;
    private String cabang;
    private String codeInputData;
    private List<DataItem> dataItems;

    public BendelAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_bendel, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SharedPreferences sp = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        periode = sp.getString(Config.SHARED_PERIODE, "");
        cabang = sp.getString(Config.SHARED_CABANG, "");

        holder.tvListBendelNolangg.setText(dataItems.get(position).getNolangg());
        holder.tvListBendelDism.setText(dataItems.get(position).getDism());
        holder.tvListBendelNama.setText(dataItems.get(position).getNama());
        holder.tvListBendelAlamat.setText(dataItems.get(position).getAlamat());

        String st = dataItems.get(position).getSt();
        if (st.contains("2")) {
            st = "Aktif";
        } else {
            st = "Tutup";
        }

        holder.tvListBendelSt.setText(st);
        holder.tvListBendelLalu.setText(dataItems.get(position).getRlTrbaca().get(0).getKini() + " - "
                + dataItems.get(position).getRlTrbaca().get(0).getM3() + "m3");
        holder.tvListBendelDibuat.setText("Generate by System Cabang " + cabang + "-" + periode);

        holder.cvKlik.setOnClickListener(v -> {
            codeInputData = "1";
            Intent intent = new Intent(context, PembacaMeterActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, dataItems.get(position).getNolangg());
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA, codeInputData);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvListBendelNolangg;
        private TextView tvListBendelDism;
        private TextView tvListBendelNama;
        private TextView tvListBendelAlamat;
        private TextView tvListBendelSt;
        private TextView tvListBendelLalu;
        private TextView tvListBendelDibuat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cvKlik);
            tvListBendelNolangg = itemView.findViewById(R.id.tv_list_bendel_nolangg);
            tvListBendelDism = itemView.findViewById(R.id.tv_list_bendel_dism);
            tvListBendelNama = itemView.findViewById(R.id.tv_list_bendel_nama);
            tvListBendelAlamat = itemView.findViewById(R.id.tv_list_bendel_alamat);
            tvListBendelSt = itemView.findViewById(R.id.tv_list_bendel_st);
            tvListBendelLalu = itemView.findViewById(R.id.tv_list_bendel_lalu);
            tvListBendelDibuat = itemView.findViewById(R.id.tv_list_bendel_dibuat);
        }
    }
}
