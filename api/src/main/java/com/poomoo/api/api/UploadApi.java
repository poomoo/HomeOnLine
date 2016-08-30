package com.poomoo.api.api;


import com.poomoo.model.response.RUploadUrlBO;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * 类名 UploadApi
 * 描述 上传图片
 * 作者 李苜菲
 * 日期 2016/8/15 13:44
 */
public interface UploadApi {
    @Multipart
    @POST("jyzx/app/upfile/picture.json")
    Observable<RUploadUrlBO> uploadPic(@PartMap Map<String, RequestBody> params);
}
