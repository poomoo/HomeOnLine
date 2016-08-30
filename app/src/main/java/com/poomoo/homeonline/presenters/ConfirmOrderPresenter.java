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
import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.ui.activity.ConfirmOrderActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QIdBO;
import com.poomoo.model.request.QOrderBO;
import com.poomoo.model.request.QSignBO;
import com.poomoo.model.request.QTransferPriceBO;
import com.poomoo.model.request.QUserIdBO;
import com.poomoo.model.response.ROrderBO;
import com.poomoo.model.response.RReceiptBO;
import com.poomoo.model.response.RSignBO;
import com.poomoo.model.response.RTransferPriceBO;

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
    private final String TAG = getClass().getSimpleName();

    @Inject
    public ConfirmOrderPresenter() {
    }

    /**
     * 默认地址
     *
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
                        mView.getDefaultAddressFailed(e.getMessage());
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
                        mView.getTransferPriceFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RTransferPriceBO rTransferPriceBO) {
                        mView.getTransferPriceSucceed(rTransferPriceBO);
                    }
                }));
    }

    public void sign(String orderId, String commodityName) {
        QSignBO qSignBO = new QSignBO(NetConfig.SIGN, orderId, commodityName);
        add(NetWork.getPayApi().sign(qSignBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RSignBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RSignBO rSignBO) {
                        mView.subSucceed(rSignBO.sign);
                    }
                }));
    }

    /**
     * 提交订单
     *
     * @param OrderBO
     */
    public void putOrder(QOrderBO OrderBO) {
        QOrderBO qOrderBO = new QOrderBO(NetConfig.PUTORDER, OrderBO.order, OrderBO.orderDetailsList);
        add(NetWork.getMyApi().subOrder(qOrderBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ROrderBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.subFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(ROrderBO rOrderBO) {
                        mView.subSucceed(rOrderBO.sign);
                    }
                }));
    }

    /**
     * 验签
     */
    public void checkSign(String resultStatus, String result, String memo) {
        add(NetWork.getPayApi().checkSign(resultStatus, result, memo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.payFailed();
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        if (responseBO.content.equals("success"))
                            mView.paySuccessful();
                        else
                            mView.payFailed();
                    }
                }));
    }

    public void getAddressById(int id) {
        QIdBO qIdBO = new QIdBO(NetConfig.GETADDRESS, id);
        add(NetWork.getMyApi().getAddressById(qIdBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RReceiptBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getAddressFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RReceiptBO rReceiptBO) {
                        mView.getAddressSucceed(rReceiptBO);
                    }
                }));
    }

}
