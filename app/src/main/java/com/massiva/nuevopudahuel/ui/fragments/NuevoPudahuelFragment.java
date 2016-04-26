package com.massiva.nuevopudahuel.ui.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.banners.model.CarrouselItem;
import com.massiva.nuevopudahuel.base.HomeFragment;
import com.massiva.nuevopudahuel.controllers.CarrouselController;

import io.realm.RealmResults;


/**
 * Created by moddity on 2/3/16.
 */
public class NuevoPudahuelFragment extends HomeFragment {

    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    private RealmResults<CarrouselItem> items;

    public static NuevoPudahuelFragment newInstance(){
        NuevoPudahuelFragment fragment = new NuevoPudahuelFragment();
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_nuevo_pudahuel;
    }

    @Override
    protected void configView(View parentView) {
        items = CarrouselController.getInstance().getStoredItems(getBaseActivity().getRealm());
        viewPager = $(R.id.view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                configIndicator();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        configIndicator();
    }

    private void configIndicator() {
        LinearLayout container = $(R.id.pager_indicator_container);
        container.removeAllViews();
        int padding = (int) getResources().getDimension(R.dimen.padding_5);
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                ImageView image = new ImageView(getBaseActivity());
                image.setImageResource(R.drawable.circle_indicator_selector);
                image.setPadding(padding, 0, padding, 0);
                image.setSelected(i == viewPager.getCurrentItem());
                container.addView(image);
            }
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.newInstance(items.get(position));
        }

        @Override
        public int getCount() {
            if (items == null) return 0;
            return items.size();
        }
    }
}
