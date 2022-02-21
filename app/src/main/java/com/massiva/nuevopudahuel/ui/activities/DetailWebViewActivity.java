package com.massiva.nuevopudahuel.ui.activities;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.ui.fragments.MenuWebviewFragment;

/**
 * Created by moddity on 14/3/16.
 */
public class DetailWebViewActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_URL = "EXTRA_URL";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_detail_webview;
    }

    @Override
    protected void configView() {
        $(R.id.webview_btnback).setOnClickListener(this);
        Fragment targetFragment = MenuWebviewFragment.newInstance("", getIntent().getStringExtra(EXTRA_URL));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.webview_fragment, targetFragment, targetFragment.getClass().getName())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.webview_btnback:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.webview_fragment);
        if (((MenuWebviewFragment) fragment).webview.canGoBack()) {
            ((MenuWebviewFragment) fragment).webview.goBack();
        }else{
            super.onBackPressed();
        }
    }
}

