package com.cleverox.nuevopudahuel.base;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.multidex.MultiDexApplication;
import android.view.View;
import android.widget.ImageView;

import com.bzutils.BZUtils;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.api.services.FlightsService;
import com.cleverox.nuevopudahuel.api.services.BannerService;
import com.cleverox.nuevopudahuel.constants.Constants;
import com.cleverox.nuevopudahuel.constants.PudahuelPrefs;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.innoquant.moca.MOCA;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.okhttp.OkHttpClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.realm.RealmConfiguration;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PudahuelApplication extends MultiDexApplication {

    private FlightsService mService;
    private BannerService mBannerService;
    private OkClient mHttpClient;
    private RealmConfiguration configuration;

    @Override
    public void onCreate() {
        super.onCreate();

        initMoca();
        initCalligraphy();
        initImageLoaderConfiguration();
        initAPI();
        initBannersAPI();
        initRealm();
        BZUtils.printSignature(this);
        FacebookSdk.sdkInitialize(this);
    }

    private void initMoca() {
        MOCA.initializeSDK (this);
    }

    private void initRealm() {
        configuration = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
    }

    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.khand_regular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void initAPI() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new gsonUTCdateAdapter())
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api_url))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(getOkHttpClient())
                .setConverter(new GsonConverter(gson))
                .build();

        mService = restAdapter.create(FlightsService.class);
    }

    private void initBannersAPI() {
        Gson gson = new GsonBuilder().create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.banners_api_url))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(getOkHttpClient())
                .setConverter(new GsonConverter(gson))
                .build();

        mBannerService = restAdapter.create(BannerService.class);
    }

    private void initHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(Constants.API_CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(Constants.API_READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        mHttpClient = new OkClient(okHttpClient);
    }

    public FlightsService getService() {
        if (mService == null)
            initAPI();
        return mService;
    }

    public BannerService getBannerService() {
        if (mBannerService == null)
            initBannersAPI();
        return mBannerService;
    }

    private OkClient getOkHttpClient() {
        if (mHttpClient == null)
            initHttpClient();
        return mHttpClient;
    }

    private void initImageLoaderConfiguration() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext())
                .defaultDisplayImageOptions(makeImageOptions(0, false))
                .memoryCache(new WeakMemoryCache())
                .build();

        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions makeImageOptions(int placeHolderImage, boolean hasPlaceHolder) {

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

    public void loadImageUrl(final String url, final ImageView image, final int placeHolderImage) {


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

    public void loadImageUrl(String url, ImageLoadingListener listener) {
        ImageLoader.getInstance().loadImage(url, listener);
    }

    private SharedPreferences getPreferences() {
        return getSharedPreferences(PudahuelPrefs.SHARED_PREFS_NAME, MODE_PRIVATE);
    }

    public void storeString(String key, String value) {
        getPreferences().edit().putString(key, value).commit();
    }

    public String getStoredString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public RealmConfiguration getRealmConfiguration() {
        return configuration;
    }

    public static class gsonUTCdateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

        private final DateFormat dateFormat;

        public gsonUTCdateAdapter() {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'");      //This is the format I need
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));                               //This is the key line which converts the date to UTC which cannot be accessed with the default serializer
        }

        @Override
        public Date deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return dateFormat.parse(json.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Date src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(dateFormat.format(src));
        }
    }
}
