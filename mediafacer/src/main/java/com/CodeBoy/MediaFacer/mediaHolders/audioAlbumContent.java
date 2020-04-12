package com.CodeBoy.MediaFacer.mediaHolders;

import android.net.Uri;

import java.util.ArrayList;

public class audioAlbumContent {

    private String albumName;
    private long albumId;
    private int numberOfSongs = 0;
    private Uri albumArtUri;
    private String albumArtist;
    private ArrayList<audioContent> audioContents = new ArrayList<>();

    public audioAlbumContent(){

    }

    public audioAlbumContent(String albumName, long albumId, Uri albumArtUri, String albumArtist) {
        this.albumName = albumName;
        this.albumId = albumId;
        this.albumArtUri = albumArtUri;
        this.albumArtist = albumArtist;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }

    public void setAlbumArtUri(Uri albumArtUri) {
        this.albumArtUri = albumArtUri;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public ArrayList<audioContent> getAudioContents() {
        return audioContents;
    }

    public void setAudioContents(ArrayList<audioContent> audioContents) {
        this.audioContents = audioContents;
    }

    public void addAudioContent(audioContent audioContent){
        audioContents.add(audioContent);
    }

    public void addNumberOfSongs(){
        numberOfSongs++;
    }

}
