package com.massiva.nuevopudahuel.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.bzutils.BZUtils;
import com.bzutils.LogBZ;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.constants.Constants;
import com.massiva.nuevopudahuel.model.Maps2DInfo;
import com.massiva.nuevopudahuel.ui.fragments.IndoorMapFragment;
import com.squareup.okhttp.internal.http.HttpConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

/**
 * Created by iaguila on 5/4/17.
 */

public class IndoorMapActivity extends BaseActivity {
    public static String pathToMapData = "/assets/MapData";

    public static Intent makeIntent(Context context) {
        return new Intent(context, IndoorMapActivity.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_indoor_map;
    }

    @Override
    protected void configView() {
        //SE COMPRUEBA LA EXISTENCIA DE NUEVOS PLANOS 2D PARA REEMPLAZAR LOS EXISTENTES

        Fragment fragment = IndoorMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.indoor_map_fragment, fragment, fragment.getClass().getName())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();

        //Miramos de descargar el json de los mapas para info
        new getDataIfisNewMaps2DAvariable().execute(null, null, null);

    }


    public void downloadMapZipFromUrl(String url, String outputFileName) {

        File dir = new File(Environment.getExternalStorageDirectory() + "/");
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        try {
            URL downloadUrl = new URL(url); // you can write any link here

            File file = new File(dir, outputFileName);
        /* Open a connection to that URL. */
            URLConnection ucon = downloadUrl.openConnection();

        /*
         * Define InputStreams to read from the URLConnection.
         */
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

        /*
         * Read bytes to the Buffer until there is nothing more to read(-1).
         */
            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

        /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Mirar de pasar por params aunque para pruebas no haría falta
    public class getDataIfisNewMaps2DAvariable extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(Constants.URL_GET_JSON_MAPS_2D_VERSIONS);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String devolucion = s;
            //Convertimos en JSON
            try {
                JSONObject mainObject = new JSONObject(s);

                if (mainObject != null) {
                    Maps2DInfo maps2DInfo = new Maps2DInfo();
                    JSONObject objectJson = mainObject.getJSONObject("checksums");
                    maps2DInfo.setCheckSum(objectJson.getString("json_map"));
                    objectJson = mainObject.getJSONObject("rescue");
                    JSONObject rescue = objectJson.getJSONObject("hack");
                    maps2DInfo.setUrlForDownloadZipMaps(rescue.getString("json_map"));
                    LogBZ.e(maps2DInfo.getUrlForDownloadZipMaps());

                    //Esto está declarado como array
                    JSONArray arrayJson;
                    arrayJson = mainObject.getJSONArray("shops-version");
                    objectJson = new JSONObject(arrayJson.getString(0));
                    JSONObject shopVersionIndividual = objectJson.getJSONObject("shops-version");
                    maps2DInfo.setShopsVersionUpdate(shopVersionIndividual.getString("shop_updatedate"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(maps2DInfo.getShopsVersionUpdate()));
                    maps2DInfo.setShopsUpdateDate(calendar.getTime());
                    //Fin de los parsings
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
