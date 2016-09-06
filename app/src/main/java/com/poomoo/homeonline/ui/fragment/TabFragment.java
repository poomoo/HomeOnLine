/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseTabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.http.POST;

/**
 * 类名 TabFragment
 * 描述 TabFragment
 * 作者 李苜菲
 * 日期 2016/7/19 11:39
 */
public abstract class TabFragment extends BaseTabFragment {
    @Bind(R.id.tab_nav)
    TabLayout mTabLayout;
    private int currItem;
    public int POSITION;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_universal_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ViewCompat.setElevation(mTabLayout, 7);
        if (mAdapter != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
        mViewPager.setCurrentItem(currItem);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fresh(POSITION);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void fresh(int POSITION) {
        setPOSITION(POSITION);
        mAdapter.notifyDataSetChanged();
    }

    public void setPage(int currItem) {
        if (currItem >= 4)
            currItem--;
        this.currItem = currItem;
    }

    @Override
    public TextView setupTabItemView(String tag) {
        RelativeLayout layout = (RelativeLayout) View.inflate(mContext, R.layout.view_tab_item, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        layout.setLayoutParams(params);
        TextView tabItemView = (TextView) layout.findViewById(R.id.pager_nav_item);
        tabItemView.setText(tag);
        return tabItemView;
    }

    /**
     * 设置Fragment
     */
    public abstract void onSetupTabs();
}
