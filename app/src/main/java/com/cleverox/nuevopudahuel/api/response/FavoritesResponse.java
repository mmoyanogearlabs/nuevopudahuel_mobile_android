package com.cleverox.nuevopudahuel.api.response;

import com.cleverox.nuevopudahuel.model.Flight;

import java.util.List;

/**
 * Created by iaguila on 16/3/16.
 */
public class FavoritesResponse extends BaseResponse {

    private List<Flight> arrivals;
    private List<Flight> departures;

    public List<Flight> getArrivals() {
        return arrivals;
    }

    public List<Flight> getDepartures() {
        return departures;
    }
}
