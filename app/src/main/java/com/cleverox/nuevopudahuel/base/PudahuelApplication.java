package com.cleverox.nuevopudahuel.base;

import android.app.Application;

import com.bzutils.LogBZ;
import com.cleverox.nuevopudahuel.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PudahuelApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initCalligraphy();
    }

    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(getString(R.string.khand_regular))
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
