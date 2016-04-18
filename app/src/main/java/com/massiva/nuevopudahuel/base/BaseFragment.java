package com.massiva.nuevopudahuel.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.banners.BannerView;
import com.massiva.nuevopudahuel.ui.activities.HomeActivity;
import com.massiva.nuevopudahuel.ui.fragments.DashboardFragment;

/**
 * Created by iaguila on 9/2/16.
 */
public abstract class BaseFragment extends Fragment implements BannerView.BannerInterface {

    private View parentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResource(), container, false);

        if ($(R.id.bar_home_button) != null)
            $(R.id.bar_home_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((HomeActivity) getBaseActivity()).changeFragment(DashboardFragment.newInstance());
                }
            });
        configView(parentView);

        return parentView;
    }

    protected <T extends View> T $(@IdRes int resId) {
        if(parentView != null)
            return (T) parentView.findViewById(resId);
        return null;
    }

    @Override
    public void onBannerClicked(String url) {
        getBaseActivity().onBannerClicked(url);
    }

    @Override
    public void onBannerSizeChanged(int height) {

    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected abstract int getLayoutResource();
    protected abstract void configView(View parentView);

}
