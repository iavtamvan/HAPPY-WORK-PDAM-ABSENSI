package co.id.pdamkotasmg.ui.fragment.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import co.id.pdamkotasmg.local.NetworkUtil;
import co.id.pdamkotasmg.local.db.AppDatabase;
import co.id.pdamkotasmg.local.db.entity.PendingBacaanEntity;
import co.id.pdamkotasmg.local.db.entity.PendingFotoEntity;
import co.id.pdamkotasmg.local.sync.SyncManager;
import co.id.pdamkotasmg.local.sync.SyncProgressEvent;
import co.id.pdamkotasmg.pembacameter.databinding.FragmentPendingDataBinding;
import co.id.pdamkotasmg.ui.activity.settings.SettingsActivity;
import co.id.pdamkotasmg.adapter.PendingDataAdapter;

/**
 * Tab "Data Pending" — list pending bacaan + tombol Sync.
 *
 * Behavior:
 *   - Saat masuk fragment: load list dari Room
 *   - Tombol "Sync Semua" → SyncManager.startSyncAll()
 *   - Per row: tampil status badge, tombol Retry (kalau FAILED)
 *   - Saat sync: progress bar overlay + tombol Cancel
 *   - Saat selesai: dialog summary "X sukses, Y gagal", refresh list
 */
public class PendingDataFragment extends Fragment implements PendingDataAdapter.Listener {

    private static final String TAG = "PendingDataFragment";

    private FragmentPendingDataBinding binding;
    private PendingDataAdapter adapter;
    private final List<PendingBacaanEntity> items = new ArrayList<>();
    private SyncManager syncManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPendingDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        syncManager = SyncManager.getInstance(requireContext());

        adapter = new PendingDataAdapter(requireContext(), items, this);
        binding.rvPending.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPending.setAdapter(adapter);

        binding.btnSyncAll.setOnClickListener(v -> startSyncAll());
        binding.btnCancelSync.setOnClickListener(v -> {
            syncManager.requestCancel();
            binding.btnCancelSync.setEnabled(false);
            binding.tvProgressStatus.setText("Membatalkan...");
        });

        binding.swipeRefresh.setOnRefreshListener(this::loadPending);
        binding.swipeRefresh.setColorSchemeResources(
                com.pdamkotasmg.goodday.R.color.redPortal,
                com.pdamkotasmg.goodday.R.color.greenGojek);

