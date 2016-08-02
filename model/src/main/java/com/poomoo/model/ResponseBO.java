/**
 * Copyright (c) 2015. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回参数的业务模型类
 * 作者: 李苜菲
 * 日期: 2015/11/27 09:28.
 */
public class ResponseBO<T> {
    public boolean result;    // "result":"true" 成功，false 失败
    public String msg = "";    // "msg":"请求成功",
    public String content = "";    // "content":"请求的结果集，主要正对查询功能"
    public List<T> records = new ArrayList<>();

    public ResponseBO(boolean result, String msg) {
        this.msg = msg;
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseBO{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", content='" + content + '\'' +
                ", records=" + records +
                '}';
    }
}
