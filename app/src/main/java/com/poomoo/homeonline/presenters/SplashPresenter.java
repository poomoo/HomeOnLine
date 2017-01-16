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
import com.poomoo.homeonline.ui.activity.SplashActivity;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.request.QTypeBO;
import com.poomoo.model.request.QVersionBO;
import com.poomoo.model.response.RIndexBO;
import com.poomoo.model.response.RVersionBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 SplashPresenter
 * 描述 启动界面
 * 作者 李苜菲
 * 日期 2016/9/13 11:10
 */
public class SplashPresenter extends BasePresenter<SplashActivity> {
    @Inject
    public SplashPresenter() {
    }

    /**
     * 获取引导页图片
     */
    public void getIndex() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.INDEX);
        NetWork.getMyApi().getIndex(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RIndexBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RIndexBO> rIndexBOs) {
                        mView.successful(rIndexBOs);
                    }
                });
    }

    /**
     * 检查更新
     */
    public void checkUpdate() {
        QVersionBO qVersionBO = new QVersionBO(NetConfig.UPDATE, 1);
        add(NetWork.getMyApi().CheckUpdate(qVersionBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RVersionBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.checkUpdateFailed();
                    }

                    @Override
                    public void onNext(RVersionBO rVersionBO) {
                        mView.checkUpdateSuccessful(rVersionBO);
                    }
                }));
    }
}
