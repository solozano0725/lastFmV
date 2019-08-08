package com.valid.lastfmclient.ui.main;

import android.content.Intent;
import android.view.WindowManager;

import com.valid.lastfmclient.R;
import com.valid.lastfmclient.ui.main.main.MainActivity;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {
    @Override
    public void initSplash(ConfigSplash configSplash) {
        getWindow().setFlags(WindowManager.LayoutParams.ROTATION_ANIMATION_CROSSFADE, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Background
        configSplash.setBackgroundColor(R.color.colorPrimary);
        configSplash.setAnimCircularRevealDuration(500);

        //Logo
        configSplash.setLogoSplash(R.drawable.ic_lastfm);
        configSplash.setAnimLogoSplashDuration(500);
        configSplash.setTitleSplash("");
    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();

    }
}
