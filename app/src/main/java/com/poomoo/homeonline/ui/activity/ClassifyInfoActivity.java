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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ClassifyInfoAdapter;
import com.poomoo.homeonline.adapter.ListCommodityAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.ClassifyInfoPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollGridLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RClassifyInfoBO;
import com.poomoo.model.response.RListCommodityBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ClassifyInfoActivity
 * 描述 分类详情
 * 作者 李苜菲
 * 日期 2016/8/16 15:53
 */
public class ClassifyInfoActivity extends BaseDaggerActivity<ClassifyInfoPresenter> implements BaseListAdapter.OnItemClickListener, ErrorLayout.OnActiveClickListener {
    @Bind(R.id.scrollView_classify_info)
    ScrollView scrollView;
    @Bind(R.id.img_classify_info)
    ImageView titleImg;
    @Bind(R.id.recycler_classify_info)
    RecyclerView classifyRecycler;
    @Bind(R.id.recycler_commodity)
    RecyclerView commodityRecycler;
    @Bind(R.id.txt_classify_info)
    TextView classifyTxt;
    @Bind(R.id.error_frame)
    ErrorLayout mErrorLayout;

    private String title;
    private ListCommodityAdapter listCommodityAdapter;
    private ClassifyInfoAdapter classifyInfoAdapter;
    private List<RListCommodityBO> rListCommodityBOs;
    public static int SELECTPOSITION = 0;
    private String categoryId;
    public final String mEmptyMsg = "暂无商品";
    private RListCommodityBO rListCommodityBO;
    private boolean isList = false;//true-加载list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra(getString(R.string.intent_value));
        categoryId = getIntent().getStringExtra(getString(R.string.intent_categoryId));
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
        scrollView.setVisibility(View.GONE);

        titleImg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为2:1

        classifyRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        classifyRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.dp_10))
                .build());
        classifyRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        classifyInfoAdapter = new ClassifyInfoAdapter(this, BaseListAdapter.NEITHER);
        classifyRecycler.setAdapter(classifyInfoAdapter);
        classifyInfoAdapter.setOnItemClickListener((position, id, view) -> {
            classifyTxt.setText(classifyInfoAdapter.getItem(position).categoryName);
            SELECTPOSITION = position;
            classifyInfoAdapter.notifyDataSetChanged();
            listCommodityAdapter.clear();
            categoryId = classifyInfoAdapter.getItem(position).id + "";
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.loadClassifyList(categoryId);
        });

        commodityRecycler.setLayoutManager(new ScrollGridLayoutManager(this, 2));
        commodityRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        commodityRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        commodityRecycler.setPadding((int) getResources().getDimension(R.dimen.dp_20), (int) getResources().getDimension(R.dimen.dp_10), (int) getResources().getDimension(R.dimen.dp_20), 0);
        listCommodityAdapter = new ListCommodityAdapter(this, BaseListAdapter.NEITHER, false);
        commodityRecycler.setAdapter(listCommodityAdapter);
        listCommodityAdapter.setOnItemClickListener(this);

        mErrorLayout.setOnActiveClickListener(this);

        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.loadClassify(categoryId);
        SELECTPOSITION = 0;
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
        scrollView.setVisibility(View.VISIBLE);
        Glide.with(this).load(NetConfig.ImageUrl + rClassifyInfoBO.advertisementList.get(0).advertisementPic).placeholder(R.drawable.replace2).priority(Priority.IMMEDIATE).into(titleImg);
        this.rListCommodityBOs = rClassifyInfoBO.commoditys;
        LogUtils.d(TAG, "rListCommodityBOs" + rListCommodityBOs);
        classifyTxt.setText(rClassifyInfoBO.threeCategoryList.get(0).categoryName);
        onLoadResultData(rListCommodityBOs);
        classifyInfoAdapter.setItems(rClassifyInfoBO.threeCategoryList);
    }

    public void loadClassifyFailed(String msg) {
        isList = false;
        scrollView.setVisibility(View.GONE);
        if (isNetWorkInvalid(msg))
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        else
            mErrorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void loadInfoSucceed(List<RListCommodityBO> rListCommodityBOs) {
        scrollView.setVisibility(View.VISIBLE);
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        this.rListCommodityBOs = rListCommodityBOs;
        listCommodityAdapter.setItems(rListCommodityBOs);
    }

    public void loadInfoFailed(String msg) {
        isList = true;
        scrollView.setVisibility(View.GONE);
        if (isNetWorkInvalid(msg))
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        else
            mErrorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        rListCommodityBO = listCommodityAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_commodityId), rListCommodityBO.commodityId);
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
