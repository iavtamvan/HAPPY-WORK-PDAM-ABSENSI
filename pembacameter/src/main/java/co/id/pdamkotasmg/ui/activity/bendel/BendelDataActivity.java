package co.id.pdamkotasmg.ui.activity.bendel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import co.id.pdamkotasmg.adapter.BendelAdapter;
import co.id.pdamkotasmg.local.repository.BendelRepository;
import co.id.pdamkotasmg.local.repository.DataResult;
import co.id.pdamkotasmg.model.bendel.DataItem;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityBendelDataBinding;

/**
 * BendelDataActivity (Fase 2 patched).
 *
 * List bendel SKIP toggle Mode Offline. Behavior:
 *   - ada koneksi → data segar dari API
 *   - no koneksi  → fallback cache + banner "Tidak ada koneksi"
 *
 * Toggle Mode Offline TIDAK pengaruhi screen ini. Toggle khusus untuk
 * input bacaan (lihat BendelPembacaKhususActivity).
 */
public class BendelDataActivity extends AppCompatActivity {
    private final String TAG = "debug";

    private ActivityBendelDataBinding binding;
    private String codeBendel;
    private String token;
    private String periode;
    private String cabang;
    private SharedPreferences sp;
    private SharedPreferences.Editor editorSp;

    private BendelAdapter bendelAdapter;
    private final List<DataItem> dataItems = new ArrayList<>();

    private BendelRepository bendelRepository;
    private BendelRepository.Cancellable currentRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBendelDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editorSp = sp.edit();
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        periode = sp.getString(Config.SHARED_PERIODE, "");
        cabang = sp.getString(Config.SHARED_CABANG, "");

        codeBendel = getIntent().getStringExtra(Config.BUNDLE_PEMBACA_METER_CODE_BENDEL);
        if (codeBendel == null) codeBendel = "";

        binding.tvBendel.setText("DAFTAR BACAAN METER CABANG " + cabang + " BENDEL " + codeBendel + " PERIODE " + periode);

        bendelAdapter = new BendelAdapter(this, dataItems);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(bendelAdapter);

        binding.swipeRefresh.setColorSchemeResources(
                com.pdamkotasmg.goodday.R.color.redPortal,
                com.pdamkotasmg.goodday.R.color.greenGojek
        );
        binding.swipeRefresh.setOnRefreshListener(this::getBendel);

        binding.btnCari.setOnClickListener(view -> getBendel());

        bendelRepository = new BendelRepository(this);
        getBendel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: refresh data bendel");
        getBendel();
    }

    private void getBendel() {
        if (currentRequest != null && !currentRequest.isCancelled()) {
            currentRequest.cancel();
        }

        showLoading(true);

        String nolanggInput = binding.edtNolangg.getText().toString().trim();
        currentRequest = bendelRepository.getBendel(token, codeBendel, nolanggInput, this::onBendelResult);
    }

    private void onBendelResult(DataResult result) {
        if (isActivityGone()) return;

        showLoading(false);

        if (!result.isSuccess()) {
            Toast.makeText(this,
                    result.getErrorMessage() != null ? result.getErrorMessage() : Config.ERROR_MSG,
                    Toast.LENGTH_SHORT).show();
            showEmptyState(dataItems.isEmpty());
            updateBanner(result);
            return;
        }

        List<DataItem> newData = result.getData();
        if (newData == null || newData.isEmpty()) {
            dataItems.clear();
            bendelAdapter.notifyDataSetChanged();
            binding.tvTotalDataBendel.setText("Data : 0 Pelanggan");
            showEmptyState(true);

            String msg = result.isFromCache()
                    ? "Tidak ada data di cache lokal"
                    : "Tidak dalam wilayah pembacaan Anda";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            dataItems.clear();
            dataItems.addAll(newData);
            bendelAdapter.notifyDataSetChanged();
            binding.tvTotalDataBendel.setText("Data : " + dataItems.size() + " Pelanggan");
            showEmptyState(false);
        }

        updateBanner(result);
    }

    /**
     * Banner kuning: hanya tampil saat data dari cache (= no koneksi fallback).
     */
    private void updateBanner(DataResult result) {
        if (binding == null) return;
        boolean showBanner = result.isFromCache();
        binding.bannerOffline.setVisibility(showBanner ? View.VISIBLE : View.GONE);

        if (showBanner) {
            binding.tvBannerOffline.setText(
                    "Tidak ada koneksi — data ditampilkan dari cache terakhir");
        }
    }

    private void showLoading(boolean show) {
        if (binding == null) return;
        binding.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.btnCari.setEnabled(!show);
        if (!show && binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        }
    }

    private void showEmptyState(boolean show) {
        if (binding == null) return;
        binding.tvEmptyState.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.rv.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private boolean isActivityGone() {
        return isFinishing() || isDestroyed() || binding == null;
    }

    @Override
    protected void onDestroy() {
        if (currentRequest != null && !currentRequest.isCancelled()) {
            currentRequest.cancel();
        }
        super.onDestroy();
    }
}