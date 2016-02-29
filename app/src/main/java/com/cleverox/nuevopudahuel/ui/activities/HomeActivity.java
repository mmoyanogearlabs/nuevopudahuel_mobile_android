package com.cleverox.nuevopudahuel.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.model.MenuItem;
import com.cleverox.nuevopudahuel.ui.fragments.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moddity on 25/2/16.
 */
public class HomeActivity extends BaseActivity {

    private DrawerLayout drawer;

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

    }

    private List<MenuItem> configMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        MenuItem myFlights = new MenuItem(0, R.drawable.iconmenualerta, getString(R.string.menuOptionFlightsTitleKey));
        items.add(myFlights);
        MenuItem flights = new MenuItem(1, R.drawable.iconmenuvuelos, getString(R.string.menuOptionFlightsTitleKey));
        items.add(flights);
        MenuItem parking = new MenuItem(2, R.drawable.iconmenuparking, getString(R.string.menuOptionParkingTitleKey));
        items.add(parking);
        MenuItem beforeFlight = new MenuItem(3, R.drawable.iconmenuantesvuelo, getString(R.string.menuOptionBeforeFlightTitleKey));
        items.add(beforeFlight);
        MenuItem airport = new MenuItem(4, R.drawable.iconmenuaeropuerto, getString(R.string.menuOptionAirportTitleKey));
        items.add(airport);
        MenuItem pudahuel = new MenuItem(5, R.drawable.iconmenuinfo, getString(R.string.menuOptionNewTitleKey));
        items.add(pudahuel);

        return items;
    }

    public void openMenu() {
        drawer.openDrawer(Gravity.RIGHT);
    }

    public void closeMenu() {
        drawer.closeDrawer(Gravity.RIGHT);
    }

    private void changeFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menuMain_fragment, targetFragment, targetFragment.getClass().getName())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT))
            closeMenu();
        else
            super.onBackPressed();
    }
}
