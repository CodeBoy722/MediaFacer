package com.CodeBoy.MediaFacer.mediaHolders;

import java.util.ArrayList;

public class audioGenreContents {

    private ArrayList<audioContent> audioFiles;
    private String GenreName;
    private String GenreId;

    public audioGenreContents(){
        audioFiles = new ArrayList<>();
    }

    public ArrayList<audioContent> getAudioFiles() {
        return audioFiles;
    }

    public void setAudioFiles(ArrayList<audioContent> audioFiles) {
        this.audioFiles = audioFiles;
    }

    public String getGenreName() {
        return GenreName;
    }

    public void setGenreName(String genreName) {
        GenreName = genreName;
    }

    public String getGenreId() {
        return GenreId;
    }

    public void setGenreId(String genreId) {
        GenreId = genreId;
    }

    public void addGenreMusic(audioContent music){
        audioFiles.add(music);
    }

}
