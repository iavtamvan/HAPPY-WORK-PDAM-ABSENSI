package com.pdamkotasmg.goodday.fitur.profil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.utils.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrCodeActivity extends AppCompatActivity {

    private String npp;
    private String nama;
    private String jabatan;

    private Bitmap saveBitmapQRCode;

    private static final String TAG = "debug";
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private ImageView ivQrCode;
    private LinearLayout divHeaderName;
    private TextView tvName;
    private TextView tvJabatan;
    private TextView tvNpp;
    private LottieAnimationView animationView;
    private ImageView ivScan;
    private LinearLayout divQrCode;
    private LinearLayout divSimpanQrCode;
    private ImageView ivLogoGDQrCode;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        
        initView();
        ivHeaderInfo.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        nama = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        jabatan = sharedPreferences.getString(Config.SHARED_JABATAN, "");
        tvHeaderJudul.setText("QR Code " + npp);
        tvName.setText(nama);
        tvNpp.setText(npp);
// Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(npp, null, QRGContents.Type.TEXT, 999);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);
        // Getting QR-Code as Bitmap
        Bitmap bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        ivQrCode.setImageBitmap(bitmap);

        ivScan.setOnClickListener(v -> {
            startActivity(new Intent(QrCodeActivity.this, ScanQRCodeActivity.class));
        });
        ivHeaderBackArrow.setOnClickListener(v -> {
            QrCodeActivity.this.finish();
        });

        divSimpanQrCode.setOnClickListener(view -> {
            ProgressDialog progressDialog = new ProgressDialog(QrCodeActivity.this);
            progressDialog.show();
            progressDialog.setMessage("Mohon Tunggu");
            progressDialog.setCancelable(false);
            divQrCode.setDrawingCacheEnabled(true);
            divQrCode.buildDrawingCache(true);
            saveBitmapQRCode = Bitmap.createBitmap(divQrCode.getDrawingCache());

            // Assume block needs to be inside a Try/Catch block.
            Date currentTime = Calendar.getInstance().getTime();
            Log.d(TAG, "currentTime: " + currentTime.getTime());

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/PDAM");
            OutputStream fOut = null;
            File file = new File(path, "files_QR_CODE_" + npp + "_" + currentTime.getTime() + ".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
            try {
                fOut = new FileOutputStream(file);
                saveBitmapQRCode.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                fOut.flush(); // Not really required
                fOut.close(); // do not forget to close the stream

                MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                Log.d(TAG, "getAbsolutePath QRCode: " + file.getAbsolutePath());
                Log.d(TAG, "getName QRCode: " + file.getName());

                progressDialog.dismiss();
                Toast.makeText(this, "Berhasil simpan di Gallery", Toast.LENGTH_SHORT).show();
                Config.showNotification(QrCodeActivity.this, "QR Code", "Berhasil menyimpan QR Code, cek di Gallery!", QrCodeActivity.class);

            } catch (IOException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }


        });

    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        ivQrCode = findViewById(R.id.iv_qr_code);
        divHeaderName = findViewById(R.id.div_header_name);
        tvName = findViewById(R.id.tv_name);
        tvNpp = findViewById(R.id.tv_npp);
        animationView = findViewById(R.id.animation_view);
        ivScan = findViewById(R.id.iv_scan);
        divQrCode = findViewById(R.id.div_qr_code);
        divSimpanQrCode = findViewById(R.id.div_simpan_qr_code);
        ivLogoGDQrCode = findViewById(R.id.iv_logo_GD_qr_code);
    }
}