package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private WebView webView;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new webViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(getString(R.string.server_url) + "?userId=" + SPUtils.get(getApplicationContext(), getString(R.string.sp_id), "") + "&isAppClient=true");
        webView.loadUrl(getString(R.string.server_url));

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);

        webView.getSettings().setAppCacheEnabled(true);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_main;
    }

    class webViewClient extends WebViewClient {

        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            //如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack() && !isIndexPage(webView.getUrl())) {
            webView.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            MyUtils.showToast(getApplicationContext(), "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    private boolean isIndexPage(String url) {
        if (url.equals(getString(R.string.server_url)))
            return true;
        else if (url.equals(getString(R.string.category_url)))
            return true;
        else if (url.equals(getString(R.string.rush_url)))
            return true;
        else if (url.equals(getString(R.string.cart_url)))
            return true;
        else if (url.equals(getString(R.string.user_url)))
            return true;
        else
            return false;
    }
}
