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

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.StatusBarUtil;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.listeners.ScrollViewListener;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.MyScrollView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.homeonline.ui.listener.AdvertisementListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 CommodityInfoActivity
 * 描述 商品详情
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class CommodityInfoActivity extends BaseActivity implements ScrollViewListener {
    @Bind(R.id.rlayout_info_title)
    RelativeLayout titleBar;
    @Bind(R.id.myScrollView)
    MyScrollView myScrollView;
    @Bind(R.id.slide_commodity_info)
    SlideShowView slideShowView;
    @Bind(R.id.img_info_back)
    ImageView backImg;
    @Bind(R.id.img_info_collect)
    ImageView collectImg;
    @Bind(R.id.txt_info_name)
    TextView nameTxt;
    @Bind(R.id.txt_info_price)
    TextView priceTxt;
    @Bind(R.id.txt_info_old_price)
    TextView oldPriceTxt;
    @Bind(R.id.txt_inventory)
    TextView inventoryTxt;
    @Bind(R.id.edt_info_count)
    EditText countEdt;

    private int screenWidth = 0;
    private String[] pics = new String[]{"http://img.jiayou9.com/jyzx//upload/company/20160402/1459581133163.jpg", "http://img.jiayou9.com/jyzx//upload/company/20160402/1459581133960.jpg", "http://img.jiayou9.com/jyzx//upload/company/20160402/1459581136663.jpg", "http://img.jiayou9.com/jyzx//upload/company/20160402/1459581137663.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        screenWidth = MyUtils.getScreenWidth(this);
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

    private void init() {
        StatusBarUtil.setTransparent(this);
        titleBar.getBackground().setAlpha(0);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            titleBar.setGravity(Gravity.BOTTOM);
//            titleBar.setPadding(0, getBarHeight(), 0, 0);
//        }
        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenWidth));
        slideShowView.setPics(pics, new AdvertisementListener() {
            @Override
            public void onAdvClick(int position) {

            }
        });

        myScrollView.setScrollViewListener(this);

        oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * 增加商品数量
     *
     * @param view
     */
    public void add(View view) {

    }

    /**
     * 减少商品数量
     *
     * @param view
     */
    public void minus(View view) {

    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
//        LogUtils.d(TAG, "screenWidth:" + screenWidth + "getBarHeight():" + getBarHeight() + "getScrollY:" + scrollView.getScrollY() + "titleBar:" + titleBar.getHeight());
        if (scrollView.getScrollY() < screenWidth - titleBar.getHeight())
            titleBar.getBackground().setAlpha(0);
//            titleBar.getBackground().setAlpha(240 * scrollView.getScrollY() / (screenWidth - titleBar.getHeight()));
        else
            titleBar.getBackground().setAlpha(255);
    }
}
