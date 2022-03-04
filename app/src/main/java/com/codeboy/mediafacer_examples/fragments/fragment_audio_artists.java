package com.codeboy.mediafacer_examples.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
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

import com.codeboy.mediafacer.AudioGet;
import com.codeboy.mediafacer.MediaFacer;
import com.codeboy.mediafacer.mediaHolders.audioArtistContent;
import com.codeboy.mediafacer.mediaHolders.audioContent;
import com.codeboy.mediafacer_examples.R;
import com.codeboy.mediafacer_examples.adapters.audioArtistsAdapter;

import java.util.ArrayList;

public class fragment_audio_artists extends Fragment {

    private RecyclerView data_recycler;
    private ArrayList<audioArtistContent> allArtists;
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

    }

    private void showArtistMusic(audioArtistContent artistContent){
        fragment_audioDataDisplay audioDataDisplay = new fragment_audioDataDisplay();
        ArrayList<audioContent> audioContents = new ArrayList<>();
        for(int i = 0;i < artistContent.getAlbums().size();i++){
            audioContents.addAll(artistContent.getAlbums().get(i).getAudioContents());
        }

        audioDataDisplay.setType("Artist",artistContent.getArtistName(),audioContents);

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
        if(allArtists == null){
            AsyncTask<Object,Integer,ArrayList<audioArtistContent>> asyncTask = new getAudioArtistTask();
            asyncTask.execute();
        }
    }

    private class getAudioArtistTask extends AsyncTask<Object,Integer, ArrayList<audioArtistContent>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected ArrayList<audioArtistContent> doInBackground(Object... objects) {
            ArrayList<String> ids = MediaFacer.withAudioContex(getActivity()).getAllArtistIds(AudioGet.externalContentUri);
            allArtists = MediaFacer.withAudioContex(getActivity()).getAllArtists(ids,AudioGet.externalContentUri);
            return allArtists;
        }

        @Override
        protected void onPostExecute(ArrayList<audioArtistContent> audioArtistContents) {
            super.onPostExecute(audioArtistContents);
            audioArtistsAdapter.artistListener listener  = new audioArtistsAdapter.artistListener() {
                @Override
                public void onArtistCliced(audioArtistContent artistContent) {
                    showArtistMusic(artistContent);
                }
            };

            audioArtistsAdapter adapter = new audioArtistsAdapter(getActivity(),audioArtistContents,listener);
            data_recycler.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

    }

}
