package com.massiva.nuevopudahuel.ui.activities;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.controllers.SyncController;


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
                return;
            case R.id.social_facebook:
                startActivity(getOpenFacebookIntent());
                break;
            case R.id.social_twitter:
                openTwitterIntent();
                break;
            case R.id.social_instagram:
                openInstagramTwitter();
                break;
        }
    }

    private Intent getOpenFacebookIntent() {
        try {
            getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/1199933116700243")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/Aeropuerto-Santiago-AMB-Nuevo-Pudahuel-1199933116700243")); //catches and opens a url to the desired page
        }
    }

    private void openTwitterIntent() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=NuevoPudahuel")));
        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/NuevoPudahuel")));
        }
    }

    private void openInstagramTwitter() {
        Uri uri = Uri.parse("http://instagram.com/_u/aeropuertoamb");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/aeropuertoamb")));
        }
    }

    @Override
    public void onBackPressed() {
        SyncController.getInstance().stopSync();
        super.onBackPressed();
    }
}
