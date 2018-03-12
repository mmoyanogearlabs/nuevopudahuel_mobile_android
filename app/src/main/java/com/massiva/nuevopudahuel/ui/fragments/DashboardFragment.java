package com.massiva.nuevopudahuel.ui.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzutils.BZUtils;
import com.bzutils.LogBZ;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.banners.BannerView;
import com.massiva.nuevopudahuel.base.HomeFragment;
import com.massiva.nuevopudahuel.controllers.WeatherController;
import com.massiva.nuevopudahuel.model.Weather;

import io.realm.RealmChangeListener;


/**
 * Created by moddity on 29/2/16.
 */
public class DashboardFragment extends HomeFragment implements View.OnClickListener, RealmChangeListener {

    private EditText searchFly;
    private ImageView weatherIcon;
    private TextView weatherText;
    private Weather weather;
    private BannerView banner;
    private int bannerHeight = 0;

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

        weatherIcon = $(R.id.dashboard_weather_icon);
        weatherText = $(R.id.dashboard_weather_text);
        weatherText.setTextColor(Color.WHITE);

        TextView degrees = $(R.id.dashboard_weather_degrees);
        degrees.setTextColor(Color.WHITE);
        TextView myFlights = $(R.id.dashboard_myflights_text);
        myFlights.setTextColor(Color.WHITE);

        weather = WeatherController.getInstance().getWeather(getBaseActivity().getRealm());
        if (weather != null) {
            weather.addChangeListener(this);
            configWeather();
        }

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
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchFlights();
                    BZUtils.hideKeyboard(getBaseActivity());
                }
                return false;
            }
        });

        Rect rectangle= new Rect();
        Window window= getBaseActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight= rectangle.top;
        $(R.id.dashboard_container).getLayoutParams().height = (BZUtils.getScreenHeight(getBaseActivity()) - bannerHeight - statusBarHeight);

        banner = $(R.id.dashboard_banner);
        banner.setBannerInterface(this);
    }

    private void configWeather() {
        weatherText.setText(weather.getTemperature() + "");
        try {
            weatherIcon.setImageResource(getResources().getIdentifier("w" + weather.getIcon(), "drawable", getBaseActivity().getPackageName()));
            weatherIcon.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            weatherIcon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dash_button_myflights:
                getHomeActivity().changeFragment(FlightsFragment.newInstance(FlightsFragment.EXTRA_MY_FLIGHTS));
                break;
            case R.id.dash_icon_lupa:
                hideSoftKeyboard(searchFly);
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



    @Override
    public void onBannerSizeChanged(int height) {
        super.onBannerSizeChanged(height);
        if (getBaseActivity() != null) {
            LinearLayout weatherContainer = $(R.id.dashboard_container);
            Rect rectangle= new Rect();
            Window window= getBaseActivity().getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            int statusBarHeight= rectangle.top;

            ResizeAnimation a = new ResizeAnimation(weatherContainer);
            a.setDuration(500);
            // set the starting height (the current height) and the new height that the view should have after the animation
            int currentHeight = (BZUtils.getScreenHeight(getBaseActivity()) - bannerHeight - statusBarHeight);
            int newHeight = (BZUtils.getScreenHeight(getBaseActivity()) - height - statusBarHeight);

            LogBZ.d("onBannerSizeChanged: " + currentHeight + " - " + newHeight);

            a.setParams(currentHeight, newHeight);

            weatherContainer.startAnimation(a);
            bannerHeight = height;
        }
    }

    @Override
    public void onDestroy() {
        try {
            weather.removeChangeListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void hideSoftKeyboard(EditText input) {
        input.setInputType(0);
        InputMethodManager imm = (InputMethodManager) getBaseActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    @Override
    public void onChange(Object element) {
        configWeather();
    }

    public class ResizeAnimation extends Animation {

        private int startHeight;
        private int deltaHeight; // distance between start and end height
        private View view;

        /**
         * constructor, do not forget to use the setParams(int, int) method before
         * starting the animation
         * @param v
         */
        public ResizeAnimation (View v) {
            this.view = v;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            view.getLayoutParams().height = (int) (startHeight + deltaHeight * interpolatedTime);
            view.requestLayout();
        }

        /**
         * set the starting and ending height for the resize animation
         * starting height is usually the views current height, the end height is the height
         * we want to reach after the animation is completed
         * @param start height in pixels
         * @param end height in pixels
         */
        public void setParams(int start, int end) {

            this.startHeight = start;
            deltaHeight = end - startHeight;
        }

        /**
         * set the duration for the hideshowanimation
         */
        @Override
        public void setDuration(long durationMillis) {
            super.setDuration(durationMillis);
        }

        @Override
        public boolean willChangeBounds() {
            return false;
        }
    }

}
