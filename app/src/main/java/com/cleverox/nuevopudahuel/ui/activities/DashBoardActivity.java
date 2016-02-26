package com.cleverox.nuevopudahuel.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;

/**
 * Created by moddity on 25/2/16.
 */
public class DashBoardActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResource() {
        return R.layout.dash_board_activity;
    }

    @Override
    protected void configView() {
        $(R.id.dash_menu).setOnClickListener(this);

        LinearLayout searchContainer = $(R.id.dash_search_container);
        Bitmap roundedLeft = BitmapFactory.decodeResource(getResources(), R.drawable.btnsalidashome);
        Bitmap roundedRight = BitmapFactory.decodeResource(getResources(), R.drawable.btnllegadashome);
        LinearLayout.LayoutParams searchContainerParams = new LinearLayout.LayoutParams(roundedLeft.getWidth() + roundedRight.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        searchContainerParams.gravity = Gravity.CENTER_HORIZONTAL;
        searchContainerParams.setMargins(0, (int) getResources().getDimension(R.dimen.padding_10), 0, (int) getResources().getDimension(R.dimen.padding_15));
        searchContainer.setLayoutParams(searchContainerParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dash_menu:
                break;
        }
    }
}
