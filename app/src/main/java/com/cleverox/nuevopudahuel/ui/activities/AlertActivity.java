package com.cleverox.nuevopudahuel.ui.activities;

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
        vuelo.setText(currentFlight.getId() + "");
        tiempo.setText(BZUtils.dateToString(currentFlight.getEstimated(), "HH:mm"));
        estado.setText(currentFlight.getStatusText());
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
            }
        });
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
