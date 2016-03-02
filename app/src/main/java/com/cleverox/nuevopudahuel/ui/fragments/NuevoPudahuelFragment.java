package com.cleverox.nuevopudahuel.ui.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;


/**
 * Created by moddity on 2/3/16.
 */
public class NuevoPudahuelFragment extends HomeFragment {

    private static int num_pages = 5;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;


    public static NuevoPudahuelFragment newInstance(){
        NuevoPudahuelFragment fragment = new NuevoPudahuelFragment();
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.nuevo_pudahuel_fragment;
    }

    @Override
    protected void configView(View parentView) {
        viewPager = $(R.id.view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return num_pages;
        }
    }
}
