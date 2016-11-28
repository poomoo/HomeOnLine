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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.AbroadPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RAbroadBO;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RCountryBO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类名 NewAbroadActivity
 * 描述 新版跨境直邮
 * 作者 李苜菲
 * 日期 2016/11/23 11:56
 */
public class NewAbroadActivity extends BaseDaggerActivity<AbroadPresenter> implements ErrorLayout.OnActiveClickListener {
    @Bind(R.id.scroll_abroad)
    ScrollView scrollView;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.llayout_abroad_category)
    LinearLayout categoryLayout;
    @Bind(R.id.llayout_abroad_classify)
    LinearLayout classifyLayout;

    @Bind(R.id.img_abroad_global)
    ImageView globalImg;
    @Bind(R.id.img_abroad_global_commodity1)
    ImageView globalCommodity1Img;
    @Bind(R.id.img_abroad_global_commodity2)
    ImageView globalCommodity2Img;
    @Bind(R.id.img_abroad_global_commodity3)
    ImageView globalCommodity3Img;

    @Bind(R.id.img_abroad_country1)
    ImageView country1Img;
    @Bind(R.id.img_abroad_country2)
    ImageView country2Img;
    @Bind(R.id.img_abroad_country3)
    ImageView country3Img;

    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    /*顶部分类*/
    private LinearLayout categorySubLayout;
    private LinearLayout categoryInfoLayout;
    private ImageView categoryImg;
    private TextView categoryTxt;
    private int categoryWidth;
    private LinearLayout.LayoutParams categoryParams;
    private int group;

    /*全球精品*/
    private LinearLayout.LayoutParams globalParams;
    private LinearLayout.LayoutParams globalParams2;
    private int globalImgWidth = 0;

    /*国家地区馆*/
    private LinearLayout.LayoutParams countryParams;
    private int countryWidth = 0;

    /*底部分类*/
    private LinearLayout.LayoutParams bottomParams;
    private int bottomImgWidth = 0;


    private int dp6 = 0;
    private int dp10 = 0;

    private String[] title;
    private String[] subTitle;
    private Bundle bundle;
    private int index1;
    private int index2;
    private RAdBO rAdBO;
    private int len = 0;

    private List<RAdBO> earthAdv;
    private List<RCountryBO> countrys;
    private RCountryBO rCountryBO;
    private String categoryId = "";
    private RAbroadBO rAbroadBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBack();
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_abroad_new;
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

        dp6 = (int) getResources().getDimension(R.dimen.dp_6);
        dp10 = (int) getResources().getDimension(R.dimen.dp_10);

        categoryWidth = MyUtils.getScreenWidth(this) / 3;
        categoryParams = new LinearLayout.LayoutParams(categoryWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        categoryParams.gravity = Gravity.CENTER;
        categoryParams.setMargins(0, 0, 0, dp10);

        globalImg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));
        globalImgWidth = MyUtils.getScreenWidth(this) - dp6 * 4;
        globalImgWidth /= 3;
        globalParams = new LinearLayout.LayoutParams(globalImgWidth, globalImgWidth * 2);
        globalParams2 = new LinearLayout.LayoutParams(globalImgWidth, globalImgWidth * 2);
        globalCommodity1Img.setLayoutParams(globalParams);
        globalParams2.setMargins(dp6, 0, dp6, 0);
        globalCommodity2Img.setLayoutParams(globalParams2);
        globalCommodity3Img.setLayoutParams(globalParams);

        countryWidth = MyUtils.getScreenWidth(this) / 3;
        countryParams = new LinearLayout.LayoutParams(countryWidth, countryWidth * 3 / 4);
        country1Img.setLayoutParams(countryParams);
        country2Img.setLayoutParams(countryParams);
        country3Img.setLayoutParams(countryParams);

        bottomImgWidth = MyUtils.getScreenWidth(this) - dp10 * 3;
        bottomImgWidth /= 2;
        bottomParams = new LinearLayout.LayoutParams(bottomImgWidth, bottomImgWidth * 3 / 2);

        errorLayout.setOnActiveClickListener(this);
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getAbroad();
    }

    private void addView(RAbroadBO rAbroadBO) {
        earthAdv = rAbroadBO.earthAdv;
        countrys = rAbroadBO.countrys;

        /*顶部分类*/
        len = rAbroadBO.categorys.size();
        group = len / 3 + len % 3;
        for (int i = 0; i < group; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad_category, null);
            categorySubLayout = (LinearLayout) view.findViewById(R.id.llayout_abroad_category);
            for (int j = 0; j < 3; j++) {
                View subView = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad_category_info, null);
                categoryInfoLayout = (LinearLayout) subView.findViewById(R.id.llayout_abroad_category_info);
                categoryImg = (ImageView) subView.findViewById(R.id.img_abroad_category);
                categoryTxt = (TextView) subView.findViewById(R.id.txt_abroad_category);

                categoryInfoLayout.setLayoutParams(categoryParams);
                Glide.with(this).load(NetConfig.ImageUrl + rAbroadBO.categorys.get(i * 3 + j).pcPic).into(categoryImg);
                categoryTxt.setText(rAbroadBO.categorys.get(i * 3 + j).categoryName);

                categoryInfoLayout.setTag(R.id.tag_first, rAbroadBO.categorys.get(i * 3 + j).id + "");
                categoryInfoLayout.setOnClickListener(new classifyClick());

                categorySubLayout.addView(subView);
                if (i * 3 + j == len - 1)
                    break;
            }
            categoryLayout.addView(view);
        }

        /*全球精品*/
        Glide.with(this).load(NetConfig.ImageUrl + earthAdv.get(0).advertisementPic).into(globalImg);
        Glide.with(this).load(NetConfig.ImageUrl + earthAdv.get(1).advertisementPic).into(globalCommodity1Img);
        Glide.with(this).load(NetConfig.ImageUrl + earthAdv.get(2).advertisementPic).into(globalCommodity2Img);
        Glide.with(this).load(NetConfig.ImageUrl + earthAdv.get(3).advertisementPic).into(globalCommodity3Img);
        globalCommodity1Img.setTag(R.id.tag_first, 1);
        globalCommodity2Img.setTag(R.id.tag_first, 2);
        globalCommodity3Img.setTag(R.id.tag_first, 3);

        /*国家地区馆*/
        Glide.with(this).load(NetConfig.ImageUrl + countrys.get(0).countrySign).into(country1Img);
        Glide.with(this).load(NetConfig.ImageUrl + countrys.get(1).countrySign).into(country2Img);
        Glide.with(this).load(NetConfig.ImageUrl + countrys.get(2).countrySign).into(country3Img);
        country1Img.setTag(R.id.tag_first, 0);
        country2Img.setTag(R.id.tag_first, 1);
        country3Img.setTag(R.id.tag_first, 2);

        /*底部分类*/
        title = getResources().getStringArray(R.array.abroad_title);
        subTitle = getResources().getStringArray(R.array.abroad_subTitle);
        len = rAbroadBO.advList.size();
        for (int i = 0; i < len; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad, null);
            ImageView titleImg = (ImageView) view.findViewById(R.id.img_abroad_title);
            TextView titleTxt = (TextView) view.findViewById(R.id.txt_abroad_title);
            TextView subTitleTxt = (TextView) view.findViewById(R.id.txt_abroad_subTitle);
            ImageView img1 = (ImageView) view.findViewById(R.id.img_abroad_commodity1);
            ImageView img2 = (ImageView) view.findViewById(R.id.img_abroad_commodity2);

            TextView moreTxt = (TextView) view.findViewById(R.id.txt_abroad_more);

            img1.setLayoutParams(bottomParams);
            img2.setLayoutParams(bottomParams);

            Glide.with(this).load(NetConfig.ImageUrl + rAbroadBO.advList.get(i).pcPic).into(titleImg);
            titleTxt.setText(rAbroadBO.advList.get(i).categoryName);
            subTitleTxt.setText(subTitle[i]);
            if (rAbroadBO.advList.get(i).advs.size() > 0) {
                img1.setOnClickListener(new commodityClick());
                Glide.with(this).load(NetConfig.ImageUrl + rAbroadBO.advList.get(i).advs.get(0).advertisementPic).placeholder(R.drawable.replace2b3).into(img1);
            }
            if (rAbroadBO.advList.get(i).advs.size() > 1) {
                img2.setOnClickListener(new commodityClick());
                Glide.with(this).load(NetConfig.ImageUrl + rAbroadBO.advList.get(i).advs.get(1).advertisementPic).placeholder(R.drawable.replace2b3).into(img2);
            }

            moreTxt.setOnClickListener(new classifyClick());
            img1.setTag(R.id.tag_first, i);
            img2.setTag(R.id.tag_first, i);
            moreTxt.setTag(R.id.tag_first, rAbroadBO.advList.get(i).categoryId);
            img1.setTag(R.id.tag_second, 0);
            img2.setTag(R.id.tag_second, 1);

            classifyLayout.addView(view);
        }
    }

    public void toGlobal(View view) {
        openActivity(AbroadGlobalActivity.class);
    }

    public void toCountry(View view) {
        openActivity(AbroadCountryActivity.class);
    }

