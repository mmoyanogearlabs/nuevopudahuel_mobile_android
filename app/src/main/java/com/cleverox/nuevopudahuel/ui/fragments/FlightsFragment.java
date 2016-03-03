package com.cleverox.nuevopudahuel.ui.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;

/**
 * Created by moddity on 3/3/16.
 */
public class FlightsFragment extends HomeFragment {

    public static FlightsFragment newInstance() {
        FlightsFragment fragment = new FlightsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.flights_fragment;
    }

    @Override
    protected void configView(View parentView) {

    }
}
