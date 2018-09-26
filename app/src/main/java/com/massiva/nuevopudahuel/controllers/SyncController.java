package com.massiva.nuevopudahuel.controllers;

import com.bzutils.LogBZ;
import com.massiva.nuevopudahuel.api.RestCallback;
import com.massiva.nuevopudahuel.api.response.FavoritesResponse;
import com.massiva.nuevopudahuel.api.response.FlightResponse;
import com.massiva.nuevopudahuel.api.response.QueueResponse;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.model.Weather;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private boolean syncing;
    private boolean hasToSync = true;
    private OnSyncListener onSyncListener;
    private Timer timer;

    public void startSync(final PudahuelApplication application) {
        if (!syncing) {
            syncing = true;
            UserController.getInstance().registerPushToken(application);
            WeatherController.getInstance().requestWeather(application, new RestCallback<Weather>() {
                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    syncArrivals(application);
                    //syncQueues(application);
                }

                @Override
                public void success(Weather weather, Response response) {
                    super.success(weather, response);
                    syncArrivals(application);
                    //syncQueues(application);
                }
            });
        }
    }

    public void stopSync() {
        hasToSync = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void syncQueues(final PudahuelApplication application) {
        QueuesController.getInstance().requestQueues(application, new RestCallback<QueueResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                syncArrivals(application);
            }

            @Override
            public void success(QueueResponse queueResponse, Response response) {
                super.success(queueResponse, response);
                syncArrivals(application);
            }
        });
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

    private void resetNextSync(final PudahuelApplication application) {
        if (onSyncListener != null)
            onSyncListener.onSyncCompleted();
        syncing = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (hasToSync) startSync(application);
            }
        }, SYNC_WAIT_TIME);
    }

    public void setOnSyncListener(OnSyncListener onSyncListener) {
        LogBZ.d("setOnSyncListener: " + onSyncListener);
        this.onSyncListener = onSyncListener;
    }

}
