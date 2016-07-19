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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.AddressListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.model.response.RReceiptBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 AddressListActivity
 * 描述 地址管理列表
 * 作者 李苜菲
 * 日期 2016/7/19 11:21
 */
public class AddressListActivity extends BaseActivity implements BaseListAdapter.OnItemClickListener {
    private static final int NEW = 1;
    private static final int DELETE = 2;
    @Bind(R.id.recycler_address)
    RecyclerView recyclerView;

    private AddressListAdapter adapter;
    private ArrayList<RReceiptBO> rReceiptBOs = new ArrayList<>();
    private RReceiptBO rReceiptBO;
    private int deletePosition = -1;//删除的地址item的下标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_address_list;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_address_list;
    }

    private void init() {
        setBack();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.divide))
                .size((int) getResources().getDimension(R.dimen.divider_height2))
                .build());

        adapter = new AddressListAdapter(this, BaseListAdapter.NEITHER);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        adapter.setItems(getList());
    }

    private List<RReceiptBO> getList() {
        for (int i = 0; i < 8; i++) {
            rReceiptBO = new RReceiptBO();
            rReceiptBO.name = "测试收货人" + (i + 1);
            rReceiptBO.tel = "1860087814" + i;
            rReceiptBO.address = "贵州省 贵阳市 观山湖区 世纪城龙禧苑" + (i + 1) + "栋";
            rReceiptBOs.add(rReceiptBO);
        }
        return rReceiptBOs;
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        deletePosition = position;
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_value), "old");
        bundle.putString(getString(R.string.intent_receiptName), adapter.getItem(position).name);
        bundle.putString(getString(R.string.intent_receiptTel), adapter.getItem(position).tel);
        String address[] = adapter.getItem(position).address.split(" ");
        bundle.putString(getString(R.string.intent_receiptAddress), address.length == 4 ? address[3] : "");
        bundle.putInt(getString(R.string.intent_provinceId), 1);
        bundle.putInt(getString(R.string.intent_cityId), 1);
        bundle.putInt(getString(R.string.intent_areaId), 7);
        openActivityForResult(AddressInfoActivity.class, bundle, DELETE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == DELETE) {
            adapter.removeItem(deletePosition);
        }

        if (resultCode == NEW) {
            rReceiptBO = (RReceiptBO) data.getSerializableExtra(getString(R.string.intent_value));
            adapter.addItem(0, rReceiptBO);
        }
    }

    public void addAddress(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_value), "new");
        openActivityForResult(AddressInfoActivity.class, bundle, NEW);
    }
}
