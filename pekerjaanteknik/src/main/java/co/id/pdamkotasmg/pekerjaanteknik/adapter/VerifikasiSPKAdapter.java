package co.id.pdamkotasmg.pekerjaanteknik.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat.DetailRiwayatSpkActivity;
import co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.verifikator.DataItem;
import com.pdamkotasmg.goodday.utils.Config;

public class VerifikasiSPKAdapter extends RecyclerView.Adapter<VerifikasiSPKAdapter.ViewHolder>
        implements Filterable {
    private final String TAG = "debug";
    Context context;
    private List<DataItem> dataItem;
    private List<DataItem> dataItemFiltered;

    public VerifikasiSPKAdapter(Context context, List<DataItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_verif_spk, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // rl_spk kosong bila baris verifikasi kehilangan induk SPK-nya.
        // Baris ini dan seluruh isi tombol di bawah membacanya.
        if (dataItem.get(position) == null || dataItem.get(position).getRlSpk() == null) {
            holder.itemView.setVisibility(View.GONE);
            return;
        }
        holder.itemView.setVisibility(View.VISIBLE);

        holder.tvNoSpk.setText(dataItem.get(position).getNoSpk());
        holder.tvUraianPekerjaan.setText(dataItem.get(position).getUraian());
        holder.tvAlamat.setText(dataItem.get(position).getRlSpk().getLokasi());

        String kdPerbaikan = dataItem.get(position).getKdPerbaikan();
        String namaPerbaikan = null;
        if ("0".equals(kdPerbaikan)) {
            namaPerbaikan = "Lain-Lain (LL)";
        } else if ("1".equals(kdPerbaikan)) {
            namaPerbaikan = "Perbaikan Pipa Bocor (PB)";
        } else if ("2".equals(kdPerbaikan)) {
            namaPerbaikan = "Penanganan Air Mati / Aliran (PA)";
        } else if ("3".equals(kdPerbaikan)) {
            namaPerbaikan = "Pekerjaan Meter Induk (PM)";
        } else if ("4".equals(kdPerbaikan)) {
            namaPerbaikan = "Pelanggaran (PL)";
        }
        String finalNamaPerbaikan = namaPerbaikan;

        String stLembur = dataItem.get(position).getStlembur();
        String stLemburName = null;
        if (stLembur == null || stLembur.isEmpty()) {
            stLemburName = "-";
        } else if ("1".equals(stLembur)) {
            stLemburName = "Ya";
        } else {
            stLemburName = "Tidak";
        }
        String finalStLemburName = stLemburName;

        holder.cvKlik.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailRiwayatSpkActivity.class);
            intent.putExtra(Config.BUNDLE_NO_SPK, dataItem.get(position).getNoSpk());
            intent.putExtra(Config.BUNDLE_TGL_SPK, dataItem.get(position).getRlSpk().getTglSpk());
            intent.putExtra(Config.BUNDLE_NM_LAPOR, dataItem.get(position).getRlSpk().getNmLapor());
            intent.putExtra(Config.BUNDLE_ALAMAT_LAPOR, dataItem.get(position).getRlSpk().getAlamatLapor());
            intent.putExtra(Config.BUNDLE_ASAL_LAPOR, dataItem.get(position).getRlSpk().getAsalLapor());
            intent.putExtra(Config.BUNDLE_NOLANGG_LAPOR, dataItem.get(position).getRlSpk().getNolanggLapor());
            intent.putExtra(Config.BUNDLE_NAMA, dataItem.get(position).getRlSpk().getNama());
            intent.putExtra(Config.BUNDLE_ALAMAT, dataItem.get(position).getRlSpk().getAlamat());
            intent.putExtra(Config.BUNDLE_LOKASI, dataItem.get(position).getRlSpk().getLokasi());
            intent.putExtra(Config.BUNDLE_URAIAN, dataItem.get(position).getRlSpk().getUraian());
            intent.putExtra(Config.BUNDLE_KASUB, dataItem.get(position).getRlSpk().getKasub());
            intent.putExtra(Config.BUNDLE_PENGAWAS, dataItem.get(position).getRlSpk().getPengawas());
            intent.putExtra(Config.BUNDLE_PC_ENTRY, dataItem.get(position).getRlSpk().getPcEntry());
            intent.putExtra(Config.BUNDLE_TGL_PELAKSANA, dataItem.get(position).getTglPelaksana());
            intent.putExtra(Config.BUNDLE_JAM1, dataItem.get(position).getJam1());
            intent.putExtra(Config.BUNDLE_JAM2, dataItem.get(position).getJam2());
            intent.putExtra(Config.BUNDLE_JENIS_PIPA, dataItem.get(position).getJenisPipa());
            intent.putExtra(Config.BUNDLE_JML_TENAGA, dataItem.get(position).getJmlTenaga() + " - " + dataItem.get(position).getJmlTenagaKet());
            intent.putExtra(Config.BUNDLE_NO_SPK_SBL, dataItem.get(position).getNoSpkSbl());
            intent.putExtra(Config.BUNDLE_KD_PERBAIKAN, finalNamaPerbaikan);
            intent.putExtra(Config.BUNDLE_KET_ZONA, dataItem.get(position).getKetZona());
            intent.putExtra(Config.BUNDLE_TKA, dataItem.get(position).getTka());
            intent.putExtra(Config.BUNDLE_STATUS, dataItem.get(position).getStatus());
            intent.putExtra(Config.BUNDLE_FOTO1, Config.BASE_URL_IMAGE_HANDLER +dataItem.get(position).getFoto1());
            intent.putExtra(Config.BUNDLE_FOTO2, Config.BASE_URL_IMAGE_HANDLER +dataItem.get(position).getFoto2());
            intent.putExtra(Config.BUNDLE_FOTO3, Config.BASE_URL_IMAGE_HANDLER +dataItem.get(position).getFoto3());
            intent.putExtra(Config.BUNDLE_FOTO4, Config.BASE_URL_IMAGE_HANDLER +dataItem.get(position).getFoto4());
            intent.putExtra(Config.BUNDLE_NO_CC, dataItem.get(position).getNocc());
            intent.putExtra(Config.BUNDLE_STATUS_VERIFIKASI, dataItem.get(position).getStatusVerif());
            intent.putExtra(Config.BUNDLE_VERIFIKATOR, dataItem.get(position).getUserVer());
            intent.putExtra(Config.BUNDLE_LEMBUR, finalStLemburName);
            intent.putExtra(Config.BUNDLE_LATITUDE, dataItem.get(position).getX());
            intent.putExtra(Config.BUNDLE_LONGITUDE, dataItem.get(position).getY());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataItem == null ? 0 : dataItem.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<DataItem> resultsItems = new ArrayList<>();

                if (dataItemFiltered == null)
                    dataItemFiltered = dataItem;

                // Kata kunci ikut dikecilkan supaya pencarian huruf besar tetap cocok.
                String kunci = constraint == null ? "" : constraint.toString().toLowerCase();
                if (dataItemFiltered != null) {
                    for (final DataItem g : dataItemFiltered) {
                        if (g != null && g.getNoSpk() != null
                                && g.getNoSpk().toLowerCase().contains(kunci)) {
                            resultsItems.add(g);
                        }
                    }
                }
                oReturn.values = resultsItems;

                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Hasil null sebelumnya membuat daftar menutup aplikasi.
                if (results != null && results.values != null) {
                    dataItem = (List<DataItem>) results.values;
                }
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private TextView tvNoSpk;
        private TextView tvAlamat;
        private TextView tvUraianPekerjaan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cvKlik);
            tvNoSpk = itemView.findViewById(R.id.tv_no_spk);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvUraianPekerjaan = itemView.findViewById(R.id.tv_uraian_pekerjaan);
        }
    }
}