        loadPending();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPending();
    }

    private void loadPending() {
        AppDatabase.databaseExecutor.execute(() -> {
            try {
                List<PendingBacaanEntity> all = AppDatabase.getInstance(requireContext())
                        .pendingBacaanDao().getAllUnsynced();
                if (binding == null) return;
                requireActivity().runOnUiThread(() -> {
                    if (binding == null) return;
                    items.clear();
                    items.addAll(all);
                    adapter.notifyDataSetChanged();
                    updateEmptyState();
                    updateSyncButtonEnabled();
                    if (binding.swipeRefresh.isRefreshing()) {
                        binding.swipeRefresh.setRefreshing(false);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "loadPending error", e);
            }
        });
    }

    private void updateEmptyState() {
        if (binding == null) return;
        boolean isEmpty = items.isEmpty();
        binding.layoutEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        binding.rvPending.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        binding.tvCount.setText(isEmpty ? "0 pending" : items.size() + " pending");
    }

    private void updateSyncButtonEnabled() {
        if (binding == null) return;
        boolean hasItems = !items.isEmpty();
        boolean syncing = syncManager.isSyncing();
        binding.btnSyncAll.setEnabled(hasItems && !syncing);
    }

    // ============== SYNC ==============

    private void startSyncAll() {
        if (items.isEmpty()) {
            Toast.makeText(requireContext(), "Tidak ada data pending", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!NetworkUtil.isOnline(requireContext())) {
            Toast.makeText(requireContext(),
                    "Tidak ada koneksi internet. Sync membutuhkan koneksi.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences sp = requireContext().getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");

        if (token == null || token.isEmpty()) {
            Toast.makeText(requireContext(), "Token tidak tersedia", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressOverlay(true);
        syncManager.startSyncAll(token, this::onSyncProgress);
    }

    private void onSyncRetry(long pendingId) {
        if (!NetworkUtil.isOnline(requireContext())) {
            Toast.makeText(requireContext(),
                    "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sp = requireContext().getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");

        showProgressOverlay(true);
        syncManager.startSyncOne(token, pendingId, this::onSyncProgress);
    }

    private void onSyncProgress(SyncProgressEvent event) {
        if (binding == null) return;

        switch (event.phase) {
            case STARTED:
                binding.progressBar.setMax(event.total);
                binding.progressBar.setProgress(0);
                binding.tvProgressStatus.setText("Memulai sync " + event.total + " data...");
                binding.tvProgressCounter.setText("0 / " + event.total);
                break;

            case UPLOADING_PHOTO:
                binding.progressBar.setProgress(event.current - 1);
                binding.tvProgressStatus.setText("Mengupload foto " + event.currentNolangg + "...");
                binding.tvProgressCounter.setText(event.current + " / " + event.total);
                break;

            case POSTING_DATA:
                binding.tvProgressStatus.setText("Mengirim bacaan " + event.currentNolangg + "...");
                break;

            case ITEM_SUCCESS:
                binding.progressBar.setProgress(event.current);
                binding.tvProgressStatus.setText("✓ " + event.currentNolangg + " sukses");
                break;

            case ITEM_FAILED:
                binding.progressBar.setProgress(event.current);
                binding.tvProgressStatus.setText("✗ " + (event.currentNolangg != null ? event.currentNolangg + " gagal" : "Gagal"));
                break;

            case ALL_DONE:
                showProgressOverlay(false);
                showSummaryDialog(event.successCount, event.failedCount);
                loadPending();
                // Refresh badge tab
                if (getActivity() instanceof SettingsActivity) {
                    ((SettingsActivity) getActivity()).refreshPendingTabBadge();
                }
                break;
        }
    }

    private void showProgressOverlay(boolean show) {
        if (binding == null) return;
        binding.layoutProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.btnSyncAll.setEnabled(!show);
        binding.btnCancelSync.setEnabled(show);
    }

    private void showSummaryDialog(int success, int failed) {
        if (binding == null || !isAdded()) return;
        int total = success + failed;
        String title;
        String message;

        if (total == 0) {
            title = "Tidak Ada Data";
            message = "Tidak ada data pending untuk di-sync.";
        } else if (failed == 0) {
            title = "Sync Selesai ✓";
            message = "Berhasil mengirim " + success + " data ke server.";
        } else if (success == 0) {
            title = "Sync Gagal";
            message = "Gagal mengirim semua " + failed + " data. Periksa koneksi & coba lagi.";
        } else {
            title = "Sync Selesai (Sebagian)";
            message = success + " data berhasil dikirim, " + failed + " gagal.\n\n" +
                    "Buka detail data yang gagal untuk melihat error.";
        }

        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setCancelable(true)
                .show();
    }

    // ============== ADAPTER LISTENER ==============

    @Override
    public void onRetryClick(PendingBacaanEntity entity) {
        if (syncManager.isSyncing()) {
            Toast.makeText(requireContext(),
                    "Sync sedang berjalan, tunggu selesai", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(requireContext())
                .setTitle("Sync ulang?")
                .setMessage("Kirim ulang bacaan untuk pelanggan " + entity.nolangg + "?")
                .setPositiveButton("Ya, Sync", (d, w) -> onSyncRetry(entity.id))
                .setNegativeButton("Batal", null)
                .show();
    }

    @Override
    public void onDeleteClick(PendingBacaanEntity entity) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Hapus pending?")
                .setMessage("Bacaan untuk " + entity.nolangg + " akan dihapus dari HP.\n\n" +
                        "Data ini tidak akan terkirim ke server. Lanjutkan?")
                .setPositiveButton("Hapus", (d, w) -> deletePending(entity))
                .setNegativeButton("Batal", null)
                .show();
    }

    private void deletePending(PendingBacaanEntity entity) {
        AppDatabase.databaseExecutor.execute(() -> {
            try {
                AppDatabase appDb = AppDatabase.getInstance(requireContext());
                // Hapus foto local
                List<PendingFotoEntity> fotos = appDb.pendingFotoDao().getByPendingBacaan(entity.id);
                for (PendingFotoEntity f : fotos) {
                    new java.io.File(f.localFilePath).delete();
                }
                // Hapus pending bacaan (cascade hapus foto entity juga)
                appDb.pendingBacaanDao().deleteById(entity.id);

                if (binding == null) return;
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Pending dihapus", Toast.LENGTH_SHORT).show();
                    loadPending();
                    if (getActivity() instanceof SettingsActivity) {
                        ((SettingsActivity) getActivity()).refreshPendingTabBadge();
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "deletePending error", e);
            }
        });
    }

    @Override
    public void onItemClick(PendingBacaanEntity entity) {
        // Tampilkan detail singkat pakai dialog
        StringBuilder sb = new StringBuilder();
        sb.append("Nolangg: ").append(entity.nolangg).append("\n");
        sb.append("Bendel: ").append(entity.bendel != null ? entity.bendel : "-").append("\n");
        sb.append("Periode: ").append(entity.periode != null ? entity.periode : "-").append("\n");
        sb.append("Bacaan kini: ").append(entity.kini != null ? entity.kini : "-").append("\n");
        sb.append("Status meter: ").append(entity.kodeStatusMeter != null ? entity.kodeStatusMeter : "-").append("\n");
        sb.append("Keterangan: ").append(entity.keterangan != null ? entity.keterangan : "-").append("\n");
        if (entity.lastError != null) {
            sb.append("\n").append("Error: ").append(entity.lastError);
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Detail Pending #" + entity.id)
                .setMessage(sb.toString())
                .setPositiveButton("OK", null)
                .setCancelable(true)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}