package com.cleverox.nuevopudahuel.controllers;

import android.os.AsyncTask;

import com.bzutils.LogBZ;
import com.cleverox.nuevopudahuel.api.RestCallback;
import com.cleverox.nuevopudahuel.api.response.FavoritesResponse;
import com.cleverox.nuevopudahuel.api.response.FlightResponse;
import com.cleverox.nuevopudahuel.base.PudahuelApplication;
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

    public interface OnSyncListener {
        void onSyncCompleted();
    }

    private static final long SYNC_WAIT_MINUTES = 5;
    private static final long SYNC_WAIT_TIME = SYNC_WAIT_MINUTES * 60 * 1000;
    private long nextSyncTime;
    private boolean syncing;
    private boolean hasToSync = true;
    private OnSyncListener onSyncListener;

    public void startSync(final PudahuelApplication application) {
        if (!syncing) {
            syncing = true;
            WeatherController.getInstance().requestWeather(application, new RestCallback<Weather>() {
                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    syncArrivals(application);
                }

                @Override
                public void success(Weather weather, Response response) {
                    super.success(weather, response);
                    syncArrivals(application);
                }
            });
        }
    }

    public void stopSync() {
        hasToSync = false;
    }

    private void syncArrivals(final PudahuelApplication application) {
        FlightsController.getInstance().requestArrivals(application, new RestCallback<List<FlightResponse>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                syncDepartures(application);
            }

            @Override
            public void success(List<FlightResponse> flights, Response response) {
                super.success(flights, response);
                syncDepartures(application);
            }
        });
    }

    private void syncDepartures(final PudahuelApplication application) {
        FlightsController.getInstance().requestDepartures(application, new RestCallback<List<FlightResponse>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                syncFavorites(application);
            }

            @Override
            public void success(List<FlightResponse> flights, Response response) {
                super.success(flights, response);
                syncFavorites(application);
            }
        });
    }

    private void syncFavorites(final PudahuelApplication application) {
        FlightsController.getInstance().requestFavorites(application, new RestCallback<FavoritesResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                resetNextSync(application);
            }

            @Override
            public void success(FavoritesResponse favoritesResponse, Response response) {
                super.success(favoritesResponse, response);
                resetNextSync(application);
            }
        });
    }

    private void resetNextSync(PudahuelApplication application) {
        if (onSyncListener != null)
            onSyncListener.onSyncCompleted();
        syncing = false;
        if (nextSyncTime == 0) {
            new nextSync().execute(application);
        } else {
            nextSyncTime = System.currentTimeMillis() + SYNC_WAIT_TIME;
        }
    }

    public void setOnSyncListener(OnSyncListener onSyncListener) {
        LogBZ.d("setOnSyncListener: " + onSyncListener);
        this.onSyncListener = onSyncListener;
    }

    private class nextSync extends AsyncTask<PudahuelApplication, Void, Boolean> {

        private PudahuelApplication application;

        @Override
        protected Boolean doInBackground(PudahuelApplication... params) {
            application = params[0];
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
                startSync(application);
            }
        }
    }
}
