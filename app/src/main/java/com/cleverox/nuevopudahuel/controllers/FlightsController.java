package com.cleverox.nuevopudahuel.controllers;

import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.response.FavoritesResponse;
import com.cleverox.nuevopudahuel.base.BaseActivity;
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

    public void requestArrivals(BaseActivity activity, final RestCallback<List<Flight>> callback) {
        activity.getPudahuelApplication().getService().getArrivals(UserController.getInstance().getFlightsAPIToken(activity), new RestCallback<List<Flight>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(List<Flight> flights, Response response) {
                super.success(flights, response);
                callback.success(flights, response);
            }
        });
    }

    public void requestDepartures(BaseActivity activity, final RestCallback<List<Flight>> callback) {
        activity.getPudahuelApplication().getService().getDepartures(UserController.getInstance().getFlightsAPIToken(activity), new RestCallback<List<Flight>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(List<Flight> flights, Response response) {
                super.success(flights, response);
                callback.success(flights, response);
            }
        });
    }

    public void requestFavorites(BaseActivity activity, final RestCallback<FavoritesResponse> callback) {
        activity.getPudahuelApplication().getService().getFavorites(UserController.getInstance().getFlightsAPIToken(activity), new RestCallback<FavoritesResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(FavoritesResponse flights, Response response) {
                super.success(flights, response);
                callback.success(flights, response);
            }
        });
    }
}
