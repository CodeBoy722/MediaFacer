package com.CodeBoy.MediaFacer.mediaHolders;

import java.util.ArrayList;

public class pictureFolderContent {

    private String folderpath;
    private String folderName;
    private ArrayList<pictureContent> photos;
    private int bucket_id;

    public pictureFolderContent(){
        photos = new ArrayList<>();
    }

    public pictureFolderContent(String path, String folderName) {
        this.folderpath = path;
        this.folderName = folderName;
        photos = new ArrayList<>();
    }

    public String getFolderPath() {
        return folderpath;
    }

    public void setFolderPath(String path) {
        this.folderpath = path;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public ArrayList<pictureContent> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<pictureContent> photos) {
        this.photos = photos;
    }

    public int getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(int bucket_id) {
        this.bucket_id = bucket_id;
    }
}
