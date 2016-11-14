/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.fragment.InviteListFragment;
import com.poomoo.homeonline.ui.fragment.MyOrdersFragment;
import com.poomoo.homeonline.ui.fragment.RepayListFragment;
import com.poomoo.homeonline.ui.fragment.TabFragment;
import com.poomoo.model.response.ROrderBO;

/**
 * 类名 RewardInfoActivity
 * 描述 奖励详情
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class RewardInfoActivity extends BaseActivity {
    private TabFragment mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBack();
        setDefaultMenuItem();
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_rewardInfo;
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_pub_tab;
    }

    /**
     * 设置默认的页面
     */
    @SuppressLint("ValidFragment")
    private void setDefaultMenuItem() {
        mTab = new TabFragment(ContextCompat.getColor(this, R.color.invite_bg)) {
            @Override
            public void onSetupTabs() {
                addTab(getResources().getString(R.string.tab_invite), InviteListFragment.class);
                addTab(getResources().getString(R.string.tab_repay), RepayListFragment.class);
            }
        };
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, mTab)
                .commit();
    }
}
