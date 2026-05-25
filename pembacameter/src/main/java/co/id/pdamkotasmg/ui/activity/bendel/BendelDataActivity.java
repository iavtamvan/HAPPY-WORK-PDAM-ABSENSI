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
import co.id.pdamkotasmg.local.NetworkUtil;
import co.id.pdamkotasmg.local.repository.BendelRepository;
import co.id.pdamkotasmg.local.repository.DataResult;
import co.id.pdamkotasmg.model.bendel.DataItem;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityBendelDataBinding;

/**
 * BendelDataActivity (Fase 6 — Cache-first dengan manual sync).
 *
 * Behavior:
 *   - Pertama kali buka bendel (cache kosong) → auto fetch API
 *   - Buka lagi (cache ada) → tampil dari cache, no API hit
 *   - Banner "Disinkron X jam/hari lalu" tampil di header
 *   - User tap FAB Sync → force refresh dari API + cleanup empty bendels
 *   - Pull-to-refresh = sama dengan FAB Sync (force refresh)
 *
 * Toggle Mode Offline TIDAK pengaruhi screen ini.
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

        bendelAdapter = new BendelAdapter(this, dataItems, codeBendel);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(bendelAdapter);

        binding.swipeRefresh.setColorSchemeResources(
                com.pdamkotasmg.goodday.R.color.redPortal,
                com.pdamkotasmg.goodday.R.color.greenGojek
        );
        binding.swipeRefresh.setOnRefreshListener(() -> getBendel(true));

        binding.btnCari.setOnClickListener(view -> getBendel(false));

        binding.fabSync.setOnClickListener(view -> {
            if (!NetworkUtil.isOnline(BendelDataActivity.this)) {
                Toast.makeText(BendelDataActivity.this,
                        "Tidak ada koneksi internet untuk sync",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(BendelDataActivity.this,
                    "Mengambil data terbaru dari server...",
                    Toast.LENGTH_SHORT).show();
            getBendel(true);
        });

        // Banner Sync Sekarang inline button
        binding.btnBannerSync.setOnClickListener(view -> {
            if (!NetworkUtil.isOnline(BendelDataActivity.this)) {
                Toast.makeText(BendelDataActivity.this,
                        "Tidak ada koneksi internet untuk sync",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            getBendel(true);
        });

        bendelRepository = new BendelRepository(this);
        getBendel(false); // pertama kali: cache-first
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: refresh data bendel from cache");
        getBendel(false); // refresh tampilan dari cache (yang sudah ter-update via markAsRead)
    }

    /**
     * @param forceRefresh true = paksa hit API (FAB / pull-to-refresh / banner button)
     */
    private void getBendel(boolean forceRefresh) {
        if (currentRequest != null && !currentRequest.isCancelled()) {
            currentRequest.cancel();
        }

        showLoading(true);

        String nolanggInput = binding.edtNolangg.getText().toString().trim();
        currentRequest = bendelRepository.getBendel(token, codeBendel, nolanggInput,
                forceRefresh, this::onBendelResult);
    }

    private void onBendelResult(DataResult result) {
        if (isActivityGone()) return;

        showLoading(false);

        if (!result.isSuccess()) {
            Toast.makeText(this,
                    result.getErrorMessage() != null ? result.getErrorMessage() : Config.ERROR_MSG,
                    Toast.LENGTH_LONG).show();
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
                    ? "Tidak ada data tersisa di cache lokal"
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
     * Update banner di top sesuai sumber data:
     *   - dari NETWORK (baru di-sync) → no banner
     *   - dari CACHE → banner kuning "Disinkron X jam/hari lalu - [Sync sekarang]"
     */
    private void updateBanner(DataResult result) {
        if (binding == null) return;

        if (result.isFromCache() && result.lastSyncAt > 0) {
            String relTime = formatRelativeTime(result.lastSyncAt);
            binding.bannerOffline.setVisibility(View.VISIBLE);
            binding.tvBannerOffline.setText("Disinkron " + relTime + " — tap Sync untuk refresh");
            binding.btnBannerSync.setVisibility(View.VISIBLE);
        } else if (result.isFromCache()) {
            // Cache lama tanpa timestamp (migration v2→v3)
            binding.bannerOffline.setVisibility(View.VISIBLE);
            binding.tvBannerOffline.setText("Data dari cache lokal — tap Sync untuk refresh");
            binding.btnBannerSync.setVisibility(View.VISIBLE);
        } else {
            binding.bannerOffline.setVisibility(View.GONE);
        }
    }

    /**
     * Format timestamp jadi "X menit/jam/hari lalu".
     */
    private static String formatRelativeTime(long timestamp) {
        if (timestamp <= 0) return "tidak diketahui";

        long now = System.currentTimeMillis();
        long diffMs = now - timestamp;

        if (diffMs < 0) return "baru saja";

        long minutes = diffMs / (60 * 1000);
        if (minutes < 1) return "baru saja";
        if (minutes < 60) return minutes + " menit lalu";

        long hours = minutes / 60;
        if (hours < 24) return hours + " jam lalu";

        long days = hours / 24;
        if (days < 7) return days + " hari lalu";

        long weeks = days / 7;
        if (weeks < 4) return weeks + " minggu lalu";

        long months = days / 30;
        return months + " bulan lalu";
    }

    private void showLoading(boolean show) {
        if (binding == null) return;
        binding.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.btnCari.setEnabled(!show);
        binding.fabSync.setEnabled(!show);
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