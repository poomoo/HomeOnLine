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
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.EditText;

import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ListCommodityAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.SearchPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseListDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RListCommodityBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 SearchActivity
 * 描述 商品搜索
 * 作者 李苜菲
 * 日期 2016/8/16 14:42
 */
public class SearchActivity extends BaseListDaggerActivity<RListCommodityBO, SearchPresenter> implements BaseListAdapter.OnItemClickListener {
    @Bind(R.id.edt_search)
    EditText searchEdt;

    private ListCommodityAdapter listCommodityAdapter;
    private String content;
    private RListCommodityBO rListCommodityBO;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected int onSetTitle() {
        return 0;
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
        listCommodityAdapter = new ListCommodityAdapter(this, BaseListAdapter.ONLY_FOOTER,true);
        return listCommodityAdapter;
    }

    private void init() {
        mSwipeRefreshLayout.setEnabled(false);
        mListView.setLayoutManager(new GridLayoutManager(this, 2));
        mListView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        mListView.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        mListView.setPadding((int) getResources().getDimension(R.dimen.recycler_divider), 0, 0, 0);
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        listCommodityAdapter.setOnItemClickListener(this);
    }

    public void search(View view) {
        mCurrentPage = 1;
        content = searchEdt.getText().toString().trim();
        MyUtils.hiddenKeyBoard(this, searchEdt);
        listCommodityAdapter.clear();
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.search(content, mCurrentPage);
    }

    @Override
    public void onLoadActiveClick() {
        super.onLoadActiveClick();
        mCurrentPage = 1;
        mPresenter.search(content, mCurrentPage);
    }

    @Override
    public void onLoading() {
        super.onLoading();
        mPresenter.search(content, mCurrentPage);
    }

    public void succeed(List<RListCommodityBO> rListCommodityBOs) {
        rListCommodityBOs.addAll(rListCommodityBOs);
        setEmptyMsg("没有找到该类商品");
        onLoadResultData(rListCommodityBOs);
    }

    public void failed(String msg) {
        if (isNetWorkInvalid(msg))
            onNetworkInvalid(LOAD_MODE_DEFAULT);
        else
            onLoadErrorState(LOAD_MODE_DEFAULT);
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        rListCommodityBO = listCommodityAdapter.getItem(position);
        bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_commodityId), rListCommodityBO.commodityId);
        bundle.putInt(getString(R.string.intent_commodityType), rListCommodityBO.commodityType);
        bundle.putInt(getString(R.string.intent_commodityDetailId), rListCommodityBO.commodityDetailId);
        openActivity(CommodityInfoActivity.class, bundle);
    }
}
