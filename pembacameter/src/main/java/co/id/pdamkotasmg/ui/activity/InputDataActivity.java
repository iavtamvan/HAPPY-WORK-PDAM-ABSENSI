package co.id.pdamkotasmg.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import co.id.pdamkotasmg.pembacameter.databinding.ActivityInputDataBinding;

public class InputDataActivity extends AppCompatActivity {

    private ActivityInputDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputDataBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);



    }
}