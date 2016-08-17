/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public abstract class BaseDaggerFragment<P extends BasePresenter> extends Fragment {
    @Inject
    protected P mPresenter;

    public String TAG = getClass().getSimpleName();
    public MyApplication application;
    private RelativeLayout mProgressBar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        application = (MyApplication) getActivity().getApplication();
        setupFragmentComponent(new FragmentModule(this));
        mPresenter.onCreate();
        mPresenter.attachView(this);
    }

    protected void getProgressBar() {
        mProgressBar = (RelativeLayout) getActivity().findViewById(R.id.rlayout_progressBar);
    }

    protected void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    /**
     * 依赖注入的入口
     */
    protected abstract void setupFragmentComponent(FragmentModule fragmentModule);

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
}
