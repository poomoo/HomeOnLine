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
import com.poomoo.homeonline.ui.activity.OnSaleActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.request.QCodeBO;
import com.poomoo.model.request.QGetTicketBO;
import com.poomoo.model.response.ROnSaleBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 OnSalePresenter
 * 描述 优惠券专区
 * 作者 李苜菲
 * 日期 2016/11/1 10:32
 */
public class OnSalePresenter extends BasePresenter<OnSaleActivity> {
    @Inject
    public OnSalePresenter() {
    }

    /**
     * 优惠专区详情
     */
    public void getOnSaleInfo() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.ONSALE);
        add(NetWork.getMyApi().GetOnSaleInfo(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ROnSaleBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ROnSaleBO rOnSaleBO) {
                        mView.successful(rOnSaleBO);
                    }
                }));
    }

    /**
     * 领取优惠券
     *
     * @param userId
     * @param money
     */
    public void getTicket(Integer userId, String money) {
        QGetTicketBO qGetTicketBO = new QGetTicketBO(NetConfig.GETTICKET, money, userId);
        add(NetWork.getMyApi().getTicket(qGetTicketBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getTicketFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.getTicketSuccessful();
                    }
                }));
    }
}
