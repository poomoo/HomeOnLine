package com.poomoo.homeonline.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.poomoo.commlib.LogUtils;

public class JSAndroidActivity extends Activity {

    private Activity mActivity = null;
    private WebView mWebView = null;
    private String TAG = "JSAndroidActivity";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;

        showWebView();
    }

    @SuppressLint("JavascriptInterface")
    public void showWebView() {        // webView与js交互代码
        try {
            mWebView = new WebView(this);
            setContentView(mWebView);

            mWebView.requestFocus();

            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    JSAndroidActivity.this.setTitle("Loading...");
                    JSAndroidActivity.this.setProgress(progress);

                    if (progress >= 80) {
                        JSAndroidActivity.this.setTitle("JsAndroid Test");
                    }
                }
            });

            mWebView.setOnKeyListener(new View.OnKeyListener() {        // webview can go back
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                    return false;
                }
            });

            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("utf-8");

            mWebView.addJavascriptInterface(this, "android");
            mWebView.loadUrl("file:///android_asset/index.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @android.webkit.JavascriptInterface
    public String HtmlCallJava() {
        LogUtils.d(TAG, "JavascriptInterface");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(JSAndroidActivity.this, "HtmlCallJava", Toast.LENGTH_SHORT).show();
            }
        });
        return "Html call Java";
    }

    @android.webkit.JavascriptInterface
    public String jumpToMainActivity() {
        LogUtils.d(TAG, "JavascriptInterface");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(JSAndroidActivity.this, "jumpToMainActivity", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        return "Html call Java";
    }
}
