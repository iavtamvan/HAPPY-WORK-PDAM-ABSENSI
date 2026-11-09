package co.id.pdamkotasmg.pekerjaanteknik.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

import co.id.pdamkotasmg.pekerjaanteknik.R;

/**
 * Menampilkan satu foto layar penuh yang bisa diperbesar dengan dua jari atau
 * ketuk dua kali. Dipakai dari form Input SPK (foto yang baru diambil, dengan
 * tombol "Ganti Foto") dan dari Detail SPK (foto dari server, hanya dilihat).
 */
public final class PenampilFoto {

    private PenampilFoto() {
    }

    /**
     * @param sumber    {@link File} lokal atau String URL/path berkas.
     * @param judul     teks di bar atas, misal "Foto 1 · Foto Awal".
     * @param aksiGanti bila tidak null, tombol "Ganti Foto" ditampilkan dan aksi ini
     *                  dijalankan setelah penampil ditutup.
     */
    public static void tampilkan(Activity activity, @Nullable Object sumber, String judul,
                                 @Nullable Runnable aksiGanti) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        if (sumberKosong(sumber)) {
            Toast.makeText(activity, "Foto belum tersedia", Toast.LENGTH_SHORT).show();
            return;
        }

        final AppCompatDialog dialog = new AppCompatDialog(activity, R.style.PTLihatFoto);
        dialog.setContentView(R.layout.dialog_lihat_foto);

        PhotoView pvFoto = dialog.findViewById(R.id.pv_lihat_foto);
        final ProgressBar pbMuat = dialog.findViewById(R.id.pb_lihat_foto);
        final View divGagal = dialog.findViewById(R.id.div_lihat_foto_gagal);
        TextView tvJudul = dialog.findViewById(R.id.tv_lihat_foto_judul);
        View divTutup = dialog.findViewById(R.id.div_lihat_foto_tutup);
        Button btnGanti = dialog.findViewById(R.id.btn_lihat_foto_ganti);

        if (tvJudul != null) {
            tvJudul.setText(judul);
        }
        if (divTutup != null) {
            divTutup.setOnClickListener(v -> dialog.dismiss());
        }
        if (btnGanti != null) {
            if (aksiGanti != null) {
                btnGanti.setVisibility(View.VISIBLE);
                btnGanti.setOnClickListener(v -> {
                    dialog.dismiss();
                    aksiGanti.run();
                });
            } else {
                btnGanti.setVisibility(View.GONE);
            }
        }

        if (pvFoto != null) {
            pvFoto.setMaximumScale(5f);
            Glide.with(activity)
                    .load(sumber)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource) {
                            if (pbMuat != null) {
                                pbMuat.setVisibility(View.GONE);
                            }
                            if (divGagal != null) {
                                divGagal.setVisibility(View.VISIBLE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model,
                                                       Target<Drawable> target, DataSource dataSource,
                                                       boolean isFirstResource) {
                            if (pbMuat != null) {
                                pbMuat.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    })
                    .into(pvFoto);
        }

        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    /**
     * URL foto di Detail dibentuk dari BASE_URL_IMAGE_HANDLER + nama berkas, sehingga
     * slot tanpa foto menjadi ".../null". Kasus itu dianggap belum ada foto.
     */
    private static boolean sumberKosong(@Nullable Object sumber) {
        if (sumber == null) {
            return true;
        }
        if (sumber instanceof File) {
            return !((File) sumber).exists();
        }
        String teks = sumber.toString().trim();
        return teks.isEmpty()
                || teks.equalsIgnoreCase("null")
                || teks.endsWith("/null")
                || teks.endsWith("/");
    }
}
