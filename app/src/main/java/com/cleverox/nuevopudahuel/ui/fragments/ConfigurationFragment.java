package com.cleverox.nuevopudahuel.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.model.ConfigurationItem;
import com.cleverox.nuevopudahuel.ui.adapter.ConfigurationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moddity on 15/3/16.
 */
public class ConfigurationFragment extends HomeFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ConfigurationAdapter adapter;

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
        adapter = new ConfigurationAdapter(getBaseActivity());
        adapter.setItems(ConfigItems());
        adapter.setClickListener(this);
        list.setAdapter(adapter);
    }

    private List<ConfigurationItem> ConfigItems () {
        List<ConfigurationItem> items = new ArrayList<>();

        ConfigurationItem infoGeneral = new ConfigurationItem(0, getString(R.string.categoryKeyGeneral), true);
        items.add(infoGeneral);
        ConfigurationItem todasCategorias = new ConfigurationItem(1, getString(R.string.categoryKeyAll), true);
        items.add(todasCategorias);
        ConfigurationItem turismo = new ConfigurationItem(2, getString(R.string.categoryKeyTurismo), true);
        items.add(turismo);
        ConfigurationItem transporte = new ConfigurationItem(3, getString(R.string.categoryKeyTransporte), true);
        items.add(transporte);
        ConfigurationItem hotel = new ConfigurationItem(4, getString(R.string.categoryKeyHotel), true);
        items.add(hotel);
        ConfigurationItem cambio = new ConfigurationItem(5, getString(R.string.categoryKeyCasasCambio), true);
        items.add(cambio);
        ConfigurationItem viaje = new ConfigurationItem(6, getString(R.string.categoryKeyAsistenciaViaje), true);
        items.add(viaje);
        ConfigurationItem jardinInfantil = new ConfigurationItem(7, getString(R.string.categoryKeyJardinInfantil), true);
        items.add(jardinInfantil);
        ConfigurationItem banco = new ConfigurationItem(8, getString(R.string.categoryKeyBanco), true);
        items.add(banco);
        ConfigurationItem parking = new ConfigurationItem(9, "Parking", true);
        items.add(parking);
        ConfigurationItem bar = new ConfigurationItem(10, "Bar", true);
        items.add(bar);

        return items;
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        boolean isChecked = !adapter.getItems().get(position).isChecked();
        adapter.getItems().get(position).setChecked(isChecked);
        switch (position) {
            case 0:
                break;
            case 1:
                for (int i = 2; i < adapter.getItemCount(); i ++) {
                    adapter.getItems().get(i).setChecked(isChecked);
                }
                break;
            default:
                if (!isChecked)
                    adapter.getItems().get(1).setChecked(false);
                else {
                    boolean allChecked = true;
                    for (int i = 2; i < adapter.getItemCount(); i ++) {
                        if (!adapter.getItems().get(i).isChecked()) {
                            allChecked = false;
                            break;
                        }
                    }
                    if (allChecked)
                        adapter.getItems().get(1).setChecked(true);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
