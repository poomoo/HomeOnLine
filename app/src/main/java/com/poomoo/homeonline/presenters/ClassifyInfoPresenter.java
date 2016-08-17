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
import com.poomoo.homeonline.ui.activity.ClassifyInfoActivity;
import com.poomoo.model.request.QCategoryIdBO;
import com.poomoo.model.response.RClassifyInfoBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 ClassifyInfoPresenter
 * 描述 分类详情
 * 作者 李苜菲
 * 日期 2016/8/16 15:54
 */
public class ClassifyInfoPresenter extends BasePresenter<ClassifyInfoActivity> {
    @Inject
    public ClassifyInfoPresenter() {

    }

    /**
     * @param categoryId
     */
    public void loadClassify(String categoryId) {
        QCategoryIdBO qCategoryIdBO = new QCategoryIdBO(NetConfig.CLASSINFO, categoryId);
        add(NetWork.getMyApi().getClassifyInfo(qCategoryIdBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RClassifyInfoBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loadClassifyFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RClassifyInfoBO rClassifyInfoBO) {
                        mView.loadClassifySucceed(rClassifyInfoBO);
                    }
                }));

    }
}
