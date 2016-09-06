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

import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.TicketAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.TicketPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RTicketBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 TicketActivity
 * 描述 卡券
 * 作者 李苜菲
 * 日期 2016/9/5 15:40
 */
public class TicketActivity extends BaseDaggerActivity<TicketPresenter> implements ErrorLayout.OnActiveClickListener {
    @Bind(R.id.txt_ticket_count)
    TextView countTxt;
    @Bind(R.id.recycler_ticket_enable)
    RecyclerView enableRecycler;
    @Bind(R.id.recycler_ticket_disable)
    RecyclerView disableRecycler;
    @Bind(R.id.llayout_content)
    LinearLayout contentLayout;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private TicketAdapter validAdapter;
    private TicketAdapter expiredAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_ticket;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_ticket;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.rightTxt.setText("用券须知");
        headerViewHolder.rightTxt.setVisibility(View.VISIBLE);
        headerViewHolder.rightTxt.setOnClickListener(v ->
                openActivity(WebViewDataActivity.class)
        );

        enableRecycler.setLayoutManager(new ScrollLinearLayoutManager(this));
        enableRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        validAdapter = new TicketAdapter(this, BaseListAdapter.NEITHER);
        enableRecycler.setAdapter(validAdapter);

        disableRecycler.setLayoutManager(new ScrollLinearLayoutManager(this));
        disableRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        expiredAdapter = new TicketAdapter(this, BaseListAdapter.NEITHER);
        disableRecycler.setAdapter(expiredAdapter);

        errorLayout.setOnActiveClickListener(this);
        contentLayout.setVisibility(View.GONE);
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getTickets(application.getUserId());
    }

    public void successful(RTicketBO rTicketBO) {
        countTxt.setText(rTicketBO.voucherlist.size() + "");

        validAdapter.setItems(rTicketBO.voucherlist);
        validAdapter.addItems(rTicketBO.voucheredlist);
        expiredAdapter.setItems(rTicketBO.expiredCouponslist);

        errorLayout.setState(ErrorLayout.HIDE, "");
        contentLayout.setVisibility(View.VISIBLE);
    }

    public void failed(String msg) {
        errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getTickets(application.getUserId());
    }

}
