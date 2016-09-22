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
import com.poomoo.homeonline.ui.activity.AddressListActivity;
import com.poomoo.homeonline.ui.fragment.MainFragment;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.request.QUserIdBO;
import com.poomoo.model.request.QVersion;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RGrabBO;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.RSpecialAdBO;
import com.poomoo.model.response.RTypeBO;
import com.poomoo.model.response.RVersionBO;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类名 MainFragmentPresenter
 * 描述 首页Presenter
 * 作者 李苜菲
 * 日期 2016/8/2 14:50
 */
public class MainFragmentPresenter extends BasePresenter<MainFragment> {
    private static final String TAG = "MainFragmentPresenter";

    @Inject
    public MainFragmentPresenter() {
    }

    /**
     * 获取首页滚动图片
     */
    public void getSlide() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.SLIDE);
        add(NetWork.getMyApi().GetSlide(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RAdBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loadSlideFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RAdBO> rAdBOs) {
                        mView.loadSlideSucceed(rAdBOs);
                    }
                }));
    }

    /**
     * 获取首页分类菜单
     */
    public void getType() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.TYPE);
        add(NetWork.getMyApi().GetType(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RTypeBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loadTypeFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RTypeBO rTypeBO) {
                        mView.loadTypeSucceed(rTypeBO);
                    }
                }));
    }

    /**
     * 获取专题广告
     */
    public void getSpecialAd() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.SPECIAL);
        add(NetWork.getMyApi().GetSpecialAd(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<RSpecialAdBO>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loadSpecialAdFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RSpecialAdBO rSpecialAdBO) {
                        mView.loadSpecialAdSucceed(rSpecialAdBO);
                    }
                }));
    }

    /**
     * 获取抢购列表
     */
    public void getGrabList() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.GRAB);
        add(NetWork.getMyApi().GetGrabList(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RGrabBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loadGrabListFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RGrabBO> rGrabBOs) {
                        mView.loadGrabListSucceed(rGrabBOs);
                    }
                }));
    }

    /**
     * 热门推荐
     */
    public void getHot() {
        BaseRequest baseRequest = new BaseRequest(NetConfig.HOT);
        add(NetWork.getMyApi().GetSlide(baseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RAdBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loadHotFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RAdBO> rGrabBOs) {
                        mView.loadHotSucceed(rGrabBOs);
                    }
                }));
    }

    /**
     * 猜你喜欢
     */
    public void getGuess(Integer userId) {
        QUserIdBO qUserIdBO = new QUserIdBO(NetConfig.GUESS, userId);
        add(NetWork.getMyApi().GetGuess(qUserIdBO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<RListCommodityBO>>() {
                    @Override
                    protected void onError(ApiException e) {
                        mView.loadGuessFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RListCommodityBO> rCommodityListBOs) {
                        mView.loadGuessSucceed(rCommodityListBOs);
                    }
                }));
    }
}
