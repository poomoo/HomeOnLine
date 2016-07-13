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

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.activity.MainActivity;

/**
 * 作者: 李苜菲
 * 日期: 2016/7/8 15:11.
 */
public class AddAndMinusView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private Context context;
    private TextView minusTxt;
    private EditText countEdt;
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
        countEdt = (EditText) view.findViewById(R.id.edt_count);
        plusTxt = (TextView) view.findViewById(R.id.txt_plus);

        minusTxt.setOnClickListener(this);
        plusTxt.setOnClickListener(this);
        countEdt.addTextChangedListener(this);

        addView(view);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        countEdt.setText(count + "");
    }

    @Override
    public void onClick(View v) {
        if (onCountChangeListener == null)
            return;
        switch (v.getId()) {
            case R.id.txt_minus:
                countEdt.setText(--count > 1 ? count + "" : 1 + "");
                break;
            case R.id.txt_plus:
                countEdt.setText(++count + "");
                break;
        }
        if (count < 1)
            count = 1;
        onCountChangeListener.count(count);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setOnCountChangeListener(OnCountChangeListener onCountChangeListener) {
        this.onCountChangeListener = onCountChangeListener;
    }

    public interface OnCountChangeListener {
        void count(int count);
    }
}
