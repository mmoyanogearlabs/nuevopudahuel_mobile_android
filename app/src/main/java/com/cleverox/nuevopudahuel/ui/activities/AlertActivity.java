package com.cleverox.nuevopudahuel.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzutils.BZUtils;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.response.FavoritesResponse;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.controllers.FlightsController;
import com.cleverox.nuevopudahuel.model.Flight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import io.realm.RealmChangeListener;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by moddity on 8/3/16.
 */
public class AlertActivity extends BaseActivity implements View.OnClickListener, RealmChangeListener {

    public static final String EXTRA_FLIGHT = "EXTRA_FLIGHT";

    private static final String LINK_APP_STORE = "https://itunes.apple.com/us/app/aeropuerto-internacional-santiago/id1090347625?l=ca&ls=1&mt=8";
    private static final String LING_GOOGLE_PLAY = "https://play.google.com/store/apps/details?id=com.cleverox.nuevopudahuel";

    private Flight currentFlight;
    private TextView vuelo, tiempo, estado;

    private String flightsDetailShareImageUri = null;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_alert;
    }

    @Override
    protected void configView() {
        $(R.id.btn_alerta_btncrear).setOnClickListener(this);
        $(R.id.btn_alerta_compartir).setOnClickListener(this);

        currentFlight = getRealm().where(Flight.class).equalTo("id", getIntent().getStringExtra(EXTRA_FLIGHT)).findFirst();
        currentFlight.addChangeListener(this);
        vuelo = $(R.id.alerta_vuelo);
        tiempo = $(R.id.alerta_tiempo);
        estado = $(R.id.alerta_estado);

        configFlight();

        $(R.id.alert_btnback).setOnClickListener(this);

    }

    private void configFlight() {
        $(R.id.alerta_created_layout).setVisibility(currentFlight.isFavorite() ? View.VISIBLE : View.INVISIBLE);
        vuelo.setText(currentFlight.getFlightCode() + "");
        tiempo.setText(BZUtils.dateToString(currentFlight.getEstimated(), "HH:mm"));
        estado.setText(currentFlight.getStatusText());
        TextView createAlert = $(R.id.alerta_create_text);
        createAlert.setText(currentFlight.isFavorite() ? getString(R.string.flightDetailDisableAlertTitleKey) : getString(R.string.flightDetailCreateAlertTitleKey));
        TextView origin = $(R.id.alerta_origin);
        origin.setText(currentFlight.isArrival() ? currentFlight.getOrigin() : getString(R.string.airportSantiagoChile));
        TextView destination = $(R.id.alerta_destination);
        destination.setText(currentFlight.isArrival() ? getString(R.string.airportSantiagoChile) : currentFlight.getDestination());
    }

    private void configFavorito() {
        showProgressDialog(null);
        FlightsController.getInstance().setFavorite(getPudahuelApplication(), currentFlight, new RestCallback<FavoritesResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                dismissProgressDialog();
            }

            @Override
            public void success(FavoritesResponse favoritesResponse, Response response) {
                super.success(favoritesResponse, response);
                dismissProgressDialog();
                showFavoriteAlert();
            }
        });
    }

    private void showFavoriteAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(currentFlight.isFavorite() ? R.string.flightDetailAlertCreateTitleKey : R.string.flightDetailAlertDisableTitleKey);
        builder.setIcon(R.drawable.iconalertacreada);
        builder.setMessage(currentFlight.isFavorite() ? R.string.flightDetailAlertCreateMsgKey : R.string.flightDetailAlertDisableMsgKey);
        builder.setPositiveButton(R.string.flightDetailAlertCreateOkTitleKey, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private String getDestinationText() {
        String destination = currentFlight.getDestination();
        if (!TextUtils.isEmpty(currentFlight.getStopOver()))
            destination.concat("/" + currentFlight.getStopOver());
        return destination;
    }

    private String getOriginText() {
        String origin = currentFlight.getOrigin();
        if (!TextUtils.isEmpty(currentFlight.getStopOver()))
            origin.concat("/" + currentFlight.getStopOver());
        return origin;
    }

    private String getArrivalShareText() {
        return currentFlight.getFlightCode() + " - " + getString(R.string.shareOrigin1TitleKey) + " " + getOriginText() + " " + getString(R.string.shareOrigin2TitleKey)
                + " " + BZUtils.dateToString(currentFlight.getEstimated(), "dd/MM/yyyy HH:mm");
    }

    private String getDepartureShareText() {
        return currentFlight.getFlightCode() + " - " + getString(R.string.shareDestination1TitleKey) + " " + getDestinationText() + " " + getString(R.string.shareDestination2TitleKey)
                + " " + BZUtils.dateToString(currentFlight.getEstimated(), "dd/MM/yyyy HH:mm");
    }

    private String getTextToShare() {
        return currentFlight.isArrival() ? getArrivalShareText() : getDepartureShareText();
    }

    private String getMailBody() {
        return "<html><body>" + getTextToShare() + "<br><br><br>---<br>" + getString(R.string.shareEmail1TitleKey) + " <a href='" + LINK_APP_STORE +
                "'>" + getString(R.string.shareEmail2TitleKey) + "</a> " + getString(R.string.shareEmail3TitleKey) + " <a href='" + LING_GOOGLE_PLAY +
                "'>" + getString(R.string.shareEmail2TitleKey) + "</a></body></html>";
    }

    private void showChooserPicker(final String [] values) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: //EMAIL
                        shareViaEmail(Html.fromHtml(getMailBody()));
                        break;
                    case 1: //FACEBOOK
                        break;
                    case 2: //TWITTER
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void shareViaEmail(Spanned htmlBody) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.setPackage("com.google.android.gm");
        i.putExtra(Intent.EXTRA_TEXT, htmlBody);
        startActivity(i);
    }

    private String saveFlightDetailImage(Bitmap bitmap) {
        long timestamp = new Date().getTime() / 1000;
        String filePath = Environment.getExternalStorageDirectory() + "/NuevoPudahuel/";
        FileOutputStream out = null;
        try {
            File file = new File(filePath);
            if (!file.isDirectory() || !file.exists())
                file.mkdirs();
            File finalFile = new File(file, timestamp + ".png");
            out = new FileOutputStream(finalFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            MediaScannerConnection.scanFile(AlertActivity.this, new String[]{

                            finalFile.getAbsolutePath()},

                    null, new MediaScannerConnection.OnScanCompletedListener() {

                        public void onScanCompleted(String path, Uri uri)

                        {


                        }

                    });
            return "file://" + finalFile.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alert_btnback:
                onBackPressed();
                break;
            case R.id.btn_alerta_btncrear:
                configFavorito();
                break;
            case R.id.btn_alerta_compartir:
                LinearLayout flightContainer = $(R.id.alerta_flight_layout);
                flightContainer.setDrawingCacheEnabled(true);
                flightsDetailShareImageUri = saveFlightDetailImage(flightContainer.getDrawingCache());
                if (flightsDetailShareImageUri != null)
                    showChooserPicker(new String[]{getString(R.string.flightDetailShareEmailTitleKey), getString(R.string.flightDetailShareFacebookTitleKey),
                            getString(R.string.flightDetailShareTwitterTitleKey)});
                break;
        }
    }

    @Override
    public void onChange() {
        configFlight();
    }

    @Override
    protected void onDestroy() {
        currentFlight.removeChangeListener(this);
        super.onDestroy();
    }

}
