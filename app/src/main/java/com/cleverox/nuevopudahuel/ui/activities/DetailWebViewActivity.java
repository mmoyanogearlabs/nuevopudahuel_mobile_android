package com.cleverox.nuevopudahuel.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.ui.fragments.MenuWebviewFragment;

import java.net.URI;

/**
 * Created by moddity on 14/3/16.
 */
public class DetailWebViewActivity extends BaseActivity {

    public static final String EXTRA_URL = "EXTRA_URL";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_detail_webview;
    }

    @Override
    protected void configView() {
        Fragment targetFragment = MenuWebviewFragment.newInstance(getIntent().getStringExtra(EXTRA_URL));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.webview_fragment, targetFragment, targetFragment.getClass().getName())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


}

