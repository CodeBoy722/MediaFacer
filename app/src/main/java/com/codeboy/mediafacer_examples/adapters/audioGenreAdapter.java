package com.codeboy.mediafacer_examples.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codeboy.mediafacer.mediaHolders.audioGenreContents;
import com.codeboy.mediafacer_examples.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class audioGenreAdapter extends RecyclerView.Adapter<audioGenreAdapter.GenreNamesViewHolder>{

    Context GenreContext;
    ArrayList<audioGenreContents> allGenres;
    GenreActionlistener listener;
    int selectedIndex;
    boolean isSelectionActive;

    public audioGenreAdapter(Context genreContext, ArrayList<audioGenreContents> allGenres, GenreActionlistener listener) {
        GenreContext = genreContext;
        this.allGenres = allGenres;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public GenreNamesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(GenreContext);
        View itemview =  inflater.inflate(R.layout.layout_genre_name_item,null,false);
        return new GenreNamesViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull audioGenreAdapter.GenreNamesViewHolder holder, int position) {
        holder.setItemPosition(position);
        holder.Bind();
    }

    @Override
    public int getItemCount() {
        return allGenres.size();
    }

    public interface GenreActionlistener{
        void OnGenreClicked(int position);
    }

    class  GenreNamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView genreCard;
        TextView genreName;
        int ItemPosition;
        public GenreNamesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            genreCard = itemView.findViewById(R.id.genre_card);
            genreName = itemView.findViewById(R.id.genre_name);
            itemView.setOnClickListener(this);
        }

        public void setItemPosition(int itemPosition) {
            ItemPosition = itemPosition;
        }

        void Bind(){
            genreName.setText(allGenres.get(ItemPosition).getGenreName());
            if(ItemPosition == selectedIndex && isSelectionActive){
                genreCard.setCardBackgroundColor(GenreContext.getResources().getColor(R.color.white));
                genreName.setTextColor(GenreContext.getResources().getColor(R.color.colorAccent));
            }
        }

        @Override
        public void onClick(View v) {
            selectedIndex = ItemPosition;
            if(!isSelectionActive){
                isSelectionActive = true;
            }
            listener.OnGenreClicked(ItemPosition);
            notifyItemRangeChanged(0,allGenres.size());
        }
    }

}
