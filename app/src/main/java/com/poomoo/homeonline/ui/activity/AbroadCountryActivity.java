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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.CountryListPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RCountryBO;
import com.poomoo.model.response.RCountryListBO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 AbroadCountryActivity
 * 描述 跨境国家馆
 * 作者 李苜菲
 * 日期 2016/11/23 17:05
 */
public class AbroadCountryActivity extends BaseDaggerActivity<CountryListPresenter> implements ErrorLayout.OnActiveClickListener, View.OnClickListener {
    @Bind(R.id.scrollView_country)
    ScrollView scrollView;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.llayout_country)
    LinearLayout countryLayout;
    @Bind(R.id.llayout_country_fashion)
    LinearLayout fashionLayout;
    @Bind(R.id.llayout_country_popularity)
    LinearLayout popularityLayout;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    /*国家列表*/
    LinearLayout countrySubLayout;
    ImageView country1Img;
    ImageView country2Img;
    TextView moreTxt;
    private int countryImgWidth = 0;
    private LinearLayout.LayoutParams countryParams;

    /*人气商品*/
    LinearLayout fashionSubLayout;
    //    LinearLayout fashionCommodityLayout;
    ImageView fashionImg;
    //    TextView fashionNameTxt;
//    TextView fashionSaleTxt;
//    TextView fashionPriceTxt;
//    TextView fashionOldPriceTxt;
//    private int fashionLayoutWidth = 0;
    private int fashionImgWidth = 0;
    //    private LinearLayout.LayoutParams fashionLayoutParams;
//    private LinearLayout.LayoutParams fashionLayout2Params;
    private LinearLayout.LayoutParams fashionImgParams;
    private LinearLayout.LayoutParams fashionImg2Params;

    /*人气品牌*/
    ImageView popularityAdImg;
    //    LinearLayout popularityCommodity1Layout;
//    LinearLayout popularityCommodity2Layout;
    ImageView popularityCommodity1Img;
    ImageView popularityCommodity2Img;
    //    TextView popularityCommodityName1Txt;
//    TextView popularityCommodityName2Txt;
//    TextView popularityCommoditySale1Txt;
//    TextView popularityCommoditySale2Txt;
//    TextView popularityCommodityPrice1Txt;
//    TextView popularityCommodityPrice2Txt;
//    TextView popularityCommodityOldPrice1Txt;
//    TextView popularityCommodityOldPrice2Txt;
    private int popularityAdImgWidth = 0;
    private int popularityCommodityImgHeight = 0;
    private LinearLayout.LayoutParams popularityAdImgParams;
    private LinearLayout.LayoutParams popularityCommodityImgParams;

    private int dp1 = 0;
    private int dp4 = 0;
    private int dp10 = 0;
    private int dp30 = 0;
    private int countryCount = 0;
    private int countryGroup = 0;

    private List<RCountryBO> countrys;
    private List<RAdBO> renqiAdv;
    private List<RAdBO> danpinAdv;
    private RAdBO rAdBO;
    private Bundle bundle;
    private RCountryBO rCountryBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setBack();
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_abroad_country;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_country;
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

        dp1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        dp4 = (int) getResources().getDimension(R.dimen.dp_4);
        dp10 = (int) getResources().getDimension(R.dimen.dp_10);
        dp30 = (int) getResources().getDimension(R.dimen.dp_30);

        countryImgWidth = MyUtils.getScreenWidth(this) - dp30 * 2 - dp10;
        countryImgWidth /= 2;
        countryParams = new LinearLayout.LayoutParams(countryImgWidth, countryImgWidth / 2);
        countryParams.setMargins(0, 0, dp10, 0);

