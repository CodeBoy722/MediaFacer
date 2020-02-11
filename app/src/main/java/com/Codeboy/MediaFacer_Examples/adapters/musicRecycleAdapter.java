package com.Codeboy.MediaFacer_Examples.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Codeboy.MediaFacer.mediaHolders.audioContent;
import com.Codeboy.MediaFacer_Examples.R;
import java.util.ArrayList;

public class musicRecycleAdapter extends RecyclerView.Adapter<musicRecycleAdapter.musicViewHolder>{

    private Context musicActivity;
    private ArrayList<audioContent> musiclist;
    private musicActionListerner actionListerner;

    public musicRecycleAdapter(Context context,ArrayList<audioContent> musiclist,musicActionListerner actionListerner){
        this.musicActivity = context;
        this.musiclist = musiclist;
        this.actionListerner = actionListerner;
    }

    @NonNull
    @Override
    public musicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(musicActivity);
        View itemView = inflater.inflate(R.layout.audio_item,parent,false);
        return new musicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull musicViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return musiclist.size();
    }

    interface musicActionListerner{
        void onMusicItemClicked(int position);
        void onMusicItemLongClicked(int position);
    }

    class musicViewHolder extends RecyclerView.ViewHolder {
         //define views

         musicViewHolder(@NonNull View itemView) {
            super(itemView);
             //instantiate views
        }
    }

}