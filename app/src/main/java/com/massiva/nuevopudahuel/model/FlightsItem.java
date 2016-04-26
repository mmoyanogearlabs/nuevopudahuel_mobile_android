package com.massiva.nuevopudahuel.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moddity on 7/3/16.
 */
public class FlightsItem implements Parcelable {

    private int id;
    private String origen, estado, tiempo;

    public FlightsItem(){}

    public FlightsItem (int id, String origen, String tiempo, String estado){
        this.id = id;
        this.origen = origen;
        this.tiempo = tiempo;
        this.estado = estado;
    }

    protected FlightsItem(Parcel in) {
        id = in.readInt();
        origen = in.readString();
        estado = in.readString();
        tiempo = in.readString();
    }

    public static final Creator<FlightsItem> CREATOR = new Creator<FlightsItem>() {
        @Override
        public FlightsItem createFromParcel(Parcel in) {
            return new FlightsItem(in);
        }

        @Override
        public FlightsItem[] newArray(int size) {
            return new FlightsItem[size];
        }
    };

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

    public String   getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(origen);
        dest.writeString(estado);
        dest.writeString(tiempo);
    }
}
