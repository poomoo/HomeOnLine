/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.TimeCountDownUtil;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.GetCodePresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * 类名 GetCodeActivity
 * 描述 获取验证码
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class GetCodeActivity extends BaseDaggerActivity<GetCodePresenter> {
    @Bind(R.id.edt_tel)
    EditText phoneEdt;
    @Bind(R.id.edt_code)
    EditText codeEdt;
    @Bind(R.id.btn_code)
    Button getCodeBtn;
    @Bind(R.id.btn_next)
    Button nextBtn;

    private TimeCountDownUtil timeCountDownUtil;

    private String phoneNum = "";
    private String code = "";
    private String PARENT = "";
    private boolean flag;//true-找回密码 false-注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected int onSetTitle() {
        if (PARENT.equals(getString(R.string.intent_register)))
            return R.string.title_register;
        else
            return R.string.title_findPassWord;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        PARENT = getIntent().getStringExtra(getString(R.string.intent_parent));
        setBack();

        if (PARENT.equals(getString(R.string.intent_register)))
            flag = false;
        else
            flag = true;

        bindViewByRxBinding();

        getCodeBtn.setEnabled(false);
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                if (temp.length() == 11)
                    getCodeBtn.setEnabled(true);
                else
                    getCodeBtn.setEnabled(false);
            }
        });

    }

    private void bindViewByRxBinding() {
        Observable<CharSequence> ObservableTel = RxTextView.textChanges(phoneEdt);
        Observable<CharSequence> ObservableCode = RxTextView.textChanges(codeEdt);

        Observable.combineLatest(ObservableTel, ObservableCode, (email, password) -> checkInput())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean verify) {
                        nextBtn.setEnabled(verify);
                    }
                });
    }

    private boolean checkInput() {
        phoneNum = phoneEdt.getText().toString().trim();
        code = codeEdt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11) {
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            return false;
        }

        return true;
    }

    public void toGetCode(View view) {
        phoneNum = phoneEdt.getText().toString().trim();
        mPresenter.getCode(phoneNum, flag);
    }

    public void toNext(View view) {
        phoneNum = phoneEdt.getText().toString().trim();
        code = codeEdt.getText().toString().trim();
//        mPresenter.checkCode(phoneNum, code);
        checkCodeSucceed();
    }

    public void getCodeSucceed() {
        MyUtils.showToast(getApplicationContext(), "验证码发送成功");
        timeCountDownUtil = new TimeCountDownUtil(60 * 1000, 1000, getCodeBtn);
        timeCountDownUtil.start();
        getCodeBtn.setEnabled(false);
    }

    public void getCodeFailed(String msg) {
        MyUtils.showToast(getApplicationContext(), msg);
    }

    public void checkCodeSucceed() {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_parent), PARENT);
        bundle.putString(getString(R.string.intent_value), phoneNum);
        openActivity(ChangePassWordActivity.class, bundle);
        finish();
    }

    public void checkCodeFailed(String msg) {
        MyUtils.showToast(getApplicationContext(), msg);
    }
}
