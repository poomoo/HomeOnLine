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
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.javascript.JavaScript;
import com.poomoo.homeonline.ui.base.BaseFragment;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.MySwipeRefreshLayout;

import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 GrabFragment
 * 描述 ${TODO}
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
    private String url;
    private boolean isValid = false;

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
        webView.addJavascriptInterface(new JavaScript(getActivity()), "android");

        webView.loadUrl(NetConfig.grabUrl);

        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mErrorLayout.setOnActiveClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_refresh_first, R.color.swipe_refresh_second,
                R.color.swipe_refresh_third, R.color.swipe_refresh_four
        );
        swipeRefreshLayout.setViewGroup(webView);
    }

    @Override
    public void onLoadActiveClick() {
        isValid = false;
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        webView.reload();
    }

    @Override
    public void onRefresh() {
        webView.setVisibility(View.GONE);
        webView.loadUrl(NetConfig.grabUrl);
    }

    class MyWebChromeClient extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {
            LogUtils.d(TAG, "onProgressChanged" + progress);
            if (progress == 100) {
                swipeRefreshLayout.setRefreshing(false);
                if (!isValid) {
                    webView.setVisibility(View.VISIBLE);
                    mErrorLayout.setState(ErrorLayout.HIDE, "");
                }
                if (validStatusCode(url)) {
                    webView.setVisibility(View.GONE);
                    mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
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
            isValid = true;
            webView.setVisibility(View.GONE);
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            LogUtils.d(TAG, "onReceivedError" + error);
            isValid = true;
            webView.setVisibility(View.GONE);
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            LogUtils.d(TAG, "onReceivedHttpError" + errorResponse);
            isValid = true;
            webView.setVisibility(View.GONE);
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        }
    }

    private boolean validStatusCode(String url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            URL validatedURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) validatedURL.openConnection();
            con.setRequestMethod("HEAD");
            int responseCode = con.getResponseCode();
            if (responseCode == 404 || responseCode == 405 || responseCode == 504) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
