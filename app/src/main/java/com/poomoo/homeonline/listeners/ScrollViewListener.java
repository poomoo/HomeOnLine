package com.poomoo.homeonline.listeners;

import com.poomoo.homeonline.ui.custom.MyScrollView;

/**
 * 类名 ScrollViewListener
 * 描述 自定义ScrollView滑动事件监听
 * 作者 李苜菲
 * 日期 2016/7/19 11:26
 */
public interface ScrollViewListener {
    void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);
}
