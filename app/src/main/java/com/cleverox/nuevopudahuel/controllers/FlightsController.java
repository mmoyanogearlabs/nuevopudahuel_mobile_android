package com.cleverox.nuevopudahuel.controllers;

import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.response.FavoritesResponse;
import com.cleverox.nuevopudahuel.base.PudahuelApplication;
import com.cleverox.nuevopudahuel.model.Flight;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 15/3/16.
 */
public class FlightsController {
    private static FlightsController ourInstance = new FlightsController();

    public static FlightsController getInstance() {
        return ourInstance;
    }

    private FlightsController() {
    }

    private List<Flight> arrivals;
    private List<Flight> departures;
    private List<Flight> favoriteArrivals;
    private List<Flight> favoriteDepartures;

    public void requestArrivals(PudahuelApplication application, final RestCallback<List<Flight>> callback) {
        application.getService().getArrivals(UserController.getInstance().getFlightsAPIToken(application), new RestCallback<List<Flight>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(List<Flight> flights, Response response) {
                super.success(flights, response);
                setArrivals(flights);
                callback.success(flights, response);
            }
        });
    }

    public void requestDepartures(PudahuelApplication application, final RestCallback<List<Flight>> callback) {
        application.getService().getDepartures(UserController.getInstance().getFlightsAPIToken(application), new RestCallback<List<Flight>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(List<Flight> flights, Response response) {
                super.success(flights, response);
                setDepartures(flights);
                callback.success(flights, response);
            }
        });
    }

    public void requestFavorites(PudahuelApplication application, final RestCallback<FavoritesResponse> callback) {
        application.getService().getFavorites(UserController.getInstance().getFlightsAPIToken(application), new RestCallback<FavoritesResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(FavoritesResponse flights, Response response) {
                super.success(flights, response);
                setFavoriteArrivals(flights.getArrivals());
                setFavoriteDepartures(flights.getDepartures());
                callback.success(flights, response);
            }
        });
    }

    public void setArrivals(List<Flight> arrivals) {
        this.arrivals = arrivals;
    }

    public void setDepartures(List<Flight> departures) {
        this.departures = departures;
    }

    public void setFavoriteArrivals(List<Flight> favoriteArrivals) {
        this.favoriteArrivals = favoriteArrivals;
    }

    public void setFavoriteDepartures(List<Flight> favoriteDepartures) {
        this.favoriteDepartures = favoriteDepartures;
    }
}
