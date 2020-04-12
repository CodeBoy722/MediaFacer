package com.CodeBoy.MediaFacer_Examples.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CodeBoy.MediaFacer.mediaHolders.audioArtistContent;
import com.CodeBoy.MediaFacer_Examples.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class audioArtistsAdapter extends RecyclerView.Adapter<audioArtistsAdapter.artistViewHolder> {

    private Context musicActivity;
    private ArrayList<audioArtistContent> artistlist;
    private artistListener actionListerner;

    public audioArtistsAdapter(Context contx, ArrayList<audioArtistContent> allArtists, artistListener Listerner){
        this.musicActivity = contx;
        this.artistlist = allArtists;
        this.actionListerner = Listerner;
    }

    @NonNull
    @Override
    public artistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(musicActivity);
        View itemView = inflater.inflate(R.layout.artist_item,null,false);
        return new artistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull artistViewHolder holder, int position) {
        holder.setItemPosition(position);
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return artistlist.size();
    }

    public interface artistListener{
        void onArtistCliced(audioArtistContent artistContent);
    }

    class artistViewHolder extends RecyclerView.ViewHolder {
        //define items
        TextView artistName;
        TextView num_of_songs;
        ImageView artist_art;
        int itemPosition;

        artistViewHolder(@NonNull View itemView) {
            super(itemView);
            //instantiate items
            artistName = itemView.findViewById(R.id.artist_name);
            num_of_songs = itemView.findViewById(R.id.num_of_songs);
            artist_art = itemView.findViewById(R.id.artist_art);
        }

        void setItemPosition(int position){
            itemPosition = position;
        }

        void bind(){
            artistName.setText(artistlist.get(itemPosition).getArtistName());
            int num_songs = artistlist.get(itemPosition).getNumOfSongs();
            String songs = "";
            if(num_songs > 1){
                songs = num_songs+" SONGS";
                num_of_songs.setText(songs);
            }else {
                songs = num_songs+" SONG";
                num_of_songs.setText(songs);
            }

            Glide.with(musicActivity)
                    .load(artistlist.get(itemPosition).getAlbums().get(0).getAlbumArtUri())
                    .apply(new RequestOptions().placeholder(R.drawable.tile_logo).centerCrop()).circleCrop()
                    .into(artist_art);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionListerner.onArtistCliced(artistlist.get(itemPosition));
                }
            });

        }

    }



}
