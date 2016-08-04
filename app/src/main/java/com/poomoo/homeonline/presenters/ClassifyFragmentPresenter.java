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
import com.poomoo.homeonline.ui.fragment.ClassifyFragment;
import com.poomoo.homeonline.ui.fragment.MainFragment;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.response.RClassifyBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 MainFragmentPresenter
 * 描述 分类Presenter
 * 作者 李苜菲
 * 日期 2016/8/2 14:50
 */
public class ClassifyFragmentPresenter extends BasePresenter<ClassifyFragment> {
    @Inject
    public ClassifyFragmentPresenter() {
    }

    /**
     * 获取商品分类
     */
    public void getClassify() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.CLASSIFY);
        add(NetWork.getMyApi().GetClassify(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RClassifyBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loadClassifyFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RClassifyBO> rClassifyBOs) {
                        mView.loadClassifySucceed(rClassifyBOs);
                    }
                }));
    }
}
