package co.id.pdamkotasmg.pekerjaanteknik.view;

import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;

/**
 * Adegan animasi kartu utama Home SPK, bertema pekerjaan teknik PDAM:
 * <ul>
 *   <li>pipa "terpasang" dari kiri ke kanan, lalu air mulai mengalir di dalamnya;</li>
 *   <li>roda katup (gate valve) diputar seperempat putaran secara berkala;</li>
 *   <li>jarum manometer bergerak mengikuti tekanan air;</li>
 *   <li>sambungan pipa bocor meneteskan air, kunci pas datang mengencangkan,
 *       lalu muncul kilau dan tanda centang: perbaikan selesai. Cerita ini berulang.</li>
 * </ul>
 * Gerakan gulir halaman "memompa" air lewat {@link #dorong(float)}: aliran makin
 * deras, gelombang lebih tinggi, dan jarum manometer ikut naik.
 *
 * <p>Semua digambar dengan Canvas dan satu {@link TimeAnimator}, tanpa bitmap dan
 * tanpa library. Animasi berhenti sendiri saat layar dijeda, jendela tidak terlihat,
 * view dilepas, atau Activity memanggil {@code setAktif(false)} (misal kartu sudah
 * tertutup gulir). Bila pengguna mematikan animasi di pengaturan sistem, yang tampil
 * adalah satu bingkai diam.</p>
 */
public class AnimasiPipaView extends View {

    /** Lama satu cerita: bocor, dikencangkan, selesai (detik). */
    private static final float SIKLUS = 8f;
    /** Cerita bocor baru dimulai setelah pipa selesai terpasang (detik). */
    private static final float MULAI_CERITA = 1.6f;
    /** Bingkai diam yang dipakai bila animasi sistem dimatikan: pipa lengkap, tanpa tetesan. */
    private static final float WAKTU_DIAM = MULAI_CERITA + 7.8f;
    /** Detik munculnya tiap tetesan dalam satu cerita. */
    private static final float[] MULAI_TETES = {0.3f, 1.15f, 2.0f, 2.85f};
    private static final float LAMA_JATUH = 0.5f;
    private static final float LAMA_RIAK = 0.9f;

    private final TimeAnimator detak = new TimeAnimator();
    private final float dp;

    private float waktu;
    private float fasaAliran;
    private float dorongan;
    private boolean diizinkan = true;
    private boolean terpasang;
    private boolean jendelaTerlihat = true;
    private boolean animasiSistemAktif = true;

    // Ukuran dan posisi, dihitung ulang di onSizeChanged.
    private float lebar;
    private float tinggi;
    private float pipaY;
    private float tebalPipa;
    private float tebalAir;
    private float katupX;
    private float badanKatupY;
    private float rodaY;
    private float radiusRoda;
    private float manoX;
    private float manoY;
    private float radiusMano;
    private float bocorX;
    private float dasarGelBelakang;
    private float dasarGelDepan;
    private float periodeAliran;
    private final float[] polaAliran = new float[2];
    private final float[] flens = new float[4];
    private int jumlahFlens;

    private final Path pipaUtama = new Path();
    private final Path pipaCabang = new Path();
    private final Path gelBelakang = new Path();
    private final Path gelDepan = new Path();
    private final Path kunci = new Path();
    private final Path bintang = new Path();
    private final Path centang = new Path();
    private final Path skala = new Path();
    private final Path tetes = new Path();
    private final RectF kotak = new RectF();

    private final Paint catPipa = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint catAir = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint catAliran = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint catIsi = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint catGaris = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint catCahayaAtas = new Paint();
    private final Paint catCahayaBawah = new Paint();

    public AnimasiPipaView(Context context) {
        this(context, null);
    }

    public AnimasiPipaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimasiPipaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp = getResources().getDisplayMetrics().density;

        catPipa.setStyle(Paint.Style.STROKE);
        catPipa.setStrokeCap(Paint.Cap.ROUND);
        catPipa.setStrokeJoin(Paint.Join.ROUND);
        catAir.setStyle(Paint.Style.STROKE);
        catAir.setStrokeCap(Paint.Cap.ROUND);
        catAir.setStrokeJoin(Paint.Join.ROUND);
        catAliran.setStyle(Paint.Style.STROKE);
        catAliran.setStrokeCap(Paint.Cap.ROUND);
        catIsi.setStyle(Paint.Style.FILL);
        catGaris.setStyle(Paint.Style.STROKE);
        catGaris.setStrokeCap(Paint.Cap.ROUND);
        catGaris.setStrokeJoin(Paint.Join.ROUND);

