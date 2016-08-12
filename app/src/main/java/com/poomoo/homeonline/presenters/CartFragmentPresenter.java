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
import com.poomoo.homeonline.adapter.AddressListAdapter;
import com.poomoo.homeonline.ui.fragment.CartFragment;
import com.poomoo.homeonline.ui.fragment.MainFragment;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QCountBO;
import com.poomoo.model.request.QDeleteBO;
import com.poomoo.model.request.QUserIdBO;
import com.poomoo.model.response.RCartShopBO;
import com.poomoo.model.response.RCommodityCount;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 MainFragmentPresenter
 * 描述 购物车Presenter
 * 作者 李苜菲
 * 日期 2016/8/2 14:50
 */
public class CartFragmentPresenter extends BasePresenter<CartFragment> {
    @Inject
    public CartFragmentPresenter() {
    }

    /**
     * 获取购物车信息
     *
     * @param userId
     */
    public void getCartInfo(int userId) {
        QUserIdBO qUserIdBO = new QUserIdBO(NetConfig.CARTINFO, userId);
        add(NetWork.getMyApi().GetCartInfo(qUserIdBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RCartShopBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.getInfoFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RCartShopBO> rCartShopBOs) {
                        mView.getInfoSucceed(rCartShopBOs);
                    }
                }));
    }

    /**
     * 修改某商品的数量
     *
     * @param cartId
     * @param cartNum
     */
    public void changeCount(int cartId, int cartNum,int commodityType) {
        QCountBO qCountBO = new QCountBO(NetConfig.CHANGCOUNT, cartId, cartNum,commodityType);
        add(NetWork.getMyApi().ChangeCommodityCount(qCountBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RCommodityCount>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.changeCountFailed();
                    }

                    @Override
                    public void onNext(RCommodityCount rCommodityCount) {
                        mView.changeCountSucceed(rCommodityCount);
                    }
                }));
    }

    /**
     * 删除购物车商品
     *
     * @param index
     */
    public void deleteCommodity(int[] index) {
        QDeleteBO qDeleteBO = new QDeleteBO(NetConfig.DELETECART, index);
        add(NetWork.getMyApi().DeleteCartCommodity(qDeleteBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.deleteFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBO responseBO) {
                        mView.deleteSucceed();
                    }
                }));

    }

}
