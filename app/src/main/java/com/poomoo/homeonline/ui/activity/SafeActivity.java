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
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 SafeActivity
 * 描述 账户与安全
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class SafeActivity extends BaseActivity {
    @Bind(R.id.txt_safe_nickName)
    TextView nameTxt;
    @Bind(R.id.txt_safe_tel)
    TextView telTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }


    @Override
    protected int onBindLayout() {
        return R.layout.activity_safe;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_safe;
    }

    private void init() {
        setBack();
        nameTxt.setText(application.getNickName());
        telTxt.setText(MyUtils.hiddenTel(application.getTel()));
    }

    /**
     * 设置昵称
     *
     * @param view
     */
    public void setNickName(View view) {
        openActivity(UpdateNickNameActivity.class);
    }

    /**
     * 修改手机号码
     *
     * @param view
     */
    public void updateTel(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_parent), getString(R.string.intent_updateTel));
        openActivity(GetCodeActivity.class, bundle);
    }

    /**
     * 修改登录密码
     *
     * @param view
     */
    public void updateLoginPW(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_parent), getString(R.string.intent_updatePW));
        openActivity(GetCodeActivity.class, bundle);
    }

    /**
     * 退出登录
     *
     * @param view
     */
    public void logOut(View view) {
        createDialog("确认退出登录?", (dialog, which) -> {
            SPUtils.put(getApplicationContext(), getString(R.string.sp_isLogin), false);
            finish();
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.intent_value), 0);
            openActivity(MainNewActivity.class, bundle);
        }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nameTxt.setText(application.getNickName());
    }
}
