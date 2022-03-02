package com.massiva.nuevopudahuel.controllers;

import com.massiva.nuevopudahuel.api.RestCallback;
import com.massiva.nuevopudahuel.api.response.CarrouselItemResponse;
import com.massiva.nuevopudahuel.banners.BannerController;
import com.massiva.nuevopudahuel.banners.model.CarrouselItem;
import com.massiva.nuevopudahuel.base.PudahuelApplication;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 24/3/16.
 */
public class CarrouselController {
    private static CarrouselController ourInstance = new CarrouselController();

    public static CarrouselController getInstance() {
        return ourInstance;
    }

    private CarrouselController() {
    }

    public void requestCarrousel(final PudahuelApplication application) {
        application.getBannerService().getCarrousel(BannerController.getInstance().getBannersAPIToken(application), new RestCallback<List<CarrouselItemResponse>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                error.printStackTrace();
            }

            @Override
            public void success(List<CarrouselItemResponse> carrouselItems, Response response) {
                super.success(carrouselItems, response);
                storeCarrousel(application, carrouselItems);
            }
        });
    }

    private void storeCarrousel(PudahuelApplication application, List<CarrouselItemResponse> carrouselItems) {
        List<CarrouselItem> items = new ArrayList<>();
        for (CarrouselItemResponse response : carrouselItems) {
            items.add(response.toItem());
        }
        Realm realm = Realm.getInstance(application.getRealmConfiguration());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(items);
        realm.commitTransaction();
        realm.close();
    }

    public RealmResults<CarrouselItem> getStoredItems(Realm realm) {
        return realm.where(CarrouselItem.class).findAll().sort("position");
    }
}
