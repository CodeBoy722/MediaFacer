package com.CodeBoy.MediaFacer.mediaHolders;

import android.net.Uri;

public class audioContent {


    private String name;
    private String Title;
    private String filePath;
    private String Artist;
    private String Album;
    private String Genre;
    private String Composer;
    private Uri art_uri;
    private long musicSize;
    private long Duration;
    private long musicID;
    private String AssetFileStringUri;
    private long date_added;
    private long date_modified;
    private long date_taken;

    public audioContent(){

    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getComposer() {
        return Composer;
    }

    public void setComposer(String composer) {
        Composer = composer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public Uri getArt_uri() {
        return art_uri;
    }

    public void setArt_uri(Uri art_uri) {
        this.art_uri = art_uri;
    }

    public long getMusicSize() {
        return musicSize;
    }

    public void setMusicSize(long musicSize) {
        this.musicSize = musicSize;
    }

    public long getDuration() {
        return Duration;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }

    public long getMusicID() {
        return musicID;
    }

    public void setMusicID(long musicID) {
        this.musicID = musicID;
    }

    public String getAssetFileStringUri() {
        return AssetFileStringUri;
    }

    public void setAssetFileStringUri(String assetFileStringUri) {
        AssetFileStringUri = assetFileStringUri;
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

    public long getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(long date_taken) {
        this.date_taken = date_taken;
    }

}
