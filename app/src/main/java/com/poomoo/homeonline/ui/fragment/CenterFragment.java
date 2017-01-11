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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.database.AreaInfo;
import com.poomoo.homeonline.database.CityInfo;
import com.poomoo.homeonline.database.DataBaseHelper;
import com.poomoo.homeonline.database.ProvinceInfo;
import com.poomoo.homeonline.presenters.CenterFragmentPresenter;
import com.poomoo.homeonline.reject.components.DaggerFragmentComponent;
import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.activity.AddressListActivity;
import com.poomoo.homeonline.ui.activity.CollectActivity;
import com.poomoo.homeonline.ui.activity.FeedBackActivity;
import com.poomoo.homeonline.ui.activity.InviteActivity;
import com.poomoo.homeonline.ui.activity.LogInActivity;
import com.poomoo.homeonline.ui.activity.MainNewActivity;
import com.poomoo.homeonline.ui.activity.MyInfoActivity;
import com.poomoo.homeonline.ui.activity.MyOrdersActivity;
import com.poomoo.homeonline.ui.activity.SafeActivity;
import com.poomoo.homeonline.ui.activity.ScanHistoryActivity;
import com.poomoo.homeonline.ui.activity.TicketActivity;
import com.poomoo.homeonline.ui.activity.WholeSaleMainActivity;
import com.poomoo.homeonline.ui.activity.WholeSaleUserPowerActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerFragment;
import com.poomoo.model.response.ROrderBO;
import com.poomoo.model.response.RZoneBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类名 CenterFragment
 * 描述 个人中心
 * 作者 李苜菲
 * 日期 2016/7/19 11:20
 */
public class CenterFragment extends BaseDaggerFragment<CenterFragmentPresenter> {
    @Bind(R.id.txt_center_nickName)
    TextView nameTxt;
    @Bind(R.id.llayout_logout)
    LinearLayout logoutLayout;

    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    protected void setupFragmentComponent(FragmentModule fragmentModule) {
        DaggerFragmentComponent.builder()
                .fragmentModule(fragmentModule)
                .build()
                .inject(this);
    }

    private void init() {
//        mPresenter.getZoneInfo();
        if (!MyUtils.isLogin(getActivity()))
            logoutLayout.setVisibility(View.GONE);
        else {
            logoutLayout.setVisibility(View.VISIBLE);
            nameTxt.setText(application.getNickName());
        }
    }

    @OnClick(R.id.rlayout_myOrder)
    void toMyOrder() {
        if (!MyUtils.isLogin(getActivity())) {
            Intent login = new Intent(getActivity(), LogInActivity.class);
            getActivity().startActivity(login);
            MyUtils.showToast(getActivity(), "请先登录");
            return;
        }
        openActivity(MyOrdersActivity.class);
    }

