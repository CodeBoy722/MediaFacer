![1 ARUJ1yzwjpDp1aRX7oZrwQ](https://user-images.githubusercontent.com/38490494/79076003-7aa64880-7cee-11ea-9bef-6dbfc908e1c3.png)

# MediaFacer
An android library for the structured querying of the MediaStore to  get media files in the simplest way possible taking in account both storage mediums on device

### [Read the article on Medium](https://android.jlelse.eu/handling-media-files-with-mediafacer-library-for-android-cd9d2ca0dc68?source=friends_link&sk=02eb8a77e0d9f1958045a39550f0e3a0)

[![](https://jitpack.io/v/CodeBoy722/MediaFacer.svg)](https://jitpack.io/#CodeBoy722/MediaFacer)

# Download
To include MediaFacer in your project, your project should have minSdkVersion 19 and above.

You can download a jar from GitHub's [releases](https://github.com/CodeBoy722/MediaFacer/releases) page.

Or use Gradle:
Add it in your your projects root build.gradle file 

```gradle
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}   
```

Add the dependency in the app level build.gradle file

```gradle
dependencies {
	   implementation 'com.github.CodeBoy722:MediaFacer:1.0.3'
	}
```
	
# Usage 
The MediaFacer library already contains a set of classes for holding audio,video and image file data and each time a query is executed to get media files, the results always come in anyone of these classes.

**videoContent** :this class holds data for a specific video file.

**audioContent** : this class holds data for a specific audio file.

**pictureContent** : this class holds data for a specific image file.

**audioAlbumContent** : this class holds all audio files and data which are related to the same music album.

**audioArtistContent** : this class holds all audio files and data related to the same music Artist.

**audioFolderContent** : this class holds all audio files found in the same folder on the storage mediums.

**pictureFolderContent** : this class holds all image files found in the same folder on the storage mediums.

**videoFolderContent** : this class holds all video files found in the same folder on the storage mediums.  

## Getting audio files from the MediaStore

get all audio files

```java
ArrayList<audioContent> audioContents;
audioContents = MediaFacer
                .withAudioContex(mContext)
                .getAllAudioContent(AudioGet.externalContentUri);
```

setting and playing audio in MediaPlayer object from audioContent class

```java
Uri content = Uri.parse(audioContent.getAssetFileStringUri());
try {
            AssetFileDescriptor file = getActivity().getContentResolver().openAssetFileDescriptor(content, "r");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                player.setDataSource(file);
            }else {
                player.setDataSource(audioContent.getFilePath());
            }
            player.setLooping(false);
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
}
```

get all audio files with similar albums

```java
ArrayList<audioAlbumContent>  allAlbums;
    allAlbums = MediaFacer
                .withAudioContex(mContext)
                .getAllAlbums(AudioGet.externalContentUri);
```

get all audio files with similar artists 

```java
ArrayList<audioArtistContent> allArtists;
    
    ArrayList<String> ids = MediaFacer
                            .withAudioContex(mContext)
                            .getAllArtistIds(AudioGet.externalContentUri);
			    
    allArtists = MediaFacer
                 .withAudioContex(mContext)
                 .getAllArtists(ids,AudioGet.externalContentUri);
```

get all audio files in same folders

```java
 ArrayList<audioFolderContent> buckets;
 buckets = MediaFacer
           .withAudioContex(mContext)
           .getAllAudioFolderContent();

```
   
## Getting images from the MediaStore

get all images in the MediaStore.

```java
ArrayList<pictureContent> allPhotos;

  allPhotos = MediaFacer
              .withPictureContex(mContext)
              .getAllPictureContents(PictureGet.externalContentUri);
```

loading and displaying images from pictureContent class,
 you can use [Glide](https://github.com/bumptech/glide) for this

```java
ImageView picture = findViewById(R.id.picture);
Glide.with(mContext)
                    .load(Uri.parse(pictureContent.getAssertFileStringUri()))
                    .apply(new RequestOptions().centerCrop())
                    .into(picture);
```

get all folders containing pictures

```java
ArrayList<pictureFolderContent> pictureFolders = new ArrayList<>();
      
      pictureFolders.addAll(MediaFacer.withPictureContex(mContext).getPictureFolders());
	
//now load images for the first pictureFolderContent object
	pictureFolders.get(0)
	.setPhotos(MediaFacer
	.withPictureContex(mContext)
	.getAllPictureContentByBucket_id(pictureFolders.get(0).getBucket_id());	
```

## Getting Videos from the MediaStore

get all videos in the MediaStore.

```java
ArrayList<videoContent> allVideos;
 allVideos = MediaFacer
             .withVideoContex(mContext)
             .getAllVideoContent(VideoGet.externalContentUri);
```

loading video into VideoView from videoContent class

```java
VideoView playZone findViewById(R.id.vid_zone);
SeekBar seeker = findViewById(R.id.seeker);

 playZone.setVideoURI(Uri.parse(videoContent.getAssetFileStringUri()));
        playZone.requestFocus();
        seeker.setMax((int) videos.get(position).getVideoDuration());
        playZone.start();
```

get all folders containing videos

```java
 ArrayList<videoFolderContent> videoFolders = new ArrayList<>();
      
     videoFolders.addAll(MediaFacer.withVideoContex(mContext).getVideoFolders(VideoGet.externalContentUri));
	
//now load videos for the first videoFolderContent object
	videoFolders.get(0)
	.setVideoFiles(MediaFacer.withVideoContex(mContext)
	.getAllVideoContentByBucket_id(videoFolders.get(0).getBucket_id());
```







