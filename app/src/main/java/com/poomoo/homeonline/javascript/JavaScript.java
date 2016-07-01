/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.javascript;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.activity.MainNewActivity;

/**
 * 作者: 李苜菲
 * 日期: 2016/6/29 17:20.
 */
public class JavaScript {
    private Activity mContext;
    private Intent intent;

    @SuppressLint("JavascriptInterface")
    public JavaScript(Activity mContext) {
        this.mContext = mContext;
    }

    @android.webkit.JavascriptInterface
    public void jumpToMainActivity(final int flag) {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(mContext, MainNewActivity.class);
                intent.putExtra(mContext.getString(R.string.intent_value), flag);
                mContext.startActivity(intent);
                mContext.finish();
            }
        });
    }
}
