package com.pdamkotasmg.goodday.fitur.profil;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.utils.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

public class DigitalSignatureActivity extends AppCompatActivity {

    public final String TAG = "debug";
    private String npp;
    private String name;
    private SharedPreferences sharedPreferences;
    private String fileTTE;

    private SignaturePad signaturePad;
    private Button btnReset;
    private Button btnSave;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private ImageView ivTte;
    private Button btnUpdateTTE;
    private LinearLayout divTtePengguna;
    private LinearLayout divBtnAction;
    private TextView tvNppName;
    private LinearLayout divTteCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_signature);
        initView();
        getSupportActionBar().hide();

        ivHeaderBackArrow.setOnClickListener(view -> {
            DigitalSignatureActivity.this.finish();
        });

        tvHeaderJudul.setText("Tanda Tangan Elektronik (TTE)");
        ivHeaderInfo.setVisibility(View.GONE);

        btnReset.setOnClickListener(view -> {
            signaturePad.clear();
        });

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(Config.SHARED_NAME, "");
        npp = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        tvNppName.setText(name + "\n" + npp);

        btnSave.setOnClickListener(view -> {
            if (signaturePad.isEmpty()) {
                Toast.makeText(this, "Lengkapi syarat TTE Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            } else {

                Log.d(TAG, "getSignatureBitmap: " + signaturePad.getSignatureBitmap());
                Log.d(TAG, "getTransparentSignatureBitmap: " + signaturePad.getTransparentSignatureBitmap());

//            File file;
//
//            Compressor compressedImageFile = new Compressor(DigitalSignatureActivity.this)
//                    .setMaxHeight(512)
//                    .setMaxWidth(512)
//                    .setQuality(80)
//                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                    .setDestinationDirectoryPath(File.pathSeparator)
//                    .compressToFile(signaturePad.getTransparentSignatureBitmap(), "comp_" + imageFiles[0].getFile().getName());

                // Assume block needs to be inside a Try/Catch block.
                Date currentTime = Calendar.getInstance().getTime();
                Log.d(TAG, "currentTime: " + currentTime.getTime());

                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                OutputStream fOut = null;
                Integer counter = 0;
                File file = new File(path, "files_DS_" + npp + "_" + currentTime.getTime() + "_" + ".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                try {
                    fOut = new FileOutputStream(file);
                    Bitmap pictureBitmap = signaturePad.getSignatureBitmap(); // obtaining the Bitmap
                    pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                    fOut.flush(); // Not really required
                    fOut.close(); // do not forget to close the stream

                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                    fileTTE = file.getAbsolutePath();
                    Log.d(TAG, "getAbsolutePath: " + file.getAbsolutePath());
                    Log.d(TAG, "getName: " + file.getName());

                    Toast.makeText(this, "Menigirim ke server...", Toast.LENGTH_SHORT).show();

                    divTteCreate.setVisibility(View.GONE);
                    divTtePengguna.setVisibility(View.VISIBLE);
                    Glide.with(DigitalSignatureActivity.this).load(file.getAbsolutePath()).into(ivTte);
                    btnUpdateTTE.setVisibility(View.VISIBLE);
                    divBtnAction.setVisibility(View.GONE);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        btnUpdateTTE.setVisibility(View.GONE);
        btnUpdateTTE.setOnClickListener(view -> {
            divBtnAction.setVisibility(View.VISIBLE);
            divTtePengguna.setVisibility(View.GONE);
            divTteCreate.setVisibility(View.VISIBLE);
        });
    }

    private void initView() {
        signaturePad = findViewById(R.id.signature_pad);
        btnReset = findViewById(R.id.btnReset);
        btnSave = findViewById(R.id.btnSave);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        ivTte = findViewById(R.id.iv_tte);
        btnUpdateTTE = findViewById(R.id.btnUpdateTTE);
        divTtePengguna = findViewById(R.id.div_tte_pengguna);
        divBtnAction = findViewById(R.id.div_btn_action);
        tvNppName = findViewById(R.id.tv_npp_name);
        divTteCreate = findViewById(R.id.div_tte_create);
    }
}