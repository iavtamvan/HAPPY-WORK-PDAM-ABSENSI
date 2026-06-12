package co.id.pdamkotasmg.ui.activity.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.pembacameter.databinding.ActivityInAppCameraBinding;

/**
 * In-App Camera (CameraX) — replace EasyImage/system camera saat toggle ON.
 *
 * Output: path File hasil capture (sudah ada watermark non-intrusif di pojok kanan-bawah)
 *         dikembalikan via Intent extra {@link #RESULT_FILE_PATH}.
 *
 * Watermark di-render saat capture (bukan overlay live preview), supaya tetap masuk ke
 * file foto tapi tidak mengganggu komposisi saat user membidik.
 */
public class InAppCameraActivity extends AppCompatActivity {

    private static final String TAG = "InAppCameraActivity";
    private static final int REQ_CAMERA_PERMISSION = 1001;

    /** Intent extra (input): text yang akan jadi watermark. Default = timestamp + lokasi placeholder. */
    public static final String EXTRA_WATERMARK_TEXT = "extra_watermark_text";

    /** Intent extra (input): identifier untuk file output. Optional. */
    public static final String EXTRA_FILE_TAG = "extra_file_tag";

    /** Intent extra (output): absolute path file hasil capture. */
    public static final String RESULT_FILE_PATH = "result_file_path";

    private ActivityInAppCameraBinding binding;
    private ImageCapture imageCapture;
    private CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
    private int flashMode = ImageCapture.FLASH_MODE_OFF;
    private ExecutorService cameraExecutor;
    private String watermarkText;
    private String fileTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInAppCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        watermarkText = getIntent().getStringExtra(EXTRA_WATERMARK_TEXT);
        if (watermarkText == null) {
            watermarkText = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                    .format(new Date());
        }
        fileTag = getIntent().getStringExtra(EXTRA_FILE_TAG);
        if (fileTag == null) fileTag = "cam";

        binding.tvWatermarkPreview.setText(watermarkText);

        cameraExecutor = Executors.newSingleThreadExecutor();

        binding.btnClose.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        binding.btnCapture.setOnClickListener(v -> takePhoto());
        binding.btnSwitchCamera.setOnClickListener(v -> switchCamera());
        binding.btnFlash.setOnClickListener(v -> toggleFlash());
        updateFlashIcon();

        if (hasCameraPermission()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQ_CAMERA_PERMISSION);
        }
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> providerFuture =
                ProcessCameraProvider.getInstance(this);
        providerFuture.addListener(() -> {
            try {
                ProcessCameraProvider provider = providerFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setFlashMode(flashMode)
                        .build();

                provider.unbindAll();
                provider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
            } catch (Exception e) {
                Log.e(TAG, "startCamera error", e);
                Toast.makeText(this, "Gagal start kamera: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void switchCamera() {
        cameraSelector = (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                ? CameraSelector.DEFAULT_FRONT_CAMERA
                : CameraSelector.DEFAULT_BACK_CAMERA;
        startCamera();
    }

    private void toggleFlash() {
        if (flashMode == ImageCapture.FLASH_MODE_OFF) {
            flashMode = ImageCapture.FLASH_MODE_ON;
        } else if (flashMode == ImageCapture.FLASH_MODE_ON) {
            flashMode = ImageCapture.FLASH_MODE_AUTO;
        } else {
            flashMode = ImageCapture.FLASH_MODE_OFF;
        }
        if (imageCapture != null) imageCapture.setFlashMode(flashMode);
        updateFlashIcon();
    }

    private void updateFlashIcon() {
        // Sederhana: warna saja, tidak ganti ikon karena bawaan android cukup
        int alpha = (flashMode == ImageCapture.FLASH_MODE_OFF) ? 110 : 255;
        binding.btnFlash.setImageAlpha(alpha);
    }

    private void takePhoto() {
        if (imageCapture == null) return;

        binding.progressCapture.setVisibility(View.VISIBLE);
        binding.btnCapture.setEnabled(false);

        imageCapture.takePicture(cameraExecutor, new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                try {
                    Bitmap original = imageProxyToBitmap(image);
                    image.close();
                    Bitmap watermarked = applyWatermark(original, watermarkText);
                    File out = saveBitmap(watermarked);
                    if (!watermarked.isRecycled()) watermarked.recycle();
                    if (original != watermarked && !original.isRecycled()) original.recycle();

                    new Handler(Looper.getMainLooper()).post(() -> {
                        Intent data = new Intent();
                        data.putExtra(RESULT_FILE_PATH, out.getAbsolutePath());
                        setResult(RESULT_OK, data);
                        finish();
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Save capture error", e);
                    new Handler(Looper.getMainLooper()).post(() -> {
                        binding.progressCapture.setVisibility(View.GONE);
                        binding.btnCapture.setEnabled(true);
                        Toast.makeText(InAppCameraActivity.this,
                                "Gagal simpan foto", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e(TAG, "Capture error", exception);
                new Handler(Looper.getMainLooper()).post(() -> {
                    binding.progressCapture.setVisibility(View.GONE);
                    binding.btnCapture.setEnabled(true);
                    Toast.makeText(InAppCameraActivity.this,
                            "Capture gagal: " + exception.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private static Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buf = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);
        Bitmap raw = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        int rotation = image.getImageInfo().getRotationDegrees();
        if (rotation != 0 && raw != null) {
            Matrix m = new Matrix();
            m.postRotate(rotation);
            Bitmap rotated = Bitmap.createBitmap(raw, 0, 0,
                    raw.getWidth(), raw.getHeight(), m, true);
            if (rotated != raw) raw.recycle();
            return rotated;
        }
        return raw;
    }

    /**
     * Watermark NON-INTRUSIVE: bottom-right, semi-transparan, font kecil.
     * Tidak menutupi area utama yang biasanya dipakai untuk angka meter.
     */
    private static Bitmap applyWatermark(Bitmap src, String text) {
        if (src == null || text == null) return src;
        Bitmap result = src.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(result);

        int w = result.getWidth();
        int h = result.getHeight();

        float textSize = Math.max(18f, w / 50f); // scale berdasarkan resolusi
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.argb(120, 0, 0, 0)); // semi-transparan hitam

        String[] lines = text.split("\n");
        Rect rect = new Rect();
        int maxWidth = 0;
        for (String l : lines) {
            textPaint.getTextBounds(l, 0, l.length(), rect);
            if (rect.width() > maxWidth) maxWidth = rect.width();
        }
        float lineHeight = textPaint.descent() - textPaint.ascent();
        int padding = (int) (textSize / 2f);
        float boxW = maxWidth + padding * 2f;
        float boxH = lineHeight * lines.length + padding * 2f;

        float boxLeft = w - boxW - padding;
        float boxTop = h - boxH - padding;

        canvas.drawRect(boxLeft, boxTop, boxLeft + boxW, boxTop + boxH, bgPaint);

        float y = boxTop + padding - textPaint.ascent();
        for (String l : lines) {
            canvas.drawText(l, boxLeft + padding, y, textPaint);
            y += lineHeight;
        }

        return result;
    }

    private File saveBitmap(Bitmap bmp) throws Exception {
        File dir = new File(getCacheDir(), "in_app_camera");
        if (!dir.exists()) dir.mkdirs();
        String name = "iac_" + fileTag + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + ".jpg";
        File out = new File(dir, name);
        try (FileOutputStream fos = new FileOutputStream(out)) {
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
        }
        return out;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraExecutor != null) cameraExecutor.shutdown();
    }
}
