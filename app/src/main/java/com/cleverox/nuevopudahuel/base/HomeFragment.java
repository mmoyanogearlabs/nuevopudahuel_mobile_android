package com.cleverox.nuevopudahuel.base;

import android.os.Bundle;
import android.view.View;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.ui.activities.HomeActivity;

/**
 * Created by moddity on 29/2/16.
 */
public abstract class HomeFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        $(R.id.home_menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeActivity().openMenu();
            }
        });
    }

    protected HomeActivity getHomeActivity() {
        return (HomeActivity) getBaseActivity();
    }
}
