package co.id.pdamkotasmg.ui.fragment.settings;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import co.id.pdamkotasmg.local.NetworkUtil;
import co.id.pdamkotasmg.local.SettingsManager;
import co.id.pdamkotasmg.local.sync.SyncNotificationHelper;
import co.id.pdamkotasmg.local.sync.SyncScheduler;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.pembacameter.databinding.FragmentSettingsBinding;

/**
 * Fragment untuk konten Settings.
 *
 * Fase 5 additions:
 *   - Dropdown frekuensi auto-sync
 *   - Switch "Hanya WiFi"
 *   - Toggle Auto Sync sekarang benar-benar schedule WorkManager
 *   - Runtime permission POST_NOTIFICATIONS untuk Android 13+
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsManager settings;

    // Permission launcher untuk POST_NOTIFICATIONS
    private ActivityResultLauncher<String> notifPermissionLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (!granted && binding != null) {
                        // User decline — toggle balik OFF, kasih tahu
                        binding.switchAutoSync.setChecked(false);
                        showPermissionDeniedDialog();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settings = SettingsManager.getInstance(requireContext());

        setupOfflineToggle();
        setupAutoSyncToggle();
        setupAutoSyncDetails();
        refreshConnectionStatus();
        refreshLastSync();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshConnectionStatus();
        refreshLastSync();
    }

    // ============== OFFLINE MODE ==============

    private void setupOfflineToggle() {
        boolean enabled = settings.isOfflineModeEnabled();
        binding.switchOfflineMode.setChecked(enabled);
        updateOfflineModeDescription(enabled);

        binding.switchOfflineMode.setOnCheckedChangeListener((btn, isChecked) -> {
            settings.setOfflineModeEnabled(isChecked);
            updateOfflineModeDescription(isChecked);
        });
    }

    private void updateOfflineModeDescription(boolean enabled) {
        if (binding == null) return;
        if (enabled) {
            binding.tvOfflineModeDesc.setText(
                    "Aktif — bacaan akan disimpan di HP saat ditekan tombol Simpan.\n" +
                            "Buka tab \"Data Pending\" untuk mengirim data ke server.");
        } else {
            binding.tvOfflineModeDesc.setText(
                    "Nonaktif — input bacaan langsung dikirim ke server " +
                            "(butuh koneksi internet saat menyimpan).");
        }
    }

    // ============== AUTO SYNC ==============

    private void setupAutoSyncToggle() {
        boolean enabled = settings.isAutoSyncEnabled();
        binding.switchAutoSync.setChecked(enabled);
        updateAutoSyncDetailsVisibility(enabled);

        binding.switchAutoSync.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                // Cek permission notif dulu (Android 13+)
                if (!hasNotifPermission()) {
                    requestNotifPermission();
                    return; // tunggu callback permission
                }
                applyAutoSyncEnabled(true);
            } else {
                applyAutoSyncEnabled(false);
            }
        });
    }

    private void applyAutoSyncEnabled(boolean enabled) {
        settings.setAutoSyncEnabled(enabled);
        updateAutoSyncDetailsVisibility(enabled);

        if (enabled) {
            int intervalMin = settings.getAutoSyncIntervalMinutes();
            boolean wifiOnly = settings.isAutoSyncWifiOnly();
            SyncScheduler.enableAutoSync(requireContext(), intervalMin, wifiOnly);
            SyncNotificationHelper.ensureChannel(requireContext());
        } else {
            SyncScheduler.disableAutoSync(requireContext());
        }
    }

    private void setupAutoSyncDetails() {
        // Spinner dropdown frekuensi
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                SettingsManager.INTERVAL_CHOICES_LABEL);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerInterval.setAdapter(adapter);
        binding.spinnerInterval.setSelection(settings.getAutoSyncIntervalChoiceIndex(), false);

        binding.spinnerInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                int newInterval = SettingsManager.INTERVAL_CHOICES_MINUTES[pos];
                int oldInterval = settings.getAutoSyncIntervalMinutes();
                if (newInterval != oldInterval) {
                    settings.setAutoSyncIntervalMinutes(newInterval);
                    // Re-schedule kalau auto-sync sedang aktif
                    if (settings.isAutoSyncEnabled()) {
                        SyncScheduler.enableAutoSync(
                                requireContext(), newInterval, settings.isAutoSyncWifiOnly());
                    }
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Switch WiFi only
        binding.switchWifiOnly.setChecked(settings.isAutoSyncWifiOnly());
        binding.switchWifiOnly.setOnCheckedChangeListener((btn, isChecked) -> {
            settings.setAutoSyncWifiOnly(isChecked);
            // Re-schedule kalau aktif
            if (settings.isAutoSyncEnabled()) {
                SyncScheduler.enableAutoSync(
                        requireContext(), settings.getAutoSyncIntervalMinutes(), isChecked);
            }
        });
    }

    private void updateAutoSyncDetailsVisibility(boolean autoSyncOn) {
        if (binding == null) return;
        binding.layoutAutoSyncDetails.setVisibility(autoSyncOn ? View.VISIBLE : View.GONE);
    }

    // ============== POST_NOTIFICATIONS PERMISSION ==============

    private boolean hasNotifPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true; // Android < 13 tidak butuh
        }
        return ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestNotifPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notifPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        } else {
            // Android < 13 langsung ON
            applyAutoSyncEnabled(true);
        }
    }

    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Izin Notifikasi Diperlukan")
                .setMessage("Auto-sync butuh izin notifikasi untuk memberi tahu Anda hasil sync di background. " +
                        "Anda bisa aktifkan lagi nanti dari Pengaturan aplikasi.")
                .setPositiveButton("OK", null)
                .show();
    }

    // ============== STATUS KONEKSI ==============

    private void refreshConnectionStatus() {
        if (binding == null) return;
        String label = NetworkUtil.getConnectionLabel(requireContext());
        boolean online = !"Offline".equals(label);
        binding.tvConnectionStatus.setText(online
                ? "Terhubung (" + label + ")"
                : "Tidak terhubung");
        binding.ivConnectionDot.setBackgroundResource(online
                ? R.drawable.bg_dot_green
                : R.drawable.bg_dot_red);
    }

    private void refreshLastSync() {
        if (binding == null) return;
        long ts = settings.getLastSyncTimestamp();
        if (ts <= 0) {
            binding.tvLastSync.setText("Belum pernah sync");
        } else {
            String formatted = new SimpleDateFormat("dd MMM yyyy, HH:mm",
                    Locale.getDefault()).format(new Date(ts));
            binding.tvLastSync.setText("Sync terakhir: " + formatted);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}