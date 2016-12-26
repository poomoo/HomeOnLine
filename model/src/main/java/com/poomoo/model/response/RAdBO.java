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
    public int commodityDetailId;
    public Integer commodityType;//1.普通商品，2抢购商品，3.新年活动商品，4活动商品，5.特价商品

    @Override
    public String toString() {
        return "RAdBO{" +
                "connect='" + connect + '\'' +
                ", advertisementPic='" + advertisementPic + '\'' +
                ", commodityId=" + commodityId +
                ", isCommodity=" + isCommodity +
                ", commodityType=" + commodityType +
                '}';
    }
}
