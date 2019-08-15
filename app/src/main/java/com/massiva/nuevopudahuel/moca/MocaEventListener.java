package com.massiva.nuevopudahuel.moca;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;

import com.innoquant.moca.MOCA;
import com.innoquant.moca.MOCAAction;
import com.innoquant.moca.MOCABeacon;
import com.innoquant.moca.MOCALabel;
import com.innoquant.moca.MOCAPlace;
import com.innoquant.moca.MOCAProximity;
import com.innoquant.moca.MOCAProximityService;
import com.innoquant.moca.MOCARegion;
import com.innoquant.moca.MOCAZone;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.push.NPPushReceiver;

import java.util.List;

/**
 * Created by iaguila on 22/3/16.
 */
public class MocaEventListener implements MOCAProximityService.EventListener,MOCAProximityService.ActionListener {

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

    @Override
    public void didEnterRange(MOCABeacon mocaBeacon, MOCAProximity mocaProximity) {
        Log.d("MocaEventListener", "didEnterRange");
    }

    @Override
    public void didExitRange(MOCABeacon mocaBeacon) {
        Log.d("MocaEventListener", "didExitRange");
    }

    @Override
    public void didBeaconProximityChange(MOCABeacon mocaBeacon, MOCAProximity mocaProximity, MOCAProximity mocaProximity1) {
        Log.d("MocaEventListener", "didBeaconProximityChange");
    }

    @Override
    public void didEnterPlace(MOCAPlace mocaPlace) {
        Log.d("MocaEventListener", "didEnterPlace");
    }

    @Override
    public void didExitPlace(MOCAPlace mocaPlace) {
        Log.d("MocaEventListener", "didExitPlace");
    }

    @Override
    public void didEnterZone(MOCAZone mocaZone) {
        Log.d("MocaEventListener", "didEnterZone");
    }

    @Override
    public void didExitZone(MOCAZone mocaZone) {
        Log.d("MocaEventListener", "didExitZone");
    }

    @Override
    public void didEnterLabel(MOCALabel mocaLabel, MOCARegion mocaRegion) {
        Log.d("MocaEventListener", "didEnterLabel");
    }

    @Override
    public void didExitLabel(MOCALabel mocaLabel, MOCARegion mocaRegion) {
        Log.d("MocaEventListener", "didExitLabel");
    }

    @Override
    public boolean handleCustomTrigger(String s) {
        return false;
    }

    @Override
    public void didLoadedBeaconsData(List<MOCABeacon> list) {
        Log.d("MocaEventListener", "didLoadedBeaconsData");
    }
}

