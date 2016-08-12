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
import com.poomoo.homeonline.ui.activity.ScanHistoryActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QCancelCollectionBO;
import com.poomoo.model.request.QPageBO;
import com.poomoo.model.request.QUserIdBO;
import com.poomoo.model.response.RListCommodityBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 ScanHistoryPresenter
 * 描述 浏览足迹
 * 作者 李苜菲
 * 日期 2016/8/12 10:13
 */
public class ScanHistoryPresenter extends BasePresenter<ScanHistoryActivity> {
    @Inject
    public ScanHistoryPresenter() {
    }

    /**
     * 获取浏览历史信息
     *
     * @param userId
     */
    public void getHistory(int userId, int index) {
        QPageBO qPageBO = new QPageBO(NetConfig.GETHISTORY, userId, index);
        add(NetWork.getMyApi().GetHistory(qPageBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RListCommodityBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getListFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RListCommodityBO> rListCommodityBOs) {
                        mView.getListSucceed(rListCommodityBOs);
                    }
                }));
    }

    /**
     * 删除当前显示的历史记录
     *
     * @param ids
     */
    public void deleteHistory(int[] ids) {
        QCancelCollectionBO qCancelCollectionBO = new QCancelCollectionBO(NetConfig.DELETEHISTORY, ids);
        add(NetWork.getMyApi().DeleteHistory(qCancelCollectionBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.deleteFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.deleteSucceed();
                    }
                }));
    }
}
