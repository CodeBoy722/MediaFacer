package com.Codeboy.MediaFacer_Examples;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Spinner;

import com.Codeboy.MediaFacer.MediaFacer;
import com.Codeboy.MediaFacer.PictureGet;
import com.Codeboy.MediaFacer.mediaHolders.pictureContent;
import com.Codeboy.MediaFacer.mediaHolders.pictureFolderContent;

import java.util.ArrayList;

public class pictureActivity extends AppCompatActivity {

    ArrayList<pictureContent> allPhotos;
    RecyclerView picture_recycler;
    Spinner folderSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        picture_recycler = findViewById(R.id.picture_recycler);
        picture_recycler.hasFixedSize();
        folderSelector = findViewById(R.id.folder_selector);

        ArrayList<pictureFolderContent> pictureFolders = MediaFacer.withPictureContex(this).getPicturePaths();
        allPhotos = MediaFacer.withPictureContex(this).getAllPictureContents(PictureGet.externalContentUri);

    }

}
