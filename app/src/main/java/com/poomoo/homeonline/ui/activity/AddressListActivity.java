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
import android.widget.Button;
import android.widget.LinearLayout;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.AddressListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.database.AreaInfo;
import com.poomoo.homeonline.database.CityInfo;
import com.poomoo.homeonline.database.DataBaseHelper;
import com.poomoo.homeonline.database.ProvinceInfo;
import com.poomoo.homeonline.presenters.AddressListPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.base.BaseListDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RReceiptBO;
import com.poomoo.model.response.RZoneBO;
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
public class AddressListActivity extends BaseListDaggerActivity<RReceiptBO, AddressListPresenter> implements BaseListAdapter.OnItemClickListener {
    @Bind(R.id.llayout_bottom)
    LinearLayout bottomLayout;

    private static final int NEW = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;

    private AddressListAdapter adapter;
    private List<RReceiptBO> rReceiptBOs;
    private int deletePosition = -1;//删除的地址item的下标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    @Override
    protected BaseListAdapter onSetupAdapter() {
        adapter = new AddressListAdapter(this, BaseListAdapter.NEITHER);
        return adapter;
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
        adapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setEnabled(false);
        mPresenter.getAddressList(application.getUserId());
    }

    @Override
    public void onLoadActiveClick() {
        super.onLoadActiveClick();
        mPresenter.getAddressList(application.getUserId());
    }

    public void succeed(List<RReceiptBO> rReceiptBOs) {
        this.rReceiptBOs = rReceiptBOs;
        setEmptyMsg("还没有收货地址，赶紧新建一个吧");
        onLoadResultData(rReceiptBOs);
        bottomLayout.setVisibility(View.VISIBLE);
    }

    public void failed(String msg) {
        if (isNetWorkInvalid(msg))
            onNetworkInvalid(LOAD_MODE_DEFAULT);
        else
            onLoadErrorState(LOAD_MODE_DEFAULT);
        bottomLayout.setVisibility(View.GONE);
    }

//    private List<RReceiptBO> getList() {
//        for (int i = 0; i < 8; i++) {
//            rReceiptBO = new RReceiptBO();
//            rReceiptBO.name = "测试收货人" + (i + 1);
//            rReceiptBO.tel = "1860087814" + i;
//            rReceiptBO.address = "贵州省 贵阳市 观山湖区 世纪城龙禧苑" + (i + 1) + "栋";
//            rReceiptBOs.add(rReceiptBO);
//        }
//        return rReceiptBOs;
//    }

    @Override
    public void onItemClick(int position, long id, View view) {
        deletePosition = position;
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_value), "old");
        bundle.putSerializable(getString(R.string.intent_receiptBO), rReceiptBOs.get(position));
        openActivityForResult(AddressInfoActivity.class, bundle, UPDATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NEW) {
            mAdapter.clear();
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.getAddressList(application.getUserId());
        }

        if (requestCode == UPDATE && resultCode == UPDATE) {
            mAdapter.clear();
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.getAddressList(application.getUserId());
        }

        if (requestCode == UPDATE && resultCode == DELETE) {
            adapter.removeItem(deletePosition);
        }
    }

    public void addAddress(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_value), "new");
        openActivityForResult(AddressInfoActivity.class, bundle, NEW);
    }
}
