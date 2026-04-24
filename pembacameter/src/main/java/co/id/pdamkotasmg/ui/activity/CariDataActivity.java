package co.id.pdamkotasmg.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.adapter.CariDataAdapter;
import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.cariData.CariDataRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityCariDataBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariDataActivity extends AppCompatActivity {

    private ActivityCariDataBinding binding;
    private CariDataAdapter cariDataAdapter;
    private ProgressDialog progressDialog;

    final String[] str = {"Pilih", "Nama", "Alamat", "Nometer"};

    private SharedPreferences sp;
    private String token;
    private String inputCariDataNama;
    private String inputCariDataAlamat;
    private String inputCariDataNometer;
    private String spnSelected;
    private String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivityCariDataBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.ivHeaderBackArrow.setOnClickListener(view -> CariDataActivity.this.finish());
        binding.tvHeaderJudul.setText("Cari Data");

        sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");

        progressDialog = new ProgressDialog(CariDataActivity.this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setCancelable(false);

        getCariData();

        binding.btnCariData.setOnClickListener(view -> {
            if (spnSelected.equalsIgnoreCase("Pilih")) {
                Toast.makeText(this, "Klik Pilih", Toast.LENGTH_SHORT).show();
            } else if (spnSelected.equalsIgnoreCase("nama")) {
                binding.edtInputan.setHint("Nama Plg");
                inputCariDataNama = binding.edtInputan.getText().toString().trim();
                inputCariDataAlamat = "";
                inputCariDataNometer = "";
            } else if (spnSelected.equalsIgnoreCase("alamat")) {
                binding.edtInputan.setHint("Alamat Plg");
                inputCariDataAlamat = binding.edtInputan.getText().toString().trim();
                inputCariDataNama = "";
                inputCariDataNometer = "";
            } else if (spnSelected.equalsIgnoreCase("nometer")) {
                binding.edtInputan.setHint("Nometer Plg");
                inputCariDataNometer = binding.edtInputan.getText().toString().trim();
                inputCariDataNama = "";
                inputCariDataAlamat = "";
            }

            getCariData();
        });

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, str);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnInputan.setAdapter(adp2);
        binding.spnInputan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnSelected = str[i];
                if (spnSelected.equalsIgnoreCase("Pilih")) {
                    Toast.makeText(CariDataActivity.this, "Klik Pilih", Toast.LENGTH_SHORT).show();
                    binding.edtInputan.setHint("Inputan");
                    binding.edtInputan.setText("");
                    binding.edtInputan.setEnabled(false);
                } else if (spnSelected.equalsIgnoreCase("nama")) {
                    binding.edtInputan.setEnabled(true);
                    binding.edtInputan.setHint("Nama Plg");
                } else if (spnSelected.equalsIgnoreCase("alamat")) {
                    binding.edtInputan.setEnabled(true);
                    binding.edtInputan.setHint("Alamat Plg");
                } else if (spnSelected.equalsIgnoreCase("nometer")) {
                    binding.edtInputan.setEnabled(true);
                    binding.edtInputan.setHint("Nometer Plg");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getCariData() {
        progressDialog.show();
        ApiService apiService = ApiConfig.getApiServiceGWAPI(CariDataActivity.this);
        apiService.getCariData(token, inputCariDataNama, inputCariDataAlamat, inputCariDataNometer)
                .enqueue(new Callback<CariDataRootModel>() {
                    @Override
                    public void onResponse(Call<CariDataRootModel> call, Response<CariDataRootModel> response) {
                        progressDialog.cancel();
                        if (response.isSuccessful()) {
                            if (response.body().getData().isEmpty()) {
                                Toast.makeText(CariDataActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            } else {
                                cariDataAdapter = new CariDataAdapter(CariDataActivity.this, response.body().getData());
                                binding.rv.setLayoutManager(new LinearLayoutManager(CariDataActivity.this));
                                binding.rv.setAdapter(cariDataAdapter);
                                cariDataAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CariDataRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(CariDataActivity.this, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}