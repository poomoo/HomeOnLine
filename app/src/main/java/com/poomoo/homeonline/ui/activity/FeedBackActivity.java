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
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.AddPicsAdapter;
import com.poomoo.homeonline.picUtils.Bimp;
import com.poomoo.homeonline.presenters.FeedBackPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.activity.pics.PhotoActivity;
import com.poomoo.homeonline.ui.activity.pics.PhotosActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.model.response.RUploadUrlBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 FeedBackActivity
 * 描述 反馈
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class FeedBackActivity extends BaseDaggerActivity<FeedBackPresenter> implements AdapterView.OnItemClickListener {
    @Bind(R.id.edt_advice)
    EditText adviceEdt;
    @Bind(R.id.grid_add_pics)
    NoScrollGridView picGrid;
    @Bind(R.id.btn_submit)
    Button submitBtn;

    private AddPicsAdapter addPicsAdapter;
    private int len = 0;
    private int index = 0;
    private String suggestion = "";
    private String url = "";


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

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        setBack();
        getProgressBar();

        picGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        addPicsAdapter = new AddPicsAdapter(this);
        addPicsAdapter.update();
        picGrid.setAdapter(addPicsAdapter);
        picGrid.setOnItemClickListener(this);

        adviceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                if (temp.length() > 0)
                    submitBtn.setEnabled(true);
                else
                    submitBtn.setEnabled(false);
            }
        });
    }

    /**
     * 提交
     *
     * @param view
     */
    public void subAdvice(View view) {
        suggestion = adviceEdt.getText().toString().trim();
        mProgressBar.setVisibility(View.VISIBLE);
        len = Bimp.files.size();
        if (len > 0)
            mPresenter.uploadPic(Bimp.files.get(index++));
        else
            mPresenter.submitFeedBack(application.getUserId(), suggestion, "");
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                if (index == len)
                    mPresenter.submitFeedBack(application.getUserId(), suggestion, url);
                else
                    mPresenter.uploadPic(Bimp.files.get(index++));
            }
            super.handleMessage(msg);
        }
    };

    public void upLoadSucceed(RUploadUrlBO rUploadUrlBO) {
        url += rUploadUrlBO.picUrl + "#";

        Message message = new Message();
        message.what = 1;
        myHandler.sendMessage(message);
    }

    public void submitSucceed() {
        mProgressBar.setVisibility(View.GONE);
        MyUtils.showToast(getApplicationContext(), "感谢您提供的宝贵意见!");
        finish();
    }

    public void failed(String msg) {
        mProgressBar.setVisibility(View.GONE);
        MyUtils.showToast(getApplicationContext(), msg);
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

