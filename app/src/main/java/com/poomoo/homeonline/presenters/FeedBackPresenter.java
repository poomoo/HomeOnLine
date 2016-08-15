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
import com.poomoo.homeonline.ui.activity.FeedBackActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QFeedBackBO;
import com.poomoo.model.response.RUploadUrlBO;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 FeedBackPresenter
 * 描述 反馈
 * 作者 李苜菲
 * 日期 2016/8/15 13:45
 */
public class FeedBackPresenter extends BasePresenter<FeedBackActivity> {
    @Inject
    public FeedBackPresenter() {
    }

    /**
     * 上传图片
     *
     * @param file
     */
    public void uploadPic(File file) {
        Map<String, RequestBody> map = new HashMap<>();
        if (file != null) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            map.put("file\"; filename=\"" + file.getName() + "", fileBody);
        }

        mSubscriptions.add(NetWork.getUploadApi().uploadPic(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RUploadUrlBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RUploadUrlBO rUploadUrlBO) {
                        mView.upLoadSucceed(rUploadUrlBO);
                    }
                }));

    }

    /**
     * 提交反馈信息
     *
     * @param userId
     * @param feedbackMessage
     * @param feedbackUrl
     */
    public void submitFeedBack(int userId, String feedbackMessage, String feedbackUrl) {
        QFeedBackBO qFeedBackBO = new QFeedBackBO(NetConfig.FEEDBACK, userId, feedbackMessage, feedbackUrl);
        add(NetWork.getMyApi().FeedBack(qFeedBackBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.submitSucceed();
                    }
                }));
    }
}
