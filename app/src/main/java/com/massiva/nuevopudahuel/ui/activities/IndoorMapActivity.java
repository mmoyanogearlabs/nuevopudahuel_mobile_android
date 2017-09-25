package com.massiva.nuevopudahuel.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.bzutils.LogBZ;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.constants.Constants;
import com.massiva.nuevopudahuel.constants.PudahuelPrefs;
import com.massiva.nuevopudahuel.model.Maps2DInfo;
import com.massiva.nuevopudahuel.ui.fragments.IndoorMapFragment;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by iaguila on 5/4/17.
 */

public class IndoorMapActivity extends BaseActivity {
    private Activity context;

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
        context = this;
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

        File dir = new File(context.getFilesDir() + "/");
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

            LogBZ.e(file.getParentFile().list().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Unzip a ZIP file, keeping the directory structure.
     *
     * @param zipFile        A valid ZIP file.
     * @param destinationDir The destination directory. It will be created if it doesn't exist.
     * @return {@code true} if the ZIP file was successfully decompressed.
     */
    public static boolean unzip(File zipFile, File destinationDir) {
        ZipFile zip = null;
        try {
            destinationDir.mkdirs();
            zip = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
            while (zipFileEntries.hasMoreElements()) {
                ZipEntry entry = zipFileEntries.nextElement();
                String entryName = entry.getName();
                File destFile = new File(destinationDir, entryName);
                File destinationParent = destFile.getParentFile();
                if (destinationParent != null && !destinationParent.exists()) {
                    destinationParent.mkdirs();
                }
                if (!entry.isDirectory()) {
                    BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
                    int currentByte;
                    byte data[] = new byte[5000];
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, 5000);
                    while ((currentByte = is.read(data, 0, 5000)) != -1) {//!=EOF
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (zip != null) {
                try {
                    zip.close();
                } catch (IOException ignored) {
                }
            }
        }
        return true;
    }

    //Mirar de pasar por params aunque para pruebas no haría falta
    public class getDataIfisNewMaps2DAvariable extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;
        boolean hayQueUpdatear = false;

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


            //Convertimos en JSON
            try {
                JSONObject mainObject = new JSONObject(result.toString());

                if (mainObject != null) {
                    Maps2DInfo maps2DInfo = new Maps2DInfo();

                    JSONObject objectJson = mainObject.getJSONObject("checksums");
                    maps2DInfo.setCheckSum(objectJson.getString("json_map"));

                    objectJson = mainObject.getJSONObject("rescue");
                    JSONObject rescue = objectJson.getJSONObject("hack");
                    maps2DInfo.setUrlForDownloadZipMaps(rescue.getString("json_map"));
                    LogBZ.e(maps2DInfo.getUrlForDownloadZipMaps());

                    //Esto está declarado como array.
                    JSONArray arrayJson;
                    arrayJson = mainObject.getJSONArray("shops-version");
                    objectJson = new JSONObject(arrayJson.getString(0));
                    JSONObject shopVersionIndividual = objectJson.getJSONObject("shops-version");
                    maps2DInfo.setShopsVersionUpdate(shopVersionIndividual.getString("shop_updatedate"));

                    //Guardo el update date si es diferente o el que tenía no existía.
                    if ((PudahuelPrefs.VERSION_MAPS_2D == null || PudahuelPrefs.VERSION_MAPS_2D.isEmpty())
                            || ((PudahuelApplication) context.getApplication()).getStoredString(PudahuelPrefs.VERSION_MAPS_2D, "").equals(maps2DInfo.getShopsVersionUpdate()) == false) {
                        ((PudahuelApplication) context.getApplication()).storeString(PudahuelPrefs.VERSION_MAPS_2D, maps2DInfo.getShopsVersionUpdate());

                        hayQueUpdatear = true;
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(maps2DInfo.getShopsVersionUpdate()));
                    maps2DInfo.setShopsUpdateDate(calendar.getTime());
                    //Fin de los parsings

                    //SI LA VERSIÓN ES MÁS NUEVA, ENTONCES HAREMOS LO DE ABAJO
                    //AQUI VIENEN CHECKSUM ETC, PERO DESPUES LO HARÉ.

                    if (hayQueUpdatear) {
                        //Borro el contenido de DownloadedMaps/ si existe
                        File dirToDelete = new File(context.getFilesDir() + "/DownloadedMaps");
                        if (dirToDelete.exists()) {
                            dirToDelete.delete();
                        }

                        //OBTENEMOS EL ZIP Y GUARDAMOS EN SISTEMA (FUNCIONA OK)
                        downloadMapZipFromUrl(maps2DInfo.getUrlForDownloadZipMaps(), "DownloadedMaps/NewMaps_" + maps2DInfo.getShopsVersionUpdate() + ".zip");


                        //Descomprimimos el zip
                        File origintToUnzip = new File(context.getFilesDir() + "/DownloadedMaps/NewMaps_" + maps2DInfo.getShopsVersionUpdate() + ".zip");
                        File destinationUnzipFilesDir = new File(context.getFilesDir() + "/DownloadedMaps/");
                        unzip(origintToUnzip, destinationUnzipFilesDir);
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "KO";
            } catch (Exception e) {
                e.printStackTrace();
                return "KO";
            }

            return "OK";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("MAPAS OBTENIDOS");
            builder.create();
            builder.show();
        }
    }
}
