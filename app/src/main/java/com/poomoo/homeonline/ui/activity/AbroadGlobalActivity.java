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

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.listeners.AdvertisementListener;
import com.poomoo.homeonline.presenters.GlobalPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RGlobalBO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 AbroadGlobalActivity
 * 描述 跨境全球精品
 * 作者 李苜菲
 * 日期 2016/11/23 17:05
 */
public class AbroadGlobalActivity extends BaseDaggerActivity<GlobalPresenter> implements ErrorLayout.OnActiveClickListener, View.OnClickListener {
    @Bind(R.id.scrollView_global)
    ScrollView scrollView;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.img_abroad_global_recommend1)
    ImageView recommend1Img;
    @Bind(R.id.img_abroad_global_recommend2)
    ImageView recommend2Img;
    @Bind(R.id.img_abroad_global_recommend3)
    ImageView recommend3Img;
    @Bind(R.id.img_abroad_global_recommend4)
    ImageView recommend4Img;
    @Bind(R.id.img_abroad_global_brand1)
    ImageView brand1Img;
    @Bind(R.id.img_abroad_global_brand2)
    ImageView brand2Img;
    @Bind(R.id.img_abroad_global_brand3)
    ImageView brand3Img;
    @Bind(R.id.llayout_abroad_global_special)
    LinearLayout specialLayout;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    /*好货推荐*/
    private int recommendWidth = 0;
    private LinearLayout.LayoutParams recommend1Params;
    private LinearLayout.LayoutParams recommend2Params;

    /*品牌精选*/
    private int brandWidth = 0;
    private LinearLayout.LayoutParams brandParams;

    /*专题特卖*/
    private int imgWidth = 0;
    private int imgHeight = 0;
    private LinearLayout.LayoutParams adParams;
    private LinearLayout.LayoutParams imgParams;

    private int dp4 = 0;
    private int dp10 = 0;
    private List<RAdBO> goodList;
    private List<RAdBO> brandList;
    private List<RAdBO> specialList;
    private String[] urls;
    private RAdBO rAdBO;
    private Bundle bundle;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setBack();
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_abroad_global;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_global;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        dp4 = (int) getResources().getDimension(R.dimen.dp_4);
        dp10 = (int) getResources().getDimension(R.dimen.dp_10);

        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为2:1

        recommendWidth = MyUtils.getScreenWidth(this) - dp10 * 2 - dp4;
        recommendWidth /= 2;
        recommend1Params = new LinearLayout.LayoutParams(recommendWidth, recommendWidth / 2);
        recommend2Params = new LinearLayout.LayoutParams(recommendWidth, recommendWidth / 2);
        recommend2Params.setMargins(dp4, 0, 0, 0);
        recommend1Img.setLayoutParams(recommend1Params);
        recommend2Img.setLayoutParams(recommend2Params);
        recommend3Img.setLayoutParams(recommend1Params);
        recommend4Img.setLayoutParams(recommend2Params);

        brandWidth = MyUtils.getScreenWidth(this);
        brandParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, brandWidth / 3);
        brandParams.setMargins(0, 0, 0, dp10);
        brand1Img.setLayoutParams(brandParams);
        brand2Img.setLayoutParams(brandParams);
        brand3Img.setLayoutParams(brandParams);

        imgWidth = MyUtils.getScreenWidth(this) - (int) getResources().getDimension(R.dimen.dp_10) * 2;
        imgWidth /= 2;
        adParams = new LinearLayout.LayoutParams(imgWidth, imgWidth);
        imgHeight = imgWidth - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        imgParams = new LinearLayout.LayoutParams(imgWidth, imgHeight / 2);

        errorLayout.setOnActiveClickListener(this);
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getGloablInfo();
    }

    private void addView(RGlobalBO rGlobalBO) {
        goodList = rGlobalBO.goodAdvs;
        brandList = rGlobalBO.pinpaiAdvs;
        specialList = rGlobalBO.temaiAdvs;

        urls = new String[rGlobalBO.topAdv.size()];
        for (int i = 0; i < rGlobalBO.topAdv.size(); i++)
            urls[i] = NetConfig.ImageUrl + rGlobalBO.topAdv.get(i).advertisementPic;
        slideShowView.setPics(urls, position -> {
            rAdBO = rGlobalBO.topAdv.get(position);
            jump();
        });

        if (goodList.size() > 0)
            Glide.with(this).load(NetConfig.ImageUrl + goodList.get(0).advertisementPic).placeholder(R.drawable.replace2).into(recommend1Img);
        if (goodList.size() > 1)
            Glide.with(this).load(NetConfig.ImageUrl + goodList.get(1).advertisementPic).placeholder(R.drawable.replace2).into(recommend2Img);
        if (goodList.size() > 2)
            Glide.with(this).load(NetConfig.ImageUrl + goodList.get(2).advertisementPic).placeholder(R.drawable.replace2).into(recommend3Img);
        if (goodList.size() > 3)
            Glide.with(this).load(NetConfig.ImageUrl + goodList.get(3).advertisementPic).placeholder(R.drawable.replace2).into(recommend4Img);
        recommend1Img.setTag(R.id.tag_first, 0);
        recommend2Img.setTag(R.id.tag_first, 1);
        recommend3Img.setTag(R.id.tag_first, 2);
        recommend4Img.setTag(R.id.tag_first, 3);

        if (brandList.size() > 0)//http://i.gasgoo.com/upload/TrainAd/201451294721.jpg
            Glide.with(this).load(NetConfig.ImageUrl + brandList.get(0).advertisementPic).placeholder(R.drawable.replace3b1).into(brand1Img);
        if (brandList.size() > 1)
            Glide.with(this).load(NetConfig.ImageUrl + brandList.get(1).advertisementPic).placeholder(R.drawable.replace3b1).into(brand2Img);
        if (brandList.size() > 2)
            Glide.with(this).load(NetConfig.ImageUrl + brandList.get(2).advertisementPic).placeholder(R.drawable.replace3b1).into(brand3Img);
        brand1Img.setTag(R.id.tag_first, 0);
        brand2Img.setTag(R.id.tag_first, 1);
        brand3Img.setTag(R.id.tag_first, 2);

        int group = specialList.size() / 3 + specialList.size() % 3;
        for (int i = 0; i < group; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_activity_abroad_global, null);
            ImageView adImg = (ImageView) view.findViewById(R.id.img_abroad_global_ad);
            ImageView img1 = (ImageView) view.findViewById(R.id.img_abroad_global_commodity1);
            ImageView img2 = (ImageView) view.findViewById(R.id.img_abroad_global_commodity2);

            adImg.setLayoutParams(adParams);
            img1.setLayoutParams(imgParams);
            img2.setLayoutParams(imgParams);

            if (specialList.size() > i * 3)
                Glide.with(this).load(NetConfig.ImageUrl + specialList.get(i * 3).advertisementPic).placeholder(R.drawable.replace).into(adImg);
            if (specialList.size() > i * 3 + 1)
                Glide.with(this).load(NetConfig.ImageUrl + specialList.get(i * 3 + 1).advertisementPic).placeholder(R.drawable.replace2).into(img1);
            if (specialList.size() > i * 3 + 2)
                Glide.with(this).load(NetConfig.ImageUrl + specialList.get(i * 3 + 2).advertisementPic).placeholder(R.drawable.replace2).into(img2);

            adImg.setTag(R.id.tag_first, i * 3);
            img1.setTag(R.id.tag_first, i * 3 + 1);
            img2.setTag(R.id.tag_first, i * 3 + 2);

            adImg.setOnClickListener(this);
            img1.setOnClickListener(this);
            img2.setOnClickListener(this);

            specialLayout.addView(view);
        }
    }

    public void failed(String msg) {
        errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void successful(RGlobalBO rGlobalBO) {
        addView(rGlobalBO);
        errorLayout.setState(ErrorLayout.HIDE, "");
        scrollView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getGloablInfo();
    }

    public void goodClick(View view) {
        position = (int) view.getTag(R.id.tag_first);
        if (position < goodList.size()) {
            rAdBO = goodList.get(position);
            jump();
        }
    }


    public void brandClick(View view) {
        position = (int) view.getTag(R.id.tag_first);
        if (position < goodList.size()) {
            rAdBO = brandList.get(position);
            jump();
        }
    }


    @Override
    public void onClick(View view) {
        position = (int) view.getTag(R.id.tag_first);
        if (position < specialList.size()) {
            rAdBO = specialList.get(position);
            jump();
        }
    }

    public void jump() {
        if (rAdBO.isCommodity) {
            bundle = new Bundle();
            bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
//            bundle.putInt(getString(R.string.intent_commodityDetailId), rAdBO.commodityDetailId);
            bundle.putInt(getString(R.string.intent_commodityType), 0);
            openActivity(CommodityInfoActivity.class, bundle);
        } else {
            bundle = new Bundle();
            bundle.putString(getString(R.string.intent_value), rAdBO.connect);
            openActivity(WebViewActivity.class, bundle);
        }
    }
}
