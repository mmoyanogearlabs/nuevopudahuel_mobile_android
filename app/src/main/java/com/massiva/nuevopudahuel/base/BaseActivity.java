package com.massiva.nuevopudahuel.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.banners.BannerView;
import com.massiva.nuevopudahuel.controllers.SyncController;
import com.massiva.nuevopudahuel.ui.activities.DetailWebViewActivity;
import com.massiva.nuevopudahuel.ui.activities.SplashActivity;
import com.massiva.nuevopudahuel.ui.activities.VideoSplashActivity;
import com.massiva.nuevopudahuel.ui.custom.CustomProgressDialog;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;

import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by iaguila on 9/2/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements SyncController.OnSyncListener, BannerView.BannerInterface {
    private CustomProgressDialog dialog;

    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutResource() != 0)
            setContentView(getLayoutResource());

        configView();
    }

    public PudahuelApplication getPudahuelApplication() {
        return (PudahuelApplication) getApplication();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    protected <T extends View> T $(int viewId) {
        return (T) findViewById(viewId);
    }

    public void showProgressDialog() {
        dialog = new CustomProgressDialog(this);
        dialog.show();
    }

    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(this instanceof SplashActivity) && !(this instanceof VideoSplashActivity))
            SyncController.getInstance().setOnSyncListener(this);
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this);
    }

    @Override
    protected void onStop() {
        FlurryAgent.onEndSession(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }

        if (realm != null) {
            realm.close();
            realm = null;
        }

        super.onDestroy();
    }

    public Realm getRealm() {
        if (realm == null) {
            realm = Realm.getInstance(getPudahuelApplication().getRealmConfiguration());
        }

        return realm;
    }

    @Override
    public void onBannerClicked(String url) {
        Intent intent = new Intent(this, DetailWebViewActivity.class);
        intent.putExtra(DetailWebViewActivity.EXTRA_URL, url);
        startActivity(intent);
    }

    @Override
    public void onBannerSizeChanged(int height) {

    }

    @Override
    public void onSyncCompleted() {
//        WeatherController.getInstance().storeWeather(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected abstract int getLayoutResource();
    protected abstract void configView();
}
