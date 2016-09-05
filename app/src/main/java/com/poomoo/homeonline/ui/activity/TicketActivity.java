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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.TicketPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;

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
        return R.string.empty;
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
        headerViewHolder.rightTxt.setOnClickListener(v -> {
            MyUtils.showToast(getApplicationContext(), "用券须知");
        });

        errorLayout.setOnActiveClickListener(this);
    }

    @Override
    public void onLoadActiveClick() {

    }
}
