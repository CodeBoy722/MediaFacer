package com.Codeboy.MediaFacer_Examples.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Codeboy.MediaFacer.mediaHolders.videoContent;
import com.Codeboy.MediaFacer_Examples.R;
import java.util.ArrayList;

public class videoRecycleAdapter extends RecyclerView.Adapter<videoRecycleAdapter.videoViewHolder>{

    private Context videoActivity;
    private ArrayList<videoContent> videoList;
    private videoActionListener actionListener;

    public videoRecycleAdapter(Context context,ArrayList<videoContent> videoList,videoActionListener actionListener){
        this.videoActivity = context;
        this.videoList = videoList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public videoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(videoActivity);
        View itemView = inflater.inflate(R.layout.video_item,parent,false);
        return new videoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull videoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    interface videoActionListener{
        void onVideoItemClicked(int position);
        void onVideoItemLongClicked(int position);
    }

    class videoViewHolder extends RecyclerView.ViewHolder {
        //define views

        videoViewHolder(@NonNull View itemView) {
            super(itemView);
            //instantiate views

        }
    }

}


