/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.core.ActionCallbackListener;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.UserBO;

/**
 * 类名 LogInActivity
 * 描述 登录
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class LogInActivity extends BaseActivity implements View.OnClickListener {
    private EditText nameEdt;
    private EditText passWordEdt;
    private TextView registerTxt;
    private TextView forgetPassWordTxt;

    private String name = "";
    private String passWord = "";

    public static LogInActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
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
        nameEdt = (EditText) findViewById(R.id.edt_userName);
        passWordEdt = (EditText) findViewById(R.id.edt_userPassWord);
        registerTxt = (TextView) findViewById(R.id.txt_register);
        forgetPassWordTxt = (TextView) findViewById(R.id.txt_forgetPassWord);

        registerTxt.setOnClickListener(this);
        forgetPassWordTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.txt_register:
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.intent_parent), getString(R.string.intent_register));
                openActivity(GetCodeActivity.class, bundle);
                break;
            case R.id.txt_forgetPassWord:
                bundle = new Bundle();
                bundle.putString(getString(R.string.intent_parent), getString(R.string.intent_find));
                openActivity(GetCodeActivity.class, bundle);
                break;
        }
    }

    public void toLogin(View view) {
        name = nameEdt.getText().toString().trim();
        passWord = passWordEdt.getText().toString().trim();
        showProgressDialog(getString(R.string.dialog));
        this.appAction.logIn(name, passWord, new ActionCallbackListener() {
            @Override
            public void onSuccess(ResponseBO data) {
                closeProgressDialog();
                UserBO userBO;
                userBO = (UserBO) data.getObj();
                LogUtils.i(TAG, "id:" + userBO.getId());
                finish();
                SPUtils.put(getApplicationContext(), getString(R.string.sp_isLogin), true);
                SPUtils.put(getApplicationContext(), getString(R.string.sp_id), userBO.getId() + "");
                openActivity(MainActivity.class);
            }

            @Override
            public void onFailure(boolean flag, String message) {
                closeProgressDialog();
                MyUtils.showToast(getApplicationContext(), message);
            }
        });
    }
}
