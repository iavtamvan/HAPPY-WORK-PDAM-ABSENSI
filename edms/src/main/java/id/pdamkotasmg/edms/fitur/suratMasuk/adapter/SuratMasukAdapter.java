package id.pdamkotasmg.edms.fitur.suratMasuk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.utils.AlphabetColor;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.pdamkotasmg.edms.R;
import id.pdamkotasmg.edms.fitur.suratMasuk.activity.DetailSuratMasukActivity;
import id.pdamkotasmg.edms.fitur.suratMasuk.model.DataItem;

public class SuratMasukAdapter extends RecyclerView.Adapter<SuratMasukAdapter.ViewHolder> {
    private final String TAG = "debug";
    Context context;
    private List<DataItem> dataItems;

    private String reason;

    public SuratMasukAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_surat, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO convert Tanggal dari server
        SimpleDateFormat dateNtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dateRequestAt = dateNtime.parse(dataItems.get(position).getSmTglTerima());

            String dateRequestAtFix = new SimpleDateFormat("ddMMM\nyyyy").format(dateRequestAt);
            holder.tvWaktuTglSurat.setText(dateRequestAtFix);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String replaceBagian = dataItems.get(position).getSmAsalNama().replace("Bagian", "").trim();
        Log.d(TAG, "onBindViewHolder:" + replaceBagian);

        char firstChar = replaceBagian.charAt(0);
        int colors = AlphabetColor.getColor(firstChar);
        holder.tvFirstChar.setText(String.valueOf(firstChar));
        holder.ciListEdms.setImageResource(colors);

        holder.tvNamaAsalSurat.setText(replaceBagian);
        holder.tvPerihalSifatJenisSurat.setText(dataItems.get(position).getSmPerihal() + ": " + dataItems.get(position).getSmSifat() + " - " + dataItems.get(position).getSmJenisSurat());
        holder.tvNoSurat.setText("No. Surat: " + dataItems.get(position).getSmNoSurat() + " | No. Agenda: " + dataItems.get(position).getSmNoAgenda());


        holder.divKlik.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailSuratMasukActivity.class);
            intent.putExtra(Config.BUNDLE_NUMBER_TRX_SURAT, dataItems.get(position).getSmTrx());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout divKlik;
        private CircleImageView ciListEdms;
        private TextView tvNamaAsalSurat;
        private TextView tvPerihalSifatJenisSurat;
        private TextView tvNoSurat;
        private TextView tvWaktuTglSurat;
        private TextView tvFirstChar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            divKlik = itemView.findViewById(R.id.div_klik);
            ciListEdms = itemView.findViewById(R.id.ci_list_edms);
            tvNamaAsalSurat = itemView.findViewById(R.id.tv_nama_asal_surat);
            tvPerihalSifatJenisSurat = itemView.findViewById(R.id.tv_perihal_sifat_jenisSurat);
            tvNoSurat = itemView.findViewById(R.id.tv_no_surat);
            tvWaktuTglSurat = itemView.findViewById(R.id.tv_waktu_tgl_surat);
            tvFirstChar = itemView.findViewById(R.id.tv_first_char);
        }
    }
}
