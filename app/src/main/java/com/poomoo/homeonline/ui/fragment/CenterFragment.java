/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * 作者: 李苜菲
 * 日期: 2016/6/23 10:52.
 */
public class CenterFragment extends BaseFragment {
//    @Bind(R.id.scrollView_main)
//    MyScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
    }


}
