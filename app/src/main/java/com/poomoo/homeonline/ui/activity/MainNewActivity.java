/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.BottomBar;
import com.poomoo.homeonline.ui.fragment.CartFragment;
import com.poomoo.homeonline.ui.fragment.CenterFragment;
import com.poomoo.homeonline.ui.fragment.ClassifyFragment;
import com.poomoo.homeonline.ui.fragment.GrabFragment;
import com.poomoo.homeonline.ui.fragment.MainFragment;

import java.util.zip.Deflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 MainNewActivity
 * 描述 主Activity
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class MainNewActivity extends BaseActivity {
    @Bind(R.id.bottomBar)
    BottomBar bottomBar;

    private MainFragment mainFragment;
    private ClassifyFragment classifyFragment;
    private GrabFragment grabFragment;
    private CartFragment cartCartFragment;
    private CenterFragment centerFragment;
    private Fragment curFragment;

    private long exitTime = 0;
    public static MainNewActivity INSTANCE = null;
    private int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        ButterKnife.bind(this);
        setDefaultFragment();

        bottomBar.setOnItemChangedListener(index -> {
            LogUtils.d(TAG, "onItemChanged:" + index);
            jump(index);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flag = intent.getIntExtra(getString(R.string.intent_value), -1);
        LogUtils.d(TAG, "onNewIntent:" + flag);
        jump(flag);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_main_new;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    private void setDefaultFragment() {
        mainFragment = new MainFragment();
        curFragment = mainFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout_main, curFragment)
                .commit();
    }

    public void jump(int position) {
        bottomBar.cancelLinearBackground(position);
        switch (position) {
            case 0:
                switchFragment(mainFragment);
                curFragment = mainFragment;
                break;
            case 1:
                if (classifyFragment == null)
                    classifyFragment = new ClassifyFragment();
                switchFragment(classifyFragment);
                curFragment = classifyFragment;
                break;
            case 2:
                if (grabFragment == null)
                    grabFragment = new GrabFragment();
                switchFragment(grabFragment);
                curFragment = grabFragment;
                break;
            case 3:
                if (cartCartFragment == null)
                    cartCartFragment = new CartFragment();
                switchFragment(cartCartFragment);
                curFragment = cartCartFragment;
                break;
            case 4:
                if (centerFragment == null)
                    centerFragment = new CenterFragment();
                switchFragment(centerFragment);
                curFragment = centerFragment;
                break;
        }
    }

    public void setInfoNum(int type, int number, boolean isShow) {
        bottomBar.setInfoNum(type, number, isShow);
    }

    /**
     * 切换fragment
     *
     * @param to
     */
    public void switchFragment(Fragment to) {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        if (!to.isAdded()) { // 先判断是否被add过
//            fragmentTransaction.hide(curFragment).add(R.id.frameLayout_main, to); // 隐藏当前的fragment，add下一个到Activity中
//        } else {
//            fragmentTransaction.hide(curFragment).show(to); // 隐藏当前的fragment，显示下一个
//        }
//        fragmentTransaction.commitAllowingStateLoss();

        if (!to.isAdded()) { // 先判断是否被add过
            getSupportFragmentManager().beginTransaction()
                    .hide(curFragment)
                    .add(R.id.frameLayout_main, to)
                    .commitAllowingStateLoss();
            // 隐藏当前的fragment，add下一个到Activity中
        } else {
            getSupportFragmentManager().beginTransaction()
                    .hide(curFragment)
                    .show(to)
                    .commitAllowingStateLoss();
            // 隐藏当前的fragment，显示下一个
        }
    }


    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            MyUtils.showToast(getApplicationContext(), "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (curFragment != mainFragment) {
                jump(0);
                bottomBar.cancelLinearBackground(0);
            } else
                exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