//    @OnClick({R.id.llayout_abroad_classify1, R.id.llayout_abroad_classify2, R.id.llayout_abroad_classify3, R.id.llayout_abroad_classify4, R.id.llayout_abroad_classify5, R.id.llayout_abroad_classify6})
//    void OnClassifyClick(View view) {
//        switch (view.getId()) {
//            /*分类*/
//            case R.id.llayout_abroad_classify1:
//                break;
//            case R.id.llayout_abroad_classify2:
//                MyUtils.showToast(getApplicationContext(), "点击了" + title[1]);
//                break;
//            case R.id.llayout_abroad_classify3:
//                MyUtils.showToast(getApplicationContext(), "点击了" + title[2]);
//                break;
//            case R.id.llayout_abroad_classify4:
//                MyUtils.showToast(getApplicationContext(), "点击了" + title[3]);
//                break;
//            case R.id.llayout_abroad_classify5:
//                MyUtils.showToast(getApplicationContext(), "点击了" + title[4]);
//                break;
//            case R.id.llayout_abroad_classify6:
//                MyUtils.showToast(getApplicationContext(), "点击了其他");
//                break;
//        }
//    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getAbroad();
    }

    public void failed(String msg) {
        errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void successful(RAbroadBO rAbroadBO) {
        scrollView.setVisibility(View.VISIBLE);
        errorLayout.setState(ErrorLayout.HIDE, "");
        String[] urls = new String[rAbroadBO.topAdvList.size()];
        int i = 0;
        for (RAdBO rAdBO : rAbroadBO.topAdvList)
            urls[i++] = NetConfig.ImageUrl + rAdBO.advertisementPic;
        slideShowView.setPics(urls, position -> {
            rAdBO = rAbroadBO.topAdvList.get(position);
            jump();
        });
        this.rAbroadBO = rAbroadBO;
        addView(rAbroadBO);
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

    public void globalClick(View view) {
        rAdBO = earthAdv.get((int) view.getTag(R.id.tag_first));
        jump();
    }

    public void countryClick(View view) {
        if ((int) view.getTag(R.id.tag_first) < countrys.size()) {
            rCountryBO = countrys.get((int) view.getTag(R.id.tag_first));
            bundle = new Bundle();
            bundle.putInt(getString(R.string.intent_countryId), rCountryBO.id);
            bundle.putString(getString(R.string.intent_countryName), rCountryBO.chName);
            openActivity(AbroadCountryInfoActivity.class, bundle);
        }
    }

    class classifyClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            bundle = new Bundle();
            bundle.putString(getString(R.string.intent_categoryId), (String) v.getTag(R.id.tag_first));
            openActivity(ClassifyListActivity.class, bundle);
        }
    }

    class commodityClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            index1 = (int) v.getTag(R.id.tag_first);
            index2 = (int) v.getTag(R.id.tag_second);
            rAdBO = rAbroadBO.advList.get(index1).advs.get(index2);
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

}
