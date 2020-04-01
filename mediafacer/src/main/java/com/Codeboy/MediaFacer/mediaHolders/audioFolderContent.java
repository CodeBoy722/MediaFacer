package com.Codeboy.MediaFacer.mediaHolders;

import android.net.Uri;

import java.util.ArrayList;

public class audioFolderContent {

    private ArrayList<audioContent> audioFiles = new ArrayList<>();
    private String folderName;
    private String folderPath;
    private Uri folderCover;
    private int numberOfSongs;

    public audioFolderContent(){

    }

    public audioFolderContent(String folderName, String folderPath) {
        this.folderName = folderName;
        this.folderPath = folderPath;
    }

    public ArrayList<audioContent> getMusicFiles() {
        return audioFiles;
    }

    public void setMusicFiles(ArrayList<audioContent> audioFiles) {
        this.audioFiles = audioFiles;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public Uri getFolderCover() {
        return folderCover;
    }

    public void setFolderCover(Uri folderCover) {
        this.folderCover = folderCover;
    }

    public int getNumberOfSongs() {
        return audioFiles.size();
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }
}
