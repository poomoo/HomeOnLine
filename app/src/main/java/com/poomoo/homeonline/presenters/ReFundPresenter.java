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
import com.poomoo.homeonline.ui.activity.ReFundActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QReFundBO;
import com.poomoo.model.response.RReFundBO;
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
 * 类名 ReFundPresenter
 * 描述 退款退货
 * 作者 李苜菲
 * 日期 2016/8/22 15:39
 */
public class ReFundPresenter extends BasePresenter<ReFundActivity> {
    @Inject
    public ReFundPresenter() {
    }

    /**
     * @param commodityId
     * @param commodityDetailId
     * @param orderId
     * @param orderDetailId
     * @param userId
     * @param returnReason//退货原因
     * @param returnExplain//退货说明
     * @param returnProof//退货凭证
     * @param returnType//退货类型
     * @param returnNum//退货数量
     * @param goodsState//货物状态
     * @param returnMoney//退款金额
     */
    public void subReFund(String commodityId, String commodityDetailId, String orderId, String orderDetailId, int userId, int returnReason, String returnExplain, String returnProof, int returnType, int returnNum, int goodsState, double returnMoney) {
        QReFundBO qReFundBO = new QReFundBO(NetConfig.REFUND, commodityId, commodityDetailId, orderId, orderDetailId, userId, returnReason, returnExplain, returnProof, returnType, returnNum, goodsState, returnMoney);
        add(NetWork.getMyApi().subReFund(qReFundBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RReFundBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onNext(RReFundBO rReFundBO) {
                        mView.successful(rReFundBO);
                    }
                }));
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
                        mView.upLoadSuccessful(rUploadUrlBO);
                    }
                }));

    }
}
