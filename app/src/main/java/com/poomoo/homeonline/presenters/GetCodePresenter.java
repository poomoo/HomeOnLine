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
import com.poomoo.homeonline.ui.activity.GetCodeActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QCheckCodeBO;
import com.poomoo.model.request.QCodeBO;
import com.poomoo.model.request.QImgCodeBO;
import com.poomoo.model.request.QUpdateInfoBO;
import com.poomoo.model.response.RImgCodeBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 GetCodePresenter
 * 描述 ${TODO}
 * 作者 李苜菲
 * 日期 2016/8/1 14:01
 */
public class GetCodePresenter extends BasePresenter<GetCodeActivity> {
    @Inject
    public GetCodePresenter() {
    }

    /**
     * 获取图片验证码
     *
     * @param phoneNum
     */
    public void getImgCode(String phoneNum) {
        QImgCodeBO qCodeBO = new QImgCodeBO(NetConfig.IMGCODE, phoneNum);
        add(NetWork.getMyApi().GetImgCode(qCodeBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RImgCodeBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RImgCodeBO rImgCodeBO) {
                        mView.getImgCodeSucceed(rImgCodeBO);
                    }
                }));
    }

    /**
     * 获取验证码
     *
     * @param phoneNum
     * @param flag
     */
    public void getCode(String phoneNum, boolean flag, String imgCode) {
        QCodeBO qCodeBO = new QCodeBO(NetConfig.CODE, phoneNum, flag, imgCode);
        add(NetWork.getMyApi().GetCode(qCodeBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.getCodeSucceed();
                    }
                }));
    }

    /**
     * 校验验证码
     *
     * @param phoneNum
     * @param code
     */
    public void checkCode(String phoneNum, String code) {
        QCheckCodeBO qCheckCodeBO = new QCheckCodeBO(NetConfig.CHECK, phoneNum, code);
        add(NetWork.getMyApi().CheckCode(qCheckCodeBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.checkCodeSucceed();
                    }
                }));
    }

    /**
     * 修改个人信息
     *
     * @param userId
     * @param nickName
     * @param phoneNum
     */
    public void updateTel(int userId, String nickName, String phoneNum) {
        QUpdateInfoBO qUpdateInfoBO = new QUpdateInfoBO(NetConfig.UPDATEINFO, userId, nickName, phoneNum);
        add(NetWork.getMyApi().UpdateUserInfo(qUpdateInfoBO)
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
}
