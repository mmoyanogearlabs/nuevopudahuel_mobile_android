package com.cleverox.nuevopudahuel.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseFragment;

import java.util.List;

/**
 * Created by moddity on 2/3/16.
 */
public class ScreenSlidePageFragment extends BaseFragment {

    private String imageUrl;

    public static ScreenSlidePageFragment newInstance(String imageUrl) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.imageUrl = imageUrl;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.screen_slide_page_fragment;
    }

    @Override
    protected void configView(View parentView) {
        //Declarar imageview
        ImageView img = $(R.id.slide_image);
        getBaseActivity().getPudahuelApplication().loadImageUrl(imageUrl, img, 0);
    }
}
