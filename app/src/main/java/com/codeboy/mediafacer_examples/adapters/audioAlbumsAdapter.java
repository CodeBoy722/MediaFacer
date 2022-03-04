package com.codeboy.mediafacer_examples.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeboy.mediafacer.mediaHolders.audioAlbumContent;
import com.codeboy.mediafacer_examples.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class audioAlbumsAdapter extends RecyclerView.Adapter<audioAlbumsAdapter.audioAlbumViewHolder> {

    private Context musicActivity;
    private ArrayList<audioAlbumContent> albumlist;
    private albumListener Listerner;

    public audioAlbumsAdapter(Context context, ArrayList<audioAlbumContent> musiclist, albumListener actionListerner){
        this.musicActivity = context;
        this.albumlist = musiclist;
        this.Listerner = actionListerner;
    }

    @NonNull
    @Override
    public audioAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(musicActivity);
        View itemview =  inflater.inflate(R.layout.album_item,null,false);
        return new audioAlbumViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull audioAlbumViewHolder holder, int position) {
        holder.setItemPosition(position);
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return albumlist.size();
    }

   public interface albumListener{
        void onAlbumClicked(audioAlbumContent album);
    }

     class audioAlbumViewHolder extends RecyclerView.ViewHolder {
        //define items
        ImageView albumArt;
        TextView albumname;
        int itemPosition;
        audioAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            //instantiate items
            albumArt = itemView.findViewById(R.id.album_art);
            albumname = itemView.findViewById(R.id.album_name);
        }

        void setItemPosition(int position){
            itemPosition = position;
        }

        void bind(){
            albumname.setText(albumlist.get(itemPosition).getAlbumName());
            Glide.with(musicActivity)
                    .load(albumlist.get(itemPosition).getAlbumArtUri())
                    .apply(new RequestOptions().placeholder(R.drawable.tile_logo).centerCrop()).circleCrop()
                    .into(albumArt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listerner.onAlbumClicked(albumlist.get(itemPosition));
                }
            });
        }
    }

}
