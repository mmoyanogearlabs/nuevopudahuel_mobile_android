package com.cleverox.nuevopudahuel.ui.fragments;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;


/**
 * Created by moddity on 1/3/16.
 */
public class MenuWebviewFragment extends HomeFragment {

    private String currentUrl;
    private String title;
    private WebView webview;

    public static MenuWebviewFragment newInstance(String title, String url) {

        MenuWebviewFragment fragment = new MenuWebviewFragment();
        fragment.title = title;
        fragment.currentUrl = url;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void configView(View parentView) {
        webview = $(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        getBaseActivity().showProgressDialog("");
        webview.setWebViewClient(new NPWebViewClient());
        webview.loadUrl(currentUrl);

        TextView viewTitle = $(R.id.flights_tittle);
        viewTitle.setText(title);
        $(R.id.flights_header_container).setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);

    }

    class NPWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            getBaseActivity().dismissProgressDialog();
        }
    }
}
