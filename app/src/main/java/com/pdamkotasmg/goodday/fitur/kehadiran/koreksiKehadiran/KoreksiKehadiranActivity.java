package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran.DetailsItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran.KoreksiKeharidanRootModel;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KoreksiKehadiranActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String accesToken;
    private ArrayList<DetailsItem> listDetails = new ArrayList<>();

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private EditText edtRequestFor;
    private EditText edtStartDate;
    private EditText edtEndDate;
    private AdView adView;
    private Button btnNewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koreksi_kehadiran);
        getSupportActionBar().hide();
        initView();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accesToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        Log.d(TAG, "token: " + accesToken);


        edtRequestFor.setOnClickListener(v -> {

        });

        edtStartDate.setOnClickListener(v -> {

        });
        edtEndDate.setOnClickListener(v -> {

        });

//        postJsonKoreksiKehadiran();
    }

    private void postJsonKoreksiKehadiran() {
        DetailsItem detailsItem = new DetailsItem();
        detailsItem.setReason("AKu coba aja");
        detailsItem.setRecordDateBefore("2021-12-28");
        detailsItem.setActualTimeInAfter("07:23:00");
        detailsItem.setActualTimeOutAfter("15:23:00");

        listDetails.add(detailsItem);

        Gson gson = new Gson();
        String arrayListDetails = gson.toJson(listDetails);
        Log.d(TAG, "ArayList: " + listDetails);
        Log.d(TAG, "Gson: " + arrayListDetails);

        ApiService apiService = ApiConfig.getApiService();
//        apiService.postJson(listDetails)
        apiService.postJson(accesToken, new KoreksiKeharidanRootModel("2021-12-28", "6908319016", "RAC", listDetails, "2021-12-28"))
                .enqueue(new Callback<KoreksiKeharidanRootModel>() {
                    @Override
                    public void onResponse(Call<KoreksiKeharidanRootModel> call, Response<KoreksiKeharidanRootModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(KoreksiKehadiranActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(KoreksiKehadiranActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KoreksiKeharidanRootModel> call, Throwable t) {
                        Toast.makeText(KoreksiKehadiranActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        edtRequestFor = findViewById(R.id.edt_request_for);
        edtStartDate = findViewById(R.id.edt_start_date);
        edtEndDate = findViewById(R.id.edt_end_date);
        adView = findViewById(R.id.adView);
        btnNewRequest = findViewById(R.id.btn_new_request);
    }
}