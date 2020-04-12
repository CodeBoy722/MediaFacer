package com.CodeBoy.MediaFacer.mediaHolders;

import java.util.ArrayList;

public class pictureFolderContent {

    private String path;
    private String folderName;
    private ArrayList<pictureContent> photos;

    public pictureFolderContent(){

    }

    public pictureFolderContent(String path, String folderName, ArrayList<pictureContent> pics) {
        this.path = path;
        this.folderName = folderName;
        this.photos = pics;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

}
