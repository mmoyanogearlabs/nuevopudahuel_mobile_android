package com.massiva.nuevopudahuel.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by iaguila on 15/3/16.
 */
public class Weather extends RealmObject {

    @PrimaryKey
    private int id = 0;
    private int temperature;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
