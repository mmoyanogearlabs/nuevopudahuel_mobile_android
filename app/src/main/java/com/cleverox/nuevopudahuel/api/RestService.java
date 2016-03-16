package com.cleverox.nuevopudahuel.api;

import com.cleverox.nuevopudahuel.api.requestModel.TokenRequestBody;
import com.cleverox.nuevopudahuel.api.response.TokenResponse;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by iaguila on 02/02/2016.
 */
public interface RestService {

    @POST("/access_token")
    void getAccessToken(@Body TokenRequestBody requestBody, RestCallback<TokenResponse> callback);
}
