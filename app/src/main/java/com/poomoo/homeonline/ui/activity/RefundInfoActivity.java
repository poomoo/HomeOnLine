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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.poomoo.commlib.MyConfig;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.RefundInfoPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RReFundInfoBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 RefundInfoActivity
 * 描述 退款详情
 * 作者 李苜菲
 * 日期 2016/8/24 17:28
 */
public class ReFundInfoActivity extends BaseDaggerActivity<RefundInfoPresenter> implements ErrorLayout.OnActiveClickListener {
    @Bind(R.id.llayout_refund_info)
    LinearLayout infoLayout;
    @Bind(R.id.llayout_refund_wait)
    LinearLayout waitLayout;
    @Bind(R.id.llayout_refund_successful)
    LinearLayout successfulLayout;
    @Bind(R.id.txt_refund_top_amount)
    TextView topAmountTxt;
    @Bind(R.id.txt_refund_successful_date)
    TextView successfulDateTxt;
    @Bind(R.id.llayout_refund_failed)
    LinearLayout failedLayout;
    @Bind(R.id.txt_refund_failed_reason)
    TextView failedReasonTxt;
    @Bind(R.id.txt_refund_failed_date)
    TextView failedDateTxt;
    @Bind(R.id.img_arrow)
    ImageView arrowImg;
    @Bind(R.id.scroll_refund)
    ScrollView scrollView;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;
    @Bind(R.id.txt_refund_type)
    TextView typeTxt;
    @Bind(R.id.txt_refund_amount)
    TextView amountTxt;
    @Bind(R.id.txt_refund_reason)
    TextView reasonTxt;
    @Bind(R.id.txt_refund_des)
    TextView desTxt;
    @Bind(R.id.txt_refund_id)
    TextView idTxt;
    @Bind(R.id.txt_refund_apply_date)
    TextView applyDateTxt;
    @Bind(R.id.txt_refund_shop)
    TextView shopTxt;
    @Bind(R.id.txt_refund_commodity)
    TextView commodityTxt;
    @Bind(R.id.txt_refund_price)
    TextView priceTxt;
    @Bind(R.id.txt_refund_count)
    TextView countTxt;
    @Bind(R.id.txt_refund_transfer)
    TextView transferTxt;
    @Bind(R.id.txt_refund_total_price)
    TextView totalPriceTxt;
    @Bind(R.id.txt_refund_orderId)
    TextView orderIdTxt;
    @Bind(R.id.txt_refund_deal_date)
    TextView dealDateTxt;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_refund_info;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_refundInfo;
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
        id = getIntent().getStringExtra(getString(R.string.intent_value));

        errorLayout.setOnActiveClickListener(this);

        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getReFundInfo(id);
    }

    /**
     * @param view
     */
    public void alterRefund(View view) {

    }

    /**
     * 展示详情
     *
     * @param view
     */
    public void toShow(View view) {
        if (scrollView.getVisibility() == View.VISIBLE) {
            scrollView.setVisibility(View.GONE);
            arrowImg.setImageResource(R.drawable.arrow_up);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            arrowImg.setImageResource(R.drawable.arrow_down);

        }
    }


    public void successful(RReFundInfoBO rReFundInfoBO) {
        errorLayout.setState(ErrorLayout.HIDE, "");
        switch (rReFundInfoBO.returnNote.returnStatus) {
            //处理中
            case MyConfig.GOODS_RETURN_NOTE_STATUS_SUBMIT:
            case MyConfig.GOODS_RETURN_NOTE_STATUS_CHECK:
                waitLayout.setVisibility(View.VISIBLE);
                successfulLayout.setVisibility(View.GONE);
                failedLayout.setVisibility(View.GONE);
                break;
            case MyConfig.GOODS_RETURN_NOTE_STATUS_CHECK_YES:
                waitLayout.setVisibility(View.GONE);
                successfulLayout.setVisibility(View.VISIBLE);
                failedLayout.setVisibility(View.GONE);
                topAmountTxt.setText(rReFundInfoBO.returnNote.returnMoney + "");
                successfulDateTxt.setText(rReFundInfoBO.returnNote.payCashTime);
                break;
            case MyConfig.GOODS_RETURN_NOTE_STATUS_CHECK_NO:
                waitLayout.setVisibility(View.GONE);
                successfulLayout.setVisibility(View.GONE);
                failedLayout.setVisibility(View.VISIBLE);
                failedReasonTxt.setText(rReFundInfoBO.returnNote.returnReason);
                failedDateTxt.setText(rReFundInfoBO.returnNote.checkTime);
                break;
        }
        typeTxt.setText(rReFundInfoBO.returnNote.returnType == 1 ? "退货且退款" : "仅退款");
        amountTxt.setText(rReFundInfoBO.returnNote.returnMoney + "");
        reasonTxt.setText(MyConfig.reason[rReFundInfoBO.returnNote.returnReason]);
        desTxt.setText(rReFundInfoBO.returnNote.returnExplain);
        idTxt.setText(rReFundInfoBO.returnNote.id);
        applyDateTxt.setText(rReFundInfoBO.returnNote.submitTime);
        shopTxt.setText(rReFundInfoBO.shopName);
        commodityTxt.setText(rReFundInfoBO.commodityName);
        priceTxt.setText(rReFundInfoBO.unitPrice + "");
        countTxt.setText(rReFundInfoBO.returnNote.returnNum + "");
        transferTxt.setText(rReFundInfoBO.deliveryFee + "");
        totalPriceTxt.setText(rReFundInfoBO.totalPrice + "");
        orderIdTxt.setText(rReFundInfoBO.orderId);
        dealDateTxt.setText(rReFundInfoBO.createTime);

        infoLayout.setVisibility(View.VISIBLE);
    }

    public void failed(String msg) {
//        if (isNetWorkInvalid(msg))
//            errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
//        else
        errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }


    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getReFundInfo(id);
    }
}
