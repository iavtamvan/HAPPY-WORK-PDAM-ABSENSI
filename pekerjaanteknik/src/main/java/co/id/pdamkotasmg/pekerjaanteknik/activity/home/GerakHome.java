package co.id.pdamkotasmg.pekerjaanteknik.activity.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import co.id.pdamkotasmg.pekerjaanteknik.view.AnimasiPipaView;

/**
 * Seluruh gerak di Home SPK yang tidak menyentuh data maupun alur bisnis:
 * <ul>
 *   <li>kartu utama parallax saat digulir, sapaan memudar, adegan pipa berhenti
 *       sendiri begitu kartu tertutup;</li>
 *   <li>bar atas berubah dari transparan menjadi putih, judul muncul, dan garis
 *       "aliran" di bawahnya terisi mengikuti posisi gulir;</li>
 *   <li>bagian halaman "naik" perlahan saat masuk layar;</li>
 *   <li>ubin menu masuk berurutan, efek tekan membal, angka statistik menghitung
 *       naik, kerangka pemuatan berdenyut, ikon segarkan berputar.</li>
 * </ul>
 * Semuanya memakai ViewPropertyAnimator/ObjectAnimator bawaan Android dan ikut
 * pengaturan "hapus animasi" di sistem.
 */
final class GerakHome {

    private final NestedScrollView gulir;
    private final View isi;
    private final View hero;
    private final View sapaan;
    private final AnimasiPipaView adegan;
    private final View barLatar;
    private final View garisAliran;
    private final View judulBar;
    private final ImageView ikonKembali;
    private final View kembaliKaca;
    private final View kembaliPadat;
    private final float tinggiBar;
    private final float dp;

    private final int warnaIkonTerang;
    private final int warnaIkonGelap;
    private final ArgbEvaluator campurWarna = new ArgbEvaluator();

    private final List<View> menungguMuncul = new ArrayList<>();
    private final Map<View, Animator> animasiBerulang = new HashMap<>();
    private final Map<TextView, ValueAnimator> hitungan = new HashMap<>();
    private ObjectAnimator putaran;

    private boolean layarAktif = true;
    private boolean heroTerlihat = true;
    private float tingkatBarTerakhir = -1f;

