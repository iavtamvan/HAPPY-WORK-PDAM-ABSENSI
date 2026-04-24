package id.pdamkotasmg.edms.fitur.suratMasuk.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;
import java.util.Random;

import id.pdamkotasmg.edms.R;
import id.pdamkotasmg.edms.api.ApiConfigEDMS;
import id.pdamkotasmg.edms.api.ApiServiceEDMS;
import id.pdamkotasmg.edms.fitur.suratMasuk.adapter.SuratMasukAdapter;
import id.pdamkotasmg.edms.fitur.suratMasuk.model.DataItem;
import id.pdamkotasmg.edms.fitur.suratMasuk.model.SuratMasukRootModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EDMSHomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private String accessToken;

    private List<DataItem> dataItems;
    private SuratMasukAdapter suratMasukAdapter;

    private ProgressDialog progressDialog;

    private ImageView ivMore;
    private TextView tvJudulSurat;
    private RecyclerView rvEdms;
    private SearchView svCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edmshome);
        
        initView();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");

        progressDialog = new ProgressDialog(EDMSHomeActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data..");
        getDataSuratMasuk();

    }

    private void getDataSuratMasuk() {
        progressDialog.show();
        ApiServiceEDMS apiServiceEDMS = ApiConfigEDMS.getApiService(EDMSHomeActivity.this);
        apiServiceEDMS.getSuratMasuk(accessToken)
                .enqueue(new Callback<SuratMasukRootModel>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<SuratMasukRootModel> call, Response<SuratMasukRootModel> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()){
                            dataItems = response.body().getData();
                            suratMasukAdapter = new SuratMasukAdapter(EDMSHomeActivity.this, dataItems);
                            rvEdms.setLayoutManager(new LinearLayoutManager(EDMSHomeActivity.this));
                            rvEdms.setAdapter(suratMasukAdapter);
                            suratMasukAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuratMasukRootModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(EDMSHomeActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(115), rnd.nextInt(198), rnd.nextInt(202));
    }

    private void initView() {
        ivMore = findViewById(R.id.iv_more);
        tvJudulSurat = findViewById(R.id.tv_judul_surat);
        rvEdms = findViewById(R.id.rv_edms);
        svCari = findViewById(R.id.sv_cari);
    }
}