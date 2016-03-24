package com.cleverox.nuevopudahuel.api.requestModel;

/**
 * Created by iaguila on 24/3/16.
 */
public class RegisterPushRequestBody {

    private String pushToken;
    private int platform = 0;

    public RegisterPushRequestBody(String pushToken) {
        this.pushToken = pushToken;
    }
}
