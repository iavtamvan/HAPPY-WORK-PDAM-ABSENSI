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
    private String codeInputData;
    private final String codeBendel;
    private List<DataItem> dataItems;

    private int markedPosition = 1; // Initially, no position is marked

    public BendelAdapter(Context context, List<DataItem> dataItems) {
        this(context, dataItems, null);
    }

    public BendelAdapter(Context context, List<DataItem> dataItems, String codeBendel) {
        this.context = context;
        this.dataItems = dataItems;
        this.codeBendel = codeBendel;
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

        DataItem item = dataItems.get(position);

        // FIX: null-safe parsing dari dism. Sebelumnya getDism().substring(0,4)
        // bisa NPE kalau dism null & StringIndexOutOfBounds kalau panjang < 4.
        // Prefer codeBendel dari Activity (kalau ada), fallback ke prefix dism.
        // BUGFIX: `bendel` dulu field — saat scroll, value tertulis ulang oleh
        // baris terakhir & click listener baris N pakai value salah. Sekarang
        // pakai local `bendelLocal` yang di-capture per bind.
        final String bendelLocal;
        if (codeBendel != null && !codeBendel.isEmpty()) {
            bendelLocal = codeBendel;
        } else {
            String dismRaw = item.getDism();
            bendelLocal = (dismRaw != null && dismRaw.length() >= 4)
                    ? dismRaw.substring(0, 4)
                    : (dismRaw != null ? dismRaw : "");
        }

        holder.tvListBendelNolangg.setText(item.getNolangg());
        holder.tvListBendelDism.setText(item.getDism());
        holder.tvListBendelNama.setText(item.getNama());
        holder.tvListBendelAlamat.setText(item.getAlamat());

        // FIX: null-safe akses rl_dt_baca_periode_skrg.get(0). Sebelumnya NPE kalau
        // list null/empty.
        RlDataBacaSekarang bacaSekarang = null;
        if (item.getRlDtBacaSekarang() != null && !item.getRlDtBacaSekarang().isEmpty()) {
            bacaSekarang = item.getRlDtBacaSekarang().get(0);
        }

        String surveyKoordinat = bacaSekarang != null ? bacaSekarang.getSurveyKoordinat() : null;
        if (surveyKoordinat == null || surveyKoordinat.equals("0")) {
            holder.ivSurveyKoordinat.setVisibility(View.GONE);
        } else {
            // FIX: hapus Toast disini — onBindViewHolder dipanggil tiap scroll → spam toast.
            // Indikator icon sudah cukup.
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

        // FIX: null-safe getSt(). Sebelumnya kalau st null, st.contains("2") NPE.
        String stRaw = item.getSt();
        String stLabel = (stRaw != null && stRaw.contains("2")) ? "Aktif" : "Tutup";
        holder.tvListBendelSt.setText(stLabel);

        // FIX: null-safe akses rlTrbaca.get(0). Sebelumnya NPE kalau list null/empty.
        String kini = "-";
        String m3 = "-";
        if (item.getRlTrbaca() != null && !item.getRlTrbaca().isEmpty()) {
            String kiniRaw = item.getRlTrbaca().get(0).getKini();
            if (kiniRaw != null) kini = kiniRaw;
            String m3Raw = item.getRlTrbaca().get(0).getM3();
            if (m3Raw != null) m3 = m3Raw;
        }

        String tandaiVal = bacaSekarang != null ? bacaSekarang.getTandai() : null;
        if (Objects.equals(tandaiVal, "1")) {
            holder.ivTandai.setVisibility(View.GONE);
            holder.ivTandaiPelanggan.setVisibility(View.VISIBLE);
        } else {
            holder.ivTandai.setVisibility(View.VISIBLE);
            holder.ivTandaiPelanggan.setVisibility(View.GONE);
        }

        holder.tvListBendelLalu.setText(kini + "\n" + m3 + "m3");

        holder.cvKlik.setOnClickListener(v -> {
            codeInputData = "1";
            Intent intent = new Intent(context, BendelPembacaKhususActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, item.getNolangg());
            // FIX: teruskan codeBendel supaya BendelPembacaKhususActivity bisa
            // markPelangganAsRead di cache yang spesifik (tidak fallback global).
            if (!bendelLocal.isEmpty()) {
                intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_BENDEL, bendelLocal);
            }
            context.startActivity(intent);
        });

        // FIX: kalau bacaSekarang null, sembunyikan tombol tandai supaya tidak NPE saat klik.
        if (bacaSekarang == null) {
            holder.divTandai.setVisibility(View.GONE);
            return;
        }
        holder.divTandai.setVisibility(View.VISIBLE);

        // FIX: kodeTandaiServer dulu di-capture sekali & tidak pernah diperbarui setelah
        // toggle sukses, jadi klik kedua kirim nilai salah. Sekarang baca state TERKINI
        // dari item tiap klik.
        final RlDataBacaSekarang bacaSekarangFinal = bacaSekarang;
        holder.divTandai.setOnClickListener(view -> {
            String kodeTandaiCurrent = bacaSekarangFinal.getTandai();
            final String kodeTandai;
            if (kodeTandaiCurrent == null || kodeTandaiCurrent.equals("0")) {
                kodeTandai = "1";
                holder.ivTandai.setVisibility(View.GONE);
                holder.ivTandaiPelanggan.setVisibility(View.VISIBLE);
            } else {
                kodeTandai = "0";
                holder.ivTandai.setVisibility(View.VISIBLE);
                holder.ivTandaiPelanggan.setVisibility(View.GONE);
            }

            ApiService apiService = ApiConfig.getApiServiceGWAPI(context);
            apiService.postUpdateTandaPlg(token, item.getNolangg(), bendelLocal, kodeTandai)
                    .enqueue(new Callback<TandaiBendelRootModel>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(Call<TandaiBendelRootModel> call, Response<TandaiBendelRootModel> response) {
                            if (response.isSuccessful()) {
                                // FIX: update state lokal setelah sukses supaya klik berikutnya
                                // toggle ke arah yang benar.
                                bacaSekarangFinal.setTandai(kodeTandai);
                                Toast.makeText(context, "Sukses " + item.getNolangg(), Toast.LENGTH_SHORT).show();
                            } else {
                                // Rollback UI kalau server tolak
                                rollbackTandaiUi(holder, kodeTandaiCurrent);
                                Toast.makeText(context, "Tandai PLG Gagal (HTTP " + response.code() + ")", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TandaiBendelRootModel> call, Throwable t) {
                            rollbackTandaiUi(holder, kodeTandaiCurrent);
                            Toast.makeText(context, "Tandai PLG Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }

    private void rollbackTandaiUi(ViewHolder holder, String previousTandai) {
        if ("1".equals(previousTandai)) {
            holder.ivTandai.setVisibility(View.GONE);
            holder.ivTandaiPelanggan.setVisibility(View.VISIBLE);
        } else {
            holder.ivTandai.setVisibility(View.VISIBLE);
            holder.ivTandaiPelanggan.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataItems == null ? 0 : dataItems.size();
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
