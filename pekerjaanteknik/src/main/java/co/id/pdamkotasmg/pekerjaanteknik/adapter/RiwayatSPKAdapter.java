package co.id.pdamkotasmg.pekerjaanteknik.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat.DetailRiwayatSpkActivity;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.mandor.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.utils.Config;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatSPKAdapter extends RecyclerView.Adapter<RiwayatSPKAdapter.ViewHolder> implements Filterable {
    Context context;
    private List<DataItem> dataItem;
    private List<DataItem> dataItemFiltered;

    public RiwayatSPKAdapter(Context context, List<DataItem> dataItem) {
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
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        String namePengawas = sharedPreferences.getString(Config.SHARED_NAME_PENGAWAS, "");

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        holder.tvNoSpk.setText(dataItem.get(position).getNoSpk());
        if (dataItem.get(position).getRlPelaksana().getNocc() == null) {
            holder.tvNoCc.setText("-");
        } else {
            holder.tvNoCc.setText(dataItem.get(position).getRlPelaksana().getNocc());
        }
        holder.tvTglSpkPelaksana.setText(dataItem.get(position).getRlPelaksana().getTglPelaksana());
        holder.tvAlamat.setText(dataItem.get(position).getAlamatLapor());
        holder.tvAlamatGps.setText(dataItem.get(position).getLokasi());
        holder.tvUraian.setText(dataItem.get(position).getUraian());

        holder.tvListItemDate.setText(dataItem.get(position).getRlPelaksana().getTanggal());
        String kdPerbaikan = dataItem.get(position).getRlPelaksana().getKdPerbaikan();
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

        String statusVerif = dataItem.get(position).getRlPelaksana().getStatusVerif();
        if (statusVerif.equalsIgnoreCase("0")) {
            holder.divRequestWaiting.setVisibility(View.VISIBLE);
            holder.divRequestDisetujui.setVisibility(View.GONE);
        } else {
            holder.divRequestDisetujui.setVisibility(View.VISIBLE);
            holder.divRequestWaiting.setVisibility(View.GONE);
        }

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateRequestAt = date.parse(dataItem.get(position).getRlPelaksana().getTglPelaksana());

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


        String stLembur = dataItem.get(position).getRlPelaksana().getStlembur();
        String stLemburName = null;
        if (stLembur == null || stLembur.isEmpty()) {
            stLemburName = "-";
        } else if (stLembur.equals("1")) {
            stLemburName = "Ya";
        } else {
            stLemburName = "Tidak";
        }
        String finalStLemburName = stLemburName;

        holder.divCopy.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                clipboardManager.setText(holder.tvNoSpk.getText());
            }
            Toast.makeText(context, holder.tvNoSpk.getText() + " berhasil di copy", Toast.LENGTH_SHORT).show();
        });


        holder.cvKlik.setOnClickListener(v -> {

            final BottomSheetDialog bottomSheetDialogRiwayatSPK = new BottomSheetDialog(context);
            bottomSheetDialogRiwayatSPK.setContentView(R.layout.bottom_sheet_dialog_riwayat_spk);

            EditText edtNoCC = bottomSheetDialogRiwayatSPK.findViewById(R.id.edt_btm_nocc);
            EditText edtNoSpk = bottomSheetDialogRiwayatSPK.findViewById(R.id.edt_btm_nospk);
            Button btnUpdateNoCC = bottomSheetDialogRiwayatSPK.findViewById(R.id.btn_btm_update_nocc);
            Button btnLihatData = bottomSheetDialogRiwayatSPK.findViewById(R.id.btn_btm_lihat_data);

            edtNoCC.setText(dataItem.get(position).getRlPelaksana().getNocc());
            edtNoSpk.setText(dataItem.get(position).getNoSpk());
            edtNoSpk.setEnabled(false);
            if (!edtNoCC.getText().toString().isEmpty()) {
                edtNoCC.setEnabled(false);
                Toast.makeText(context, "Sudah Ada No CallCenter", Toast.LENGTH_SHORT).show();
                btnUpdateNoCC.setVisibility(View.GONE);
            }

            btnUpdateNoCC.setOnClickListener(v1 -> {
                if (edtNoCC.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Isi No CC", Toast.LENGTH_SHORT).show();
                }
//                else if (!edtNoCC.getText().toString().isEmpty()) {
//                    edtNoCC.setEnabled(false);
//                    Toast.makeText(context, "Sudah Ada No CallCenter", Toast.LENGTH_SHORT).show();
//                }
                else {
                    progressDialog.setMessage("Mohon tunggu ...");
                    progressDialog.show();
                    btnUpdateNoCC.setVisibility(View.VISIBLE);
                    ApiService apiService = ApiConfig.getApiService(context, Config.BASE_URL);
                    apiService.updateNoCC(token, holder.tvNoSpk.getText().toString().trim(), edtNoCC.getText().toString().trim(), edtNoCC.getText().toString().trim(), namePengawas)
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    progressDialog.cancel();
                                    if (response.isSuccessful()) {
                                        bottomSheetDialogRiwayatSPK.cancel();
                                        Toast.makeText(context, "Sukses update No CC", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Gagal Update No CC", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    progressDialog.cancel();
                                    Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

            btnLihatData.setOnClickListener(v1 -> {
                bottomSheetDialogRiwayatSPK.cancel();
                Intent intent = new Intent(context, DetailRiwayatSpkActivity.class);
                intent.putExtra(Config.BUNDLE_NO_SPK, dataItem.get(position).getNoSpk());
                intent.putExtra(Config.BUNDLE_TGL_SPK, dataItem.get(position).getTglSpk());
                intent.putExtra(Config.BUNDLE_NM_LAPOR, dataItem.get(position).getNmLapor());
                intent.putExtra(Config.BUNDLE_ALAMAT_LAPOR, dataItem.get(position).getAlamatLapor());
                intent.putExtra(Config.BUNDLE_ASAL_LAPOR, dataItem.get(position).getAsalLapor());
                intent.putExtra(Config.BUNDLE_NOLANGG_LAPOR, dataItem.get(position).getNolanggLapor());
                intent.putExtra(Config.BUNDLE_NAMA, dataItem.get(position).getNama());
                intent.putExtra(Config.BUNDLE_ALAMAT, dataItem.get(position).getAlamat());
                intent.putExtra(Config.BUNDLE_LOKASI, dataItem.get(position).getLokasi());
                intent.putExtra(Config.BUNDLE_URAIAN, dataItem.get(position).getUraian());
                intent.putExtra(Config.BUNDLE_KASUB, dataItem.get(position).getRlKasub().getNama() + " (" + dataItem.get(position).getRlKasub().getNpp() + ") ");
                intent.putExtra(Config.BUNDLE_PENGAWAS, dataItem.get(position).getRlPengawas().getNama() + " (" + dataItem.get(position).getRlPengawas().getNpp() + ") ");
                intent.putExtra(Config.BUNDLE_PC_ENTRY, dataItem.get(position).getRlUserEntry().getNama() + " (" + dataItem.get(position).getRlUserEntry().getNpp() + ") ");
                intent.putExtra(Config.BUNDLE_TGL_PELAKSANA, dataItem.get(position).getRlPelaksana().getTglPelaksana());
                intent.putExtra(Config.BUNDLE_JAM1, dataItem.get(position).getRlPelaksana().getJam1());
                intent.putExtra(Config.BUNDLE_JAM2, dataItem.get(position).getRlPelaksana().getJam2());
                intent.putExtra(Config.BUNDLE_JENIS_PIPA, dataItem.get(position).getRlPelaksana().getRlMBarang().getNmBrg() + " (" + dataItem.get(position).getRlPelaksana().getRlMBarang().getKdBrg() + ") ");
                intent.putExtra(Config.BUNDLE_JML_TENAGA, dataItem.get(position).getRlPelaksana().getJmlTenaga() + " - " + dataItem.get(position).getRlPelaksana().getJmlTenagaKet());
                intent.putExtra(Config.BUNDLE_NO_SPK_SBL, dataItem.get(position).getRlPelaksana().getNoSpkSbl());
                intent.putExtra(Config.BUNDLE_KD_PERBAIKAN, finalNamaPerbaikan);
                intent.putExtra(Config.BUNDLE_KET_ZONA, dataItem.get(position).getRlPelaksana().getKetZona());
                intent.putExtra(Config.BUNDLE_TKA, dataItem.get(position).getRlPelaksana().getTka());
                intent.putExtra(Config.BUNDLE_STATUS, dataItem.get(position).getRlPelaksana().getStatus());
                intent.putExtra(Config.BUNDLE_FOTO1,Config.BASE_URL_IMAGE + dataItem.get(position).getRlPelaksana().getFoto1());
                intent.putExtra(Config.BUNDLE_FOTO2,Config.BASE_URL_IMAGE + dataItem.get(position).getRlPelaksana().getFoto2());
                intent.putExtra(Config.BUNDLE_FOTO3,Config.BASE_URL_IMAGE + dataItem.get(position).getRlPelaksana().getFoto3());
                intent.putExtra(Config.BUNDLE_FOTO4,Config.BASE_URL_IMAGE + dataItem.get(position).getRlPelaksana().getFoto4());
                intent.putExtra(Config.BUNDLE_STATUS_VERIFIKASI, dataItem.get(position).getRlPelaksana().getStatusVerif());
                intent.putExtra(Config.BUNDLE_NO_CC, dataItem.get(position).getRlPelaksana().getNocc());
                intent.putExtra(Config.BUNDLE_VERIFIKATOR, dataItem.get(position).getRlPelaksana().getUserVer());
                intent.putExtra(Config.BUNDLE_LEMBUR, finalStLemburName);
                intent.putExtra(Config.BUNDLE_LATITUDE, dataItem.get(position).getRlPelaksana().getX());
                intent.putExtra(Config.BUNDLE_LONGITUDE, dataItem.get(position).getRlPelaksana().getY());
                context.startActivity(intent);
            });

            bottomSheetDialogRiwayatSPK.show();


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
