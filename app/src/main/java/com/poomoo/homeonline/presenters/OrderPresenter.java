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
import com.poomoo.homeonline.ui.fragment.MyOrdersFragment;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QCancelOrderBO;
import com.poomoo.model.request.QOrderId;
import com.poomoo.model.request.QOrderListBO;
import com.poomoo.model.response.ROrderListBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 OrderPresenter
 * 描述 订单列表
 * 作者 李苜菲
 * 日期 2016/8/19 9:21
 */
public class OrderPresenter extends BasePresenter<MyOrdersFragment> {
    @Inject
    public OrderPresenter() {
    }

    /**
     * 获取订单列表
     *
     * @param userId
     * @param state
     * @param index
     */
    public void getOrderList(int userId, int state, int index) {
        QOrderListBO qOrderListBO = new QOrderListBO(NetConfig.ORDERLIST, userId, state, index);
        add(NetWork.getMyApi().getOrderList(qOrderListBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<ROrderListBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ROrderListBO> rOrderListBOs) {
                        mView.succeed(rOrderListBOs);
                    }
                }));
    }

    /**
     * 取消订单
     *
     * @param userId
     * @param orderId
     */
    public void cancelOrder(int userId, String orderId) {
        QCancelOrderBO qCancelOrderBO = new QCancelOrderBO(NetConfig.CANCELORDER, userId, orderId);
        add(NetWork.getMyApi().cancelOrder(qCancelOrderBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.successful();
                    }
                }));
    }


    /**
     * 确认收货
     *
     * @param orderId
     */
    public void confirm(String orderId) {
        QOrderId qOrderId = new QOrderId(NetConfig.CONFIRM, orderId);
        add(NetWork.getMyApi().confirm(qOrderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.successful();
                    }
                }));
    }

    /**
     * 删除订单
     *
     * @param userId
     * @param orderId
     */
    public void deleteOrder(Integer userId, String orderId) {
        QCancelOrderBO qCancelOrderBO = new QCancelOrderBO(NetConfig.DELETEORDER, userId, orderId);
        add(NetWork.getMyApi().cancelOrder(qCancelOrderBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.successful();
                    }
                }));
    }
}
