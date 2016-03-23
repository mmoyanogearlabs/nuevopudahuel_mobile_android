package com.cleverox.nuevopudahuel.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.ui.fragments.MenuWebviewFragment;

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
        Fragment targetFragment = MenuWebviewFragment.newInstance(getIntent().getStringExtra(EXTRA_URL));
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
}

