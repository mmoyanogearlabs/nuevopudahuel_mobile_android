package com.cleverox.nuevopudahuel.ui.fragments;
import android.view.View;
import android.webkit.WebView;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;


/**
 * Created by moddity on 1/3/16.
 */
public class MenuWebviewFragment extends HomeFragment {

    private String currentUrl;
    private WebView webview;

    public static MenuWebviewFragment newInstance(String url) {

        MenuWebviewFragment fragment = new MenuWebviewFragment();
        fragment.currentUrl = url;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.web_view_fragment;
    }

    @Override
    protected void configView(View parentView) {
        webview = $(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(currentUrl);
    }
}
