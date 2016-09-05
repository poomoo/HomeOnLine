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
import android.view.View;

import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.HistoryAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.ScanHistoryPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseListDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RListCommodityBO;

import java.util.List;

/**
 * 类名 ScanHistoryActivity
 * 描述 浏览足迹
 * 作者 李苜菲
 * 日期 2016/8/12 10:08
 */
public class ScanHistoryActivity extends BaseListDaggerActivity<RListCommodityBO, ScanHistoryPresenter> implements BaseListAdapter.OnItemClickListener {
    private HistoryAdapter adapter;
    private List<RListCommodityBO> rListCommodityBOs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_history;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_history;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    @Override
    protected BaseListAdapter<RListCommodityBO> onSetupAdapter() {
        adapter = new HistoryAdapter(this, BaseListAdapter.ONLY_FOOTER);
        return adapter;
    }

    private void init() {
        mSwipeRefreshLayout.setEnabled(false);
        mAdapter.setOnItemClickListener(this);
        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.rightImg.setImageResource(R.drawable.ic_delete);
        headerViewHolder.rightImg.setVisibility(View.VISIBLE);
        mPresenter.getHistory(application.getUserId(), mCurrentPage);
    }

    public void toDo(View view) {
        createDialog("确定删除当前页面的记录?", (dialog, which) ->
                mPresenter.deleteHistory(getIndexes())
        ).show();
    }

    private int[] getIndexes() {
        int[] temp = new int[rListCommodityBOs.size()];
        int i = 0;
        for (RListCommodityBO rListCommodityBO : rListCommodityBOs)
            temp[i++] = rListCommodityBO.id;
        return temp;
    }

    @Override
    public void onLoadActiveClick() {
        super.onLoadActiveClick();
        mCurrentPage = 1;
        mPresenter.getHistory(application.getUserId(), mCurrentPage);
    }

    @Override
    public void onLoading() {
        super.onLoading();
        mPresenter.getHistory(application.getUserId(), mCurrentPage);
    }

    public void getListSucceed(List<RListCommodityBO> rListCommodityBOs) {
        this.rListCommodityBOs = rListCommodityBOs;
        setEmptyMsg("您还没有浏览任何商品");
        onLoadResultData(rListCommodityBOs);
    }

    public void getListFailed(String msg) {
        if (isNetWorkInvalid(msg))
            onNetworkInvalid(LOAD_MODE_DEFAULT);
        else
            onLoadErrorState(LOAD_MODE_DEFAULT);
    }

    public void deleteSucceed() {
        MyUtils.showToast(getApplicationContext(), "删除成功!");
        mCurrentPage = 1;
        adapter.clear();
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getHistory(application.getUserId(), mCurrentPage);
    }

    public void deleteFailed(String msg) {
        MyUtils.showToast(getApplicationContext(), msg);
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_commodityId), mAdapter.getItem(position).commodityId);
        bundle.putInt(getString(R.string.intent_commodityDetailId), mAdapter.getItem(position).commodityDetailId);
        bundle.putInt(getString(R.string.intent_commodityType), mAdapter.getItem(position).commodityType);
        bundle.putInt(getString(R.string.intent_matchId),  mAdapter.getItem(position).rushPurchaseId);
        openActivity(CommodityInfoActivity.class, bundle);
    }
}
