package com.massiva.nuevopudahuel.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bzutils.BZUtils;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.api.RestCallback;
import com.massiva.nuevopudahuel.api.response.FavoritesResponse;
import com.massiva.nuevopudahuel.banners.BannerView;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.controllers.FlightsController;
import com.massiva.nuevopudahuel.model.Flight;

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

    private static final String LINK_APP_STORE = "https://itunes.apple.com/us/app/aeropuerto-scl-chile-aplicacion/id1104983979?l=ca&ls=1&mt=8";
    private static final String LING_GOOGLE_PLAY = "https://play.google.com/store/apps/details?id=com.massiva.nuevopudahuel";

    private BannerView currentBanner;
    private Flight currentFlight;
    private TextView vuelo, tiempo, estado;
    private LinearLayout flightDetail;

    private String flightsDetailShareImageUri = null;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_alert;
    }

    @Override
    protected void configView() {
        $(R.id.btn_alerta_btncrear).setOnClickListener(this);
        $(R.id.btn_alerta_compartir).setOnClickListener(this);

        currentBanner = $(R.id.alert_banner);
        currentBanner.setBannerInterface(this);

        currentFlight = getRealm().where(Flight.class).equalTo("id", getIntent().getStringExtra(EXTRA_FLIGHT)).findFirst();
        currentFlight.addChangeListener(this);
        vuelo = $(R.id.alerta_vuelo);
        tiempo = $(R.id.alerta_tiempo);
        estado = $(R.id.alerta_estado);

        configFlight();

        $(R.id.alert_btnback).setOnClickListener(this);
        flightDetail = $(R.id.alerta_flight_layout);
        flightDetail.setDrawingCacheEnabled(true);
    }

    private void configFlight() {
        $(R.id.alerta_created_layout).setVisibility(currentFlight.isFavorite() ? View.VISIBLE : View.INVISIBLE);
        vuelo.setText(currentFlight.getFlightCode() + "");
        tiempo.setText(BZUtils.dateToString(currentFlight.getEstimated(), "HH:mm"));
        estado.setText(currentFlight.getStatusText());
        estado.setTextColor(getResources().getColor(FlightsController.getInstance().getStatusColor(this, currentFlight)));
        TextView createAlert = $(R.id.alerta_create_text);
        createAlert.setText(currentFlight.isFavorite() ? getString(R.string.flightDetailDisableAlertTitleKey) : getString(R.string.flightDetailCreateAlertTitleKey));
        createAlert.setTextColor(Color.WHITE);
        TextView origin = $(R.id.alerta_origin);
        origin.setText(currentFlight.isArrival() ? currentFlight.getOrigin() : getString(R.string.airportSantiagoChile));
        origin.setTextColor(Color.WHITE);
        TextView destination = $(R.id.alerta_destination);
        destination.setText(currentFlight.isArrival() ? getString(R.string.airportSantiagoChile) : currentFlight.getDestination());
        destination.setTextColor(Color.WHITE);
        TextView stopOver = $(R.id.alerta_stopover_text);
        stopOver.setText(currentFlight.getStopOver());
        stopOver.setTextColor(Color.WHITE);
        $(R.id.alerta_stopcover).setVisibility(TextUtils.isEmpty(currentFlight.getStopOver()) ? View.INVISIBLE : View.VISIBLE);
    }

    private void configFavorito() {
        showProgressDialog();
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
                        shareViaFacebook();
                        break;
                    case 2: //TWITTER
                        shareViaTwitter();
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

    private void shareViaTwitter() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("/*");
            intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
            intent.putExtra(Intent.EXTRA_TEXT, getTextToShare());
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(saveFlightDetailImage(flightDetail.getDrawingCache())));
            startActivity(intent);

        } catch (final ActivityNotFoundException e) {
            Toast.makeText(this, "You don't seem to have twitter installed on this device", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareViaFacebook() {
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            Bitmap image = flightDetail.getDrawingCache();
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .setCaption(getTextToShare())
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .setRef(getTextToShare())
                    .build();
            ShareDialog dialog = new ShareDialog(this);
            dialog.show(content);
        } else {
            Toast.makeText(this, "You don't seem to have facebook installed on this device", Toast.LENGTH_SHORT).show();
        }
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
                if (getPudahuelApplication().isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    saveImage();
                } else if (Build.VERSION.SDK_INT >= 23) {
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage();
            } else {
                BZUtils.showSimpleMessage(this, getString(R.string.permissionDeniedStorage));
            }
        }
    }

    private void saveImage() {
        LinearLayout flightContainer = $(R.id.alerta_flight_layout);
        flightContainer.setDrawingCacheEnabled(true);
        flightsDetailShareImageUri = saveFlightDetailImage(flightContainer.getDrawingCache());
        if (flightsDetailShareImageUri != null)
            showChooserPicker(new String[]{getString(R.string.flightDetailShareEmailTitleKey), getString(R.string.flightDetailShareFacebookTitleKey),
                    getString(R.string.flightDetailShareTwitterTitleKey)});
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
