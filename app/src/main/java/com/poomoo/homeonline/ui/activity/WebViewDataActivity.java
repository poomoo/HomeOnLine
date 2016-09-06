/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.WebViewPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RDataBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 WebViewActivity
 * 描述 WebView
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class WebViewDataActivity extends BaseDaggerActivity<WebViewPresenter> implements ErrorLayout.OnActiveClickListener {
    @Bind(R.id.web_pub)
    WebView pubWeb;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_webview_data;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_ticket_statement;
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
        pubWeb.getSettings().setDefaultTextEncodingName("utf-8");

        errorLayout.setState(ErrorLayout.LOADING, "");
        errorLayout.setOnActiveClickListener(this);
        mPresenter.getData();
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getData();
    }

    public void successful(RDataBO rDataBO) {
        errorLayout.setState(ErrorLayout.HIDE, "");
        pubWeb.loadData(rDataBO.remark, "text/html; charset=UTF-8", null);// 这种写法可以正确解码
    }

    public void failed(String msg) {
        errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
    }
}
