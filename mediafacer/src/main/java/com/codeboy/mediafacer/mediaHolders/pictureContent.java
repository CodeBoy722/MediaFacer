package com.codeboy.mediafacer.mediaHolders;

public class pictureContent {

    private String pictureName;
    private String picturePath;
    private  Long pictureSize;
    private  String photoUri;
    private  int pictureId;

    public pictureContent() { }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Long getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(Long pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
