package com.cleverox.nuevopudahuel.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.bzutils.BZUtils;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.response.FavoritesResponse;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.controllers.FlightsController;
import com.cleverox.nuevopudahuel.model.Flight;

import io.realm.RealmChangeListener;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by moddity on 8/3/16.
 */
public class AlertActivity extends BaseActivity implements View.OnClickListener, RealmChangeListener {

    public static final String EXTRA_FLIGHT = "EXTRA_FLIGHT";

    private Flight currentFlight;
    private TextView vuelo, tiempo, estado;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alert_btnback:
                onBackPressed();
                break;
            case R.id.btn_alerta_btncrear:
                configFavorito();
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
