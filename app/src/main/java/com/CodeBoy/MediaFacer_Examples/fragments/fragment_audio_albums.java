package com.CodeBoy.MediaFacer_Examples.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CodeBoy.MediaFacer.AudioGet;
import com.CodeBoy.MediaFacer.MediaFacer;
import com.CodeBoy.MediaFacer.mediaHolders.audioAlbumContent;
import com.CodeBoy.MediaFacer_Examples.R;
import com.CodeBoy.MediaFacer_Examples.adapters.audioAlbumsAdapter;

import java.util.ArrayList;

public class fragment_audio_albums extends Fragment {

    private ArrayList<audioAlbumContent>  allAlbums;
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
        int numOfColumns = calculateNoOfColumns(getActivity(),140);
        data_recycler.setLayoutManager(new GridLayoutManager(getActivity(),numOfColumns));
    }

    private int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    private void showAlbumAudios(audioAlbumContent album){
        fragment_audioDataDisplay audioDataDisplay = new fragment_audioDataDisplay();
        audioDataDisplay.setType("Album",album.getAlbumName(),album.getAudioContents());

        Transition transition = TransitionInflater.from(getContext()).
                inflateTransition(android.R.transition.explode);

        setEnterTransition(transition);
        setExitTransition(transition);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder,audioDataDisplay)
                .addToBackStack(null)
                .commit();

    }

    public void firstLoad(){
        if(allAlbums == null){
            AsyncTask<Object,Integer,ArrayList<audioAlbumContent>> asyncTask = new getAudioAlbumsTask();
            asyncTask.execute();
        }
    }

    private class getAudioAlbumsTask extends AsyncTask<Object,Integer, ArrayList<audioAlbumContent>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<audioAlbumContent> doInBackground(Object... objects) {
            allAlbums = MediaFacer.withAudioContex(getActivity()).getAllAlbums(AudioGet.externalContentUri);
            return allAlbums;
        }

        @Override
        protected void onPostExecute(ArrayList<audioAlbumContent> audioAlbumContents) {
            super.onPostExecute(audioAlbumContents);
            audioAlbumsAdapter.albumListener listener = new audioAlbumsAdapter.albumListener() {
                @Override
                public void onAlbumClicked(audioAlbumContent album) {
                    showAlbumAudios(album);
                }
            };
            audioAlbumsAdapter adapter = new audioAlbumsAdapter(getActivity(),audioAlbumContents,listener);
            data_recycler.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

    }


}
