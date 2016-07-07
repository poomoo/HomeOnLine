package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;
    private boolean isIndex = false;//是否需要引导


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isIndex = (boolean) SPUtils.get(getApplicationContext(), getString(R.string.sp_isIndex), true);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isIndex) {
                    SPUtils.put(getApplicationContext(), getString(R.string.sp_isIndex), false);
                    openActivity(IndexViewPagerActivity.class);
                    finish();
                } else {
//                    if ((boolean) SPUtils.get(getApplicationContext(), getString(R.string.sp_isLogin), false))
                    openActivity(MainNewActivity.class);
//                    else
//                    openActivity(LogInActivity.class);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_splash;
    }
}
