package com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.activity.RiwayatKoreksiKehadiranActivity;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff.GetMyStaffRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.adapter.GetMyStaffOrSupervisiorAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.postPerjalanDinas.DetailsItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.postPerjalanDinas.PostPerjalananDinasRootModel;
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

public class PerjalananDinasActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private final String TAG = "debug";
    private SharedPreferences sharedPreferences;
    private List<com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff.DataItem> dataItemsGetMyStaffOrSupervisior;
    private GetMyStaffOrSupervisiorAdapter getMyStaffOrSupervisiorAdapter;
    public TextView tvTutupDialog;

    private String accesToken;
    private String name;
    public String npp;

    private List<DetailsItem> myList;
    private String flag;

    private ProgressDialog progressDialog;

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    public EditText edtRequestFor;
    private EditText edtRemark;
    private TextView tvListKoreksiKehadiranDetailsText;
    private RecyclerView rvDetailsAttedance;
    private Button btnNewRequest;
    private LinearLayout cvKlik;
    private EditText edtPerjalananDinasStart;
    private EditText edtPerjalananDinasEnd;
    private Button btnKehadiranLihatDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perjalanan_dinas);
        getSupportActionBar().hide();
        initView();

        ivHeaderBackArrow.setOnClickListener(v -> {
            PerjalananDinasActivity.this.finish();
        });

        tvHeaderJudul.setText("Perjalanan Dinas");

        ivHeaderInfo.setOnClickListener(v -> {
            Toast.makeText(this, "Form Perjalanan Dinas", Toast.LENGTH_SHORT).show();
        });

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accesToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");

        edtRequestFor.setText(name + " (" + npp + ")");
        edtRequestFor.setOnClickListener(v -> {
            getMyStaff();
        });

        progressDialog = new ProgressDialog(PerjalananDinasActivity.this);

        edtPerjalananDinasStart.setOnClickListener(v -> {
            flag = "startDate";
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PerjalananDinasActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setThemeDark(true);
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

        edtPerjalananDinasEnd.setOnClickListener(v -> {
            flag = "endDate";
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PerjalananDinasActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setThemeDark(true);
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

        btnNewRequest.setOnClickListener(v -> {
            if (edtRemark.getText().toString().isEmpty() || edtPerjalananDinasStart.getText().toString().isEmpty() || edtPerjalananDinasEnd.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                myList = new ArrayList<DetailsItem>() {{
                    add(new DetailsItem("1", edtPerjalananDinasStart.getText().toString().trim(), edtPerjalananDinasEnd.getText().toString().trim()));
                }};
                Log.d(TAG, "Results : " + myList);

                postPerjalananDinas();
            }
        });

    }

    private void postPerjalananDinas() {
        progressDialog.setMessage("Mengambil data...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.postJsonPerjalananDinas(accesToken, new PostPerjalananDinasRootModel(npp, "ROD", "1", edtRemark.getText().toString(),
                myList))
                .enqueue(new Callback<PostPerjalananDinasRootModel>() {
                    @Override
                    public void onResponse(Call<PostPerjalananDinasRootModel> call, Response<PostPerjalananDinasRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            Config.dialogAlertSukses(PerjalananDinasActivity.this, "Perjalanan Dinas", "Berhasil disimpan! " + response.message(), "Ok", RiwayatKoreksiKehadiranActivity.class);
                        } else {
                            Config.dialogAlertSukses(PerjalananDinasActivity.this, "Perjalanan Dinas", "Gagal disimpan! " + response.message(), "Ok", RiwayatKoreksiKehadiranActivity.class);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostPerjalananDinasRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(PerjalananDinasActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMyStaff() {
        progressDialog.setMessage("Mengambil data...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
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
                            getMyStaffOrSupervisiorAdapter = new GetMyStaffOrSupervisiorAdapter(PerjalananDinasActivity.this, dataItemsGetMyStaffOrSupervisior);

                            final BottomSheetDialog bottomSheetDialogStaffOfSupervisior = new BottomSheetDialog(PerjalananDinasActivity.this);
                            bottomSheetDialogStaffOfSupervisior.setContentView(R.layout.bottom_sheet_dialog_get_staff_or_supervisior);

                            RecyclerView rvGetStafOrSupervisior = bottomSheetDialogStaffOfSupervisior.findViewById(R.id.rv_get_staff_or_supervisior);
                            tvTutupDialog = bottomSheetDialogStaffOfSupervisior.findViewById(R.id.tv_tutup_dialog);
                            rvGetStafOrSupervisior.setLayoutManager(new LinearLayoutManager(PerjalananDinasActivity.this));
                            rvGetStafOrSupervisior.setAdapter(getMyStaffOrSupervisiorAdapter);
                            getMyStaffOrSupervisiorAdapter.notifyDataSetChanged();

                            tvTutupDialog.setOnClickListener(v -> {
                                bottomSheetDialogStaffOfSupervisior.cancel();
                            });

                            bottomSheetDialogStaffOfSupervisior.show();


                        } else {
                            progressDialog.cancel();
                            Toast.makeText(PerjalananDinasActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetMyStaffRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(PerjalananDinasActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        edtRequestFor = findViewById(R.id.edt_request_for);
        edtRemark = findViewById(R.id.edt_remark);
        tvListKoreksiKehadiranDetailsText = findViewById(R.id.tv_list_koreksi_kehadiran_details_text);
        rvDetailsAttedance = findViewById(R.id.rv_details_attedance);
        btnNewRequest = findViewById(R.id.btn_new_request);
        cvKlik = findViewById(R.id.cv_klik);
        edtPerjalananDinasStart = findViewById(R.id.edt_perjalanan_dinas_start);
        edtPerjalananDinasEnd = findViewById(R.id.edt_perjalanan_dinas_end);
        btnKehadiranLihatDetail = findViewById(R.id.btn_kehadiran_lihat_detail);
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
                edtPerjalananDinasStart.setText(dateFinal);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            try {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date dates = fmt.parse(date);
                String dateFinal = new SimpleDateFormat("yyyy-MM-dd").format(dates);
                edtPerjalananDinasEnd.setText(dateFinal);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}