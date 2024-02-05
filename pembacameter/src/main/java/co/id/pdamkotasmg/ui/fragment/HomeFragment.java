package co.id.pdamkotasmg.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pdamkotasmg.goodday.fitur.profil.ProfileActivity;
import com.pdamkotasmg.goodday.fitur.profil.controller.ProfileController;
import com.pdamkotasmg.goodday.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.Calendar;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.home.HomeRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.FragmentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private String TAG = "debug";

    private FragmentHomeBinding binding;
    private ProfileController profileController;
    private ProgressDialog progressDialog;
    private String token;
    private String npp;
    private String nama;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        SharedPreferences sp = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        nama = sp.getString(Config.SHARED_NAME, "");
        npp = sp.getString(Config.SHARED_NPP_PROFILE, "");
        profileController = new ProfileController();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.show();

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 11) {
            binding.tvGood.setText("Good Morning \uD83C\uDF04");
            Config.showNotification(getActivity(), "Pekerjaan Sudah Siap", "Semangat kerja !!!");
        } else if (timeOfDay >= 11 && timeOfDay < 15) {
            binding.tvGood.setText("Good Afternoon \uD83C\uDF1E");
            Config.showNotification(getActivity(), "Sudah Waktunya Istirahat", "Semangat!!!");
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            Config.showNotification(getActivity(), "Saatnya Istirahat Sejenak", "Kalau lembur, jangan lupa klik Tidur YA!");
            binding.tvGood.setText("Good Evening \uD83C\uDF25");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            Config.showNotification(getActivity(), "Saatnya Tidur", "Kalau lembur, jangan lupa tidur YA!");
            binding.tvGood.setText("Good Night \uD83D\uDECC \uD83D\uDCA4");
        }

        binding.tvNameDashboard.setText(nama + " (" + npp + ")");
        binding.tvSatker.setText("Pembaca Meter");

        binding.ivLogout.setOnClickListener(view -> {
            MaterialDialog mDialog = new MaterialDialog.Builder(getActivity())
                    .setMessage("Yakin mau keluar?")
                    .setAnimation("lt_logout.json")
                    .setCancelable(true)
                    .setNegativeButton("Gak", (dialogInterface, which) -> dialogInterface.dismiss())
                    .setPositiveButton("Iya", (dialogInterface, which) -> {
                        profileController.logout(getActivity());
                        dialogInterface.dismiss();
                    })
                    .build();

            // Show Dialog
            mDialog.show();
        });

        binding.divProfil.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        });

        binding.ivRefresh.setOnClickListener(view -> {
            binding.tvTotalHariIni.setText("--");
            binding.tvJamAwal.setText("--");
            binding.tvPeriode.setText("--");
            binding.tvTotalPeriode.setText("--");
            progressDialog.show();
            getCountPeriode();
        });

        Log.d(TAG, "Access Token Pembaca Meter " + token);
        getCountPeriode();

        return root;
    }

    private void getCountPeriode() {
        ApiService apiService = ApiConfig.getApiService(getActivity());
        apiService.getCountPeriode(token)
                .enqueue(new Callback<HomeRootModel>() {
                    @Override
                    public void onResponse(Call<HomeRootModel> call, Response<HomeRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            binding.tvTotalHariIni.setText(response.body().getData().getCountTotalpembacaanPetugasHariIni());
                            binding.tvJamAwal.setText(response.body().getData().getJamAwal().getJamBaca());
                            binding.tvPeriode.setText("Total " + response.body().getData().getPeriode());
                            binding.tvTotalPeriode.setText(response.body().getData().getCountTotalPetugas());
                        } else {
                            progressDialog.cancel();
                            Config.logout(getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(getActivity(), "1: " + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}