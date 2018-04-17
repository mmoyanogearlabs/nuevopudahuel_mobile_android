package com.massiva.nuevopudahuel.push;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.bzutils.LogBZ;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.constants.PudahuelPrefs;
import com.massiva.nuevopudahuel.controllers.UserController;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by iaguila on 30/3/16.
 */
public class GcmRegistrationIntentService extends JobIntentService {
    static final int JOB_ID = 1000;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, GcmRegistrationIntentService.class, JOB_ID, work);
    }

    public GcmRegistrationIntentService() {
      //  super("GcmRegistrationIntentService");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        LogBZ.d("PudahuelPush: onHandleIntent");
        try {
            String token = instanceID.getToken("950762352762",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            ((PudahuelApplication) getApplicationContext()).storeString(PudahuelPrefs.PUSH_TOKEN, token);
            UserController.getInstance().registerPushToken((PudahuelApplication) getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

  /*  @Override
    protected void onHandleIntent(Intent intent) {

    }*/

}
