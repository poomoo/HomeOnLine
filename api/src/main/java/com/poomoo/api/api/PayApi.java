package com.poomoo.api.api;

import com.poomoo.model.ResponseBO;

import retrofit2.http.POST;
import rx.Observable;

/**
 * 类名 PayApi
 * 描述 支付API
 * 作者 李苜菲
 * 日期 2016/8/18 10:18
 */
public interface PayApi {
    @POST("/app/pay/alipay_signaturs.html")
    Observable<ResponseBO> sign();
}
