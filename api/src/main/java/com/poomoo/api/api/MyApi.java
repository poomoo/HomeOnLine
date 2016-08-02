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
import com.poomoo.model.request.QCheckCodeBO;
import com.poomoo.model.request.QCodeBO;
import com.poomoo.model.request.QLoginBO;
import com.poomoo.model.request.QRegisterBO;


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
}