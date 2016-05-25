package com.massiva.nuevopudahuel.moca;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.innoquant.moca.MOCA;
import com.innoquant.moca.MOCAAction;
import com.innoquant.moca.MOCAProximityService;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.push.NPPushReceiver;

/**
 * Created by iaguila on 22/3/16.
 */
public class MocaEventListener implements MOCAProximityService.ActionListener {

    private Context context;

    public MocaEventListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean displayNotificationAlert(MOCAAction mocaAction, String s) {
        return false;
    }

    @Override
    public boolean openUrl(MOCAAction mocaAction, String s) {
        return false;
    }

    @Override
    public boolean showHtmlWithString(MOCAAction mocaAction, String s) {
        return false;
    }

    @Override
    public boolean playVideoFromUrl(MOCAAction mocaAction, String s) {
        return false;
    }

    @Override
    public boolean displayImageFromUrl(MOCAAction mocaAction, String s) {
        return false;
    }

    @Override
    public boolean displayPassFromUrl(MOCAAction mocaAction, String s) {
        return false;
    }

    @Override
    public boolean addTag(MOCAAction mocaAction, String s, String s1) {
        return false;
    }

    @Override
    public boolean playNotificationSound(MOCAAction mocaAction, String s) {
        return false;
    }

    @Override
    public boolean performCustomAction(MOCAAction mocaAction, String s) {
        if (s != null && s.equals("enter_place")) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
                NPPushReceiver.sendNotification(context, context.getString(R.string.bluetoothDisableNotification), context.getString(R.string.app_name));
                MOCA.getInstance().addTag("bluetooth_disabled", "+1");
            }
        }
        return false;
    }
}

