package com.cleverox.nuevopudahuel.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.model.FlightsItem;
import com.cleverox.nuevopudahuel.ui.adapter.FlightsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moddity on 3/3/16.
 */
public class FlightsFragment extends HomeFragment {

    private FlightsAdapter adapter;

    public static FlightsFragment newInstance() {
        FlightsFragment fragment = new FlightsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_flights;
    }

    @Override
    protected void configView(View parentView) {
        RecyclerView list = $(R.id.flights_list);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        adapter = new FlightsAdapter(getBaseActivity());
        adapter.setVols(generateFakeFlights());
        list.setAdapter(adapter);
    }

    private List<FlightsItem> generateFakeFlights() {
        List<FlightsItem> items = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            items.add(new FlightsItem(i, "Origen " + i, "16:35", "Estado " + 1));
        }
        return items;
    }
}
