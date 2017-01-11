/**
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 * Copyright (c) 2017. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.poomoo.commlib.CountDownListener;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.TimeCountDownUtil;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.CustomDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * 类名 RegisterToWholeSaleUserActivity
 * 描述 注册成为集采用户
 * 作者 李苜菲
 * 日期 2017/1/3 14:20
 */
public class RegisterToWholeSaleUserActivity extends BaseActivity {
    @Bind(R.id.edt_applicantName)
    EditText nameEdt;
    @Bind(R.id.edt_applicantTel)
    EditText telEdt;
    @Bind(R.id.txt_getCode)
    TextView codeTxt;
    @Bind(R.id.edt_code)
    EditText codeEdt;
    @Bind(R.id.edt_applicantCompany)
    EditText companyEdt;
    @Bind(R.id.edt_applicantCompanyAddress)
    EditText addressEdt;
    @Bind(R.id.edt_applicantCompanyTel)
    EditText companyTelEdt;
    @Bind(R.id.btn_submit)
    Button submitBtn;

    private String name;
    private String tel;
    private String code;
    private String company;
    private String address;
    private String companyTel;
    private TimeCountDownUtil timeCountDownUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setBack();
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_register_whole_sale;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_toWhole_sale_user;
    }

    private void init() {
        telEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!codeTxt.isEnabled())
                    return;
                String temp = s.toString();
                if (MyUtils.checkPhoneNum(temp)) {
                    codeTxt.setBackgroundResource(R.drawable.shape_rectangle_round_red);
                    codeTxt.setTextColor(ContextCompat.getColor(RegisterToWholeSaleUserActivity.this, R.color.ThemeRed));
                    codeTxt.setClickable(true);
                } else {
                    codeTxt.setBackgroundResource(R.drawable.shape_rectangle_round_gray);
                    codeTxt.setTextColor(ContextCompat.getColor(RegisterToWholeSaleUserActivity.this, R.color.ThemeText));
                    codeTxt.setClickable(false);
                }
            }
        });

        bindViewByRxBinding();
    }

    private void bindViewByRxBinding() {
        Observable<CharSequence> ObservableName = RxTextView.textChanges(nameEdt);
        Observable<CharSequence> ObservableTel = RxTextView.textChanges(telEdt);
        Observable<CharSequence> ObservableCode = RxTextView.textChanges(codeEdt);
        Observable<CharSequence> ObservableCompany = RxTextView.textChanges(companyEdt);
        Observable<CharSequence> ObservableAddress = RxTextView.textChanges(addressEdt);
        Observable<CharSequence> ObservableCompanyTel = RxTextView.textChanges(companyTelEdt);

        Observable.combineLatest(ObservableName, ObservableTel, ObservableCode, ObservableCompany, ObservableAddress, ObservableCompanyTel, (name, tel, code, company, address, companyTel) -> checkInput())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean verify) {
                        submitBtn.setEnabled(verify);
                    }
                });
    }

    private boolean checkInput() {
        name = nameEdt.getText().toString().trim();
        tel = telEdt.getText().toString().trim();
        code = codeEdt.getText().toString().trim();
        company = companyEdt.getText().toString().trim();
        address = addressEdt.getText().toString().trim();
        companyTel = companyTelEdt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        if (!MyUtils.checkPhoneNum(tel)) {
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        if (TextUtils.isEmpty(company)) {
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            return false;
        }
        if (TextUtils.isEmpty(companyTel)) {
            return false;
        }

        return true;
    }

    public void toGetCode(View view) {
//        MyUtils.hiddenKeyBoard(this, phoneEdt);
//        showProgressBar();
        LogUtils.d(TAG, "点击发送验证码");
        tel = telEdt.getText().toString().trim();
//        mPresenter.getCode(phoneNum, flag);
        codeTxt.setBackgroundResource(R.drawable.shape_rectangle_round_gray);
        codeTxt.setTextColor(ContextCompat.getColor(RegisterToWholeSaleUserActivity.this, R.color.ThemeText));
        timeCountDownUtil = new TimeCountDownUtil(MyConfig.SMSCOUNTDOWNTIME, MyConfig.COUNTDOWNTIBTERVAL, codeTxt, result -> {
            tel = telEdt.getText().toString().trim();
            if (MyUtils.checkPhoneNum(tel)) {
                codeTxt.setBackgroundResource(R.drawable.shape_rectangle_round_red);
                codeTxt.setTextColor(ContextCompat.getColor(RegisterToWholeSaleUserActivity.this, R.color.ThemeRed));
                codeTxt.setText("重新获取");
            } else {
                codeTxt.setBackgroundResource(R.drawable.shape_rectangle_round_gray);
                codeTxt.setTextColor(ContextCompat.getColor(RegisterToWholeSaleUserActivity.this, R.color.ThemeText));
                codeTxt.setText("获取短信验证码");
                codeTxt.setClickable(false);
            }
        });
        timeCountDownUtil.start();
        codeTxt.setEnabled(false);
    }

    public void toSubmit(View view) {
        showDialog();
    }

    public void showDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("恭喜您已提交成功,我们将在1～3个工作日内完成审核,请耐心等待。");
        builder.setPositiveButton("返回", (dialog, which) ->
                dialog.dismiss()
        );
        builder.create().show();
    }
}
