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
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;    
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bzutils.BZUtils;
import com.bzutils.LogBZ;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.massiva.nuevopudahuel.BuildConfig;
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
import java.util.ArrayList;
import java.util.Date;

import io.realm.Case;
import io.realm.RealmChangeListener;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by moddity on 8/3/16.
 */
public class AlertActivity extends BaseActivity implements View.OnClickListener, RealmChangeListener {

    private static final Integer PERMISSION_CODE = 10000;

    public static final String EXTRA_FLIGHT = "EXTRA_FLIGHT";

    private static final String LINK_APP_STORE = "https://itunes.apple.com/us/app/aeropuerto-scl-chile-aplicacion/id1104983979?l=ca&ls=1&mt=8";
    private static final String LING_GOOGLE_PLAY = "https://play.google.com/store/apps/details?id=com.massiva.nuevopudahuel";

    private BannerView currentBanner;
    private Flight currentFlight;
    private TextView vuelo, tiempo, estado, terminal;
    private LinearLayout flightDetail;

    private String flightId = null;

    private String flightsDetailShareImageUri = null;

    private String[] requiredPermissionList = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_alert;
    }

    private Flight findFlight(String flightId) {
        if (flightId == null || flightId.trim().isEmpty()) {
            Log.d("FCM_DEBUG", "AlertActivity.findFlight: empty flight id received");
            return null;
        }
        String cleanId = flightId.trim();
        Log.d("FCM_DEBUG", "AlertActivity.findFlight: attempting lookup for flightId='" + cleanId + "'");

        Flight flight = getRealm().where(Flight.class).equalTo("id", cleanId).findFirst();
        if (flight != null) {
            Log.d("FCM_DEBUG", "AlertActivity.findFlight: matched by id='" + cleanId + "'");
            return flight;
        }

        flight = getRealm().where(Flight.class).equalTo("flightCode", cleanId, Case.INSENSITIVE).findFirst();
        if (flight != null) {
            Log.d("FCM_DEBUG", "AlertActivity.findFlight: matched by flightCode='" + cleanId + "'");
            return flight;
        }

        flight = getRealm().where(Flight.class).equalTo("mainFlightCode", cleanId, Case.INSENSITIVE).findFirst();
        if (flight != null) {
            Log.d("FCM_DEBUG", "AlertActivity.findFlight: matched by mainFlightCode='" + cleanId + "'");
            return flight;
        }

        String formattedInput = cleanId.replaceAll("\\s+", "");
        try {
            io.realm.RealmResults<Flight> allFlights = getRealm().where(Flight.class).findAll();
            for (Flight f : allFlights) {
                if (f.getFlightCode() != null) {
                    String storedCode = f.getFlightCode().replaceAll("\\s+", "");
                    if (storedCode.equalsIgnoreCase(formattedInput)) {
                        Log.d("FCM_DEBUG", "AlertActivity.findFlight: matched normalized flightCode='" + cleanId + "' to stored flight id='" + f.getId() + "'");
                        return f;
                    }
                }
                if (f.getMainFlightCode() != null) {
                    String storedMainCode = f.getMainFlightCode().replaceAll("\\s+", "");
                    if (storedMainCode.equalsIgnoreCase(formattedInput)) {
                        Log.d("FCM_DEBUG", "AlertActivity.findFlight: matched normalized mainFlightCode='" + cleanId + "' to stored flight id='" + f.getId() + "'");
                        return f;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("AlertActivity", "Error scanning flights for match", e);
        }

        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (getIntent() != null) {
            flightId = getIntent().getStringExtra(EXTRA_FLIGHT);
            Log.d("FCM_DEBUG", "AlertActivity.onNewIntent: received EXTRA_FLIGHT='" + flightId + "'");
            if (flightId != null) {
                currentFlight = findFlight(flightId);
                configFlight();
            }
        }
    }

    @Override
    protected void configView() {
        $(R.id.btn_alerta_btncrear).setOnClickListener(this);
        $(R.id.btn_alerta_compartir).setOnClickListener(this);

        vuelo = $(R.id.alerta_vuelo);
        tiempo = $(R.id.alerta_tiempo);
        estado = $(R.id.alerta_estado);
        terminal = $(R.id.alerta_terminal);

        currentBanner = $(R.id.alert_banner);
        currentBanner.setBannerInterface(this);

        flightId = getIntent().getStringExtra(EXTRA_FLIGHT);
        Log.d("FCM_DEBUG", "AlertActivity.configView: EXTRA_FLIGHT='" + flightId + "'");

        try {
            getRealm().addChangeListener(this);
        } catch (Exception e) {
            Log.e("AlertActivity", "Error adding Realm change listener", e);
        }

        if (flightId != null) {
            currentFlight = findFlight(flightId);
        }

        if (currentFlight != null) {
            Log.d("FCM_DEBUG", "AlertActivity.configView: matched currentFlight id='" + currentFlight.getId() + "'");
            configFlight();
        } else {
            Log.w("AlertActivity", "Flight not found yet for flightId: " + flightId);
            Log.d("FCM_DEBUG", "AlertActivity.configView: no currentFlight found for flightId='" + flightId + "'");
        }

        $(R.id.alert_btnback).setOnClickListener(this);
        flightDetail = $(R.id.alerta_flight_layout);
        flightDetail.setDrawingCacheEnabled(true);
    }

    private void configFlight() {
        if (currentFlight == null || !currentFlight.isValid()) {
            return;
        }

        try {
            if ($(R.id.alerta_created_layout) != null) {
                $(R.id.alerta_created_layout).setVisibility(currentFlight.isFavorite() ? View.VISIBLE : View.INVISIBLE);
            }
            if (vuelo != null) {
                vuelo.setText(currentFlight.getFlightCode() != null ? currentFlight.getFlightCode() : "");
            }
            if (tiempo != null) {
                tiempo.setText(currentFlight.getEstimated() != null ? BZUtils.dateToString(currentFlight.getEstimated(), "HH:mm") : "--:--");
            }
            if (estado != null) {
                estado.setText(currentFlight.getStatusText() != null ? currentFlight.getStatusText() : "");
                estado.setTextColor(getResources().getColor(FlightsController.getInstance().getStatusColor(this, currentFlight)));
            }
            TextView createAlert = $(R.id.alerta_create_text);
            if (createAlert != null) {
                createAlert.setText(currentFlight.isFavorite() ? getString(R.string.flightDetailDisableAlertTitleKey) : getString(R.string.flightDetailCreateAlertTitleKey));
                createAlert.setTextColor(Color.WHITE);
            }
            TextView origin = $(R.id.alerta_origin);
            if (origin != null) {
                origin.setText(currentFlight.isArrival() ? (currentFlight.getOrigin() != null ? currentFlight.getOrigin() : "") : getString(R.string.airportSantiagoChile));
                origin.setTextColor(Color.WHITE);
            }
            TextView destination = $(R.id.alerta_destination);
            if (destination != null) {
                destination.setText(currentFlight.isArrival() ? getString(R.string.airportSantiagoChile) : (currentFlight.getDestination() != null ? currentFlight.getDestination() : ""));
                destination.setTextColor(Color.WHITE);
            }
            TextView stopOver = $(R.id.alerta_stopover_text);
            if (stopOver != null) {
                stopOver.setText(currentFlight.getStopOver() != null ? currentFlight.getStopOver() : "");
                stopOver.setTextColor(Color.WHITE);
            }
            if ($(R.id.alerta_stopcover) != null) {
                $(R.id.alerta_stopcover).setVisibility(TextUtils.isEmpty(currentFlight.getStopOver()) ? View.INVISIBLE : View.VISIBLE);
            }
            TextView gateBeltTitle = $(R.id.alerta_gate_belt_title);
            if (gateBeltTitle != null) {
                gateBeltTitle.setText(currentFlight.isArrival() ? getString(R.string.flightDetailArrivalGateTitleKey) : getString(R.string.flightDetailDepartureGateTitleKey));
            }
            TextView gateBelt = $(R.id.alerta_gate_belt);
            if (gateBelt != null) {
                String val = currentFlight.isArrival() ? currentFlight.getBelt() : currentFlight.getGate();
                gateBelt.setText(val != null ? val : "-");
            }
            if (terminal != null) {
                terminal.setText(currentFlight.getPublicTerminal() != null ? currentFlight.getPublicTerminal() : "");
            }
        } catch (Exception e) {
            Log.e("AlertActivity", "Error in configFlight", e);
        }
    }

    private void configFavorito() {
        showProgressDialog();
        currentFlight = /*getRealm().copyFromRealm(*/(getRealm().where(Flight.class).equalTo("id", flightId).findFirst());//);


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
            }
        });

    }

    private void showFavoriteAlert() {
        try {
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
        } catch (Exception e) {
            LogBZ.printStackTrace(e);
        }
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

    private void showChooserPicker(final String[] values) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        File file = saveFlightDetailImage(flightDetail.getDrawingCache());
        Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);

        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.setAction(Intent.ACTION_SEND);

        sendIntent.putExtra(Intent.EXTRA_TEXT,getTextToShare());

        //sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/*");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

