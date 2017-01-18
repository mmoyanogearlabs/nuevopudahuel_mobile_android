package com.massiva.nuevopudahuel.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by iaguila on 18/1/17.
 */

public class Queue extends RealmObject {

    @PrimaryKey
    private int id = 0;
    private int international;
    private int national;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInternational() {
        return international;
    }

    public void setInternational(int international) {
        this.international = international;
    }

    public int getNational() {
        return national;
    }

    public void setNational(int national) {
        this.national = national;
    }
}
