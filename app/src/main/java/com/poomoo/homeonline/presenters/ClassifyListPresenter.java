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
import com.poomoo.homeonline.ui.activity.ClassifyListActivity;
import com.poomoo.model.request.QClassifyListBO;
import com.poomoo.model.response.RListCommodityBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 ClassifyListPresenter
 * 描述 分类商品列表
 * 作者 李苜菲
 * 日期 2016/8/17 11:43
 */
public class ClassifyListPresenter extends BasePresenter<ClassifyListActivity> {
    @Inject
    public ClassifyListPresenter() {
    }

    /**
     * 获取分类商品
     *
     * @param categoryId
     * @param index
     */
    public void getCommodity(String categoryId, int index) {
        QClassifyListBO qClassifyListBO = new QClassifyListBO(NetConfig.CLASSCOMMODITYLIST, categoryId, index);
        add(NetWork.getMyApi().getClassifyCommodityList(qClassifyListBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RListCommodityBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RListCommodityBO> rListCommodityBOs) {
                        mView.succeed(rListCommodityBOs);
                    }
                }));
    }
}
