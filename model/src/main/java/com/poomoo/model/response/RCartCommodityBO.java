/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

import java.io.Serializable;

/**
 * 类名 RCartCommodityBO
 * 描述 购物车商品
 * 作者 李苜菲
 * 日期 2016/8/5 16:54
 */
public class RCartCommodityBO implements Serializable{
    public int id;//在购物车里面的ID
    public String listPic;
    public String commodityName;
    public int commodityId;
    public int commodityDetailId;
    public int commodityDetailsId;
    public double commodityPrice;
    public int commodityNum;
    public int orderType; //0.普通商品，2.抢购商品，1.特价商品，4.活动商品，3新年活动商品
    public int commodityType;
    public boolean isFreePostage;//是否包邮 true-包邮

    public boolean isBuyChecked;
    public boolean isEditChecked;
    public Integer rushPurchaseId;

    public void toggleBuy() {
        this.isBuyChecked = !this.isBuyChecked;
    }

    public void setBuyChecked(boolean isChecked) {
        this.isBuyChecked = isChecked;
    }

    public void toggleEdit() {
        this.isEditChecked = !this.isEditChecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this.id == ((RCartCommodityBO) o).id)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "RCartCommodityBO{" +
                "id=" + id +
                ", listPic='" + listPic + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", commodityId=" + commodityId +
                ", commodityDetailId=" + commodityDetailId +
                ", commodityDetailsId=" + commodityDetailsId +
                ", commodityPrice=" + commodityPrice +
                ", commodityNum=" + commodityNum +
                ", orderType=" + orderType +
                ", commodityType=" + commodityType +
                ", isFreePostage=" + isFreePostage +
                ", isBuyChecked=" + isBuyChecked +
                ", isEditChecked=" + isEditChecked +
                ", rushPurchaseId=" + rushPurchaseId +
                '}';
    }
}
