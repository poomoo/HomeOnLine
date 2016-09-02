/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.javascript.JavaScript;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 WebViewActivity
 * 描述 WebView
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class WebViewActivity extends BaseActivity implements ErrorLayout.OnActiveClickListener {
    @Bind(R.id.web_pub)
    WebView pubWeb;
    //    @Bind(R.id.txt_progress)
//    TextView progressTxt;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private String url;
    private boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected int onSetTitle() {
        return R.string.empty;
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        url = getIntent().getStringExtra(getString(R.string.intent_value));
        LogUtils.d(TAG, "url:" + url);
        pubWeb.setWebViewClient(new webViewClient());
        pubWeb.setWebChromeClient(new MyWebChromeClient());
        pubWeb.getSettings().setJavaScriptEnabled(true);
        pubWeb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        pubWeb.getSettings().setDomStorageEnabled(true);//启用H5 DOM API （默认false）
        pubWeb.getSettings().setAppCacheEnabled(true);//启用应用缓存（默认false）可结合 setAppCachePath 设置缓存路径
        pubWeb.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        pubWeb.getSettings().setDefaultTextEncodingName("utf-8");

        pubWeb.addJavascriptInterface(new JavaScript(this), "android");
        pubWeb.loadUrl(url);

        errorLayout.setState(ErrorLayout.LOADING, "");
        errorLayout.setOnActiveClickListener(this);
    }

    @Override
    public void onLoadActiveClick() {
        isValid = false;
        errorLayout.setState(ErrorLayout.LOADING, "");
        pubWeb.reload();
    }

    class webViewClient extends WebViewClient {
        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            //如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            LogUtils.d(TAG, "onReceivedError" + error);
            isValid = true;
            pubWeb.setVisibility(View.GONE);
            errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            LogUtils.d(TAG, "onReceivedError过时" + errorCode);
            isValid = true;
//            pubWeb.setVisibility(View.GONE);
//            errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            LogUtils.d(TAG, "onReceivedHttpError" + errorResponse);
            isValid = true;
            pubWeb.setVisibility(View.GONE);
            errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        }
    }

    class MyWebChromeClient extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {
            LogUtils.i(TAG, "onProgressChanged:" + progress);
            if (progress > 80){
                errorLayout.setState(ErrorLayout.HIDE, "");
                pubWeb.setVisibility(View.VISIBLE);
            }
            if (progress == 100 && isValid) {
                pubWeb.setVisibility(View.GONE);
                errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
        }
    }

    public void back(View view) {
        if (pubWeb.canGoBack())
            pubWeb.goBack();
        else {
            finish();
            getActivityOutToRight();
        }
    }

    public void toDo(View view) {
        finish();
        getActivityOutToRight();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && pubWeb.canGoBack()) {
            pubWeb.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
