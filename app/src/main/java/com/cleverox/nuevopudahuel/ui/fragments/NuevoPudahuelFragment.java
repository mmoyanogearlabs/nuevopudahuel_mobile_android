package com.cleverox.nuevopudahuel.ui.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by moddity on 2/3/16.
 */
public class NuevoPudahuelFragment extends HomeFragment {

    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    private List<String> urls;

    public static NuevoPudahuelFragment newInstance(){
        NuevoPudahuelFragment fragment = new NuevoPudahuelFragment();
        fragment.urls = new ArrayList<>();
        fragment.urls.add("http://www.publimetro.cl/_internal/gxml!0/r0dc21o2f3vste5s7ezej9x3a10rp3w$mqonqhwdtut6p7w66p97v4zmvnwrre4/airportmerino.jpeg");
        fragment.urls.add("http://www.tropezon.cl/wp-content/uploads/2010/04/neruda_aeropuerto-1.jpg");
        fragment.urls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGKY90F8dBJHLDH9Y3E0ljiW8VVxJEUqvL3IMKoG9xNtSTd1ZL");
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.nuevo_pudahuel_fragment;
    }

    @Override
    protected void configView(View parentView) {
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
        for (int i = 0; i < urls.size(); i++) {
            ImageView image = new ImageView(getBaseActivity());
            image.setImageResource(R.drawable.circle_indicator_selector);
            image.setPadding(padding, 0, padding, 0);
            image.setSelected(i == viewPager.getCurrentItem());
            container.addView(image);
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.newInstance(urls.get(position));
        }

        @Override
        public int getCount() {
            if (urls == null) return 0;
            return urls.size();
        }
    }
}
