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
import com.poomoo.homeonline.ui.activity.LogInActivity;
import com.poomoo.model.RUserBO;
import com.poomoo.model.request.QLoginBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 LoginPresenter
 * 描述 ${TODO}
 * 作者 李苜菲
 * 日期 2016/8/1 11:14
 */
public class LoginPresenter extends BasePresenter<LogInActivity> {
    @Inject
    public LoginPresenter() {
    }

    /**
     * 登录
     *
     * @param tel
     * @param password
     */
    public void login(String tel, String password) {
        QLoginBO qLoginBO = new QLoginBO(NetConfig.LOGIN, tel, password);
        mSubscriptions.add(NetWork.getMyApi().Login(qLoginBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RUserBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loginFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RUserBO rUserBO) {
                        mView.loginSucceed(rUserBO);
                    }
                }));
    }
}
