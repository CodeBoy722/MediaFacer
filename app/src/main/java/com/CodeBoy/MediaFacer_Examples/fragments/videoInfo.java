package com.CodeBoy.MediaFacer_Examples.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.CodeBoy.MediaFacer.MediaDataCalculator;
import com.CodeBoy.MediaFacer.mediaHolders.videoContent;
import com.CodeBoy.MediaFacer_Examples.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;

public class videoInfo extends DialogFragment {

    private videoContent video;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.content_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView filename = view.findViewById(R.id.filename);
        TextView filepath = view.findViewById(R.id.fullpath);
        TextView size = view.findViewById(R.id.size);
        ImageView pictureView = view.findViewById(R.id.pic);

        filename.setText(video.getVideoName());
        filepath.setText(video.getPath());
        size.setText(MediaDataCalculator.convertBytes(video.getVideoSize()));

        Glide.with(getActivity())
                .load(Uri.parse(video.getAssetFileStringUri()))
                .apply(new RequestOptions().placeholder(R.drawable.tile_logo).centerCrop())
                .into(pictureView);
    }

    public void setVideo(videoContent video){
        this.video = video;
    }

}
