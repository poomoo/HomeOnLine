/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.poomoo.core.ActionCallbackListener;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.utils.LogUtils;
import com.poomoo.homeonline.utils.MyUtil;
import com.poomoo.homeonline.utils.SPUtils;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.UserBO;

/**
 * 登录
 * 作者: 李苜菲
 * 日期: 2016/1/11 14:15.
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
        setContentView(R.layout.activity_login);
        instance = this;
        initView();
    }

    @Override
    protected void initView() {
        initTitleBar();
        nameEdt = (EditText) findViewById(R.id.edt_userName);
        passWordEdt = (EditText) findViewById(R.id.edt_userPassWord);
        registerTxt = (TextView) findViewById(R.id.txt_register);
        forgetPassWordTxt = (TextView) findViewById(R.id.txt_forgetPassWord);

        registerTxt.setOnClickListener(this);
        forgetPassWordTxt.setOnClickListener(this);
    }

    @Override
    protected void initTitleBar() {
        HeaderViewHolder headerViewHolder = getHeaderView();
        headerViewHolder.titleTxt.setText(R.string.title_login);
        headerViewHolder.backImg.setVisibility(View.GONE);
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
                MyUtil.showToast(getApplicationContext(), message);
            }
        });
    }
}
