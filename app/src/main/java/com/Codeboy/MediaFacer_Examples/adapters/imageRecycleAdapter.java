package com.Codeboy.MediaFacer_Examples.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Codeboy.MediaFacer.mediaHolders.pictureContent;
import com.Codeboy.MediaFacer_Examples.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class imageRecycleAdapter extends RecyclerView.Adapter<imageRecycleAdapter.pictureViewHolder>{

    private Context pictureActivity;
    private ArrayList<pictureContent> pictureList;
    private pictureActionListrener actionListener;

    public imageRecycleAdapter(Context context,ArrayList<pictureContent> pictureList,pictureActionListrener actionListener){
        this.pictureActivity = context;
        this.pictureList = pictureList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public pictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(pictureActivity);
        View itemView = inflater.inflate(R.layout.picture_item,null,false);
        return new pictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull pictureViewHolder holder, int position) {
        holder.setPosition(position);
        holder.Bind();
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public interface pictureActionListrener{
        void onPictureItemClicked(int position);
        void onPictureItemLongClicked(int position);
    }

    class pictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //define vies
        ImageView picture;
        int position;
        pictureViewHolder(@NonNull View itemView) {
            super(itemView);
            //instantiate views
            picture = itemView.findViewById(R.id.picture);
            picture.setOnClickListener(this);
            picture.setOnLongClickListener(this);
        }

        void setPosition(int position) {
            this.position = position;
        }

        void Bind(){
            pictureContent pic = pictureList.get(position);
            Glide.with(pictureActivity)
                    .load(Uri.parse(pic.getAssertFileStringUri()))
                    .apply(new RequestOptions().centerCrop())
                    .into(picture);
        }

        @Override
        public void onClick(View v) {
            actionListener.onPictureItemClicked(position);
        }

        @Override
        public boolean onLongClick(View v) {
            actionListener.onPictureItemLongClicked(position);
            return false;
        }
    }

}
