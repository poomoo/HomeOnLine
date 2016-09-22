package com.poomoo.api.api;

import com.poomoo.api.NetConfig;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.request.QSignBO;
import com.poomoo.model.response.RSignBO;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 类名 PayApi
 * 描述 支付API
 * 作者 李苜菲
 * 日期 2016/8/18 10:18
 */
public interface PayApi {
    //签名
    @POST(NetConfig.suffix)
    Observable<RSignBO> sign(@Body QSignBO data);

    //验签
    @FormUrlEncoded
    @POST(NetConfig.check)
    Observable<ResponseBO> checkSign(@Field("resultStatus") String resultStatus, @Field("result") String result, @Field("memo") String memo);
}
