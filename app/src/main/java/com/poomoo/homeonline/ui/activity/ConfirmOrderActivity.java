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

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ConfirmOrderAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.pay.PayResult;
import com.poomoo.homeonline.presenters.ConfirmOrderPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.request.QOrderBO;
import com.poomoo.model.response.RCartCommodityBO;
import com.poomoo.model.response.ROrderBO;
import com.poomoo.model.response.RReceiptBO;
import com.poomoo.model.response.RTransferPriceBO;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ConfirmOrderActivity
 * 描述 确认订单
 * 作者 李苜菲
 * 日期 2016/8/17 15:42
 */
public class ConfirmOrderActivity extends BaseDaggerActivity<ConfirmOrderPresenter> implements ErrorLayout.OnActiveClickListener {
    @Bind(R.id.llayout_selectAddress)
    LinearLayout selectAddressLayout;
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
    @Bind(R.id.error_frame)
    ErrorLayout mErrorLayout;
    @Bind(R.id.llayout_content)
    LinearLayout contentLayout;

    private QOrderBO qOrderBO = new QOrderBO("");
    private ConfirmOrderAdapter confirmOrderAdapter;
    private List<RCartCommodityBO> rCartCommodityBOs;
    private double totalPrice;
    private int receiptId = -1;
    private boolean isFreePostage;//是否包邮

    private static final int SDK_PAY_FLAG = 1;
    private static final int ADDRESS = 2;
    private static final int DEFAULT = 3;//获取默认地址失败
    private static final int TRANSFER = 4;//获取运费失败
    private static final int SUBMIT = 5;//提交订单失败

    private int failed = TRANSFER;
    private String orderId;
    private String commodityName;
    private boolean isCreateOrder = false;//是否已生成订单
    private int deliveryId;
    private double deliveryFee;

    private Bundle bundle;
    private DecimalFormat df = new DecimalFormat("0.00");

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

        isCreateOrder = getIntent().getBooleanExtra(getString(R.string.intent_value), false);
        if (isCreateOrder) {
            orderId = getIntent().getStringExtra(getString(R.string.intent_orderId));
            commodityName = getIntent().getStringExtra(getString(R.string.intent_commodityName));
            deliveryId = getIntent().getIntExtra(getString(R.string.intent_deliveryId), -1);
            deliveryFee = getIntent().getDoubleExtra(getString(R.string.intent_deliveryFee), 0.00);
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.getAddressById(deliveryId);
            selectAddressLayout.setVisibility(View.GONE);//已生成订单不能修改收货地址
        }

        rCartCommodityBOs = (List<RCartCommodityBO>) getIntent().getSerializableExtra(getString(R.string.intent_commodityList));
        totalPrice = getIntent().getDoubleExtra(getString(R.string.intent_totalPrice), 0.00);
        isFreePostage = getIntent().getBooleanExtra(getString(R.string.intent_isFreePostage), false);
        recyclerView.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        confirmOrderAdapter = new ConfirmOrderAdapter(this, BaseListAdapter.NEITHER);
        recyclerView.setAdapter(confirmOrderAdapter);
        confirmOrderAdapter.addItems(rCartCommodityBOs);

        commodityPriceTxt.setText("￥" + df.format(totalPrice));

        recyclerView.setFocusable(false);//不让scrollview自动滚动

        qOrderBO.order = qOrderBO.new Order(application.getUserId());
        qOrderBO.orderDetailsList = rCartCommodityBOs;
        mErrorLayout.setOnActiveClickListener(this);

        totalPriceTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                LogUtils.d(TAG, "totalPriceTxt:" + temp);
                if (temp.length() > 0)
                    zfgBtn.setEnabled(true);
            }
        });

        if (isCreateOrder) {
            if (isFreePostage) {//包邮
                totalPriceTxt.setText("￥" + df.format(totalPrice));
                transferPriceTxt.setText("包邮");
            } else {
                transferPriceTxt.setText(df.format(totalPrice));
                totalPrice += deliveryFee;
                totalPriceTxt.setText("￥" + df.format(totalPrice));
            }
        } else
            setAddressInfo();
    }

    private void setAddressInfo() {
        //没有收货地址
        if (TextUtils.isEmpty((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptName), ""))
                || TextUtils.isEmpty((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptAddress), ""))
                || TextUtils.isEmpty((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptTel), ""))) {
            infoLayout.setVisibility(View.GONE);
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.getDefaultAddress(application.getUserId());
        } else {
            receiptId = (int) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptId), -1);
            receiptNameTxt.setText((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptName), ""));
            receiptTelTxt.setText((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptTel), ""));
            receiptAddressTxt.setText((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_receiptAddress), ""));
            infoLayout.setVisibility(View.VISIBLE);
            qOrderBO.order.deliveryId = receiptId;
            if (isFreePostage) {//包邮
                totalPriceTxt.setText("￥" + df.format(totalPrice));
                transferPriceTxt.setText("包邮");
            } else {
                mErrorLayout.setState(ErrorLayout.LOADING, "");
                mPresenter.getTransferPrice(application.getUserId(), receiptId, totalPrice);
            }
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
        openActivityForResult(AddressListActivity.class, bundle, ADDRESS);
    }

    public void getDefaultAddressSucceed(RReceiptBO rReceiptBO) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        if (rReceiptBO == null)
            return;
        contentLayout.setVisibility(View.VISIBLE);
        qOrderBO.order.deliveryId = rReceiptBO.id;
        SPUtils.put(getApplicationContext(), getString(R.string.sp_receiptId), rReceiptBO.id);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_receiptName), rReceiptBO.consigneeName);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_receiptTel), rReceiptBO.consigneeTel);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_receiptAddress), rReceiptBO.pca);
        setAddressInfo();
    }

    public void getDefaultAddressFailed(String msg) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        if (isNetWorkInvalid(msg)) {
            failed = DEFAULT;
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
            contentLayout.setVisibility(View.GONE);
        } else
            MyUtils.showToast(getApplicationContext(), msg);
    }

    public void getAddressFailed(String msg) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        if (isNetWorkInvalid(msg)) {
            failed = ADDRESS;
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
            contentLayout.setVisibility(View.GONE);
        } else
            MyUtils.showToast(getApplicationContext(), msg);
    }

    public void getAddressSucceed(RReceiptBO rReceiptBO) {
        contentLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        receiptNameTxt.setText(rReceiptBO.consigneeName);
        receiptTelTxt.setText(rReceiptBO.consigneeTel);
        receiptAddressTxt.setText(rReceiptBO.pca);
        infoLayout.setVisibility(View.VISIBLE);
    }

    public void getTransferPriceSucceed(RTransferPriceBO rTransferPriceBO) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        transferPriceTxt.setText("￥" + df.format(rTransferPriceBO.conutFreight));
        totalPriceTxt.setText("￥" + df.format(totalPrice + rTransferPriceBO.conutFreight));
    }

    public void getTransferPriceFailed(String msg) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        transferPriceTxt.setText("获取运费失败");
        if (isNetWorkInvalid(msg)) {
            failed = TRANSFER;
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        } else
            MyUtils.showToast(getApplicationContext(), msg);
    }

    public void failed(String msg) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
    }

    public void subSucceed(String sign) {
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(ConfirmOrderActivity.this);
            Map<String, String> result = alipay.payV2(sign, false);

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void subFailed(String msg) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        if (isNetWorkInvalid(msg)) {
            failed = SUBMIT;
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        } else
            MyUtils.showToast(getApplicationContext(), msg);
    }

    public void paySuccessful() {
        MyUtils.showToast(getApplicationContext(), "支付成功");
        if (isCreateOrder) {
            finish();
            return;
        }
        bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_DELIVER);
        openActivity(MyOrdersActivity.class, bundle);
        finish();
    }

    public void payFailed() {
        MyUtils.showToast(getApplicationContext(), "支付失败");
        if (isCreateOrder) {
            finish();
            return;
        }
        bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_PAY);
        openActivity(MyOrdersActivity.class, bundle);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS && resultCode == ADDRESS)
            setAddressInfo();
    }

    /**
     * 支付宝支付
     *
     * @param view
     */
    public void payByZFB(View view) {
        for (RCartCommodityBO cartCommodityBOs : rCartCommodityBOs)
            if (cartCommodityBOs.commodityNum == 0) {
                createDialog("购买的商品中存在没有库存的,请重新购买", (dialog, which) -> finish()).show();
                return;
            }
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        if (isCreateOrder)
            mPresenter.sign(orderId, commodityName);
        else {
            qOrderBO.order.payWay = 1;
            qOrderBO.order.orderFrom = 2;
            mPresenter.putOrder(qOrderBO);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    mPresenter.checkSign(payResult.getResultStatus(), payResult.getResult(), payResult.getMemo());
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    public void onLoadActiveClick() {
        switch (failed) {
            case DEFAULT:
                mErrorLayout.setState(ErrorLayout.LOADING, "");
                mPresenter.getDefaultAddress(application.getUserId());
                break;
            case TRANSFER:
                mErrorLayout.setState(ErrorLayout.LOADING, "");
                mPresenter.getTransferPrice(application.getUserId(), receiptId, totalPrice);
                break;
            case SUBMIT:
                mErrorLayout.setState(ErrorLayout.LOADING, "");
                mPresenter.putOrder(qOrderBO);
                break;
            case ADDRESS:
                mErrorLayout.setState(ErrorLayout.LOADING, "");
                mPresenter.getAddressById(deliveryId);
                break;
        }
    }
}
