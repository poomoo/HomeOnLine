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

import com.jakewharton.rxbinding.widget.AdapterViewNothingSelectionEvent;
import com.poomoo.api.AbsAPICallback;
import com.poomoo.api.ApiException;
import com.poomoo.api.NetConfig;
import com.poomoo.api.NetWork;
import com.poomoo.homeonline.ui.activity.ConfirmOrderActivity;
import com.poomoo.model.request.QTransferPriceBO;
import com.poomoo.model.request.QUserIdBO;
import com.poomoo.model.response.RReceiptBO;
import com.poomoo.model.response.RTransferPriceBO;

import java.net.NetworkInterface;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 ConfirmOrderPresenter
 * 描述 确认订单
 * 作者 李苜菲
 * 日期 2016/8/17 15:42
 */
public class ConfirmOrderPresenter extends BasePresenter<ConfirmOrderActivity> {
    @Inject
    public ConfirmOrderPresenter() {
    }

    /**
     * 默认地址
     * @param userId
     */
    public void getDefaultAddress(int userId) {
        QUserIdBO qUserIdBO = new QUserIdBO(NetConfig.DEFAULTADDRESS, userId);
        add(NetWork.getMyApi().getDefaultAddress(qUserIdBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RReceiptBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RReceiptBO rReceiptBO) {
                        mView.getDefaultAddressSucceed(rReceiptBO);
                    }
                }));
    }

    /**
     * 获取运费
     *
     * @param userId
     * @param deliveryId
     * @param totalPrice
     */
    public void getTransferPrice(int userId, int deliveryId, double totalPrice) {
        QTransferPriceBO qTransferPriceBO = new QTransferPriceBO(NetConfig.TRANSFERPRICE, userId, deliveryId, totalPrice);
        add(NetWork.getMyApi().getTransferPrice(qTransferPriceBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RTransferPriceBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RTransferPriceBO rTransferPriceBO) {
                        mView.getTransferPriceSucceed(rTransferPriceBO);
                    }
                }));
    }
}
