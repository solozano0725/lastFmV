package com.valid.lastfmclient.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


import java.lang.ref.WeakReference;

public class ImageLoader {

    public static void loadImage(Context context, String imageUrl, int placeHolderResourceID, ImageView imageView) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Glide.with(weakReference.get()).load(imageUrl).asBitmap().placeholder(placeHolderResourceID).into(imageView);
    }

    public interface ImageLoaderCallbacks {
        void onSuccess();

        void onFail(Exception e);
    }
}
