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
    private boolean result;    // "result":"true" 成功，false 失败
    private String msg = "";    // "msg":"请求成功",
    private String content = "";    // "content":"请求的结果集，主要正对查询功能"

    private T obj;
    private List<T> objList = new ArrayList<>();
    private int totalCount;

    public ResponseBO(boolean result, String msg) {
        this.msg = msg;
        this.result = result;
    }

    public boolean isSuccess() {
        if (result)
            return true;
        else
            return false;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public List<T> getObjList() {
        return objList;
    }

    public void setObjList(List<T> objList) {
        this.objList = objList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "ResponseBO{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", content='" + content + '\'' +
                ", obj=" + obj +
                ", objList=" + objList +
                ", totalCount=" + totalCount +
                '}';
    }
}