/*
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
        */

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

            File file = saveFlightDetailImage(flightDetail.getDrawingCache());
            Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,getTextToShare());
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/jpeg");
            intent.setPackage("com.twitter.android");
            startActivity(intent);

            /*

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("/*");
            intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
            intent.putExtra(Intent.EXTRA_TEXT, getTextToShare());

            File file = saveFlightDetailImage(flightDetail.getDrawingCache());
            Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);

            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(intent);

            */

        } catch (final ActivityNotFoundException e) {
            e.printStackTrace();
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

    private File saveFlightDetailImage(Bitmap bitmap) {
        long timestamp = new Date().getTime() / 1000;

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+ "/";

       // String filePath = getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/";
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
            return  finalFile;
            //return "file://" + finalFile.getPath();
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
                if (checkAndRequestPermission()) {
                    saveImage();
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
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
        File file = saveFlightDetailImage(flightContainer.getDrawingCache());
        if (file != null)
            showChooserPicker(new String[]{getString(R.string.flightDetailShareEmailTitleKey), getString(R.string.flightDetailShareFacebookTitleKey),
                    getString(R.string.flightDetailShareTwitterTitleKey)});
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        try {
            getRealm().removeChangeListener(this);
        } catch (Exception e) {
            // ignore
        }
        if (currentFlight != null && currentFlight.isValid()) {
            try {
                currentFlight.removeChangeListener(this);
            } catch (Exception e) {
                // ignore
            }
        }
        super.onDestroy();
    }

    @Override
    public void onChange(Object element) {
        if ((currentFlight == null || !currentFlight.isValid()) && flightId != null) {
            currentFlight = findFlight(flightId);
            if (currentFlight != null && currentFlight.isValid()) {
                try {
                    currentFlight.addChangeListener(this);
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        configFlight();
    }

    private Boolean checkAndRequestPermission()  {

        ArrayList<String> permissionsNeeded = new ArrayList<>();

        for (String permission : requiredPermissionList) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        if (permissionsNeeded.size() < requiredPermissionList.length) {
            ActivityCompat.requestPermissions(this, requiredPermissionList, PERMISSION_CODE);
            return false;
        }

        return true;

    }

}
