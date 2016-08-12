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
import com.poomoo.homeonline.ui.activity.CollectActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QCancelCollectionBO;
import com.poomoo.model.request.QPageBO;
import com.poomoo.model.response.RCollectBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 CollectPresenter
 * 描述 ${TODO}
 * 作者 李苜菲
 * 日期 2016/8/11 16:06
 */
public class CollectPresenter extends BasePresenter<CollectActivity> {
    @Inject
    public CollectPresenter() {
    }

    /**
     * 获取收藏列表
     *
     * @param userId
     * @param index
     */
    public void getCollectionList(int userId, int index) {
        QPageBO qPageBO = new QPageBO(NetConfig.COLLECTIONLIST, userId, index);
        add(NetWork.getMyApi().GetCollectionList(qPageBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RCollectBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getListFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RCollectBO> rCollectBOs) {
                        mView.getListSucceed(rCollectBOs);
                    }
                }));
    }

    /**
     * 取消收藏
     *
     * @param ids
     */
    public void cancelCollections(int[] ids) {
        QCancelCollectionBO qCancelCollectionBO = new QCancelCollectionBO(NetConfig.CANCELCOLLECTION, ids);
        add(NetWork.getMyApi().CancelCollection(qCancelCollectionBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.cancelFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.cancelSucceed();
                    }
                }));
    }
}
