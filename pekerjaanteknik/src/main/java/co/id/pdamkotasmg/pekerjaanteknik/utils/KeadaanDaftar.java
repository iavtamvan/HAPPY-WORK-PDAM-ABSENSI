package co.id.pdamkotasmg.pekerjaanteknik.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import co.id.pdamkotasmg.pekerjaanteknik.R;

/**
 * Pengendali tampilan kosong layar daftar (layout include_keadaan_daftar).
 * <ul>
 *   <li><b>Kosong</b>: server menjawab tetapi belum ada data.</li>
 *   <li><b>Tidak ditemukan</b>: kata di kolom cari tidak cocok dengan data mana pun.</li>
 *   <li><b>Gagal dimuat</b>: koneksi putus atau server menolak, dengan tombol "Coba lagi".</li>
 * </ul>
 * Dengan {@link #pantau} kelas ini mengamati adapter lewat AdapterDataObserver, jadi
 * hasil filter pencarian ikut terpantau tanpa mengubah adapter mana pun.
 * Sebelumnya kelima layar daftar hanya menampilkan layar putih polos.
 */
public final class KeadaanDaftar {

    public static final String PESAN_GAGAL_KONEKSI = "Periksa koneksi internet, lalu coba lagi.";

    private static final int JENIS_BIASA = 0;
    private static final int JENIS_BERES = 1;
    private static final int JENIS_GAGAL = 2;

    private final View akar;
    private final View riak;
    private final View lingkar;
    private final ImageView ikon;
    private final TextView judul;
    private final TextView pesan;
    private final Button aksi;
    private final float dp;

    private ObjectAnimator animasiApung;
    private ObjectAnimator animasiRiak;

    private RecyclerView.Adapter<?> adapterDipantau;
    private RecyclerView.AdapterDataObserver pengamat;
    private EditText kolomCari;
    private int ikonKosong;
    private String judulKosong;
    private String pesanKosong;
    private boolean kosongBerartiBeres;

