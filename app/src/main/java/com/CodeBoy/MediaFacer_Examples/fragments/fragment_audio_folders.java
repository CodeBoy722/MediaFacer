package com.CodeBoy.MediaFacer_Examples.fragments;

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

import com.CodeBoy.MediaFacer.MediaFacer;
import com.CodeBoy.MediaFacer.mediaHolders.audioFolderContent;
import com.CodeBoy.MediaFacer_Examples.R;
import com.CodeBoy.MediaFacer_Examples.adapters.audioBucketAdapter;

import java.util.ArrayList;

public class fragment_audio_folders extends Fragment {

    private RecyclerView data_recycler;
    private ArrayList<audioFolderContent> buckets;
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

    private void showAudioFolder(audioFolderContent folderContent){
        fragment_audioDataDisplay audioDataDisplay = new fragment_audioDataDisplay();
        audioDataDisplay.setType("Folder",folderContent.getFolderName(),folderContent.getMusicFiles());
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
        if(buckets == null){
            AsyncTask<Object,Integer,ArrayList<audioFolderContent>> asyncTask = new getAudioFoldersTask();
            asyncTask.execute();
        }
    }

    private class getAudioFoldersTask extends AsyncTask<Object,Integer, ArrayList<audioFolderContent>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<audioFolderContent> doInBackground(Object... objects) {
            buckets = MediaFacer.withAudioContex(getActivity()).getAllAudioFolderContent();
            return buckets;
        }

        @Override
        protected void onPostExecute(ArrayList<audioFolderContent> audioFolderContents) {
            super.onPostExecute(audioFolderContents);
            audioBucketAdapter.folderListener listener = new audioBucketAdapter.folderListener() {
                @Override
                public void onFolderClicked(audioFolderContent folderContent) {
                    showAudioFolder(folderContent);
                }
            };
            audioBucketAdapter adapter = new audioBucketAdapter(getActivity(),audioFolderContents,listener);
            data_recycler.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

    }

}
