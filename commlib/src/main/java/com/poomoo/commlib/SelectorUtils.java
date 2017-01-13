package com.poomoo.commlib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;

public class SelectorUtils {
    /**
     * 代码生成选择器
     *
     * @param context   当前上下文
     * @param idNormal  默认图片id
     * @param idPressed 触摸时图片id
     * @param idFocused 获得焦点时图片id
     * @param idUnable  没有选中时图片id
     * @return
     */
    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused, int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_focused}, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_window_focused}, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }


    /**
     * 控件选择器
     *
     * @param context   当前上下文
     * @param DNormal  默认图片drawable
     * @param DChecked 选中时时图片drawable
     * @return
     */
    public static StateListDrawable setSelector(Context context, Drawable DNormal, Drawable DChecked) {
        StateListDrawable bg = new StateListDrawable();
//        Drawable normal = idNormal == -1 ? null : ContextCompat.getDrawable(context,idNormal);
//        Drawable pressed = idChecked == -1 ? null : ContextCompat.getDrawable(context,idChecked);
        bg.addState(new int[]{android.R.attr.state_checked, android.R.attr.state_enabled}, DChecked);
        bg.addState(new int[]{android.R.attr.state_enabled}, DNormal);
        bg.addState(new int[]{}, DNormal);
        return bg;
    }
}