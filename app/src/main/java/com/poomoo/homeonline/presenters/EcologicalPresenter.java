package com.poomoo.homeonline.presenters;

import com.poomoo.api.AbsAPICallback;
import com.poomoo.api.ApiException;
import com.poomoo.api.NetConfig;
import com.poomoo.api.NetWork;
import com.poomoo.homeonline.ui.activity.EcologicalActivity;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.request.QAddCartBO;
import com.poomoo.model.response.RNewSpecialBO;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 EcologicalPresenter
 * 描述 生态有机
 * 作者 李苜菲
 * 日期 2017/1/17 9:46
 */
public class EcologicalPresenter extends BasePresenter<EcologicalActivity> {
    @Inject
    public EcologicalPresenter() {
    }

    public void getInfo() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.ECOLOGICAL);
        add(NetWork.getMyApi().getEcologicalInfo(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RNewSpecialBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getInfoFailed();
                    }

                    @Override
                    public void onNext(RNewSpecialBO rNewSpecialBO) {
                        mView.getInfoSuccessful(rNewSpecialBO);
                    }
                }));

    }

    /**
     * 添加到购物车
     *
     * @param commodityId
     * @param commodityName
     * @param commodityType
     * @param commodityNum
     * @param listPic
     * @param commodityDetailId
     */
    public void addToCart(int userId, int newActivityId, int commodityId, String commodityName, int commodityType, int commodityNum, String listPic, int commodityDetailId, Integer rushPurchaseId) {
        QAddCartBO qAddCartBO = new QAddCartBO(NetConfig.ADDCART, userId, -1, newActivityId, commodityDetailId, listPic, commodityNum, rushPurchaseId, commodityType, commodityId, commodityName);
        add(NetWork.getMyApi().AddToCart(qAddCartBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.addToCartFailed();
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.addToCartSucceed();
                    }
                }));
    }
}
