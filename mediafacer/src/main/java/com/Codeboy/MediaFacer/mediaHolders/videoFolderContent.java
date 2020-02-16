package com.Codeboy.MediaFacer.mediaHolders;

import android.net.Uri;

import java.util.ArrayList;

public class videoFolderContent {

    private ArrayList<videoContent> videoFiles = new ArrayList<>();
    private String folderName;
    private String folderPath;
    private Uri folderCover;
    private int numberOfSongs;

    public videoFolderContent(){

    }

    public videoFolderContent(String folderPath,String folderName) {
        this.folderName = folderName;
        this.folderPath = folderPath;
    }


    public ArrayList<videoContent> getVideoFiles() {
        return videoFiles;
    }

    public void setVideoFiles(ArrayList<videoContent> videoFiles) {
        this.videoFiles = videoFiles;
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
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }
}
