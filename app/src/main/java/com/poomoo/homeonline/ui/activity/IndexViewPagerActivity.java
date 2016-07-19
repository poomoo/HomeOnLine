package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ViewPagerAdapter;
import com.poomoo.homeonline.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名 IndexViewPagerActivity
 * 描述 引导页
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class IndexViewPagerActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private TextView clickInTxt;

    // 引导图片资源
    private static final int[] pics = {R.drawable.index1, R.drawable.index2, R.drawable.index3};
    private static final int lenth = pics.length;

    // 底部小店图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;
    private LinearLayout ll;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickInTxt = (TextView) findViewById(R.id.txt_clickIn);
        views = new ArrayList<>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // 初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setAdjustViewBounds(true);
            // 防止图片不能填满屏幕
            iv.setScaleType(ScaleType.CENTER_CROP);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        vp = (ViewPager) findViewById(R.id.viewpager_viewpager);
        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);

//        views.get(pics.length - 1).setOnClickListener(this);
        // 初始化底部小点
        initDots();

        clickInTxt.setOnClickListener(this);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.viewpager;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    private void initDots() {
        ll = (LinearLayout) findViewById(R.id.viewpager_ll);
        dots = new ImageView[lenth];

        // 循环取得小点图片
        for (int i = 0; i < lenth; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);// 都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(true);// 设置为白色，即选中状态
    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= lenth) {
            return;
        }
        vp.setCurrentItem(position);
    }

    /**
     * 当前引导小点的选中
     */
    private void setCurDot(int position) {
        if (position < 0 || position > lenth - 1 || currentIndex == position) {
            return;
        }
        if (position == lenth - 1)
            ll.setVisibility(View.INVISIBLE);
        else
            ll.setVisibility(View.VISIBLE);
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurDot(arg0);
        if (arg0 == pics.length - 1)
            clickInTxt.setVisibility(View.VISIBLE);
        else
            clickInTxt.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        openActivity(MainActivity.class);
        finish();
    }
}
