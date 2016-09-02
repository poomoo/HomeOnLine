/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.ChangePassWordPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.model.RUserBO;
import com.poomoo.model.request.QUserBO;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * 类名 ChangePassWordActivity
 * 描述 修改密码
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class ChangePassWordActivity extends BaseDaggerActivity<ChangePassWordPresenter> {
    @Bind(R.id.edt_passWord)
    EditText passWordEdt;
    @Bind(R.id.edt_passWordAgain)
    EditText passWordAgainEdt;
    @Bind(R.id.btn_confirm)
    Button confirmBtn;

    private String phoneNum = "";
    private String passWord1 = "";
    private String passWord2 = "";
    private String PARENT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_change_password2;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_resetPassWord;
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
        getProgressBar();

        PARENT = getIntent().getStringExtra(getString(R.string.intent_parent));
        phoneNum = getIntent().getStringExtra(getString(R.string.intent_value));

        bindViewByRxBinding();
    }

    public void toComplete(View view) {
        MyUtils.hiddenKeyBoard(this, passWordAgainEdt);
        if (!passWord1.equals(passWord2)) {
            MyUtils.showToast(getApplicationContext(), "两次输入的密码不一样");
            return;
        }
        showProgressBar();
        if (PARENT.equals(getString(R.string.intent_register)))
            mPresenter.register(phoneNum, passWord1);
        else
            mPresenter.changePW(application.getTel(), passWord1);
    }

    private void bindViewByRxBinding() {
        Observable<CharSequence> ObservableEmail = RxTextView.textChanges(passWordEdt);
        Observable<CharSequence> ObservablePassword = RxTextView.textChanges(passWordAgainEdt);

        Observable.combineLatest(ObservableEmail, ObservablePassword, (email, password) -> checkInput())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean verify) {
                        confirmBtn.setEnabled(verify);
                    }
                });
    }


    private boolean checkInput() {
        passWord1 = passWordEdt.getText().toString().trim();
        passWord2 = passWordAgainEdt.getText().toString().trim();
        if (TextUtils.isEmpty(passWord1)) {
//            MyUtils.showToast(getApplicationContext(), "请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(passWord2)) {
//            MyUtils.showToast(getApplicationContext(), "请输入确认密码");
            return false;
        }

        if (passWord1.length() < 6) {
//            MyUtils.showToast(getApplicationContext(), "请输入6位以上密码");
            return false;
        }
        return true;
    }

    public void registerSucceed() {
        if (PARENT.equals(getString(R.string.intent_register)))
            mPresenter.login(phoneNum, passWord1);
        else {
            hideProgressBar();
            finish();
            MyUtils.showToast(getApplicationContext(), "修改密码成功");
        }
    }

    public void registerFailed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }

    public void loginSucceed(RUserBO rUserBO) {
        hideProgressBar();
        SPUtils.put(getApplicationContext(), getString(R.string.sp_isLogin), true);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_userId), rUserBO.userId);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_phoneNum), phoneNum);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_nickName), rUserBO.nickName);
        application.setUserId(rUserBO.userId);
        application.setNickName(rUserBO.nickName);
        application.setTel(phoneNum);
        if (LogInActivity.instance != null)
            LogInActivity.instance.finish();
        finish();
    }

    public void loginFailed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }


    public void changeSuccessful() {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), "修改密码成功");
        finish();
    }

    public void changeFailed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }
}
