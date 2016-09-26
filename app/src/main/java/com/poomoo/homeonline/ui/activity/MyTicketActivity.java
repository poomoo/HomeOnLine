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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.TicketAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.MyTicketPresenter;
import com.poomoo.homeonline.presenters.TicketPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RTicketBO;
import com.poomoo.model.response.TicketBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 TicketActivity
 * 描述 卡券
 * 作者 李苜菲
 * 日期 2016/9/5 15:40
 */
public class MyTicketActivity extends BaseDaggerActivity<MyTicketPresenter> implements ErrorLayout.OnActiveClickListener, BaseListAdapter.OnItemClickListener {
    private static final int TICKET = 6;
    @Bind(R.id.recycler_my_ticket)
    RecyclerView enableRecycler;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private TicketAdapter validAdapter;
    private String totalPrice;
    private int[] commodityIds;
    private int[] commodityDetailIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_myticket;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_my_ticket;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        totalPrice = getIntent().getStringExtra(getString(R.string.intent_totalPrice));
        commodityIds = getIntent().getIntArrayExtra(getString(R.string.intent_commodityIds));
        commodityDetailIds = getIntent().getIntArrayExtra(getString(R.string.intent_commodityDetailIds));

        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.rightTxt.setText("用券须知");
        headerViewHolder.rightTxt.setVisibility(View.VISIBLE);
        headerViewHolder.rightTxt.setOnClickListener(v ->
                openActivity(WebViewDataActivity.class)
        );

        enableRecycler.setLayoutManager(new LinearLayoutManager(this));
        enableRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        validAdapter = new TicketAdapter(this, BaseListAdapter.NEITHER);
        validAdapter.setOnItemClickListener(this);
        enableRecycler.setAdapter(validAdapter);

        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getTickets(application.getUserId(), totalPrice, commodityIds, commodityDetailIds);
    }

    public void successful(List<TicketBO> ticketBOList) {
        validAdapter.setItems(ticketBOList);
        errorLayout.setState(ErrorLayout.HIDE, "");
        if (ticketBOList.size() == 0)
            errorLayout.setState(ErrorLayout.EMPTY_DATA, "您还没有优惠券!");
    }

    public void failed(String msg) {
        errorLayout.setOnActiveClickListener(this);
        errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getTickets(application.getUserId(), totalPrice, commodityIds, commodityDetailIds);
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        getIntent().putExtra(getString(R.string.intent_vouchersId), validAdapter.getItem(position).id);
        getIntent().putExtra(getString(R.string.intent_vouchersMoney), validAdapter.getItem(position).money);
        setResult(TICKET, getIntent());
        finish();
    }
}
