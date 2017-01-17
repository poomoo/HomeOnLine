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
import android.support.v7.widget.RecyclerView;
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
import com.poomoo.homeonline.adapter.SpecialtyTitleAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.NewSpecialPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollGridLayoutManager;
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
import com.poomoo.model.response.RThirdClassifyBO;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.poomoo.commlib.MyConfig.TEA_WINE;

/**
 * 类名 TeaWineActivity
 * 描述 贵酒贵茶
 * 作者 李苜菲
 * 日期 2017/1/13 15:17
 */
public class TeaWineActivity extends BaseDaggerActivity<NewSpecialPresenter> implements BaseListAdapter.OnItemClickListener, ErrorLayout.OnActiveClickListener {
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.recycler_title)
    RecyclerView titleRecycler;
    @Bind(R.id.img_arrow)
    ImageView arrowImg;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.img_1)
    ImageView img1;
    @Bind(R.id.img_2)
    ImageView img2;
    @Bind(R.id.img_3)
    ImageView img3;
    @Bind(R.id.recycler_commodity)
    NoScrollRecyclerView listRecycler;
    @Bind(R.id.error_frame)
    ErrorLayout mErrorLayout;

    private SpecialtyListCommodityAdapter infoAdapter;
    private SpecialtyTitleAdapter titleAdapter;
    private LinearLayout.LayoutParams layoutParams;
    private ImageView[] hot = new ImageView[3];
    private int dp8;
    private int dp10;
    private int width;
    private String[] ad;
    private RAdBO rAdBO;
    private Bundle bundle;
    private int categoryId = 0;
    private RListCommodityBO commodityBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_tea_wine;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_tea_wine;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        BaseDaggerActivity.HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.titleTxt.setTextColor(ContextCompat.getColor(this, R.color.tea_wine));
        headerViewHolder.titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.sp_12));
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
        width = MyUtils.getScreenWidth(this) - dp8 * 2 - dp10 * 2;
        width /= 3;
        layoutParams = new LinearLayout.LayoutParams(width, width * 5 / 4);//宽高比4:5
        hot[0] = img1;
        hot[1] = img2;
        hot[2] = img3;
        hot[0].setLayoutParams(layoutParams);
        hot[1].setLayoutParams(layoutParams);
        hot[2].setLayoutParams(layoutParams);

        initRecycler();
        mErrorLayout.setOnActiveClickListener(this);

        mPresenter.getInfo(2, 0);
        mErrorLayout.setState(ErrorLayout.LOADING, "");
    }

    private void initRecycler() {
        titleRecycler.setLayoutManager(new ScrollGridLayoutManager(this, 4));
        titleRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.dp_10))
                .build());

        titleAdapter = new SpecialtyTitleAdapter(this, BaseListAdapter.NEITHER, TEA_WINE);
        titleRecycler.setAdapter(titleAdapter);
        titleAdapter.setOnItemClickListener((position, id, view) -> {
            SpecialtyTitleAdapter.SELECT_POSITION = position;
            titleAdapter.notifyDataSetChanged();
//            infoAdapter.clear();
            categoryId = titleAdapter.getItem(position).id;
            mPresenter.getInfo(2, categoryId);
            mErrorLayout.setState(ErrorLayout.LOADING, "");
        });
//        titleAdapter.setItems(getTitleList());

        listRecycler.setLayoutManager(new ScrollLinearLayoutManager(this));
        infoAdapter = new SpecialtyListCommodityAdapter(this, BaseListAdapter.NEITHER, TEA_WINE, position -> {//添加购物车
            commodityBO = infoAdapter.getItem(position);
            addToCart(commodityBO);
        });
        listRecycler.setAdapter(infoAdapter);
        infoAdapter.setOnItemClickListener(this);
//        infoAdapter.setItems(getList());
    }

    public void showHide(View view) {
        titleAdapter.isShowAll = !titleAdapter.isShowAll;
        arrowImg.setImageResource(titleAdapter.isShowAll ? R.drawable.ic_classify_arrow_up : R.drawable.ic_classify_arrow_down);
        titleAdapter.notifyDataSetChanged();
    }

    @Override
    public void getInfoSuccessful(RNewSpecialBO rNewSpecialBO) {
        LogUtils.d(TAG, "顶部分类");
        if (rNewSpecialBO.commodityCategorys != null) {
            titleAdapter.setItems(rNewSpecialBO.commodityCategorys);
            if (rNewSpecialBO.commodityCategorys.size() < 5)
                arrowImg.setVisibility(View.GONE);
            else
                arrowImg.setVisibility(View.VISIBLE);
        }
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
        for (int i = 0; i < 3; i++) {
            if (hotSize > i) {
                Glide.with(this).load(NetConfig.ImageUrl + rNewSpecialBO.hotAdvs.get(i).advertisementPic).placeholder(R.drawable.replace2b3).into(hot[i]);
                hot[i].setTag(R.id.tag_first, rNewSpecialBO.hotAdvs.get(i));
                hot[i].setVisibility(View.VISIBLE);
            } else
                hot[i].setVisibility(View.GONE);

        }
        LogUtils.d(TAG, "精选单品");
        infoAdapter.setItems(rNewSpecialBO.selectSingles);

        scrollView.setVisibility(View.VISIBLE);
        mErrorLayout.setState(ErrorLayout.HIDE, "");
    }

    @Override
    public void getInfoFailed() {
        mErrorLayout.setState(ErrorLayout.LOAD_FAILED, "");
        scrollView.setVisibility(View.GONE);
    }

    private void addToCart(RListCommodityBO commodityBO) {
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.addToCart(application.getUserId(), -1, commodityBO.lowestPriceDetail.commodityId, commodityBO.commodityName, CommodityType.COMMON, 1, commodityBO.listPic, commodityBO.lowestPriceDetail.id, -1);
    }

    @Override
    public void addToCartSucceed() {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        MyUtils.showToast(getApplicationContext(), "添加购物车成功");
    }

    @Override
    public void addToCartFailed() {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        MyUtils.showToast(getApplicationContext(), "添加购物车失败");
    }

    @Override
    public void onLoadActiveClick() {
        mPresenter.getInfo(2, categoryId);
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
        bundle.putInt(getString(R.string.intent_commodityId), infoAdapter.getItem(position).lowestPriceDetail.commodityId);
        bundle.putInt(getString(R.string.intent_commodityDetailId), infoAdapter.getItem(position).id);
        bundle.putInt(getString(R.string.intent_commodityType), CommodityType.COMMON);
        openActivity(CommodityInfoActivity.class, bundle);
    }

    public List<RThirdClassifyBO> getTitleList() {
        List<RThirdClassifyBO> threeCategoryLists = new ArrayList<>();
        RThirdClassifyBO rThirdClassifyBO;
        for (int i = 0; i < 3; i++) {
            rThirdClassifyBO = new RThirdClassifyBO();
            rThirdClassifyBO.categoryName = "测试标题" + (i + 1);
            threeCategoryLists.add(rThirdClassifyBO);
        }
        return threeCategoryLists;
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
