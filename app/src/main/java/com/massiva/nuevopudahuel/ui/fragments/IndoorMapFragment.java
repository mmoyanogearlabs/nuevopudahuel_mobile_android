package com.massiva.nuevopudahuel.ui.fragments;

import android.os.Environment;
import android.view.View;
import android.webkit.WebView;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseFragment;

import java.io.File;
import java.util.Locale;

/**
 * Created by iaguila on 8/3/17.
 */

public class IndoorMapFragment extends BaseFragment implements View.OnClickListener {
    //Se contempla si ha hablido una actualización de mapas con su consecuente creación de la
    //carpeta y datos de los mismos en los datos de la apk o si se siguen visualizando los que
    //venían en los assets por defecto de la apk. Ver IndoorMapActivity.java para más detalle.
    private WebView webView;
    public static String publicPathParent;
    public static String publicPathAssets;
    public static String publicPathAssetsMaps;
    public static String publicPatAssetsMapsMapData;
    public static File baseUrlDirs;

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

        baseUrlDirs = getActivity().getFilesDir();
        baseUrlDirs.setReadable(true);
        baseUrlDirs.setWritable(true);

        publicPathParent = baseUrlDirs + "/DownloadedMaps/";
        publicPathAssets = baseUrlDirs + "/DownloadedMaps/assets/";
        publicPathAssetsMaps = baseUrlDirs + "/DownloadedMaps/assets/maps/";
        publicPatAssetsMapsMapData = baseUrlDirs + "/DownloadedMaps/assets/maps/MapData/";

        String folderPath;
        File dirParent = new File(publicPathParent);
        dirParent.setReadable(true);
        dirParent.setWritable(true);
        File dir = new File(publicPathAssetsMaps);


        if (dir.exists() == false) {
            //Path assets incluidos en la apk
            folderPath = "file:android_asset/maps/";
        } else {
            //Path que se creo como documentos de la app por obtener nuevos mapas
            folderPath = dir.getPath() + File.separator;
        }

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

        //Si viene de assets file se recrea normal, si no hay que añadir file:/// al path
        if (dir.exists() == false)
            webView.loadUrl(file);
        else {
            String fileCreateVB = "file://" + file;
            webView.loadUrl(fileCreateVB);
        }

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
