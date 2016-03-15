package com.cleverox.nuevopudahuel.ui.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;

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

        VideoView videoView = $(R.id.video_splash);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.intro_aplicacion_np_android_324;
        videoView.setVideoURI(Uri.parse(path));
        videoView.setOnCompletionListener(this);
        videoView.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent intent = new Intent(this, SocialStartUpActivity.class);
        startActivity(intent);
        finish();
    }
}
