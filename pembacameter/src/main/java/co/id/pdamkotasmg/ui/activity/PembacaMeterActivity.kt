package co.id.pdamkotasmg.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.pdamkotasmg.api.ApiConfig
import co.id.pdamkotasmg.model.pelanggan.DataItem
import co.id.pdamkotasmg.model.pelanggan.PelangganByNolanggRootModel
import co.id.pdamkotasmg.pembacameter.R
import co.id.pdamkotasmg.pembacameter.databinding.ActivityPembacaMeterBinding
import retrofit2.Callback

private lateinit var binding: ActivityPembacaMeterBinding;

class PembacaMeterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembacaMeterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}