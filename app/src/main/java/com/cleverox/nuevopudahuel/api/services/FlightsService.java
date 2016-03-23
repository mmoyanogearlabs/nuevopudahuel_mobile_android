package com.cleverox.nuevopudahuel.api.services;

import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.requestModel.TokenRequestBody;
import com.cleverox.nuevopudahuel.api.response.FavoritesResponse;
import com.cleverox.nuevopudahuel.api.response.FlightResponse;
import com.cleverox.nuevopudahuel.api.response.TokenResponse;
import com.cleverox.nuevopudahuel.api.response.WeatherResponse;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by iaguila on 02/02/2016.
 */
public interface FlightsService {

    String HEADER_AUTHORIZATION = "Authorization";

    @POST("/access_token")
    void getAccessToken(@Body TokenRequestBody requestBody, RestCallback<TokenResponse> callback);

    @GET("/weather")
    void getWeather(@Header(HEADER_AUTHORIZATION) String token, RestCallback<WeatherResponse> callback);

    @GET("/arrivals")
    void getArrivals(@Header(HEADER_AUTHORIZATION) String token, RestCallback<List<FlightResponse>> callback);

    @GET("/departures")
    void getDepartures(@Header(HEADER_AUTHORIZATION) String token, RestCallback<List<FlightResponse>> callback);

    @GET("/subscriptions")
    void getFavorites(@Header(HEADER_AUTHORIZATION) String token, RestCallback<FavoritesResponse> callback);

    @POST("/{type}/{flight_id}/{favorite}")
    void setFavorite(@Header(HEADER_AUTHORIZATION) String token, @Path("type") String type, @Path("flight_id") String flightId, @Path("favorite") String favorite,
                     RestCallback<FavoritesResponse> callback);
}
