/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.fragment.MyOrdersFragment;
import com.poomoo.homeonline.ui.fragment.TabFragment;
import com.poomoo.model.response.ROrderBO;

/**
 * 类名 MyOrdersActivity
 * 描述 我的订单
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class MyOrdersActivity extends BaseActivity {
    int type;
    private TabFragment mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBack();
        type = getIntent().getIntExtra(getString(R.string.intent_value), 0);
        setDefaultMenuItem();
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_myOrders;
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_my_orders;
    }

    /**
     * 设置默认的页面
     */
    @SuppressLint("ValidFragment")
    private void setDefaultMenuItem() {
        mTab = new TabFragment() {
            @Override
            public void onSetupTabs() {
                addTab(getResources().getString(R.string.tab_order_all), MyOrdersFragment.class, ROrderBO.ORDER_ALL);
                addTab(getResources().getString(R.string.tab_order_pay), MyOrdersFragment.class, ROrderBO.ORDER_PAY);
                addTab(getResources().getString(R.string.tab_order_deliver), MyOrdersFragment.class, ROrderBO.ORDER_DELIVER);
                addTab(getResources().getString(R.string.tab_order_receipt), MyOrdersFragment.class, ROrderBO.ORDER_RECEIPT);
                addTab(getResources().getString(R.string.tab_order_evaluate), MyOrdersFragment.class, ROrderBO.ORDER_EVALUATE);
                addTab(getResources().getString(R.string.tab_order_after_sale), MyOrdersFragment.class, ROrderBO.ORDER_AFTER_SALE);
            }
        };
        mTab.setPage(type);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, mTab)
                .commit();
    }
}
