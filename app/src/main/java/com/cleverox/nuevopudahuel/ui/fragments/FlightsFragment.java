package com.cleverox.nuevopudahuel.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.model.FlightsItem;
import com.cleverox.nuevopudahuel.ui.activities.AlertActivity;
import com.cleverox.nuevopudahuel.ui.adapter.FlightsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moddity on 3/3/16.
 */
public class FlightsFragment extends HomeFragment implements View.OnClickListener {

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
        $(R.id.flights_salidas_container).setOnClickListener(this);
        $(R.id.flights_llegadas_container).setOnClickListener(this);

        RecyclerView list = $(R.id.flights_list);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        adapter = new FlightsAdapter(getBaseActivity());
        adapter.setVols(generateFakeFlights());
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
    }

    private List<FlightsItem> generateFakeFlights() {
        List<FlightsItem> items = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            items.add(new FlightsItem(i, "Origen " + i, "16:35", "Estado " + 1));
        }
        return items;
    }

    @Override
    public void onClick(View v) {

    }
}
