package com.Codeboy.MediaFacer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import com.Codeboy.MediaFacer.mediaHolders.audioAlbumContent;
import com.Codeboy.MediaFacer.mediaHolders.audioArtistContent;
import com.Codeboy.MediaFacer.mediaHolders.audioContent;
import com.Codeboy.MediaFacer.mediaHolders.audioFolderContent;
import java.io.File;
import java.util.ArrayList;


public class AudioGet {

    private static  AudioGet audioGet;
    private Context audioContex;
    public static final Uri externalContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri internalContentUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
    private static Cursor cursor;

    private AudioGet(Context contx){
        //set up audio getting params
        audioContex = contx.getApplicationContext();
    }

    static AudioGet getInstance(Context contx){
        if(audioGet == null){
            audioGet = new AudioGet(contx);
        }
        return audioGet;
    }

    public ArrayList<audioContent> getAllAudioContent(Uri contentLocation) {
        ArrayList<audioContent> allAudioContent = new ArrayList<>();
        String selection = android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0";
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = audioContex.getContentResolver().query(contentLocation,projection, selection, null, "LOWER ("+MediaStore.Audio.Media.TITLE + ") ASC");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    audioContent audioContent = new audioContent();

                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    audioContent.setName(song_name);

                    String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    audioContent.setTitle(songTitle);

                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    audioContent.setMusicID(id);

                    Uri contentUri = Uri.withAppendedPath(contentLocation, String.valueOf(id));
                    audioContent.setAssetFileStringUri(contentUri.toString());

                    //for android 10 exclusively
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        Uri contentUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
                        try {
                            AssetFileDescriptor file = audioContex.getContentResolver().openAssetFileDescriptor(contentUri, "r");
                            audioContent.setMusicPathQ(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }*/

                    try{
                        audioContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_added(0000);
                    }

                    try{
                        audioContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_modified(0000);
                    }

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    audioContent.setFilePath(path);
                    File audio = new File(path);
                    audioContent.setMusicSize(audio.length());


                    String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    audioContent.setAlbum(album_name);

                    @SuppressLint("InlinedApi") long dur = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    audioContent.setDuration(dur);

                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                    audioContent.setArt_uri(imageUri);

                    String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    audioContent.setArtist(artist_name);

                    allAudioContent.add(audioContent);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        //try saving in cache for better loading next time
        audioContex.getExternalCacheDir();
        return allAudioContent;
    }

    public ArrayList<audioAlbumContent> getAllAlbums(Uri contentLocation) {
        ArrayList<audioAlbumContent> audioAlbumContents = new ArrayList<>();
        String selection = android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0";
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};

        cursor = audioContex.getContentResolver().query(contentLocation,projection, selection, null, "LOWER ("+MediaStore.Audio.Media.ALBUM + ") ASC");
        ArrayList<String> albumNames = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    audioAlbumContent album; audioContent audioContent = new audioContent();

                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    audioContent.setName(song_name);

                    String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    audioContent.setTitle(songTitle);

                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    audioContent.setMusicID(id);

                    @SuppressLint("InlinedApi") long dur = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    audioContent.setDuration(dur);

                    String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    audioContent.setAlbum(album_name);

                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                    audioContent.setArt_uri(imageUri);

                    Uri contentUri = Uri.withAppendedPath(contentLocation, String.valueOf(id));
                    audioContent.setAssetFileStringUri(contentUri.toString());

                    try{
                        audioContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_added(0000);
                    }

                    try{
                        audioContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_modified(0000);
                    }

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    audioContent.setFilePath(path);
                    File audio = new File(path);
                    audioContent.setMusicSize(audio.length());

                    String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    audioContent.setArtist(artist_name);

                    if(!albumNames.contains(String.valueOf(album_id))){
                        albumNames.add(String.valueOf(album_id));
                        album = new audioAlbumContent(album_name,album_id,imageUri,artist_name);
                        album.addAudioContent(audioContent);
                        album.addNumberOfSongs();
                        audioAlbumContents.add(album);
                    }else{
                        for(int i = 0;i < audioAlbumContents.size();i++){
                            if(String.valueOf(audioAlbumContents.get(i).getAlbumId()).equals(String.valueOf(album_id))){
                                audioAlbumContents.get(i).addAudioContent(audioContent);
                                audioAlbumContents.get(i).addNumberOfSongs();
                            }
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        //try saving in cache for better loading next time
        audioContex.getExternalCacheDir();
        return audioAlbumContents;
    }

    private ArrayList<audioAlbumContent> getAllAlbumsByArtistId(String artist_id, Uri contentLocation) {
        ArrayList<audioAlbumContent> AllAlbums = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = audioContex.getContentResolver().query(contentLocation,projection,
                MediaStore.Audio.Artists.ARTIST + " like ? ", new String[] {"%"+artist_id+"%"}, "LOWER ("+MediaStore.Audio.Artists.ARTIST + ") ASC");
        ArrayList<String> albumNames = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    audioAlbumContent album; audioContent audioContent = new audioContent();

                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    audioContent.setName(song_name);

                    String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    audioContent.setTitle(songTitle);

                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    audioContent.setMusicID(id);

                    @SuppressLint("InlinedApi") long dur = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    audioContent.setDuration(dur);

                    String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    audioContent.setAlbum(album_name);

                    Uri contentUri = Uri.withAppendedPath(contentLocation, String.valueOf(id));
                    audioContent.setAssetFileStringUri(contentUri.toString());

                    try{
                        audioContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_added(0000);
                    }

                    try{
                        audioContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_modified(0000);
                    }

                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                    audioContent.setArt_uri(imageUri);

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    audioContent.setFilePath(path);
                    File audio = new File(path);
                    audioContent.setMusicSize(audio.length());

                    String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    audioContent.setArtist(artist_name);

                    if(!albumNames.contains(String.valueOf(album_id))){
                        albumNames.add(String.valueOf(album_id));
                        album = new audioAlbumContent(album_name,album_id,imageUri,artist_name);
                        album.addAudioContent(audioContent);
                        album.addNumberOfSongs();
                        AllAlbums.add(album);
                    }else{
                        for(int i = 0;i < AllAlbums.size();i++){
                            if(String.valueOf(AllAlbums.get(i).getAlbumId()).equals(String.valueOf(album_id))){
                                AllAlbums.get(i).addAudioContent(audioContent);
                                AllAlbums.get(i).addNumberOfSongs();
                            }
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        //try saving in cache for better loading next time
        audioContex.getExternalCacheDir();
        return AllAlbums;
    }

    /** Returns an ArrayList of Strings which represent names of all artist with music in the {@link MediaStore}
     * database of the device */
    public ArrayList<String> getAllArtistIds(Uri contentLocation){
        ArrayList<String> allArtistIds = new ArrayList<>();
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.ARTIST_ID,MediaStore.Audio.Media.ARTIST_KEY,MediaStore.Audio.Artists.ARTIST};
        cursor = audioContex.getContentResolver().query(contentLocation,projection, selection, null, "LOWER ("+MediaStore.Audio.Artists.ARTIST + ") ASC");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String artistId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                    if(!allArtistIds.contains(String.valueOf(artistId))){
                        allArtistIds.add(String.valueOf(artistId));
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return  allArtistIds;
    }

    /** Returns and ArrayList of Artists as {@link audioArtistContent}
     * objects from the android {@link MediaStore} database */
    public ArrayList<audioArtistContent> getAllArtists(ArrayList<String> ids,Uri contentLocation){
        ArrayList<audioArtistContent> audioArtistContents = new ArrayList<>();
        for(int i = 0; i < ids.size();i++ ){
            audioArtistContent artist = new audioArtistContent();
            ArrayList<audioAlbumContent> artistAlbums = getAllAlbumsByArtistId(ids.get(i),contentLocation);
            artist.setAlbums(artistAlbums);
            artist.setArtistName(ids.get(i));
            audioArtistContents.add(artist);
        }
        return audioArtistContents;
    }

    public ArrayList<audioFolderContent> getAllAudioFolderContent(){
        ArrayList<audioFolderContent> musicFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allsongsuri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0";
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = audioContex.getContentResolver().query(allsongsuri, projection, selection, null, "LOWER ("+MediaStore.Audio.Media.DATA + ") ASC");
        ArrayList<String> folders = new ArrayList<>();
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                audioFolderContent audioFolder = new audioFolderContent(); audioContent audioContent = new audioContent();

                String datapath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                File path = new File(datapath);
                File parent = new File(path.getParent()) ;
                String parentName = parent.getName();
                String parentPath = parent.getAbsolutePath();
                audioContent.setMusicSize(path.length());

                String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                audioContent.setName(song_name);

                String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                audioContent.setTitle(songTitle);

                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                audioContent.setMusicID(id);

                audioContent.setFilePath(datapath);
                File audio = new File(datapath);
                audioContent.setMusicSize(audio.length());

                Uri contentUri = Uri.withAppendedPath(externalContentUri, String.valueOf(id));
                audioContent.setAssetFileStringUri(contentUri.toString());

                try{
                    audioContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
                }catch (Exception ex){
                    ex.printStackTrace();
                    audioContent.setDate_added(0000);
                }

                try{
                    audioContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                }catch (Exception ex){
                    ex.printStackTrace();
                    audioContent.setDate_modified(0000);
                }

                String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                audioContent.setAlbum(album_name);

                @SuppressLint("InlinedApi") long dur = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                audioContent.setDuration(dur);

                long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                audioContent.setArt_uri(imageUri);

                String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                audioContent.setArtist(artist_name);

                if (!folders.contains(parentName)) {
                    folders.add(parentName);
                    audioFolder.setFolderName(parentName);
                    audioFolder.setFolderPath(parentPath);
                    audioFolder.getMusicFiles().add(audioContent);
                    audioFolder.setFolderCover(imageUri);
                    musicFolders.add(audioFolder);
                }else{
                    for(int i = 0;i<musicFolders.size();i++){
                        if(musicFolders.get(i).getFolderName().equals(parentName)){
                            musicFolders.get(i).getMusicFiles().add(audioContent);
                        }
                    }
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0;i < musicFolders.size();i++){
            Log.d("audio folders",musicFolders.get(i).getFolderName()+" and path = "+musicFolders.get(i).getFolderPath()+" "+musicFolders.get(i).getNumberOfSongs());
        }

        //try saving in cache for better loading next time
        audioContex.getExternalCacheDir();
        return musicFolders;
    }

    public audioContent getMusicMetaData(String datapath){
        audioContent audioContent = new audioContent();
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = audioContex.getContentResolver().query(externalContentUri,projection,
                MediaStore.Audio.Media.DATA + " like ? ", new String[] {"%"+datapath+"%"}, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    audioContent.setName(song_name);

                    String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    audioContent.setTitle(songTitle);

                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    audioContent.setMusicID(id);

                    Uri contentUri = Uri.withAppendedPath(externalContentUri, String.valueOf(id));
                    audioContent.setAssetFileStringUri(contentUri.toString());

                    try{
                        audioContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_added(0000);
                    }

                    try{
                        audioContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_modified(0000);
                    }

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    audioContent.setFilePath(path);
                    File audio = new File(path);
                    audioContent.setMusicSize(audio.length());

                    String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    audioContent.setAlbum(album_name);

                    @SuppressLint("InlinedApi") long dur = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    audioContent.setDuration(dur);

                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                    audioContent.setArt_uri(imageUri);

                    String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    audioContent.setArtist(artist_name);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return audioContent;
    }

    /** returns an ArrayList of audioContent whose names all match the search string */
    public ArrayList<audioContent> searchMusic(String audioTilte){
        ArrayList<audioContent> audioContents = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = audioContex.getContentResolver().query(externalContentUri,projection,
                MediaStore.Audio.Media.TITLE + " like ? ", new String[] {"%"+audioTilte+"%"}, "LOWER ("+MediaStore.Audio.Media.TITLE + ") ASC");

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    audioContent audioContent = new audioContent();

                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    audioContent.setName(song_name);

                    String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    audioContent.setTitle(songTitle);

                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    audioContent.setMusicID(id);

                    Uri contentUri = Uri.withAppendedPath(externalContentUri, String.valueOf(id));
                    audioContent.setAssetFileStringUri(contentUri.toString());

                    try{
                        audioContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_added(0000);
                    }

                    try{
                        audioContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_modified(0000);
                    }

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    audioContent.setFilePath(path);
                    File audio = new File(path);
                    audioContent.setMusicSize(audio.length());

                    String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    audioContent.setAlbum(album_name);

                    @SuppressLint("InlinedApi") long dur = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    audioContent.setDuration(dur);

                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                    audioContent.setArt_uri(imageUri);

                    String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    audioContent.setArtist(artist_name);

                    audioContents.add(audioContent);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return audioContents;
    }

    /** returns an ArrayList of albumHolder whose names all match the search string */
    public ArrayList<audioContent> searchAlbum(String albumName){
        ArrayList<audioContent> audioContents = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.AudioColumns.ALBUM,MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DURATION,MediaStore.Audio.ArtistColumns.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = audioContex.getContentResolver().query(externalContentUri,projection,
                MediaStore.Audio.Media.ALBUM + " like ? ", new String[] {"%"+albumName+"%"}, "LOWER ("+MediaStore.Audio.Media.TITLE + ") ASC");

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    audioContent audioContent = new audioContent();

                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    audioContent.setName(song_name);

                    String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    audioContent.setTitle(songTitle);

                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    audioContent.setMusicID(id);

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    audioContent.setFilePath(path);
                    File audio = new File(path);
                    audioContent.setMusicSize(audio.length());

                    Uri contentUri = Uri.withAppendedPath(externalContentUri, String.valueOf(id));
                    audioContent.setAssetFileStringUri(contentUri.toString());

                    try{
                        audioContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_added(0000);
                    }

                    try{
                        audioContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_modified(0000);
                    }

                    String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    audioContent.setAlbum(album_name);

                    @SuppressLint("InlinedApi") long dur = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    audioContent.setDuration(dur);

                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                    audioContent.setArt_uri(imageUri);

                    String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    audioContent.setArtist(artist_name);

                    audioContents.add(audioContent);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return audioContents;
    }

    /** returns an ArrayList of artistHolder whose names all match the search string */
    public ArrayList<audioContent> searchArtist(String artistName){
        ArrayList<audioContent> audioContents = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = audioContex.getContentResolver().query(externalContentUri,projection,
                MediaStore.Audio.Media.ARTIST + " like ? ", new String[] {"%"+artistName+"%"}, "LOWER ("+MediaStore.Audio.Media.TITLE + ") ASC");

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    audioContent audioContent = new audioContent();

                    String song_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    audioContent.setName(song_name);

                    String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    audioContent.setTitle(songTitle);

                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    audioContent.setMusicID(id);

                    Uri contentUri = Uri.withAppendedPath(externalContentUri, String.valueOf(id));
                    audioContent.setAssetFileStringUri(contentUri.toString());

                    try{
                        audioContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_added(0000);
                    }

                    try{
                        audioContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                    }catch (Exception ex){
                        ex.printStackTrace();
                        audioContent.setDate_modified(0000);
                    }

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    audioContent.setFilePath(path);
                    File audio = new File(path);
                    audioContent.setMusicSize(audio.length());

                    String album_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    audioContent.setAlbum(album_name);

                    @SuppressLint("InlinedApi") long dur = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    audioContent.setDuration(dur);

                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                    audioContent.setArt_uri(imageUri);

                    String artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    audioContent.setArtist(artist_name);

                    audioContents.add(audioContent);


                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return audioContents;
    }

}
