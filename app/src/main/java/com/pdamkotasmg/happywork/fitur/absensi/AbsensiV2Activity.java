package com.pdamkotasmg.happywork.fitur.absensi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pdamkotasmg.happywork.R;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class AbsensiV2Activity extends AppCompatActivity {
    private static final String TAG = "debug";
    private File compressedImageFile;

    private ImageView ivFotoFront;
    private EasyImage easyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_v2);
        initView();
        easyImage = new EasyImage.Builder(AbsensiV2Activity.this)

// Chooser only
// Will appear as a system chooser title, DEFAULT empty string
//.setChooserTitle("Pick media")
// Will tell chooser that it should show documents or gallery apps
//.setChooserType(ChooserType.CAMERA_AND_DOCUMENTS)  you can use this or the one below
//.setChooserType(ChooserType.CAMERA_AND_GALLERY)
// saving EasyImage state (as for now: last camera file link)
//                .setMemento(memento)

// Setting to true will cause taken pictures to show up in the device gallery, DEFAULT false
                .setCopyImagesToPublicGalleryFolder(false)
// Sets the name for images stored if setCopyImagesToPublicGalleryFolder = true
                .setFolderName("PDAM-KOTA-SMG")

// Allow multiple picking
                .allowMultiple(true)
                .build();

        ivFotoFront.setOnClickListener(v -> {
//            dispatchTakePictureIntent();
            easyImage.openCameraForImage(AbsensiV2Activity.this);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
//                onPhotosReturned(imageFiles);

                // TODO jepret foto
                Log.d(TAG, "onMediaFilesPickedPath: " + imageFiles[0].getFile().getPath());
                Log.d(TAG, "onMediaFilesPickedAbsoluthPath: " + imageFiles[0].getFile().getAbsolutePath());
                Log.d(TAG, "onMediaFilesPickedGetName: " + imageFiles[0].getFile().getName());
                Log.d(TAG, "onMediaFilesPickedGetParent: " + imageFiles[0].getFile().getParent());
                Toast.makeText(AbsensiV2Activity.this, "" + imageFiles.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(AbsensiV2Activity.this, "" + source, Toast.LENGTH_SHORT).show();
                Glide.with(AbsensiV2Activity.this).load(imageFiles[0].getFile()).override(512, 512).into(ivFotoFront);

                // TODO Compress file/image
                try {
                    compressedImageFile = new Compressor(AbsensiV2Activity.this)
                            .setMaxHeight(640)
                            .setMaxWidth(480)
                            .setQuality(40)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(imageFiles[0].getFile().getParent())
                            .compressToFile(imageFiles[0].getFile(), "comp_" + imageFiles[0].getFile().getName());
                    Log.d(TAG, "compressed: " + compressedImageFile);

                    // TODO delete image
                    File file = new File(imageFiles[0].getFile().getPath());
                    boolean deleted = file.delete();
                    Log.d(TAG, "deletedFilesStatus: " + deleted);

                    // TODO Check Face


                } catch (IOException e) {
                    Log.d(TAG, "failureCOmpressed: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });
    }

    private void initView() {
        ivFotoFront = findViewById(R.id.iv_foto_front);
    }
}