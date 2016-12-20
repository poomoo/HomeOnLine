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
package com.poomoo.homeonline.presenters;

import com.poomoo.api.AbsAPICallback;
import com.poomoo.api.ApiException;
import com.poomoo.api.NetConfig;
import com.poomoo.api.NetWork;
import com.poomoo.homeonline.ui.activity.AbroadGlobalActivity;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.response.RGlobalBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 GlobalPresenter
 * 描述 ${TODO}
 * 作者 李苜菲
 * 日期 2016/11/28 10:22
 */
public class GlobalPresenter extends BasePresenter<AbroadGlobalActivity> {
    @Inject
    public GlobalPresenter() {
    }

    public void getGlobalInfo() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.GLOBAL);
        add(NetWork.getMyApi().getGlobal(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RGlobalBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RGlobalBO rGlobalBO) {
                        mView.successful(rGlobalBO);
                    }
                }));
    }
}
