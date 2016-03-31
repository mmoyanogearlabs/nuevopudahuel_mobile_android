package com.cleverox.nuevopudahuel.push;

import android.app.IntentService;
import android.content.Intent;

import com.bzutils.LogBZ;
import com.cleverox.nuevopudahuel.base.PudahuelApplication;
import com.cleverox.nuevopudahuel.constants.PudahuelPrefs;
import com.cleverox.nuevopudahuel.controllers.UserController;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by iaguila on 30/3/16.
 */
public class GcmRegistrationIntentService extends IntentService {

    public GcmRegistrationIntentService() {
        super("GcmRegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
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

}
