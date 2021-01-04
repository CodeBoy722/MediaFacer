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
    private final Context videoContex;
    public static final Uri externalContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    public static final Uri internalContentUri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
    private static Cursor cursor;

    private VideoGet(Context contx){
        videoContex = contx.getApplicationContext();
    }

    static VideoGet getInstance(Context contx){
        if(videoGet == null){
            videoGet = new VideoGet(contx);
        }
        return videoGet;
    }

    @SuppressLint("InlinedApi") String[] Projections = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.ALBUM,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media.ARTIST};


    /**Returns an Arraylist of {@link videoContent}  */
    @SuppressLint("InlinedApi")
    public ArrayList<videoContent> getAllVideoContent(Uri contentLocation) {
        ArrayList<videoContent> allVideo = new ArrayList<>();
        cursor = videoContex.getContentResolver().query(contentLocation, Projections, null, null, "LOWER ("+MediaStore.Video.Media.DATE_TAKEN+") DESC");//DESC ASC
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

                allVideo.add(videoContent);
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allVideo;
    }

    /**Returns an Arraylist of {@link videoContent} in a specific folder  */
    @SuppressLint("InlinedApi")
    public ArrayList<videoContent> getAllVideoContentByBucket_id(int bucket_id){
        ArrayList<videoContent> videoContents = new ArrayList<>();
       cursor = videoContex.getContentResolver().query(externalContentUri, Projections,
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

                videoContents.add(videoContent);
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoContents;
    }

    /**Returns an Arraylist of {@link videoFolderContent} with each videoFolderContent having an Arraylist of all it videoContent*/
    @SuppressLint("InlinedApi")
    public ArrayList<videoFolderContent> getAllVideoFolders(Uri contentLocation){
        ArrayList<videoFolderContent> allVideoFolders = new ArrayList<>();
        ArrayList<Integer> videoPaths = new ArrayList<>();
        cursor = videoContex.getContentResolver().query(contentLocation, Projections, null, null, "LOWER ("+MediaStore.Video.Media.DATE_TAKEN+") DESC");//DESC
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
