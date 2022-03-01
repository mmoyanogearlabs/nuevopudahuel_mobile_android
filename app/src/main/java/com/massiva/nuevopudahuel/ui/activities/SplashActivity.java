package com.massiva.nuevopudahuel.ui.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.bzutils.BZUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.constants.PudahuelPrefs;
import com.massiva.nuevopudahuel.controllers.UserController;
import com.massiva.nuevopudahuel.push.GcmRegistrationIntentService;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initApp(true);

/*
        if (((PudahuelApplication) getApplicationContext()).isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            initApp(true);
        } else if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 11);
        }
        */
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

        //MOCA.setGeoTrackingEnabled(locationPermission);
        requestFCMToken();

        Intent intent = new Intent();
        ComponentName component = new ComponentName(this,GcmRegistrationIntentService.class);
        intent.setComponent(component);
        //GcmRegistrationIntentService.enqueueWork(this,intent);
/*        Intent intent = new Intent(this, GcmRegistrationIntentService.class);
        startService(intent);*/
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


    private void requestFCMToken() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        ((PudahuelApplication) getApplicationContext()).storeString(PudahuelPrefs.PUSH_TOKEN, token);
                        UserController.getInstance().registerPushToken((PudahuelApplication) getApplicationContext());
                        Log.d("newToken", token);
                    }
                });


/*
        String token = FirebaseInstanceId.getInstance().getToken();

        if (null != token) {
            ((PudahuelApplication) getApplicationContext()).storeString(PudahuelPrefs.PUSH_TOKEN, token);
            UserController.getInstance().registerPushToken((PudahuelApplication) getApplicationContext());

            // Log and toast

            Log.e("newToken", token);
        }
*/

    }



}
