package com.cleverox.nuevopudahuel.moca;

import com.innoquant.moca.MOCAAction;
import com.innoquant.moca.MOCAProximityService;

/**
 * Created by iaguila on 22/3/16.
 */
public class MocaEventListener implements MOCAProximityService.ActionListener {

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
        return false;
    }
}

