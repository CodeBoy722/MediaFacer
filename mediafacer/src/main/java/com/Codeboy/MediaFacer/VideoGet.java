package com.Codeboy.MediaFacer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.Codeboy.MediaFacer.mediaHolders.videoContent;
import com.Codeboy.MediaFacer.mediaHolders.videoFolderContent;
import java.util.ArrayList;
public class VideoGet {

    private static  VideoGet videoGet;
    private Context videoContex;
    public static final Uri externalContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    public static final Uri internalContentUri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
    private static Cursor cursor;


    private VideoGet(Context contx){
        //set up picture getting params
        videoContex = contx.getApplicationContext();
    }

    static VideoGet getInstance(Context contx){
        if(videoGet == null){
            videoGet = new VideoGet(contx);
        }
        return videoGet;
    }

    /**Returns an Arraylist of {@link videoContent}  */
    @SuppressLint("InlinedApi")
    public ArrayList<videoContent> getAllVideoContent(Uri contentLocation) {
        ArrayList<videoContent> allVideo = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,MediaStore.Video.Media._ID,MediaStore.Video.Media.DATE_ADDED,MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.ALBUM,MediaStore.Video.Media.ARTIST};
        cursor = videoContex.getContentResolver().query(contentLocation, projection, null, null, "LOWER ("+MediaStore.Video.Media.DATE_ADDED+") DESC");//DESC ASC
        try {
            cursor.moveToFirst();
            do{
                videoContent videoContent = new videoContent();

                videoContent.setVideoName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));

                videoContent.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));

                videoContent.setVideoDuration(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));

                videoContent.setVideoSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                videoContent.setVideoId(id);

                Uri contentUri = Uri.withAppendedPath(contentLocation, String.valueOf(id));
                videoContent.setAssetFileStringUri(contentUri.toString());

                videoContent.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)));

                videoContent.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)));

                try{
                    videoContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)));
                }catch (Exception ex){
                    ex.printStackTrace();
                    videoContent.setDate_added(0000);
                }

                try{
                    videoContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)));
                }catch (Exception ex){
                    ex.printStackTrace();
                    videoContent.setDate_added(0000);
                }

                allVideo.add(videoContent);
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allVideo;
    }

    /**Returns an Arraylist of {@link videoFolderContent}  */
    public ArrayList<videoFolderContent> getVidioPaths(Uri contentLocation){
        ArrayList<videoFolderContent> allVideoFolders = new ArrayList<>();
        ArrayList<String> videoPaths = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME
               ,MediaStore.Video.Media.DATE_ADDED};
        cursor = videoContex.getContentResolver().query(contentLocation, projection, null, null, "LOWER ("+MediaStore.Video.Media.DATE_ADDED+") DESC");//DESC
        try {
            cursor.moveToFirst();
            do{
                videoFolderContent videoFolder = new videoFolderContent();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                @SuppressLint("InlinedApi") String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!videoPaths.contains(folderpaths)) {
                    videoPaths.add(folderpaths);

                    videoFolder.setFolderPath(folderpaths);
                    videoFolder.setFolderName(folder);
                    allVideoFolders.add(videoFolder);
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allVideoFolders;
    }

    /**Returns an Arraylist of {@link videoContent} in a specific folder  */
    @SuppressLint("InlinedApi")
    public ArrayList<videoContent> getAllVideoContentInFolder(String folderPath){
        ArrayList<videoContent> videoContents = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,MediaStore.Video.Media._ID,MediaStore.Video.Media.DATE_ADDED,MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.ALBUM,MediaStore.Video.Media.ARTIST};
        cursor = videoContex.getContentResolver().query(externalContentUri, projection,
                MediaStore.Video.Media.DATA + " like ? ", new String[] {"%"+folderPath+"%"}, "LOWER ("+MediaStore.Video.Media.DATE_ADDED+") DESC");//DESC
        try {
            cursor.moveToFirst();
            do{
                videoContent videoContent = new videoContent();

                videoContent.setVideoName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));

                videoContent.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));

                videoContent.setVideoDuration(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));

                videoContent.setVideoSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                videoContent.setVideoId(id);

                Uri contentUri = Uri.withAppendedPath(externalContentUri, String.valueOf(id));
                videoContent.setAssetFileStringUri(contentUri.toString());

                videoContent.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)));

                videoContent.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)));

                try{
                    videoContent.setDate_added(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)));
                }catch (Exception ex){
                    ex.printStackTrace();
                    videoContent.setDate_added(0000);
                }

                try{
                    videoContent.setDate_modified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)));
                }catch (Exception ex){
                    ex.printStackTrace();
                    videoContent.setDate_added(0000);
                }

                videoContents.add(videoContent);
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoContents;
    }

}
