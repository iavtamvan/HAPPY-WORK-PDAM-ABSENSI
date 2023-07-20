package co.id.pdamkotasmg.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.pembacameter.databinding.FragmentInputDataBinding;
import co.id.pdamkotasmg.ui.activity.InputDataActivity;

public class InputDataFragment extends Fragment {

    private FragmentInputDataBinding binding;
    private String codeInputData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInputDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.divInBendel.setOnClickListener(view -> {
            codeInputData = "1";
            Intent intent = new Intent(getActivity(), InputDataActivity.class);
            intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA, codeInputData);
            getActivity().startActivity(intent);
        });
        binding.divInBacaUlang.setOnClickListener(view -> {
//            codeInputData = "5";
//            Intent intent = new Intent(getActivity(), InputDataActivity.class);
//            intent.putExtra(Config.BUNDLE_PEMBACA_METER_CODE_INPUT_DATA, codeInputData);
//            getActivity().startActivity(intent);

            Config.logout(getActivity());
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}