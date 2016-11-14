package com.poomoo.homeonline.ui.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;

public class VerticalViewPager extends ViewPager {

    private static final String TAG = "VerticalViewPager";

    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // The majority of the magic happens here
        setPageTransformer(true, new VerticalPageTransformer());
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        ScrollView scrollView = (ScrollView) v.findViewById(R.id.scroll_commodity_info);
        WebView webView = (WebView) v.findViewById(R.id.web_commodity_info);
        RecyclerView recyclerView1 = (RecyclerView) v.findViewById(R.id.recycler_commodity_details);
        RecyclerView recyclerView2 = (RecyclerView) v.findViewById(R.id.recycler_commodity_recommend);
        if (dx == 0)
            return false;
        if (webView != null && webView.getVisibility() == View.VISIBLE) {
            return webView.canScrollVertically(-dx);
        } else if (recyclerView1 != null && recyclerView1.getVisibility() == View.VISIBLE) {
            return recyclerView1.canScrollVertically(-dx);
        } else if (recyclerView2 != null && recyclerView2.getVisibility() == View.VISIBLE) {
            return recyclerView2.canScrollVertically(-dx);
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

}
