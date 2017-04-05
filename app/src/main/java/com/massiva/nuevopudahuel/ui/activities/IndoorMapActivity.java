package com.massiva.nuevopudahuel.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.ui.fragments.IndoorMapFragment;

/**
 * Created by iaguila on 5/4/17.
 */

public class IndoorMapActivity extends BaseActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, IndoorMapActivity.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_indoor_map;
    }

    @Override
    protected void configView() {

        Fragment fragment = IndoorMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.indoor_map_fragment, fragment, fragment.getClass().getName())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();

    }
}
