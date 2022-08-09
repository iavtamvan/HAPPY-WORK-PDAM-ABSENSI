package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.activity;

import android.annotation.SuppressLint;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.RiwayatKehadiranRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.form.EditDetailsKehadiranAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.form.GetMyStaffOrSupervisiorAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff.GetMyStaffRootModel;
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
    private GetMyStaffOrSupervisiorAdapter getMyStaffOrSupervisiorAdapter;
    private List<com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff.DataItem> dataItemsGetMyStaffOrSupervisior;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public TextView tvTutupDialog;

    private String accesToken;
    private String name;
    public String npp;
    private List<DetailsItem> detailsItemArray = new ArrayList<DetailsItem>();
    private EditDetailsKehadiranAdapter editDetailsKehadiranAdapter;
    private List<DataItem> dataItems;

    private String flag;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    public EditText edtRequestFor;
    private EditText edtStartDate;
    private EditText edtEndDate;
    private AdView adView;
    private Button btnNewRequest;
    private RecyclerView rvDetailsAttedance;
    private TextView tvListKoreksiKehadiranDetailsText;

    String[] strArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koreksi_kehadiran);
        getSupportActionBar().hide();
        initView();

        tvHeaderJudul.setText("Detail Koreksi Kehadiran");
        ivHeaderInfo.setOnClickListener(v -> {
            KoreksiKehadiranActivity.this.finish();
        });
        ivHeaderInfo.setOnClickListener(v -> {
            Config.dialogAlert(KoreksiKehadiranActivity.this, "Info", "Isi koreksi kehadirand dengan benar dan jujur", "Ok");
        });

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accesToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        Log.d(TAG, "token: " + accesToken);

        edtRequestFor.setText(name + " (" + npp + ")");
        edtRequestFor.setOnClickListener(v -> {
            getMyStaff();
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
            detailsItemArray = editDetailsKehadiranAdapter.detailsItemArray;
            Log.d(TAG, "detailsItemArray : " + detailsItemArray.size());
            postJsonKoreksiKehadiran();

        });
    }

    private void getMyStaff() {
        ProgressDialog progressDialog = new ProgressDialog(KoreksiKehadiranActivity.this);
        progressDialog.setMessage("Mengambil data...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this);
        apiService.getMyStaff(accesToken)
                .enqueue(new Callback<GetMyStaffRootModel>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<GetMyStaffRootModel> call, Response<GetMyStaffRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            Log.d(TAG, "getMyStaff: " + response.body());
                            dataItemsGetMyStaffOrSupervisior = new ArrayList<>();
                            dataItemsGetMyStaffOrSupervisior = response.body().getData();
                            getMyStaffOrSupervisiorAdapter = new GetMyStaffOrSupervisiorAdapter(KoreksiKehadiranActivity.this, dataItemsGetMyStaffOrSupervisior);

                            final BottomSheetDialog bottomSheetDialogStaffOfSupervisior = new BottomSheetDialog(KoreksiKehadiranActivity.this);
                            bottomSheetDialogStaffOfSupervisior.setContentView(R.layout.bottom_sheet_dialog_get_staff_or_supervisior);

                            RecyclerView rvGetStafOrSupervisior = bottomSheetDialogStaffOfSupervisior.findViewById(R.id.rv_get_staff_or_supervisior);
                            tvTutupDialog = bottomSheetDialogStaffOfSupervisior.findViewById(R.id.tv_tutup_dialog);
                            rvGetStafOrSupervisior.setLayoutManager(new LinearLayoutManager(KoreksiKehadiranActivity.this));
                            rvGetStafOrSupervisior.setAdapter(getMyStaffOrSupervisiorAdapter);
                            getMyStaffOrSupervisiorAdapter.notifyDataSetChanged();

                            tvTutupDialog.setOnClickListener(v -> {
                                bottomSheetDialogStaffOfSupervisior.cancel();
                            });

                            bottomSheetDialogStaffOfSupervisior.show();


                        } else {
                            progressDialog.cancel();
                            Toast.makeText(KoreksiKehadiranActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetMyStaffRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(KoreksiKehadiranActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getHistoryPresensi() {
        ProgressDialog progressDialog = new ProgressDialog(KoreksiKehadiranActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this);
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
                                editDetailsKehadiranAdapter = new EditDetailsKehadiranAdapter(KoreksiKehadiranActivity.this, dataItems);
                                rvDetailsAttedance.setLayoutManager(new LinearLayoutManager(KoreksiKehadiranActivity.this));
                                rvDetailsAttedance.setAdapter(editDetailsKehadiranAdapter);
                                editDetailsKehadiranAdapter.notifyDataSetChanged();
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
        Log.d(TAG, "NPP Updated : " + npp);
        ProgressDialog progressDialog = new ProgressDialog(KoreksiKehadiranActivity.this);
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService(this);
        apiService.postJson(accesToken, new KoreksiKeharidanRootModel(edtEndDate.getText().toString().trim(), npp, "RAC", detailsItemArray, edtStartDate.getText().toString().trim()))
                .enqueue(new Callback<KoreksiKeharidanRootModel>() {
                    @Override
                    public void onResponse(Call<KoreksiKeharidanRootModel> call, Response<KoreksiKeharidanRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            Config.dialogAlertSukses(KoreksiKehadiranActivity.this, "Koreksi Kehadiran", "Berhasil disimpan! " + response.message(), "Ok", RiwayatKoreksiKehadiranActivity.class);
                        } else {
                            Config.dialogAlertGagal(KoreksiKehadiranActivity.this, "Koreksi Kehadiran", "Gagal disimpan! " + response.message(), "Ok");
                        }
                    }

                    @Override
                    public void onFailure(Call<KoreksiKeharidanRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(KoreksiKehadiranActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
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
        tvListKoreksiKehadiranDetailsText = findViewById(R.id.tv_list_koreksi_kehadiran_details_text);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (flag.equals("startDate")) {
//            String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            Log.d(TAG, "onDateSet: " + date);
//            startDate = date;
            try {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date dates = fmt.parse(date);
                Log.d(TAG, "dates : " + dates);
                String dateFinal = new SimpleDateFormat("yyyy-MM-dd").format(dates);
                Log.d(TAG, "dateFinal : " + dateFinal);
                edtStartDate.setText(dateFinal);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            try {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date dates = fmt.parse(date);
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