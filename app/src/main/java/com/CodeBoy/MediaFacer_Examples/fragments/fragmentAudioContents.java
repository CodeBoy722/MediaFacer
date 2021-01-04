package com.CodeBoy.MediaFacer_Examples.fragments;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CodeBoy.MediaFacer.AudioGet;
import com.CodeBoy.MediaFacer.MediaFacer;
import com.CodeBoy.MediaFacer.mediaHolders.audioContent;
import com.CodeBoy.MediaFacer_Examples.R;
import com.CodeBoy.MediaFacer_Examples.adapters.audioRecycleAdapter;

import java.util.ArrayList;

public class fragmentAudioContents extends Fragment {

    private MediaPlayer player;
    private ArrayList<audioContent> audioContents;
    private RecyclerView data_recycler;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        TextView title = view.findViewById(R.id.title);
        title.setVisibility(View.GONE);

        data_recycler = view.findViewById(R.id.data_recycler);
        data_recycler.setHasFixedSize(true);
        data_recycler.setItemViewCacheSize(20);
        data_recycler.setDrawingCacheEnabled(true);
        data_recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        data_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadAudioData();
    }

    private void loadAudioData(){
        if(audioContents == null){
            AsyncTask<Object,Integer,ArrayList<audioContent>> asyncTask = new getAudioTask();
            asyncTask.execute();
        }
    }

    private void playAudio(audioContent audio){
        Uri content = Uri.parse(audio.getAssetFileStringUri());
        if(player == null){
            player = new MediaPlayer();
            try {
                AssetFileDescriptor file = getActivity().getContentResolver().openAssetFileDescriptor(content, "r");
                assert file != null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    player.setDataSource(file);
                }else {
                    player.setDataSource(audio.getFilePath());
                }
                player.setLooping(false);
                player.prepare();
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            player.release();
            player = new MediaPlayer();
            try {
                AssetFileDescriptor file = getActivity().getContentResolver().openAssetFileDescriptor(content, "r");
                assert file != null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    player.setDataSource(file);
                }else {
                    player.setDataSource(audio.getFilePath());
                }
                player.setLooping(false);
                player.prepare();
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAudioInfo(int position){
        audioInfo audio = new audioInfo();
        audio.setAudio(audioContents.get(position));
        audio.show(getActivity().getSupportFragmentManager(),"audio_info");
    }

    private class getAudioTask extends AsyncTask<Object,Integer, ArrayList<audioContent>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<audioContent> doInBackground(Object... objects) {
            audioContents = MediaFacer.withAudioContex(getActivity()).getAllAudioContent(AudioGet.externalContentUri);
            return audioContents;
        }

        @Override
        protected void onPostExecute(ArrayList<audioContent> audioContents) {
            super.onPostExecute(audioContents);
            audioRecycleAdapter.musicActionListerner listerner = new audioRecycleAdapter.musicActionListerner() {
                @Override
                public void onMusicItemClicked(int position,audioContent audio) {
                    playAudio(audio);
                }

                @Override
                public void onMusicItemLongClicked(int position, audioContent audio) {
                    showAudioInfo(position);
                }
            };
            audioRecycleAdapter allaudio = new audioRecycleAdapter(getActivity(),audioContents,listerner);
            data_recycler.setAdapter(allaudio);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(player != null){
            player.release();
            player = null;
        }
    }
}
