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
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ListCommodityAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.AbroadClassifyListPresenter;
import com.poomoo.homeonline.presenters.ClassifyListPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseListDaggerActivity;
import com.poomoo.homeonline.ui.custom.AddAndMinusView;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.CommodityType;
import com.poomoo.model.response.RListCommodityBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类名 AbroadClassifyListActivity
 * 描述 跨境分类下的商品
 * 作者 李苜菲
 * 日期 2016/8/17 11:40
 */
public class AbroadClassifyListActivity extends BaseListDaggerActivity<RListCommodityBO, AbroadClassifyListPresenter> implements BaseListAdapter.OnItemClickListener {
    private ListCommodityAdapter listCommodityAdapter;
    private String categoryId;
    private String title;
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
        return R.layout.activity_abroad_classify_list;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_empty;
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
        listCommodityAdapter = new ListCommodityAdapter(this, BaseListAdapter.ONLY_FOOTER, true);
        return listCommodityAdapter;
    }

    private void init() {
        title = getIntent().getStringExtra(getString(R.string.intent_title));
        categoryId = getIntent().getStringExtra(getString(R.string.intent_categoryId));

        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.titleTxt.setText(title);

        mSwipeRefreshLayout.setEnabled(false);
        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize;
                switch (mAdapter.getItemViewType(position)) {
                    case -2:
                        spanSize = 2;
                        break;
                    default:
                        spanSize = 1;
                        break;
                }
                return spanSize;
            }
        });
        mListView.setLayoutManager(gridLayoutManager);
        mListView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        mListView.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        mListView.setPadding((int) getResources().getDimension(R.dimen.recycler_divider), 0, 0, 0);
        listCommodityAdapter.setOnItemClickListener(this);

        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getCommodity(categoryId, mCurrentPage);
    }

    @Override
    public void onLoadActiveClick() {
        super.onLoadActiveClick();
        mCurrentPage = 1;
        mPresenter.getCommodity(categoryId, mCurrentPage);
    }

    @Override
    public void onLoading() {
        super.onLoading();
        mPresenter.getCommodity(categoryId, mCurrentPage);
    }

    public void succeed(List<RListCommodityBO> rListCommodityBOs) {
        rListCommodityBOs.addAll(rListCommodityBOs);
        setEmptyMsg("暂时没有商品");
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
        bundle.putInt(getString(R.string.intent_commodityType), CommodityType.ABROAD);//跨境商品
        bundle.putInt(getString(R.string.intent_commodityDetailId), rListCommodityBO.commodityDetailId);
        openActivity(CommodityInfoActivity.class, bundle);
    }
}
