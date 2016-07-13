/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.TimeCountDownUtilBy3View;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.MainGridAdapter;
import com.poomoo.homeonline.adapter.MainListAdapter;
import com.poomoo.homeonline.adapter.PicturesGridAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ScrollViewListener;
import com.poomoo.homeonline.ui.base.BaseFragment;
import com.poomoo.homeonline.ui.custom.MyScrollView;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.homeonline.ui.listener.AdvertisementListener;
import com.poomoo.model.response.RRecommendBO;
import com.poomoo.model.response.RTypeBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者: 李苜菲
 * 日期: 2016/6/23 10:52.
 */
public class MainFragment extends BaseFragment implements AdapterView.OnItemClickListener, ScrollViewListener {
    @Bind(R.id.scrollView_main)
    MyScrollView scrollView;
    @Bind(R.id.grid_menu)
    NoScrollGridView menuGrid;
    @Bind(R.id.txt_hour)
    TextView hourTxt;
    @Bind(R.id.txt_minute)
    TextView minuteTxt;
    @Bind(R.id.txt_second)
    TextView secondTxt;
    @Bind(R.id.recycler_main)
    RecyclerView recyclerView;
    @Bind(R.id.img_qhcs_title)
    ImageView qhcsTitleImg;
    @Bind(R.id.img_qhcs_content)
    ImageView qhcsContentImg;
    @Bind(R.id.grid_qhcs)
    NoScrollGridView qhcsGrid;
    @Bind(R.id.img_lsyz_title)
    ImageView lsyzTitleImg;
    @Bind(R.id.img_lsyz_content)
    ImageView lsyzContentImg;
    @Bind(R.id.grid_lsyz)
    NoScrollGridView lsyzGrid;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.img_top)
    ImageView topImg;

    private MainGridAdapter gridAdapter;
    private MainListAdapter adapter;
    private PicturesGridAdapter qhcsGridAdapter;
    private PicturesGridAdapter lsyzGridAdapter;
    private TimeCountDownUtilBy3View timeCountDownUtilBy3View;

    private List<RRecommendBO> rRecommendBOs = new ArrayList<>();
    private String[] ad = {"http://img.jiayou9.com/jyzx/upload/company/20160621/20160621235614_798.jpg", "http://img.jiayou9.com/jyzx//upload/company/20160617/20160617152510_21.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160617/20160617153952_548.jpg"};
    private String[] qhcs = {"http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133639_805.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133706_479.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133619_901.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133559_450.jpg"};
    private String[] lsyz = {"http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133432_159.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160622/20160622135718_235.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133453_903.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133539_91.jpg"};
    private List<RTypeBO> rTypeBOs = new ArrayList<>();
    private List<String> qhcsList = new ArrayList<>();
    private List<String> lsyzList = new ArrayList<>();
    private RTypeBO rTypeBO;
    private int len = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        slideShowView.setLayoutParams(new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(getActivity()) / 2));//设置广告栏的宽高比为3:1
        gridAdapter = new MainGridAdapter(getActivity());
        menuGrid.setAdapter(gridAdapter);
        menuGrid.setOnItemClickListener(this);

        slideShowView.setPics(ad, new AdvertisementListener() {
            @Override
            public void onAdvClick(int position) {

            }
        });

        initCountDownTime();

        initRecyclerView();

        initType();

        initQhcs();

        initLsyz();

        scrollView.setScrollViewListener(this);

        topImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scrollView.fullScroll(ScrollView.FOCUS_UP);
                scrollView.smoothScrollTo(0, 0);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
