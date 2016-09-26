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
import com.poomoo.homeonline.ui.activity.MyTicketActivity;
import com.poomoo.model.request.QMyTicketBO;
import com.poomoo.model.response.TicketBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 MyTicketPresenter
 * 描述 我的优惠券
 * 作者 李苜菲
 * 日期 2016/9/23 15:00
 */
public class MyTicketPresenter extends BasePresenter<MyTicketActivity> {
    @Inject
    public MyTicketPresenter() {
    }

    public void getTickets(int userId, String totalPrice, int[] commodityIds, int[] commodityDetailIds) {
        QMyTicketBO qMyTicketBO = new QMyTicketBO(NetConfig.MYTICKETS, userId, totalPrice, commodityIds, commodityDetailIds);
        add(NetWork.getMyApi().getMyTickets(qMyTicketBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<TicketBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TicketBO> ticketBOs) {
                        mView.successful(ticketBOs);
                    }
                }));
    }
}
