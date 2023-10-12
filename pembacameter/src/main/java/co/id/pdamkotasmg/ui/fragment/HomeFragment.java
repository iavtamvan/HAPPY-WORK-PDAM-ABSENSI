package co.id.pdamkotasmg.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.pelanggan.PelangganByNolanggRootModel;
import co.id.pdamkotasmg.pembacameter.databinding.ContentHeaderNoArrowBinding;
import co.id.pdamkotasmg.pembacameter.databinding.FragmentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ContentHeaderNoArrowBinding contentHeaderNoArrowBinding;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        contentHeaderNoArrowBinding = ContentHeaderNoArrowBinding.inflate(inflater, container, false);
//        View contentRoot = contentHeaderNoArrowBinding.getRoot();

        contentHeaderNoArrowBinding.tvHeaderJudul.setText("ANJAY");

        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        SharedPreferences sp = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");

        getDataPelanggan();

        return root;
    }

    private void getDataPelanggan() {
        ApiService apiService = ApiConfig.getApiService(getActivity());
        apiService.getPelanggan(token, "05130044")
                .enqueue(new Callback<PelangganByNolanggRootModel>() {
                    @Override
                    public void onResponse(Call<PelangganByNolanggRootModel> call, Response<PelangganByNolanggRootModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "" + response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PelangganByNolanggRootModel> call, Throwable t) {
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