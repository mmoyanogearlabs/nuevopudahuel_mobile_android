package com.cleverox.nuevopudahuel.controllers;

import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.model.Weather;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 15/3/16.
 */
public class WeatherController {

    private static WeatherController ourInstance = new WeatherController();

    public static WeatherController getInstance() {
        return ourInstance;
    }

    private WeatherController() {
    }

    private Weather currentWeather;

    public void requestWeather(BaseActivity activity, final RestCallback<Weather> callback) {
        activity.getPudahuelApplication().getService().getWeather(UserController.getInstance().getFlightsAPIToken(activity), new RestCallback<Weather>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(Weather weather, Response response) {
                super.success(weather, response);
                setCurrentWeather(weather);
                callback.success(weather, response);
            }
        });
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
