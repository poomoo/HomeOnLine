package com.poomoo.homeonline.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 类名 NoScrollGridView
 * 描述 自定义的GridView 用于嵌套在scrollview里，避免显示不全
 * 作者 李苜菲
 * 日期 2016/7/19 11:37
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
