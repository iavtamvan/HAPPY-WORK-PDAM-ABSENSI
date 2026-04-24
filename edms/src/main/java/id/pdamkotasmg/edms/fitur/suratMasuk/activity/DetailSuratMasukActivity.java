package id.pdamkotasmg.edms.fitur.suratMasuk.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pdamkotasmg.goodday.utils.AlphabetColor;
import com.pdamkotasmg.goodday.utils.Config;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private String urlFile1;
    private String urlFile2;
    private String nameFile1;
    private String nameFile2;
    private String nameFileGlobals;

    private ProgressDialog progressDialog;

    private String trxSuratMasuk;
//    private ImageView ivHeaderBackArrow;
//    private TextView tvHeaderJudul;
//    private ImageView ivHeaderInfo;
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
    private LinearLayout divLampiran1;
    private TextView tvFileLampiran1;
    private LinearLayout divLampiran2;
    private TextView tvFileLampiran2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_masuk);
        
        initView();

//        ivHeaderBackArrow.setOnClickListener(view -> {
//            finish();
//        });
//
//        tvHeaderJudul.setText("Surat Masuk");
//        ivHeaderInfo.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        trxSuratMasuk = getIntent().getStringExtra(Config.BUNDLE_NUMBER_TRX_SURAT);

        progressDialog = new ProgressDialog(DetailSuratMasukActivity.this);
        progressDialog.setCancelable(false);
        getData();

        tvPerihal.setOnClickListener(view -> {
            String replacement = urlFile1.replace("https://gateway.pdamkotasmg.co.id/api-gw-dev/file-handler/document/?filename=", "/").trim();
            Log.d(TAG, "replacement: " + replacement.trim());
//            downloadFile(replacement);
            nameFileGlobals = nameFile1;
            new DownloadFileFromURL().execute(urlFile1);
        });

        divLampiran1.setOnClickListener(view -> {
            String replacement = urlFile1.replace("https://gateway.pdamkotasmg.co.id/api-gw-dev/file-handler/document/?filename=", "/").trim();
            Log.d(TAG, "replacement: " + replacement.trim());
//            downloadFile(replacement);
            nameFileGlobals = nameFile1;
            new DownloadFileFromURL().execute(urlFile1);
        });

        divLampiran2.setOnClickListener(view -> {
            String replacement = urlFile2.replace("https://gateway.pdamkotasmg.co.id/api-gw-dev/file-handler/document/?filename=", "/").trim();
            Log.d(TAG, "replacement: " + replacement.trim());
//            downloadFile(replacement);
            nameFileGlobals = nameFile2;
            new DownloadFileFromURL().execute(urlFile2);
        });

    }

    private void getData() {
        progressDialog.setMessage("Loading data..");
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

                            urlFile1 = response.body().getData().get(0).getFileUrl1();
                            nameFile1 = response.body().getData().get(0).getFileName1();
                            tvFileLampiran1.setText(nameFile1);
                            urlFile2 = response.body().getData().get(0).getFileUrl2();
                            nameFile2 = response.body().getData().get(0).getFileName2();
                            tvFileLampiran2.setText(nameFile2);
                        }
                    }

                    @Override
                    public void onFailure(Call<SuratMasukRootModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailSuratMasukActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         **/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Mengunduh file..");
            progressDialog.show();
//            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         **/
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                Date currentTime = Calendar.getInstance().getTime();

                // Output stream
                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/PDAM/" + nameFile1 + "-" + currentTime.getTime() + ".xlsx");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                Log.d(TAG, "doInBackground: " + output);
                Log.d(TAG, "Aww: Sukses");
                progressDialog.dismiss();
            } catch (Exception e) {
                progressDialog.dismiss();
//                Toast.makeText(DetailSuratMasukActivity.this, "Error mengunduh", Toast.LENGTH_SHORT).show();
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            progressDialog.dismiss();
        }

        @Override
        protected void onPostExecute(String file_url) {
            Toast.makeText(DetailSuratMasukActivity.this, "Berhasil di simpan", Toast.LENGTH_SHORT).show();
            Config.showNotification(DetailSuratMasukActivity.this, "Surat Masuk", "Berhasil menyimpan Surat Masuk " + nameFileGlobals + ", cek di Folder Download/PDAM!", DetailSuratMasukActivity.class);
            progressDialog.dismiss();
        }
    }

    private void initView() {
//        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
//        tvHeaderJudul = findViewById(R.id.tv_header_judul);
//        ivHeaderInfo = findViewById(R.id.iv_header_info);
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
        divLampiran1 = findViewById(R.id.div_lampiran_1);
        tvFileLampiran1 = findViewById(R.id.tv_file_lampiran_1);
        divLampiran2 = findViewById(R.id.div_lampiran_2);
        tvFileLampiran2 = findViewById(R.id.tv_file_lampiran_2);
    }
}