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
package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.javascript.JavaScript;
import com.poomoo.homeonline.ui.base.BaseFragment;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.MySwipeRefreshLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 GrabFragment
 * 描述 家有抢购
 * 作者 李苜菲
 * 日期 2016/8/19 16:12
 */
public class GrabFragment extends BaseFragment implements ErrorLayout.OnActiveClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipe_refresh)
    MySwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.web_grab)
    WebView webView;
    @Bind(R.id.error_frame)
    ErrorLayout mErrorLayout;
    private boolean enabled = true;//是否加载了网页

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        webView.setWebViewClient(new webViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        if (MyUtils.hasInternet(getActivity()))
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        else
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓存模式
        webView.getSettings().setDomStorageEnabled(true);//启用H5 DOM API （默认false）
        webView.getSettings().setAppCacheEnabled(true);//启用应用缓存（默认false）可结合 setAppCachePath 设置缓存路径
        String appCacheDir = getActivity().getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCacheDir);
        webView.getSettings().setAllowFileAccess(true);
        webView.addJavascriptInterface(new JavaScript(getActivity(), webView), "android");
        webView.loadUrl(NetConfig.grabUrl);
        LogUtils.d(TAG, "grabUrl:" + NetConfig.grabUrl);

        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mErrorLayout.setOnActiveClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_refresh_first, R.color.swipe_refresh_second,
                R.color.swipe_refresh_third, R.color.swipe_refresh_four
        );
        swipeRefreshLayout.setViewGroup(webView);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onLoadActiveClick() {
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        webView.reload();
        enabled = true;
    }

    @Override
    public void onRefresh() {
        if (MyUtils.hasInternet(getActivity())) {
            webView.setVisibility(View.GONE);
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
            webView.reload();
        } else swipeRefreshLayout.setRefreshing(false);
    }

    class MyWebChromeClient extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {
            LogUtils.d(TAG, "onProgressChanged" + progress);
            if (progress == 100) {
                swipeRefreshLayout.setRefreshing(false);
                if (enabled) {
                    mErrorLayout.setState(ErrorLayout.HIDE, "");
                    webView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setEnabled(true);
                    SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_hasCache), true);
                }
            }
        }
    }

    class webViewClient extends WebViewClient {

        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }


        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            LogUtils.d(TAG, "error code:" + errorCode);
            swipeRefreshLayout.setEnabled(false);
            if ((boolean) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_hasCache), false)) {
                webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓存模式
                webView.reload();
                webView.setVisibility(View.GONE);
                enabled = true;
            } else {
                webView.setVisibility(View.GONE);
                mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
                enabled = false;
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            LogUtils.d(TAG, "onReceivedError" + error);
            swipeRefreshLayout.setEnabled(false);
        }
    }
}
