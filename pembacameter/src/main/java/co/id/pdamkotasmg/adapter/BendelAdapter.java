package co.id.pdamkotasmg.adapter;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.bendel.DataItem;
import co.id.pdamkotasmg.model.bendel.RlDataBacaSekarang;
import co.id.pdamkotasmg.model.bendel.tandaiPlg.TandaiBendelRootModel;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.ui.activity.bendel.BendelPembacaKhususActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BendelAdapter extends RecyclerView.Adapter<BendelAdapter.ViewHolder> {
    Context context;
    private final String TAG = "debug";
    private String periode;
    private String cabang;
    private String token;
    private String bendel;
    private String codeInputData;
    private List<DataItem> dataItems;

    private int markedPosition = 1; // Initially, no position is marked

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

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables", "ResourceAsColor"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SharedPreferences sp = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        periode = sp.getString(Config.SHARED_PERIODE, "");
        cabang = sp.getString(Config.SHARED_CABANG, "");
        bendel = dataItems.get(position).getDism().substring(0, 4);

        holder.tvListBendelNolangg.setText(dataItems.get(position).getNolangg());
        holder.tvListBendelDism.setText(dataItems.get(position).getDism());
        holder.tvListBendelNama.setText(dataItems.get(position).getNama());
        holder.tvListBendelAlamat.setText(dataItems.get(position).getAlamat());

        if (dataItems.get(position).getRlDtBacaSekarang().get(0).getSurveyKoordinat() == null || dataItems.get(position).getRlDtBacaSekarang().get(0).getSurveyKoordinat().equals("0")) {
            holder.ivSurveyKoordinat.setVisibility(View.GONE);
        } else {
            Toast.makeText(context, "Ada Pelanggan yang harus sesuai Koordinatnya !!!", Toast.LENGTH_SHORT).show();
            holder.ivSurveyKoordinat.setVisibility(View.VISIBLE);
        }

        holder.divCopy.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                clipboardManager.setText(holder.tvListBendelNolangg.getText());
            }
            Toast.makeText(context, holder.tvListBendelNolangg.getText() + " berhasil di copy", Toast.LENGTH_SHORT).show();
        });

        holder.divNolangg.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                clipboardManager.setText(holder.tvListBendelNolangg.getText());
            }
            Toast.makeText(context, holder.tvListBendelNolangg.getText() + " berhasil di copy", Toast.LENGTH_SHORT).show();
        });

        int incrementedNumber = position + 1; // Increment starting from 1
        holder.tvNo.setText("" + incrementedNumber);

        String st = dataItems.get(position).getSt();
        if (st.contains("2")) {
            st = "Aktif";
        } else {
            st = "Tutup";
        }

        holder.tvListBendelSt.setText(st);

        String kini = dataItems.get(position).getRlTrbaca().get(0).getKini();
        if (kini == null) {
            kini = "-";
        }

        if (Objects.equals(dataItems.get(position).getRlDtBacaSekarang().get(0).getTandai(), "1")) {
            holder.ivTandai.setVisibility(View.GONE);
            holder.ivTandaiPelanggan.setVisibility(View.VISIBLE);
        } else {
            holder.ivTandai.setVisibility(View.VISIBLE);
            holder.ivTandaiPelanggan.setVisibility(View.GONE);
        }

        holder.tvListBendelLalu.setText(kini + "\n"
                + dataItems.get(position).getRlTrbaca().get(0).getM3() + "m3");
//        holder.tvListBendelDibuat.setText("Generate by System Cabang " + cabang + "-" + periode);

        holder.cvKlik.setOnClickListener(v -> {
            codeInputData = "1";
            Intent intent = new Intent(context, BendelPembacaKhususActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, dataItems.get(position).getNolangg());
//            intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_BENDEL_NEXT, bendel);
//            intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA, codeInputData);
            context.startActivity(intent);
        });

        ArrayList<RlDataBacaSekarang> listDataBacaSekarang = new ArrayList<>();
        RlDataBacaSekarang rlDataBacaSekarang = new RlDataBacaSekarang();
        rlDataBacaSekarang.setTandai(dataItems.get(position).getRlDtBacaSekarang().get(0).getTandai());
        listDataBacaSekarang.add(rlDataBacaSekarang);

//        holder.ivTandai.setOnClickListener(view -> {
//
//        });
//        holder.ivTandaiPelanggan.setOnClickListener(view -> {
//            Toast.makeText(context, "Sudah ditandai", Toast.LENGTH_SHORT).show();
//        });

        String kodeTandaiServer = dataItems.get(position).getRlDtBacaSekarang().get(0).getTandai();
        holder.divTandai.setOnClickListener(view -> {
            String kodeTandai = null;
            if (kodeTandaiServer == null) {
                kodeTandai = "1";
                holder.ivTandai.setVisibility(View.GONE);
                holder.ivTandaiPelanggan.setVisibility(View.VISIBLE);
            } else if (kodeTandaiServer.equals("1")) {
                kodeTandai = "0";
                holder.ivTandai.setVisibility(View.VISIBLE);
                holder.ivTandaiPelanggan.setVisibility(View.GONE);
            } else if (kodeTandaiServer.equals("0")){
                kodeTandai = "1";
                holder.ivTandai.setVisibility(View.GONE);
                holder.ivTandaiPelanggan.setVisibility(View.VISIBLE);
            }

            ApiService apiService = ApiConfig.getApiServiceGWAPI(context);
            apiService.postUpdateTandaPlg(token, dataItems.get(position).getNolangg(), bendel, kodeTandai)
                    .enqueue(new Callback<TandaiBendelRootModel>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(Call<TandaiBendelRootModel> call, Response<TandaiBendelRootModel> response) {
                            if (response.isSuccessful()) {
//                                holder.ivTandai.setVisibility(View.GONE);
//                                holder.ivTandaiPelanggan.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "Sukses " + dataItems.get(position).getNolangg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TandaiBendelRootModel> call, Throwable t) {
                            Toast.makeText(context, "Tandai PLG Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
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
        //        private TextView tvListBendelDibuat;
        private ImageView ivTandai;
        private ImageView ivTandaiPelanggan;
        private ImageView ivSurveyKoordinat;
        private TextView tvNo;
        private LinearLayout divTandai;
        private LinearLayout divNolangg;
        private LinearLayout divCopy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cvKlik);
            tvListBendelNolangg = itemView.findViewById(R.id.tv_list_bendel_nolangg);
            tvListBendelDism = itemView.findViewById(R.id.tv_list_bendel_dism);
            tvListBendelNama = itemView.findViewById(R.id.tv_list_bendel_nama);
            tvListBendelAlamat = itemView.findViewById(R.id.tv_list_bendel_alamat);
            tvListBendelSt = itemView.findViewById(R.id.tv_list_bendel_st);
            tvListBendelLalu = itemView.findViewById(R.id.tv_list_bendel_lalu);
//            tvListBendelDibuat = itemView.findViewById(R.id.tv_list_bendel_dibuat);
            ivTandai = itemView.findViewById(R.id.iv_tandai);
            ivTandaiPelanggan = itemView.findViewById(R.id.iv_tandai_success);
            ivSurveyKoordinat = itemView.findViewById(R.id.iv_survey_koordinat);
            tvNo = itemView.findViewById(R.id.tv_no);
            divTandai = itemView.findViewById(R.id.div_tandai);
            divNolangg = itemView.findViewById(R.id.div_nolangg);
            divCopy = itemView.findViewById(R.id.div_copy);
        }
    }
}
