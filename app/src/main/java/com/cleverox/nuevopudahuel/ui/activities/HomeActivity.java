package com.cleverox.nuevopudahuel.ui.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.controllers.SyncController;
import com.cleverox.nuevopudahuel.controllers.TrackingController;
import com.cleverox.nuevopudahuel.model.MenuItem;
import com.cleverox.nuevopudahuel.ui.adapter.MenuAdapter;
import com.cleverox.nuevopudahuel.ui.fragments.ConfigurationFragment;
import com.cleverox.nuevopudahuel.ui.fragments.DashboardFragment;
import com.cleverox.nuevopudahuel.ui.fragments.FlightsFragment;
import com.cleverox.nuevopudahuel.ui.fragments.MenuWebviewFragment;
import com.cleverox.nuevopudahuel.ui.fragments.NuevoPudahuelFragment;
import com.cleverox.nuevopudahuel.ui.fragments.PromocionesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moddity on 25/2/16.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView list;
    private DrawerLayout drawer;
    private ImageButton close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configMenu();
        changeFragment(DashboardFragment.newInstance());



    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void configView() {
        drawer = $(R.id.drawer_layout);
    }

    private void configMenu() {

        close = $(R.id.close_menu_button);
        close.setOnClickListener(this);

        list = $(R.id.menu_list);
        MenuAdapter adapter = new MenuAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        adapter.setItems(configMenuItems());
        adapter.setOnItemListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                switch (position) {

                    case 0: // HOME
                        TrackingController.trackEvent(TrackingController.FLURRY_HOME_EVENT);
                        changeFragment(DashboardFragment.newInstance());
                        break;
                    case 1: // MIS VUELOS
                        changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_MY_FLIGHTS));
                        break;
                    case 2: // VUELOS
                        changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_FLIGTHS));
                        break;
                    case 3: // PARKING
                        TrackingController.trackEvent(TrackingController.FLURRY_PARKING_EVENT);
                        changeFragment(MenuWebviewFragment.newInstance(getString(R.string.menuOptionParkingTitleKey), getString(R.string.menuOptionParkingUrlKey)));
                        break;
                    case 4: // ANTES DEL VUELO
                        TrackingController.trackEvent(TrackingController.FLURRY_ANTES_EVENT);
                        changeFragment(MenuWebviewFragment.newInstance(getString(R.string.menuOptionBeforeFlightTitleKey), getString(R.string.menuOptionBeforeUrlKey)));
                        break;
                    case 5: //EN EL AEROPUERTO
                        TrackingController.trackEvent(TrackingController.FLURRY_AEROPUERTO_EVENT);
                        changeFragment(MenuWebviewFragment.newInstance(getString(R.string.menuOptionAirportTitleKey), getString(R.string.menuOptionAirportUrlKey)));
                        break;
                    case 6: // PROMOCIONES
                        TrackingController.trackEvent(TrackingController.FLURRY_PROMOS_EVENT);
                        changeFragment(PromocionesFragment.newInstance());
                        break;
                    case 7: // CONFIGURACIÓN
                        TrackingController.trackEvent(TrackingController.FLURRY_CONFIG_EVENT);
                        changeFragment(ConfigurationFragment.newInstance());
                        break;
                    case 8: // NUEVOPUDAHUEL
                        TrackingController.trackEvent(TrackingController.FLURRY_NEW_EVENT);
                        changeFragment(NuevoPudahuelFragment.newInstance());
                        break;
                }
            }
        });
        list.setAdapter(adapter);
    }

    private List<MenuItem> configMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        MenuItem home = new MenuItem(0, R.drawable.iconmenuhome, getString(R.string.menuOptionHomeTitleKey));
        items.add(home);
        MenuItem myFlights = new MenuItem(1, R.drawable.iconmenualerta, getString(R.string.menuOptionUserFlightsTitleKey));
        items.add(myFlights);
        MenuItem flights = new MenuItem(2, R.drawable.iconmenuvuelos, getString(R.string.menuOptionFlightsTitleKey));
        items.add(flights);
        MenuItem parking = new MenuItem(3, R.drawable.iconmenuparking, getString(R.string.menuOptionParkingTitleKey));
        items.add(parking);
        MenuItem beforeFlight = new MenuItem(4, R.drawable.iconmenuantesvuelo, getString(R.string.menuOptionBeforeFlightTitleKey));
        items.add(beforeFlight);
        MenuItem airport = new MenuItem(5, R.drawable.iconmenuaeropuerto, getString(R.string.menuOptionAirportTitleKey));
        items.add(airport);
        MenuItem promo = new MenuItem(6, R.drawable.iconmenuqr,getString(R.string.menuOptionPromosTitleKey));
        items.add(promo);
        MenuItem config = new MenuItem(7, R.drawable.iconmenusettings,getString(R.string.menuOptionConfigTitleKey));
        items.add(config);
        MenuItem pudahuel = new MenuItem(8, R.drawable.iconmenuinfo, getString(R.string.menuOptionNewTitleKey));
        items.add(pudahuel);

        return items;
    }

    public void openMenu() {
        drawer.openDrawer(Gravity.RIGHT);
    }

    public void closeMenu() {
        drawer.closeDrawer(Gravity.RIGHT);
    }

    public void changeFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menuMain_fragment, targetFragment, targetFragment.getClass().getName())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        closeMenu();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT))
            closeMenu();
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_menu_button:
                closeMenu();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SyncController.getInstance().stopSync();
    }

}

