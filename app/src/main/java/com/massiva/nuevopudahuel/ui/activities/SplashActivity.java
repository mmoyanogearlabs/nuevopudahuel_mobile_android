package com.massiva.nuevopudahuel.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.bzutils.BZUtils;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.controllers.UserController;
import com.massiva.nuevopudahuel.push.GcmRegistrationIntentService;
import com.innoquant.moca.MOCA;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (((PudahuelApplication) getApplicationContext()).isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            initApp(true);
        } else if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 11);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void configView() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initApp(true);
            } else {
                BZUtils.showSimpleMessage(this, getString(R.string.permissionDeniedLocation));
                initApp(false);
            }
        }
    }

    private void initApp(boolean locationPermission) {

        MOCA.setGeoTrackingEnabled(locationPermission);

        Intent intent = new Intent(this, GcmRegistrationIntentService.class);
        startService(intent);
        UserController.getInstance().startSyncProcess(getPudahuelApplication());

        if (getIntent().getBooleanExtra("fromPush", false) && getPudahuelApplication().isHomeAlive()) {
            finish();
            return;
        }

        if (getPudahuelApplication().isAlive()) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
            return;
        }

        getPudahuelApplication().setAlive(true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, VideoSplashActivity.class));
                finish();
            }
        }, 1500);
    }

}
