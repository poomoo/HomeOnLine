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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ClassifyInfoAdapter;
import com.poomoo.homeonline.adapter.ListCommodityAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.ClassifyInfoPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RClassifyInfoBO;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.RThirdClassifyBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 WholeSaleClassifyInfoActivity
 * 描述 集采分类详情
 * 作者 李苜菲
 * 日期 2016/8/16 15:53
 */
public class WholeSaleClassifyInfoActivity extends BaseDaggerActivity<ClassifyInfoPresenter> implements BaseListAdapter.OnItemClickListener, ErrorLayout.OnActiveClickListener {
    @Bind(R.id.recycler_classify_title)
    RecyclerView titleRecycler;
    @Bind(R.id.recycler_classify_info)
    RecyclerView infoRecycler;
    @Bind(R.id.error_frame)
    ErrorLayout mErrorLayout;

    private String title;
    private ListCommodityAdapter listCommodityAdapter;
    private ClassifyInfoAdapter classifyInfoAdapter;
    private List<RListCommodityBO> rListCommodityBOs;
    //    public static int SELECT_POSITION = 0;
    private int categoryId;
    public final String mEmptyMsg = "暂无商品";
    private RListCommodityBO rListCommodityBO;
    private boolean isList = false;//true-加载list
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra(getString(R.string.intent_title));
        categoryId = getIntent().getIntExtra(getString(R.string.intent_categoryId), 0);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_classify_info;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_classify_info;
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
        headerViewHolder.titleTxt.setText(title);

        titleRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        titleRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.dp_10))
                .build());
        classifyInfoAdapter = new ClassifyInfoAdapter(this, BaseListAdapter.NEITHER);
        titleRecycler.setAdapter(classifyInfoAdapter);
        classifyInfoAdapter.setOnItemClickListener((position, id, view) -> {
            ClassifyInfoAdapter.SELECT_POSITION = position;
            classifyInfoAdapter.notifyDataSetChanged();
            listCommodityAdapter.clear();
            categoryId = classifyInfoAdapter.getItem(position).id;
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.loadClassifyList(categoryId);
        });

        infoRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        infoRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        infoRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
//        infoRecycler.setPadding((int) getResources().getDimension(R.dimen.dp_20), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_20), 0);
        listCommodityAdapter = new ListCommodityAdapter(this, BaseListAdapter.NEITHER, ListCommodityAdapter.COMMON);
        infoRecycler.setAdapter(listCommodityAdapter);
        listCommodityAdapter.setOnItemClickListener(this);

        mErrorLayout.setOnActiveClickListener(this);

        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.loadClassify(categoryId);
        ClassifyInfoAdapter.SELECT_POSITION = 0;

    }

    @Override
    public void onLoadActiveClick() {
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        if (!isList)
            mPresenter.loadClassify(categoryId);
        else
            mPresenter.loadClassifyList(categoryId);
    }

    public void loadClassifySucceed(RClassifyInfoBO rClassifyInfoBO) {
        LogUtils.d(TAG, "advertisementList" + rClassifyInfoBO.advertisementList);
        LogUtils.d(TAG, "threeCategoryList" + rClassifyInfoBO.threeCategoryList);
        LogUtils.d(TAG, "commoditys" + rClassifyInfoBO.commoditys);

        this.rListCommodityBOs = rClassifyInfoBO.commoditys;
        onLoadResultData(rListCommodityBOs);
        RThirdClassifyBO rThirdClassifyBO = new RThirdClassifyBO();
        rThirdClassifyBO.categoryName = "全部";
        rThirdClassifyBO.id = categoryId;
        rClassifyInfoBO.threeCategoryList.add(0, rThirdClassifyBO);
        classifyInfoAdapter.setItems(rClassifyInfoBO.threeCategoryList);
    }

    public void loadClassifyFailed(String msg) {
        isList = false;
        mErrorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void loadInfoSucceed(List<RListCommodityBO> rListCommodityBOs) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        this.rListCommodityBOs = rListCommodityBOs;
        listCommodityAdapter.setItems(rListCommodityBOs);
    }

    public void loadInfoFailed(String msg) {
        isList = true;
        mErrorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        rListCommodityBO = listCommodityAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_commodityId), rListCommodityBO.commodityId);
        bundle.putInt(getString(R.string.intent_commodityDetailId), rListCommodityBO.commodityDetailId);
        bundle.putInt(getString(R.string.intent_commodityType), rListCommodityBO.commodityType);
        openActivity(CommodityInfoActivity.class, bundle);
    }

    /**
     * 请求成功，读取缓存成功，加载数据
     *
     * @param result
     */
    public void onLoadResultData(List<RListCommodityBO> result) {
        if (result == null) return;

        listCommodityAdapter.clear();

        if (listCommodityAdapter.getDataSize() + result.size() == 0) {
            mErrorLayout.setState(ErrorLayout.EMPTY_DATA, mEmptyMsg);
            return;
        }
        listCommodityAdapter.addItems(0, result);
        mErrorLayout.setState(ErrorLayout.HIDE, "");
    }
}
