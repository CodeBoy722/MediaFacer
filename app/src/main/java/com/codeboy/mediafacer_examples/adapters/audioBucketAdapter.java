package com.codeboy.mediafacer_examples.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeboy.mediafacer.mediaHolders.audioFolderContent;
import com.codeboy.mediafacer_examples.R;

import java.util.ArrayList;

public class audioBucketAdapter extends RecyclerView.Adapter<audioBucketAdapter.bucketViewHolder>{

    private Context musicActivity;
    private ArrayList<audioFolderContent> folderList;
    private folderListener listerner;


    public audioBucketAdapter(Context context, ArrayList<audioFolderContent> musiclist, folderListener actionListerner){
        this.musicActivity = context;
        this.folderList = musiclist;
        this.listerner = actionListerner;
    }

    @NonNull
    @Override
    public bucketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(musicActivity);
        View itemView = inflater.inflate(R.layout.bucket_item,null,false);
        return new bucketViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull bucketViewHolder holder, int position) {
        holder.setItemPosition(position);
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public interface folderListener{
        void onFolderClicked(audioFolderContent folderContent);
    }

    class bucketViewHolder extends RecyclerView.ViewHolder {
        // define views
        TextView folderName;
        TextView num_of_songs;
        int itemPosition;

        bucketViewHolder(@NonNull View itemView) {
            super(itemView);
            // instantiate views
            folderName = itemView.findViewById(R.id.folder_name);
            num_of_songs = itemView.findViewById(R.id.num_of_songs);
        }

        void setItemPosition(int position){
            this.itemPosition = position;
        }

        void bind(){
            folderName.setText(folderList.get(itemPosition).getFolderName());

            int num_songs = folderList.get(itemPosition).getNumberOfSongs();
            String songs = "";
            if(num_songs > 1){
                songs = num_songs+" SONGS";
                num_of_songs.setText(songs);
            }else {
                songs = num_songs+" SONG";
                num_of_songs.setText(songs);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listerner.onFolderClicked(folderList.get(itemPosition));
                }
            });

        }
    }

}
