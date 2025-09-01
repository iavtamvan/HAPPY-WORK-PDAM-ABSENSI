package co.id.pdamkotasmg.pekerjaanteknik.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.InputSpkActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.callCenter.DaftarCallCenterActivity;
import co.id.pdamkotasmg.pekerjaanteknik.activity.spk.riwayat.RiwayatAmbilPekerjaanActivity;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiConfig;
import co.id.pdamkotasmg.pekerjaanteknik.api.ApiService;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.diposisi.DataItem;
import co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.diposisi.DisposisiRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPerbaikanCCAdapter extends RecyclerView.Adapter<DaftarPerbaikanCCAdapter.ViewHolder> implements Filterable {
    Context context;
    private List<DataItem> dataItem;
    private List<DataItem> dataItemFiltered;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String accessToken;
    private String name;
    private String npp;
    private String kdAmbilRiwayatCC;

    public DaftarPerbaikanCCAdapter(Context context, List<DataItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_perbaikan_cc, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        kdAmbilRiwayatCC = sharedPreferences.getString(Config.SHARED_KD_AMBIL_PEKERJAAN, "");

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu...");

        holder.tvNoCc.setText(dataItem.get(position).getNoPengaduanCc());
        holder.tvNoSpk.setText(dataItem.get(position).getNoSpk());
        holder.tvAlamatPelanggan.setText(dataItem.get(position).getAlamatPelanggan());
        holder.tvCabang.setText(dataItem.get(position).getCabangPelanggan());
        holder.tvAlamat.setText(dataItem.get(position).getAlamat());
        holder.tvUraian.setText(dataItem.get(position).getUraian());
        holder.tvUraianPenyelesaian.setText(dataItem.get(position).getPenyelesaian());
        holder.tvDiambilOleh.setText(dataItem.get(position).getTenaga());

        SimpleDateFormat dateNtimeAduan = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dateRequestAt = dateNtimeAduan.parse(dataItem.get(position).getTglAduan());

            String dateRequestAtFix = new SimpleDateFormat("EEE, dd MMM yyyy").format(dateRequestAt);
            holder.tvTglAduan.setText(dateRequestAtFix);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.divCopy.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                clipboardManager.setText(holder.tvNoCc.getText());
            }
            Toast.makeText(context, holder.tvNoCc.getText() + " berhasil di copy", Toast.LENGTH_SHORT).show();
        });

        if (dataItem.get(position).getNoSpk() == null) {
            holder.divRequestDisetujui.setVisibility(View.VISIBLE);
            holder.divRequestRejected.setVisibility(View.GONE);
            holder.divRequestReserved.setVisibility(View.GONE);
            holder.tvNoSpk.setText("--/--/--/----");
        } else {
            holder.divRequestRejected.setVisibility(View.VISIBLE);
            holder.divRequestDisetujui.setVisibility(View.GONE);
            holder.divRequestReserved.setVisibility(View.GONE);
        }

        if (dataItem.get(position).getTenaga() != null) {
            holder.divRequestDisetujui.setVisibility(View.GONE);
            holder.divRequestRejected.setVisibility(View.GONE);
            holder.divRequestReserved.setVisibility(View.VISIBLE);
        }