    @OnClick({R.id.llayout_pay, R.id.llayout_deliver, R.id.llayout_recepit, R.id.llayout_evaluate})
    void toMyOrder(View view) {
        if (!MyUtils.isLogin(getActivity())) {
            Intent login = new Intent(getActivity(), LogInActivity.class);
            getActivity().startActivity(login);
            MyUtils.showToast(getActivity(), "请先登录");
            return;
        }
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
        }
        openActivity(MyOrdersActivity.class, bundle);
    }

    @OnClick({R.id.txt_collection, R.id.txt_history, R.id.rlayout_my_info, R.id.rlayout_ticket, R.id.rlayout_my_address, R.id.rlayout_wholeSale, R.id.rlayout_invite, R.id.rlayout_tel, R.id.rlayout_feed_back, R.id.rlayout_safe_center, R.id.llayout_after_sale})
    void other(View view) {
        if (!MyUtils.isLogin(getActivity())) {
            Intent login = new Intent(getActivity(), LogInActivity.class);
            getActivity().startActivity(login);
            MyUtils.showToast(getActivity(), "请先登录");
            return;
        }
        switch (view.getId()) {
            case R.id.txt_collection:
                openActivity(CollectActivity.class);
                break;
            case R.id.txt_history:
                openActivity(ScanHistoryActivity.class);
                break;
            case R.id.llayout_after_sale:
                break;
            case R.id.rlayout_my_info:
                openActivity(MyInfoActivity.class);
                break;
            case R.id.rlayout_ticket:
                openActivity(TicketActivity.class);
                break;
            case R.id.rlayout_my_address:
                bundle = new Bundle();
                bundle.putBoolean(getString(R.string.intent_isEdit), true);
                openActivity(AddressListActivity.class, bundle);
                break;
            case R.id.rlayout_wholeSale:
//                openActivity(WholeSaleUserPowerActivity.class);
                openActivity(WholeSaleMainActivity.class);
                break;
            case R.id.rlayout_invite:
                openActivity(InviteActivity.class);
                break;
            case R.id.rlayout_tel:
                dial();
                break;
            case R.id.rlayout_feed_back:
                openActivity(FeedBackActivity.class);
                break;
            case R.id.rlayout_safe_center:
                openActivity(SafeActivity.class);
                break;
        }
    }

    @OnClick(R.id.txt_center_nickName)
    void logIn() {
        Intent login = new Intent(getActivity(), LogInActivity.class);
        getActivity().startActivity(login);
    }

    @OnClick(R.id.rlayout_logOut)
    void logOut() {
        createDialog("确定退出登录?", (dialog, which) -> {
            SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_isLogin), false);
            SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_receiptAddress), "");
            SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_receiptTel), "");
            SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_receiptId), -1);
            SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_userId), -1);
            SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_phoneNum), "");
            SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_nickName), "");
            ((MainNewActivity) getActivity()).jump(0);
            ((MainNewActivity) getActivity()).setInfoNum(3, 0, false);
            application.setCartNum(0);
            application.setUserId(null);
            nameTxt.setText("登录/注册");
            logoutLayout.setVisibility(View.GONE);
        }).show();
    }


    /**
     * 打电话
     */
    private void dial() {
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


    private List<ProvinceInfo> provinceInfo;
    private List<CityInfo> cityInfo;
    private List<AreaInfo> areaInfo;

    public void getZoneInfo(List<RZoneBO> rZoneBOs) {
        LogUtils.d(TAG, "getZoneInfo");
        provinceInfo = new ArrayList<>();
        for (RZoneBO rZoneBO : rZoneBOs) {
            ProvinceInfo provinceInfo = new ProvinceInfo(rZoneBO.provinceId, rZoneBO.provinceName);
            int len = rZoneBO.cities.size();
            cityInfo = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                CityInfo cityInfo = new CityInfo(rZoneBO.cities.get(i).cityId, rZoneBO.cities.get(i).cityName, rZoneBO.cities.get(i).isHot, provinceInfo.getProvinceId());
                int len2 = rZoneBO.cities.get(i).areas.size();
                areaInfo = new ArrayList<>();
                for (int j = 0; j < len2; j++)
                    areaInfo.add(new AreaInfo(rZoneBO.cities.get(i).areas.get(j).areaId, rZoneBO.cities.get(i).areas.get(j).areaName, cityInfo.getCityId()));
                DataBaseHelper.saveArea(areaInfo);
                this.cityInfo.add(cityInfo);
            }
            DataBaseHelper.saveCity(cityInfo);
            this.provinceInfo.add(provinceInfo);
        }
        DataBaseHelper.saveProvince(provinceInfo);
        LogUtils.d(TAG, "插表完成");
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden)
//            nameTxt.setText(application.getNickName());
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyUtils.isLogin(getActivity())) {
            nameTxt.setText(application.getNickName());
            logoutLayout.setVisibility(View.VISIBLE);
        } else {
            nameTxt.setText("登录/注册");
            logoutLayout.setVisibility(View.GONE);
        }
    }
}
