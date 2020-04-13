![1 ARUJ1yzwjpDp1aRX7oZrwQ](https://user-images.githubusercontent.com/38490494/79076003-7aa64880-7cee-11ea-9bef-6dbfc908e1c3.png)

# MediaFacer
An android library for the structured querying of the MediaStore to  get media files in the simplest way possible taking in account both storage mediums on device

[![](https://jitpack.io/v/CodeBoy722/MediaFacer.svg)](https://jitpack.io/#CodeBoy722/MediaFacer)

# Download
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
	   implementation 'com.github.CodeBoy722:MediaFacer:1.0.0'
	}
```
	
#Usage 
The MediaFacer library already contains a set of classes for holding audio,video and image file data and each time a query is executed to get media files, the results always come in anyone of these classes.

**videoContent** :this class holds data for a specific video file.

**audioContent** : this class holds data for a specific audio file.

**pictureContent** : this class holds data for a specific image file.

**audioAlbumContent** : this class holds all audio files and data which are related to the same music album.

**audioArtistContent** : this class holds all audio files and data related to the same music Artist.

**audioFolderContent** : this class holds all audio files found in the same folder on the storage mediums.

**pictureFolderContent** : this class holds all image files found in the same folder on the storage mediums.

**videoFolderContent** : this class holds all video files found in the same folder on the storage mediums.  

##Getting audio files from the MediaStore

get all audio files

```java
ArrayList<audioContent> audioContents;
audioContents = MediaFacer
                .withAudioContex(mContext)
                .getAllAudioContent(AudioGet.externalContentUri;
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

get all audio files with similar album

```java
ArrayList<audioAlbumContent>  allAlbums;
    allAlbums = MediaFacer
                .withAudioContex(mContext)
                .getAllAlbums(AudioGet.externalContentUri);
```

get all audio files with similar artist 

```java
ArrayList<audioArtistContent> allArtists;
    
    ArrayList<String> ids = MediaFacer
                            .withAudioContex(mContext)
                            .getAllArtistIds(AudioGet.externalContentUri);
			    
    allArtists = MediaFacer
                 .withAudioContex(mContext)
                 .getAllArtists(ids,AudioGet.externalContentUri);
```

get all audio files in same folder

```java
 ArrayList<audioFolderContent> buckets;
 buckets = MediaFacer
           .withAudioContex(mContext)
           .getAllAudioFolderContent();

```
   





















