package co.id.pdamkotasmg.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import co.id.pdamkotasmg.pembacameter.databinding.FragmentInputDataBinding;
import co.id.pdamkotasmg.ui.activity.InputDataActivity;

public class InputDataFragment extends Fragment {

    private FragmentInputDataBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInputDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.divInBendel.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getActivity(), InputDataActivity.class));
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}