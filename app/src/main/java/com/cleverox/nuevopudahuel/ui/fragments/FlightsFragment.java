package com.cleverox.nuevopudahuel.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.controllers.FlightsController;
import com.cleverox.nuevopudahuel.model.Flight;
import com.cleverox.nuevopudahuel.model.FlightsItem;
import com.cleverox.nuevopudahuel.ui.activities.AlertActivity;
import com.cleverox.nuevopudahuel.ui.adapter.FlightsAdapter;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by moddity on 3/3/16.
 */
public class FlightsFragment extends HomeFragment implements View.OnClickListener, RealmChangeListener {

    public static final int EXTRA_MY_FLIGHTS = 0;
    public static final int EXTRA_FLIGTHS = 1;
    public static final int EXTRA_LLEGADAS = 2;

    private RealmResults<Flight> arrivals, departures;

    private int currentScreen;
    private FlightsAdapter adapter;
    LinearLayout salidas;
    LinearLayout llegadas;
    TextView origen, myFlightsTitle;
    EditText flights;
    private String searchText;

    public static FlightsFragment newInstance(String searchText) {
        FlightsFragment fragment = new FlightsFragment();
        fragment.currentScreen = EXTRA_FLIGTHS;
        fragment.searchText = searchText;
        return fragment;
    }

    public static FlightsFragment newInstance(int currentScreen) {
        FlightsFragment fragment = new FlightsFragment();
        fragment.currentScreen = currentScreen;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_flights;
    }

    @Override
    protected void configView(View parentView) {

        salidas = $(R.id.flights_salidas_container);
        llegadas = $(R.id.flights_llegadas_container);
        origen = $(R.id.flights_origen_button);
        myFlightsTitle = $(R.id.flights_tittle);
        $(R.id.flights_salidas_container).setSelected(true);

        flights = $(R.id.flights_editText);
        if (searchText != null){
            flights.setText(searchText);
        }

        if(currentScreen == EXTRA_MY_FLIGHTS){
            $(R.id.flights_editText_container).setVisibility(View.GONE);
            $(R.id.flights_a).setVisibility(View.GONE);
            myFlightsTitle.setText(getText(R.string.menuOptionUserFlightsTitleKey));
        }
        else if(currentScreen == EXTRA_LLEGADAS){
            $(R.id.flights_salidas_container).setSelected(false);
            $(R.id.flights_llegadas_container).setSelected(true);
            origen.setText(getText(R.string.flightHeaderDestinationTitleKey));
        }

        flights = $(R.id.flights_editText);
        flights.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        flights.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });


        RecyclerView list = $(R.id.flights_list);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);

        arrivals = FlightsController.getInstance().getArrivals(getBaseActivity().getRealm());
        arrivals.addChangeListener(this);
        departures = FlightsController.getInstance().getDepartures(getBaseActivity().getRealm());
        departures.addChangeListener(this);

        adapter = new FlightsAdapter(getBaseActivity());
        adapter.setVols(configFlights());
        adapter.setOnItemListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightsItem vol = (FlightsItem) v.getTag();
                Intent intent = new Intent(getBaseActivity(), AlertActivity.class);
                intent.putExtra(AlertActivity.EXTRA_FLIGHT, vol);
                startActivity(intent);
            }
        });
        list.setAdapter(adapter);

        salidas.setOnClickListener(this);
        llegadas.setOnClickListener(this);
    }

    private RealmResults<Flight> configFlights() {
        switch (currentScreen) {
            case EXTRA_FLIGTHS:
                return departures;
            case EXTRA_LLEGADAS:
                return arrivals;
            case EXTRA_MY_FLIGHTS:
                break;
        }

        return FlightsController.getInstance().getArrivals(getBaseActivity().getRealm());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flights_salidas_container:
                salidas.setSelected(true);
                llegadas.setSelected(false);
                origen.setText(getText(R.string.flightHeaderOriginTitleKey));
                currentScreen = EXTRA_FLIGTHS;
                adapter.setVols(configFlights());
                adapter.notifyDataSetChanged();
                break;
            case R.id.flights_llegadas_container:
                salidas.setSelected(false);
                llegadas.setSelected(true);
                origen.setText(getText(R.string.flightHeaderDestinationTitleKey));
                currentScreen = EXTRA_LLEGADAS;
                adapter.setVols(configFlights());
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onChange() {
        adapter.setVols(configFlights());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        arrivals.removeChangeListener(this);
        departures.removeChangeListener(this);
        super.onDestroy();
    }
}
