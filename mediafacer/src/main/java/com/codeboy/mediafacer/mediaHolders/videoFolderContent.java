package com.codeboy.mediafacer.mediaHolders;

import java.util.ArrayList;

public class videoFolderContent {

    private ArrayList<videoContent> videoFiles;
    private String folderName;
    private String folderPath;
    private int bucket_id;

    public videoFolderContent(){
        videoFiles = new ArrayList<>();
    }

    public videoFolderContent(String folderPath,String folderName) {
        this.folderName = folderName;
        this.folderPath = folderPath;
        videoFiles = new ArrayList<>();
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

    public int getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(int bucket_id) {
        this.bucket_id = bucket_id;
    }
}
