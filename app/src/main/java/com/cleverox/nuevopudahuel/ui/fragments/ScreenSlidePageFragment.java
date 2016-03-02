package com.cleverox.nuevopudahuel.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleverox.nuevopudahuel.R;

/**
 * Created by moddity on 2/3/16.
 */
public class ScreenSlidePageFragment extends NuevoPudahuelFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.screen_slide_page_fragment, container, false);

        return rootView;
    }
}
