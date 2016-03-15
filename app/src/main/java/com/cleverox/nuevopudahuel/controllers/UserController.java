package com.cleverox.nuevopudahuel.controllers;

import android.content.Context;

/**
 * Created by iaguila on 14/3/16.
 */
public class UserController {

    private static UserController instance;

    public static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }

    public void startSyncProcess(Context context) {
        //TODO: get Google AID and store it
        //TODO: get Flights Info
        //TODO: init MOCA config
    }

}