    private final ViewTreeObserver.OnGlobalLayoutListener saatTataUlang = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            terapkanGulir(gulir.getScrollY());
            periksaMuncul();
        }
    };

    GerakHome(NestedScrollView gulir, View isi, View hero, View sapaan, AnimasiPipaView adegan,
              View barLatar, View garisAliran, View judulBar,
              ImageView ikonKembali, View kembaliKaca, View kembaliPadat,
              int warnaIkonTerang, int warnaIkonGelap) {
        this.gulir = gulir;
        this.isi = isi;
        this.hero = hero;
        this.sapaan = sapaan;
        this.adegan = adegan;
        this.barLatar = barLatar;
        this.garisAliran = garisAliran;
        this.judulBar = judulBar;
        this.ikonKembali = ikonKembali;
        this.kembaliKaca = kembaliKaca;
        this.kembaliPadat = kembaliPadat;
        this.warnaIkonTerang = warnaIkonTerang;
        this.warnaIkonGelap = warnaIkonGelap;
        this.dp = gulir.getResources().getDisplayMetrics().density;
        this.tinggiBar = 60 * dp;
    }

    /** Memasang pendengar gulir dan tata letak, lalu menyetel keadaan awal. */
    void pasang() {
        if (garisAliran != null) {
            garisAliran.setPivotX(0f);
        }
        gulir.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    terapkanGulir(scrollY);
                    if (adegan != null) {
                        adegan.dorong(scrollY - oldScrollY);
                    }
                    periksaMuncul();
                });
        isi.getViewTreeObserver().addOnGlobalLayoutListener(saatTataUlang);
        terapkanGulir(0);
    }

    /** Dipanggil dari onResume/onPause Activity. */
    void setLayarAktif(boolean aktif) {
        layarAktif = aktif;
        perbaruiAdegan();
        for (Animator a : animasiBerulang.values()) {
            if (aktif && a.isPaused()) {
                a.resume();
            } else if (!aktif && a.isRunning()) {
                a.pause();
            }
        }
    }

    /** Dipanggil dari onDestroy: melepas pendengar dan menghentikan semua animasi. */
    void lepas() {
        isi.getViewTreeObserver().removeOnGlobalLayoutListener(saatTataUlang);
        gulir.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) null);
        for (Animator a : animasiBerulang.values()) {
            a.cancel();
        }
        animasiBerulang.clear();
        for (ValueAnimator a : hitungan.values()) {
            a.cancel();
        }
        hitungan.clear();
        if (putaran != null) {
            putaran.cancel();
            putaran = null;
        }
        if (adegan != null) {
            adegan.setAktif(false);
        }
    }

    // ---------------------------------------------------------------------
    // Gulir: parallax, bar atas, garis aliran
    // ---------------------------------------------------------------------

    private void terapkanGulir(int y) {
        float tinggiHero = hero.getHeight();
        if (tinggiHero <= 0f) {
            return;
        }
        float rentang = Math.max(1f, tinggiHero - tinggiBar);
        float p = jepit(y / rentang);

        // Kartu utama bergerak lebih lambat dari isi halaman (parallax).
        hero.setTranslationY(y * 0.42f);
        if (sapaan != null) {
            sapaan.setTranslationY(-y * 0.18f);
            sapaan.setAlpha(1f - jepit(p * 1.6f));
        }
        if (adegan != null) {
            adegan.setAlpha(1f - 0.85f * jepit(p * 1.15f));
            float skala = 1f + 0.06f * p;
            adegan.setScaleX(skala);
            adegan.setScaleY(skala);
        }
        boolean terlihat = p < 1f;
        if (terlihat != heroTerlihat) {
            heroTerlihat = terlihat;
            perbaruiAdegan();
        }

        // Bar atas mulai padat saat lembar konten mendekati bar.
        float pBar = jepit((p - 0.55f) / 0.35f);
        if (Math.abs(pBar - tingkatBarTerakhir) > 0.001f) {
            tingkatBarTerakhir = pBar;
            barLatar.setAlpha(pBar);
            // Bar yang sudah padat menahan sentuhan, supaya ubin menu yang tergulir
            // di bawahnya tidak ikut tertekan. Saat transparan, sentuhan diteruskan.
            barLatar.setClickable(pBar > 0.5f);
            judulBar.setAlpha(pBar);
            judulBar.setTranslationY((1f - pBar) * 6 * dp);
            kembaliKaca.setAlpha(1f - pBar);
            kembaliPadat.setAlpha(pBar);
            int warna = (Integer) campurWarna.evaluate(pBar, warnaIkonTerang, warnaIkonGelap);
            ikonKembali.setColorFilter(warna, PorterDuff.Mode.SRC_IN);
        }

        // Garis aliran di bawah bar: terisi sesuai seberapa jauh halaman digulir.
        if (garisAliran != null) {
            float maksGulir = Math.max(1f, isi.getHeight() - gulir.getHeight());
            garisAliran.setAlpha(pBar);
            garisAliran.setScaleX(jepit(y / maksGulir));
        }
    }

    private void perbaruiAdegan() {
        if (adegan != null) {
            adegan.setAktif(layarAktif && heroTerlihat);
        }
    }

    /** Menggulir halus ke atas, misal saat judul bar diketuk. */
    void gulirKeAtas() {
        gulir.smoothScrollTo(0, 0);
    }

    // ---------------------------------------------------------------------
    // Pembuka dan kemunculan saat digulir
    // ---------------------------------------------------------------------

    /** Tulisan kartu utama muncul berurutan, lembar konten naik dari bawah. */
    void animasiPembuka(View lembar, View... tulisanHero) {
        int urutan = 0;
        for (View v : tulisanHero) {
            if (v == null) {
                continue;
            }
            v.setAlpha(0f);
            v.setTranslationY(14 * dp);
            v.animate().alpha(1f).translationY(0f)
                    .setStartDelay(180L + urutan * 80L).setDuration(520)
                    .setInterpolator(new DecelerateInterpolator(1.6f)).start();
            urutan++;
        }
        if (lembar != null) {
            lembar.setAlpha(0f);
            lembar.setTranslationY(56 * dp);
            lembar.animate().alpha(1f).translationY(0f)
                    .setStartDelay(260).setDuration(640)
                    .setInterpolator(new DecelerateInterpolator(2f)).start();
        }
    }

    /**
     * Mendaftarkan bagian halaman yang akan "naik" saat masuk layar. Bagian yang
     * masih tersembunyi (GONE) menunggu sampai ditampilkan.
     */
    void daftarkanMuncul(View... bagian) {
        for (View v : bagian) {
            if (v == null) {
                continue;
            }
            v.setAlpha(0f);
            v.setTranslationY(26 * dp);
            v.setScaleX(0.97f);
            v.setScaleY(0.97f);
            menungguMuncul.add(v);
        }
    }

    private void periksaMuncul() {
        if (menungguMuncul.isEmpty() || gulir.getHeight() == 0) {
            return;
        }
        float batas = gulir.getScrollY() + gulir.getHeight() * 0.92f;
        int urutan = 0;
        Iterator<View> it = menungguMuncul.iterator();
        while (it.hasNext()) {
            View v = it.next();
            if (!v.isShown()) {
                continue;
            }
            if (posisiAtas(v) < batas) {
                it.remove();
                v.animate().alpha(1f).translationY(0f).scaleX(1f).scaleY(1f)
                        .setStartDelay(urutan * 70L).setDuration(520)
                        .setInterpolator(new DecelerateInterpolator(1.8f))
                        .withLayer().start();
                urutan++;
            }
        }
    }

    /** Posisi atas view terhadap isi gulir (tanpa translasi animasi). */
    private float posisiAtas(View v) {
        float atas = 0f;
        View kini = v;
        while (kini != null && kini != isi) {
            atas += kini.getTop();
            ViewParent induk = kini.getParent();
            kini = induk instanceof View ? (View) induk : null;
        }
        return atas;
    }

    // ---------------------------------------------------------------------
    // Menu
    // ---------------------------------------------------------------------

    /** Ubin menu yang berisi masuk berurutan dengan pantulan kecil. */
    void masukkanMenu(View... kartu) {
        int urutan = 0;
        for (View k : kartu) {
            if (k == null || !adaIsiTerlihat(k)) {
                continue;
            }
            k.setAlpha(0f);
            k.setTranslationY(22 * dp);
            k.setScaleX(0.9f);
            k.setScaleY(0.9f);
            k.animate().alpha(1f).translationY(0f).scaleX(1f).scaleY(1f)
                    .setStartDelay(20L + urutan * 55L).setDuration(460)
                    .setInterpolator(new OvershootInterpolator(1.1f))
                    .withLayer().start();
            urutan++;
        }
    }

    private boolean adaIsiTerlihat(View kartu) {
        if (!(kartu instanceof ViewGroup)) {
            return kartu.getVisibility() == View.VISIBLE;
        }
        ViewGroup g = (ViewGroup) kartu;
        for (int i = 0; i < g.getChildCount(); i++) {
            if (g.getChildAt(i).getVisibility() == View.VISIBLE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Efek tekan: sasaran mengecil sedikit saat disentuh lalu membal kembali.
     * Mengembalikan false supaya klik tetap diproses seperti biasa.
     */
    @SuppressLint("ClickableViewAccessibility")
    void pasangEfekTekan(View pemicu, final View sasaran) {
        if (pemicu == null || sasaran == null) {
            return;
        }
        pemicu.setOnTouchListener((v, e) -> {
            switch (e.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    sasaran.animate().scaleX(0.94f).scaleY(0.94f).setStartDelay(0)
                            .setDuration(110).setInterpolator(new DecelerateInterpolator()).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    sasaran.animate().scaleX(1f).scaleY(1f).setStartDelay(0)
                            .setDuration(280).setInterpolator(new OvershootInterpolator(2.2f)).start();
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    // ---------------------------------------------------------------------
    // Data: hitung naik, kerangka berdenyut, ikon berputar
    // ---------------------------------------------------------------------

    /**
     * Angka menghitung naik dari 0 ke nilaiAkhir. Teks akhir selalu persis
     * nilaiAkhir dari server; bila bukan bilangan bulat, langsung ditampilkan.
     */
    void hitungNaik(final TextView tv, final String nilaiAkhir) {
        if (tv == null) {
            return;
        }
        ValueAnimator lama = hitungan.remove(tv);
        if (lama != null) {
            lama.cancel();
        }
        int target;
        try {
            target = Integer.parseInt(nilaiAkhir == null ? "" : nilaiAkhir.trim());
        } catch (NumberFormatException e) {
            tv.setText(nilaiAkhir);
            return;
        }
        if (target <= 0 || target > 100000) {
            tv.setText(nilaiAkhir);
            return;
        }
        final ValueAnimator a = ValueAnimator.ofInt(0, target);
        a.setDuration(Math.min(1400L, 700L + target * 12L));
        a.setInterpolator(new DecelerateInterpolator(1.6f));
        a.addUpdateListener(an -> tv.setText(String.valueOf((int) an.getAnimatedValue())));
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tv.setText(nilaiAkhir);
                if (hitungan.get(tv) == a) {
                    hitungan.remove(tv);
                }
            }
        });
        hitungan.put(tv, a);
        a.start();
    }

    /** Kerangka pemuatan berdenyut pelan sampai data datang. */
    void mulaiDenyut(View v) {
        if (v == null || animasiBerulang.containsKey(v)) {
            return;
        }
        // Batalkan animasi sembunyi yang mungkin masih berjalan (misal "Coba lagi"
        // diketuk cepat), supaya view tidak ikut di-GONE setelah denyut dimulai.
        v.animate().cancel();
        ObjectAnimator a = ObjectAnimator.ofFloat(v, View.ALPHA, 1f, 0.45f);
        a.setDuration(750);
        a.setRepeatMode(ValueAnimator.REVERSE);
        a.setRepeatCount(ValueAnimator.INFINITE);
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        animasiBerulang.put(v, a);
        a.start();
    }

    /** Menghentikan denyut; bila sembunyikan, view memudar lalu GONE. */
    void hentikanDenyut(final View v, boolean sembunyikan) {
        if (v == null) {
            return;
        }
        Animator a = animasiBerulang.remove(v);
        if (a != null) {
            a.cancel();
        }
        if (sembunyikan && v.getVisibility() == View.VISIBLE) {
            v.animate().alpha(0f).setStartDelay(0).setDuration(220).withEndAction(() -> {
                v.setVisibility(View.GONE);
                v.setAlpha(1f);
            }).start();
        } else {
            v.animate().alpha(1f).setStartDelay(0).setDuration(180).start();
        }
    }

    /** Ikon segarkan berputar selama data diambil. */
    void mulaiPutar(View ikon) {
        if (ikon == null || (putaran != null && putaran.isRunning())) {
            return;
        }
        ikon.animate().cancel();
        float awal = ikon.getRotation() % 360f;
        putaran = ObjectAnimator.ofFloat(ikon, View.ROTATION, awal, awal + 360f);
        putaran.setDuration(800);
        putaran.setRepeatCount(ValueAnimator.INFINITE);
        putaran.setInterpolator(new LinearInterpolator());
        putaran.start();
    }

    /** Putaran diselesaikan halus ke posisi tegak. */
    void hentikanPutar(View ikon) {
        if (putaran != null) {
            putaran.cancel();
            putaran = null;
        }
        if (ikon != null) {
            float r = ikon.getRotation();
            float target = (float) (Math.ceil(r / 360f) * 360f);
            ikon.animate().rotation(target).setStartDelay(0).setDuration(320)
                    .setInterpolator(new DecelerateInterpolator()).start();
        }
    }

    private static float jepit(float v) {
        return v < 0f ? 0f : (v > 1f ? 1f : v);
    }
}
