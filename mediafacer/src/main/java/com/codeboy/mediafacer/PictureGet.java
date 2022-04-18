package com.codeboy.mediafacer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.codeboy.mediafacer.mediaHolders.pictureContent;
import com.codeboy.mediafacer.mediaHolders.pictureFolderContent;

import java.util.ArrayList;

public class PictureGet {

    private static  PictureGet pictureGet;
    private final Context pictureContext;
    public static final Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static final Uri internalContentUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
    private static Cursor cursor;

    private PictureGet(Context context){
        pictureContext = context.getApplicationContext();
    }

    static PictureGet getInstance(Context context){
        if(pictureGet == null){
            pictureGet = new PictureGet(context);
        }
        return pictureGet;
    }

    @SuppressLint("InlinedApi")
    private final String[] Projections = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_TAKEN};

    /**Returns an ArrayList of {@link pictureContent}  */
    @SuppressLint("InlinedApi")
    public ArrayList<pictureContent> getAllPictureContents(Uri contentLocation){
        ArrayList<pictureContent> images = new ArrayList<>();
        cursor = pictureContext.getContentResolver().query( contentLocation, Projections, null, null,
                "LOWER ("+MediaStore.Images.Media.DATE_TAKEN+") DESC");
        try {
            cursor.moveToFirst();
            do{
                pictureContent pictureContent = new pictureContent();

                pictureContent.setPictureName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                pictureContent.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                pictureContent.setPictureSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                pictureContent.setPictureId(id);

                Uri contentUri = Uri.withAppendedPath(contentLocation, String.valueOf(id));
                pictureContent.setPhotoUri(contentUri.toString());

                images.add(pictureContent);
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    /**Returns an ArrayList of {@link pictureContent} in a specific folder*/
    @SuppressLint("InlinedApi")
    public ArrayList<pictureContent> getAllPictureContentByBucket_id(int bucket_id){
        ArrayList<pictureContent> images = new ArrayList<>();
        cursor = pictureContext.getContentResolver().query( externalContentUri, Projections, MediaStore.Images.Media.BUCKET_ID + " like ? ", new String[] {"%"+bucket_id+"%"},
                "LOWER ("+MediaStore.Images.Media.DATE_TAKEN+") DESC");
        try {
            cursor.moveToFirst();
            do{
                pictureContent pictureContent = new pictureContent();

                pictureContent.setPictureName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                pictureContent.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                pictureContent.setPictureSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                pictureContent.setPictureId(id);

                Uri contentUri = Uri.withAppendedPath(externalContentUri, String.valueOf(id));
                pictureContent.setPhotoUri(contentUri.toString());

                images.add(pictureContent);
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    /**Returns an ArrayList of {@link pictureFolderContent}  */
    @SuppressLint("InlinedApi")
    public ArrayList<pictureFolderContent> getAllPictureFolders(){
        ArrayList<pictureFolderContent> absolutePictureFolders = new ArrayList<>();
        ArrayList<Integer> picturePaths = new ArrayList<>();
        cursor = pictureContext.getContentResolver().query( externalContentUri, Projections, null, null,
                "LOWER ("+MediaStore.Images.Media.DATE_TAKEN+") DESC");
        try{
            cursor.moveToFirst();
            do{
                pictureFolderContent photoFolder = new pictureFolderContent();
                pictureContent pictureContent = new pictureContent();

                pictureContent.setPictureName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                pictureContent.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                pictureContent.setPictureSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                pictureContent.setPictureId(id);

                pictureContent.setPhotoUri(Uri.withAppendedPath(externalContentUri, String.valueOf(id)).toString());

                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                int bucket_id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID));

                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picturePaths.contains(bucket_id)) {
                    picturePaths.add(bucket_id);
                    photoFolder.setBucket_id(bucket_id);
                    photoFolder.setFolderPath(folderpaths);
                    photoFolder.setFolderName(folder);
                    photoFolder.getPhotos().add(pictureContent);
                    absolutePictureFolders.add(photoFolder);
                }else {
                    for (pictureFolderContent folderX : absolutePictureFolders){
                        if(folderX.getBucket_id() == bucket_id){
                            folderX.getPhotos().add(pictureContent);
                        }
                    }
                }
            }while (cursor.moveToNext());
        }catch (Exception e){
            e.printStackTrace();
        }

        return absolutePictureFolders;
    }

   /* suspend fun getImages(count: Int, start: Int): Three<MutableList<Three<Uri?, String?, Date>>, Boolean, Int> {
        val imagesList = mutableListOf<Three<Uri?, String?, Date>>()
        var index = start

        return withContext(Dispatchers.IO) {
            while (imageCursor?.moveToPosition(i) == true) {
                val id = imageIdColumn?.let { imageCursor.getLong(it) }
                val dateModified = Date(TimeUnit.SECONDS.toMillis(imageCursor.getLong(imageDateModifiedColumn ?: 0)))

                val displayName = imageDisplayNameColumn?.let { imageCursor.getString(it) }
                val contentUri = id?.let {
                    ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            it
                    )
                }
                index++
                contentUri?.let { imagesList.add(Three(contentUri, displayName, dateModified)) }
                if (index == count)
                    break
            }
            val areAllLoaded = index == imagesToLoad
            return@withContext Three(imagesList, areAllLoaded, index)
        }
    }*/


}
