package com.Codeboy.MediaFacer;

import android.content.Context;

public class MediaFacer {

    public static VideoGet withVideoContex(Context contx){
        return VideoGet.getInstance(contx);
    }

    public static PictureGet withPictureContex(Context contx){
        return PictureGet.getInstance(contx);
    }


    public static AudioGet withAudioContex(Context contx){
        return AudioGet.getInstance(contx);
    }

}