//        if (dataItem.get(position).getNoPengaduanCc() == null) {
//            holder.tvStatus.setText("Tidak, No CC belum ada!");
//            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
//        }

        // TODO convert Tanggal dari server
        SimpleDateFormat dateNtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dateRequestAt = dateNtime.parse(dataItem.get(position).getCreatedAt());

            String dateRequestAtFix = new SimpleDateFormat("EEE, dd MMM yyyy").format(dateRequestAt);
            holder.tvTanggal.setText(dateRequestAtFix);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cvKlik.setOnClickListener(v -> {

            if (dataItem.get(position).getTenaga() != null) {
                if (kdAmbilRiwayatCC.equals("1")) { // dari fitur HOME
                    MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                            .setTitle("Batalkan booking " + holder.tvNoCc.getText().toString().trim() + "?")
                            .setCancelable(true)
                            .setNegativeButton("Ya", (dialogInterface, which) -> {
                                dialogInterface.dismiss();
                                progressDialog.show();
                                editor.putString(Config.SHARED_STATU_BOOKING, "0");
                                editor.apply();
                                ambilDanBatalPekerjaanCC(holder.tvNoCc.getText().toString().trim(), "", "Berhasil batalkan pekerjaan");
                            })
                            .setPositiveButton("Buat SPK", (dialogInterface, which) -> {
                                dialogInterface.dismiss();
                                editor.putString(Config.SHARED_STATU_BOOKING, "0");
                                editor.apply();
                                // intent ke
                                Intent intent = new Intent(context, InputSpkActivity.class);
                                intent.putExtra(Config.BUNDLE_STATUS_INTENT, "cc");
                                intent.putExtra(Config.BUNDLE_NO_LANGG, dataItem.get(position).getNoLangg());
                                intent.putExtra(Config.BUNDLE_NAMA_PENGADU, dataItem.get(position).getNamaPengadu());
                                intent.putExtra(Config.BUNDLE_ALAMAT_PENGADU, dataItem.get(position).getAlamat());
                                intent.putExtra(Config.BUNDLE_URAIAN_PENGADU, dataItem.get(position).getUraian());
                                intent.putExtra(Config.BUNDLE_NO_PENGADUAN_CC, dataItem.get(position).getNoPengaduanCc());
                                intent.putExtra(Config.BUNDLE_NO_TELP_PENGADU, dataItem.get(position).getTelpPengadu());
                                context.startActivity(intent);
                            })
                            .build();

                    mDialog.show();
                } else {
                    Toast.makeText(context, "Sudah di booking oleh " + dataItem.get(position).getTenaga(), Toast.LENGTH_SHORT).show();
                }
            } else if (dataItem.get(position).getNoSpk() == null) {
                MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Pilih aksi?")
                        .setCancelable(true)
                        .setNegativeButton("Booking", (dialogInterface, which) -> {
                            getCekBooking(holder.tvNoCc.getText().toString().trim());
                            dialogInterface.dismiss();
                        })
                        .setPositiveButton("Buat SPK Langsung", (dialogInterface, which) -> {
//                            if (dataItem.get(position).getNoSpk() == null) {
                            dialogInterface.dismiss();
                            editor.putString(Config.SHARED_STATU_BOOKING, "0");
                            editor.apply();
                            // intent ke
                            Intent intent = new Intent(context, InputSpkActivity.class);
                            intent.putExtra(Config.BUNDLE_STATUS_INTENT, "cc");
                            intent.putExtra(Config.BUNDLE_NO_LANGG, dataItem.get(position).getNoLangg());
                            intent.putExtra(Config.BUNDLE_NAMA_PENGADU, dataItem.get(position).getNamaPengadu());
                            intent.putExtra(Config.BUNDLE_ALAMAT_PENGADU, dataItem.get(position).getAlamat());
                            intent.putExtra(Config.BUNDLE_URAIAN_PENGADU, dataItem.get(position).getUraian());
                            intent.putExtra(Config.BUNDLE_NO_PENGADUAN_CC, dataItem.get(position).getNoPengaduanCc());
                            intent.putExtra(Config.BUNDLE_NO_TELP_PENGADU, dataItem.get(position).getTelpPengadu());
                            context.startActivity(intent);
//                            } else {
//                                Toast.makeText(context, "Sudah ada No SPK, tidak bisa di ambil!", Toast.LENGTH_SHORT).show();
//                            }
                        })
                        .build();

                mDialog.show();
            } else {
                Toast.makeText(context, "Sudah ada No SPK, tidak bisa di ambil!", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void ambilDanBatalPekerjaanCC(String noCC, String tenagaNameNPP, String note) {
        ApiService apiService = ApiConfig.getApiService(context, Config.BASE_URL);
        apiService.ambilDanBatalPekerjaanCC(accessToken, noCC, tenagaNameNPP)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            Toast.makeText(context, note, Toast.LENGTH_SHORT).show();

                            if (kdAmbilRiwayatCC.equals("1")) { // dari fitur HOME
                                ((RiwayatAmbilPekerjaanActivity) context).getRiwayatAmbilPekerjaan();
                            } else {
                                ((DaftarCallCenterActivity) context).getDataDisposisi();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getCekBooking(String noCC) {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(context, Config.BASE_URL);
        apiService.getDataCallCenterAduanByTenagaNPP(accessToken)
                .enqueue(new Callback<DisposisiRootModel>() {
                    @Override
                    public void onResponse(Call<DisposisiRootModel> call, Response<DisposisiRootModel> response) {
                        if (response.isSuccessful()) {
//                            progressDialog.cancel();
                            Log.d("debug", "data cek booking: " + response.body().getData().size());

                            if (response.body().getData().size() >= 1){
                                progressDialog.cancel();
                                Toast.makeText(context, "Selesaikan Pekerjaan Yang di Booking", Toast.LENGTH_SHORT).show();
                            } else {
                                editor.putString(Config.SHARED_STATU_BOOKING, "1");
                                editor.apply();
                                ambilDanBatalPekerjaanCC(noCC, name + " (" + npp + ")", "Berhasil booking pekerjaan");
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<DisposisiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
                            if (g.getNoPengaduanCc().toLowerCase().contains(constraint.toString()))
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
        private CardView cvKlik;
        private TextView tvCabang;
        private TextView tvNoCc;
        private LinearLayout divCopy;
        private LinearLayout divRequestRejected;
        private LinearLayout divRequestDisetujui;
        private LinearLayout divRequestReserved;
        private TextView tvTglAduan;
        private TextView tvNoSpk;
        private TextView tvAlamat;
        private TextView tvAlamatPelanggan;
        private TextView tvUraian;
        private TextView tvUraianPenyelesaian;
        private TextView tvTanggal;
        private TextView tvDiambilOleh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvCabang = itemView.findViewById(R.id.tv_cabang);
            tvNoCc = itemView.findViewById(R.id.tv_no_cc);
            divCopy = itemView.findViewById(R.id.div_copy);
            divRequestRejected = itemView.findViewById(R.id.div_request_rejected);
            divRequestDisetujui = itemView.findViewById(R.id.div_request_disetujui);
            divRequestReserved = itemView.findViewById(R.id.div_request_reserved);
            tvTglAduan = itemView.findViewById(R.id.tv_tgl_aduan);
            tvNoSpk = itemView.findViewById(R.id.tv_no_spk);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvAlamatPelanggan = itemView.findViewById(R.id.tv_alamat_pelanggan);
            tvUraian = itemView.findViewById(R.id.tv_uraian);
            tvUraianPenyelesaian = itemView.findViewById(R.id.tv_uraian_penyelesaian);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvDiambilOleh = itemView.findViewById(R.id.tv_diambil_oleh);
        }
    }
}