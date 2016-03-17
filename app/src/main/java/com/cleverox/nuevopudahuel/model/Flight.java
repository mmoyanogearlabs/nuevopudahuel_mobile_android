package com.cleverox.nuevopudahuel.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by iaguila on 15/3/16.
 */
public class Flight extends RealmObject {

    @PrimaryKey
    private String id;
    private boolean secundaryFlight;
    private int statusId;
    private Date embark;
    private Date estimated;
    private Date scheduled;
    private String origin;
    private String stopOver;
    private String destination;
    private boolean international;
    private String flightCode;
    private String belt;
    private String statusText;
    private boolean arrival;
    private boolean favorite;
    private long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSecundaryFlight() {
        return secundaryFlight;
    }

    public void setSecundaryFlight(boolean secundaryFlight) {
        this.secundaryFlight = secundaryFlight;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getEmbark() {
        return embark;
    }

    public void setEmbark(Date embark) {
        this.embark = embark;
    }

    public Date getEstimated() {
        return estimated;
    }

    public void setEstimated(Date estimated) {
        this.estimated = estimated;
    }

    public Date getScheduled() {
        return scheduled;
    }

    public void setScheduled(Date scheduled) {
        this.scheduled = scheduled;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isInternational() {
        return international;
    }

    public void setInternational(boolean international) {
        this.international = international;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getStopOver() {
        return stopOver;
    }

    public void setStopOver(String stopOver) {
        this.stopOver = stopOver;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isArrival() {
        return arrival;
    }

    public void setArrival(boolean arrival) {
        this.arrival = arrival;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
