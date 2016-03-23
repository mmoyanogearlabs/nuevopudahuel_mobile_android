package com.cleverox.nuevopudahuel.controllers;

import android.os.AsyncTask;

import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.requestModel.TokenRequestBody;
import com.cleverox.nuevopudahuel.api.response.TokenResponse;
import com.cleverox.nuevopudahuel.base.PudahuelApplication;
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

    public void startSyncProcess(PudahuelApplication context) {
        if (getStoredGoogleAID(context) == null)
            new getGoolgeAIDAsync().execute(context);
        else {
            getAPIToken(context);
            ConfigurationCategoriesController.getInstance().getCategoriesConfig(context);
        }
    }

    private void getAPIToken(final PudahuelApplication application) {
        TokenRequestBody requestBody = new TokenRequestBody(getStoredGoogleAID(application), Constants.FLIGHTS_API_CLIENT_ID, Constants.FLIGHTS_API_CLIENT_SECRET);
        application.getService().getAccessToken(requestBody, new RestCallback<TokenResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }

            @Override
            public void success(TokenResponse tokenResponse, Response response) {
                super.success(tokenResponse, response);
                storedFlightsAPIToken(application, tokenResponse.getAccessToken());
                SyncController.getInstance().startSync(application);
            }
        });
    }

    private void storedFlightsAPIToken(PudahuelApplication application, String token) {
        application.storeString(PudahuelPrefs.FLIGHTS_API_TOKEN, token);
    }

    public String getFlightsAPIToken(PudahuelApplication pudahuelApplication) {
        return "Bearer " + pudahuelApplication.getStoredString(PudahuelPrefs.FLIGHTS_API_TOKEN, null);
    }

    private void storeGoogleAID(PudahuelApplication application, String aid) {
        application.storeString(PudahuelPrefs.STORED_AID, aid);
    }

    public String getStoredGoogleAID(PudahuelApplication application) {
        return application.getStoredString(PudahuelPrefs.STORED_AID, null);
    }

    private class getGoolgeAIDAsync extends AsyncTask<PudahuelApplication, Void, String> {

        private PudahuelApplication application;

        @Override
        protected String doInBackground(PudahuelApplication... params) {
            application = params[0];
            AdvertisingIdClient.Info idInfo = null;
            try {
                idInfo = AdvertisingIdClient.getAdvertisingIdInfo(application);
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
            storeGoogleAID(application, res);
            getAPIToken(application);
            ConfigurationCategoriesController.getInstance().initCategoriesConfig(application);
        }
    }

}
