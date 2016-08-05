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
import com.poomoo.model.request.QCommodityInfoBO;
import com.poomoo.model.response.RCommodityInfoBO;

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

    public void getCommodity(int commodityId, int commodityDetailId) {
        QCommodityInfoBO qCommodityInfoBO = new QCommodityInfoBO(NetConfig.COMMODITY, commodityId, commodityDetailId);
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
}
