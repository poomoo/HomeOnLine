/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.annotation.SuppressLint;
import android.media.Image;
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
public class WebViewActivity extends BaseActivity {
    @Bind(R.id.web_pub)
    WebView pubWeb;
    @Bind(R.id.img_load)
    ImageView loadImg;
    @Bind(R.id.txt_progress)
    TextView progressTxt;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    @SuppressLint("JavascriptInterface")
    private void initView() {
        url = getIntent().getStringExtra(getString(R.string.intent_value));
        pubWeb.setWebViewClient(new webViewClient());
        pubWeb.setWebChromeClient(new MyWebChromeClient());
        pubWeb.getSettings().setJavaScriptEnabled(true);
        pubWeb.loadUrl(url);
        pubWeb.getSettings().setDomStorageEnabled(true);
        pubWeb.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        pubWeb.getSettings().setAppCachePath(appCachePath);
        pubWeb.getSettings().setAllowFileAccess(true);
        pubWeb.getSettings().setAppCacheEnabled(true);
        pubWeb.getSettings().setDefaultTextEncodingName("utf-8");

        pubWeb.addJavascriptInterface(new JavaScript(this), "android");
//        pubWeb.loadUrl("file:///android_asset/index.html");
    }

    class webViewClient extends WebViewClient {
        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressTxt.setVisibility(View.VISIBLE);
            loadImg.setVisibility(View.VISIBLE);
            pubWeb.setVisibility(View.GONE);

            view.loadUrl(url);
            //如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            LogUtils.d(TAG, "onReceivedError" + error);
            pubWeb.setVisibility(View.GONE);
            MyUtils.showToast(getApplicationContext(), "onReceivedError");
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            LogUtils.d(TAG, "onReceivedHttpError" + errorResponse);
            MyUtils.showToast(getApplicationContext(), "onReceivedHttpError");
        }
    }

    class MyWebChromeClient extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {
            LogUtils.i(TAG, "onProgressChanged:" + progress);
            progressTxt.setText(progress + "%");
            if (progress == 100) {
                progressTxt.setVisibility(View.GONE);
                loadImg.setVisibility(View.GONE);
                pubWeb.setVisibility(View.VISIBLE);
            }
        }
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
