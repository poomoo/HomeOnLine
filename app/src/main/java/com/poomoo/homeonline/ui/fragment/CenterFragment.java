/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.activity.AddressInfoActivity;
import com.poomoo.homeonline.ui.activity.AddressListActivity;
import com.poomoo.homeonline.ui.activity.FeedBackActivity;
import com.poomoo.homeonline.ui.activity.MyInfoActivity;
import com.poomoo.homeonline.ui.activity.MyOrdersActivity;
import com.poomoo.homeonline.ui.base.BaseFragment;
import com.poomoo.model.response.ROrderBO;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 李苜菲
 * 日期: 2016/6/23 10:52.
 */
public class CenterFragment extends BaseFragment {
//    @Bind(R.id.scrollView_main)
//    MyScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
    }

    @OnClick(R.id.rlayout_myOrder)
    void toMyOrder() {
        openActivity(MyOrdersActivity.class);
    }

    @OnClick({R.id.llayout_pay, R.id.llayout_deliver, R.id.llayout_recepit, R.id.llayout_evaluate, R.id.llayout_after_safe})
    void toMyOrder(View view) {
//        if (!application.isLogin()) {
//            Dialog dialog = new AlertDialog.Builder(getActivity()).setMessage("请先登录").setPositiveButton("确定", (new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            })).setNegativeButton("取消", (new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            })).create();
//            dialog.show();
//            return;
//        }
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.llayout_pay:
                bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_PAY);
                break;
            case R.id.llayout_deliver:
                bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_DELIVER);
                break;
            case R.id.llayout_recepit:
                bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_RECEIPT);
                break;
            case R.id.llayout_evaluate:
                bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_EVALUATE);
                break;
            case R.id.llayout_after_safe:
                bundle.putInt(getString(R.string.intent_value), ROrderBO.ORDER_AFTER_SALE);
                break;
        }
        openActivity(MyOrdersActivity.class, bundle);
    }

    @OnClick({R.id.rlayout_my_info, R.id.rlayout_my_address, R.id.rlayout_tel, R.id.rlayout_feed_back, R.id.rlayout_safe_center})
    void other(View view) {
        switch (view.getId()) {
            case R.id.rlayout_my_info:
                openActivity(MyInfoActivity.class);
                break;
            case R.id.rlayout_my_address:
                openActivity(AddressListActivity.class);
                break;
            case R.id.rlayout_tel:
                dial();
                break;
            case R.id.rlayout_feed_back:
                openActivity(FeedBackActivity.class);
                break;
            case R.id.rlayout_safe_center:
                break;
        }
    }

    /**
     * 打电话
     */
    private void dial() {
//        if (!application.isLogin()) {
//            MyUtils.showToast(getApplicationContext(), MyConfig.pleaseLogin);
//            return;
//        }
        Dialog dialog = new AlertDialog.Builder(getActivity()).setMessage("拨打电话 4008865355").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4008865355"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).create();

        dialog.show();
    }


}
