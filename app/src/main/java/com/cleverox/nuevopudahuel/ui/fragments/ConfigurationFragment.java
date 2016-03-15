package com.cleverox.nuevopudahuel.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.model.ConfigurationItem;
import com.cleverox.nuevopudahuel.ui.adapter.ConfigurationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moddity on 15/3/16.
 */
public class ConfigurationFragment extends HomeFragment {

    public static ConfigurationFragment newInstance() {
        ConfigurationFragment fragment = new ConfigurationFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_configuration;
    }

    @Override
    protected void configView(View parentView) {

        RecyclerView list = $(R.id.configuration_list);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        ConfigurationAdapter adapter = new ConfigurationAdapter(getBaseActivity());
        adapter.setItems(ConfigItems());
        list.setAdapter(adapter);
    }

    private List<ConfigurationItem> ConfigItems () {
        List<ConfigurationItem> items = new ArrayList<>();

        ConfigurationItem infoGeneral = new ConfigurationItem(0, getString(R.string.categoryKeyGeneral), true);
        items.add(infoGeneral);
        ConfigurationItem cupones = new ConfigurationItem(1, getString(R.string.categoryKeyAll), true);
        items.add(cupones);
        ConfigurationItem turismo = new ConfigurationItem(2, getString(R.string.categoryKeyTurismo), false);
        items.add(turismo);
        ConfigurationItem transporte = new ConfigurationItem(3, getString(R.string.categoryKeyTransporte), false);
        items.add(transporte);
        ConfigurationItem hotel = new ConfigurationItem(4, getString(R.string.categoryKeyHotel), false);
        items.add(hotel);
        ConfigurationItem cambio = new ConfigurationItem(5, getString(R.string.categoryKeyCasasCambio), false);
        items.add(cambio);
        ConfigurationItem viaje = new ConfigurationItem(6, getString(R.string.categoryKeyAsistenciaViaje), false);
        items.add(viaje);
        ConfigurationItem jardinInfantil = new ConfigurationItem(7, getString(R.string.categoryKeyJardinInfantil), false);
        items.add(jardinInfantil);
        ConfigurationItem banco = new ConfigurationItem(8, getString(R.string.categoryKeyBanco), false);
        items.add(banco);

        return items;
    }
}
