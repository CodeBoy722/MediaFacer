package com.codeboy.mediafacer.mediaHolders;

import java.util.ArrayList;

public class audioGenreContents {

    private ArrayList<audioContent> audioFiles;
    private String genreName;
    private String genreId;

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
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public void addGenreMusic(audioContent music){
        audioFiles.add(music);
    }

}
