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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.poomoo.homeonline.recyclerLayoutManager.ScrollGridLayoutManager;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.PinchImageView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.homeonline.ui.custom.VerticalViewPager;
import com.poomoo.homeonline.ui.fragment.TabFragment;
import com.poomoo.model.response.RCommodityInfoBO;
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
 * 类名 TestActivity
 * 描述 ${TODO}
 * 作者 李苜菲
 * 日期 2016/8/8 14:58
 */
public class TestActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.rlayout_info_title)
    RelativeLayout titleBar;
    @Bind(R.id.img_info_back)
    ImageView backImg;
    @Bind(R.id.img_info_collect)
    ImageView collectImg;
    @Bind(R.id.vp_commodity_info)
    VerticalViewPager viewPager;
    @Bind(R.id.rlayout_progressBar)
    RelativeLayout progressBarRlayout;
    @Bind(R.id.txt_cart_num)
    TextView cartNumTxt;

    //view1
    private SlideShowView slideShowView;
    private TextView nameTxt;
    private TextView priceTxt;
    private TextView oldPriceTxt;
    private TextView inventoryTxt;
    private TextView selectedTxt;
    private EditText countEdt;
    private LinearLayout countLayout;
    private LinearLayout specificationLayout;

    //view2
    private TabLayout mTabLayout;
    private WebView infoWeb;
    private RecyclerView detailsRecycler;
    private RecyclerView recommendRecycler;
    private CommodityDetailsAdapter commodityDetailsAdapter;
    private CommodityRecommendAdapter commodityRecommendAdapter;

    private int screenWidth = 0;
    private int commodityId;//商品ID
    private int commodityDetailId;//商品规格ID
    private DialogPlus contentDialog = null;
    private ImageView dialogBigImg;
    private EditText dialog_product_sum;
    private TextView dialog_oldPriceTxt;
    private TextView confirmTxt;
    private LinearLayout dialog_specificationLayout;
    private LinearLayout bottomLayout;
    private BigPicPopUpWindow popUpWindow;
    private List<TagFlowLayout> tagFlowLayouts = new ArrayList<>();
    private String select = "";
    private boolean isAllSelected = false;//是否选择了所有的属性
    private View[] specialView;
    private RCommodityInfoBO rCommodityInfoBO;

    private ViewPagerAdapter viewPagerAdapter;
    private List<View> viewList = new ArrayList<>();
    private LayoutInflater mInflater;
    private String[] pics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        screenWidth = MyUtils.getScreenWidth(this);
        mInflater = LayoutInflater.from(this);
        commodityId = getIntent().getIntExtra(getString(R.string.intent_commodityId), -1);
        commodityDetailId = getIntent().getIntExtra(getString(R.string.intent_commodityDetailId), -1);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    private void init() {
        StatusBarUtil.setTransparent(this);
        titleBar.getBackground().mutate().setAlpha(0);

        if (application.getCartNum() == 0)
            cartNumTxt.setVisibility(View.INVISIBLE);
        else {
            cartNumTxt.setVisibility(View.VISIBLE);
            cartNumTxt.setText(application.getCartNum() + "");
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


        commodityId = 6048;
//        mPresenter.getCommodity(commodityId, commodityDetailId, 0);
    }

    private void addView() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_commodity_info1, null);
        slideShowView = (SlideShowView) view1.findViewById(R.id.slide_commodity_info);
        nameTxt = (TextView) view1.findViewById(R.id.txt_info_name);
        priceTxt = (TextView) view1.findViewById(R.id.txt_info_price);
        oldPriceTxt = (TextView) view1.findViewById(R.id.txt_info_old_price);
        inventoryTxt = (TextView) view1.findViewById(R.id.txt_inventory);
        selectedTxt = (TextView) view1.findViewById(R.id.txt_commodity_selected);
        countLayout = (LinearLayout) view1.findViewById(R.id.llayout_commodity_count);
        specificationLayout = (LinearLayout) view1.findViewById(R.id.layout_commodity_specification);
        specificationLayout.setOnClickListener(this);

        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenWidth));
        oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

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

    @OnClick({R.id.btn_info_addToCart, R.id.btn_info_buy})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn_info_addToCart:
                createDialog(false);
                break;
            case R.id.btn_info_buy:
                createDialog(false);
                break;
        }
    }

    public void getCommodityInfoSucceed(RCommodityInfoBO rCommodityInfoBO) {
        LogUtils.d(TAG, "getCommodityInfoSucceed:" + rCommodityInfoBO.commodityPictures + "\n" + rCommodityInfoBO.commodity + "\n" + rCommodityInfoBO.paramters + "\n" + rCommodityInfoBO.tjCommodity + "\n" + rCommodityInfoBO.specialParamters);

        int len = rCommodityInfoBO.commodityPictures.size();
        pics = new String[len];
        int i = 0;
        for (RCommodityInfoBO.Pic pic : rCommodityInfoBO.commodityPictures)
            pics[i++] = NetConfig.ImageUrl + pic.url;
        slideShowView.setPics(pics, position -> {

        });

        nameTxt.setText(rCommodityInfoBO.commodity.commodityName);
        priceTxt.setText("￥ " + rCommodityInfoBO.commodity.lowestPriceDetail.platformPrice);
        oldPriceTxt.setText("￥ " + rCommodityInfoBO.commodity.lowestPriceDetail.commonPrice);
        inventoryTxt.setText("库存" + rCommodityInfoBO.commodity.lowestPriceDetail.repertory + "件");

        len = rCommodityInfoBO.specialParamters.size();
        if (len > 0) {
            countLayout.setVisibility(View.GONE);
            specificationLayout.setVisibility(View.VISIBLE);
        } else {
            countLayout.setVisibility(View.VISIBLE);
            specificationLayout.setVisibility(View.GONE);
        }

        List<Integer> integers = new ArrayList<>();

        for (i = 0; i < len; i++) {
            if (rCommodityInfoBO.specialParamters.get(i).parametersValues.size() > 0)//该规格有多个属性
                integers.add(i);
        }
        this.rCommodityInfoBO = rCommodityInfoBO;
        setSelectedInfo(integers);
        addView(rCommodityInfoBO, len);

        initInfo();
        initDetails();
        initRecommend();
        viewPager.setVisibility(View.VISIBLE);
        progressBarRlayout.setVisibility(View.GONE);
    }

    public void getCommodityInfoFailed(String msg) {
        MyUtils.showToast(getApplicationContext(), msg);
        finish();
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
    private void createDialog(boolean showBottom) {
        if (contentDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.product_detail_dialog_content, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenHeight(this) * 3 / 4));
            dialog_oldPriceTxt = (TextView) view.findViewById(R.id.txt_dialog_oldPrice);
            dialog_specificationLayout = ((LinearLayout) view.findViewById(R.id.llayout_dialog_specification));
            dialog_product_sum = ((EditText) view.findViewById(R.id.edt_commodity_specification_count));
            confirmTxt = ((TextView) view.findViewById(R.id.txt_dialog_ok));
            bottomLayout = ((LinearLayout) view.findViewById(R.id.llayout_dialog_bottom));

            dialog_oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            for (View childView : specialView)
                dialog_specificationLayout.addView(childView);

            Holder holder = new ViewHolder(view);
            OnClickListener clickListener = (dialog, view1) -> {
                switch (view1.getId()) {
//                    case R.id.img_commodity_detail:
//                        zoomPic();
//                        break;
                    case R.id.dialog_close:
                        dialog.dismiss();
                        break;
                    case R.id.txt_dialog_ok:
                        dialog.dismiss();
                        break;
                    case R.id.txt_dialog_cart:
                        if (!isAllSelected) {
                            MyUtils.showToast(getApplicationContext(), "请选择商品属性");
                            return;
                        }
                        dialog.dismiss();
                        break;
                    case R.id.txt_dialog_buy:
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
        showSpecificationDialog(showBottom);
    }

    /**
     * 展示商品属性选择Dialog
     *
     * @param showBottom
     */
    private void showSpecificationDialog(boolean showBottom) {
        if (showBottom) {
            confirmTxt.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.VISIBLE);
        } else {
            confirmTxt.setVisibility(View.VISIBLE);
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
                    if (position != 0 && position % 3 == 0)
                        tv = (TextView) mInflater.inflate(R.layout.tv_enable, tagFlowLayout, false);
                    else
                        tv = (TextView) mInflater.inflate(R.layout.tv, tagFlowLayout, false);
                    tv.setText(s.parameterValue);
                    return tv;
                }

                @Override
                public boolean setEnabled(int position, RCommodityInfoBO.SpecialParamter.ParametersValues s) {
                    return false;
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
        for (int i = 0; i < len; i++) {
            RCommodityInfoBO.SpecialParamter.ParametersValues item = (RCommodityInfoBO.SpecialParamter.ParametersValues) tagFlowLayouts.get(i).getSelectedItem();
            if (item != null && !TextUtils.isEmpty(item.parameterValue))
                select += "\"" + item.parameterValue + "\"" + " ";
            else
                integers.add(i);
        }
        if (integers.size() > 0) {
            setSelectedInfo(integers);
            isAllSelected = false;
        } else {
            selectedTxt.setText(select);
            isAllSelected = true;
        }
    }

    /**
     * 显示没有选中的属性或者显示选中的全部属性
     *
     * @param integers
     */
    private void setSelectedInfo(List<Integer> integers) {
        select = "选择 ";
        if (integers == null || integers.size() == 0) {
//            int len = temp.length;
            int len = rCommodityInfoBO.specialParamters.size();

            for (int i = 0; i < len; i++)
                select += rCommodityInfoBO.specialParamters.get(i).parameterName + ",";

        } else {
            int len = integers.size();
            for (int i = 0; i < len; i++)
//                select += temp[integers.get(i)] + " ";
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
            case R.id.layout_commodity_specification:
                createDialog(true);
                break;
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
}
