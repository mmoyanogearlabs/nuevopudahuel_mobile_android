package com.cleverox.nuevopudahuel.push;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by iaguila on 24/3/16.
 */
public class NPPushReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName componentName = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());

        startWakefulService(context,(intent.setComponent(componentName)));
        setResultCode(Activity.RESULT_OK);

    }

}
