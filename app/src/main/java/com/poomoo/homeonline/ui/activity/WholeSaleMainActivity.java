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
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ListCommodityAdapter;
import com.poomoo.homeonline.adapter.MainGridAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollGridLayoutManager;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.homeonline.ui.custom.NoScrollRecyclerView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RListCommodityBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 WholeSaleMainActivity
 * 描述 集采商城首页
 * 作者 李苜菲
 * 日期 2017/1/5 10:11
 */
public class WholeSaleMainActivity extends BaseActivity implements BaseListAdapter.OnItemClickListener, AdapterView.OnItemClickListener {
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.grid_menu)
    NoScrollGridView menuGrid;
    @Bind(R.id.recycler_wholesale)
    NoScrollRecyclerView recycler;

    private MainGridAdapter gridAdapter;
    private ListCommodityAdapter listCommodityAdapter;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_wholesale_main;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_toWhole_sale_center;
    }

    private void init() {
        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.rightImg.setImageResource(R.drawable.ic_info_cart);
        headerViewHolder.rightImg.setVisibility(View.VISIBLE);
        headerViewHolder.backImg.setOnClickListener(v -> {
            finish();
            getActivityOutToRight();
        });

        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为2:1
        gridAdapter = new MainGridAdapter(this, true);
        menuGrid.setAdapter(gridAdapter);
        menuGrid.setOnItemClickListener(this);

        recycler.setLayoutManager(new ScrollGridLayoutManager(this, 2));
        recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        recycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());

        listCommodityAdapter = new ListCommodityAdapter(this, BaseListAdapter.NEITHER, ListCommodityAdapter.COMMON);
        recycler.setAdapter(listCommodityAdapter);
        listCommodityAdapter.setOnItemClickListener(this);

        String[] temp = {"", ""};
        slideShowView.setPics(temp, position -> {

        });
        listCommodityAdapter.setItems(getList());
    }

    private List<RListCommodityBO> getList() {
        List<RListCommodityBO> rListCommodityBOs = new ArrayList<>();
        RListCommodityBO rListCommodityBO;
        for (int i = 0; i < 10; i++) {
            rListCommodityBO = new RListCommodityBO();
            rListCommodityBO.commodityName = "集采商品" + (i + 1);
            rListCommodityBO.platformPrice = 251 + i;
            rListCommodityBOs.add(rListCommodityBO);
        }
        return rListCommodityBOs;
    }

    @Override
    public void onItemClick(int position, long id, View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        bundle = new Bundle();
        bundle.putString(getString(R.string.intent_title), MainGridAdapter.wholeSaleName[position]);
        bundle.putInt(getString(R.string.intent_categoryId), 1757);
        openActivity(WholeSaleClassifyInfoActivity.class, bundle);
    }
}
