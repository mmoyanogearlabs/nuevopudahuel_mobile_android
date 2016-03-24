package com.cleverox.nuevopudahuel.push;

import android.content.Context;
import android.content.Intent;

import com.bzutils.LogBZ;
import com.innoquant.moca.push.GcmPushReceiver;

/**
 * Created by iaguila on 24/3/16.
 */
public class NPPushReceiver extends GcmPushReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        LogBZ.d("NPPushReceiver");

    }
}