//        fashionLayoutWidth = MyUtils.getScreenWidth(this) - dp10 * 2 - dp4 * 2;
//        fashionLayoutWidth /= 3;
//        fashionLayoutParams = new LinearLayout.LayoutParams(fashionLayoutWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
//        fashionLayout2Params = new LinearLayout.LayoutParams(fashionLayoutWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
//        fashionLayout2Params.setMargins(dp4, 0, dp4, 0);

        fashionImgWidth = MyUtils.getScreenWidth(this) - dp10 * 4;
        fashionImgWidth /= 3;
        fashionImgParams = new LinearLayout.LayoutParams(fashionImgWidth, fashionImgWidth * 3 / 2);
        fashionImg2Params = new LinearLayout.LayoutParams(fashionImgWidth, fashionImgWidth * 3 / 2);
        fashionImg2Params.setMargins(dp10, 0, dp10, 0);

        popularityAdImgWidth = MyUtils.getScreenWidth(this) - dp10 * 2;
        popularityAdImgWidth /= 2;
        popularityAdImgParams = new LinearLayout.LayoutParams(popularityAdImgWidth, popularityAdImgWidth);

        popularityCommodityImgHeight = (popularityAdImgWidth - dp1) / 2;//高度为广告图片高度的一半
        popularityCommodityImgParams = new LinearLayout.LayoutParams(popularityAdImgWidth, popularityCommodityImgHeight);

        errorLayout.setOnActiveClickListener(this);
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getCountryList();
    }

    private void addView(RCountryListBO rCountryListBO) {
        countrys = rCountryListBO.countrys;
        danpinAdv = rCountryListBO.danpinAdv;
        renqiAdv = rCountryListBO.renqiAdv;
        /*国家列表*/
        countryCount = countrys.size();
        countryGroup = countryCount / 2;
        countryGroup++;//无论单双都要自加1，给期待更多留出位置
        for (int i = 0; i < countryGroup; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad_country_sub, null);
            countrySubLayout = (LinearLayout) view.findViewById(R.id.llayout_country_sub);

            View subView = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad_country, null);
            country1Img = (ImageView) subView.findViewById(R.id.img_country1);
            country2Img = (ImageView) subView.findViewById(R.id.img_country2);
            moreTxt = (TextView) subView.findViewById(R.id.txt_country_more);

            country1Img.setLayoutParams(countryParams);
            country2Img.setLayoutParams(countryParams);
            moreTxt.setLayoutParams(countryParams);

            countrySubLayout.addView(subView);

            if (i == (countryGroup - 1)) {//最后一组
                if (countryCount % 2 != 0) {//单出一个国家
                    Glide.with(this).load(NetConfig.ImageUrl + countrys.get(i * 2).countrySign).placeholder(R.drawable.replace2).into(country1Img);
                    country1Img.setTag(R.id.tag_first, i * 2);
                    country1Img.setOnClickListener(new OnCountryClick());
                    country2Img.setVisibility(View.GONE);
                    moreTxt.setVisibility(View.VISIBLE);
                } else {//否则只显示期待更多
                    country1Img.setVisibility(View.GONE);
                    country2Img.setVisibility(View.GONE);
                    moreTxt.setVisibility(View.VISIBLE);
                }
            } else {
                Glide.with(this).load(NetConfig.ImageUrl + countrys.get(i * 2).countrySign).placeholder(R.drawable.replace2).into(country1Img);
                Glide.with(this).load(NetConfig.ImageUrl + countrys.get(i * 2 + 1).countrySign).placeholder(R.drawable.replace2).into(country2Img);

                country1Img.setTag(R.id.tag_first, i * 2);
                country2Img.setTag(R.id.tag_first, i * 2 + 1);

                country1Img.setOnClickListener(new OnCountryClick());
                country2Img.setOnClickListener(new OnCountryClick());
            }
            countryLayout.addView(view);
        }

        /*时尚单品*/
        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad_country_fashion_sub, null);
            fashionSubLayout = (LinearLayout) view.findViewById(R.id.llayout_country_fashion_sub);
            for (int j = 0; j < 3; j++) {
                View subView = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad_country_fashion, null);
                fashionImg = (ImageView) subView.findViewById(R.id.img_country_fashion_commodity);
                fashionImg.setLayoutParams(fashionImgParams);
                if (j == 1) fashionImg.setLayoutParams(fashionImg2Params);
                else fashionImg.setLayoutParams(fashionImgParams);
                if (danpinAdv.size() > i * 3 + j) {
                    Glide.with(this).load(NetConfig.ImageUrl + danpinAdv.get(i * 3 + j).advertisementPic).placeholder(R.drawable.replace2b3).into(fashionImg);
                    fashionImg.setTag(R.id.tag_first, i * 3 + j);
                    fashionImg.setOnClickListener(new onFashionClick());
                }
                fashionSubLayout.addView(subView);
            }
            fashionLayout.addView(view);
        }

        /*人气品牌*/
        int group = renqiAdv.size() / 3 + renqiAdv.size() % 3;
        for (int i = 0; i < group; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad_country_popularity, null);

            popularityAdImg = (ImageView) view.findViewById(R.id.img_country_popularity_ad);
            popularityCommodity1Img = (ImageView) view.findViewById(R.id.img_country_popularity_commodity1);
            popularityCommodity2Img = (ImageView) view.findViewById(R.id.img_country_popularity_commodity2);

            popularityAdImg.setLayoutParams(popularityAdImgParams);
            popularityCommodity1Img.setLayoutParams(popularityCommodityImgParams);
            popularityCommodity2Img.setLayoutParams(popularityCommodityImgParams);

            if (renqiAdv.size() > i * 3) {
                popularityAdImg.setOnClickListener(this);
                Glide.with(this).load(NetConfig.ImageUrl + renqiAdv.get(i * 3).advertisementPic).placeholder(R.drawable.replace).into(popularityAdImg);
            }
            if (renqiAdv.size() > i * 3 + 1) {
                popularityCommodity1Img.setOnClickListener(this);
                Glide.with(this).load(NetConfig.ImageUrl + renqiAdv.get(i * 3 + 1).advertisementPic).placeholder(R.drawable.replace2).into(popularityCommodity1Img);
            }
            if (renqiAdv.size() > i * 3 + 2) {
                popularityCommodity2Img.setOnClickListener(this);
                Glide.with(this).load(NetConfig.ImageUrl + renqiAdv.get(i * 3 + 2).advertisementPic).placeholder(R.drawable.replace2).into(popularityCommodity2Img);
            }

            popularityAdImg.setTag(R.id.tag_first, i * 3);
            popularityCommodity1Img.setTag(R.id.tag_first, i * 3 + 1);
            popularityCommodity2Img.setTag(R.id.tag_first, i * 3 + 2);

            popularityLayout.addView(view);
        }

    }

    public void failed() {
        errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void successful(RCountryListBO rCountryListBO) {
        String[] urls = new String[rCountryListBO.topAdv.size()];
        int i = 0;
        for (RAdBO rAdBO : rCountryListBO.topAdv)
            urls[i++] = NetConfig.ImageUrl + rAdBO.advertisementPic;
        slideShowView.setPics(urls, position -> {
            rAdBO = rCountryListBO.topAdv.get(position);
            jump();
        });
        addView(rCountryListBO);
        errorLayout.setState(ErrorLayout.HIDE, "");
        scrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getCountryList();
    }

    class OnCountryClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            rCountryBO = countrys.get((int) v.getTag(R.id.tag_first));
            bundle = new Bundle();
            bundle.putInt(getString(R.string.intent_countryId), rCountryBO.id);
            bundle.putString(getString(R.string.intent_countryName), rCountryBO.chName);
            openActivity(AbroadCountryInfoActivity.class, bundle);
        }
    }

    class onFashionClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            rAdBO = danpinAdv.get((int) v.getTag(R.id.tag_first));
            jump();
        }
    }

    @Override
    public void onClick(View v) {
        rAdBO = renqiAdv.get((int) v.getTag(R.id.tag_first));
        jump();
    }

    public void jump() {
        if (rAdBO.isCommodity) {
            bundle = new Bundle();
            bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
            bundle.putInt(getString(R.string.intent_commodityType), 0);
            openActivity(CommodityInfoActivity.class, bundle);
        } else {
            bundle = new Bundle();
            bundle.putString(getString(R.string.intent_value), rAdBO.connect);
            openActivity(WebViewActivity.class, bundle);
        }
    }

}
