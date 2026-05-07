package co.id.pdamkotasmg.ui.activity.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.id.pdamkotasmg.local.db.AppDatabase;
import co.id.pdamkotasmg.pembacameter.databinding.ActivitySettingsOfflineBinding;
import co.id.pdamkotasmg.ui.fragment.settings.PendingDataFragment;
import co.id.pdamkotasmg.ui.fragment.settings.SettingsFragment;

/**
 * SettingsActivity refactored ke struktur Tab (Fase 4).
 *
 * 2 Tab:
 *   - "Pengaturan"   → SettingsFragment (toggle offline, koneksi, dll)
 *   - "Data Pending" → PendingDataFragment (list pending + tombol Sync)
 *
 * Title tab "Data Pending" otomatis update jadi "Data Pending (X)" saat ada
 * pending bacaan unsynced.
 */
public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsOfflineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsOfflineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHeader();
        setupTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPendingTabBadge();
    }

    private void setupHeader() {
        binding.ivHeaderBackArrow.setOnClickListener(v -> finish());
        binding.tvHeaderJudul.setText("Pengaturan & Data Pending");
    }

    private void setupTabs() {
        binding.viewPager.setAdapter(new SettingsTabAdapter(this));

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Pengaturan");
                    } else {
                        tab.setText("Data Pending");
                    }
                }).attach();
    }

    /**
     * Update badge jumlah pending di tab title — dipanggil dari onResume()
     * dan dari fragment saat sync selesai.
     */
    public void refreshPendingTabBadge() {
        AppDatabase.databaseExecutor.execute(() -> {
            int count = AppDatabase.getInstance(this).pendingBacaanDao().countUnsynced();
            runOnUiThread(() -> {
                if (binding == null || binding.tabLayout.getTabAt(1) == null) return;
                String title = count > 0 ? "Data Pending (" + count + ")" : "Data Pending";
                binding.tabLayout.getTabAt(1).setText(title);
            });
        });
    }

    /**
     * Adapter untuk ViewPager2 yang host kedua fragment.
     */
    private static class SettingsTabAdapter extends FragmentStateAdapter {

        public SettingsTabAdapter(@NonNull FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new SettingsFragment();
            } else {
                return new PendingDataFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}