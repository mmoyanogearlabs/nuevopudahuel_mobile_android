package com.cleverox.nuevopudahuel.api.response;

import java.util.List;

/**
 * Created by iaguila on 16/3/16.
 */
public class FavoritesResponse extends BaseResponse {

    private List<FlightResponse> arrivals;
    private List<FlightResponse> departures;

    public List<FlightResponse> getArrivals() {
        return arrivals;
    }

    public List<FlightResponse> getDepartures() {
        return departures;
    }
}
