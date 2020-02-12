package com.Codeboy.MediaFacer_Examples.fragments;

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

import com.Codeboy.MediaFacer.mediaDataCalculator;
import com.Codeboy.MediaFacer.mediaHolders.pictureContent;
import com.Codeboy.MediaFacer_Examples.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class pictureInfo extends DialogFragment {

    private pictureContent picture;

    public pictureContent getPicture() {
        return picture;
    }

    public void setPicture(pictureContent picture) {
        this.picture = picture;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.picture_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView filename = view.findViewById(R.id.filename);
        TextView filepath = view.findViewById(R.id.fullpath);
        TextView size = view.findViewById(R.id.size);
        TextView date_added = view.findViewById(R.id.date_added);
        TextView last_modified = view.findViewById(R.id.last_modified);
        ImageView pictureView = view.findViewById(R.id.pic);

        filename.setText(picture.getPicturName());
        filepath.setText(picture.getPicturePath());
        size.setText(mediaDataCalculator.convertBytes(picture.getPictureSize()));
        date_added.setText(picture.getDate_added());
        last_modified.setText(picture.getDate_modified());

        Glide.with(getActivity())
                .load(picture.getPicturePath())
                .apply(new RequestOptions().placeholder(R.drawable.ic_mediafacer).centerCrop())
                .into(pictureView);

    }
}
