package com.cleverox.nuevopudahuel.controllers;

import android.os.AsyncTask;

import com.bzutils.LogBZ;
import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.response.FavoritesResponse;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.model.Flight;
import com.cleverox.nuevopudahuel.model.Weather;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 15/3/16.
 */
public class SyncController {
    private static SyncController ourInstance = new SyncController();

    public static SyncController getInstance() {
        return ourInstance;
    }

    private SyncController() {
    }

    private static final long SYNC_WAIT_MINUTES = 5;
    private static final long SYNC_WAIT_TIME = SYNC_WAIT_MINUTES * 60 * 1000;
    private long nextSyncTime;
    private boolean syncing;
    private boolean hasToSync = true;

    public void startSync(final BaseActivity activity) {
        if (!syncing) {
            syncing = true;
            WeatherController.getInstance().requestWeather(activity, new RestCallback<Weather>() {
                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    resetNextSync(activity);
                }

                @Override
                public void success(Weather weather, Response response) {
                    super.success(weather, response);
                    syncArrivals(activity);
                }
            });
        }
    }

    public void stopSync() {
        hasToSync = false;
    }

    private void syncArrivals(final BaseActivity activity) {
        FlightsController.getInstance().requestArrivals(activity, new RestCallback<List<Flight>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                resetNextSync(activity);
            }

            @Override
            public void success(List<Flight> flights, Response response) {
                super.success(flights, response);
                syncDepartures(activity);
            }
        });
    }

    private void syncDepartures(final BaseActivity activity) {
        FlightsController.getInstance().requestDepartures(activity, new RestCallback<List<Flight>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                resetNextSync(activity);
            }

            @Override
            public void success(List<Flight> flights, Response response) {
                super.success(flights, response);
                syncFavorites(activity);
            }
        });
    }

    private void syncFavorites(final BaseActivity activity) {
        FlightsController.getInstance().requestFavorites(activity, new RestCallback<FavoritesResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                resetNextSync(activity);
            }

            @Override
            public void success(FavoritesResponse favoritesResponse, Response response) {
                super.success(favoritesResponse, response);
                resetNextSync(activity);
            }
        });
    }

    private void resetNextSync(BaseActivity activity) {
        syncing = false;
        if (nextSyncTime == 0) {
            new nextSync().execute(activity);
        } else {
            nextSyncTime = System.currentTimeMillis() + SYNC_WAIT_TIME;
        }
    }

    private class nextSync extends AsyncTask<BaseActivity, Void, Boolean> {

        private BaseActivity activity;

        @Override
        protected Boolean doInBackground(BaseActivity... params) {
            activity = params[0];
            nextSyncTime = System.currentTimeMillis() + SYNC_WAIT_TIME;
            while (System.currentTimeMillis() < nextSyncTime) {}
            return hasToSync;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            LogBZ.d("SyncController: finish countdown");
            if (hasToSync) {
                nextSyncTime = 0;
                startSync(activity);
            }
        }
    }
}
