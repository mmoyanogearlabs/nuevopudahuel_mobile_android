package com.massiva.nuevopudahuel.controllers;

import com.massiva.nuevopudahuel.api.RestCallback;
import com.massiva.nuevopudahuel.api.response.QueueResponse;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.model.Queue;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 18/1/17.
 */

public class QueuesController {

    private static QueuesController ourInstance = new QueuesController();

    public static QueuesController getInstance() {
        return ourInstance;
    }

    private QueuesController() {
    }

    public void requestQueues(final PudahuelApplication application, final RestCallback<QueueResponse> callback) {
        application.getService().getQueue(UserController.getInstance().getFlightsAPIToken(application), new RestCallback<QueueResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(QueueResponse queueResponse, Response response) {
                super.success(queueResponse, response);
                storeQueues(application, queueResponse);
                callback.success(queueResponse, response);
            }
        });
    }

    private void storeQueues(PudahuelApplication application, QueueResponse response) {
        if (response != null) {
            Realm realm = Realm.getInstance(application.getRealmConfiguration());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(response.toQueue());
            realm.commitTransaction();
        }
    }

    public Queue getQueues(Realm realm) {
        return realm.where(Queue.class).findFirst();
    }

}
