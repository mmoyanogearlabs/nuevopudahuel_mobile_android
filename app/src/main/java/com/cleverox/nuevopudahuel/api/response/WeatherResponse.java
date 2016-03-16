package com.cleverox.nuevopudahuel.api.response;

import com.cleverox.nuevopudahuel.model.Weather;

/**
 * Created by iaguila on 16/3/16.
 */
public class WeatherResponse {

    private int temperature;
    private String icon;

    public Weather getWeather() {
        Weather weather = new Weather();
        weather.setIcon(icon);
        weather.setTemperature(temperature);
        return weather;
    }
}
