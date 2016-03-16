package com.cleverox.nuevopudahuel.api.requestModel;

/**
 * Created by iaguila on 15/3/16.
 */
public class TokenRequestBody {

    private String vendorId;
    private String clientId;
    private String clientSecret;

    public TokenRequestBody(String vendorId, String clientId, String clientSecret) {
        this.vendorId = vendorId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
