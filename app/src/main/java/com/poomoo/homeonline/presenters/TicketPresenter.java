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
import com.poomoo.homeonline.ui.activity.TicketActivity;
import com.poomoo.model.request.QUserIdBO;
import com.poomoo.model.response.RTicketBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 TicketPresenter
 * 描述 卡券
 * 作者 李苜菲
 * 日期 2016/9/5 15:40
 */
public class TicketPresenter extends BasePresenter<TicketActivity> {
    @Inject
    public TicketPresenter() {
    }

    /**
     * 获取优惠券
     *
     * @param userId
     */
    public void getTickets(int userId) {
        QUserIdBO qUserIdBO = new QUserIdBO(NetConfig.TICKETS, userId);
        add(NetWork.getMyApi().getTickets(qUserIdBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RTicketBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RTicketBO rTicketBO) {
                        mView.successful(rTicketBO);
                    }
                }));

    }
}
