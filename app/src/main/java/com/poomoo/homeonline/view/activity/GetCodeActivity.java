/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.poomoo.core.ActionCallbackListener;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.utils.MyUtil;
import com.poomoo.homeonline.utils.TimeCountDownUtil;
import com.poomoo.model.ResponseBO;

/**
 * 获取验证码
 * 作者: 李苜菲
 * 日期: 2016/1/11 14:22.
 */
public class GetCodeActivity extends BaseActivity {
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
        setContentView(R.layout.activity_change_passwrod);

        initView();
    }

    @Override
    protected void initView() {
        initTitleBar();
        phoneEdt = (EditText) findViewById(R.id.edt_tel);
        codeEdt = (EditText) findViewById(R.id.edt_code);
        getCodeBtn = (Button) findViewById(R.id.btn_code);

        PARENT = getIntent().getStringExtra(getString(R.string.intent_parent));
        if (PARENT.equals(getString(R.string.intent_register)))
            flag = false;
        else
            flag = true;
    }

    @Override
    protected void initTitleBar() {
        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.titleTxt.setText(R.string.title_register);
    }

    public void toGetCode(View view) {
        phoneNum = phoneEdt.getText().toString().trim();
        timeCountDownUtil = new TimeCountDownUtil(60 * 1000, 1000, getCodeBtn);
        timeCountDownUtil.start();
        this.appAction.getCode(phoneNum, flag, new ActionCallbackListener() {
            @Override
            public void onSuccess(ResponseBO data) {
                MyUtil.showToast(getApplicationContext(), "验证码发送成功");
            }

            @Override
            public void onFailure(boolean flag, String message) {
                MyUtil.showToast(getApplicationContext(), message);
            }
        });
    }

    public void toNext(View view) {
        code = codeEdt.getText().toString().trim();
        this.appAction.checkCode(phoneNum, code, new ActionCallbackListener() {
            @Override
            public void onSuccess(ResponseBO data) {
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.intent_parent), PARENT);
                bundle.putString(getString(R.string.intent_value), phoneNum);
                openActivity(ChangePassWordActivity.class, bundle);
                finish();
            }

            @Override
            public void onFailure(boolean flag, String message) {
                MyUtil.showToast(getApplicationContext(), message);
            }
        });

    }
}
