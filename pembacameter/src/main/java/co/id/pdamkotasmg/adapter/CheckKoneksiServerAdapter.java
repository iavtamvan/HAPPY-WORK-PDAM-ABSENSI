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

import co.id.pdamkotasmg.model.checkKoneksi.CheckItem;
import co.id.pdamkotasmg.pembacameter.R;

public class CheckKoneksiServerAdapter extends RecyclerView.Adapter<CheckKoneksiServerAdapter.ViewHolder> {
    Context context;
    private List<CheckItem> dataItems;

    public CheckKoneksiServerAdapter(Context context, List<CheckItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_check_server, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences sp = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        holder.tvNamaServer.setText(dataItems.get(position).getName());
        holder.tvEndpoint.setText(dataItems.get(position).getEndpoint());
        holder.tvDurasiAkses.setText(dataItems.get(position).getDurasi());
        holder.tvSpeedLatency.setText(dataItems.get(position).getSpeedLatency());
        holder.tvStatusCode.setText(dataItems.get(position).getStatusCode());
        holder.tvStatusDesc.setText(dataItems.get(position).getStatusDesc());

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvNamaServer;
        private TextView tvEndpoint;
        private TextView tvDurasiAkses;
        private TextView tvSpeedLatency;
        private TextView tvStatusCode;
        private TextView tvStatusDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cvKlik);
            tvNamaServer = itemView.findViewById(R.id.tv_nama_server);
            tvEndpoint = itemView.findViewById(R.id.tv_endpoint);
            tvDurasiAkses = itemView.findViewById(R.id.tv_durasi_akses);
            tvSpeedLatency = itemView.findViewById(R.id.tv_speed_latency);
            tvStatusCode = itemView.findViewById(R.id.tv_status_code);
            tvStatusDesc = itemView.findViewById(R.id.tv_status_desc);
        }
    }
}
