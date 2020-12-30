package com.CodeBoy.MediaFacer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import com.CodeBoy.MediaFacer.mediaHolders.audioAlbumContent;
import com.CodeBoy.MediaFacer.mediaHolders.audioArtistContent;
import com.CodeBoy.MediaFacer.mediaHolders.audioContent;
import com.CodeBoy.MediaFacer.mediaHolders.audioFolderContent;
import java.io.File;
import java.util.ArrayList;

public class AudioGet {

    private static  AudioGet audioGet;
    private Context AudioContext;
    public static final Uri externalContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri internalContentUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
    private static Cursor cursor;

    private AudioGet(Context context){
        //set up audio getting params
        AudioContext = context.getApplicationContext();
    }

    static AudioGet getInstance(Context context){
        if(audioGet == null){
            audioGet = new AudioGet(context);
        }
        return audioGet;
    }
    String Selection = android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0";
    @SuppressLint("InlinedApi") String[] Projections = {
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATE_TAKEN,
            MediaStore.Audio.Media.DATE_MODIFIED,
    };

    /**Returns an Arraylist of {@link audioContent} */
    @SuppressLint("InlinedApi")
    public ArrayList<audioContent> getAllAudioContent(Uri contentLocation) {
        ArrayList<audioContent> allAudioContent = new ArrayList<>();
        cursor = AudioContext.getContentResolver().query(contentLocation,Projections, Selection, null, "LOWER ("+MediaStore.Audio.Media.TITLE + ") ASC");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    audioContent audioContent = new audioContent();

                    audioContent.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));

                    audioContent.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));

                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    audioContent.setMusicID(id);
                    Uri contentUri = Uri.withAppendedPath(contentLocation, String.valueOf(id));
                    audioContent.setAssetFileStringUri(contentUri.toString());

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    audioContent.setFilePath(path);
                    File audio = new File(path);
                    audioContent.setMusicSize(audio.length());

                    audioContent.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));

                    audioContent.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));

                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
                    audioContent.setArt_uri(imageUri);

                    audioContent.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));

                    audioContent.setGenre(GetGenre(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))));

                    audioContent.setComposer(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER)));

                    allAudioContent.add(audioContent);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return allAudioContent;
    }
    
    private String GetGenre(int media_id){
        String[] genresProj = {
                MediaStore.Audio.Genres.NAME,
                MediaStore.Audio.Genres._ID
        };

        Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", media_id );
        Cursor genresCursor = AudioContext.getContentResolver().query(uri, genresProj , null, null, null);
        int genreIndex = genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);

        String genre = "";
        while (genresCursor.moveToNext()) {
            genre = genresCursor.getString(genreIndex);
        }
        genresCursor.close();
    return genre;
    }

    /**Returns an ArrayList of {@link audioAlbumContent} */
    public ArrayList<audioAlbumContent> getAllAlbums(Uri contentLocation) {
        ArrayList<audioAlbumContent> audioAlbumContents = new ArrayList<>();
        String selection = android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0";
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};

        cursor = AudioContext.getContentResolver().query(contentLocation,projection, selection, null, "LOWER ("+MediaStore.Audio.Media.ALBUM + ") ASC");
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
        AudioContext.getExternalCacheDir();
        return audioAlbumContents;
    }

    /**Returns an ArrayList of String representing artist ids in the {@link MediaStore} */
    private ArrayList<audioAlbumContent> getAllAlbumsByArtistId(String artist_id, Uri contentLocation) {
        ArrayList<audioAlbumContent> AllAlbums = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = AudioContext.getContentResolver().query(contentLocation,projection,
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
        AudioContext.getExternalCacheDir();
        return AllAlbums;
    }

    /** Returns an ArrayList of Strings which represent names of all artist with music in the {@link MediaStore}
     * database of the device */
    public ArrayList<String> getAllArtistIds(Uri contentLocation){
        ArrayList<String> allArtistIds = new ArrayList<>();
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.ARTIST_ID,MediaStore.Audio.Media.ARTIST_KEY,MediaStore.Audio.Artists.ARTIST};
        cursor = AudioContext.getContentResolver().query(contentLocation,projection, selection, null, "LOWER ("+MediaStore.Audio.Artists.ARTIST + ") ASC");
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

    /** Returns and ArrayList of {@link audioFolderContent} from the android MediaStore */
    public ArrayList<audioFolderContent> getAllAudioFolderContent(){
        ArrayList<audioFolderContent> musicFolders = new ArrayList<>();
        Uri allsongsuri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.BUCKET_ID,MediaStore.Audio.Media.ALBUM_ID,MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID};
        cursor = AudioContext.getContentResolver().query(allsongsuri, projection, selection, null, "LOWER ("+MediaStore.Audio.Media.TITLE + ") ASC");
        ArrayList<Integer> folders = new ArrayList<>();
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

                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                audioContent.setMusicSize(duration);

                int bucket_id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.BUCKET_ID));

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

                if (!folders.contains(bucket_id)) {
                    folders.add(bucket_id);

                    audioFolder.setBucket_id(bucket_id);
                    audioFolder.setFolderName(parentName);
                    audioFolder.setFolderPath(parentPath);
                    audioFolder.getMusicFiles().add(audioContent);
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
        AudioContext.getExternalCacheDir();
        return musicFolders;
    }

    public audioContent getMusicMetaData(String datapath){
        audioContent audioContent = new audioContent();
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = AudioContext.getContentResolver().query(externalContentUri,projection,
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
    public ArrayList<audioContent> searchMusic(String audioTitle){
        ArrayList<audioContent> audioContents = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST_ID};
        cursor = AudioContext.getContentResolver().query(externalContentUri,projection,
                MediaStore.Audio.Media.TITLE + " like ? ", new String[] {"%"+audioTitle+"%"}, "LOWER ("+MediaStore.Audio.Media.TITLE + ") ASC");

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
        cursor = AudioContext.getContentResolver().query(externalContentUri,projection,
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
        cursor = AudioContext.getContentResolver().query(externalContentUri,projection,
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
