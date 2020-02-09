package com.Codeboy.MediaFacer_Examples;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int continueAfterPermission = 0;
    Intent audioIntent;
    Intent picIntent;
    Intent videoIntent ;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS = 10;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioIntent = new Intent(MainActivity.this,audioActivity.class);
        picIntent = new Intent(MainActivity.this,pictureActivity.class);
        videoIntent = new Intent(MainActivity.this,videoActivity.class);

        Button mPicButton = findViewById(R.id.pictureBut);
        mPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoragePermisssionGranted()){
                    startActivity(picIntent);
                }else {
                    continueAfterPermission = 1;
                    requestStoragePermission();
                }

            }
        });

        Button mAudioBut = findViewById(R.id.audioBut);
        mAudioBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoragePermisssionGranted()){
                    startActivity(audioIntent);
                }else {
                    continueAfterPermission = 2;
                    requestStoragePermission();
                }
            }
        });

        Button mVideoBut = findViewById(R.id.videoBut);
        mVideoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoragePermisssionGranted()){
                    startActivity(videoIntent);
                }else{
                    continueAfterPermission = 3;
                    requestStoragePermission();
                }
            }
        });
    }

    private void requestStoragePermission(){
        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS);


    }

    private boolean isStoragePermisssionGranted(){
        boolean granted = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if( (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
                granted = true;

            }else {
                granted = false;
            }
        }else {
            granted = true;
        }
        return granted;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Storage permission granted");
            continueAfterPermissionGrant();
        }else{
            Toast.makeText(this,"You must Grants Storage Permission to continue",Toast.LENGTH_LONG).show();
        }
    }

    private void continueAfterPermissionGrant(){
        switch (continueAfterPermission){
            case 1 :
                startActivity(picIntent);
                break;
            case 2 :
                startActivity(audioIntent);
                break;
            case 3 :
                startActivity(videoIntent);
                break;
        }
        continueAfterPermission = 0;
    }

}
