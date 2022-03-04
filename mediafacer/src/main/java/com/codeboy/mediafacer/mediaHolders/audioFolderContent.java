package com.codeboy.mediafacer.mediaHolders;

import java.util.ArrayList;

public class audioFolderContent {

    private ArrayList<audioContent> audioFiles;
    private String folderName;
    private String folderPath;
    private int bucket_id;

    public audioFolderContent(){
        audioFiles = new ArrayList<>();
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

    public int getNumberOfSongs() {
        return audioFiles.size();
    }

    public int getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(int bucket_id) {
        this.bucket_id = bucket_id;
    }
}
