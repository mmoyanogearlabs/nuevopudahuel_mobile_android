package com.cleverox.nuevopudahuel.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.controllers.UserController;
import com.cleverox.nuevopudahuel.push.GcmRegistrationIntentService;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initApp();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void configView() {

    }

    private void initApp() {
        Intent intent = new Intent(this, GcmRegistrationIntentService.class);
        startService(intent);
        UserController.getInstance().startSyncProcess(getPudahuelApplication());

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
