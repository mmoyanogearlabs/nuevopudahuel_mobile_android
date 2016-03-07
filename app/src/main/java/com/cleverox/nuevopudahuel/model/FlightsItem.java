package com.cleverox.nuevopudahuel.model;

import java.util.Date;

/**
 * Created by moddity on 7/3/16.
 */
public class FlightsItem {

    private int id;
    private String origen, estado, tiempo;

    public FlightsItem(){}

    public FlightsItem (int id, String origen, String tiempo, String estado){
        this.id = id;
        this.origen = origen;
        this.tiempo = tiempo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
