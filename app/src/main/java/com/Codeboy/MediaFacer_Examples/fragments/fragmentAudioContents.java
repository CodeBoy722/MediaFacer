package com.Codeboy.MediaFacer_Examples.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Codeboy.MediaFacer.AudioGet;
import com.Codeboy.MediaFacer.MediaFacer;
import com.Codeboy.MediaFacer.mediaHolders.audioContent;

import java.util.ArrayList;

public class fragmentAudioContents extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       ArrayList<audioContent> audioContents = MediaFacer.withAudioContex(getActivity()).getAllAudioContent(AudioGet.externalContentUri);


    }

}
