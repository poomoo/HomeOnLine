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
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.StatusBarUtil;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.CommodityDetailsAdapter;
import com.poomoo.homeonline.adapter.CommodityRecommendAdapter;
import com.poomoo.homeonline.adapter.ViewPagerAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.CommodityPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.PinchImageView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.homeonline.ui.custom.VerticalViewPager;
import com.poomoo.model.response.RCartCommodityBO;
import com.poomoo.model.response.RCartNumBO;
import com.poomoo.model.response.RCommodityInfoBO;
import com.poomoo.model.response.RIsCollect;
import com.poomoo.model.response.RSpecificationBO;
import com.poomoo.myflayout.FlowLayout;
import com.poomoo.myflayout.TagAdapter;
import com.poomoo.myflayout.TagFlowLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类名 CommodityInfoActivity
 * 描述 商品详情
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class CommodityInfoActivity extends BaseDaggerActivity<CommodityPresenter> implements View.OnClickListener, TextWatcher {
    @Bind(R.id.rlayout_info_title)
    RelativeLayout titleBar;
    @Bind(R.id.img_info_back)
    ImageView backImg;
    @Bind(R.id.img_info_collect)
    ImageView collectImg;
    @Bind(R.id.vp_commodity_info)
    VerticalViewPager viewPager;
    @Bind(R.id.txt_cart_num)
    TextView cartNumTxt;
    @Bind(R.id.btn_info_addToCart)
    Button cartBtn;
    @Bind(R.id.btn_info_buy)
    Button buyBtn;

    //view1
    private SlideShowView slideShowView;
    private TextView nameTxt;
    private TextView priceTxt;
    private TextView oldPriceTxt;
    private TextView purchaseTxt;
    private TextView inventoryTxt;
    private TextView selectedTxt;
    private TextView presentTxt;
    private EditText countEdt;
    private ImageView plusImg;
    private ImageView minusImg;
    private LinearLayout presentLayout;
    private LinearLayout countLayout;
    private LinearLayout specificationLayout;
    private ScrollView scrollView;

    //view2
    private TabLayout mTabLayout;
    private WebView infoWeb;
    private RecyclerView detailsRecycler;
    private RecyclerView recommendRecycler;
    private CommodityDetailsAdapter commodityDetailsAdapter;
    private CommodityRecommendAdapter commodityRecommendAdapter;

    //DialogPlus
    private DialogPlus contentDialog = null;
    private ImageView dialogBigImg;
    private ImageView dialogSmallImg;
    private TextView dialog_product_name;
    private EditText dialog_product_sum;
    private TextView dialog_newPriceTxt;
    private TextView dialog_oldPriceTxt;
    private TextView dialog_inventoryTxt;
    private EditText dialog_countEdt;
    private ImageView dialog_plusImg;
    private ImageView dialog_minusImg;
    private Button confirmBtn;
    private Button dialog_cartBtn;
    private Button dialog_buyBtn;
    private LinearLayout dialog_specificationLayout;
    private LinearLayout bottomLayout;
    private LinearLayout noPurchaseLayout;
    private LinearLayout purchaseLayout;
    private RelativeLayout dialog_progressBarRlayout;
    private BigPicPopUpWindow popUpWindow;
    private List<TagFlowLayout> tagFlowLayouts = new ArrayList<>();
    private String select = "";
    private boolean isAllSelected = false;//是否选择了所有的属性
    private boolean isBuy = true;//true-购买 false-添加购物车
    private View[] specialView;
    private LayoutInflater mInflater;

    private ViewPagerAdapter viewPagerAdapter;
    private List<View> viewList = new ArrayList<>();
    private String[] pics;
    private int screenWidth = 0;
    private int commodityId;//商品ID
    private int commodityDetailId;//商品规格ID
    private int commodityType;//商品类型
    private int activityRule;//买赠规则
    private String commodityName;//商品名称
    private int matchId;
    private RCommodityInfoBO rCommodityInfoBO;
    private List<Integer> specification = new ArrayList<>();
    private int count = 1;//购买的数量
    private int maxNum = 0;//购买的最大量
    private boolean hasSpecification = false;//是否有商品规格
    private ArrayList<RCartCommodityBO> rCartCommodityBOs = new ArrayList<>();
    private RCartCommodityBO cartCommodityBO = new RCartCommodityBO();
    private boolean isFreePostage = true;//是否包邮
    private double totalPrice = 0.00;
    private boolean isStar;//是否开抢（只有抢购类型的商品才会有该项）
    private boolean isCollect;//是否收藏
    private int[] ids = new int[1];//取消收藏
    public static CommodityInfoActivity inStance = null;
    private int repertory;//库存
    private int newActivityId = -1;//买赠商品需要传值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        inStance = this;

        screenWidth = MyUtils.getScreenWidth(this);
        mInflater = LayoutInflater.from(this);
        commodityId = getIntent().getIntExtra(getString(R.string.intent_commodityId), -1);
        commodityDetailId = getIntent().getIntExtra(getString(R.string.intent_commodityDetailId), -1);
        commodityType = getIntent().getIntExtra(getString(R.string.intent_commodityType), -1);
        matchId = getIntent().getIntExtra(getString(R.string.intent_matchId), -1);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_commodity_info;
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

    private void init() {
        StatusBarUtil.setTransparent(this);
        titleBar.getBackground().mutate().setAlpha(0);
        backImg.setOnClickListener(this);
        collectImg.setOnClickListener(this);
        if (application.getCartNum() == 0 && application.getUserId() != null) {
            cartNumTxt.setVisibility(View.INVISIBLE);
            mPresenter.getCartNum(application.getUserId());
        } else {
            cartNumTxt.setVisibility(View.VISIBLE);
            cartNumTxt.setText(application.getCartNum() > 99 ? "99+" : application.getCartNum() + "");
        }

        addView();
        viewPagerAdapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        titleBar.getBackground().mutate().setAlpha(0);
                        break;
                    case 1:
                        titleBar.getBackground().mutate().setAlpha(255);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 0:// 滑动结束，即切换完毕或者加载完毕
                        break;
                    case 1:// 手势滑动，空闲中
                        break;
                    case 2:// 界面切换中
                        break;

                }
            }
        });

        viewPager.setVisibility(View.GONE);
        getProgressBar();
        showProgressBar();
        mPresenter.getCommodity(commodityId, commodityDetailId, commodityType, matchId);
        if (application.getUserId() != null)
            mPresenter.addHistory(application.getUserId(), commodityId, commodityType);
    }

    private void addView() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_commodity_info1, null);
        slideShowView = (SlideShowView) view1.findViewById(R.id.slide_commodity_info);
        nameTxt = (TextView) view1.findViewById(R.id.txt_info_name);
        priceTxt = (TextView) view1.findViewById(R.id.txt_info_price);
        oldPriceTxt = (TextView) view1.findViewById(R.id.txt_info_old_price);
        purchaseTxt = (TextView) view1.findViewById(R.id.txt_purchase);
        inventoryTxt = (TextView) view1.findViewById(R.id.txt_inventory);
        selectedTxt = (TextView) view1.findViewById(R.id.txt_commodity_selected);
        presentTxt = (TextView) view1.findViewById(R.id.txt_present_info);
        presentLayout = (LinearLayout) view1.findViewById(R.id.llayout_present_info);
        countLayout = (LinearLayout) view1.findViewById(R.id.llayout_commodity_count);
        countEdt = (EditText) view1.findViewById(R.id.edt_info_count);
        plusImg = (ImageView) view1.findViewById(R.id.img_info_add);
        minusImg = (ImageView) view1.findViewById(R.id.img_info_minus);
        specificationLayout = (LinearLayout) view1.findViewById(R.id.layout_commodity_specification);
        scrollView = (ScrollView) view1.findViewById(R.id.scroll_commodity_info);

        purchaseTxt.setOnClickListener(this);
        specificationLayout.setOnClickListener(this);

        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenWidth));
        oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        countEdt.addTextChangedListener(this);
        viewList.add(view1);

        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_commodity_info2, null);
        mTabLayout = (TabLayout) view2.findViewById(R.id.tab_nav);
        infoWeb = (WebView) view2.findViewById(R.id.web_commodity_info);
        detailsRecycler = (RecyclerView) view2.findViewById(R.id.recycler_commodity_details);
        recommendRecycler = (RecyclerView) view2.findViewById(R.id.recycler_commodity_recommend);

        //添加tab
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_commodity_info)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_commodity_details)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_commodity_recommend)));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewList.add(view2);
    }

    public void nextPage(View view) {
        viewPager.setCurrentItem(1);
    }


    public void toMain(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_value), 0);
        openActivity(MainNewActivity.class, bundle);
        finish();
    }

    public void toCart(View view) {
        if (!MyUtils.isLogin(this)) {
            openActivity(LogInActivity.class);
            MyUtils.showToast(context, "请先登录");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_value), 3);
        openActivity(MainNewActivity.class, bundle);
        finish();
    }

    /**
     * tab变化
     *
     * @param position
     */
    private void changeTab(int position) {
        switch (position) {
            case 0:
                infoWeb.setVisibility(View.VISIBLE);
                detailsRecycler.setVisibility(View.GONE);
                recommendRecycler.setVisibility(View.GONE);
                break;
            case 1:
                infoWeb.setVisibility(View.GONE);
                detailsRecycler.setVisibility(View.VISIBLE);
                recommendRecycler.setVisibility(View.GONE);
                break;
            case 2:
                infoWeb.setVisibility(View.GONE);
                detailsRecycler.setVisibility(View.GONE);
                recommendRecycler.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void minus(View view) {
        countEdt.setText(--count + "");
        if (count == 1)
            MyUtils.showToast(getApplicationContext(), "不能低于1件");
    }

    public void add(View view) {
        countEdt.setText(++count + "");
        if (count == maxNum)
            MyUtils.showToast(getApplicationContext(), "库存有限,请谅解!");
    }

    @OnClick({R.id.btn_info_addToCart, R.id.btn_info_buy})
    public void btnClick(View v) {
        if (!MyUtils.isLogin(this)) {
            openActivity(LogInActivity.class);
            MyUtils.showToast(context, "请先登录");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_info_addToCart:
                isBuy = false;
                if (hasSpecification)
                    createDialog(false, false);
                else
                    addToCart();
                break;
            case R.id.btn_info_buy:
                isBuy = true;
                if (hasSpecification)
                    createDialog(false, false);
                else
                    createOrder();
                break;
        }
    }

    public void getCommodityInfoSucceed(RCommodityInfoBO rCommodityInfoBO) {
        LogUtils.d(TAG, rCommodityInfoBO.specialParamters + "\n" + rCommodityInfoBO.commodity.lowestPriceDetail);
        int len = rCommodityInfoBO.commodityPictures.size();
        pics = new String[len];
        int i = 0;
        for (RCommodityInfoBO.Pic pic : rCommodityInfoBO.commodityPictures)
            pics[i++] = NetConfig.ImageUrl + pic.url;
        slideShowView.setPics(pics, position -> {

        });
        commodityName = rCommodityInfoBO.commodity.commodityName;
        nameTxt.setText(commodityName);
        priceTxt.setText("￥ " + rCommodityInfoBO.commodity.lowestPriceDetail.platformPrice);
        oldPriceTxt.setText("￥ " + rCommodityInfoBO.commodity.lowestPriceDetail.commonPrice);
        inventoryTxt.setText("库存" + rCommodityInfoBO.commodity.lowestPriceDetail.repertory + "件");
        repertory = rCommodityInfoBO.commodity.lowestPriceDetail.repertory;
        maxNum = rCommodityInfoBO.commodity.lowestPriceDetail.repertory;
        commodityDetailId = rCommodityInfoBO.commodity.lowestPriceDetail.id;
        if (application.getUserId() != null)
            mPresenter.isCollect(application.getUserId(), commodityId, commodityDetailId);

        isFreePostage = rCommodityInfoBO.commodity.isFreePostage;
        isStar = rCommodityInfoBO.isStar;
        LogUtils.d(TAG, "isStar:" + rCommodityInfoBO.isStar);

        len = rCommodityInfoBO.specialParamters.size();
        if (len > 0) {
            hasSpecification = true;
            countLayout.setVisibility(View.GONE);
            specificationLayout.setVisibility(View.VISIBLE);
        } else {
            hasSpecification = false;
            countLayout.setVisibility(View.VISIBLE);
            specificationLayout.setVisibility(View.GONE);
        }

        if (commodityType == 2) {//抢购商品不能加入购物车 且每人只能购买一件
            cartBtn.setEnabled(false);
            countEdt.setEnabled(false);
            plusImg.setClickable(false);
            minusImg.setClickable(false);
            if (!isStar) buyBtn.setEnabled(false);
        }

        if (commodityType == 4) {//买赠
            activityRule = rCommodityInfoBO.activityRule;
            presentLayout.setVisibility(View.VISIBLE);
            presentTxt.setText(getResources().getStringArray(R.array.activityRule)[rCommodityInfoBO.activityRule]);
            collectImg.setVisibility(View.GONE);
        }

        if (repertory == 0 && !hasSpecification) {//库存为0且没有商品规格
            buyBtn.setEnabled(false);
            cartBtn.setEnabled(false);
            countEdt.setEnabled(false);
            plusImg.setClickable(false);
            minusImg.setClickable(false);
        }

        cartCommodityBO.commodityId = rCommodityInfoBO.commodity.id;
        cartCommodityBO.listPic = rCommodityInfoBO.commodity.listPic;
        cartCommodityBO.commodityName = rCommodityInfoBO.commodity.commodityName;
        cartCommodityBO.commodityPrice = rCommodityInfoBO.commodity.lowestPriceDetail.platformPrice;
        cartCommodityBO.orderType = rCommodityInfoBO.orderType;
        cartCommodityBO.commodityType = commodityType;

        List<Integer> integers = new ArrayList<>();
        for (i = 0; i < len; i++)
            if (rCommodityInfoBO.specialParamters.get(i).parametersValues.size() > 0)//该规格有多个属性
                integers.add(i);

        this.rCommodityInfoBO = rCommodityInfoBO;
        setSelectedInfo(integers);
        addView(rCommodityInfoBO, len);

        initInfo();
        initDetails();
        initRecommend();
        viewPager.setVisibility(View.VISIBLE);
        hideProgressBar();
    }

    public void getCommodityInfoFailed(String msg) {
        MyUtils.showToast(getApplicationContext(), msg);
        finish();
    }

    public void getNum(RCartNumBO rCartNumBO) {
        hideProgressBar();
        application.setCartNum(rCartNumBO.cartNum);
        if (application.getCartNum() == 0)
            cartNumTxt.setVisibility(View.INVISIBLE);
        else {
            cartNumTxt.setVisibility(View.VISIBLE);
            cartNumTxt.setText(application.getCartNum() > 99 ? "99+" : application.getCartNum() + "");
            MainNewActivity.INSTANCE.setInfoNum(3, application.getCartNum(), true);
        }
    }

    public void getSpecificationSucceed(RSpecificationBO rSpecificationBO) {
        LogUtils.d(TAG, "getSpecificationSucceed START");
        dialog_progressBarRlayout.setVisibility(View.GONE);
        dialog_newPriceTxt.setText("￥" + rSpecificationBO.commodityDetail.platformPrice);
        dialog_oldPriceTxt.setText("￥" + rSpecificationBO.commodityDetail.commonPrice);
        dialog_inventoryTxt.setText("库存" + rSpecificationBO.commodityDetail.repertory + "件");
        maxNum = rSpecificationBO.commodityDetail.repertory;
        commodityDetailId = rSpecificationBO.commodityDetail.id;
        repertory = rSpecificationBO.commodityDetail.repertory;

        if (isAllSelected && repertory > 0) {
            confirmBtn.setEnabled(true);
            dialog_cartBtn.setEnabled(true);
            dialog_buyBtn.setEnabled(true);
            confirmBtn.setText("确定");
            dialog_countEdt.setEnabled(true);
            dialog_plusImg.setClickable(true);
            dialog_minusImg.setClickable(true);
        } else {
            confirmBtn.setEnabled(false);
            dialog_cartBtn.setEnabled(false);
            dialog_buyBtn.setEnabled(false);
            confirmBtn.setText("库存不足");
            dialog_countEdt.setEnabled(false);
            dialog_countEdt.setText("1");
            dialog_plusImg.setClickable(false);
            dialog_minusImg.setClickable(false);
        }
        priceTxt.setText("￥ " + rSpecificationBO.commodityDetail.platformPrice);
        oldPriceTxt.setText("￥ " + rSpecificationBO.commodityDetail.commonPrice);
    }

    public void getSpecificationFailed(String msg) {
        dialog_progressBarRlayout.setVisibility(View.GONE);
        MyUtils.showToast(getApplicationContext(), msg);
    }

    private void addToCart() {
        showProgressBar();
        if (commodityType == 4) {
            newActivityId = matchId;//买赠的matchId即为newActivityId
            matchId = -1;
        }
        mPresenter.addToCart(application.getUserId(), newActivityId, commodityId, rCommodityInfoBO.commodity.commodityName, commodityType, count, rCommodityInfoBO.commodity.listPic, commodityDetailId, matchId);
    }

    public void addToCartSucceed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), "添加购物车成功");
        mPresenter.getCartNum(application.getUserId());
    }

    public void addToCartFailed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }

    /**
     * 到确认订单界面生成订单
     */
    public void createOrder() {
        cartCommodityBO.commodityNum = count;
        cartCommodityBO.commodityDetailsId = commodityDetailId;
        totalPrice = count * cartCommodityBO.commodityPrice;
        if (commodityType == 4) {//买赠
            cartCommodityBO.newActivityId = matchId;
            cartCommodityBO.activityRule = activityRule;
        } else
            cartCommodityBO.rushPurchaseId = matchId;
        rCartCommodityBOs = new ArrayList<>();
        rCartCommodityBOs.add(cartCommodityBO);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.intent_commodityList), rCartCommodityBOs);
        bundle.putDouble(getString(R.string.intent_totalPrice), totalPrice);
        bundle.putBoolean(getString(R.string.intent_isFreePostage), isFreePostage);
        bundle.putBoolean(getString(R.string.intent_value), false);
        openActivity(ConfirmOrderActivity.class, bundle);
    }

    public void getCollectSucceed(RIsCollect rIsCollect) {
        changeCollection(rIsCollect.isCollect);
    }

    public void getCollectFailed(String msg) {

    }

    public void collectSucceed() {
        hideProgressBar();
        changeCollection(true);
        MyUtils.showToast(getApplicationContext(), "收藏成功");

    }

    public void collectFailed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }

    public void cancelSucceed() {
        hideProgressBar();
        changeCollection(false);
        MyUtils.showToast(getApplicationContext(), "取消收藏成功");
    }

    public void cancelFailed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }

    private void changeCollection(boolean isCollect) {
        this.isCollect = isCollect;
        collectImg.setImageResource(isCollect ? R.drawable.ic_info_collect_checked : R.drawable.ic_info_collect_normal);
    }

    /**
     * 初始化商品详情
     */
    private void initInfo() {
        WebSettings webSettings = infoWeb.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        infoWeb.loadData(rCommodityInfoBO.commodity.remark, "text/html; charset=UTF-8", null);// 这种写法可以正确解码
    }

    /**
     * 初始化商品明细
     */
    private void initDetails() {
        detailsRecycler.setLayoutManager(new LinearLayoutManager(this));
        detailsRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.divide))
                .size((int) getResources().getDimension(R.dimen.divider_height2))
                .build());
        commodityDetailsAdapter = new CommodityDetailsAdapter(this, BaseListAdapter.NEITHER);
        commodityDetailsAdapter.setItems(rCommodityInfoBO.paramters);
        detailsRecycler.setAdapter(commodityDetailsAdapter);
    }

    /**
     * 初始化推荐商品
     */
    private void initRecommend() {
        recommendRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        recommendRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        recommendRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());

        commodityRecommendAdapter = new CommodityRecommendAdapter(this, BaseListAdapter.NEITHER);
        commodityRecommendAdapter.setItems(rCommodityInfoBO.tjCommodity);
        recommendRecycler.setAdapter(commodityRecommendAdapter);
        commodityRecommendAdapter.setOnItemClickListener((position, id, view) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.intent_commodityId), rCommodityInfoBO.tjCommodity.get(position).commodityId);
            bundle.putInt(getString(R.string.intent_commodityDetailId), rCommodityInfoBO.tjCommodity.get(position).commodityDetailId);
            openActivity(CommodityInfoActivity.class, bundle);
        });
    }

    /**
     * 创建商品属性选择Dialog
     *
     * @param showBottom
     */
    private void createDialog(boolean showBottom, boolean isPurchase) {
        if (contentDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.product_detail_dialog_content, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenHeight(this) * 3 / 4));
            dialog_product_name = (TextView) view.findViewById(R.id.dialog_product_name);
            dialog_newPriceTxt = (TextView) view.findViewById(R.id.txt_dialog_newPrice);
            dialog_oldPriceTxt = (TextView) view.findViewById(R.id.txt_dialog_oldPrice);
            dialog_specificationLayout = (LinearLayout) view.findViewById(R.id.llayout_dialog_specification);
            noPurchaseLayout = (LinearLayout) view.findViewById(R.id.llayout_no_purchase);
            purchaseLayout = (LinearLayout) view.findViewById(R.id.llayout_purchase);
            dialog_progressBarRlayout = (RelativeLayout) view.findViewById(R.id.rlayout_progressBar);
            dialog_product_sum = ((EditText) view.findViewById(R.id.edt_commodity_specification_count));
            dialog_inventoryTxt = (TextView) view.findViewById(R.id.txt_dialog_inventory);
            dialog_countEdt = (EditText) view.findViewById(R.id.edt_commodity_specification_count);
            dialog_minusImg = (ImageView) view.findViewById(R.id.dialog_product_sum_sub);
            dialog_plusImg = (ImageView) view.findViewById(R.id.dialog_product_sum_add);
            confirmBtn = ((Button) view.findViewById(R.id.btn_dialog_ok));
            dialog_cartBtn = ((Button) view.findViewById(R.id.btn_dialog_cart));
            dialog_buyBtn = ((Button) view.findViewById(R.id.btn_dialog_buy));
            bottomLayout = ((LinearLayout) view.findViewById(R.id.llayout_dialog_bottom));
            dialogSmallImg = (ImageView) view.findViewById(R.id.img_dialog_detail);

            if (repertory > 0) {//库存>0
                confirmBtn.setEnabled(true);
                dialog_cartBtn.setEnabled(true);
                dialog_buyBtn.setEnabled(true);
                dialog_inventoryTxt.setVisibility(View.VISIBLE);
            }

            dialog_countEdt.addTextChangedListener(this);
            dialog_product_name.setText(commodityName);
            dialog_inventoryTxt.setText("库存" + rCommodityInfoBO.commodity.lowestPriceDetail.repertory + "件");
            dialog_newPriceTxt.setText("￥" + rCommodityInfoBO.commodity.lowestPriceDetail.platformPrice);
            dialog_oldPriceTxt.setText("￥" + rCommodityInfoBO.commodity.lowestPriceDetail.commonPrice);
            dialog_oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            Glide.with(this).load(NetConfig.ImageUrl + rCommodityInfoBO.commodity.listPic).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(dialogSmallImg);

            for (View childView : specialView)
                dialog_specificationLayout.addView(childView);

            Holder holder = new ViewHolder(view);
            OnClickListener clickListener = (dialog, view1) -> {
                switch (view1.getId()) {
                    case R.id.img_dialog_detail:
                        zoomPic();
                        break;
                    case R.id.dialog_close:
                        dialog.dismiss();
                        break;
                    case R.id.btn_dialog_ok:
                        if (!MyUtils.isLogin(this)) {
                            openActivity(LogInActivity.class);
                            MyUtils.showToast(context, "请先登录");
                            return;
                        }
                        if (!isBuy)
                            addToCart();
                        else
                            createOrder();
                        dialog.dismiss();
                        break;
                    case R.id.btn_dialog_cart:
                        if (!MyUtils.isLogin(this)) {
                            openActivity(LogInActivity.class);
                            MyUtils.showToast(context, "请先登录");
                            return;
                        }
                        addToCart();
                        dialog.dismiss();
                        break;
                    case R.id.btn_dialog_buy:
                        if (!MyUtils.isLogin(this)) {
                            openActivity(LogInActivity.class);
                            MyUtils.showToast(context, "请先登录");
                            return;
                        }
                        createOrder();
                        dialog.dismiss();
                        break;
                    case R.id.dialog_product_sum_add:
                        dialog_product_sum.setText(String.valueOf(Integer.parseInt(dialog_product_sum.getText().toString()) + 1));
                        break;
                    case R.id.dialog_product_sum_sub:
                        if (Integer.parseInt(dialog_product_sum.getText().toString()) > 1) {
                            dialog_product_sum.setText(String.valueOf(Integer.parseInt(dialog_product_sum.getText().toString()) - 1));
                        }
                        break;
                }
            };
            contentDialog = DialogPlus.newDialog(this)
                    .setContentHolder(holder)
                    .setGravity(Gravity.BOTTOM)
                    .setCancelable(true)
                    .setOnClickListener(clickListener)
                    .setContentBackgroundResource(R.color.transParent)
                    .create();
        }
        showSpecificationDialog(showBottom, isPurchase);
    }

    /**
     * 展示商品属性选择Dialog
     *
     * @param showBottom
     */
    private void showSpecificationDialog(boolean showBottom, boolean isPurchase) {
        if (showBottom) {
            confirmBtn.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.VISIBLE);
            if (isPurchase) {//集采
                noPurchaseLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
            } else {
                noPurchaseLayout.setVisibility(View.VISIBLE);
                purchaseLayout.setVisibility(View.GONE);
            }
        } else {
            confirmBtn.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
        }
        contentDialog.show();
    }

    /**
     * 动态增加商品的属性
     */
    private void addView(RCommodityInfoBO rCommodityInfoBO, int len) {
        specialView = new View[len];
        for (int i = 0; i < len; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.product_detail_dialog_content_specification, null);
            TextView textView = (TextView) view.findViewById(R.id.txt_commodity_specification_name);
            final TagFlowLayout tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.flayout_commodity_specification_content);
            textView.setText(rCommodityInfoBO.specialParamters.get(i).parameterName);
            final List<RCommodityInfoBO.SpecialParamter.ParametersValues> typeInfos = new ArrayList<>();
            typeInfos.addAll(rCommodityInfoBO.specialParamters.get(i).parametersValues);
            final TagAdapter<RCommodityInfoBO.SpecialParamter.ParametersValues> adapterType = new TagAdapter<RCommodityInfoBO.SpecialParamter.ParametersValues>(typeInfos) {
                @Override
                public View getView(FlowLayout parent, int position, RCommodityInfoBO.SpecialParamter.ParametersValues s) {
                    TextView tv;
//                    if (position != 0 && position % 3 == 0)
//                        tv = (TextView) mInflater.inflate(R.layout.tv_enable, tagFlowLayout, false);
//                    else
                    tv = (TextView) mInflater.inflate(R.layout.tv, tagFlowLayout, false);
                    tv.setText(s.parameterValue);

                    return tv;
                }

                @Override
                public boolean setSelected(int position, RCommodityInfoBO.SpecialParamter.ParametersValues parametersValues) {
                    return super.setSelected(position, parametersValues);
                }

            };
            tagFlowLayouts.add(tagFlowLayout);
            tagFlowLayout.setAdapter(adapterType);
            tagFlowLayout.setTag(i);
            tagFlowLayout.setOnTagClickListener((view1, position, parent) -> {
                getSelectedItem();
                return false;
            });
            specialView[i] = view;
        }

    }

    /**
     * 获取选中的商品属性
     */
    private void getSelectedItem() {
        int len = tagFlowLayouts.size();
        select = "选择了 ";
        List<Integer> integers = new ArrayList<>();
        specification.clear();
        for (int i = 0; i < len; i++) {
            RCommodityInfoBO.SpecialParamter.ParametersValues item = (RCommodityInfoBO.SpecialParamter.ParametersValues) tagFlowLayouts.get(i).getSelectedItem();
            if (item != null && !TextUtils.isEmpty(item.parameterValue)) {
                select += "\"" + item.parameterValue + "\"" + " ";
                specification.add(item.id);
            } else
                integers.add(i);
        }
        if (integers.size() > 0) {
            setSelectedInfo(integers);
            isAllSelected = false;
            dialog_inventoryTxt.setVisibility(View.INVISIBLE);
        } else {
            selectedTxt.setText(select);
            isAllSelected = true;
            dialog_progressBarRlayout.setVisibility(View.VISIBLE);
            dialog_inventoryTxt.setVisibility(View.VISIBLE);
            mPresenter.getCommodityInfoBySpecification(commodityId, commodityType, specification.toArray(new Integer[specification.size()]));
        }
        confirmBtn.setEnabled(isAllSelected);
        dialog_cartBtn.setEnabled(isAllSelected);
        dialog_buyBtn.setEnabled(isAllSelected);
    }

    /**
     * 显示没有选中的属性或者显示选中的全部属性
     *
     * @param integers
     */
    private void setSelectedInfo(List<Integer> integers) {
        select = "选择 ";
        if (integers == null || integers.size() == 0) {
            int len = rCommodityInfoBO.specialParamters.size();
            for (int i = 0; i < len; i++)
                select += rCommodityInfoBO.specialParamters.get(i).parameterName + ",";
        } else {
            int len = integers.size();
            for (int i = 0; i < len; i++)
                select += rCommodityInfoBO.specialParamters.get(integers.get(i)).parameterName + ",";
        }
        select = select.substring(0, select.length() - 1);
        selectedTxt.setText(select);
    }

    /**
     * 放大图片
     */
    private void zoomPic() {
        if (popUpWindow == null)
            popUpWindow = new BigPicPopUpWindow(this);
        popUpWindow.setOnDismissListener(() -> {
            titleBar.setBackgroundColor(ContextCompat.getColor(this, R.color.ThemeBg));
        });
        Glide.with(this).load(R.drawable.test).into(dialogBigImg);
        popUpWindow.showAtLocation(viewList.get(0), Gravity.CENTER, 0, 0);
        titleBar.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_info_back:
                finish();
                getActivityOutToRight();
                break;
            case R.id.img_info_collect:
                if (!MyUtils.isLogin(this)) {
                    openActivity(LogInActivity.class);
                    MyUtils.showToast(context, "请先登录");
                    return;
                }
                if (!isCollect)
                    mPresenter.collect(application.getUserId(), commodityId, commodityDetailId, commodityType, matchId);
                else {
                    ids[0] = commodityId;
                    mPresenter.cancelCollection(ids);
                }
                showProgressBar();
                break;
            case R.id.txt_purchase:
                createDialog(true, true);
                break;
            case R.id.layout_commodity_specification:
                createDialog(true, false);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String temp = s.toString();
        if (temp.length() == 0)
            return;
        if (temp.length() == 1 && temp.equals("0")) {
            s.replace(0, 1, "1");
            count = 1;
        }
        count = Integer.parseInt(temp);
        if (count < 1) {
            count = 1;
            s.replace(0, s.length(), count + "");
        }

        if (count > maxNum && maxNum > 0) {
            count = maxNum;
            s.replace(0, s.length(), count + "");
        }
    }

    /**
     * 查看大图的PopupWindow
     */
    class BigPicPopUpWindow extends PopupWindow {
        private View mMenuView;

        public BigPicPopUpWindow(Context context) {
            super(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mMenuView = inflater.inflate(R.layout.dialog_pic_view, null);
            dialogBigImg = (PinchImageView) mMenuView.findViewById(R.id.img_big);

            this.setContentView(mMenuView);
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            this.setBackgroundDrawable(dw);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (contentDialog != null && contentDialog.isShowing()) {
                contentDialog.dismiss();
                return true;
            }
            if (popUpWindow != null && popUpWindow.isShowing()) {
                popUpWindow.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
