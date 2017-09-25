package com.massiva.nuevopudahuel.ui.fragments;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseFragment;


import java.io.File;
import java.util.Locale;

/**
 * Created by iaguila on 8/3/17.
 */

public class IndoorMapFragment extends BaseFragment implements View.OnClickListener {
    //Begin update maps
    //Antes de generar el webView, ahora tenemos que comprobar si hay datos para actualizar y reemplazar entera la carpeta /assets/maps/MapData por lo descargado del zip
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
        //Si no existe la carpeta de descargas de nuevos maps, irá a assets que esta en la apk y no puede reemplazarse
        //Ya tendremos hasta los assets copiados y todo.
        String folderPath;
        File dir = new File(getActivity().getFilesDir() + "/DownloadedMaps/assets/maps");

        if (dir.exists() == false) {
            folderPath = "file:android_asset/maps/";
        } else {
            folderPath = dir.getPath();
            File[] listado = dir.listFiles();
        }


        //folderPath = "file:android_asset/maps/";

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
        //Se hace un scale to fit del webview para que los controles de los botones quepan
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

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