//                .color(getResources().getColor(R.color.transparent))
//                .size((int) getResources().getDimension(R.dimen.divider_height2))
//                .build());

        adapter = new MainListAdapter(getActivity(), BaseListAdapter.NEITHER);
        recyclerView.setAdapter(adapter);
        initCommodities();
    }

    private void initCommodities() {
        RRecommendBO rRecommendBO = new RRecommendBO();
        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628100836_78.jpg";
        rRecommendBO.newPrice = "10.80";
        rRecommendBO.oldPrice = "10.80";
        rRecommendBOs.add(rRecommendBO);

        rRecommendBO = new RRecommendBO();
        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628101204_324.jpg";
        rRecommendBO.newPrice = "68";
        rRecommendBO.oldPrice = "68";
        rRecommendBOs.add(rRecommendBO);

        rRecommendBO = new RRecommendBO();
        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628101635_324.jpg";
        rRecommendBO.newPrice = "8.18";
        rRecommendBO.oldPrice = "8.18";
        rRecommendBOs.add(rRecommendBO);

        rRecommendBO = new RRecommendBO();
        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628100543_124.jpg";
        rRecommendBO.newPrice = "15.20";
        rRecommendBO.oldPrice = "15.20";
        rRecommendBOs.add(rRecommendBO);

        rRecommendBO = new RRecommendBO();
        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628101204_324.jpg";
        rRecommendBO.newPrice = "68";
        rRecommendBO.oldPrice = "68";
        rRecommendBOs.add(rRecommendBO);

        rRecommendBO = new RRecommendBO();
        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628101635_324.jpg";
        rRecommendBO.newPrice = "8.18";
        rRecommendBO.oldPrice = "8.18";
        rRecommendBOs.add(rRecommendBO);

        rRecommendBO = new RRecommendBO();
        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628100543_124.jpg";
        rRecommendBO.newPrice = "15.20";
        rRecommendBO.oldPrice = "15.20";
        rRecommendBOs.add(rRecommendBO);

        adapter.addItems(rRecommendBOs);
    }

    private void initCountDownTime() {
//        timeCountDownUtilBy3View = new TimeCountDownUtilBy3View(5 * 60 * 60 * 1000, 1000, hourTxt, minuteTxt, secondTxt);
        timeCountDownUtilBy3View = new TimeCountDownUtilBy3View(MyUtils.DateToTime("2016-06-28 18:00:00"), 1000, hourTxt, minuteTxt, secondTxt);
        timeCountDownUtilBy3View.start();
    }

    private void initType() {
        for (int i = 0; i < 8; i++) {
            rTypeBO = new RTypeBO();
            rTypeBO.icon = getString(R.string.base_url) + "/weixin/images/icon" + (i + 1) + ".png";
            rTypeBO.name = MyConfig.classify[i];
            rTypeBOs.add(rTypeBO);
        }
        gridAdapter.setItems(rTypeBOs);
    }

    private void initQhcs() {
        Glide.with(getActivity()).load(getString(R.string.base_url) + "/weixin/images/index-market-1.png").into(qhcsTitleImg);
        Glide.with(getActivity()).load("http://img.jiayou9.com/jyzx/upload/company/20160622/20160622173551_317.jpg").into(qhcsContentImg);

        len = qhcs.length;
        LogUtils.d(TAG, "initQhcs" + len);
        for (int i = 0; i < len; i++)
            qhcsList.add(qhcs[i]);
        qhcsGridAdapter = new PicturesGridAdapter(getActivity());
        qhcsGrid.setAdapter(qhcsGridAdapter);
        qhcsGridAdapter.setItems(qhcsList);
    }

    private void initLsyz() {
        Glide.with(getActivity()).load(getString(R.string.base_url) + "/weixin/images/index-market-2.png").into(lsyzTitleImg);
        Glide.with(getActivity()).load("http://img.jiayou9.com/jyzx/upload/company/20160622/20160622172550_936.jpg").into(lsyzContentImg);

        len = lsyz.length;
        LogUtils.d(TAG, "initLsyz" + len);
        for (int i = 0; i < len; i++)
            lsyzList.add(lsyz[i]);
        lsyzGridAdapter = new PicturesGridAdapter(getActivity());
        lsyzGrid.setAdapter(lsyzGridAdapter);
        lsyzGridAdapter.setItems(lsyzList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
//        LogUtils.d(TAG, "onScrollChanged:" + scrollView.getScrollY() + ":" + scrollView.getHeight());
        if (scrollView.getScrollY() > scrollView.getHeight())
            topImg.setVisibility(View.VISIBLE);
        else
            topImg.setVisibility(View.INVISIBLE);
    }
}
