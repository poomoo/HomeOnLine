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
import com.poomoo.homeonline.ui.activity.CommodityInfoActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QAddCartBO;
import com.poomoo.model.request.QCommodityInfoBO;
import com.poomoo.model.request.QHistory;
import com.poomoo.model.request.QSpecificationBO;
import com.poomoo.model.response.RCommodityInfoBO;
import com.poomoo.model.response.RSpecificationBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 CommodityPresenter
 * 描述 商品详情
 * 作者 李苜菲
 * 日期 2016/8/5 17:20
 */
public class CommodityPresenter extends BasePresenter<CommodityInfoActivity> {
    @Inject
    public CommodityPresenter() {
    }

    public void getCommodity(int commodityId, Integer commodityDetailId, int commodityType) {
        QCommodityInfoBO qCommodityInfoBO = new QCommodityInfoBO(NetConfig.COMMODITY, commodityId, commodityDetailId, commodityType);
        add(NetWork.getMyApi().GetCommodityInfo(qCommodityInfoBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RCommodityInfoBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getCommodityInfoFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RCommodityInfoBO rCommodityInfoBO) {
                        mView.getCommodityInfoSucceed(rCommodityInfoBO);
                    }
                }));
    }

    /**
     * @param commodityId
     * @param paramterValueIds
     */
    public void getCommodityInfoBySpecification(int commodityId, Integer[] paramterValueIds) {
        QSpecificationBO qSpecificationBO = new QSpecificationBO(NetConfig.SPECIFICATION, commodityId, paramterValueIds);
        add(NetWork.getMyApi().GetCommodityInfoBySpecification(qSpecificationBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RSpecificationBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getSpecificationFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RSpecificationBO rSpecificationBO) {
                        mView.getSpecificationSucceed(rSpecificationBO);
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
    public void addToCart(int userId, int commodityId, String commodityName, int commodityType, int commodityNum, String listPic, int commodityDetailId) {
        QAddCartBO qAddCartBO = new QAddCartBO(NetConfig.ADDCART, userId, -1, -1, commodityDetailId, listPic, commodityNum, -1, commodityType, commodityId, commodityName);
        add(NetWork.getMyApi().AddToCart(qAddCartBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.addToCartFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.addToCartSucceed(responseBO.msg);
                    }
                }));
    }

    /**
     * 添加历史记录
     *
     * @param userId
     * @param commodityId
     * @param commodityType
     */
    public void addHistory(int userId, int commodityId, int commodityType) {
        QHistory qHistory = new QHistory(NetConfig.ADDHISTORY, userId, commodityId, commodityType);
        add(NetWork.getMyApi().AddHistory(qHistory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {

                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {

                    }
                }));
    }
}
