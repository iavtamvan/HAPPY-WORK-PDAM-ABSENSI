package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.RiwayatKehadiranRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.DetailsKehadiranAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran.DetailsItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran.KoreksiKeharidanRootModel;
import com.pdamkotasmg.goodday.utils.Config;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KoreksiKehadiranActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private final String TAG = "debug";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String accesToken;
    private String name;
    private String npp;
    private List<DetailsItem> detailsItemArray = new ArrayList<DetailsItem>();
    private DetailsKehadiranAdapter detailsKehadiranAdapter;
    private List<DataItem> dataItems;
    private String resultDetails;

    private String flag;
    private String startDate;
    private String endDate;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private EditText edtRequestFor;
    private EditText edtStartDate;
    private EditText edtEndDate;
    private AdView adView;
    private Button btnNewRequest;
    private RecyclerView rvDetailsAttedance;
    private TextView tvListKoreksiKehadiranRequestAt;
    private EditText edtReason;
    private EditText edtStartTime;
    private EditText edtEndTime;
    private TextView tvListKoreksiKehadiranDetailsText;

    String[] strArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koreksi_kehadiran);
        getSupportActionBar().hide();
        initView();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accesToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        Log.d(TAG, "token: " + accesToken);

        resultDetails = sharedPreferences.getString(Config.SHARED_SAVE_ARRAY_DETAILS_KOREKSI_KEHADIRAN, "");

        edtRequestFor.setText(name + " (" + npp + ")");
        edtRequestFor.setOnClickListener(v -> {

        });

        edtStartDate.setOnClickListener(v -> {
            flag = "startDate";
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    KoreksiKehadiranActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setThemeDark(true);
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

        edtEndDate.setOnClickListener(v -> {
            flag = "endDate";
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    KoreksiKehadiranActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setThemeDark(true);
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

        btnNewRequest.setOnClickListener(v -> {
//            postJsonKoreksiKehadiran();
            detailsItemArray = detailsKehadiranAdapter.detailsItemArray;
            Log.d(TAG, "detailsItemArray : " + detailsItemArray.size());
            postJsonKoreksiKehadiran();

//            try {
//                JSONArray jsonArray = new JSONArray(resultDetails);
//                strArr = new String[jsonArray.length()];
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    strArr[i] = jsonArray.getString(i);
//                }
//                Log.d(TAG, "jsonArray :  " + Arrays.toString(strArr));
////                System.out.println(Arrays.toString(strArr));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        });

//        postJsonKoreksiKehadiran();
//        getHistoryPresensi();
    }

    private void getHistoryPresensi() {
        ProgressDialog progressDialog = new ProgressDialog(KoreksiKehadiranActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getHistoryPresensi(accesToken, edtStartDate.getText().toString().trim(), edtEndDate.getText().toString().trim(), "1") // tanggal dari, dan tanggal selesai
                .enqueue(new Callback<RiwayatKehadiranRootModel>() {
                    @Override
                    public void onResponse(Call<RiwayatKehadiranRootModel> call, Response<RiwayatKehadiranRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            dataItems = new ArrayList<>();
                            dataItems = response.body().getData();
                            rvDetailsAttedance.setVisibility(View.VISIBLE);
                            if (dataItems.isEmpty()) {
                                tvListKoreksiKehadiranDetailsText.setText("Data tidak ada");
                                rvDetailsAttedance.setVisibility(View.GONE);
                            } else {
                                tvListKoreksiKehadiranDetailsText.setText("Detail");
                                detailsKehadiranAdapter = new DetailsKehadiranAdapter(KoreksiKehadiranActivity.this, dataItems);
                                rvDetailsAttedance.setLayoutManager(new LinearLayoutManager(KoreksiKehadiranActivity.this));
                                rvDetailsAttedance.setAdapter(detailsKehadiranAdapter);
                                detailsKehadiranAdapter.notifyDataSetChanged();
                            }
                        } else {
                            progressDialog.cancel();
                            tvListKoreksiKehadiranDetailsText.setText(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatKehadiranRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(KoreksiKehadiranActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postJsonKoreksiKehadiran() {

//        listDetails.add(detailsItem);
//        detailsItemArray.add(resultDetails);

        Gson gson = new Gson();
        String arrayListDetails = gson.toJson(resultDetails);
//        Log.d(TAG, "ArayList: " + listDetails);
//        Log.d(TAG, "Gson: " + arrayListDetails);



        ApiService apiService = ApiConfig.getApiService();
//        apiService.postJson(listDetails)
        apiService.postJson(accesToken, new KoreksiKeharidanRootModel(edtEndDate.getText().toString().trim(), npp, "RAC", detailsItemArray, edtStartDate.getText().toString().trim()))
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
        rvDetailsAttedance = findViewById(R.id.rv_details_attedance);
        tvListKoreksiKehadiranRequestAt = findViewById(R.id.tv_list_koreksi_kehadiran_request_at);
        edtReason = findViewById(R.id.edt_reason);
        edtStartTime = findViewById(R.id.edt_start_time);
        edtEndTime = findViewById(R.id.edt_end_time);
        tvListKoreksiKehadiranDetailsText = findViewById(R.id.tv_list_koreksi_kehadiran_details_text);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (flag.equals("startDate")) {
//            String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            startDate = date;
            try {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
                Date dates = fmt.parse(startDate);
                String dateFinal = new SimpleDateFormat("yyyy-MM-dd").format(dates);
                edtStartDate.setText(dateFinal);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            endDate = date;
            try {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
                Date dates = fmt.parse(endDate);
                String dateFinal = new SimpleDateFormat("yyyy-MM-dd").format(dates);
                edtEndDate.setText(dateFinal);
                tvListKoreksiKehadiranDetailsText.setVisibility(View.VISIBLE);
                getHistoryPresensi();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}