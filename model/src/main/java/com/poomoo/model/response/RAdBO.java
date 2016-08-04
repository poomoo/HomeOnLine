/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

/**
 * 广告
 * 作者: 李苜菲
 * 日期: 2016/4/22 10:08.
 */
public class RAdBO {
    public String connect;//广告链接
    public String advertisementPic;//广告图片
    public int commodityId;//商品主键
    public boolean isCommodity;//是否是商品广告

    @Override
    public String toString() {
        return "RAdBO{" +
                "connect='" + connect + '\'' +
                ", advertisementPic='" + advertisementPic + '\'' +
                ", commodityId=" + commodityId +
                ", isCommodity=" + isCommodity +
                '}';
    }
}
