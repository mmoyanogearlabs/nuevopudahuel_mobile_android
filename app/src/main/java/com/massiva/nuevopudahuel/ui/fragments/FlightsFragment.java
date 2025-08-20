package com.massiva.nuevopudahuel.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bzutils.BZUtils;
import com.bzutils.LogBZ;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.banners.BannerView;
import com.massiva.nuevopudahuel.base.HomeFragment;
import com.massiva.nuevopudahuel.controllers.FlightsController;
import com.massiva.nuevopudahuel.controllers.SyncController;
import com.massiva.nuevopudahuel.model.Flight;
import com.massiva.nuevopudahuel.ui.activities.AlertActivity;
import com.massiva.nuevopudahuel.ui.activities.HomeActivity;
import com.massiva.nuevopudahuel.ui.adapter.FlightsAdapter;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by moddity on 3/3/16.
 */
public class FlightsFragment extends HomeFragment implements View.OnClickListener, RealmChangeListener, SwipeRefreshLayout.OnRefreshListener, SyncController.OnSyncListener {

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
    private SwipeRefreshLayout refreshLayout;
    private BannerView banner;
    private boolean searchFromHome;
    private SeekBar seekBar;

    private boolean showNational = true;


    public static FlightsFragment newInstance(String searchText) {
        FlightsFragment fragment = new FlightsFragment();
        fragment.currentScreen = EXTRA_FLIGTHS;
        fragment.searchText = searchText;

        fragment.searchFromHome = !(searchText == null || searchText == "");

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

        salidas = $(R.id.flights_national_container);//$(R.id.flights_salidas_container);
        llegadas = $(R.id.flights_international_container);//$(R.id.flights_llegadas_container);
        origen = $(R.id.flights_origen_button);
        seekBar = $(R.id.seekBar);
        Button yesterdayButton = $(R.id.yesterday_button);
        Button nowButton = $(R.id.now_button);
        Button todayButton = $(R.id.today_button);
        Button tomorrowButton = $(R.id.tomorrow_button);

        myFlightsTitle = $(R.id.flights_tittle);


        //$(R.id.flights_salidas_container).setSelected(true);
        $(R.id.flights_national_container).setSelected(true);

        $(R.id.flights_search).setOnClickListener(this);

        TextView flightCode = $(R.id.flights_vuelos_button);
        flightCode.setTextColor(Color.WHITE);
        TextView time = $(R.id.flights_tiempo_button);
        time.setTextColor(Color.WHITE);
        TextView status = $(R.id.flights_estado_button);
        status.setTextColor(Color.WHITE);
        origen.setTextColor(Color.WHITE);

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
            /*
            $(R.id.flights_salidas_container).setSelected(false);
            $(R.id.flights_llegadas_container).setSelected(true);
            */
            myFlightsTitle.setText(getText(R.string.homeArribalButtonTitleKey));
            origen.setText(getString(R.string.flightHeaderOriginTitleKey));
        } else {
            myFlightsTitle.setText(getText(R.string.homeDepartureButtonTitleKey));
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
        LinearLayoutManagerWithSmoothScroller manager = new LinearLayoutManagerWithSmoothScroller(getBaseActivity());
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

        refreshLayout = $(R.id.flightsRefreshLayout);
        refreshLayout.setOnRefreshListener(this);

        banner = $(R.id.flights_banner);
        banner.setBannerInterface(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                changedSelectedState(progress);

            }
        });

        yesterdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(0, true);
            }
        });
        nowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(1, true);
            }
        });
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(2, true);
            }
        });
        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(3, true);
            }
        });
        changedSelectedState(1);

    }

    private RealmResults<Flight> configFlights() {
        if (flightsResults != null)
            flightsResults.removeChangeListener(this);
        switch (currentScreen) {
            case EXTRA_FLIGTHS:
                if (!TextUtils.isEmpty(searchText))
                    flightsResults = FlightsController.getInstance().searchDepartures(getBaseActivity().getRealm(), searchText.trim(), !showNational);
                else
                    flightsResults = FlightsController.getInstance().getDepartures(getBaseActivity().getRealm(), !showNational);

                if (searchFromHome && flightsResults.size() == 0){
                    searchFromHome = false;
                    changeTabFromId(R.id.flights_national_container);
                    //changeTabFromId(R.id.flights_llegadas_container);
                }
                searchFromHome = false;

                break;
            case EXTRA_LLEGADAS:
                if (!TextUtils.isEmpty(searchText))
                    flightsResults = FlightsController.getInstance().searchArrivals(getBaseActivity().getRealm(), searchText.trim(), !showNational);
                else
                    flightsResults = FlightsController.getInstance().getArrivals(getBaseActivity().getRealm(), !showNational);
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
        if (flightsResults != null) {
            boolean found = false;
            for (int i = 0; i < flightsResults.size(); i++) {
                Flight flight = flightsResults.get(i);

                if (null != flight.getEstimated() && flight.getEstimated().after(new Date())) {
                    found = true;
                    final int finalI = i;
                    list.post(new Runnable() {
                        @Override
                        public void run() {
                            ((LinearLayoutManager)list.getLayoutManager()).scrollToPositionWithOffset(finalI, 0);
                            list.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                }
            }
            if (!found) list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        changeTabFromId(v.getId());
    }

    private void changeTabFromId(int type) {

        switch (type) {
            case R.id.flights_national_container:
                salidas.setSelected(true);
                llegadas.setSelected(false);
                //origen.setText(getText(R.string.homeWaitTimeNationalTitleKey));
                showNational = true;
                //currentScreen = EXTRA_FLIGTHS;
                refreshList();
               // seekBar.setProgress(1, true);
                break;
            case R.id.flights_international_container:
                salidas.setSelected(false);
                llegadas.setSelected(true);
                showNational = false;
               // origen.setText(getText(R.string.homeWaitTimeInternationalTitleKey));
                //currentScreen = EXTRA_LLEGADAS;
                refreshList();
                //seekBar.setProgress(1, true);
                break;
                /*
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
                */
            case R.id.flights_search:
                BZUtils.hideKeyboard(getBaseActivity());
                break;
        }
        seekBar.setProgress(1, true);
    }

    private void refreshList() {
        list.setVisibility(View.GONE);
        adapter.setVols(configFlights());
        adapter.notifyDataSetChanged();
        scrollToNextFlight();
    }
    private void changedSelectedState(int progress) {

        if (flightsResults.isEmpty()) {
            return;
        }

        int position = 0;
        switch (progress) {
            case 0:
                position = indexYesterday();
                ((LinearLayoutManagerWithSmoothScroller)list.getLayoutManager()).smoothScrollToTopPosition(list,position);

                break;
            case 1:
                 position = indexNow();
                list.smoothScrollToPosition(position);

                break;
            case 2:
                 position = indexToday();
                ((LinearLayoutManagerWithSmoothScroller)list.getLayoutManager()).smoothScrollToTopPosition(list,position);

                break;
            case 3:
                position = indexTomorrow();
                ((LinearLayoutManagerWithSmoothScroller)list.getLayoutManager()).smoothScrollToTopPosition(list,position);

                break;
            default:
                break;

        }

    }


    private int indexYesterday() {
        return 0;
    }


    private int indexNow() {
        int returnValue = 0;
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < flightsResults.size(); i++) {
            Flight flight = flightsResults.get(i);
            assert flight != null;
            Date estimated = flight.getEstimated();
            if (now.getTime() < estimated.getTime()) {
                break;
            }else {
                returnValue = i;

            }
        }
        return returnValue;
    }

    private int indexToday() {
        int returnValue = 0;
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < flightsResults.size(); i++) {
            Flight flight = flightsResults.get(i);
            assert flight != null;
            Date estimated = flight.getEstimated();
            Calendar calendarEstimated = Calendar.getInstance();
            calendarEstimated.setTime(estimated);
            int estimatedDay = calendarEstimated.get(Calendar.DAY_OF_MONTH);
            returnValue = i;

            if (currentDay == estimatedDay) {
                break;
            }
        }
        return returnValue;
    }


/*
    private int indexTomorrow() {
        int returnValue = 0;
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < flightsResults.size(); i++) {
            Flight flight = flightsResults.get(i);
            assert flight != null;
            Date estimated = flight.getEstimated();
            Calendar calendarEstimated = Calendar.getInstance();
            calendarEstimated.setTime(estimated);
            int estimatedDay = calendarEstimated.get(Calendar.DAY_OF_MONTH);
            returnValue += 1;
            if (currentDay != estimatedDay) {
                returnValue += 1;
                break;
            }
        }
        return returnValue > 1 ? returnValue - 2 : (Math.min(returnValue, 0));
    }
    */

    private int indexTomorrow() {
        int returnValue = 0;
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, 1);
        int dayTomorrow = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < flightsResults.size(); i++) {
            Flight flight = flightsResults.get(i);
            assert flight != null;
            Date estimated = flight.getEstimated();
            Calendar calendarEstimated = Calendar.getInstance();
            calendarEstimated.setTime(estimated);
            int estimatedDay = calendarEstimated.get(Calendar.DAY_OF_MONTH);
            returnValue = i;

            if (dayTomorrow == estimatedDay) {
                break;
            }
        }
        return returnValue;
    }

    @Override
    public void onBannerSizeChanged(int height) {
        super.onBannerSizeChanged(height);
        adapter.setBottomOffset(height);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        try {
            if (flightsResults != null)
                flightsResults.removeChangeListener(this);
        } catch (Exception e) {}
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        SyncController.getInstance().setOnSyncListener(this);
        SyncController.getInstance().startSync(getBaseActivity().getPudahuelApplication());
    }

    @Override
    public void onSyncCompleted() {
        LogBZ.d("onSyncCompleted");
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onChange(Object element) {
        adapter.setVols(configFlights());
        adapter.notifyDataSetChanged();
    }
}


