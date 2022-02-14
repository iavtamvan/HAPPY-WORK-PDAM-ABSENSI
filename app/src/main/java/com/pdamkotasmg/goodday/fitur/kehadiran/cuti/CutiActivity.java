package com.pdamkotasmg.goodday.fitur.kehadiran.cuti;

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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.tipeCuti.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.tipeCuti.TipeCutiRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.form.GetMyStaffOrSupervisiorAdapter;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff.GetMyStaffRootModel;
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

public class CutiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final String TAG = "debug";

    private List<com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff.DataItem> dataItemsGetMyStaffOrSupervisior;
    private GetMyStaffOrSupervisiorAdapter getMyStaffOrSupervisiorAdapter;
    public TextView tvTutupDialog;

    private SharedPreferences sharedPreferences;
    private String flag;
    private String accesToken;
    private String name;
    public String npp;

    private ArrayList<String> arrayTipeCuti = new ArrayList<>();
    private ArrayList<String> arrayTipeCutiId = new ArrayList<String>();
    private String tipeCutiID;
    private String tipeCutiString;
    private List<DataItem> tipeCutiItems = new ArrayList<>();

    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private EditText edtRequestFor;
    private LinearLayout divInfoRemaining;
    private TextView tvValidityDateEnd;
    private TextView tvRemaining;
    private EditText edtStartDate;
    private EditText edtEndDate;
    private EditText edtRemark;
    private Button btnNewRequest;
    private MaterialSpinner spnAsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuti);
        getSupportActionBar().hide();
        initView();

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        accesToken = sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "");
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        Log.d(TAG, "token: " + accesToken);
        getTipeCuti();

        edtRequestFor.setText(name + " (" + npp + ")");
        edtRequestFor.setOnClickListener(v -> {
            getMyStaff();
        });

        edtStartDate.setOnClickListener(v -> {
            flag = "startDate";
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    CutiActivity.this,
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
                    CutiActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.setThemeDark(true);
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

    }

    private void getTipeCuti() {
        ProgressDialog progressDialog = new ProgressDialog(CutiActivity.this);
        progressDialog.setMessage("Mengambil data...");
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getTipeCuti(accesToken, "1")
                .enqueue(new Callback<TipeCutiRootModel>() {
                    @Override
                    public void onResponse(Call<TipeCutiRootModel> call, Response<TipeCutiRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            tipeCutiItems = response.body().getData();
                            for (int i = 0; i < tipeCutiItems.size(); i++) {
                                String kode = String.valueOf(tipeCutiItems.get(i).getId());
                                String ket = tipeCutiItems.get(i).getRequestLeaveTypeName();
                                arrayTipeCutiId.add(kode);
                                arrayTipeCuti.add(ket);
                            }
                            spnAsal.setItems(arrayTipeCuti);

                        }
                    }

                    @Override
                    public void onFailure(Call<TipeCutiRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(CutiActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void getMyStaff() {
        ProgressDialog progressDialog = new ProgressDialog(CutiActivity.this);
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
                            getMyStaffOrSupervisiorAdapter = new GetMyStaffOrSupervisiorAdapter(CutiActivity.this, dataItemsGetMyStaffOrSupervisior);

                            final BottomSheetDialog bottomSheetDialogStaffOfSupervisior = new BottomSheetDialog(CutiActivity.this);
                            bottomSheetDialogStaffOfSupervisior.setContentView(R.layout.bottom_sheet_dialog_get_staff_or_supervisior);

                            RecyclerView rvGetStafOrSupervisior = bottomSheetDialogStaffOfSupervisior.findViewById(R.id.rv_get_staff_or_supervisior);
                            tvTutupDialog = bottomSheetDialogStaffOfSupervisior.findViewById(R.id.tv_tutup_dialog);
                            rvGetStafOrSupervisior.setLayoutManager(new LinearLayoutManager(CutiActivity.this));
                            rvGetStafOrSupervisior.setAdapter(getMyStaffOrSupervisiorAdapter);
                            getMyStaffOrSupervisiorAdapter.notifyDataSetChanged();

                            tvTutupDialog.setOnClickListener(v -> {
                                bottomSheetDialogStaffOfSupervisior.cancel();
                            });

                            bottomSheetDialogStaffOfSupervisior.show();


                        } else {
                            progressDialog.cancel();
                            Toast.makeText(CutiActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetMyStaffRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(CutiActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        edtRequestFor = findViewById(R.id.edt_request_for);
        divInfoRemaining = findViewById(R.id.div_info_remaining);
        tvValidityDateEnd = findViewById(R.id.tv_validity_date_end);
        tvRemaining = findViewById(R.id.tv_remaining);
        edtStartDate = findViewById(R.id.edt_start_date);
        edtEndDate = findViewById(R.id.edt_end_date);
        edtRemark = findViewById(R.id.edt_remark);
        btnNewRequest = findViewById(R.id.btn_new_request);
        spnAsal = findViewById(R.id.spn_asal);
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