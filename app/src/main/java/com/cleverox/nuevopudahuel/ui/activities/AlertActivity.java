package com.cleverox.nuevopudahuel.ui.activities;

import android.view.View;
import android.widget.TextView;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.model.FlightsItem;

/**
 * Created by moddity on 8/3/16.
 */
public class AlertActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_FLIGHT = "EXTRA_FLIGHT";

    private FlightsItem currentFlight;
    private TextView vuelo, tiempo, estado;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_alert;
    }

    @Override
    protected void configView() {
        $(R.id.btn_alerta_btncrear).setOnClickListener(this);
        $(R.id.btn_alerta_compartir).setOnClickListener(this);

        currentFlight = getIntent().getParcelableExtra(EXTRA_FLIGHT);

        vuelo = $(R.id.alerta_vuelo);
        tiempo = $(R.id.alerta_tiempo);
        estado = $(R.id.alerta_estado);

        vuelo.setText(currentFlight.getId() + "");
        tiempo.setText(currentFlight.getTiempo());
        estado.setText(currentFlight.getEstado());

        $(R.id.alert_btnback).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alert_btnback:
                onBackPressed();
                break;
        }
    }
}
