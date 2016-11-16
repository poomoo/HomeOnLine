package com.poomoo.homeonline.ui.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.poomoo.homeonline.R;


/**
 * 类名 RulePopupWindow
 * 描述
 * 作者 李苜菲
 * 日期 2016/11/1 11:28
 */
public class RulePopupWindow extends PopupWindow {
    private View mMenuView;
    private TextView ruleTxt;
    private Button closeBtn;

    public RulePopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_rule, null);
        closeBtn = (Button) mMenuView.findViewById(R.id.btn_close);
        ruleTxt = (TextView) mMenuView.findViewById(R.id.txt_rule);

        closeBtn.setOnClickListener(itemsOnClick);
        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        mMenuView.setOnTouchListener((v, event) -> {
            int height_top = mMenuView.findViewById(R.id.popup_rule_layout).getTop();
            int height_bottom = mMenuView.findViewById(R.id.popup_rule_layout).getBottom();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y < height_top || y > height_bottom) {
                    dismiss();
                }
            }
            return true;
        });
    }

    public void setRule(String rule) {
        ruleTxt.setText(rule);
    }

}
