/**
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 * Copyright (c) 2016. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.HotAdapter;
import com.poomoo.homeonline.adapter.OnSaleGridAdapter;
import com.poomoo.homeonline.adapter.PresentCommodityAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.OnSalePresenter;
import com.poomoo.homeonline.presenters.PresentPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.CustomDialog;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.homeonline.ui.popup.RulePopupWindow;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.ROnSaleBO;
import com.poomoo.model.response.RPresentBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 PresentActivity
 * 描述 买赠专区
 * 作者 李苜菲
 * 日期 2016/10/31 15:26
 */
public class PresentActivity extends BaseDaggerActivity<PresentPresenter> implements ErrorLayout.OnActiveClickListener, BaseListAdapter.OnItemClickListener {
    @Bind(R.id.scroll_present)
    ScrollView scrollView;
    @Bind(R.id.llayout_present_content)
    LinearLayout contentLayout;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private PresentCommodityAdapter adapter;
    private Bundle bundle;
    private List<RPresentBO> rPresentBOs;
    private int POSITION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setBack();
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_present;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_present_zone;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getPresentInfo();
        errorLayout.setOnActiveClickListener(this);
    }

    public void successful(List<RPresentBO> rPresentBOs) {
        this.rPresentBOs = rPresentBOs;
        addView(rPresentBOs);
        errorLayout.setState(ErrorLayout.HIDE, "");
        scrollView.setVisibility(View.VISIBLE);
    }

    public void failed(String msg) {
        if (isNetWorkInvalid(msg))
            errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        else
            errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }


    private void addView(List<RPresentBO> rPresentBOs) {
        int len = rPresentBOs.size();
        for (int i = 0; i < len; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_present_content, null);
            TextView titleTxt = (TextView) view.findViewById(R.id.txt_present_name);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_present);

//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins((int) getResources().getDimension(R.dimen.dp_10), 0, 0, 0);
//            titleTxt.setLayoutParams(lp);
            titleTxt.setText(rPresentBOs.get(i).title);

            recyclerView.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .color(ContextCompat.getColor(context, R.color.transParent))
                    .size((int) getResources().getDimension(R.dimen.dp_10))
                    .build());

            adapter = new PresentCommodityAdapter(this, BaseListAdapter.NEITHER);
            recyclerView.setAdapter(adapter);
            recyclerView.setTag(i);
            adapter.setOnItemClickListener(this);
            adapter.setItems(rPresentBOs.get(i).activityCommodities);
            contentLayout.addView(view);
        }
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getPresentInfo();
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        bundle = new Bundle();
        POSITION = (int) ((View) view.getParent()).getTag();
        bundle.putInt(getString(R.string.intent_commodityId), rPresentBOs.get(POSITION).activityCommodities.get(position).commodityId);
        bundle.putInt(getString(R.string.intent_commodityDetailId), rPresentBOs.get(POSITION).activityCommodities.get(position).detailId);
        bundle.putInt(getString(R.string.intent_commodityType), 4);//买赠商品固定传4
        bundle.putInt(getString(R.string.intent_matchId), rPresentBOs.get(POSITION).activityCommodities.get(position).activityId);//match_id传activityId
        openActivity(CommodityInfoActivity.class, bundle);
    }
}
