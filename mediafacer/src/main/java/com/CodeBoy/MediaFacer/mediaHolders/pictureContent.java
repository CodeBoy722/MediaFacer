package com.CodeBoy.MediaFacer.mediaHolders;

public class pictureContent {

    private String picturName;
    private String picturePath;
    private  Long pictureSize;
    private  String assertFileStringUri;
    private  int pictureId;

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

}
