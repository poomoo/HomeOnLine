/**
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.AddPicsAdapter;
import com.poomoo.homeonline.picUtils.Bimp;
import com.poomoo.homeonline.ui.activity.pics.PhotoActivity;
import com.poomoo.homeonline.ui.activity.pics.PhotosActivity;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 FeedBackActivity
 * 描述 反馈
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class FeedBackActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.edt_advice)
    EditText adviceEdt;
    @Bind(R.id.grid_add_pics)
    NoScrollGridView picGrid;

    private AddPicsAdapter addPicsAdapter;

    private final String image_capture_path = Environment.getExternalStorageDirectory() + "/" + "homeOnLine.temp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_feed_back;
    }

    private void init() {
        setBack();

        picGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        addPicsAdapter = new AddPicsAdapter(this);
        addPicsAdapter.update();
        picGrid.setAdapter(addPicsAdapter);
        picGrid.setOnItemClickListener(this);
    }

    /**
     * 提交
     *
     * @param view
     */
    public void subAdvice(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == Bimp.bmp.size()) {
            openActivity(PhotosActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("ID", position);
            openActivity(PhotoActivity.class, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        addPicsAdapter.update();
    }
}

