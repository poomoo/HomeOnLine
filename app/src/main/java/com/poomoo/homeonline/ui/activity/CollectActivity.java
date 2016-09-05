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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.CollectionListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.CollectPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseListDaggerActivity;
import com.poomoo.model.response.RCollectBO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 CollectActivity
 * 描述 收藏
 * 作者 李苜菲
 * 日期 2016/8/11 15:28
 */
public class CollectActivity extends BaseListDaggerActivity<RCollectBO, CollectPresenter> implements BaseListAdapter.OnItemClickListener, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.chk_all_commodity)
    public CheckBox checkBox;

    private int count;//选中的数量
    private List<RCollectBO> rCollectBOs;
    public boolean isClick = true;//是否是点击全选框 true-点击 false-适配器变化
    public static CollectActivity inStance = null;
    private CollectionListAdapter adapter;
    private int[] indexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        inStance = this;
        setBack();

        mSwipeRefreshLayout.setEnabled(false);
        checkBox.setOnCheckedChangeListener(this);
        mAdapter.setOnItemClickListener(this);

        adapter = (CollectionListAdapter) mAdapter;
        mPresenter.getCollectionList(application.getUserId(), mCurrentPage);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_collect;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_collection;
    }

    @Override
    protected BaseListAdapter onSetupAdapter() {
        return new CollectionListAdapter(this, BaseListAdapter.ONLY_FOOTER);
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mCurrentPage = 1;
        mPresenter.getCollectionList(application.getUserId(), mCurrentPage);
    }

    @Override
    public void onLoadActiveClick() {
        super.onLoadActiveClick();
        mCurrentPage = 1;
        mPresenter.getCollectionList(application.getUserId(), mCurrentPage);
    }

    @Override
    public void onLoading() {
        super.onLoading();
        mPresenter.getCollectionList(application.getUserId(), mCurrentPage);
    }

    /**
     * 取消收藏
     *
     * @param view
     */
    public void cancelCollect(View view) {
        indexes = adapter.getIndexes();
        count = indexes.length;
        if (count == 0) {
            MyUtils.showToast(getApplicationContext(), "没有选中收藏的商品");
            return;
        }
        showDialog("确认删除这" + count + "个收藏的商品?");
    }

    private void showDialog(String msg) {
        createDialog(msg, (dialog, which) -> {
            mPresenter.cancelCollections(((CollectionListAdapter) mAdapter).getIndexes());
        }).show();
    }

    public void getListSucceed(List<RCollectBO> rCollectBOs) {
        this.rCollectBOs = rCollectBOs;
        LogUtils.d(TAG, "getListSucceed:" + rCollectBOs.size());
        setEmptyMsg("您还没有收藏任何商品");
        onLoadResultData(rCollectBOs);
    }

    public void getListFailed(String msg) {
        if (isNetWorkInvalid(msg))
            onNetworkInvalid(LOAD_MODE_DEFAULT);
        else
            onLoadErrorState(LOAD_MODE_DEFAULT);
    }

    public void cancelSucceed() {
        MyUtils.showToast(getApplicationContext(), "删除成功!");
        isClick = false;
        checkBox.setChecked(false);
        adapter.clearIndexes();
        mCurrentPage = 1;
        mPresenter.getCollectionList(application.getUserId(), mCurrentPage);
    }

    public void cancelFailed(String msg) {
        MyUtils.showToast(getApplicationContext(), msg);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isClick)
            if (isChecked)
                adapter.selectAll();
            else
                adapter.cancelAll();
        isClick = true;
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_commodityId), mAdapter.getItem(position).commodityId);
        bundle.putInt(getString(R.string.intent_commodityDetailId), mAdapter.getItem(position).commodityDetailId);
        bundle.putInt(getString(R.string.intent_commodityType), mAdapter.getItem(position).commodityType);
        bundle.putInt(getString(R.string.intent_matchId), mAdapter.getItem(position).rushPurchaseId);
        openActivity(CommodityInfoActivity.class, bundle);
    }
}
