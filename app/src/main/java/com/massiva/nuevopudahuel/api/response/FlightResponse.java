package com.massiva.nuevopudahuel.api.response;

import com.massiva.nuevopudahuel.model.Flight;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by iaguila on 16/3/16.
 */
public class FlightResponse extends BaseResponse {

    @SerializedName("npg_id")
    private String id;
    private boolean secundaryFlight;
    private int statusId;
    private Date embark;
    private Date estimated;
    private Date scheduled;
    private String origin;
    private String destination;
    private String stopOver;
    private boolean international;
    private String flightCode;
    private String belt;
    private String statusText;
    private String mainFlightCode;
    @SerializedName("public_terminal")
    private String publicTerminal;
    private String gate;

    public Flight toFlight() {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setPublicTerminal(publicTerminal);
        flight.setSecundaryFlight(secundaryFlight);
        flight.setStatusId(statusId);
        flight.setEmbark(embark);
        flight.setEstimated(estimated);
        flight.setScheduled(scheduled);
        flight.setOrigin(origin);
        flight.setStopOver(stopOver);
        flight.setDestination(destination);
        flight.setInternational(international);
        flight.setFlightCode(flightCode);
        flight.setBelt(belt);
        flight.setStatusText(statusText);
        flight.setArrival(origin != null);
        flight.setMainFlightCode(mainFlightCode);
        flight.setGate(gate);
        return flight;
    }
}
