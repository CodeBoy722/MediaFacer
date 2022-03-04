package com.codeboy.mediafacer.mediaHolders;

import java.util.ArrayList;

public class pictureFolderContent {

    private String folderPath;
    private String folderName;
    private ArrayList<pictureContent> photos;
    private int bucket_id;

    public pictureFolderContent(){
        photos = new ArrayList<>();
    }

    public pictureFolderContent(String path, String folderName) {
        this.folderPath = path;
        this.folderName = folderName;
        photos = new ArrayList<>();
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String path) {
        this.folderPath = path;
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
