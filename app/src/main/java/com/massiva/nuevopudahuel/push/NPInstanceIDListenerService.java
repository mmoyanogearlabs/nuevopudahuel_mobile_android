package com.massiva.nuevopudahuel.push;

import android.content.ComponentName;
import android.content.Intent;

import com.bzutils.LogBZ;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by iaguila on 30/3/16.
 */
public class NPInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        LogBZ.d("PudahuelPush: onTokenRefresh");
        //Intent intent = new Intent(this, GcmRegistrationIntentService.class);
        //startService(intent);
        Intent intent = new Intent();
        ComponentName component = new ComponentName(this,GcmRegistrationIntentService.class);
        intent.setComponent(component);
        GcmRegistrationIntentService.enqueueWork(this,intent);

    }
}
