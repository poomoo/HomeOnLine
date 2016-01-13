package com.poomoo.homeonline.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.utils.SPUtils;

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;
    private boolean isIndex = false;//是否需要引导


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isIndex = (boolean) SPUtils.get(getApplicationContext(), getString(R.string.sp_isIndex), true);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isIndex) {
                    SPUtils.put(getApplicationContext(), getString(R.string.sp_isIndex), false);
                    openActivity(IndexViewPagerActivity.class);
                    finish();
                } else {
                    if ((boolean) SPUtils.get(getApplicationContext(), getString(R.string.sp_isLogin), false))
                        openActivity(MainActivity.class);
                    else
                        openActivity(LogInActivity.class);

                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
