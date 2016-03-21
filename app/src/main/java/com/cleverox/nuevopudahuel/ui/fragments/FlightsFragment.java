package com.cleverox.nuevopudahuel.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzutils.BZUtils;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.controllers.FlightsController;
import com.cleverox.nuevopudahuel.model.Flight;
import com.cleverox.nuevopudahuel.ui.activities.AlertActivity;
import com.cleverox.nuevopudahuel.ui.adapter.FlightsAdapter;

import java.util.Date;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by moddity on 3/3/16.
 */
public class FlightsFragment extends HomeFragment implements View.OnClickListener, RealmChangeListener {

    public static final int EXTRA_MY_FLIGHTS = 0;
    public static final int EXTRA_FLIGTHS = 1;
    public static final int EXTRA_LLEGADAS = 2;

    private RealmResults<Flight> flightsResults;

    private int currentScreen;
    private FlightsAdapter adapter;
    LinearLayout salidas;
    LinearLayout llegadas;
    TextView origen, myFlightsTitle;
    EditText flights;
    private String searchText;
    private RecyclerView list;

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
        $(R.id.flights_search).setOnClickListener(this);

        flights = $(R.id.flights_editText);
        if (searchText != null) {
            flights.setText(searchText);
        }
        if(currentScreen == EXTRA_MY_FLIGHTS){
            $(R.id.flights_editText_container).setVisibility(View.GONE);
            $(R.id.flights_a).setVisibility(View.GONE);
            myFlightsTitle.setText(getText(R.string.menuOptionUserFlightsTitleKey));
            origen.setText(getString(R.string.flightHeaderOriginTitleKey) + "/" + getString(R.string.flightHeaderDestinationTitleKey));
        }
        else if(currentScreen == EXTRA_LLEGADAS){
            $(R.id.flights_salidas_container).setSelected(false);
            $(R.id.flights_llegadas_container).setSelected(true);
            origen.setText(getString(R.string.flightHeaderOriginTitleKey));
        }

        flights.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        flights.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                BZUtils.hideKeyboard(getBaseActivity());
                return false;
            }
        });

        flights.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = flights.getText().toString();
                adapter.setVols(configFlights());
                adapter.notifyDataSetChanged();
            }
        });

        list = $(R.id.flights_list);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);

        adapter = new FlightsAdapter(getBaseActivity());
        adapter.setOnItemListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flight vol = (Flight) v.getTag();
                Intent intent = new Intent(getBaseActivity(), AlertActivity.class);
                intent.putExtra(AlertActivity.EXTRA_FLIGHT, vol.getId());
                startActivity(intent);
            }
        });
        list.setAdapter(adapter);
        refreshList();
        salidas.setOnClickListener(this);
        llegadas.setOnClickListener(this);
    }

    private RealmResults<Flight> configFlights() {
        if (flightsResults != null)
            flightsResults.removeChangeListener(this);
        switch (currentScreen) {
            case EXTRA_FLIGTHS:
                if (!TextUtils.isEmpty(searchText))
                    flightsResults = FlightsController.getInstance().searchDepartures(getBaseActivity().getRealm(), searchText);
                else
                    flightsResults = FlightsController.getInstance().getDepartures(getBaseActivity().getRealm());
                break;
            case EXTRA_LLEGADAS:
                if (!TextUtils.isEmpty(searchText))
                    flightsResults = FlightsController.getInstance().searchArrivals(getBaseActivity().getRealm(), searchText);
                else
                    flightsResults = FlightsController.getInstance().getArrivals(getBaseActivity().getRealm());
                break;
            case EXTRA_MY_FLIGHTS:
                flightsResults = FlightsController.getInstance().getAllFavorites(getBaseActivity().getRealm());
                break;
        }
        if (flightsResults != null)
            flightsResults.addChangeListener(this);
        return flightsResults;
    }

    private void scrollToNextFlight() {
        if (adapter.getVols() != null) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                Flight flight = adapter.getVols().get(i);
                if (flight.getEstimated().after(new Date())) {
                    final int finalI = i;
                    list.post(new Runnable() {
                        @Override
                        public void run() {
                            list.scrollToPosition(finalI);
                            list.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flights_salidas_container:
                salidas.setSelected(true);
                llegadas.setSelected(false);
                origen.setText(getText(R.string.flightHeaderDestinationTitleKey));
                currentScreen = EXTRA_FLIGTHS;
                refreshList();
                break;
            case R.id.flights_llegadas_container:
                salidas.setSelected(false);
                llegadas.setSelected(true);
                origen.setText(getText(R.string.flightHeaderOriginTitleKey));
                currentScreen = EXTRA_LLEGADAS;
                refreshList();
                break;
            case R.id.flights_search:
                BZUtils.hideKeyboard(getBaseActivity());
                break;
        }
    }

    private void refreshList() {
        list.setVisibility(View.GONE);
        adapter.setVols(configFlights());
        adapter.notifyDataSetChanged();
        scrollToNextFlight();
    }

    @Override
    public void onChange() {
        adapter.setVols(configFlights());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        if (flightsResults != null)
            flightsResults.removeChangeListener(this);
        super.onDestroy();
    }
}
