package com.poomoo.api;

import com.poomoo.model.ResponseBO;

/**
 * @author 李苜菲
 * @ClassName HttpCallbackListener
 * @Description TODO 回调接口
 * @date 2015-7-24 下午3:08:02
 */
public interface HttpCallbackListener {
    void onFinish(final ResponseBO responseData);

    void onError(final Exception e);
}
