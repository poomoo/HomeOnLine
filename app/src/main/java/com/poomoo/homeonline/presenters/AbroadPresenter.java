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
import com.poomoo.homeonline.ui.activity.AbroadActivity;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.response.RAbroadBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 AbroadPresenter
 * 描述 跨境
 * 作者 李苜菲
 * 日期 2016/11/7 16:19
 */
public class AbroadPresenter extends BasePresenter<AbroadActivity> {
    @Inject
    public AbroadPresenter() {
    }

    public void getAbroad() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.ABROAD);
        NetWork.getMyApi().getAbroad(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RAbroadBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RAbroadBO rAbroadBO) {
                        mView.successful(rAbroadBO);
                    }
                });
    }

//    public void getSubCommodity(int id) {
//        QIdBO qIdBO = new QIdBO(NetConfig.ABROADSUBCOMMODITY, id);
//        add(NetWork.getMyApi().getSubCommodity(qIdBO)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new AbsAPICallback<List<RAbroadCommodityBO>>() {
//                    @Override
//                    protected void onError(ApiException e) {
//                        mView.failed(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(List<RAbroadCommodityBO> rAbroadCommodityBOs) {
//                        mView.getSubCommodity(rAbroadCommodityBOs);
//                    }
//                }));
//    }
}
