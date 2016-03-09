package com.cleverox.nuevopudahuel.ui.fragments;

import android.view.View;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;

/**
 * Created by moddity on 8/3/16.
 */
public class MyFlightsFragment extends HomeFragment {

    public static MyFlightsFragment newInstance(){
        MyFlightsFragment fragment = new MyFlightsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_flights;
    }

    @Override
    protected void configView(View parentView) {

        $(R.id.flights_editText_container).setVisibility(View.GONE);
        $(R.id.flights_a).setVisibility(View.GONE);
     }


}
