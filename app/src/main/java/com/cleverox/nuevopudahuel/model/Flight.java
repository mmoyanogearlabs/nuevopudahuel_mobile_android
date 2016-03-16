package com.cleverox.nuevopudahuel.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by iaguila on 15/3/16.
 */
public class Flight {

    @SerializedName("_id")
    private String id;
    private boolean secundaryFlight;
    private int statusId;
    private Date embark;
    private Date estimated;
    private Date scheduled;
    private String origin;
    private boolean international;
    private String flightCode;
    private String belt;
    private String statusText;

    public String getId() {
        return id;
    }

    public boolean isSecundaryFlight() {
        return secundaryFlight;
    }

    public int getStatusId() {
        return statusId;
    }

    public Date getEmbark() {
        return embark;
    }

    public Date getEstimated() {
        return estimated;
    }

    public Date getScheduled() {
        return scheduled;
    }

    public String getOrigin() {
        return origin;
    }

    public boolean isInternational() {
        return international;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getBelt() {
        return belt;
    }

    public String getStatusText() {
        return statusText;
    }
}
