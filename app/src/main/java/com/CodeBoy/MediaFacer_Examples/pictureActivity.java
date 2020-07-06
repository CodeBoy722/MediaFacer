package com.CodeBoy.MediaFacer_Examples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.CodeBoy.MediaFacer.MediaFacer;
import com.CodeBoy.MediaFacer.PictureGet;
import com.CodeBoy.MediaFacer.mediaHolders.pictureContent;
import com.CodeBoy.MediaFacer.mediaHolders.pictureFolderContent;
import com.CodeBoy.MediaFacer_Examples.adapters.imageRecycleAdapter;
import com.CodeBoy.MediaFacer_Examples.fragments.fragment_picture_display;
import com.CodeBoy.MediaFacer_Examples.fragments.pictureInfo;

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
        picture_recycler.setHasFixedSize(true);
        picture_recycler.setItemViewCacheSize(20);
        picture_recycler.setDrawingCacheEnabled(true);
        picture_recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        int numOfColumns = calculateNoOfColumns(this,90);
        picture_recycler.setLayoutManager(new GridLayoutManager(this,numOfColumns));

        allPhotos = new ArrayList<>();
        setUpFolderSelector();
    }

    void setUpAndDisplayPictures(){
        imageRecycleAdapter.pictureActionListrener actionListener = new imageRecycleAdapter.pictureActionListrener() {
            @Override
            public void onPictureItemClicked(int position) {
                //show picture information
                displayPictureInFragment(allPhotos,position);
            }

            @Override
            public void onPictureItemLongClicked(int position) {
                //show picture in fragment
                showPictureInfo(allPhotos.get(position));
            }
        };
        imageRecycleAdapter pictureAdapter = new imageRecycleAdapter(this,allPhotos,actionListener);
        picture_recycler.setAdapter(pictureAdapter);
    }

    int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    private void showPictureInfo(pictureContent picture){
        pictureInfo pictureDetails = new pictureInfo();
        pictureDetails.setPicture(picture);
        pictureDetails.show(getSupportFragmentManager(),"content_info");
    }

    private void displayPictureInFragment(ArrayList<pictureContent> pictureList, int position){
        fragment_picture_display picturePage = new fragment_picture_display();
        picturePage.setAllpics(pictureList,position);

        Transition transition = TransitionInflater.from(this).
                inflateTransition(android.R.transition.explode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            picturePage.setEnterTransition(new Slide(Gravity.TOP));
            picturePage.setExitTransition(new Slide(Gravity.BOTTOM));
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.motherview,picturePage)
                .addToBackStack(null)
                .commit();
    }

    private void setUpFolderSelector(){
        folderSelector = findViewById(R.id.folder_selector);

        final ArrayList<pictureFolderContent> pictureFolders = new ArrayList<>();
        pictureFolders.add(new pictureFolderContent("all","*All*"));
        pictureFolders.addAll(MediaFacer.withPictureContex(this).getAbsolutePictureFolders());

        final ArrayList<String> folders = new ArrayList<>();
        for(int i = 0;i < pictureFolders.size();i++){
            folders.add(pictureFolders.get(i).getFolderName());
        }

        ArrayAdapter seletorAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, folders);
        folderSelector.setAdapter(seletorAdapter);

        folderSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(folders.get(position).equals("*All*")){
                    allPhotos = MediaFacer
                            .withPictureContex(pictureActivity.this)
                            .getAllPictureContents(PictureGet.externalContentUri);
                    Toast.makeText(pictureActivity.this,String.valueOf(allPhotos.size()),Toast.LENGTH_LONG).show();
                    setUpAndDisplayPictures();
                }else {
                    allPhotos = pictureFolders.get(position).getPhotos();
                   /* allPhotos = MediaFacer
                            .withPictureContex(pictureActivity.this)
                            .getAllPictureContentByBucket_id(pictureFolders.get(position).getBucket_id());*/
                    Toast.makeText(pictureActivity.this,String.valueOf(allPhotos.size()),Toast.LENGTH_LONG).show();
                    setUpAndDisplayPictures();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
