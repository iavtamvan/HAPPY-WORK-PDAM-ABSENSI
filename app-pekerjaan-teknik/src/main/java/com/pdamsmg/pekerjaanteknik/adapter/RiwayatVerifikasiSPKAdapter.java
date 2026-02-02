package com.pdamsmg.pekerjaanteknik.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamsmg.pekerjaanteknik.R;
import com.pdamsmg.pekerjaanteknik.fitur.activity.spk.riwayat.DetailRiwayatSpkActivity;
import com.pdamsmg.pekerjaanteknik.model.riwayatSpk.verifikator.DataItem;
import com.pdamsmg.pekerjaanteknik.utils.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RiwayatVerifikasiSPKAdapter extends RecyclerView.Adapter<RiwayatVerifikasiSPKAdapter.ViewHolder> implements Filterable {
    Context context;
    private List<DataItem> dataItem;
    private List<DataItem> dataItemFiltered;

    public RiwayatVerifikasiSPKAdapter(Context context, List<DataItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_riwayat_spk, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNoSpk.setText(dataItem.get(position).getNoSpk());
        holder.tvNoCc.setText(dataItem.get(position).getNocc());
        holder.tvTglSpkPelaksana.setText(dataItem.get(position).getTglPelaksana());
        holder.tvAlamat.setText(dataItem.get(position).getRlSpk().getAlamatLapor());
        holder.tvAlamatGps.setText(dataItem.get(position).getRlSpk().getLokasi());
        holder.tvUraian.setText(dataItem.get(position).getUraian());

        holder.tvListItemDate.setText(dataItem.get(position).getTanggal());
        String kdPerbaikan = dataItem.get(position).getKdPerbaikan();
        String namaPerbaikan = null;
        if (kdPerbaikan.equals("0")) {
            namaPerbaikan = "Lain-Lain (LL)";
        } else if (kdPerbaikan.equals("1")) {
            namaPerbaikan = "Perbaikan Pipa Bocor (PB)";
        } else if (kdPerbaikan.equals("2")) {
            namaPerbaikan = "Penanganan Air Mati / Aliran (PA)";
        } else if (kdPerbaikan.equals("3")) {
            namaPerbaikan = "Pekerjaan Meter Induk (PM)";
        } else if (kdPerbaikan.equals("4")) {
            namaPerbaikan = "Pelanggaran (PL)";
        }
        String finalNamaPerbaikan = namaPerbaikan;
        holder.tvListItemTypePekerjaan.setText(finalNamaPerbaikan);

        String statusVerif = dataItem.get(position).getStatusVerif();
        if (statusVerif.equalsIgnoreCase("0")) {
            holder.divRequestWaiting.setVisibility(View.VISIBLE);
            holder.divRequestDisetujui.setVisibility(View.GONE);
        } else {
            holder.divRequestDisetujui.setVisibility(View.VISIBLE);
            holder.divRequestWaiting.setVisibility(View.GONE);
        }

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateRequestAt = date.parse(dataItem.get(position).getTglPelaksana());

            String dateRequestAtFix = new SimpleDateFormat("EEE, dd MMM yyyy").format(dateRequestAt);
            holder.tvTglSpkPelaksana.setText(dateRequestAtFix);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateNtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dateRequestAt = dateNtime.parse(dataItem.get(position).getTanggal());

            String dateRequestAtFix = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss").format(dateRequestAt);
            holder.tvListItemDate.setText(dateRequestAtFix);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String stLembur = dataItem.get(position).getStlembur();
        String stLemburName = null;
        if (stLembur == null || stLembur.isEmpty()){
            stLemburName = "-";
        } else if (stLembur.equals("1")){
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
            intent.putExtra(Config.BUNDLE_FOTO1, Config.BASE_URL_IMAGE + dataItem.get(position).getFoto1());
            intent.putExtra(Config.BUNDLE_FOTO2, Config.BASE_URL_IMAGE + dataItem.get(position).getFoto2());
            intent.putExtra(Config.BUNDLE_FOTO3, Config.BASE_URL_IMAGE + dataItem.get(position).getFoto3());
            intent.putExtra(Config.BUNDLE_FOTO4, Config.BASE_URL_IMAGE + dataItem.get(position).getFoto4());
            intent.putExtra(Config.BUNDLE_STATUS_VERIFIKASI, dataItem.get(position).getStatusVerif());
            intent.putExtra(Config.BUNDLE_VERIFIKATOR, dataItem.get(position).getUserVer());
            intent.putExtra(Config.BUNDLE_NO_CC, dataItem.get(position).getNocc());
            intent.putExtra(Config.BUNDLE_LEMBUR, finalStLemburName);
            intent.putExtra(Config.BUNDLE_LATITUDE, dataItem.get(position).getX());
            intent.putExtra(Config.BUNDLE_LONGITUDE, dataItem.get(position).getY());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
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
                if (constraint != null) {
                    if (dataItemFiltered != null & dataItemFiltered.size() > 0) {
                        for (final DataItem g : dataItemFiltered) {
                            if (g.getNoSpk().toLowerCase().contains(constraint.toString()))
                                resultsItems.add(g);
                        }
                    }
                    oReturn.values = resultsItems;
                }

                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataItem = (ArrayList<DataItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvListItemTypePekerjaan;
        private TextView tvNoSpk;
        private LinearLayout divCopy;
        private LinearLayout divRequestWaiting;
        private LinearLayout divRequestDisetujui;
        private TextView tvTglSpkPelaksana;
        private TextView tvNoCc;
        private TextView tvAlamat;
        private TextView tvAlamatGps;
        private TextView tvUraian;
        private TextView tvListItemDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cvKlik);
            tvListItemTypePekerjaan = itemView.findViewById(R.id.tv_list_item_type_pekerjaan);
            tvNoSpk = itemView.findViewById(R.id.tv_no_spk);
            divCopy = itemView.findViewById(R.id.div_copy);
            divRequestWaiting = itemView.findViewById(R.id.div_request_waiting);
            divRequestDisetujui = itemView.findViewById(R.id.div_request_disetujui);
            tvTglSpkPelaksana = itemView.findViewById(R.id.tv_tgl_spk_pelaksana);
            tvNoCc = itemView.findViewById(R.id.tv_no_cc);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvAlamatGps = itemView.findViewById(R.id.tv_alamat_gps);
            tvUraian = itemView.findViewById(R.id.tv_uraian);
            tvListItemDate = itemView.findViewById(R.id.tv_list_item_date);
        }
    }
}
