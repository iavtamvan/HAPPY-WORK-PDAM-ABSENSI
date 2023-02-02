package id.pdamkotasmg.edms.fitur.suratMasuk.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.goodday.utils.AlphabetColor;
import com.pdamkotasmg.goodday.utils.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import id.pdamkotasmg.edms.R;
import id.pdamkotasmg.edms.api.ApiConfigEDMS;
import id.pdamkotasmg.edms.api.ApiServiceEDMS;
import id.pdamkotasmg.edms.fitur.suratMasuk.model.SuratMasukRootModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSuratMasukActivity extends AppCompatActivity {
    private static final String TAG = "debug";

    private SharedPreferences sharedPreferences;
    private String accessToken;

    private ProgressDialog progressDialog;

    private String trxSuratMasuk;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private TextView tvPerihal;
    private TextView tvJenis;
    private CircleImageView ciAvatar;
    private TextView tvFirstChar;
    private TextView tvNamaAsalSurat;
    private LinearLayout divExpandDetailSurat;
    private LinearLayout divSuratDetail;
    private TextView tvNamaAsalSuratDetail;
    private TextView tvTujuanSuratDetail;
    private TextView tvWaktuTglSuratDetail;
    private TextView tvSifatDetail;
    private TextView tvSifatDetailTop;
    private TextView tvTipeSurat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_masuk);
        getSupportActionBar().hide();
        initView();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        trxSuratMasuk = getIntent().getStringExtra(Config.BUNDLE_NUMBER_TRX_SURAT);

        progressDialog = new ProgressDialog(DetailSuratMasukActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data..");
        getData();
    }

    private void getData() {
        progressDialog.show();
        ApiServiceEDMS apiServiceEDMS = ApiConfigEDMS.getApiService(DetailSuratMasukActivity.this);
        apiServiceEDMS.postDetailSuratMasuk(accessToken, trxSuratMasuk)
                .enqueue(new Callback<SuratMasukRootModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<SuratMasukRootModel> call, Response<SuratMasukRootModel> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            tvPerihal.setText(response.body().getData().get(0).getSmPerihal());
                            tvJenis.setText(response.body().getData().get(0).getSmJenisSurat());

                            String replaceBagianAsal = response.body().getData().get(0).getSmAsalNama().replace("Bagian", "").trim();
                            String replaceBagianTujuan = response.body().getData().get(0).getSmTujuanNama().replace("Bagian", "").trim();

                            tvNamaAsalSurat.setText(replaceBagianAsal);
                            tvNamaAsalSuratDetail.setText(replaceBagianAsal + " • " + response.body().getData().get(0).getSmCreatedBy());
                            tvTujuanSuratDetail.setText(replaceBagianTujuan);

                            char firstChar = replaceBagianAsal.charAt(0);
                            int colors = AlphabetColor.getColor(firstChar);
                            tvFirstChar.setText(String.valueOf(firstChar));
                            ciAvatar.setImageResource(colors);

                            SimpleDateFormat dateNtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            try {
                                Date dateRequestAt = dateNtime.parse(response.body().getData().get(0).getSmTglTerima());

                                String dateRequestAtFix = new SimpleDateFormat("dd MMM yyyy hh.mm").format(dateRequestAt);
                                tvWaktuTglSuratDetail.setText(dateRequestAtFix);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            tvSifatDetail.setText(response.body().getData().get(0).getSmSifat());
                            tvSifatDetailTop.setText(response.body().getData().get(0).getSmSifat());
                            tvTipeSurat.setText(response.body().getData().get(0).getModalType());

                        }
                    }

                    @Override
                    public void onFailure(Call<SuratMasukRootModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailSuratMasukActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        tvPerihal = findViewById(R.id.tv_perihal);
        tvJenis = findViewById(R.id.tv_jenis);
        ciAvatar = findViewById(R.id.ci_avatar);
        tvFirstChar = findViewById(R.id.tv_first_char);
        tvNamaAsalSurat = findViewById(R.id.tv_nama_asal_surat);
        divExpandDetailSurat = findViewById(R.id.div_expand_detail_surat);
        divSuratDetail = findViewById(R.id.div_surat_detail);
        tvNamaAsalSuratDetail = findViewById(R.id.tv_nama_asal_surat_detail);
        tvTujuanSuratDetail = findViewById(R.id.tv_tujuan_surat_detail);
        tvWaktuTglSuratDetail = findViewById(R.id.tv_waktu_tgl_surat_detail);
        tvSifatDetail = findViewById(R.id.tv_sifat_detail);
        tvSifatDetailTop = findViewById(R.id.tv_sifat_detail_top);
        tvTipeSurat = findViewById(R.id.tv_tipe_surat);
    }
}