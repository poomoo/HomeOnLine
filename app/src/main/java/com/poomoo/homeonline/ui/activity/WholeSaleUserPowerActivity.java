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
 * Copyright (c) 2016. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;

/**
 * 类名 WholeSaleUserPowerActivity
 * 描述 集采用户权限
 * 作者 李苜菲
 * 日期 2016/12/27 10:17
 */
public class WholeSaleUserPowerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBack();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_wholesale_user_power;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_wholeSale1;
    }

    public void toWholeSaleRegister(View view) {

    }
}
