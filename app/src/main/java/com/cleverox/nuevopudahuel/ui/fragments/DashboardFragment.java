package com.cleverox.nuevopudahuel.ui.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;


/**
 * Created by moddity on 29/2/16.
 */
public class DashboardFragment extends HomeFragment implements View.OnClickListener {

    private EditText searchFly;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dash_board;
    }

    @Override
    protected void configView(View parentView) {
        $(R.id.dash_salidas_container).setOnClickListener(this);
        $(R.id.dash_llegadas_container).setOnClickListener(this);
        $(R.id.dash_icon_lupa).setOnClickListener(this);
        $(R.id.dash_button_myflights).setOnClickListener(this);

        LinearLayout searchContainer = $(R.id.dash_search_container);
        Bitmap roundedLeft = BitmapFactory.decodeResource(getResources(), R.drawable.btnsalidashome);
        Bitmap roundedRight = BitmapFactory.decodeResource(getResources(), R.drawable.btnllegadashome);
        LinearLayout.LayoutParams searchContainerParams = new LinearLayout.LayoutParams(roundedLeft.getWidth() + roundedRight.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        searchContainerParams.gravity = Gravity.CENTER_HORIZONTAL;
        searchContainerParams.setMargins(0, (int) getResources().getDimension(R.dimen.padding_10), 0, (int) getResources().getDimension(R.dimen.padding_15));
        searchContainer.setLayoutParams(searchContainerParams);

        searchFly = $(R.id.dash_editText);
        searchFly.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchFly.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                    searchFlights();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dash_button_myflights:
                getHomeActivity().changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_FLIGTHS));
                break;
            case R.id.dash_icon_lupa:
                searchFlights();
                break;
            case R.id.dash_salidas_container:
                getHomeActivity().changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_FLIGTHS));
                break;
            case R.id.dash_llegadas_container:
                getHomeActivity().changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_LLEGADAS));
                break;
        }
    }

    private void searchFlights() {
        String vol = searchFly.getText().toString();
        if (!TextUtils.isEmpty(vol))
            getHomeActivity().changeFragment(FlightsFragment.newInstance(vol));
    }
}
