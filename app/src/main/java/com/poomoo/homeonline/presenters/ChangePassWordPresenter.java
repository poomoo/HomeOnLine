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
import com.poomoo.homeonline.ui.activity.ChangePassWordDaggerActivity;
import com.poomoo.model.RUserBO;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QLoginBO;
import com.poomoo.model.request.QRegisterBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 ChangePassWordPresenter
 * 描述 修改密码
 * 作者 李苜菲
 * 日期 2016/8/1 15:24
 */
public class ChangePassWordPresenter extends BasePresenter<ChangePassWordDaggerActivity> {
    @Inject
    public ChangePassWordPresenter() {
    }

    /**
     * 注册
     *
     * @param phoneNum
     * @param passWord
     */
    public void register(String phoneNum, String passWord) {
        QRegisterBO qRegisterBO = new QRegisterBO(NetConfig.REGISTER, phoneNum, passWord);
        add(NetWork.getMyApi().Register(qRegisterBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.registerFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.registerSucceed();
                    }
                }));

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
