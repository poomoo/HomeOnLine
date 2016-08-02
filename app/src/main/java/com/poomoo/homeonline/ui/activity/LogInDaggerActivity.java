/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.model.RUserBO;
import com.poomoo.homeonline.presenters.LoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类名 LogInActivity
 * 描述 登录
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class LogInDaggerActivity extends BaseDaggerActivity<LoginPresenter> {
    @Bind(R.id.edt_userName)
    EditText nameEdt;
    @Bind(R.id.edt_userPassWord)
    EditText passWordEdt;

    private String name = "";
    private String passWord = "";

    public static LogInDaggerActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_login;
    }

    private void initView() {
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    @OnClick({R.id.txt_register, R.id.txt_forgetPassWord})
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.txt_register:
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.intent_parent), getString(R.string.intent_register));
                openActivity(GetCodeDaggerActivity.class, bundle);
                break;
            case R.id.txt_forgetPassWord:
                bundle = new Bundle();
                bundle.putString(getString(R.string.intent_parent), getString(R.string.intent_find));
                openActivity(GetCodeDaggerActivity.class, bundle);
                break;
        }
    }

    public void toLogin(View view) {
        name = nameEdt.getText().toString().trim();
        passWord = passWordEdt.getText().toString().trim();
        showProgressDialog(getString(R.string.dialog));
        mPresenter.login(name, passWord);
    }

    public void loginSucceed(RUserBO rUserBO) {
        closeProgressDialog();
        finish();
        SPUtils.put(getApplicationContext(), getString(R.string.sp_isLogin), true);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_id), rUserBO.id + "");
        openActivity(MainActivity.class);
    }

    public void loginFailed(String msg) {
        closeProgressDialog();
        MyUtils.showToast(getApplicationContext(), msg);
    }
}
