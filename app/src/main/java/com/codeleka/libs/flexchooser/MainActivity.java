package com.codeleka.libs.flexchooser;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codeleka.lib.choosergallery.ChooserType;
import com.codeleka.lib.choosergallery.FlexChooser;
import com.codeleka.lib.choosergallery.inter.OnResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.choose_file);
        TextView textView = findViewById(R.id.choose_path);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Dexter.withContext(this).withPermissions(android.Manifest.permission.READ_MEDIA_VIDEO, android.Manifest.permission.READ_MEDIA_VIDEO).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        } else {
            Dexter.withContext(this).withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }


        FlexChooser flexChooser = new FlexChooser(this);

        button.setOnClickListener(v -> {
            flexChooser.setLimit(5).setType(ChooserType.Image).startActivity().getResult(new OnResult() {
                @Override
                public void onResult(List<String> imageList) {

                    StringBuilder textPath = new StringBuilder();
                    for (String path : imageList) {
                        textPath.append("\n").append(path);
                    }
                    textView.setText(textPath.toString());

                }
            });
        });


    }
}