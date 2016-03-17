package com.cleverox.nuevopudahuel.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by iaguila on 9/2/16.
 */
public abstract class BaseFragment extends Fragment {

    private View parentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResource(), container, false);

        configView(parentView);

        return parentView;
    }

    protected <T extends View> T $(@IdRes int resId) {
        if(parentView != null)
            return (T) parentView.findViewById(resId);
        return null;
    }


    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected abstract int getLayoutResource();
    protected abstract void configView(View parentView);

}
