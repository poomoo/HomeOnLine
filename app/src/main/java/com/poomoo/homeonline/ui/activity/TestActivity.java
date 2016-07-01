/**
 * Copyright (c) 2015. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.homeonline.ui.listener.AdvertisementListener;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 作者: 李苜菲
 * 日期: 2016/6/30 14:29.
 */
public class TestActivity extends AppCompatActivity {
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;

    private String[] urls = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.app_bar_main);
        ButterKnife.bind(this);

        slideShowView.setLayoutParams(new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为3:1
        urls[0] = "http://hunchaowang.com/hckj/images/slide1.jpg";
        urls[1] = "http://img5.imgtn.bdimg.com/it/u=1831523257,4273085642&fm=21&gp=0.jpg";
        urls[2] = "http://img0.imgtn.bdimg.com/it/u=2724261082,1059352100&fm=21&gp=0.jpg";
        slideShowView.setPics(urls, new AdvertisementListener() {
            @Override
            public void onAdvClick(int position) {

            }
        });
    }

}
