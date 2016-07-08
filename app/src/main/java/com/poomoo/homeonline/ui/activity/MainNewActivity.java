/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.BottomBar;
import com.poomoo.homeonline.ui.fragment.CenterFragment;
import com.poomoo.homeonline.ui.fragment.ClassifyFragment;
import com.poomoo.homeonline.ui.fragment.MainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者: 李苜菲
 * 日期: 2016/6/22 15:57.
 */
public class MainNewActivity extends BaseActivity {
    @Bind(R.id.bottomBar)
    BottomBar bottomBar;

    private MainFragment mainFragment;
    private ClassifyFragment classifyFragment;
    private CenterFragment centerFragment;
    private Fragment curFragment;
    private FragmentTransaction fragmentTransaction;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setDefaultFragment();

        bottomBar.setOnItemChangedListener(new BottomBar.OnItemChangedListener() {
            @Override
            public void onItemChanged(final int index) {
                LogUtils.d(TAG, "onItemChanged:" + index);
                jump(index);
                if (index == 3)
                    bottomBar.setInfoNum(3, 5, false);
                else
                    bottomBar.setInfoNum(3, 5, true);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flag = getIntent().getIntExtra(getString(R.string.intent_value), 0);
        LogUtils.d(TAG, "onNewIntent:" + flag);
        jump(flag);
        bottomBar.cancelLinearBackground(flag);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_main_new;
    }

    private void setDefaultFragment() {
        // TODO 自动生成的方法存根
        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mainFragment = new MainFragment();
        curFragment = mainFragment;
        fragmentTransaction.add(R.id.frameLayout_main, curFragment);
        fragmentTransaction.commit();
    }

    private void jump(int position) {
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

                break;
            case 3:
                break;
            case 4:
                if (centerFragment == null)
                    centerFragment = new CenterFragment();
                switchFragment(centerFragment);
                curFragment = centerFragment;
                break;
        }
    }

    /**
     * 切换fragment
     *
     * @param to
     */
    public void switchFragment(Fragment to) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) { // 先判断是否被add过
            fragmentTransaction.hide(curFragment).add(R.id.frameLayout_main, to); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            fragmentTransaction.hide(curFragment).show(to); // 隐藏当前的fragment，显示下一个
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
