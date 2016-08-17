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
package com.poomoo.api.api;

import com.poomoo.model.RUserBO;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.request.QAddCartBO;
import com.poomoo.model.request.QAddressBO;
import com.poomoo.model.request.QCancelCollectionBO;
import com.poomoo.model.request.QCategoryIdBO;
import com.poomoo.model.request.QCheckCodeBO;
import com.poomoo.model.request.QClassifyListBO;
import com.poomoo.model.request.QCodeBO;
import com.poomoo.model.request.QFeedBackBO;
import com.poomoo.model.request.QIdBO;
import com.poomoo.model.request.QPageBO;
import com.poomoo.model.request.QCommodityInfoBO;
import com.poomoo.model.request.QCountBO;
import com.poomoo.model.request.QDeleteBO;
import com.poomoo.model.request.QHistory;
import com.poomoo.model.request.QLoginBO;
import com.poomoo.model.request.QRegisterBO;
import com.poomoo.model.request.QSearchBO;
import com.poomoo.model.request.QSpecificationBO;
import com.poomoo.model.request.QTransferPriceBO;
import com.poomoo.model.request.QUpdateInfoBO;
import com.poomoo.model.request.QUserIdBO;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RCartShopBO;
import com.poomoo.model.response.RClassifyBO;
import com.poomoo.model.response.RClassifyInfoBO;
import com.poomoo.model.response.RCollectBO;
import com.poomoo.model.response.RCommodityCount;
import com.poomoo.model.response.RCommodityInfoBO;
import com.poomoo.model.response.RGrabBO;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.RReceiptBO;
import com.poomoo.model.response.RSpecialAdBO;
import com.poomoo.model.response.RSpecificationBO;
import com.poomoo.model.response.RTransferPriceBO;
import com.poomoo.model.response.RTypeBO;
import com.poomoo.model.response.RZoneBO;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscription;

/**
 * 类名 MyApi
 * 描述 我的接口
 * 作者 李苜菲
 * 日期 2016/8/1 9:59
 */
public interface MyApi {
    @POST("app/call.json")
    Observable<RUserBO> Login(@Body QLoginBO data);

    @POST("app/call.json")
    Observable<ResponseBO> GetCode(@Body QCodeBO data);

    @POST("app/call.json")
    Observable<ResponseBO> CheckCode(@Body QCheckCodeBO data);

    @POST("app/call.json")
    Observable<ResponseBO> Register(@Body QRegisterBO data);

    //获取首页滚动的广告 热门推荐
    @POST("app/call.json")
    Observable<List<RAdBO>> GetSlide(@Body BaseRequest data);

    //获取首页分类
    @POST("app/call.json")
    Observable<RTypeBO> GetType(@Body BaseRequest data);

    //获取首页的专题广告
    @POST("app/call.json")
    Observable<RSpecialAdBO> GetSpecialAd(@Body BaseRequest data);

    //获取首页抢购列表
    @POST("app/call.json")
    Observable<List<RGrabBO>> GetGrabList(@Body BaseRequest data);

    //猜你喜欢
    @POST("app/call.json")
    Observable<List<RListCommodityBO>> GetGuess(@Body QUserIdBO data);

    //商品分类
    @POST("app/call.json")
    Observable<List<RClassifyBO>> GetClassify(@Body BaseRequest data);

    //商品详情
    @POST("app/call.json")
    Observable<RCommodityInfoBO> GetCommodityInfo(@Body QCommodityInfoBO data);

    //商品规格详情
    @POST("app/call.json")
    Observable<RSpecificationBO> GetCommodityInfoBySpecification(@Body QSpecificationBO data);

    //加入购物车
    @POST("app/call.json")
    Observable<ResponseBO> AddToCart(@Body QAddCartBO data);

    //获取购物车信息
    @POST("app/call.json")
    Observable<List<RCartShopBO>> GetCartInfo(@Body QUserIdBO data);

    //修改购物车某个商品的数量
    @POST("app/call.json")
    Observable<RCommodityCount> ChangeCommodityCount(@Body QCountBO data);

    //删除购物车商品
    @POST("app/call.json")
    Observable<ResponseBO> DeleteCartCommodity(@Body QDeleteBO data);

    //增加浏览记录
    @POST("app/call.json")
    Observable<ResponseBO> AddHistory(@Body QHistory data);

    //获取浏览记录
    @POST("app/call.json")
    Observable<List<RListCommodityBO>> GetHistory(@Body QPageBO data);

    //删除浏览记录
    @POST("app/call.json")
    Observable<ResponseBO> DeleteHistory(@Body QCancelCollectionBO data);

    //收藏记录
    @POST("app/call.json")
    Observable<List<RCollectBO>> GetCollectionList(@Body QPageBO data);

    //取消收藏
    @POST("app/call.json")
    Observable<ResponseBO> CancelCollection(@Body QCancelCollectionBO data);

    //收货地址列表
    @POST("app/call.json")
    Observable<List<RReceiptBO>> GetAddressList(@Body QUserIdBO data);

    //获取区域信息
    @POST("app/call.json")
    Observable<List<RZoneBO>> GetZoneInfo(@Body BaseRequest data);

    //新建收货地址
    @POST("app/call.json")
    Observable<ResponseBO> NewAddress(@Body QAddressBO data);

    //更新收货地址
    @POST("app/call.json")
    Observable<ResponseBO> UpdateAddress(@Body QAddressBO data);

    //删除收货地址
    @POST("app/call.json")
    Observable<ResponseBO> DeleteAddress(@Body QIdBO data);

    //意见反馈
    @POST("app/call.json")
    Observable<ResponseBO> FeedBack(@Body QFeedBackBO data);

    //修改个人信息
    @POST("app/call.json")
    Observable<ResponseBO> UpdateUserInfo(@Body QUpdateInfoBO data);

    //商品搜素
    @POST("app/call.json")
    Observable<List<RListCommodityBO>> search(@Body QSearchBO data);

    //导航商品列表
    @POST("app/call.json")
    Observable<RClassifyInfoBO> getClassifyInfo(@Body QCategoryIdBO data);

    //三级分类下的商品
    @POST("app/call.json")
    Observable<List<RListCommodityBO>> getClassifyCommodityList(@Body QClassifyListBO data);

    //计算运费
    @POST("app/call.json")
    Observable<RTransferPriceBO> getTransferPrice(@Body QTransferPriceBO data);

    //默认地址
    @POST("app/call.json")
    Observable<RReceiptBO> getDefaultAddress(@Body QUserIdBO data);
}
