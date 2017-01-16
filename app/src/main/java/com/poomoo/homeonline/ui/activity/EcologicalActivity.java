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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.SpecialtyListCommodityAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.AddToCartListener;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollGridLayoutManager;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.NoScrollRecyclerView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RListCommodityBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

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
public class EcologicalActivity extends BaseActivity implements BaseListAdapter.OnItemClickListener {
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

    private SpecialtyListCommodityAdapter adapter;
    private LinearLayout.LayoutParams layoutParams;
    private int dp8;
    private int dp10;
    private int width;

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

    private void init() {
        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.titleTxt.setTextColor(ContextCompat.getColor(this, R.color.ecological));
        headerViewHolder.backImg.setOnClickListener(v -> {
            finish();
            getActivityOutToRight();
        });
        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为2:1

        dp8 = (int) getResources().getDimension(R.dimen.dp_8);
        dp10 = (int) getResources().getDimension(R.dimen.dp_10);
        width = MyUtils.getScreenWidth(this) - dp8 - dp10 * 2;
        layoutParams = new LinearLayout.LayoutParams(width / 2, width / 4);
        img1.setLayoutParams(layoutParams);
        img2.setLayoutParams(layoutParams);
        img3.setLayoutParams(layoutParams);
        img4.setLayoutParams(layoutParams);
        Glide.with(this).load("").placeholder(R.drawable.replace2).into(img1);
        Glide.with(this).load("").placeholder(R.drawable.replace2).into(img2);
        Glide.with(this).load("").placeholder(R.drawable.replace2).into(img3);
        Glide.with(this).load("").placeholder(R.drawable.replace2).into(img4);

        initRecycler();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new ScrollLinearLayoutManager(this));
//        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//                .color(ContextCompat.getColor(this, R.color.transParent))
//                .size((int)
//                        getResources().getDimension(R.dimen.recycler_divider))
//                .build());
//        recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
//                .color(ContextCompat.getColor(this, R.color.transParent))
//                .size((int) getResources().getDimension(R.dimen.recycler_divider))
//                .build());

        adapter = new SpecialtyListCommodityAdapter(this, BaseListAdapter.NEITHER, ECOLOGICAL, position -> {

        });
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        adapter.setItems(getList());
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

    @Override
    public void onItemClick(int position, long id, View view) {

    }
}
