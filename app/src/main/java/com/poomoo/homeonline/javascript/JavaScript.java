/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.javascript;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Toast;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.activity.CommodityInfoActivity;
import com.poomoo.homeonline.ui.activity.MainNewActivity;
import com.poomoo.homeonline.ui.base.BaseActivity;

/**
 * 类名 JavaScript
 * 描述 JS注入webView类
 * 作者 李苜菲
 * 日期 2016/7/19 11:27
 */
public class JavaScript {
    private Activity mContext;
    private Intent intent;
    private WebView webView;

    @SuppressLint("JavascriptInterface")
    public JavaScript(Activity mContext, WebView webView) {
        this.mContext = mContext;
        this.webView = webView;
    }

    @android.webkit.JavascriptInterface
    public void jumpToMainActivity(final int flag) {
        mContext.runOnUiThread(() -> {
            intent = new Intent(mContext, MainNewActivity.class);
            intent.putExtra(mContext.getString(R.string.intent_value), flag);
            mContext.startActivity(intent);
            mContext.finish();
        });
    }

    @android.webkit.JavascriptInterface
    public void jumpToCommodityInfo(final int commodityId, final int commodityDetailId, final int rushId, final int commodityType) {
        mContext.runOnUiThread(() -> {
            intent = new Intent(mContext, CommodityInfoActivity.class);
            intent.putExtra(mContext.getString(R.string.intent_commodityId), commodityId);
            intent.putExtra(mContext.getString(R.string.intent_commodityDetailId), commodityDetailId);
            intent.putExtra(mContext.getString(R.string.intent_commodityType), commodityType);
            intent.putExtra(mContext.getString(R.string.intent_matchId), rushId);
            mContext.startActivity(intent);
        });
    }

    @android.webkit.JavascriptInterface
    public void back() {
        mContext.runOnUiThread(() -> {
            if (webView.canGoBack())
                webView.goBack();
            else {
                mContext.finish();
                mContext.overridePendingTransition(R.anim.activity_center, R.anim.activity_out_to_right);
            }
        });
    }
}