    public KeadaanDaftar(View akar) {
        this.akar = akar;
        this.riak = akar.findViewById(R.id.v_keadaan_riak);
        this.lingkar = akar.findViewById(R.id.div_keadaan_lingkar);
        this.ikon = akar.findViewById(R.id.iv_keadaan_ikon);
        this.judul = akar.findViewById(R.id.tv_keadaan_judul);
        this.pesan = akar.findViewById(R.id.tv_keadaan_pesan);
        this.aksi = akar.findViewById(R.id.btn_keadaan_aksi);
        this.dp = akar.getResources().getDisplayMetrics().density;
        akar.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                hentikanAnimasi();
            }
        });
    }

    /** Pesan untuk respons server yang bukan 2xx. */
    public static String pesanGagalServer(int kode) {
        return "Server belum bisa melayani permintaan (kode " + kode + "). Coba lagi beberapa saat lagi.";
    }

    /**
     * Mengamati adapter: bila isinya nol, tampilan kosong muncul; bila ada isi,
     * tampilan kosong hilang. Bila kolomCari berisi teks, pesannya "Tidak ditemukan".
     */
    public void pantau(RecyclerView.Adapter<?> adapter, @Nullable EditText cari,
                       int idIkon, String teksJudul, String teksPesan) {
        pantau(adapter, cari, idIkon, teksJudul, teksPesan, false);
    }

    /** Sama seperti di atas; bila beres = true, daftar kosong adalah kabar baik (warna hijau). */
    public void pantau(RecyclerView.Adapter<?> adapter, @Nullable EditText cari,
                       int idIkon, String teksJudul, String teksPesan, boolean beres) {
        lepasPantauan();
        adapterDipantau = adapter;
        kolomCari = cari;
        ikonKosong = idIkon;
        judulKosong = teksJudul;
        pesanKosong = teksPesan;
        kosongBerartiBeres = beres;
        if (adapter == null) {
            tampilkan(idIkon, teksJudul, teksPesan);
            return;
        }
        pengamat = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                perbarui();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                perbarui();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                perbarui();
            }
        };
        adapter.registerAdapterDataObserver(pengamat);
        perbarui();
    }

    private void lepasPantauan() {
        if (adapterDipantau != null && pengamat != null) {
            try {
                adapterDipantau.unregisterAdapterDataObserver(pengamat);
            } catch (IllegalStateException ignored) {
                // Sudah tidak terdaftar.
            }
        }
        adapterDipantau = null;
        pengamat = null;
    }

    private void perbarui() {
        if (adapterDipantau == null) {
            return;
        }
        if (adapterDipantau.getItemCount() > 0) {
            sembunyikan();
            return;
        }
        String kata = kolomCari == null ? "" : kolomCari.getText().toString().trim();
        if (!kata.isEmpty()) {
            atur(JENIS_BIASA, R.drawable.ic_pt_search, "Tidak ditemukan",
                    "Tidak ada hasil untuk “" + kata + "”. Periksa kembali yang diketik.", null, null);
        } else {
            atur(kosongBerartiBeres ? JENIS_BERES : JENIS_BIASA, ikonKosong, judulKosong, pesanKosong, null, null);
        }
    }

    /** Menampilkan keadaan kosong/petunjuk tanpa tombol. */
    public void tampilkan(int idIkon, String teksJudul, String teksPesan) {
        lepasPantauan();
        atur(JENIS_BIASA, idIkon, teksJudul, teksPesan, null, null);
    }

    /** Menampilkan keadaan gagal dimuat dengan tombol "Coba lagi". */
    public void tampilkanGagal(String teksPesan, Runnable cobaLagi) {
        lepasPantauan();
        atur(JENIS_GAGAL, R.drawable.ic_pt_alert, "Data belum bisa dimuat", teksPesan, "Coba lagi", cobaLagi);
    }

    /** Menyembunyikan tampilan kosong, misal saat data mulai dimuat ulang. */
    public void sembunyikan() {
        if (akar.getVisibility() != View.VISIBLE) {
            return;
        }
        hentikanAnimasi();
        akar.animate().cancel();
        akar.animate().alpha(0f).setDuration(160).withEndAction(() -> {
            akar.setVisibility(View.GONE);
            akar.setAlpha(1f);
        }).start();
    }

    /** Dipanggil sebelum memuat ulang: melepas pantauan lama dan menyembunyikan tampilan. */
    public void siapMemuat() {
        lepasPantauan();
        sembunyikan();
    }

    private void atur(int jenis, int idIkon, String teksJudul, String teksPesan,
                      @Nullable String teksAksi, @Nullable Runnable tindakan) {
        int warnaIkon;
        int latar;
        if (jenis == JENIS_GAGAL) {
            warnaIkon = R.color.pdam_warn_body;
            latar = R.drawable.bg_lingkar_gagal;
        } else if (jenis == JENIS_BERES) {
            warnaIkon = R.color.pdam_ok_body;
            latar = R.drawable.bg_lingkar_beres;
        } else {
            warnaIkon = R.color.pdam_blue_dark;
            latar = R.drawable.bg_icon_soft_blue;
        }
        ikon.setImageResource(idIkon);
        ikon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(akar.getContext(), warnaIkon)));
        lingkar.setBackgroundResource(latar);
        judul.setText(teksJudul);
        pesan.setText(teksPesan);
        if (teksAksi != null && tindakan != null) {
            aksi.setText(teksAksi);
            aksi.setVisibility(View.VISIBLE);
            aksi.setOnClickListener(v -> tindakan.run());
        } else {
            aksi.setOnClickListener(null);
            aksi.setVisibility(View.GONE);
        }

        // Muncul memudar sambil naik sedikit; bila sudah tampil, hanya isinya yang berganti.
        akar.animate().cancel();
        if (akar.getVisibility() != View.VISIBLE) {
            akar.setAlpha(0f);
            akar.setTranslationY(16 * dp);
            akar.setVisibility(View.VISIBLE);
        }
        akar.animate().alpha(1f).translationY(0f).setDuration(320)
                .setInterpolator(new DecelerateInterpolator(1.6f)).start();

        mulaiAnimasi(jenis != JENIS_GAGAL);
    }

    /** Ikon mengambang pelan; cincin riak hanya untuk keadaan kosong, bukan gagal. */
    private void mulaiAnimasi(boolean denganRiak) {
        riak.setVisibility(denganRiak ? View.VISIBLE : View.INVISIBLE);
        if (!animasiSistemAktif()) {
            return;
        }
        if (animasiApung == null) {
            animasiApung = ObjectAnimator.ofFloat(lingkar, View.TRANSLATION_Y, -5 * dp, 5 * dp);
            animasiApung.setDuration(1800);
            animasiApung.setRepeatMode(ValueAnimator.REVERSE);
            animasiApung.setRepeatCount(ValueAnimator.INFINITE);
            animasiApung.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        if (!animasiApung.isStarted()) {
            animasiApung.start();
        }
        if (denganRiak) {
            if (animasiRiak == null) {
                animasiRiak = ObjectAnimator.ofPropertyValuesHolder(riak,
                        PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f, 1.4f),
                        PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f, 1.4f),
                        PropertyValuesHolder.ofFloat(View.ALPHA, 0.9f, 0f));
                animasiRiak.setDuration(2200);
                animasiRiak.setRepeatCount(ValueAnimator.INFINITE);
                animasiRiak.setInterpolator(new DecelerateInterpolator());
            }
            if (!animasiRiak.isStarted()) {
                animasiRiak.start();
            }
        } else if (animasiRiak != null) {
            animasiRiak.cancel();
        }
    }

    private void hentikanAnimasi() {
        if (animasiApung != null) {
            animasiApung.cancel();
            lingkar.setTranslationY(0f);
        }
        if (animasiRiak != null) {
            animasiRiak.cancel();
        }
    }

    /** Animasi berulang tanpa henti tidak dijalankan bila animasi sistem dimatikan. */
    private boolean animasiSistemAktif() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ValueAnimator.areAnimatorsEnabled();
        }
        try {
            return Settings.Global.getFloat(akar.getContext().getContentResolver(),
                    Settings.Global.ANIMATOR_DURATION_SCALE, 1f) != 0f;
        } catch (Exception e) {
            return true;
        }
    }
}
