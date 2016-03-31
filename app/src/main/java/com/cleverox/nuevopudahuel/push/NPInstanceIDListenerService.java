package com.cleverox.nuevopudahuel.push;

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
        Intent intent = new Intent(this, GcmRegistrationIntentService.class);
        startService(intent);
    }
}
