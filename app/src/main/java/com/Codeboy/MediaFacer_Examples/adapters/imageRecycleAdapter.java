package com.Codeboy.MediaFacer_Examples.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Codeboy.MediaFacer.mediaHolders.pictureContent;
import com.Codeboy.MediaFacer_Examples.R;
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
        View itemView = inflater.inflate(R.layout.picture_item,parent,false);
        return new pictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull pictureViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    interface pictureActionListrener{
        void onPictureItemClicked(int position);
        void onPictureItemLongClicked(int position);
    }

    class pictureViewHolder extends RecyclerView.ViewHolder {
        //define vies

        pictureViewHolder(@NonNull View itemView) {
            super(itemView);
            //instantiate views
        }

        void Bind(){

        }

    }

}
