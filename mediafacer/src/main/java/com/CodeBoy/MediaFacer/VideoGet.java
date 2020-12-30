package com.CodeBoy.MediaFacer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.CodeBoy.MediaFacer.mediaHolders.videoContent;
import com.CodeBoy.MediaFacer.mediaHolders.videoFolderContent;
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
        @SuppressLint("InlinedApi") String[] projection = {
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DATE_TAKEN,
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.ARTIST};
        cursor = videoContex.getContentResolver().query(contentLocation, projection, null, null, "LOWER ("+MediaStore.Video.Media.DATE_TAKEN+") DESC");//DESC ASC
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
                    videoContent.setDate_taken(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN)));
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
    public ArrayList<videoFolderContent> getVideoFolders(Uri contentLocation){
        ArrayList<videoFolderContent> allVideoFolders = new ArrayList<>();
        ArrayList<Integer> videoPaths = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.DATE_ADDED};
        cursor = videoContex.getContentResolver().query(contentLocation, projection, null, null, "LOWER ("+MediaStore.Video.Media.DATE_TAKEN+") DESC");//DESC
        try {
            cursor.moveToFirst();
            do{
                videoFolderContent videoFolder = new videoFolderContent();
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                int bucket_id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID));

                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";

                if (!videoPaths.contains(bucket_id)) {
                    videoPaths.add(bucket_id);

                    videoFolder.setBucket_id(bucket_id);
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
    public ArrayList<videoContent> getAllVideoContentByBucket_id(int bucket_id){
        ArrayList<videoContent> videoContents = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {
                MediaStore.Video.VideoColumns.DATA ,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DATE_TAKEN,
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.ARTIST};
       cursor = videoContex.getContentResolver().query(externalContentUri, projection,
                MediaStore.Video.Media.BUCKET_ID + " like ? ", new String[] {"%"+bucket_id+"%"}, "LOWER ("+MediaStore.Video.Media.DATE_TAKEN+") DESC");//DESC
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
                    videoContent.setDate_taken(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN)));
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

    /**Returns an Arraylist of {@link videoFolderContent} with each videoFolderContent having an Arraylist of all it videoContent*/
    public ArrayList<videoFolderContent> getAbsoluteVideoFolders(Uri contentLocation){
        ArrayList<videoFolderContent> allVideoFolders = new ArrayList<>();
        ArrayList<Integer> videoPaths = new ArrayList<>();
        @SuppressLint("InlinedApi") String[] projection = {
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DATE_TAKEN,
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.ARTIST};
        cursor = videoContex.getContentResolver().query(contentLocation, projection, null, null, "LOWER ("+MediaStore.Video.Media.DATE_TAKEN+") DESC");//DESC
        try{
            cursor.moveToFirst();
            do{
                videoFolderContent videoFolder = new videoFolderContent();
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
                    videoContent.setDate_taken(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN)));
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

                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                int bucket_id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID));

                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";

                if (!videoPaths.contains(bucket_id)) {
                    videoPaths.add(bucket_id);
                    videoFolder.setBucket_id(bucket_id);
                    videoFolder.setFolderPath(folderpaths);
                    videoFolder.setFolderName(folder);
                    videoFolder.getVideoFiles().add(videoContent);
                    allVideoFolders.add(videoFolder);
                }else{
                    for(int i = 0; i < allVideoFolders.size();i++){
                        if(allVideoFolders.get(i).getBucket_id() == bucket_id){
                            allVideoFolders.get(i).getVideoFiles().add(videoContent);
                        }
                    }
                }
            }while (cursor.moveToNext());
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return allVideoFolders;
    }

}
