package com.CodeBoy.MediaFacer.mediaHolders;

public class pictureContent {

    private String picturName;
    private String picturePath;
    private  Long pictureSize;
    private  String assertFileStringUri;
    private  int pictureId;
    private  int date_added;
    private  int date_modified;
    private long date_taken;

    public pictureContent() {
    }

    public pictureContent(String picturName, String picturePath, long pictureSize, String assertFileStringUri) {
        this.picturName = picturName;
        this.picturePath = picturePath;
        this.pictureSize = pictureSize;
        this.assertFileStringUri = assertFileStringUri;
    }


    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public long getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(long pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getAssertFileStringUri() {
        return assertFileStringUri;
    }

    public void setAssertFileStringUri(String assertFileStringUri) {
        this.assertFileStringUri = assertFileStringUri;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public int getDate_added() {
        return date_added;
    }

    public void setDate_added(int date_added) {
        this.date_added = date_added;
    }

    public int getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(int date_modified) {
        this.date_modified = date_modified;
    }

    public long getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(long date_taken) {
        this.date_taken = date_taken;
    }

}
