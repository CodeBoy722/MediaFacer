package com.CodeBoy.MediaFacer.mediaHolders;

public class videoContent {

    private long  videoId;
    private String videoName;
    private String path;
    private long videoDuration;
    private long videoSize;
    private String AssetFileStringUri;
    private String album;
    private String artist;
    private long date_added;
    private long date_modified;

    public videoContent(){

    }

    public videoContent(long videoId, String videoName, String path, long videoDuration, long videoSize) {
        this.videoId = videoId;
        this.videoName = videoName;
        this.path = path;
        this.videoDuration = videoDuration;
        this.videoSize = videoSize;
    }


    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(long videoDuration) {
        this.videoDuration = videoDuration;
    }

    public long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(long videoSize) {
        this.videoSize = videoSize;
    }

    public String getAssetFileStringUri() {
        return AssetFileStringUri;
    }

    public void setAssetFileStringUri(String assetFileStringUri) {
        AssetFileStringUri = assetFileStringUri;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDate_added() {
        return date_added;
    }

    public void setDate_added(long date_added) {
        this.date_added = date_added;
    }

    public long getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(long date_modified) {
        this.date_modified = date_modified;
    }
}
