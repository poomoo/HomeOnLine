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
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.UpdateNickNamePresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 UpdateNickNameActivity
 * 描述 修改昵称
 * 作者 李苜菲
 * 日期 2016/8/16 10:30
 */
public class UpdateNickNameActivity extends BaseDaggerActivity<UpdateNickNamePresenter> {
    @Bind(R.id.txt_nickName)
    TextView nameTxt;
    @Bind(R.id.edt_newName)
    EditText nameEdt;
    @Bind(R.id.btn_submit)
    Button btn;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_update_nickname;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_updateNickName;
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

        nameTxt.setText(application.getNickName());
        nameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
                if (name.length() > 0)
                    btn.setEnabled(true);
                else
                    btn.setEnabled(false);
            }
        });
    }

    public void submitName(View view) {
        showProgressBar();
        LogUtils.d(TAG, "userId:" + application.getUserId());
        mPresenter.updateNickName(application.getUserId(), name, "");
    }

    public void failed(String msg) {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), msg);
    }

    public void succeed() {
        hideProgressBar();
        MyUtils.showToast(getApplicationContext(), "修改昵称成功");
        SPUtils.put(getApplicationContext(), getString(R.string.sp_nickName), name);
        finish();
    }
}
