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

import com.poomoo.api.NetConfig;
import com.poomoo.model.RUserBO;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.BaseRequest;
import com.poomoo.model.request.QAddCartBO;
import com.poomoo.model.request.QAddressBO;
import com.poomoo.model.request.QCancelCollectionBO;
import com.poomoo.model.request.QCancelOrderBO;
import com.poomoo.model.request.QCategoryIdBO;
import com.poomoo.model.request.QChangeReFundBO;
import com.poomoo.model.request.QCheckCodeBO;
import com.poomoo.model.request.QClassifyListBO;
import com.poomoo.model.request.QCodeBO;
import com.poomoo.model.request.QCollectBO;
import com.poomoo.model.request.QCommodityInfoBO;
import com.poomoo.model.request.QCountBO;
import com.poomoo.model.request.QDeleteBO;
import com.poomoo.model.request.QEvaluateBO;
import com.poomoo.model.request.QFeedBackBO;
import com.poomoo.model.request.QHistory;
import com.poomoo.model.request.QIdBO;
import com.poomoo.model.request.QIsCollectBO;
import com.poomoo.model.request.QLoginBO;
import com.poomoo.model.request.QOrderBO;
import com.poomoo.model.request.QOrderId;
import com.poomoo.model.request.QOrderListBO;
import com.poomoo.model.request.QPageBO;
import com.poomoo.model.request.QReFundBO;
import com.poomoo.model.request.QReFundInfoBO;
import com.poomoo.model.request.QRegisterBO;
import com.poomoo.model.request.QSearchBO;
import com.poomoo.model.request.QSpecificationBO;
import com.poomoo.model.request.QTransferPriceBO;
import com.poomoo.model.request.QUpdateInfoBO;
import com.poomoo.model.request.QUserBO;
import com.poomoo.model.request.QUserIdBO;
import com.poomoo.model.request.QVersion;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RCartNumBO;
import com.poomoo.model.response.RCartShopBO;
import com.poomoo.model.response.RClassifyBO;
import com.poomoo.model.response.RClassifyInfoBO;
import com.poomoo.model.response.RCollectBO;
import com.poomoo.model.response.RCommodityCount;
import com.poomoo.model.response.RCommodityInfoBO;
import com.poomoo.model.response.RDataBO;
import com.poomoo.model.response.RGrabBO;
import com.poomoo.model.response.RIndexBO;
import com.poomoo.model.response.RIsCollect;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.ROrderBO;
import com.poomoo.model.response.ROrderListBO;
import com.poomoo.model.response.RReFundBO;
import com.poomoo.model.response.RReFundInfoBO;
import com.poomoo.model.response.RReceiptBO;
import com.poomoo.model.response.RSpecialAdBO;
import com.poomoo.model.response.RSpecificationBO;
import com.poomoo.model.response.RTicketBO;
import com.poomoo.model.response.RTransferPriceBO;
import com.poomoo.model.response.RTypeBO;
import com.poomoo.model.response.RVersionBO;
import com.poomoo.model.response.RZoneBO;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 类名 MyApi
 * 描述 我的接口
 * 作者 李苜菲
 * 日期 2016/8/1 9:59
 */
public interface MyApi {
    @POST(NetConfig.suffix)
    Observable<RUserBO> Login(@Body QLoginBO data);

    @POST(NetConfig.suffix)
    Observable<ResponseBO> GetCode(@Body QCodeBO data);

    @POST(NetConfig.suffix)
    Observable<ResponseBO> CheckCode(@Body QCheckCodeBO data);

    @POST(NetConfig.suffix)
    Observable<ResponseBO> Register(@Body QRegisterBO data);

    //获取首页滚动的广告 热门推荐
    @POST(NetConfig.suffix)
    Observable<List<RAdBO>> GetSlide(@Body BaseRequest data);

    //获取首页分类
    @POST(NetConfig.suffix)
    Observable<RTypeBO> GetType(@Body BaseRequest data);

    //获取首页的专题广告
    @POST(NetConfig.suffix)
    Observable<RSpecialAdBO> GetSpecialAd(@Body BaseRequest data);

    //获取首页抢购列表
    @POST(NetConfig.suffix)
    Observable<List<RGrabBO>> GetGrabList(@Body BaseRequest data);

    //猜你喜欢
    @POST(NetConfig.suffix)
    Observable<List<RListCommodityBO>> GetGuess(@Body QUserIdBO data);

    //商品分类
    @POST(NetConfig.suffix)
    Observable<List<RClassifyBO>> GetClassify(@Body BaseRequest data);

    //商品详情
    @POST(NetConfig.suffix)
    Observable<RCommodityInfoBO> GetCommodityInfo(@Body QCommodityInfoBO data);

    //商品规格详情
    @POST(NetConfig.suffix)
    Observable<RSpecificationBO> GetCommodityInfoBySpecification(@Body QSpecificationBO data);

    //加入购物车
    @POST(NetConfig.suffix)
    Observable<ResponseBO> AddToCart(@Body QAddCartBO data);

    //购物车数量
    @POST(NetConfig.suffix)
    Observable<RCartNumBO> GetCartNum(@Body QUserIdBO data);

    //获取购物车信息
    @POST(NetConfig.suffix)
    Observable<List<RCartShopBO>> GetCartInfo(@Body QUserIdBO data);

