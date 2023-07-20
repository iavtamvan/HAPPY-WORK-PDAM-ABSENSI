package co.id.pdamkotasmg.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class BendelAdapter extends RecyclerView.Adapter<BendelAdapter.ViewHolder> {
    Context context;
    private String periode;
    private String cabang;
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
        holder.tvListBendelLalu.setText(dataItems.get(position).getRlTrbaca().getLalu() + "m3");
        holder.tvListBendelDibuat.setText("Generate by System Cabang " + cabang + "-" + periode);

        holder.cvKlik.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DetailCutiActivity.class);
//            intent.putExtra(Config.BUNDLE_NUMBER_REQUEST, dataItems.get(position).getRequestNumber());
//            intent.putExtra(Config.BUNDLE_NUMBER_APPROVALS, "2");
//            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvListItemNolangg;
        private LinearLayout divCopy;
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
            tvListItemNolangg = itemView.findViewById(R.id.tv_list_item_nolangg);
            divCopy = itemView.findViewById(R.id.div_copy);
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
