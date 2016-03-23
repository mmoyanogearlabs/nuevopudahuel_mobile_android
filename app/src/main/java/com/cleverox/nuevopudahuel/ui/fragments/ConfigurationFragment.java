package com.cleverox.nuevopudahuel.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.controllers.ConfigurationCategoriesController;
import com.cleverox.nuevopudahuel.ui.adapter.ConfigurationAdapter;

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
        adapter.setItems(ConfigurationCategoriesController.getInstance().getStoredCategories(getBaseActivity().getPudahuelApplication()));
        adapter.setClickListener(this);
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        boolean isChecked = !adapter.getItems().get(position).isValue();
        adapter.getItems().get(position).setValue(isChecked);
        switch (position) {
            case 0:
                break;
            case 1:
                for (int i = 2; i < adapter.getItemCount(); i ++) {
                    adapter.getItems().get(i).setValue(isChecked);
                }
                break;
            default:
                if (!isChecked)
                    adapter.getItems().get(1).setValue(false);
                else {
                    boolean allChecked = true;
                    for (int i = 2; i < adapter.getItemCount(); i ++) {
                        if (!adapter.getItems().get(i).isValue()) {
                            allChecked = false;
                            break;
                        }
                    }
                    if (allChecked)
                        adapter.getItems().get(1).setValue(true);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onPause() {
        ConfigurationCategoriesController.getInstance().uploadCategoriesToMOCA(getBaseActivity().getPudahuelApplication(), adapter.getItems());
        super.onPause();
    }
}
