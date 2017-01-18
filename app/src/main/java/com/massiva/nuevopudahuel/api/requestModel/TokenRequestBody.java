package com.massiva.nuevopudahuel.api.requestModel;

/**
 * Created by iaguila on 15/3/16.
 */
public class TokenRequestBody {

    private String vendorId;
    private String clientId;
    private String clientSecret;
    private String locale;

    public TokenRequestBody(String vendorId, String clientId, String clientSecret, String locale) {
        this.vendorId = vendorId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.locale = locale;
    }
}
