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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.GridSpacingItemDecoration;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.CountryCommodityListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.CountryInfoPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RCountryInfoBO;
import com.poomoo.model.response.RListCommodityBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 CountryInfoActivity
 * 描述 国家馆
 * 作者 李苜菲
 * 日期 2016/11/24 15:50
 */
public class AbroadCountryInfoActivity extends BaseDaggerActivity<CountryInfoPresenter> implements SwipeRefreshLayout.OnRefreshListener, BaseListAdapter.OnItemClickListener, BaseListAdapter.OnLoadingListener, BaseListAdapter.OnLoadingHeaderCallBack, ErrorLayout.OnActiveClickListener {
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.recycler_country_info)
    RecyclerView recyclerView;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private CountryCommodityListAdapter adapter;
    private String[] urls;
    private HeaderViewHolder headerViewHolder;
    private int countryId = 0;
    private String countryName = "";
    private int len = 0;
    private RAdBO rAdBO;
    private List<RListCommodityBO> rListCommodityBOs;
    private Bundle bundle;
    private int FLAG = 0;//0-详情 1-商品列表
    private int page = 1;//当前页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_abroad_country_info;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_abroad;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        countryId = getIntent().getIntExtra(getString(R.string.intent_countryId), -1);
        countryName = getIntent().getStringExtra(getString(R.string.intent_countryName));

        BaseDaggerActivity.HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.titleTxt.setText(countryName + "馆");
        headerViewHolder.backImg.setOnClickListener(v -> {
            finish();
            getActivityOutToRight();
        });

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(
                R.color.swipe_refresh_first, R.color.swipe_refresh_second,
                R.color.swipe_refresh_third, R.color.swipe_refresh_four
        );
        refreshLayout.setEnabled(false);

        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize;
                switch (adapter.getItemViewType(position)) {
                    case -1:
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
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(new GridSpacingItemDecoration.Builder(this, 2)
                .hasHeader()
                .setH_spacing((int) getResources().getDimension(R.dimen.recycler_divider))
                .setV_spacing((int) getResources().getDimension(R.dimen.recycler_divider))
                .setDividerColor(ContextCompat.getColor(this, R.color.transParent))));

        adapter = new CountryCommodityListAdapter(this, BaseListAdapter.BOTH_HEADER_FOOTER);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnLoadingHeaderCallBack(this);

//        adapter.setItems(getList());
        errorLayout.setOnActiveClickListener(this);
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getCountryInfo(countryId);
    }

    private List<RListCommodityBO> getList() {
        List<RListCommodityBO> rListCommodityBOs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RListCommodityBO rListCommodityBO = new RListCommodityBO();
            rListCommodityBO.commonPrice = 123 + i;
            rListCommodityBO.commonPrice = 234 + i;
            rListCommodityBO.commodityName = "测试商品" + i;
            rListCommodityBOs.add(rListCommodityBO);
        }
        return rListCommodityBOs;
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        rAdBO = new RAdBO();
        rAdBO.commodityId = adapter.getItem(position).commodityId;
        rAdBO.commodityDetailId = adapter.getItem(position).commodityDetailId;
        rAdBO.commodityType = adapter.getItem(position).commodityType;
        jump();
    }

    public void failed() {
        errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void successful(RCountryInfoBO rCountryInfoBO) {
        rListCommodityBOs = rCountryInfoBO.commoditys;
        len = rCountryInfoBO.topAdv.size();
        urls = new String[len];
        for (int i = 0; i < len; i++)
            urls[i] = NetConfig.ImageUrl + rCountryInfoBO.topAdv.get(i).advertisementPic;
        headerViewHolder.slideShowView.setPics(urls, position -> {
            rAdBO = rCountryInfoBO.topAdv.get(position);
            jump();
        });

        refreshLayout.setEnabled(true);
        getCommoditySuccessful(rListCommodityBOs);

        errorLayout.setState(ErrorLayout.HIDE, "");
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void getCommodityFailed() {
        refreshLayout.post(() -> refreshLayout.setRefreshing(false));
        errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void getCommoditySuccessful(List<RListCommodityBO> result) {
        refreshLayout.post(() -> refreshLayout.setRefreshing(false));
        if (result == null) return;

        if (page == 1)
            adapter.clear();
        if (adapter.getDataSize() + result.size() == 0) {
            errorLayout.setState(ErrorLayout.EMPTY_DATA, "暂时没有商品");
            refreshLayout.setRefreshing(false);
            refreshLayout.setEnabled(false);
            adapter.setState(BaseListAdapter.STATE_HIDE);
            return;
        } else if (result.size() < MyConfig.PAGE_SIZE) {
            adapter.setState(BaseListAdapter.STATE_NO_MORE);
        } else {
            adapter.setState(BaseListAdapter.STATE_LOAD_MORE);
        }
        if (page == 1)
            adapter.addItems(0, result);
        else
            adapter.addItems(result);
        page++;
    }

    private void jump() {
        bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
        bundle.putInt(getString(R.string.intent_commodityDetailId), rAdBO.commodityDetailId);
        bundle.putInt(getString(R.string.intent_commodityType), rAdBO.commodityType);
        openActivity(CommodityInfoActivity.class, bundle);
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        if (FLAG == 0)
            mPresenter.getCountryInfo(countryId);
        else
            mPresenter.getCountryInfoCommoditys(countryId, page);
    }

    @Override
    public void onLoading() {
        mPresenter.getCountryInfoCommoditys(countryId, page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getCountryInfoCommoditys(countryId, page);
    }

    public final class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.flipper_ad)
        SlideShowView slideShowView;

        public HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new HeaderViewHolder(LayoutInflater.from(this).inflate(R.layout.title_country_info, parent, false));
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
        headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为2:1

    }
}
