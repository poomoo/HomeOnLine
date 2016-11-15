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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.AbroadClassifyListAdapter;
import com.poomoo.homeonline.adapter.AbroadSubClassifyListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.AbroadPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RAbroadBO;
import com.poomoo.model.response.RAbroadClassifyBO;
import com.poomoo.model.response.RAbroadCommodityBO;
import com.poomoo.model.response.RAdBO;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 AbroadActivity
 * 描述 跨境专区
 * 作者 李苜菲
 * 日期 2016/10/31 15:26
 */
public class AbroadActivity extends BaseDaggerActivity<AbroadPresenter> implements ErrorLayout.OnActiveClickListener, BaseListAdapter.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.scroll_abroad)
    ScrollView scrollView;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.llayout_abroad_commodity)
    LinearLayout commodityLayout;
    @Bind(R.id.llayout_abroad_advertisement)
    LinearLayout advertisementLayout;
    @Bind(R.id.recycler_classify)
    RecyclerView classifyRecycler;
    @Bind(R.id.recycler_commodity)
    RecyclerView commodityRecycler;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private String[] menu;
    private String[] content;

    private Bundle bundle;
    private AbroadClassifyListAdapter classifyListAdapter;
    private AbroadSubClassifyListAdapter subClassifyListAdapter;
    private List<RAbroadClassifyBO> rAbroadClassifyBOs;
    private List<List<RAbroadCommodityBO>> rListCommodityBOs;
    private List<List<RAdBO>> rAdBOs;
    private List<RAbroadCommodityBO> commodityListBOs;//分类商品
    public static int SELECT_POSITION = 0;
    private int POSITION = 0;
    private String[] ad;
    private RAdBO rAdBO;
    private int flag = 0;//0-加载全部 1-加载分类
    private int index1 = 0;
    private int index2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setBack();
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_abroad;
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
        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为2:1
        initClassify();
        initSubClassify();

        errorLayout.setOnActiveClickListener(this);
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getAbroad();
    }

    public void successful(RAbroadBO rAbroadBO) {
        rAbroadClassifyBOs = rAbroadBO.categorys;
        commodityListBOs = rAbroadClassifyBOs.get(SELECT_POSITION).commodityList;
        rListCommodityBOs = rAbroadBO.commodityList;
        rAdBOs = rAbroadBO.advList;

        int len = rAbroadBO.topAdvList.size();
        ad = new String[len];
        for (int i = 0; i < len; i++) {
            rAdBO = new RAdBO();
            rAdBO = rAbroadBO.topAdvList.get(i);
            ad[i] = NetConfig.ImageUrl + rAdBO.advertisementPic;
        }
        slideShowView.setPics(ad, position -> {
            rAdBO = rAbroadBO.topAdvList.get(position);
            if (rAdBO.isCommodity) {
                bundle = new Bundle();
                bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
                bundle.putInt(getString(R.string.intent_commodityDetailId), rAdBO.commodityDetailId);
                bundle.putInt(getString(R.string.intent_commodityType), rAdBO.commodityType);
                openActivity(CommodityInfoActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putString(getString(R.string.intent_value), rAdBO.connect);
                openActivity(WebViewActivity.class, bundle);
            }
        });
        addView();
        LogUtils.d(TAG, "断点0");
        classifyListAdapter.setItems(rAbroadClassifyBOs);
        LogUtils.d(TAG, "断点1");
        subClassifyListAdapter.setItems(commodityListBOs);
        LogUtils.d(TAG, "断点2");
        errorLayout.setState(ErrorLayout.HIDE, "");
        scrollView.setVisibility(View.VISIBLE);
    }

    public void failed(String msg) {
        if (isNetWorkInvalid(msg))
            errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        else
            errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void getSubCommodity(List<RAbroadCommodityBO> rAbroadCommodityBOs) {
        errorLayout.setState(ErrorLayout.HIDE, "");
        if (rAbroadCommodityBOs.size() == 0) {
            MyUtils.showToast(getApplicationContext(), "目前没有商品");
        } else {
            SELECT_POSITION = POSITION;
            classifyListAdapter.notifyDataSetChanged();
            subClassifyListAdapter.setItems(rAbroadCommodityBOs);
        }
    }

    private void addView() {
        menu = getResources().getStringArray(R.array.abroad_menu);
        content = getResources().getStringArray(R.array.abroad_content);
        for (int i = 0; i < rListCommodityBOs.size(); i++) {
            if (rListCommodityBOs.get(i).size() == 0)
                continue;

            View view = LayoutInflater.from(this).inflate(R.layout.layout_abroad_commodity, null);
            TextView titleTxt = (TextView) view.findViewById(R.id.txt_abroad_commodity_title);
            TextView contentTxt = (TextView) view.findViewById(R.id.txt_abroad_commodity_content);
            LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.llayout_abroad_commodity1);
            LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.llayout_abroad_commodity2);
            LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.llayout_abroad_commodity3);
            ImageView img1 = (ImageView) view.findViewById(R.id.img_abroad_commodity1);
            ImageView img2 = (ImageView) view.findViewById(R.id.img_abroad_commodity2);
            ImageView img3 = (ImageView) view.findViewById(R.id.img_abroad_commodity3);
            TextView name1Txt = (TextView) view.findViewById(R.id.txt_abroad_commodity1_name);
            TextView name2Txt = (TextView) view.findViewById(R.id.txt_abroad_commodity2_name);
            TextView name3Txt = (TextView) view.findViewById(R.id.txt_abroad_commodity3_name);
            TextView price1Txt = (TextView) view.findViewById(R.id.txt_abroad_commodity1_price);
            TextView price2Txt = (TextView) view.findViewById(R.id.txt_abroad_commodity2_price);
            TextView price3Txt = (TextView) view.findViewById(R.id.txt_abroad_commodity3_price);

            titleTxt.setText(menu[i]);
            contentTxt.setText(content[i]);

            linearLayout1.setOnClickListener(this);
            linearLayout2.setOnClickListener(this);
            linearLayout3.setOnClickListener(this);
            linearLayout1.setTag(R.id.tag_first, i);
            linearLayout2.setTag(R.id.tag_first, i);
            linearLayout3.setTag(R.id.tag_first, i);
            linearLayout1.setTag(R.id.tag_second, 0);
            linearLayout2.setTag(R.id.tag_second, 1);
            linearLayout3.setTag(R.id.tag_second, 2);


            if (rListCommodityBOs.get(i).size() > 0) {
                Glide.with(this).load(NetConfig.ImageUrl + rListCommodityBOs.get(i).get(0).listPic).into(img1);
                Glide.with(this).load(NetConfig.ImageUrl + rListCommodityBOs.get(i).get(1).listPic).into(img2);
                Glide.with(this).load(NetConfig.ImageUrl + rListCommodityBOs.get(i).get(2).listPic).into(img3);
                name1Txt.setText(rListCommodityBOs.get(i).get(0).commodityName);
                name2Txt.setText(rListCommodityBOs.get(i).get(1).commodityName);
                name3Txt.setText(rListCommodityBOs.get(i).get(2).commodityName);
                price1Txt.setText("￥ " + rListCommodityBOs.get(i).get(0).lowestPriceDetail.platformPrice);
                price2Txt.setText("￥ " + rListCommodityBOs.get(i).get(1).lowestPriceDetail.platformPrice);
                price3Txt.setText("￥ " + rListCommodityBOs.get(i).get(2).lowestPriceDetail.platformPrice);

                switch (rListCommodityBOs.get(i).size()) {
                    case 1:
                        linearLayout2.setVisibility(View.GONE);
                        linearLayout3.setVisibility(View.GONE);
                        break;
                    case 2:
                        linearLayout3.setVisibility(View.GONE);
                        break;
                }
            }
            commodityLayout.addView(view);
        }
        for (int i = 2; i < rAdBOs.size() + 2; i++) {
            if (rAdBOs.get(i - 2).size() == 0)
                continue;
            View view = LayoutInflater.from(this).inflate(R.layout.layout_abroad_advertisement, null);
            TextView titleTxt = (TextView) view.findViewById(R.id.txt_abroad_advertisement_title);
            TextView contentTxt = (TextView) view.findViewById(R.id.txt_abroad_advertisement_content);
            ImageView img1 = (ImageView) view.findViewById(R.id.img_abroad_advertisement1);
            ImageView img2 = (ImageView) view.findViewById(R.id.img_abroad_advertisement2);
            ImageView img3 = (ImageView) view.findViewById(R.id.img_abroad_advertisement3);
            ImageView img4 = (ImageView) view.findViewById(R.id.img_abroad_advertisement4);
            ImageView img5 = (ImageView) view.findViewById(R.id.img_abroad_advertisement5);
            img1.setOnClickListener(this);
            img2.setOnClickListener(this);
            img3.setOnClickListener(this);
            img4.setOnClickListener(this);
            img5.setOnClickListener(this);

            int space = (int) getResources().getDimension(R.dimen.dp_10);
            int width = (MyUtils.getScreenWidth(this) - space) / 2;
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(width, width / 2);
            img1.setLayoutParams(layoutParams1);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(width, width / 2);
            layoutParams2.setMargins(space, 0, 0, 0);
            img2.setLayoutParams(layoutParams2);

            int width2 = (MyUtils.getScreenWidth(this) - space * 4) / 3;
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(width2, width2 * 4 / 3);
            img3.setLayoutParams(layoutParams3);
            img5.setLayoutParams(layoutParams3);
            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(width2, width2 * 4 / 3);
            layoutParams4.setMargins(space, 0, space, 0);
            img4.setLayoutParams(layoutParams4);

            titleTxt.setText(menu[i]);
            contentTxt.setText(content[i]);
            int len = rAdBOs.get(i - 2).size();
            if (len > 0) {
                img2.setVisibility(len > 1 ? View.VISIBLE : View.GONE);
                img3.setVisibility(len > 2 ? View.VISIBLE : View.GONE);
                img4.setVisibility(len > 3 ? View.VISIBLE : View.GONE);
                img5.setVisibility(len > 4 ? View.VISIBLE : View.GONE);

                Glide.with(this).load(NetConfig.ImageUrl + rAdBOs.get(i - 2).get(0).advertisementPic).into(img1);
                if (len > 1)
                    Glide.with(this).load(NetConfig.ImageUrl + rAdBOs.get(i - 2).get(1).advertisementPic).into(img2);
                if (len > 2)
                    Glide.with(this).load(NetConfig.ImageUrl + rAdBOs.get(i - 2).get(2).advertisementPic).into(img3);
                if (len > 3)
                    Glide.with(this).load(NetConfig.ImageUrl + rAdBOs.get(i - 2).get(3).advertisementPic).into(img4);
                if (len > 4)
                    Glide.with(this).load(NetConfig.ImageUrl + rAdBOs.get(i - 2).get(4).advertisementPic).into(img5);
            }
            img1.setTag(R.id.tag_first, i);
            img2.setTag(R.id.tag_first, i);
            img3.setTag(R.id.tag_first, i);
            img4.setTag(R.id.tag_first, i);
            img5.setTag(R.id.tag_first, i);
            img1.setTag(R.id.tag_second, 0);
            img2.setTag(R.id.tag_second, 1);
            img3.setTag(R.id.tag_second, 2);
            img4.setTag(R.id.tag_second, 3);
            img5.setTag(R.id.tag_second, 4);
            advertisementLayout.addView(view);
        }
        LogUtils.d(TAG, "断点");
    }

    private void initClassify() {
        classifyRecycler.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        classifyRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.dp_10))
                .build());

        classifyListAdapter = new AbroadClassifyListAdapter(this, BaseListAdapter.NEITHER);
        classifyRecycler.setAdapter(classifyListAdapter);
        classifyListAdapter.setOnItemClickListener(this);
    }

    private void initSubClassify() {
        commodityRecycler.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        subClassifyListAdapter = new AbroadSubClassifyListAdapter(this, BaseListAdapter.NEITHER);
        commodityRecycler.setAdapter(subClassifyListAdapter);
        subClassifyListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        if (flag == 0)
            mPresenter.getAbroad();
        else
            mPresenter.getSubCommodity(rAbroadClassifyBOs.get(SELECT_POSITION).id);
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        switch (((View) view.getParent()).getId()) {
            case R.id.recycler_classify:
                SELECT_POSITION = POSITION;
                POSITION = position;
                errorLayout.setState(ErrorLayout.LOADING, "");
                mPresenter.getSubCommodity(rAbroadClassifyBOs.get(position).id);
                break;
            case R.id.recycler_commodity:
                bundle = new Bundle();
                bundle.putInt(getString(R.string.intent_commodityId), subClassifyListAdapter.getItem(position).lowestPriceDetail.commodityId);
                bundle.putInt(getString(R.string.intent_commodityDetailId), subClassifyListAdapter.getItem(position).lowestPriceDetail.id);
                bundle.putInt(getString(R.string.intent_commodityType), 0);
                openActivity(CommodityInfoActivity.class, bundle);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        bundle = new Bundle();
        index1 = (int) v.getTag(R.id.tag_first);
        index2 = (int) v.getTag(R.id.tag_second);
        LogUtils.d(TAG, "index1:" + index1 + " index2:" + index2);
        if (index1 < 2) {
            bundle.putInt(getString(R.string.intent_commodityId), rListCommodityBOs.get(index1).get(index2).lowestPriceDetail.commodityId);
            bundle.putInt(getString(R.string.intent_commodityDetailId), rListCommodityBOs.get(index1).get(index2).lowestPriceDetail.id);
            bundle.putInt(getString(R.string.intent_commodityType), 0);
            openActivity(CommodityInfoActivity.class, bundle);
        } else {
            index1 -= 2;
            rAdBO = rAdBOs.get(index1).get(index2);
            if (rAdBO.isCommodity) {
                bundle = new Bundle();
                bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
                bundle.putInt(getString(R.string.intent_commodityDetailId), rAdBO.commodityDetailId);
                bundle.putInt(getString(R.string.intent_commodityType), 0);
                openActivity(CommodityInfoActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putString(getString(R.string.intent_value), rAdBO.connect);
                openActivity(WebViewActivity.class, bundle);
            }
        }
    }
}
