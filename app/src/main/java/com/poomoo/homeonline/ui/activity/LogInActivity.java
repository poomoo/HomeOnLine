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
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.model.RUserBO;
import com.poomoo.homeonline.presenters.LoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 类名 LogInActivity
 * 描述 登录
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class LogInActivity extends BaseDaggerActivity<LoginPresenter> {
    @Bind(R.id.edt_userName)
    EditText nameEdt;
    @Bind(R.id.edt_userPassWord)
    EditText passWordEdt;
    @Bind(R.id.btn_login)
    Button loginBtn;

    private String name = "";
    private String passWord = "";

    public static LogInActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        instance = this;
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_login;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        setBack();
        getProgressBar();
        bindViewByRxBinding();
    }

    private void bindViewByRxBinding() {
        Observable<CharSequence> ObservableName = RxTextView.textChanges(nameEdt);
        Observable<CharSequence> ObservablePassword = RxTextView.textChanges(passWordEdt);

        Observable.combineLatest(ObservableName, ObservablePassword, (email, password) -> checkInput())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean verify) {
                        loginBtn.setEnabled(verify);
                    }
                });
    }

    private boolean checkInput() {
        name = nameEdt.getText().toString().trim();
        passWord = passWordEdt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        if (passWord.length() < 6) {
            return false;
        }

        return true;
    }


    @OnClick({R.id.txt_register, R.id.txt_forgetPassWord})
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
        showProgressBar();
        mPresenter.login(name, passWord);
    }

    public void loginSucceed(RUserBO rUserBO) {
        hideProgressBar();
        SPUtils.put(getApplicationContext(), getString(R.string.sp_isLogin), true);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_userId), rUserBO.userId);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_phoneNum), name);
        SPUtils.put(getApplicationContext(), getString(R.string.sp_nickName), rUserBO.nickName);
        application.setUserId(rUserBO.userId);
        application.setNickName(rUserBO.nickName);
        application.setTel(name);
        finish();
    }

    public void loginFailed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }
}
