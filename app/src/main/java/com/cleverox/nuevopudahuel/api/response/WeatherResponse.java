package com.cleverox.nuevopudahuel.api.response;

import com.cleverox.nuevopudahuel.model.Weather;

/**
 * Created by iaguila on 16/3/16.
 */
public class WeatherResponse {

    private Double temperature;
    private String icon;

    public Weather getWeather() {
        Weather weather = new Weather();
        weather.setIcon(icon);
        try {
            weather.setTemperature(temperature.intValue());
        } catch (Exception e) {
            weather.setTemperature(0);
        }
        return weather;
    }
}
