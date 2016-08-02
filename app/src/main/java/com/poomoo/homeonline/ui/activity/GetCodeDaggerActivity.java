/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.TimeCountDownUtil;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.GetCodePresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;

import butterknife.ButterKnife;

/**
 * 类名 GetCodeActivity
 * 描述 获取验证码
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class GetCodeDaggerActivity extends BaseDaggerActivity<GetCodePresenter> {
    private EditText phoneEdt;
    private EditText codeEdt;
    private Button getCodeBtn;

    private TimeCountDownUtil timeCountDownUtil;

    private String phoneNum = "";
    private String code = "";
    private String PARENT = "";
    private boolean flag;//true-找回密码 false-注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_change_passwrod;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_register;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void initView() {
        setBack();

        phoneEdt = (EditText) findViewById(R.id.edt_tel);
        codeEdt = (EditText) findViewById(R.id.edt_code);
        getCodeBtn = (Button) findViewById(R.id.btn_code);

        PARENT = getIntent().getStringExtra(getString(R.string.intent_parent));
        if (PARENT.equals(getString(R.string.intent_register)))
            flag = false;
        else
            flag = true;
    }

    public void toGetCode(View view) {
        phoneNum = phoneEdt.getText().toString().trim();
        timeCountDownUtil = new TimeCountDownUtil(60 * 1000, 1000, getCodeBtn);
        timeCountDownUtil.start();
        mPresenter.getCode(phoneNum, flag);
    }

    public void toNext(View view) {
        phoneNum = phoneEdt.getText().toString().trim();
        code = codeEdt.getText().toString().trim();
        mPresenter.checkCode(phoneNum, code);
        checkCodeSucceed();
    }

    public void getCodeSucceed() {
        MyUtils.showToast(getApplicationContext(), "验证码发送成功");
    }

    public void getCodeFailed(String msg) {
        MyUtils.showToast(getApplicationContext(), msg);
    }

    public void checkCodeSucceed() {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_parent), PARENT);
        bundle.putString(getString(R.string.intent_value), phoneNum);
        openActivity(ChangePassWordDaggerActivity.class, bundle);
        finish();
    }

    public void checkCodeFailed(String msg) {
        MyUtils.showToast(getApplicationContext(), msg);
    }
}
