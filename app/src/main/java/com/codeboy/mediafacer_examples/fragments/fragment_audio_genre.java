package com.codeboy.mediafacer_examples.fragments;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeboy.mediafacer.AudioGet;
import com.codeboy.mediafacer.MediaFacer;
import com.codeboy.mediafacer.mediaHolders.audioContent;
import com.codeboy.mediafacer.mediaHolders.audioGenreContents;
import com.codeboy.mediafacer_examples.R;
import com.codeboy.mediafacer_examples.adapters.audioGenreAdapter;
import com.codeboy.mediafacer_examples.adapters.audioRecycleAdapter;

import java.util.ArrayList;

public class fragment_audio_genre extends Fragment {

    RecyclerView GenreNames, GenreMusic;ProgressBar progressBar;
    ArrayList<audioGenreContents> AllGenres; private MediaPlayer player;
    audioGenreAdapter GenreAdapter; audioRecycleAdapter GenreMusicAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_genre,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
    }

    private void InitViews(View parentView){
        GenreNames = parentView.findViewById(R.id.genre_names);
        GenreMusic = parentView.findViewById(R.id.genre_music);
        progressBar = parentView.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        GenreNames.setHasFixedSize(true);
        GenreNames.setItemViewCacheSize(20);
        GenreNames.setDrawingCacheEnabled(true);
        GenreNames.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        GenreNames.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        GenreMusic.setHasFixedSize(true);
        GenreMusic.setItemViewCacheSize(20);
        GenreMusic.setDrawingCacheEnabled(true);
        GenreMusic.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        GenreMusic.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void LoadGenreData(){
        audioGenreAdapter.GenreActionlistener actionListener = new audioGenreAdapter.GenreActionlistener() {
            @Override
            public void OnGenreClicked(int position) {
                GenreMusicAdapter = new audioRecycleAdapter(getContext(), AllGenres.get(position).getAudioFiles(), new audioRecycleAdapter.musicActionListerner() {
                    @Override
                    public void onMusicItemClicked(int position, audioContent audio) {
                        playAudio(audio);
                    }

                    @Override
                    public void onMusicItemLongClicked(int position, audioContent audio) {
                        showAudioInfo(audio);
                    }
                });

                GenreMusic.setAdapter(GenreMusicAdapter);
            }
        };
        GenreAdapter = new audioGenreAdapter(getActivity(),AllGenres, actionListener);
        GenreNames.setAdapter(GenreAdapter);
    }

    private void playAudio(audioContent audio){
        Uri content = Uri.parse(audio.getMusicUri());
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

    private void showAudioInfo(audioContent audioX){
        audioInfo audio = new audioInfo();
        audio.setAudio(audioX);
        audio.show(getActivity().getSupportFragmentManager(),"audio_info");
    }

    public void firstLoad(){
        if(AllGenres == null){
            progressBar.setVisibility(View.VISIBLE);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    AllGenres = MediaFacer.withAudioContex(getActivity()).getAudioContentsByGenre(AudioGet.externalContentUri);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            LoadGenreData();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }; new Thread(run).start();
        }
    }

}
