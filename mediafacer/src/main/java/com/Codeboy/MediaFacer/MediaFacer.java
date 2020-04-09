package com.Codeboy.MediaFacer;

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

}
