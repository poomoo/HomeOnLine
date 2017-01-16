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
 * Copyright (c) 2017. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.presenters;

import com.poomoo.api.AbsAPICallback;
import com.poomoo.api.ApiException;
import com.poomoo.api.NetConfig;
import com.poomoo.api.NetWork;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QAddCartBO;
import com.poomoo.model.request.QTypeBO;
import com.poomoo.model.response.RNewSpecialBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 NewSpecialPresenter
 * 描述 新增专题
 * 作者 李苜菲
 * 日期 2017/1/16 14:13
 */
public class NewSpecialPresenter extends BasePresenter<BaseDaggerActivity> {
    @Inject
    public NewSpecialPresenter() {
    }

    public void getInfo(int type, int categoryId) {
        QTypeBO qTypeBO = new QTypeBO(NetConfig.NEWSPECIAL, type, categoryId);
        add(NetWork.getMyApi().getSpecialInfo(qTypeBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RNewSpecialBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getInfoFailed();
                    }

                    @Override
                    public void onNext(RNewSpecialBO rNewSpecialBO) {
                        mView.getInfoSuccessful(rNewSpecialBO);
                    }
                }));
    }

    /**
     * 添加到购物车
     *
     * @param commodityId
     * @param commodityName
     * @param commodityType
     * @param commodityNum
     * @param listPic
     * @param commodityDetailId
     */
    public void addToCart(int userId, int newActivityId, int commodityId, String commodityName, int commodityType, int commodityNum, String listPic, int commodityDetailId, Integer rushPurchaseId) {
        QAddCartBO qAddCartBO = new QAddCartBO(NetConfig.ADDCART, userId, -1, newActivityId, commodityDetailId, listPic, commodityNum, rushPurchaseId, commodityType, commodityId, commodityName);
        add(NetWork.getMyApi().AddToCart(qAddCartBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.addToCartFailed();
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.addToCartSucceed();
                    }
                }));
    }
}
