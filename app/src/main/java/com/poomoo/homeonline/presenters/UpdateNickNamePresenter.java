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
import com.poomoo.homeonline.ui.activity.UpdateNickNameActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QUpdateInfoBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 UpdateNickNamePresenter
 * 描述 修改昵称
 * 作者 李苜菲
 * 日期 2016/8/16 10:30
 */
public class UpdateNickNamePresenter extends BasePresenter<UpdateNickNameActivity> {
    @Inject
    public UpdateNickNamePresenter() {
    }

    /**
     * 修改昵称
     *
     * @param userId
     * @param nickName
     * @param tel
     */
    public void updateNickName(int userId, String nickName, String tel) {
        QUpdateInfoBO qUpdateInfoBO = new QUpdateInfoBO(NetConfig.UPDATEINFO, userId, nickName, tel);
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
                        mView.succeed();
                    }
                }));
    }
}
