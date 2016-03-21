package com.cleverox.nuevopudahuel.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.controllers.SyncController;
import com.cleverox.nuevopudahuel.ui.activities.SplashActivity;
import com.cleverox.nuevopudahuel.ui.activities.VideoSplashActivity;
import com.facebook.appevents.AppEventsLogger;

import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by iaguila on 9/2/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements SyncController.OnSyncListener {
    private ProgressDialog dialog;

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

    public void showProgressDialog(String message) {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage(message);
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
