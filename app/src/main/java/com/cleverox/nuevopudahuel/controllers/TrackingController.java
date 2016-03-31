package com.cleverox.nuevopudahuel.controllers;

import com.flurry.android.FlurryAgent;

/**
 * Created by iaguila on 24/3/16.
 */
public class TrackingController {

    public static final String FLURRY_KEY = "W7DWR4XWDY77JY7C49QF";
    public static final String FLURRY_HOME_EVENT = "Home";
    public static final String FLURRY_SALIDAS_EVENT = "Vuelos/salidas";
    public static final String FLURRY_LLEGADAS_EVENT = "Vuelos/llegadas";
    public static final String FLURRY_MISVUELOS_EVENT = "Mis Viajes";
    public static final String FLURRY_PARKING_EVENT = "Parking";
    public static final String FLURRY_ANTES_EVENT = "Antes del Vuelo";
    public static final String FLURRY_AEROPUERTO_EVENT = "En el Aeropuerto";
    public static final String FLURRY_PROMOS_EVENT = "Promociones";
    public static final String FLURRY_CONFIG_EVENT = "Configuracion";
    public static final String FLURRY_NEW_EVENT = "Nuevo Pudahuel";

    public static void trackEvent(String eventKey) {
        trackFlurry(eventKey);
    }

    private static void trackFlurry(String eventKey) {
        FlurryAgent.logEvent(eventKey);
    }
}
