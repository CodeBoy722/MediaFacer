package com.CodeBoy.MediaFacer;

import android.content.Context;

public class MediaFacer {

    /**Returns a static instance of {@link VideoGet} */
    public static VideoGet withVideoContex(Context contx){
        return VideoGet.getInstance(contx);
    }

    /**Returns a static instance of {@link PictureGet} */
    public static PictureGet withPictureContex(Context contx){
        return PictureGet.getInstance(contx);
    }

    /**Returns a static instance of {@link AudioGet} */
    public static AudioGet withAudioContex(Context contx){
        return AudioGet.getInstance(contx);
    }

    public static void Initialize(){

    }

    /** scans all media content on device */
    private void ScanAllMedia(){



    }

    /** save general information about all media on de vice in  */
    private void UpdateGeneralMediaInfo(){

    }

}
