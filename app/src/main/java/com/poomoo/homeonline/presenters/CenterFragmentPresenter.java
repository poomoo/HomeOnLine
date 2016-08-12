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
import com.poomoo.homeonline.ui.fragment.CenterFragment;
import com.poomoo.homeonline.ui.fragment.MainFragment;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.response.RZoneBO;

import java.util.List;

import javax.inject.Inject;

import rx.schedulers.Schedulers;

/**
 * 类名 MainFragmentPresenter
 * 描述 个人中心Presenter
 * 作者 李苜菲
 * 日期 2016/8/2 14:50
 */
public class CenterFragmentPresenter extends BasePresenter<CenterFragment> {
    @Inject
    public CenterFragmentPresenter() {
    }

    /**
     * 获取区域信息
     */
    public void getZoneInfo() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.GETZONEINFO);
        add(NetWork.getMyApi().GetZoneInfo(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new AbsAPICallback<List<RZoneBO>>() {
                    @Override
                    protected void onError(ApiException e) {

                    }

                    @Override
                    public void onNext(List<RZoneBO> rZoneBOs) {
                        mView.getZoneInfo(rZoneBOs);
                    }
                }));
    }
}
