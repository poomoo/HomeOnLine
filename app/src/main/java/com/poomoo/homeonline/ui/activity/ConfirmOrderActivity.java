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
import android.app.AlertDialog;
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
import com.google.gson.Gson;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ConfirmOrderAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.pay.OrderInfoUtil2_0;
import com.poomoo.homeonline.pay.PayResult;
import com.poomoo.homeonline.pay.SignUtils;
import com.poomoo.homeonline.presenters.ConfirmOrderPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.PayBO;
import com.poomoo.model.request.QOrderBO;
import com.poomoo.model.response.RCartCommodityBO;
import com.poomoo.model.response.ROrderBO;
import com.poomoo.model.response.RReceiptBO;
import com.poomoo.model.response.RTransferPriceBO;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
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

    private QOrderBO qOrderBO = new QOrderBO("");
    private ConfirmOrderAdapter confirmOrderAdapter;
    private List<RCartCommodityBO> rCartCommodityBOs;
    private double totalPrice;
    private int receiptId = -1;
    private boolean isFreePostage;//是否包邮

    private static final String APPID = "2015122401034999";
    // 商户PID
    public static final String PARTNER = "2088121268159874";
    // 商户收款账号
    public static final String SELLER = "jyzx66@163.com";
//    // 商户私钥，pkcs8格式
//    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMnUCfiC80AiOV8u"
//            + "OlgETQk+z0iz4YAN7nsMkAlC2hvWIa5l6GXhoHLCcawxuK5vd9RpCO9q728h9SLH5HHpUfOR1EV02RhmS82YUOCE3"
//            + "N+NjagIy5XQVI+nmkECTVhi5STcWNqLVJpZtWPxBAeK+F6NJ/gNfFvnrl34FGq4WMFzAgMBAAECgYAHp+lw6da8Wg"
//            + "//EkvYRuF9Nkq6oUguiVjAhit4jnajk63XZbo5EyP49nNFauVsiIHtJsbV/iJy/sDDvv1lbMpU97MFim8q6J93ueJ"
//            + "zHi+PB67eX/GPoFT4YPIlUmrhdCHsSBxP9RrWGJjtotLc+qNlO5268yd2JSJL/MP0tx5H0QJBAOx1DZ9sGqaQ32Ix"
//            + "YMawZ+J8JEbWfFEfp7s59CghPZYtgTKghhgbiQWkf2zDfvPX8gXG6GNk76mNyeCIcDBW9YcCQQDagk703Rj009Ce7"
//            + "2icU+Ki1zlTKUAFbUOUysAgyx0qsj3rJut5Ud1Hzytj3bI+hzU6qlKCGBzjqxHR8L5IQ8+1AkEAm6CW7Lx79fEX9U"
//            + "rGhT8JwLkwLydv/vy0qreECP2HyVX92NJqL2fAEWSpMW6iGd+hPUgjH3gJfTEDE7L5E/fH9QJAU2SwJZvMToQYdtA"
//            + "AKMmLEYL6idaQbIHK7RqEQP/D3euw3fI4pFCuFx3l/XM698o6cm1Wl/gD7o/3eRxcQtA+kQJAVRBjyjQKsn5UYZDC"
//            + "i46TlqKK4pB1QS1rTSL+68xX1BF1lXLsujlNRrsgrjQnq3+LO7NTVvYhw+rKBvLH8cJ0Lw==";

    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMY9DlEc+112Z+sW\n" +
            "M5ZV0avIXgWlsgLPzv0uACKkQcOyLtZKZmLOgeqhBYZgfi/m+vjgOrQmHN8bb7ss\n" +
            "ADFL2hnierAjS3O+reNak0ahp5AwQgg/uVMtUNQOE7Ajpjxf95rS0ho+1HwQ1yhv\n" +
            "YlEtI3zfsPwdWGpG2CzElGWIUfbFAgMBAAECgYBEOy2bU4tdDu0TqC+XVfB13OAn\n" +
            "t3E+sIIA+H1JbxnHnqOqVC9LYOKsfEGnj7y/BhRp5tutt9SGIi5h0PI8BWyKkrnE\n" +
            "J8LFendO2DyL7HDu1fDebDi5iFFOhVfQh0Mg8ELNvHXBxZZehGJEyOPzIG7ShoDV\n" +
            "176rqEenaUhLSQNWoQJBAPLsProqxB4sC+UoRfm18PJF/hXd6/RLlBwCWnVc9637\n" +
            "J5PfMcHCAQLNIGPXVgnN9yCjItT/EdUGIjOBY6IxgJkCQQDQ6QJ04teszPUSzUWf\n" +
            "y/8zF8LhFd1mUPjqfn24JBv+RBAEszb8YkwVgl/lZt5DAoE5x0Pmb2RffP7Dsbp8\n" +
            "xEcNAkEA2KZr9zsG9+XOogy+A8wJXPmhdz0aICF7sVND++HzH16cWJw5UxXEMwxg\n" +
            "s8qr+EiR+7Ci8xWdgMBcplTiWBgQGQJAZBLajGeF7U8ZLvKKIaw1CaKeRfiVYEf1\n" +
            "lU8WpjkV981e5wF6m5fy3nLfwSJv4iW3BZiA3EgMCvnziIcA0HetmQJAJBw3LxI1\n" +
            "/GwDax0IQaWbO+ZPURzxq0cMHPhKTG5xRi48popYrVmtL/h+SKjkOwmX8q35Zqn3\n" +
            "TXBumzuCvWaWZw==";

    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    private static final int ADDRESS = 2;

    private static final int DEFAULT = 3;//获取默认地址失败
    private static final int TRANSFER = 4;//获取运费失败
    private static final int SUBMIT = 5;//提交订单失败

    private int failed = TRANSFER;
    private String orderId;

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

        rCartCommodityBOs = (List<RCartCommodityBO>) getIntent().getSerializableExtra(getString(R.string.intent_commodityList));
        totalPrice = getIntent().getDoubleExtra(getString(R.string.intent_totalPrice), 0.00);
        isFreePostage = getIntent().getBooleanExtra(getString(R.string.intent_isFreePostage), false);
        LogUtils.d(TAG, "isFreePostage:" + isFreePostage);

        recyclerView.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        confirmOrderAdapter = new ConfirmOrderAdapter(this, BaseListAdapter.NEITHER);
        recyclerView.setAdapter(confirmOrderAdapter);
        confirmOrderAdapter.addItems(rCartCommodityBOs);

        commodityPriceTxt.setText("￥" + totalPrice);

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
                if (temp.length() > 0)
                    zfgBtn.setEnabled(true);
            }
        });

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
                totalPriceTxt.setText("￥" + totalPrice);
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
        } else
            MyUtils.showToast(getApplicationContext(), msg);
    }

    public void getTransferPriceSucceed(RTransferPriceBO rTransferPriceBO) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        transferPriceTxt.setText("￥" + rTransferPriceBO.conutFreight);
        totalPrice += rTransferPriceBO.conutFreight;
        totalPriceTxt.setText("￥" + totalPrice);
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

    public void signed(String rsa_private) {
//        RSA_PRIVATE = rsa_private;
    }

    public void subSucceed(ROrderBO rOrderBO) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        orderId = rOrderBO.orderId;
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, "0.01", rCartCommodityBOs.get(0).commodityName, getString(R.string.app_name), orderId);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        LogUtils.d(TAG, "orderParam:" + orderParam);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;
        LogUtils.d(TAG, "orderInfo:" + orderInfo);

        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(ConfirmOrderActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, false);

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
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        qOrderBO.order.payWay = 0;
        qOrderBO.order.orderFrom = 2;
        mPresenter.putOrder(qOrderBO);
    }

    private Bundle bundle = new Bundle();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    LogUtils.d(TAG, "payResult:" + payResult);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    resultStatus = "9000";
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        MyUtils.showToast(getApplicationContext(), "支付成功");
                        bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_DELIVER);
                        JSONObject jsonObject = null;
                        String jsonData = "";
                        try {
                            jsonObject = new JSONObject(resultInfo);
                            jsonData = jsonObject.getString("alipay_trade_app_pay_response");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        PayBO payBO = new Gson().fromJson(resultInfo, PayBO.class);

                        if (SignUtils.isLegal(payBO.sign, RSA_PUBLIC, jsonData)) {
                            if (!payBO.alipay_trade_app_pay_response.out_trade_no.equals(orderId)
                                    || !payBO.alipay_trade_app_pay_response.total_amount.equals("0.01")
                                    || !payBO.alipay_trade_app_pay_response.seller_id.equals(PARTNER)
                                    || !payBO.alipay_trade_app_pay_response.app_id.equals(APPID)) {
                                LogUtils.d(TAG, "比较失败");
                                MyUtils.showToast(getApplicationContext(), "支付失败");
                                bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_PAY);
                                openActivity(MyOrdersActivity.class, bundle);
                            } else {
                                openActivity(MyOrdersActivity.class, bundle);
                                if (CommodityInfoActivity.inStance != null)
                                    CommodityInfoActivity.inStance.finish();
                                finish();
                            }
                        } else {
                            LogUtils.d(TAG, "校验失败");
                            MyUtils.showToast(getApplicationContext(), "支付失败");
                            bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_PAY);
                            openActivity(MyOrdersActivity.class, bundle);
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        MyUtils.showToast(getApplicationContext(), "支付失败");
                        bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_PAY);
                        openActivity(MyOrdersActivity.class, bundle);
                    }
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
        }
    }
}
