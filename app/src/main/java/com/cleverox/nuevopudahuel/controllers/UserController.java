package com.cleverox.nuevopudahuel.controllers;

import android.os.AsyncTask;

import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.requestModel.TokenRequestBody;
import com.cleverox.nuevopudahuel.api.response.TokenResponse;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.constants.Constants;
import com.cleverox.nuevopudahuel.constants.PudahuelPrefs;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.UUID;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 14/3/16.
 */
public class UserController {

    private static UserController instance;

    public static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }

    public void startSyncProcess(BaseActivity context) {
        if (getStoredGoogleAID(context) == null)
            new getGoolgeAIDAsync().execute(context);
        else
            getAPIToken(context);
        //TODO: get Flights Info
        //TODO: init MOCA config
    }

    private void getAPIToken(final BaseActivity activity) {
        TokenRequestBody requestBody = new TokenRequestBody(getStoredGoogleAID(activity), Constants.FLIGHTS_API_CLIENT_ID, Constants.FLIGHTS_API_CLIENT_SECRET);
        activity.getPudahuelApplication().getService().getAccessToken(requestBody, new RestCallback<TokenResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }

            @Override
            public void success(TokenResponse tokenResponse, Response response) {
                super.success(tokenResponse, response);
                storedFlightsAPIToken(activity, tokenResponse.getAccessToken());
                SyncController.getInstance().startSync(activity);
            }
        });
    }

    private void storedFlightsAPIToken(BaseActivity activity, String token) {
        activity.getPudahuelApplication().storeString(PudahuelPrefs.FLIGHTS_API_TOKEN, token);
    }

    public String getFlightsAPIToken(BaseActivity activity) {
        return "Bearer " + activity.getPudahuelApplication().getStoredString(PudahuelPrefs.FLIGHTS_API_TOKEN, null);
    }

    private void storeGoogleAID(BaseActivity activity, String aid) {
        activity.getPudahuelApplication().storeString(PudahuelPrefs.STORED_AID, aid);
    }

    public String getStoredGoogleAID(BaseActivity activity) {
        return activity.getPudahuelApplication().getStoredString(PudahuelPrefs.STORED_AID, null);
    }

    private class getGoolgeAIDAsync extends AsyncTask<BaseActivity, Void, String> {

        private BaseActivity activity;

        @Override
        protected String doInBackground(BaseActivity... params) {
            activity = params[0];
            AdvertisingIdClient.Info idInfo = null;
            try {
                idInfo = AdvertisingIdClient.getAdvertisingIdInfo(activity);
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String advertId = null;
            try{
                advertId = idInfo.getId();
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            return advertId;
        }

        @Override
        protected void onPostExecute(String res) {
            if (res == null) {
                res = UUID.randomUUID().toString();
            }
            storeGoogleAID(activity, res);
            getAPIToken(activity);
        }
    }

}
