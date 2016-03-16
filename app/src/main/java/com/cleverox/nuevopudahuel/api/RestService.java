package com.cleverox.nuevopudahuel.api;

import com.cleverox.nuevopudahuel.api.requestModel.TokenRequestBody;
import com.cleverox.nuevopudahuel.api.response.FavoritesResponse;
import com.cleverox.nuevopudahuel.api.response.TokenResponse;
import com.cleverox.nuevopudahuel.api.response.WeatherResponse;
import com.cleverox.nuevopudahuel.model.Flight;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by iaguila on 02/02/2016.
 */
public interface RestService {

    String HEADER_AUTHORIZATION = "Authorization";

    @POST("/access_token")
    void getAccessToken(@Body TokenRequestBody requestBody, RestCallback<TokenResponse> callback);

    @GET("/weather")
    void getWeather(@Header(HEADER_AUTHORIZATION) String token, RestCallback<WeatherResponse> callback);

    @GET("/arrivals")
    void getArrivals(@Header(HEADER_AUTHORIZATION) String token, RestCallback<List<Flight>> callback);

    @GET("/departures")
    void getDepartures(@Header(HEADER_AUTHORIZATION) String token, RestCallback<List<Flight>> callback);

    @GET("/subscriptions")
    void getFavorites(@Header(HEADER_AUTHORIZATION) String token, RestCallback<FavoritesResponse> callback);
}
