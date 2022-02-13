package com.massiva.nuevopudahuel.controllers;

import android.content.Context;

import com.massiva.nuevopudahuel.api.RestCallback;
import com.massiva.nuevopudahuel.api.response.FavoritesResponse;
import com.massiva.nuevopudahuel.api.response.FlightResponse;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.model.Flight;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 15/3/16.
 */
public class FlightsController {
    private static FlightsController ourInstance = new FlightsController();

    public static FlightsController getInstance() {
        return ourInstance;
    }

    private FlightsController() {
    }

    public void requestArrivals(final PudahuelApplication application, final RestCallback<List<FlightResponse>> callback) {
        application.getService().getArrivals(UserController.getInstance().getFlightsAPIToken(application), new RestCallback<List<FlightResponse>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(List<FlightResponse> flights, Response response) {
                super.success(flights, response);
                storeFlights(application, flights, true);
                callback.success(flights, response);
            }
        });
    }

    public void requestDepartures(final PudahuelApplication application, final RestCallback<List<FlightResponse>> callback) {
        application.getService().getDepartures(UserController.getInstance().getFlightsAPIToken(application), new RestCallback<List<FlightResponse>>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(List<FlightResponse> flights, Response response) {
                super.success(flights, response);
                storeFlights(application, flights, false);
                callback.success(flights, response);
            }
        });
    }

    public void requestFavorites(final PudahuelApplication application, final RestCallback<FavoritesResponse> callback) {
        application.getService().getFavorites(UserController.getInstance().getFlightsAPIToken(application), new RestCallback<FavoritesResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(FavoritesResponse flights, Response response) {
                super.success(flights, response);
                storeFavorites(application, flights.getArrivals(), true);
                storeFavorites(application, flights.getDepartures(), false);
                callback.success(flights, response);
            }
        });
    }

    public void setFavorite(final PudahuelApplication application, final Flight flight, final RestCallback<FavoritesResponse> callback) {
        String type = flight.isArrival() ? "arrivals" : "departures";
        String favorite = flight.isFavorite() ? "unsubscribe" : "subscribe";

        //storeFavorite(application, flight);

        application.getService().setFavorite(UserController.getInstance().getFlightsAPIToken(application), type, flight.getId(), favorite, new RestCallback<FavoritesResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                callback.failure(error);
            }

            @Override
            public void success(FavoritesResponse favoritesResponse, Response response) {
                super.success(favoritesResponse, response);
                storeFavorite(application, flight);
                callback.success(favoritesResponse, response);
            }
        });

    }

    private void storeFlights(PudahuelApplication application, List<FlightResponse> response, boolean arrivals) {
        List<Flight> flights = new ArrayList<>();
        List<String> favoritesId = new ArrayList<>();
        Realm realm = Realm.getInstance(application.getRealmConfiguration());
        RealmResults<Flight> favorites = getAllFavorites(realm);
        if (favorites != null) {
            for (Flight favorite : favorites) {
                favoritesId.add(favorite.getId());
            }
        }
        long timeStamp = System.currentTimeMillis();
        for (FlightResponse flightResponse : response) {
            Flight flight = flightResponse.toFlight();
            flight.setArrival(arrivals);
            flight.setFavorite(favoritesId.contains(flight.getId()));
            flight.setTimestamp(timeStamp);
            flights.add(flight);
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(flights);
        realm.where(Flight.class).equalTo("arrival", arrivals).notEqualTo("timestamp", timeStamp).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    private void storeFavorites(PudahuelApplication application, List<FlightResponse> response, boolean arrivals) {
        Realm realm = Realm.getInstance(application.getRealmConfiguration());
/*        RealmResults<Flight> allFlights = realm.where(Flight.class).equalTo("arrival", arrivals).equalTo("favorite", true).findAll();
        if (allFlights != null) {
            List<Flight> flights = new ArrayList<>();
            flights.addAll(allFlights);
            realm.beginTransaction();
            for (Flight flight : flights) {
                flight.setFavorite(false);
            }
            realm.copyToRealmOrUpdate(flights);
            realm.commitTransaction();
        }*/
        List<Flight> flights = new ArrayList<>();
        for (FlightResponse flightResponse : response) {
            Flight flight = flightResponse.toFlight();
            flight.setFavorite(true);
            flights.add(flight);
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(flights);
        realm.commitTransaction();
        realm.close();
    }

    private void storeFavorite(PudahuelApplication application, Flight flight) {
        Realm realm = Realm.getInstance(application.getRealmConfiguration());
        realm.beginTransaction();
        flight.setFavorite(!flight.isFavorite());
        realm.commitTransaction();
        realm.close();
    }

    public RealmResults<Flight> getArrivals(Realm realm) {
        return realm.where(Flight.class).equalTo("arrival", true).findAllSorted("estimated");
    }

    public RealmResults<Flight> getDepartures(Realm realm) {
        return realm.where(Flight.class).equalTo("arrival", false).findAllSorted("estimated");
    }

    public RealmResults<Flight> getAllFavorites(Realm realm) {
        return realm.where(Flight.class).equalTo("favorite", true).findAllSorted("estimated");
    }

    public RealmResults<Flight> searchArrivals(Realm realm, String searchText) {
        return realm.where(Flight.class).equalTo("arrival", true)
                .beginGroup()
                    .contains("origin", searchText, Case.INSENSITIVE).or()
                    .contains("stopOver", searchText, Case.INSENSITIVE).or()
                    .contains("destination", searchText, Case.INSENSITIVE).or()
                    .contains("flightCode", searchText, Case.INSENSITIVE)
                .endGroup()
                .findAllSorted("estimated");
    }

    public RealmResults<Flight> searchDepartures(Realm realm, String searchText) {
        return realm.where(Flight.class).equalTo("arrival", false)
                .beginGroup()
                .contains("origin", searchText, Case.INSENSITIVE).or()
                .contains("stopOver", searchText, Case.INSENSITIVE).or()
                .contains("destination", searchText, Case.INSENSITIVE).or()
                .contains("flightCode", searchText, Case.INSENSITIVE)
                .endGroup()
                .findAllSorted("estimated");
    }

    public String capitalizeFirstLetter(String text) {
        if (text == null) return null;
        if (text.length() == 0) return "";
        String lower = text.toLowerCase();
        String upper =lower.substring(0,1).toUpperCase() + lower.substring(1);
        return upper;
    }

    public int getStatusColor(Context context, Flight flight) {
        try {
            int i = context.getResources().getIdentifier("flight_status_" + flight.getStatusId(), "color", context.getPackageName());
            if (i == 0) {
                return context.getResources().getIdentifier("flight_status_0", "color", context.getPackageName());
            }
            return i;
        } catch (Exception e) {
            return context.getResources().getIdentifier("flight_status_0", "color", context.getPackageName());
        }
    }
}
