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
import com.poomoo.model.request.QCheckCodeBO;
import com.poomoo.model.request.QCodeBO;
import com.poomoo.model.request.QLoginBO;
import com.poomoo.model.request.QRegisterBO;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RClassifyBO;
import com.poomoo.model.response.RGrabBO;
import com.poomoo.model.response.RGuessBO;
import com.poomoo.model.response.RSpecialAdBO;
import com.poomoo.model.response.RTypeBO;


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
    Observable<List<RGuessBO>> GetGuess(@Body BaseRequest data);

    //商品分类
    @POST("app/call.json")
    Observable<List<RClassifyBO>> GetClassify(@Body BaseRequest data);
}
