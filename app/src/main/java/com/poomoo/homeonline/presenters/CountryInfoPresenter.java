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
import com.poomoo.homeonline.ui.activity.AbroadCountryInfoActivity;
import com.poomoo.model.request.QCountryInfoBO;
import com.poomoo.model.request.QCountryInfoCommodityBO;
import com.poomoo.model.response.RCountryInfoBO;
import com.poomoo.model.response.RListCommodityBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 CountryInfoPresenter
 * 描述 国家馆
 * 作者 李苜菲
 * 日期 2016/11/28 14:26
 */
public class CountryInfoPresenter extends BasePresenter<AbroadCountryInfoActivity> {
    @Inject
    public CountryInfoPresenter() {
    }

    public void getCountryInfo(int countryId) {
        QCountryInfoBO qCountryInfoBO = new QCountryInfoBO(NetConfig.COUNTRYINFO, countryId);
        add(NetWork.getMyApi().getCountryInfo(qCountryInfoBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RCountryInfoBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed();
                    }

                    @Override
                    public void onNext(RCountryInfoBO rCountryInfoBO) {
                        mView.successful(rCountryInfoBO);
                    }
                }));

    }

    public void getCountryInfoCommoditys(int countryId, int index) {
        QCountryInfoCommodityBO qCountryInfoCommodityBO = new QCountryInfoCommodityBO(NetConfig.COUNTRYINFOCOMMODITY, countryId, index);
        add(NetWork.getMyApi().getCountryInfoCommodity(qCountryInfoCommodityBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RListCommodityBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getCommodityFailed();
                    }

                    @Override
                    public void onNext(List<RListCommodityBO> rListCommodityBOs) {
                        mView.getCommoditySuccessful(rListCommodityBOs);
                    }
                }));
    }
}
