package com.Codeboy.MediaFacer_Examples.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Codeboy.MediaFacer.mediaHolders.videoContent;
import com.Codeboy.MediaFacer_Examples.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
        View itemView = inflater.inflate(R.layout.video_item,null,false);
        return new videoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull videoViewHolder holder, int position) {
        holder.setPosition(position);
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public interface videoActionListener{
        void onVideoItemClicked(int position);
        void onVideoItemLongClicked(int position);
    }

    class videoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //define views
        ImageView preview;
        ImageButton play;
        int position;

        videoViewHolder(@NonNull View itemView) {
            super(itemView);
            //instantiate views
            preview = itemView.findViewById(R.id.video_preview);
            play  = itemView.findViewById(R.id.play);
            preview.setOnLongClickListener(this);
            play.setOnClickListener(this);
        }

        void setPosition(int position) {
            this.position = position;

        }

        void bind(){
            videoContent video = videoList.get(position);
            Glide.with(videoActivity)
                    .load(video.getPath())
                    .apply(new RequestOptions().centerCrop())
                    .into(preview);
        }

        @Override
        public void onClick(View view) {
            actionListener.onVideoItemClicked(position);
        }

        @Override
        public boolean onLongClick(View view) {
            actionListener.onVideoItemLongClicked(position);
            return false;
        }
    }

}


