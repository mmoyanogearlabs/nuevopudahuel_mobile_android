package com.cleverox.nuevopudahuel.controllers;

import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.base.PudahuelApplication;
import com.cleverox.nuevopudahuel.model.Weather;

import io.realm.Realm;
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

    public void requestWeather(final PudahuelApplication application, final RestCallback<Weather> callback) {
        application.getService().getWeather(UserController.getInstance().getFlightsAPIToken(application), new RestCallback<Weather>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(Weather weather, Response response) {
                super.success(weather, response);
                storeWeather(application, weather);
                callback.success(weather, response);
            }
        });
    }

    private void storeWeather(PudahuelApplication application, Weather currentWeather) {
        Realm realm = Realm.getInstance(application.getRealmConfiguration());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(currentWeather);
        realm.commitTransaction();
    }
}
