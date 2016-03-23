package com.cleverox.nuevopudahuel.model;

import android.content.Context;

import com.bzutils.LogBZ;

/**
 * Created by iaguila on 22/3/16.
 */
public class ConfigCategory {

    private String key;
    private boolean value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getText(Context context) {
        LogBZ.d("ConfigCategory getTextForKey: " + key);
        return context.getString(context.getResources().getIdentifier(key, "string", context.getPackageName()));
    }
}
