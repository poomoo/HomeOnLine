/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.activity.MainActivity;

/**
 * 类名 AddAndMinusView
 * 描述 购物车数量加减控件
 * 作者 李苜菲
 * 日期 2016/7/19 11:35
 */
public class AddAndMinusView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "AddAndMinusView";
    private Context context;
    private TextView minusTxt;
    private TextView countTxt;
    private TextView plusTxt;

    private int count = 1;
    private OnCountChangeListener onCountChangeListener;

    public AddAndMinusView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AddAndMinusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_minus_view, null);

        minusTxt = (TextView) view.findViewById(R.id.txt_minus);
        countTxt = (TextView) view.findViewById(R.id.txt_count);
        plusTxt = (TextView) view.findViewById(R.id.txt_plus);

        minusTxt.setOnClickListener(this);
        countTxt.setOnClickListener(this);
        plusTxt.setOnClickListener(this);

        addView(view);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        countTxt.setText(count + "");
        LogUtils.d(TAG, "setCount:" + count);
    }

    @Override
    public void onClick(View v) {
        if (onCountChangeListener == null)
            return;
        switch (v.getId()) {
            case R.id.txt_minus:
                countTxt.setText(--count > 1 ? count + "" : 1 + "");
                if (count < 1)
                    count = 1;
                onCountChangeListener.count(count, false);
                break;
            case R.id.txt_plus:
                countTxt.setText(++count + "");
                if (count < 1)
                    count = 1;
                onCountChangeListener.count(count, false);
                break;
            case R.id.txt_count:
                onCountChangeListener.count(count, true);
                break;
        }
    }

    public void setOnCountChangeListener(OnCountChangeListener onCountChangeListener) {
        this.onCountChangeListener = onCountChangeListener;
    }

    public interface OnCountChangeListener {
        void count(int count, boolean isEdit);
    }
}
