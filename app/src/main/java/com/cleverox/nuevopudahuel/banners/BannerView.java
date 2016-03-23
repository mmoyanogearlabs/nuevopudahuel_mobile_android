package com.cleverox.nuevopudahuel.banners;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bzutils.BZUtils;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.banners.model.APIBanner;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by iaguila on 23/3/16.
 */
public class BannerView extends ImageView {

    private static final int BANNER_WIDTH = 320;
    private static final int IPHONE6_WIDHT = 375;
    private static final int TIME_BETWEEN = 5000;

    private String screen;
    private boolean changeSize;
    private APIBanner banner;
    private BannerInterface bannerInterface;

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        checkAttributeSet(attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        checkAttributeSet(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        checkAttributeSet(attrs);
    }

    private void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bannerInterface != null)
                    bannerInterface.onBannerClicked(banner.getLinkUrl());
            }
        });
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public void load() {
        BannerController.getInstance().loadBanner(((BaseActivity) getContext()).getPudahuelApplication(), this);
    }

    private void checkAttributeSet(AttributeSet attrs) {
        int[] attrsArray = new int[]{
                R.attr.screenName, // 0
                R.attr.autoLoad, // 1
                R.attr.changeSize //2
        };

        TypedArray ta = getContext().obtainStyledAttributes(attrs, attrsArray);
        setScreen(ta.getString(0));

        boolean autoLoad = ta.getBoolean(1, true);
        changeSize = ta.getBoolean(2, true);
        if (autoLoad) load();

    }

    public void setBanner(APIBanner banner) {
        this.banner = banner;
        loadBannerImg();
    }

    private void loadBannerImg() {
        if (isSmallScreen()){
            loadSmallBanner();
            return;
        }
        loadBigBanner();
    }

    private boolean isSmallScreen() {
        return BZUtils.getScreenWidht((BaseActivity) getContext()) <= 480;
    }

    private void loadBigBanner() {
        ((BaseActivity) getContext()).getPudahuelApplication().loadImageUrl(banner.getBigBannerUrl(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                scaleBannerImg(loadedImage);
                checkChangeSize();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    private void checkChangeSize() {
        if (changeSize) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadSmallBanner();
                }
            }, TIME_BETWEEN);
        }
    }

    private void loadSmallBanner() {
        ((BaseActivity) getContext()).getPudahuelApplication().loadImageUrl(banner.getSmallBannerUrl(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                scaleBannerImg(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    private void scaleBannerImg(Bitmap loadBitmap) {
        int bannerWidth = BANNER_WIDTH * BZUtils.getScreenWidht((BaseActivity) getContext()) / IPHONE6_WIDHT;
        int bannerHeight = bannerWidth * loadBitmap.getHeight() / BANNER_WIDTH;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(bannerWidth, bannerHeight);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        setLayoutParams(params);
        setImageBitmap(loadBitmap);
        if (bannerInterface != null)
            bannerInterface.onBannerSizeChanged(bannerHeight);
    }

    public void setBannerInterface(BannerInterface bannerInterface) {
        this.bannerInterface = bannerInterface;
    }

    public interface BannerInterface {
        void onBannerClicked(String url);
        void onBannerSizeChanged(int height);
    }
}
