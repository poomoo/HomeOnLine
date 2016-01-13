/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.poomoo.core.ActionCallbackListener;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.utils.LogUtils;
import com.poomoo.homeonline.utils.MyUtil;
import com.poomoo.homeonline.utils.SPUtils;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.UserBO;

/**
 * 作者: 李苜菲
 * 日期: 2016/1/11 15:06.
 */
public class ChangePassWordActivity extends BaseActivity {
    private EditText passWordEdt;
    private EditText passWordAgainEdt;

    private String phoneNum = "";
    private String passWord1 = "";
    private String passWord2 = "";

    private String PARENT = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwrod2);

        initView();
    }

    @Override
    protected void initView() {
        initTitleBar();
        passWordEdt = (EditText) findViewById(R.id.edt_passWord);
        passWordAgainEdt = (EditText) findViewById(R.id.edt_passWordAgain);

        PARENT = getIntent().getStringExtra(getString(R.string.intent_parent));
    }

    @Override
    protected void initTitleBar() {
        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.titleTxt.setText(getString(R.string.title_resetPassWord));
    }

    public void toComplete(View view) {
        phoneNum = getIntent().getStringExtra(getString(R.string.intent_value));
        if (checkInput()) {
            showProgressDialog(getString(R.string.dialog));
            this.appAction.register(phoneNum, passWord1, new ActionCallbackListener() {
                @Override
                public void onSuccess(ResponseBO data) {
                    if (PARENT.equals(getString(R.string.intent_register)))
                        login();
                    else
                        finish();
                    MyUtil.showToast(getApplicationContext(), "修改密码成功");
                }

                @Override
                public void onFailure(boolean flag, String message) {
                    closeProgressDialog();
                    MyUtil.showToast(getApplicationContext(), message);
                }
            });
        }

    }

    private boolean checkInput() {
        passWord1 = passWordEdt.getText().toString().trim();
        passWord2 = passWordAgainEdt.getText().toString().trim();
        if (TextUtils.isEmpty(passWord1)) {
            MyUtil.showToast(getApplicationContext(), "请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(passWord2)) {
            MyUtil.showToast(getApplicationContext(), "请输入确认密码");
            return false;
        }
        if (!passWord1.equals(passWord2)) {
            MyUtil.showToast(getApplicationContext(), "两次输入的密码不一样");
            return false;
        }
        if (passWord1.length() < 6) {
            MyUtil.showToast(getApplicationContext(), "请输入6位以上密码");
            return false;
        }
        return true;
    }

    private void login() {
        this.appAction.logIn(phoneNum, passWord1, new ActionCallbackListener() {
            @Override
            public void onSuccess(ResponseBO data) {
                closeProgressDialog();
                UserBO userBO;
                userBO = (UserBO) data.getObj();
                LogUtils.i(TAG, "id:" + userBO.getId());
                if (LogInActivity.instance != null)
                    LogInActivity.instance.finish();
                finish();
                SPUtils.put(getApplicationContext(), getString(R.string.sp_id), userBO.getId() + "");
                openActivity(MainActivity.class);
            }

            @Override
            public void onFailure(boolean flag, String message) {
                closeProgressDialog();
                MyUtil.showToast(getApplicationContext(), message);
            }
        });
    }
}
