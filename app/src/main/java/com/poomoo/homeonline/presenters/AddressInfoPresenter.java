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

import android.net.Network;

import com.poomoo.api.AbsAPICallback;
import com.poomoo.api.ApiException;
import com.poomoo.api.NetConfig;
import com.poomoo.api.NetWork;
import com.poomoo.homeonline.ui.activity.AddressInfoActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QAddressBO;
import com.poomoo.model.request.QIdBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 AddressInfoPresenter
 * 描述 地址详情
 * 作者 李苜菲
 * 日期 2016/8/15 10:05
 */
public class AddressInfoPresenter extends BasePresenter<AddressInfoActivity> {
    @Inject
    public AddressInfoPresenter() {
    }

    /**
     * 新建地址
     *
     * @param userId
     * @param consigneeName
     * @param consigneeTel
     * @param postCode
     * @param provinceId
     * @param cityId
     * @param areaId
     * @param streetName
     * @param isDefault
     */
    public void newAddress(int userId, String consigneeName, String consigneeTel, String postCode, int provinceId, int cityId, int areaId, String streetName, boolean isDefault) {
        QAddressBO qAddressBO = new QAddressBO(NetConfig.NEWADDRESS, -1, userId, consigneeName, consigneeTel, postCode, provinceId, cityId, areaId, streetName, isDefault);
        add(NetWork.getMyApi().NewAddress(qAddressBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.newSucceed();
                    }
                }));
    }

    /**
     * 修改地址
     *
     * @param id
     * @param userId
     * @param consigneeName
     * @param consigneeTel
     * @param postCode
     * @param provinceId
     * @param cityId
     * @param areaId
     * @param streetName
     * @param isDefault
     */
    public void updateAddress(int id, int userId, String consigneeName, String consigneeTel, String postCode, int provinceId, int cityId, int areaId, String streetName, boolean isDefault) {
        QAddressBO qAddressBO = new QAddressBO(NetConfig.UPDATEADDRESS, id, userId, consigneeName, consigneeTel, postCode, provinceId, cityId, areaId, streetName, isDefault);
        add(NetWork.getMyApi().UpdateAddress(qAddressBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.updateSucceed();
                    }
                }));
    }

    /**
     * 删除地址
     *
     * @param id
     */
    public void deleteAddress(int id) {
        QIdBO qIdBO = new QIdBO(NetConfig.DELETEADDRESS, id);
        add(NetWork.getMyApi().DeleteAddress(qIdBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.deleteSucceed();
                    }
                }));
    }


}
