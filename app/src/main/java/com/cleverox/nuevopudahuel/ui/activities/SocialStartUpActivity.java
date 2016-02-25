package com.cleverox.nuevopudahuel.ui.activities;


import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;


/**
 * Created by moddity on 24/2/16.
 */
public class SocialStartUpActivity extends BaseActivity {

    private ImageButton facebook, twitter, instagram;
    private Button remember;

    @Override
    protected int getLayoutResource() {
        return R.layout.social_startup_activity;
    }

    @Override
    protected void configView() {
        facebook = $(R.id.social_facebook);
        twitter = $(R.id.social_twitter);
        instagram = $(R.id.social_instagram);

        remember = $(R.id.social_remember);

    }
}
