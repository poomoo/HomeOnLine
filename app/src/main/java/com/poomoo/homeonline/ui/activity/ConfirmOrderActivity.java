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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ConfirmOrderAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.ConfirmOrderPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.model.response.RCartCommodityBO;
import com.poomoo.model.response.RReceiptBO;
import com.poomoo.model.response.RTransferPriceBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ConfirmOrderActivity
 * 描述 确认订单
 * 作者 李苜菲
 * 日期 2016/8/17 15:42
 */
public class ConfirmOrderActivity extends BaseDaggerActivity<ConfirmOrderPresenter> {
    @Bind(R.id.llayout_address_info)
    LinearLayout infoLayout;
    @Bind(R.id.txt_receiptName)
    TextView receiptNameTxt;
    @Bind(R.id.txt_receiptTel)
    TextView receiptTelTxt;
    @Bind(R.id.txt_receiptAddress)
    TextView receiptAddressTxt;
    @Bind(R.id.recycler_confirm_order)
    RecyclerView recyclerView;
    @Bind(R.id.txt_commodity_price)
    TextView commodityPriceTxt;
    @Bind(R.id.txt_transfer_price)
    TextView transferPriceTxt;
    @Bind(R.id.txt_total_price)
    TextView totalPriceTxt;
    @Bind(R.id.btn_pay_zfb)
    Button zfgBtn;

    private ConfirmOrderAdapter confirmOrderAdapter;
    private List<RCartCommodityBO> rCartCommodityBOs;
    private double totalPrice;
    private int receiptId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_confirm_order;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        setBack();
        getProgressBar();

        rCartCommodityBOs = (List<RCartCommodityBO>) getIntent().getSerializableExtra(getString(R.string.intent_commodityList));
        LogUtils.d(TAG, "rCartCommodityBOs:" + rCartCommodityBOs);
        totalPrice = getIntent().getDoubleExtra(getString(R.string.intent_totalPrice), 0.00);

        recyclerView.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        confirmOrderAdapter = new ConfirmOrderAdapter(this, BaseListAdapter.NEITHER);
        recyclerView.setAdapter(confirmOrderAdapter);
        confirmOrderAdapter.addItems(rCartCommodityBOs);

        commodityPriceTxt.setText("￥" + totalPrice);

        recyclerView.setFocusable(false);//不让scrollview自动滚动
    }

    private void setAddressInfo() {
        //没有收货地址
        if (TextUtils.isEmpty((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptName), ""))
                || TextUtils.isEmpty((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptAddress), ""))
                || TextUtils.isEmpty((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptTel), ""))) {
            infoLayout.setVisibility(View.GONE);
            showProgressBar();
            mPresenter.getDefaultAddress(application.getUserId());
        } else {
            receiptId = (int) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptId), -1);
            receiptNameTxt.setText((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptName), ""));
            receiptTelTxt.setText((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptTel), ""));
            receiptAddressTxt.setText((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptAddress), ""));
            infoLayout.setVisibility(View.VISIBLE);
            showProgressBar();
            mPresenter.getTransferPrice(application.getUserId(), receiptId, totalPrice);
        }
    }

    /**
     * 选择地址
     *
     * @param view
     */
    public void selectAddress(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.intent_isEdit), false);
        openActivity(AddressListActivity.class, bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAddressInfo();
    }

    public void getDefaultAddressSucceed(RReceiptBO rReceiptBO) {
        hideProgressBar();
        SPUtils.put(getApplicationContext(), getString(R.string.sp_receiptId), rReceiptBO.id);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_receiptName), rReceiptBO.consigneeName);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_receiptTel), rReceiptBO.consigneeTel);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_receiptAddress), rReceiptBO.pca);
        setAddressInfo();
    }

    public void getTransferPriceSucceed(RTransferPriceBO rTransferPriceBO) {
        hideProgressBar();
        transferPriceTxt.setText("￥" + rTransferPriceBO.conutFreight);
    }

    public void failed(String msg) {
        hideProgressBar();
    }
}