    //修改购物车某个商品的数量
    @POST(NetConfig.suffix)
    Observable<RCommodityCount> ChangeCommodityCount(@Body QCountBO data);

    //删除购物车商品
    @POST(NetConfig.suffix)
    Observable<ResponseBO> DeleteCartCommodity(@Body QDeleteBO data);

    //增加浏览记录
    @POST(NetConfig.suffix)
    Observable<ResponseBO> AddHistory(@Body QHistory data);

    //获取浏览记录
    @POST(NetConfig.suffix)
    Observable<List<RListCommodityBO>> GetHistory(@Body QPageBO data);

    //删除浏览记录
    @POST(NetConfig.suffix)
    Observable<ResponseBO> DeleteHistory(@Body QCancelCollectionBO data);

    //收藏记录
    @POST(NetConfig.suffix)
    Observable<List<RCollectBO>> GetCollectionList(@Body QPageBO data);

    //取消收藏
    @POST(NetConfig.suffix)
    Observable<ResponseBO> CancelCollection(@Body QCancelCollectionBO data);

    //收货地址列表
    @POST(NetConfig.suffix)
    Observable<List<RReceiptBO>> GetAddressList(@Body QUserIdBO data);

    //获取区域信息
    @POST(NetConfig.suffix)
    Observable<List<RZoneBO>> GetZoneInfo(@Body BaseRequest data);

    //新建收货地址
    @POST(NetConfig.suffix)
    Observable<ResponseBO> NewAddress(@Body QAddressBO data);

    //更新收货地址
    @POST(NetConfig.suffix)
    Observable<ResponseBO> UpdateAddress(@Body QAddressBO data);

    //删除收货地址
    @POST(NetConfig.suffix)
    Observable<ResponseBO> DeleteAddress(@Body QIdBO data);

    //意见反馈
    @POST(NetConfig.suffix)
    Observable<ResponseBO> FeedBack(@Body QFeedBackBO data);

    //修改个人信息
    @POST(NetConfig.suffix)
    Observable<ResponseBO> UpdateUserInfo(@Body QUpdateInfoBO data);

    //商品搜素
    @POST(NetConfig.suffix)
    Observable<List<RListCommodityBO>> search(@Body QSearchBO data);

    //导航商品列表
    @POST(NetConfig.suffix)
    Observable<RClassifyInfoBO> getClassifyInfo(@Body QCategoryIdBO data);

    //导航商品列表
    @POST(NetConfig.suffix)
    Observable<List<RListCommodityBO>> getClassifyInfoList(@Body QCategoryIdBO data);

    //三级分类下的商品
    @POST(NetConfig.suffix)
    Observable<List<RListCommodityBO>> getClassifyCommodityList(@Body QClassifyListBO data);

    //计算运费
    @POST(NetConfig.suffix)
    Observable<RTransferPriceBO> getTransferPrice(@Body QTransferPriceBO data);

    //默认地址
    @POST(NetConfig.suffix)
    Observable<RReceiptBO> getDefaultAddress(@Body QUserIdBO data);

    //订单列表
    @POST(NetConfig.suffix)
    Observable<List<ROrderListBO>> getOrderList(@Body QOrderListBO data);

    //收藏
    @POST(NetConfig.suffix)
    Observable<ResponseBO> collect(@Body QCollectBO data);

    //是否收藏
    @POST(NetConfig.suffix)
    Observable<RIsCollect> isCollect(@Body QIsCollectBO data);

    //提交订单
    @POST(NetConfig.suffix)
    Observable<ROrderBO> subOrder(@Body QOrderBO data);

    //确认收货
    @POST(NetConfig.suffix)
    Observable<ResponseBO> confirm(@Body QOrderId data);

    //取消订单
    @POST(NetConfig.suffix)
    Observable<ResponseBO> cancelOrder(@Body QCancelOrderBO data);

    //申请退款
    @POST(NetConfig.suffix)
    Observable<RReFundBO> subReFund(@Body QReFundBO data);

    //修改退款
    @POST(NetConfig.suffix)
    Observable<ResponseBO> changeReFund(@Body QChangeReFundBO data);

    //退款详情
    @POST(NetConfig.suffix)
    Observable<RReFundInfoBO> getReFundInfo(@Body QReFundInfoBO data);

    //获取地址详情
    @POST(NetConfig.suffix)
    Observable<RReceiptBO> getAddressById(@Body QIdBO qIdBO);

    //修改密码
    @POST(NetConfig.suffix)
    Observable<ResponseBO> changePW(@Body QUserBO data);

    //评价
    @POST(NetConfig.suffix)
    Observable<ResponseBO> Evaluate(@Body QEvaluateBO data);

    //检查更新
    @POST(NetConfig.suffix)
    Observable<RVersionBO> CheckUpdate(@Body QVersion data);

    //检查更新
    @POST(NetConfig.suffix)
    Observable<RTicketBO> getTickets(@Body QUserIdBO data);

    //用券须知
    @POST(NetConfig.suffix)
    Observable<RDataBO> getData(@Body BaseRequest data);

    //引导页
    @POST(NetConfig.suffix)
    Observable<List<RIndexBO>> getIndex(@Body BaseRequest data);
}
