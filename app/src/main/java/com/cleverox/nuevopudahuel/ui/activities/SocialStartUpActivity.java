package com.cleverox.nuevopudahuel.ui.activities;


import android.content.Intent;
import android.view.View;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.controllers.SyncController;


/**
 * Created by moddity on 24/2/16.
 */
public class SocialStartUpActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_social_startup;
    }

    @Override
    protected void configView() {
        $(R.id.social_facebook).setOnClickListener(this);
        $(R.id.social_twitter).setOnClickListener(this);
        $(R.id.social_instagram).setOnClickListener(this);
        $(R.id.social_remember).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.social_remember:
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
            case R.id.social_facebook:
                break;
            case R.id.social_twitter:
                break;
            case R.id.social_instagram:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        SyncController.getInstance().stopSync();
        super.onBackPressed();
    }
}
