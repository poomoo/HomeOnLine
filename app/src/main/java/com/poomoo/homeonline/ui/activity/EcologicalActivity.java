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
 * Copyright (c) 2017. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.SpecialtyListCommodityAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.EcologicalPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.NoScrollRecyclerView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.CommodityType;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.RNewSpecialBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.poomoo.commlib.MyConfig.ECOLOGICAL;

/**
 * 类名 EcologicalActivity
 * 描述 生态有机
 * 作者 李苜菲
 * 日期 2017/1/13 15:17
 */
public class EcologicalActivity extends BaseDaggerActivity<EcologicalPresenter> implements BaseListAdapter.OnItemClickListener, ErrorLayout.OnActiveClickListener {
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.img_1)
    ImageView img1;
    @Bind(R.id.img_2)
    ImageView img2;
    @Bind(R.id.img_3)
    ImageView img3;
    @Bind(R.id.img_4)
    ImageView img4;
    @Bind(R.id.recycler_commodity)
    NoScrollRecyclerView recyclerView;
    @Bind(R.id.error_frame)
    ErrorLayout mErrorLayout;

    private SpecialtyListCommodityAdapter adapter;
    private LinearLayout.LayoutParams layoutParams;
    private ImageView[] hot = new ImageView[4];
    private int dp8;
    private int dp10;
    private int width;
    private String[] ad;
    private RAdBO rAdBO;
    private RListCommodityBO commodityBO;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_ecological;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_ecological;
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
        headerViewHolder.titleTxt.setTextColor(ContextCompat.getColor(this, R.color.ecological));
        headerViewHolder.titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        headerViewHolder.rightImg.setImageResource(R.drawable.ic_special_search);
        headerViewHolder.rightImg.setVisibility(View.VISIBLE);
        headerViewHolder.rightImg.setOnClickListener(v -> openActivity(SearchActivity.class));
        headerViewHolder.backImg.setOnClickListener(v -> {
            finish();
            getActivityOutToRight();
        });
        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为2:1

        dp8 = (int) getResources().getDimension(R.dimen.dp_8);
        dp10 = (int) getResources().getDimension(R.dimen.dp_10);
        width = MyUtils.getScreenWidth(this) - dp8 - dp10 * 2;
        layoutParams = new LinearLayout.LayoutParams(width / 2, width / 4);
        hot[0] = img1;
        hot[1] = img2;
        hot[2] = img3;
        hot[3] = img4;
        hot[0].setLayoutParams(layoutParams);
        hot[1].setLayoutParams(layoutParams);
        hot[2].setLayoutParams(layoutParams);
        hot[3].setLayoutParams(layoutParams);

        initRecycler();
        mErrorLayout.setOnActiveClickListener(this);

        mPresenter.getInfo();
        mErrorLayout.setState(ErrorLayout.LOADING, "");
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new ScrollLinearLayoutManager(this));

        adapter = new SpecialtyListCommodityAdapter(this, BaseListAdapter.NEITHER, ECOLOGICAL, position -> {//添加购物车
            commodityBO = adapter.getItem(position);
            addToCart(commodityBO);
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

//        adapter.setItems(getList());
    }

    public void getInfoSuccessful(RNewSpecialBO rNewSpecialBO) {
        super.getInfoSuccessful(rNewSpecialBO);
        LogUtils.d(TAG, "顶部广告");
        int len = rNewSpecialBO.topAdv.size();
        ad = new String[len];
        for (int i = 0; i < len; i++) {
            rAdBO = new RAdBO();
            rAdBO = rNewSpecialBO.topAdv.get(i);
            ad[i] = NetConfig.ImageUrl + rAdBO.advertisementPic;
        }
        slideShowView.setPics(ad, position -> {
            if (ad.length == 0)
                return;
            rAdBO = rNewSpecialBO.topAdv.get(position);
            if (rAdBO.isCommodity) {
                bundle = new Bundle();
                bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
                bundle.putInt(getString(R.string.intent_commodityDetailId), rAdBO.commodityDetailId);
                bundle.putInt(getString(R.string.intent_commodityType), CommodityType.COMMON);
                openActivity(CommodityInfoActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putString(getString(R.string.intent_value), rAdBO.connect);
                openActivity(WebViewActivity.class, bundle);
            }
        });
        LogUtils.d(TAG, "热门推荐");
        int hotSize = rNewSpecialBO.hotAdvs.size();
        for (int i = 0; i < 4; i++) {
            if (hotSize > i) {
                Glide.with(this).load(NetConfig.ImageUrl + rNewSpecialBO.hotAdvs.get(i).advertisementPic).placeholder(R.drawable.replace2).into(hot[i]);
                hot[i].setTag(R.id.tag_first, rNewSpecialBO.hotAdvs.get(i));
                hot[i].setVisibility(View.VISIBLE);
            } else
                hot[i].setVisibility(View.GONE);

        }
        LogUtils.d(TAG, "精选单品");
        adapter.setItems(rNewSpecialBO.selectSingles);

        scrollView.setVisibility(View.VISIBLE);
        mErrorLayout.setState(ErrorLayout.HIDE, "");
    }

    public void getInfoFailed() {
        super.getInfoFailed();
        mErrorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }


    private void addToCart(RListCommodityBO commodityBO) {
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.addToCart(application.getUserId(), -1, commodityBO.lowestPriceDetail.commodityId, commodityBO.commodityName, CommodityType.COMMON, 1, commodityBO.listPic, commodityBO.lowestPriceDetail.id, -1);
    }

    public void addToCartSucceed() {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        MyUtils.showToast(getApplicationContext(), "添加购物车成功");
    }

    public void addToCartFailed() {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        MyUtils.showToast(getApplicationContext(), "添加购物车失败");
    }

    @Override
    public void onLoadActiveClick() {
        mPresenter.getInfo();
        mErrorLayout.setState(ErrorLayout.LOADING, "");
    }

    public void hotClick(View view) {
        rAdBO = (RAdBO) view.getTag(R.id.tag_first);
        if (rAdBO == null)
            return;
        if (rAdBO.isCommodity) {//商品广告
            bundle = new Bundle();
            bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
            bundle.putInt(getString(R.string.intent_commodityType), CommodityType.COMMON);
            openActivity(CommodityInfoActivity.class, bundle);
        } else {//链接
            bundle = new Bundle();
            bundle.putString(getString(R.string.intent_value), rAdBO.connect);
            openActivity(WebViewActivity.class, bundle);
        }
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_commodityId), adapter.getItem(position).lowestPriceDetail.commodityId);
        bundle.putInt(getString(R.string.intent_commodityDetailId), adapter.getItem(position).lowestPriceDetail.id);
        bundle.putInt(getString(R.string.intent_commodityType), CommodityType.COMMON);
        openActivity(CommodityInfoActivity.class, bundle);
    }

    private List<RListCommodityBO> getList() {
        List<RListCommodityBO> rListCommodityBOs = new ArrayList<>();
        RListCommodityBO rListCommodityBO;
        for (int i = 0; i < 10; i++) {
            rListCommodityBO = new RListCommodityBO();
            rListCommodityBO.commonPrice = 99 + i;
            rListCommodityBO.platformPrice = 80 + i;
            rListCommodityBO.commodityName = "测试商品" + (i + 1);
            rListCommodityBO.listPic = "";
            rListCommodityBOs.add(rListCommodityBO);
        }
        return rListCommodityBOs;
    }
}
