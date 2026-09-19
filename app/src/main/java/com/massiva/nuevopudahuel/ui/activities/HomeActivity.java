package com.massiva.nuevopudahuel.ui.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import com.bzutils.BZUtils;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.controllers.SyncController;
import com.massiva.nuevopudahuel.model.MenuItem;
import com.massiva.nuevopudahuel.ui.adapter.MenuAdapter;
import com.massiva.nuevopudahuel.ui.fragments.ConfigurationFragment;
import com.massiva.nuevopudahuel.ui.fragments.DashboardFragment;
import com.massiva.nuevopudahuel.ui.fragments.FlightsFragment;
import com.massiva.nuevopudahuel.ui.fragments.MenuWebviewFragment;
import com.massiva.nuevopudahuel.ui.fragments.NuevoPudahuelFragment;
import com.massiva.nuevopudahuel.ui.fragments.PromocionesFragment;

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

        getPudahuelApplication().setHomeAlive(true);

        if (Build.VERSION.SDK_INT >= 33) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 102);
            }
        }

        configMenu();
        handlePushIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handlePushIntent(intent);
    }

    private void handlePushIntent(Intent intent) {
        Log.d("FCM_DEBUG", "=== HomeActivity handlePushIntent ===");
        if (intent == null) {
            Log.d("FCM_DEBUG", "handlePushIntent: intent is null");
        } else {
            Log.d("FCM_DEBUG", "handlePushIntent action=" + intent.getAction());
            Log.d("FCM_DEBUG", "handlePushIntent component=" + (intent.getComponent() != null ? intent.getComponent().flattenToString() : "null"));
            android.os.Bundle extras = intent.getExtras();
            if (extras == null || extras.isEmpty()) {
                Log.d("FCM_DEBUG", "handlePushIntent extras=none");
            } else {
                for (String key : extras.keySet()) {
                    Log.d("FCM_DEBUG", "handlePushIntent extra[" + key + "]=" + String.valueOf(extras.get(key)));
                }
            }
        }

        String flightId = extractFlightId(intent);
        if (flightId != null && !flightId.isEmpty()) {
            Log.d("FCM_DEBUG", "HomeActivity deep-linking to AlertActivity with flight_id=" + flightId);
            Intent alertIntent = new Intent(this, AlertActivity.class);
            alertIntent.putExtra(AlertActivity.EXTRA_FLIGHT, flightId);
            alertIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(alertIntent);
            return;
        }

        if (intent != null && intent.getBooleanExtra("show_my_flights", false)) {
            changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_MY_FLIGHTS));
        } else if (getSupportFragmentManager().findFragmentById(R.id.menuMain_fragment) == null) {
            changeFragment(DashboardFragment.newInstance());
        }
    }

    private String extractFlightId(Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return null;
        }

        android.os.Bundle extras = intent.getExtras();
        String[] priorityKeys = new String[] {
                AlertActivity.EXTRA_FLIGHT,
                "flight_id", "flightId", "npg_id", "npgId",
                "flight_code", "flightCode", "flight_number", "flightNumber"
        };

        for (String key : priorityKeys) {
            if (extras.containsKey(key)) {
                Object value = extras.get(key);
                if (value != null) {
                    String stringValue = String.valueOf(value).trim();
                    if (stringValue != null && !stringValue.isEmpty()
                            && !stringValue.equalsIgnoreCase("null")
                            && !stringValue.equalsIgnoreCase("true")
                            && !stringValue.equalsIgnoreCase("false")) {
                        return stringValue;
                    }
                }
            }
        }

        for (String key : extras.keySet()) {
            Object value = extras.get(key);
            if (value instanceof String) {
                String stringValue = ((String) value).trim();
                if (stringValue.startsWith("{") && stringValue.endsWith("}")) {
                    try {
                        org.json.JSONObject json = new org.json.JSONObject(stringValue);
                        for (String pKey : priorityKeys) {
                            if (json.has(pKey)) {
                                String jsonValue = json.optString(pKey, "").trim();
                                if (jsonValue != null && !jsonValue.isEmpty()
                                        && !jsonValue.equalsIgnoreCase("null")
                                        && !jsonValue.equalsIgnoreCase("true")
                                        && !jsonValue.equalsIgnoreCase("false")) {
                                    return jsonValue;
                                }
                            }
                        }
                    } catch (org.json.JSONException ignored) {
                    }
                }
            }
        }

        String[] secondaryKeys = new String[] {"vuelo", "npg", "flight", "id", "code"};
        for (String key : secondaryKeys) {
            if (extras.containsKey(key)) {
                Object value = extras.get(key);
                if (value != null) {
                    String stringValue = String.valueOf(value).trim();
                    if (stringValue != null && !stringValue.isEmpty()
                            && !stringValue.equalsIgnoreCase("null")
                            && !stringValue.equalsIgnoreCase("true")
                            && !stringValue.equalsIgnoreCase("false")) {
                        return stringValue;
                    }
                }
            }
        }

        return null;
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
                        changeFragment(DashboardFragment.newInstance());
                        break;
                    case 1: // MIS VUELOS
                        changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_MY_FLIGHTS));
                        break;
                    case 2: // VUELOS
                        changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_LLEGADAS));
                        break;
                    case 3: // VUELOS
                        changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_FLIGTHS));
                        break;
                    case 4: // PARKING
                        changeFragment(MenuWebviewFragment.newInstance(getString(R.string.menuOptionParkingTitleKey), getString(R.string.menuOptionParkingUrlKey)));
                        break;
                    case 5: // ANTES DEL VUELO
                        changeFragment(MenuWebviewFragment.newInstance(getString(R.string.menuOptionBeforeFlightTitleKey), getString(R.string.menuOptionBeforeUrlKey)));
                        break;
                    case 6: //EN EL AEROPUERTO
                        changeFragment(MenuWebviewFragment.newInstance(getString(R.string.menuOptionAirportTitleKey), getString(R.string.menuOptionAirportUrlKey)));
                        break;
                    case 7: // PROMOCIONES




                        if (((PudahuelApplication)getApplicationContext()).isPermissionGranted(Manifest.permission.CAMERA)) {
                            changeFragment(PromocionesFragment.newInstance());
                        } else if (Build.VERSION.SDK_INT >= 23) {
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[] {Manifest.permission.CAMERA}, 11);
                        }

                        break;
                    case 8: // CONFIGURACIÓN
                        changeFragment(ConfigurationFragment.newInstance());
                        break;
                    case 9: // NUEVOPUDAHUEL
                        changeFragment(NuevoPudahuelFragment.newInstance());
                        break;
                }
            }
        });
        list.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                changeFragment(PromocionesFragment.newInstance());
            } else {
                BZUtils.showSimpleMessage(this, getString(R.string.permissionDeniedCamera));
            }
        }
    }

    private List<MenuItem> configMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        MenuItem home = new MenuItem(0, R.drawable.iconmenuhome, getString(R.string.menuOptionHomeTitleKey));
        items.add(home);
        MenuItem myFlights = new MenuItem(1, R.drawable.iconmenualerta, getString(R.string.menuOptionUserFlightsTitleKey));
        items.add(myFlights);
        MenuItem flightsArrives = new MenuItem(2, R.drawable.icons_airplane_landing, getString(R.string.menuOptionFlightsArrivesTitleKey));
        items.add(flightsArrives);
        MenuItem flightsDepartures = new MenuItem(3, R.drawable.icons_airplane_take_off, getString(R.string.menuOptionFlightsDeparturesTitleKey));
        items.add(flightsDepartures);
        MenuItem parking = new MenuItem(4, R.drawable.iconmenuparking, getString(R.string.menuOptionParkingTitleKey));
        items.add(parking);
        MenuItem beforeFlight = new MenuItem(5, R.drawable.iconmenuantesvuelo, getString(R.string.menuOptionBeforeFlightTitleKey));
        items.add(beforeFlight);
        MenuItem airport = new MenuItem(6, R.drawable.iconmenuaeropuerto, getString(R.string.menuOptionAirportTitleKey));
        items.add(airport);
        //MenuItem mapa = new MenuItem(9, R.drawable.iconmenuplanos, getString(R.string.menuOptionPlansTitleKey));
       // items.add(mapa);
        MenuItem promo = new MenuItem(7, R.drawable.iconmenuqr,getString(R.string.menuOptionPromosTitleKey));
        items.add(promo);
        /*
        MenuItem config = new MenuItem(7, R.drawable.iconmenusettings,getString(R.string.menuOptionConfigTitleKey));
        items.add(config);
        MenuItem pudahuel = new MenuItem(8, R.drawable.iconmenuinfo, getString(R.string.menuOptionNewTitleKey));
        items.add(pudahuel);
*/
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
                .commitAllowingStateLoss();
        closeMenu();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT))
            closeMenu();
        else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menuMain_fragment);
            if (fragment instanceof MenuWebviewFragment && ((MenuWebviewFragment) fragment).webview.canGoBack()) {
                ((MenuWebviewFragment) fragment).webview.goBack();
            } else if (!(fragment instanceof DashboardFragment)) {
                changeFragment(DashboardFragment.newInstance());
            } else {
                super.onBackPressed();
            }
        }
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
        getPudahuelApplication().setHomeAlive(false);
        SyncController.getInstance().stopSync();
        super.onDestroy();
    }

}

