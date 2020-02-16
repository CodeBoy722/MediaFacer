package com.Codeboy.MediaFacer_Examples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.Codeboy.MediaFacer.MediaFacer;
import com.Codeboy.MediaFacer.VideoGet;
import com.Codeboy.MediaFacer.mediaHolders.videoContent;
import com.Codeboy.MediaFacer.mediaHolders.videoFolderContent;
import com.Codeboy.MediaFacer_Examples.adapters.videoRecycleAdapter;

import java.util.ArrayList;

public class videoActivity extends AppCompatActivity {

    ArrayList<videoContent> allVideos;
    RecyclerView video_recycler;
    Spinner folder_selector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        video_recycler = findViewById(R.id.video_recycler);
        video_recycler.hasFixedSize();
        video_recycler.setHasFixedSize(true);
        video_recycler.setItemViewCacheSize(20);
        video_recycler.setDrawingCacheEnabled(true);
        video_recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        int numOfColumns = calculateNoOfColumns(this,120);
        video_recycler.setLayoutManager(new GridLayoutManager(this,numOfColumns));

        allVideos = new ArrayList<>();
        setupFolderSelector();
    }

    int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    private void setupFolderSelector(){

        folder_selector = findViewById(R.id.video_folder_selector);

        final ArrayList<videoFolderContent> videoFolders = new ArrayList<>();
        videoFolders.add(new videoFolderContent("all","*All*"));
        videoFolders.addAll(MediaFacer.withVideoContex(this).getVidioPaths(VideoGet.externalContentUri));

        final ArrayList<String> folders = new ArrayList<>();
        for(int i = 0;i < videoFolders.size();i++){
            folders.add(videoFolders.get(i).getFolderName());
        }

        ArrayAdapter seletorAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, folders);
        folder_selector.setAdapter(seletorAdapter);

        folder_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(folders.get(position).equals("*All*")){
                    allVideos = MediaFacer
                            .withVideoContex(videoActivity.this)
                            .getAllVideoContent(VideoGet.externalContentUri);
                    Toast.makeText(videoActivity.this,String.valueOf(allVideos.size()),Toast.LENGTH_LONG).show();
                    setuUpAndDisplayVideos();
                }else {
                    allVideos = MediaFacer
                            .withVideoContex(videoActivity.this)
                            .getAllVideoContentInFolder(videoFolders.get(position).getFolderPath());
                    Toast.makeText(videoActivity.this,String.valueOf(allVideos.size()),Toast.LENGTH_LONG).show();
                    setuUpAndDisplayVideos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setuUpAndDisplayVideos(){

        videoRecycleAdapter.videoActionListener actionListener = new videoRecycleAdapter.videoActionListener() {
            @Override
            public void onVideoItemClicked(int position) {
                //show picture information
                playVideo(allVideos.get(position),position);
            }

            @Override
            public void onVideoItemLongClicked(int position) {
                //show picture in fragment
                showVideoInfo(allVideos.get(position));
            }
        };

        videoRecycleAdapter videoAdapter = new videoRecycleAdapter(this,allVideos,actionListener);
        video_recycler.setAdapter(videoAdapter);

    }


    private void playVideo(videoContent video,int position){

    }

    private void showVideoInfo(videoContent video){


    }

}
