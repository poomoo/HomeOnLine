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
import com.poomoo.homeonline.listeners.EvaluateClickListener;
import com.poomoo.homeonline.ui.activity.EvaluateActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QEvaluateBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 EvaluatePresenter
 * 描述 评价
 * 作者 李苜菲
 * 日期 2016/8/24 11:08
 */
public class EvaluatePresenter extends BasePresenter<EvaluateActivity> {
    @Inject
    public EvaluatePresenter() {
    }

    public void evaluate(String orderId, int commodityId, String content, int descriptFit, int qualitySatisfy, int priceRational, int orderDetailId,boolean isAnonymity) {
        QEvaluateBO qEvaluateBO = new QEvaluateBO(NetConfig.EVALUATE, orderId, commodityId, content, descriptFit, qualitySatisfy, priceRational, orderDetailId,isAnonymity);
        add(NetWork.getMyApi().Evaluate(qEvaluateBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.successful();
                    }
                }));
    }

}
