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
 * Copyright (c) 2016. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxRatingBar;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.EvaluatePresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * 类名 EvaluateActivity
 * 描述 评价
 * 作者 李苜菲
 * 日期 2016/8/24 11:08
 */
public class EvaluateActivity extends BaseDaggerActivity<EvaluatePresenter> {
    @Bind(R.id.ratingBar_describe)
    RatingBar desRbar;
    @Bind(R.id.ratingBar_quality)
    RatingBar qualityRbar;
    @Bind(R.id.ratingBar_price)
    RatingBar priceRbar;
    @Bind(R.id.edt_evaluate)
    EditText evaluateEdt;
    @Bind(R.id.txt_input_num)
    TextView numTxt;
    @Bind(R.id.chk_anonymity)
    CheckBox checkBox;
    @Bind(R.id.btn_evaluate)
    Button btn;

    private final int num = 200;
    private float des;
    private float quality;
    private float price;
    private String content;
    private boolean isAnonymity = false;//是否匿名

    private String orderId;
    private int commodityId;
    private int orderDetailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_evaluate;
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
        bindViewByRxBinding();
        getProgressBar();

        orderId = getIntent().getStringExtra(getString(R.string.intent_orderId));
        commodityId = getIntent().getIntExtra(getString(R.string.intent_commodityId), -1);
        orderDetailId = getIntent().getIntExtra(getString(R.string.intent_orderDetailId), -1);

        evaluateEdt.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                temp = s;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                int number = s.length();
                numTxt.setText(number + "");

                selectionStart = evaluateEdt.getSelectionStart();
                selectionEnd = evaluateEdt.getSelectionEnd();
                if (temp.length() > num) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    evaluateEdt.setText(s);
                    evaluateEdt.setSelection(tempSelection);// 设置光标在最后
                }

            }
        });
    }

    private void bindViewByRxBinding() {
        Observable<Float> ObservableDes = RxRatingBar.ratingChanges(desRbar);
        Observable<Float> ObservableQuality = RxRatingBar.ratingChanges(qualityRbar);
        Observable<Float> ObservablePrice = RxRatingBar.ratingChanges(priceRbar);
        Observable<CharSequence> ObservableContent = RxTextView.textChanges(evaluateEdt);

        Observable.combineLatest(ObservableDes, ObservableQuality, ObservablePrice, ObservableContent, (Float, Float2, Float3, charSequence) -> checkInput())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean Boolean) {
                        btn.setEnabled(Boolean);
                    }
                });
    }

    private Boolean checkInput() {
        des = desRbar.getRating();
        if (des == 0)
            return false;
        quality = qualityRbar.getRating();
        if (quality == 0)
            return false;
        price = priceRbar.getRating();
        if (price == 0)
            return false;
        content = evaluateEdt.getText().toString().trim();
        if (TextUtils.isEmpty(content))
            return false;
        return true;
    }

    public void evaluate(View view) {
        isAnonymity = checkBox.isChecked();
        showProgressBar();
        mPresenter.evaluate(application.getUserId(), orderId, commodityId, content, (int) des, (int) quality, (int) price, orderDetailId, isAnonymity);
    }

    public void successful() {
        hideProgressBar();
        finish();
    }

    public void failed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }

}
