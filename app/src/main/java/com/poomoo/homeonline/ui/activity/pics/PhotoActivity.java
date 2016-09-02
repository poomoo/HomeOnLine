package com.poomoo.homeonline.ui.activity.pics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.picUtils.Bimp;
import com.poomoo.homeonline.picUtils.FileUtils;
import com.poomoo.homeonline.ui.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends BaseActivity {

    private ArrayList<View> listViews = null;
    private ViewPager pager;
    private RelativeLayout photoRlayout;
    private MyPageAdapter adapter;
    private int count;

    //    public List<Bitmap> bmp = new ArrayList<>();
    public List<String> drr = new ArrayList<>();
    public List<String> del = new ArrayList<>();
    public List<File> files = new ArrayList<>();
    public int max;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoRlayout = (RelativeLayout) findViewById(R.id.rLayout_photo);
        photoRlayout.setBackgroundColor(0x70000000);

//        for (int i = 0; i < Bimp.bmp.size(); i++) {
//            bmp.add(Bimp.bmp.get(i));
//        }
        for (int i = 0; i < Bimp.drr.size(); i++) {
            drr.add(Bimp.drr.get(i));
        }
        for (int i = 0; i < Bimp.files.size(); i++) {
            files.add(Bimp.files.get(i));
        }
        max = Bimp.max;

        Button photo_bt_del = (Button) findViewById(R.id.btn_del);
        photo_bt_del.setOnClickListener(v -> {
            if (listViews.size() == 1) {
//                Bimp.bmp.clear();
                Bimp.drr.clear();
                Bimp.files.clear();
                Bimp.max = 0;
                FileUtils.deleteDir();
                finish();
            } else {
                if (!drr.get(count).startsWith("http")) {
                    String newStr = drr.get(count).substring(
                            drr.get(count).lastIndexOf("/") + 1,
                            drr.get(count).lastIndexOf("."));
                    del.add(newStr);
                }
                drr.remove(count);
                if (files.size() > 0)
                    files.remove(count);
//                bmp.remove(count);
                max--;
                pager.removeAllViews();
                listViews.remove(count);
                adapter.setListViews(listViews);
                adapter.notifyDataSetChanged();
//                Bimp.bmp = bmp;
                Bimp.drr = drr;
                Bimp.max = max;
                Bimp.files = files;
                for (int i = 0; i < del.size(); i++) {
                    FileUtils.delFile(del.get(i) + ".JPEG");
                }
            }
        });

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.addOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < drr.size(); i++) {
            initListViews(drr.get(i));//
        }

        adapter = new MyPageAdapter(listViews);// 构造adapter
        pager.setAdapter(adapter);// 设置适配器
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(id);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_photo;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_bucket;
    }

    private void initListViews(String bm) {
        if (listViews == null)
            listViews = new ArrayList<>();
        ImageView img = new ImageView(this);// 构造textView对象
        img.setBackgroundColor(0xff000000);
//        img.setImageBitmap(bm);
        img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        Glide.with(this).load(bm).into(img);
        listViews.add(img);// 添加view
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0) {// 页面选择响应函数
            count = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

        }

        public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

        }
    };

    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;// content

        private int size;// 页数

        public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
            // 初始化viewpager的时候给的一个页面
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {// 返回数量
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
            ((ViewPager) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {// 返回view对象
            try {
                ((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}