        polaAliran[0] = 9 * dp;
        polaAliran[1] = 15 * dp;
        periodeAliran = polaAliran[0] + polaAliran[1];

        detak.setTimeListener((animation, totalTime, deltaTime) -> {
            // Dibatasi supaya setelah jeda panjang adegan tidak "melompat".
            majukan(Math.min(deltaTime, 48L) / 1000f);
            invalidate();
        });

        animasiSistemAktif = bacaAnimasiSistem();
        if (!animasiSistemAktif) {
            waktu = WAKTU_DIAM;
        }
    }

    /**
     * Dipanggil Activity: false saat layar dijeda atau kartu sudah tertutup gulir,
     * supaya tidak ada gambar ulang yang sia-sia.
     */
    public void setAktif(boolean aktif) {
        if (diizinkan == aktif) {
            return;
        }
        diizinkan = aktif;
        aturDetak();
    }

    /** Gerakan gulir "memompa" air: aliran makin deras dan jarum manometer naik. */
    public void dorong(float jarakPx) {
        if (!animasiSistemAktif) {
            return;
        }
        dorongan = Math.min(1.2f, dorongan + Math.abs(jarakPx) / (dp * 900f));
    }

    // ---------------------------------------------------------------------
    // Siklus hidup animasi
    // ---------------------------------------------------------------------

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        terpasang = true;
        animasiSistemAktif = bacaAnimasiSistem();
        aturDetak();
    }

    @Override
    protected void onDetachedFromWindow() {
        terpasang = false;
        detak.cancel();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        jendelaTerlihat = visibility == VISIBLE;
        aturDetak();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        aturDetak();
    }

    private void aturDetak() {
        // Bisa terpanggil dari konstruktor View sebelum field ini terisi.
        if (detak == null) {
            return;
        }
        boolean jalan = diizinkan && terpasang && jendelaTerlihat && animasiSistemAktif && isShown();
        if (jalan) {
            if (!detak.isStarted()) {
                detak.start();
            } else if (detak.isPaused()) {
                detak.resume();
            }
        } else if (detak.isStarted() && !detak.isPaused()) {
            detak.pause();
        }
    }

    private boolean bacaAnimasiSistem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ValueAnimator.areAnimatorsEnabled();
        }
        try {
            return Settings.Global.getFloat(getContext().getContentResolver(),
                    Settings.Global.ANIMATOR_DURATION_SCALE, 1f) != 0f;
        } catch (Exception e) {
            return true;
        }
    }

    private void majukan(float dt) {
        waktu += dt;
        dorongan *= (float) Math.exp(-2.4f * dt);
        if (dorongan < 0.002f) {
            dorongan = 0f;
        }
        fasaAliran = (fasaAliran + dt * dp * 34f * (1f + 2.6f * dorongan)) % periodeAliran;
    }

    // ---------------------------------------------------------------------
    // Ukuran dan bentuk statis
    // ---------------------------------------------------------------------

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        lebar = w;
        tinggi = h;
        if (w == 0 || h == 0) {
            return;
        }

        tebalPipa = 13 * dp;
        tebalAir = 6.5f * dp;
        pipaY = Math.max(h * 0.58f, h - 84 * dp);
        dasarGelBelakang = h - 56 * dp;
        dasarGelDepan = h - 47 * dp;

        katupX = w - 50 * dp;
        radiusRoda = 15 * dp;
        badanKatupY = pipaY - 22 * dp;
        rodaY = pipaY - 50 * dp;

        manoX = w - 112 * dp;
        radiusMano = 17 * dp;
        manoY = pipaY - 38 * dp;

        bocorX = w * 0.40f;

        pipaUtama.rewind();
        pipaUtama.moveTo(-tebalPipa, pipaY);
        pipaUtama.lineTo(w + tebalPipa, pipaY);

        pipaCabang.rewind();
        pipaCabang.moveTo(manoX, pipaY);
        pipaCabang.lineTo(manoX, manoY + radiusMano * 0.6f);
        pipaCabang.moveTo(katupX, pipaY);
        pipaCabang.lineTo(katupX, badanKatupY);

        // Sambungan (flens) di pipa utama, dijauhkan dari percabangan.
        jumlahFlens = 0;
        float[] calon = {w * 0.13f, bocorX, w * 0.62f};
        for (float x : calon) {
            if (Math.abs(x - manoX) < 32 * dp || Math.abs(x - katupX) < 32 * dp) {
                continue;
            }
            if (jumlahFlens < flens.length) {
                flens[jumlahFlens++] = x;
            }
        }

        bangunKunci();
        bangunBintang();
        bangunCentang();
        bangunSkala();

        catCahayaAtas.setShader(new RadialGradient(w * 0.9f, h * 0.1f, Math.max(w, h) * 0.55f,
                0x2EFFFFFF, 0x00FFFFFF, Shader.TileMode.CLAMP));
        catCahayaBawah.setShader(new RadialGradient(w * 0.85f, h * 0.95f, Math.max(w, h) * 0.6f,
                0x5522C1DC, 0x0022C1DC, Shader.TileMode.CLAMP));
    }

    /** Kunci pas: kepala bulat bertakik menghadap sambungan, gagang ke arah atas. */
    private void bangunKunci() {
        kunci.rewind();
        Path kepala = new Path();
        kepala.addCircle(0, 0, 6.4f * dp, Path.Direction.CW);
        Path gagang = new Path();
        kotak.set(-2.6f * dp, -27f * dp, 2.6f * dp, -3f * dp);
        gagang.addRoundRect(kotak, 2.6f * dp, 2.6f * dp, Path.Direction.CW);
        kunci.op(kepala, gagang, Path.Op.UNION);

        Path takik = new Path();
        takik.addRect(-2.3f * dp, 1.4f * dp, 2.3f * dp, 8f * dp, Path.Direction.CW);
        kunci.op(takik, Path.Op.DIFFERENCE);

        Path lubang = new Path();
        lubang.addCircle(0, -22.5f * dp, 1.3f * dp, Path.Direction.CW);
        kunci.op(lubang, Path.Op.DIFFERENCE);
    }

    /** Kilau empat sudut, ukuran satuan (diperbesar saat digambar). */
    private void bangunBintang() {
        bintang.rewind();
        float k = 0.2f;
        bintang.moveTo(0, -1);
        bintang.quadTo(k, -k, 1, 0);
        bintang.quadTo(k, k, 0, 1);
        bintang.quadTo(-k, k, -1, 0);
        bintang.quadTo(-k, -k, 0, -1);
        bintang.close();
    }

    private void bangunCentang() {
        float r = 8.5f * dp;
        centang.rewind();
        centang.moveTo(-0.42f * r, 0.02f * r);
        centang.lineTo(-0.12f * r, 0.32f * r);
        centang.lineTo(0.44f * r, -0.30f * r);
    }

    /** Garis skala manometer: sembilan garis dari 135 derajat, sapuan 270 derajat. */
    private void bangunSkala() {
        skala.rewind();
        for (int i = 0; i <= 8; i++) {
            double a = Math.toRadians(135 + 270.0 * i / 8);
            float r1 = radiusMano - 3.5f * dp;
            float r2 = radiusMano - (i % 2 == 0 ? 7.5f : 6f) * dp;
            skala.moveTo((float) (Math.cos(a) * r1), (float) (Math.sin(a) * r1));
            skala.lineTo((float) (Math.cos(a) * r2), (float) (Math.sin(a) * r2));
        }
    }

    // ---------------------------------------------------------------------
    // Menggambar
    // ---------------------------------------------------------------------

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lebar == 0 || tinggi == 0) {
            return;
        }
        float t = waktu;

        canvas.drawRect(0, 0, lebar, tinggi, catCahayaAtas);
        canvas.drawRect(0, 0, lebar, tinggi, catCahayaBawah);

        gambarGelembung(canvas, t);

        // Gelombang naik dari bawah saat adegan pertama kali tampil.
        float naikGel = (1f - keluar(jepit(t / 0.9f))) * 26 * dp;
        float amp = 1f + 0.8f * Math.min(1f, dorongan);

        bangunGelombang(gelBelakang, dasarGelBelakang + naikGel, 5.5f * dp * amp, 190 * dp, t * 0.9f);
        catIsi.setColor(0x21FFFFFF);
        canvas.drawPath(gelBelakang, catIsi);

        gambarPipa(canvas, t);
        gambarManometer(canvas, t);
        gambarKatup(canvas, t);
        gambarTetesan(canvas, t, naikGel, amp);

        bangunGelombang(gelDepan, dasarGelDepan + naikGel, 4.2f * dp * amp, 132 * dp, -t * 1.35f);
        catIsi.setColor(0x38FFFFFF);
        canvas.drawPath(gelDepan, catIsi);

        gambarRiak(canvas, t, naikGel, amp);
        gambarKunci(canvas, t);
        gambarSelesai(canvas, t);
    }

    private void gambarGelembung(Canvas c, float t) {
        catGaris.setStrokeWidth(1.2f * dp);
        for (int i = 0; i < 7; i++) {
            float kecepatan = (8f + 4f * (i % 3)) * dp;
            float rentang = tinggi * 0.95f;
            float jalan = (t * kecepatan + i * 47f * dp) % rentang;
            float y = tinggi - jalan;
            float x = lebar * (0.05f + 0.14f * i) + 5f * dp * (float) Math.sin(t * 1.1f + i * 1.7f);
            float r = (1.8f + (i % 3) * 1.2f) * dp;
            float a = jepit((y - tinggi * 0.06f) / (tinggi * 0.3f)) * jepit((tinggi - y) / (tinggi * 0.18f));
            if (a <= 0.01f) {
                continue;
            }
            catGaris.setColor(putih(0.45f * a));
            c.drawCircle(x, y, r, catGaris);
            catIsi.setColor(putih(0.5f * a));
            c.drawCircle(x - r * 0.35f, y - r * 0.35f, r * 0.28f, catIsi);
        }
    }

    private void gambarPipa(Canvas c, float t) {
        float buka = keluar(jepit(t / 1.1f));
        float bukaAir = keluar(jepit((t - 0.3f) / 1.1f));
        if (buka <= 0f) {
            return;
        }

        // 1. Badan pipa dan kilap di sisi atasnya.
        c.save();
        if (buka < 1f) {
            c.clipRect(0, 0, lebar * buka, tinggi);
        }
        catPipa.setStrokeWidth(tebalPipa);
        catPipa.setColor(0x3DFFFFFF);
        c.drawPath(pipaUtama, catPipa);
        c.drawPath(pipaCabang, catPipa);
        catGaris.setStrokeWidth(1.4f * dp);
        catGaris.setColor(0x47FFFFFF);
        c.drawLine(0, pipaY - tebalPipa * 0.3f, lebar, pipaY - tebalPipa * 0.3f, catGaris);
        c.restore();

        // 2. Air di dalam pipa, masuk sedikit lebih lambat dari pipanya.
        if (bukaAir > 0f) {
            c.save();
            if (bukaAir < 1f) {
                c.clipRect(0, 0, lebar * bukaAir, tinggi);
            }
            catAir.setStrokeWidth(tebalAir);
            catAir.setColor(0x8CB9F3FF);
            c.drawPath(pipaUtama, catAir);
            c.drawPath(pipaCabang, catAir);

            float fase = periodeAliran - fasaAliran;
            catAliran.setPathEffect(new DashPathEffect(polaAliran, fase));
            catAliran.setStrokeWidth(2.4f * dp);
            catAliran.setColor(putih(0.7f + 0.3f * Math.min(1f, dorongan)));
            c.drawPath(pipaUtama, catAliran);
            c.drawPath(pipaCabang, catAliran);
            c.restore();
        }

        // 3. Sambungan T dan flens menutupi pipa.
        c.save();
        if (buka < 1f) {
            c.clipRect(0, 0, lebar * buka, tinggi);
        }
        catIsi.setColor(0x52FFFFFF);
        kotakTengah(c, manoX, pipaY, 12 * dp, tebalPipa + 4 * dp, 3 * dp);
        kotakTengah(c, katupX, pipaY, 12 * dp, tebalPipa + 4 * dp, 3 * dp);
        catGaris.setStrokeWidth(1f * dp);
        catGaris.setColor(0x33004E6E);
        for (int i = 0; i < jumlahFlens; i++) {
            catIsi.setColor(0x6BFFFFFF);
            kotakTengah(c, flens[i], pipaY, 7 * dp, tebalPipa + 9 * dp, 2.5f * dp);
            c.drawLine(flens[i], pipaY - tebalPipa * 0.5f - 3 * dp,
                    flens[i], pipaY + tebalPipa * 0.5f + 3 * dp, catGaris);
        }
        c.restore();
    }

    private void kotakTengah(Canvas c, float cx, float cy, float w, float h, float r) {
        kotak.set(cx - w / 2f, cy - h / 2f, cx + w / 2f, cy + h / 2f);
        c.drawRoundRect(kotak, r, r, catIsi);
    }

    private void gambarManometer(Canvas c, float t) {
        float s = lenting(jepit((t - 0.75f) / 0.5f));
        if (s <= 0.01f) {
            return;
        }
        c.save();
        c.translate(manoX, manoY);
        c.scale(s, s);

        catGaris.setStrokeWidth(3.5f * dp);
        catGaris.setColor(0x40FFFFFF);
        c.drawCircle(0, 0, radiusMano + 2.5f * dp, catGaris);

        catIsi.setColor(0xF5FFFFFF);
        c.drawCircle(0, 0, radiusMano, catIsi);

        // Zona merah: tekanan berlebih.
        float rz = radiusMano - 3.5f * dp;
        kotak.set(-rz, -rz, rz, rz);
        catGaris.setStrokeWidth(2.4f * dp);
        catGaris.setColor(0xFFFF6B6B);
        c.drawArc(kotak, 135 + 270 * 0.78f, 270 * 0.22f, false, catGaris);

        catGaris.setStrokeWidth(1.2f * dp);
        catGaris.setColor(0xB3074A67);
        c.drawPath(skala, catGaris);

        // Tekanan berdenyut halus; gulir menambah tekanan; saat pertama tampil
        // jarum menyapu naik seperti alat baru dinyalakan.
        float nilai = 0.5f + 0.07f * (float) Math.sin(t * 1.4f)
                + 0.03f * (float) Math.sin(t * 4.1f + 1f)
                + 0.3f * Math.min(1f, dorongan);
        nilai = 0.04f + 0.92f * jepit(nilai);
        nilai *= lenting(jepit((t - 1.0f) / 0.9f));
        double a = Math.toRadians(135 + 270 * nilai);
        float panjang = radiusMano - 5.5f * dp;
        catGaris.setStrokeWidth(2f * dp);
        catGaris.setColor(0xFF0B4F6C);
        c.drawLine((float) (-Math.cos(a) * 3 * dp), (float) (-Math.sin(a) * 3 * dp),
                (float) (Math.cos(a) * panjang), (float) (Math.sin(a) * panjang), catGaris);
        catIsi.setColor(0xFF0B4F6C);
        c.drawCircle(0, 0, 2.4f * dp, catIsi);

        c.restore();
    }

    private void gambarKatup(Canvas c, float t) {
        float s = lenting(jepit((t - 0.95f) / 0.5f));
        if (s <= 0.01f) {
            return;
        }

        c.save();
        c.translate(katupX, badanKatupY);
        c.scale(s, s);
        catIsi.setColor(0x73FFFFFF);
        kotak.set(-9 * dp, -6.5f * dp, 9 * dp, 6.5f * dp);
        c.drawRoundRect(kotak, 3 * dp, 3 * dp, catIsi);
        c.restore();

        catGaris.setStrokeWidth(2.4f * dp);
        catGaris.setColor(putih(0.85f * jepit(s)));
        c.drawLine(katupX, badanKatupY - 6 * dp * s, katupX, rodaY, catGaris);

        // Diputar seperempat putaran tiap 3,4 detik, seperti petugas membuka katup.
        float sudut = 0f;
        if (t > 1.5f) {
            float periode = 3.4f;
            float jalan = t - 1.5f;
            int ke = (int) (jalan / periode);
            float sisa = (jalan - ke * periode) / 0.95f;
            sudut = 90f * (ke % 4 + masukKeluar(jepit(sisa)));
        }

        c.save();
        c.translate(katupX, rodaY);
        c.scale(s, s);
        c.rotate(sudut);
        catGaris.setStrokeWidth(3f * dp);
        catGaris.setColor(0xF2FFFFFF);
        c.drawCircle(0, 0, radiusRoda, catGaris);
        catGaris.setStrokeWidth(2.2f * dp);
        for (int i = 0; i < 4; i++) {
            double a = Math.toRadians(i * 90);
            c.drawLine(0, 0, (float) Math.cos(a) * radiusRoda, (float) Math.sin(a) * radiusRoda, catGaris);
        }
        catIsi.setColor(0xFFFFFFFF);
        for (int i = 0; i < 4; i++) {
            double a = Math.toRadians(45 + i * 90);
            c.drawCircle((float) Math.cos(a) * radiusRoda, (float) Math.sin(a) * radiusRoda, 2.3f * dp, catIsi);
        }
        c.drawCircle(0, 0, 3.8f * dp, catIsi);
        c.restore();
    }

    /** Waktu di dalam satu cerita bocor, atau -1 bila pipa belum selesai terpasang. */
    private float waktuCerita(float t) {
        if (t < MULAI_CERITA) {
            return -1f;
        }
        return (t - MULAI_CERITA) % SIKLUS;
    }

    private float yGelDepan(float x, float naikGel, float amp, float t) {
        double k = 2 * Math.PI / (132 * dp);
        return dasarGelDepan + naikGel + 4.2f * dp * amp * (float) Math.sin(x * k - t * 1.35f);
    }

    private void gambarTetesan(Canvas c, float t, float naikGel, float amp) {
        float u = waktuCerita(t);
        if (u < 0f) {
            return;
        }
        float yAwal = pipaY + tebalPipa * 0.5f + dp;
        float yAir = yGelDepan(bocorX, naikGel, amp, t);
        catIsi.setColor(0xFFE3FAFF);
        for (float mulai : MULAI_TETES) {
            // Tetes menggantung dan membesar di bawah sambungan sebelum jatuh.
            float tumbuh = (u - (mulai - 0.45f)) / 0.45f;
            if (tumbuh >= 0f && tumbuh < 1f) {
                float r = 2.5f * dp * tumbuh;
                c.drawCircle(bocorX, yAwal + r * 0.9f, r, catIsi);
            }
            float umur = u - mulai;
            if (umur >= 0f && umur < LAMA_JATUH) {
                float f = umur / LAMA_JATUH;
                float y = yAwal + (yAir - yAwal) * f * f;
                gambarTetes(c, bocorX, y, 2.5f * dp, 1f + 1.4f * f);
            }
        }
    }

    private void gambarTetes(Canvas c, float x, float y, float r, float regang) {
        c.drawCircle(x, y, r, catIsi);
        // Ekor tetesan memanjang ke atas mengikuti kecepatan jatuh.
        tetes.rewind();
        tetes.moveTo(x - r * 0.92f, y - r * 0.2f);
        tetes.quadTo(x, y - r * 2.4f * regang, x + r * 0.92f, y - r * 0.2f);
        tetes.close();
        c.drawPath(tetes, catIsi);
    }

    private void gambarRiak(Canvas c, float t, float naikGel, float amp) {
        float u = waktuCerita(t);
        if (u < 0f) {
            return;
        }
        float yAir = yGelDepan(bocorX, naikGel, amp, t);
        catGaris.setStrokeWidth(1.4f * dp);
        for (float mulai : MULAI_TETES) {
            float r = u - mulai - LAMA_JATUH;
            if (r < 0f || r >= LAMA_RIAK) {
                continue;
            }
            float f = r / LAMA_RIAK;
            float rx = 3 * dp + 17 * dp * keluar(f);
            catGaris.setColor(putih(0.75f * (1f - f)));
            kotak.set(bocorX - rx, yAir - rx * 0.3f, bocorX + rx, yAir + rx * 0.3f);
            c.drawOval(kotak, catGaris);
        }
    }

    private void gambarKunci(Canvas c, float t) {
        float u = waktuCerita(t);
        if (u < 2.6f || u > 6.4f) {
            return;
        }
        float alpha = jepit((u - 2.6f) / 0.3f) * jepit((6.4f - u) / 0.3f);
        float masuk = keluar(jepit((u - 2.6f) / 0.45f));
        // Datang dari kiri, menjepit sambungan, lalu tiga kali mengencangkan.
        float sudut = -70f + 32f * masuk;
        if (u > 3.0f && u < 5.1f) {
            sudut += 16f * (float) Math.sin((u - 3.0f) / 0.7f * 2 * Math.PI);
        }
        c.save();
        c.translate(bocorX, pipaY);
        c.rotate(sudut);

        c.save();
        c.translate(1.2f * dp, 1.6f * dp);
        catIsi.setColor(warna(0.14f * alpha, 0x002A3A));
        c.drawPath(kunci, catIsi);
        c.restore();

        catIsi.setColor(putih(alpha));
        c.drawPath(kunci, catIsi);
        c.restore();
    }

    private void gambarSelesai(Canvas c, float t) {
        float u = waktuCerita(t);
        if (u < 5.1f || u > 7.5f) {
            return;
        }
        float bx = bocorX + 15 * dp;
        float by = pipaY - 23 * dp;

        // Kilau saat baut selesai dikencangkan.
        if (u < 5.95f) {
            float f = (u - 5.1f) / 0.85f;
            float s = (float) Math.sin(Math.PI * f);
            if (s > 0.01f) {
                c.save();
                c.translate(bx - 3 * dp, by + 4 * dp);
                c.rotate(45f * f);
                c.scale(7.5f * dp * s, 7.5f * dp * s);
                catIsi.setColor(0xFFFFF1A8);
                c.drawPath(bintang, catIsi);
                c.restore();
            }
            catGaris.setStrokeWidth(1.3f * dp);
            catGaris.setColor(putih(0.6f * (1f - f)));
            c.drawCircle(bx - 3 * dp, by + 4 * dp, 4 * dp + 14 * dp * f, catGaris);
        }

        // Lencana centang: perbaikan selesai.
        if (u > 5.35f) {
            float s = lenting(jepit((u - 5.35f) / 0.4f));
            float alpha = jepit((7.5f - u) / 0.4f);
            if (s > 0.01f && alpha > 0.01f) {
                c.save();
                c.translate(bx, by);
                c.scale(s, s);
                catIsi.setColor(putih(alpha));
                c.drawCircle(0, 0, 8.5f * dp, catIsi);
                catGaris.setStrokeWidth(2.1f * dp);
                catGaris.setColor(warna(alpha, 0x12A67A));
                c.drawPath(centang, catGaris);
                c.restore();
            }
        }
    }

    private void bangunGelombang(Path p, float dasar, float amp, float panjang, float fasa) {
        p.rewind();
        p.moveTo(0, tinggi);
        float langkah = 7 * dp;
        double k = 2 * Math.PI / panjang;
        for (float x = 0; x <= lebar + langkah; x += langkah) {
            p.lineTo(x, dasar + amp * (float) Math.sin(x * k + fasa));
        }
        p.lineTo(lebar + langkah, tinggi);
        p.close();
    }

    // ---------------------------------------------------------------------
    // Bantuan matematika dan warna
    // ---------------------------------------------------------------------

    private static float jepit(float v) {
        return v < 0f ? 0f : (v > 1f ? 1f : v);
    }

    /** Ease-out kubik: cepat di awal, melambat di akhir. */
    private static float keluar(float v) {
        float b = 1f - v;
        return 1f - b * b * b;
    }

    /** Ease-out "back": sedikit melewati target lalu kembali, kesan lentur. */
    private static float lenting(float v) {
        float c1 = 1.70158f;
        float c3 = c1 + 1f;
        float x = v - 1f;
        return 1f + c3 * x * x * x + c1 * x * x;
    }

    /** Ease-in-out kubik. */
    private static float masukKeluar(float v) {
        if (v < 0.5f) {
            return 4f * v * v * v;
        }
        float b = -2f * v + 2f;
        return 1f - b * b * b / 2f;
    }

    private static int putih(float alpha) {
        return warna(alpha, 0xFFFFFF);
    }

    private static int warna(float alpha, int rgb) {
        return (Math.round(jepit(alpha) * 255f) << 24) | (rgb & 0x00FFFFFF);
    }
}
