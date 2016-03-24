package com.cleverox.nuevopudahuel.api.services;

import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.requestModel.TokenRequestBody;
import com.cleverox.nuevopudahuel.api.response.CarrouselItemResponse;
import com.cleverox.nuevopudahuel.api.response.TokenResponse;
import com.cleverox.nuevopudahuel.banners.model.APIBanner;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by iaguila on 14/3/16.
 */
public interface BannerService {

    String HEADER_AUTHORIZATION = "Authorization";
    String RANDOM_AD = "/randomAd/";

    @POST("/access_token")
    void getAccessToken(@Body TokenRequestBody requestBody, RestCallback<TokenResponse> callback);

    @GET(RANDOM_AD + "{screen}")
    void getBanner(@Header(HEADER_AUTHORIZATION) String token, @Path("screen") String screen, RestCallback<APIBanner> callback);

    @GET("/carrousel")
    void getCarrousel(@Header(HEADER_AUTHORIZATION) String token, RestCallback<List<CarrouselItemResponse>> callback);
}
