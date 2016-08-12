/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poomoo.commlib.MyConfig;
import com.poomoo.homeonline.R;

/**
 * 类名 AddAndMinusView
 * 描述 购物车数量加减控件
 * 作者 李苜菲
 * 日期 2016/7/19 11:35
 */
public class AddAndMinusView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "AddAndMinusView";
    private Context context;
    //    private TextView minusTxt;
    private ImageView minusImg;
    private TextView countTxt;
    private ImageView plusImg;
//    private TextView plusTxt;

    private int count = 1;
//    private int minNum = 1;
//    private int maxNum = 200;
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

        minusImg = (ImageView) view.findViewById(R.id.img_minus);
        countTxt = (TextView) view.findViewById(R.id.txt_count);
        plusImg = (ImageView) view.findViewById(R.id.img_plus);

        minusImg.setOnClickListener(this);
        countTxt.setOnClickListener(this);
        plusImg.setOnClickListener(this);

        addView(view);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        countTxt.setText(count + "");
//        LogUtils.d(TAG, "setCount:" + count);
        setEnabled();
    }

    @Override
    public void onClick(View v) {
        if (onCountChangeListener == null)
            return;
        switch (v.getId()) {
            case R.id.img_minus:
//                LogUtils.d(TAG, "点击减" + count);
                count--;
//                countTxt.setText(--count > 1 ? count + "" : 1 + "");
                if (count < 1)
                    count = 1;
                onCountChangeListener.count(count, false);
                break;
            case R.id.img_plus:
//                LogUtils.d(TAG, "点击加" + count);
                count++;
//                countTxt.setText(++count + "");
                if (count < 1)
                    count = 1;
                onCountChangeListener.count(count, false);
                break;
            case R.id.txt_count:
                onCountChangeListener.count(count, true);
                break;
        }
        setEnabled();
    }

    private void setEnabled() {
        if (count == MyConfig.MINCOUNT) {
            minusImg.setEnabled(false);
            plusImg.setEnabled(true);
        } else if (count == MyConfig.MAXCOUNT) {
            minusImg.setEnabled(true);
            plusImg.setEnabled(false);
        } else {
            minusImg.setEnabled(true);
            plusImg.setEnabled(true);
        }
//        LogUtils.d(TAG, "minusImg:" + minusImg.isEnabled() + " plusImg:" + plusImg.isEnabled());
    }

    public void setOnCountChangeListener(OnCountChangeListener onCountChangeListener) {
        this.onCountChangeListener = onCountChangeListener;
    }

    public interface OnCountChangeListener {
        void count(int count, boolean isEdit);
    }
}
