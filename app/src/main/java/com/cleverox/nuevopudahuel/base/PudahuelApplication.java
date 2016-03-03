package com.cleverox.nuevopudahuel.base;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bzutils.LogBZ;
import com.cleverox.nuevopudahuel.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PudahuelApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initCalligraphy();
        initImageLoaderConfiguration();
    }

    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(getString(R.string.khand_regular))
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    private void initImageLoaderConfiguration(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext())
                .defaultDisplayImageOptions(makeImageOptions(0,false))
                .memoryCache(new WeakMemoryCache())
                .build();

        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions makeImageOptions(int placeHolderImage,boolean hasPlaceHolder){

        boolean cacheInMemory = true;
        boolean cacheOnDisc = true;
        boolean resetViewOnLoad = false;


        BitmapFactory.Options resizeOptions = new BitmapFactory.Options();
        resizeOptions.inScaled = true;


        DisplayImageOptions.Builder optionsBuilder = new DisplayImageOptions.Builder();

        optionsBuilder.resetViewBeforeLoading(resetViewOnLoad)
                .cacheInMemory(cacheInMemory)
                .cacheOnDisk(cacheOnDisc)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .decodingOptions(resizeOptions)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT);

        if (hasPlaceHolder) {
            optionsBuilder.showImageOnLoading(placeHolderImage)
                    .showImageForEmptyUri(placeHolderImage)
                    .showImageOnFail(placeHolderImage);
        }



        return optionsBuilder.build();

    }

    public void loadImageUrl(final String url,final ImageView image,final int placeHolderImage){


        ImageLoader.getInstance().displayImage(url, image, makeImageOptions(placeHolderImage, true), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

    }
}
