package co.id.pdamkotasmg.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.checkByNolangg.CheckByNolanggRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.FragmentInputBinding;
import co.id.pdamkotasmg.ui.activity.PembacaMeterActivity;
import co.id.pdamkotasmg.ui.activity.RiwayatPembacaMeterActivity;
import co.id.pdamkotasmg.ui.activity.VerifikasiDitolakActivity;
import co.id.pdamkotasmg.ui.activity.bendel.InputDataActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputFragment extends Fragment {

    private FragmentInputBinding binding;
    private String codeInputData;
    private String token;
    private String nolangg;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInputBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sp = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu...");

        binding.tvHeaderJudul.setText("Inpu Baca Meter");
        binding.ivHeaderInfo.setOnClickListener(view -> Config.logout(getActivity()));

        binding.divInBendel.setOnClickListener(view -> {
            codeInputData = "1";
            Intent intent = new Intent(getActivity(), InputDataActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA, codeInputData);
            getActivity().startActivity(intent);
        });
        binding.divInBacaUlang.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Nolangg");

            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                nolangg = input.getText().toString();
                if (nolangg.isEmpty()) {
                    Toast.makeText(getActivity(), "Isi nolangg", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    codeInputData = "8";
                    checkPelanggan(nolangg, codeInputData);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();

        });

        binding.divInPerPelanggan.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Nolangg");

            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                nolangg = input.getText().toString();
                if (nolangg.isEmpty()) {
                    Toast.makeText(getActivity(), "Isi nolangg", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    codeInputData = "2";
                    checkPelanggan(nolangg, codeInputData);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        binding.divInFotoMeter.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Nolangg");

            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                nolangg = input.getText().toString();
                if (nolangg.isEmpty()) {
                    Toast.makeText(getActivity(), "Isi nolangg", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    codeInputData = "3";
                    checkPelanggan(nolangg, codeInputData);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        binding.divRiwayatBacaMeter.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), RiwayatPembacaMeterActivity.class));
        });

        binding.divInVerifikasiDitolak.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), VerifikasiDitolakActivity.class));
        });

        binding.divInFotoMeterManual.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Belum tersedia", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    private void checkPelanggan(String nolangg, String codeInputData) {
        ApiService apiService = ApiConfig.getApiServiceGWAPI(getActivity());
        apiService.getCheckPelangganStatus(token, nolangg)
                .enqueue(new Callback<CheckByNolanggRootModel>() {
                    @Override
                    public void onResponse(Call<CheckByNolanggRootModel> call, Response<CheckByNolanggRootModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData().isEmpty()) {
                                Toast.makeText(getActivity(), "Data tidak ada/nolangg salah", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            } else {
                                if (response.body().getData().get(0).getRlStatusPelanggan().getKode().contains("4")) {
                                    Toast.makeText(getActivity(), "Status pelanggan tutup/blokir", Toast.LENGTH_LONG).show();
                                    progressDialog.cancel();
                                } else {
                                    progressDialog.cancel();
                                    Intent intent = new Intent(getActivity(), PembacaMeterActivity.class);
                                    intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, nolangg);
                                    intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA, codeInputData);
                                    getActivity().startActivity(intent);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckByNolanggRootModel> call, Throwable throwable) {
                        progressDialog.cancel();
                        Toast.makeText(getActivity(), "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}