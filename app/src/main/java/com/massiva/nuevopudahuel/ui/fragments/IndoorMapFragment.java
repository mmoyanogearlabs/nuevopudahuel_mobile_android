package com.massiva.nuevopudahuel.ui.fragments;

import android.view.View;
import android.webkit.WebView;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseFragment;


import java.util.Locale;

/**
 * Created by iaguila on 8/3/17.
 */

public class IndoorMapFragment extends BaseFragment implements View.OnClickListener {

    private WebView webView;

    public static IndoorMapFragment newInstance() {
        return new IndoorMapFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_indoor_map;
    }

    @Override
    protected void configView(View parentView) {
        $(R.id.indoor_map_close).setOnClickListener(this);
        webView = $(R.id.indoor_map_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        String folderPath = "file:android_asset/maps/";

        // Get the HTML file name
        String language = null;
        switch (Locale.getDefault().getLanguage().toLowerCase()) {
            case "es":
            case "pt":
                language = Locale.getDefault().getLanguage().toLowerCase();
                break;
            default:
                language = "en";
        }
        String fileName = "index-" + language + ".html";

        // Get the exact file location
        String file = folderPath + fileName;

                /*
                    loadUrl(String url)
                        Loads the given URL.
                 */

        // Render the HTML file on WebView
        webView.loadUrl(file);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.indoor_map_close:
                getBaseActivity().finish();
                break;
        }
    }
}
