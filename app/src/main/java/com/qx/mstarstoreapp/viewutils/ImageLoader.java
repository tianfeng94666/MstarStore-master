package com.qx.mstarstoreapp.viewutils;

import android.content.Context;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class ImageLoader {
    private static volatile ImageLoader mSingleton;
    private final Context mContext;

    private ImageLoader(Context context) {
        mContext = context;
    }

    public static ImageLoader with(Context context) {
        if(mSingleton == null) {
            synchronized (ImageLoader.class) {
                if(mSingleton == null) {
                    mSingleton = new ImageLoader(context);
                }
            }
        }
        return mSingleton;
    }
}

