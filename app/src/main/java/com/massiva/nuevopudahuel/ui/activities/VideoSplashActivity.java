package com.massiva.nuevopudahuel.ui.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.constants.PudahuelPrefs;

/**
 * Created by iaguila on 15/3/16.
 */
public class VideoSplashActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_video_splash;
    }

    @Override
    protected void configView() {

        final VideoView videoView = $(R.id.video_splash);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.intro_aplicacion_np_android_324;
        videoView.setVideoURI(Uri.parse(path));
        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        String dontShowSocial = ((PudahuelApplication) this.getApplication()).getStoredString(PudahuelPrefs.DONT_SHOW_SOCIAL, "false");

        if (dontShowSocial.equals("true")) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            Intent intent = new Intent(this, SocialStartUpActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
