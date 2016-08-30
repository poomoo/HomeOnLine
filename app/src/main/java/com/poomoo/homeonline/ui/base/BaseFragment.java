/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.application.MyApplication;
import com.poomoo.homeonline.presenters.BasePresenter;
import com.poomoo.homeonline.reject.modules.FragmentModule;

import javax.inject.Inject;


/**
 * @author 李苜菲
 * @ClassName BaseFragment
 * @Description 基类Fragment
 * @date 2016/7/19 11:15
 */
public abstract class BaseFragment extends Fragment {
    public String TAG = getClass().getSimpleName();
    public MyApplication application;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        application = (MyApplication) getActivity().getApplication();
    }

    /**
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        Intent intent = new Intent(getActivity(), pClass);
        getActivity().startActivity(intent);
    }

    /**
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        this.startActivity(intent);
        getActivityInFromRight();
    }

    /**
     * @param pClass
     * @param requestCode
     */
    protected void openActivityForResult(Class<?> pClass, int requestCode) {
        Intent intent = new Intent(getActivity(), pClass);
        startActivityForResult(intent, requestCode);
        getActivityInFromRight();
    }

    /**
     * activity切换-> 向左进(覆盖)
     */
    protected void getActivityInFromRight() {
        getActivity().overridePendingTransition(R.anim.activity_in_from_right,
                R.anim.activity_center);
    }

    /**
     * activity切换-> 向右出(抽出)
     */
    protected void getActivityOutToRight() {
        getActivity().overridePendingTransition(R.anim.activity_center,
                R.anim.activity_out_to_right);
    }

    protected Dialog createDialog(String msg, DialogInterface.OnClickListener onClickListener) {
        Dialog dialog = new AlertDialog
                .Builder(getActivity())
                .setMessage(msg)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", onClickListener)
                .create();
        return dialog;
    }


    protected boolean isNetWorkInvalid(String msg) {
        LogUtils.d(TAG, "isNetWorkInvalid:" + msg + " " + getString(R.string.invalid_network));
        if (msg.equals(getString(R.string.invalid_network)) || msg.equals(getString(R.string.time_out)))
            return true;
        return false;
    }
}
