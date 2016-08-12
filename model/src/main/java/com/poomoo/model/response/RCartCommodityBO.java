/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

/**
 * 类名 RCartCommodityBO
 * 描述 购物车商品
 * 作者 李苜菲
 * 日期 2016/8/5 16:54
 */
public class RCartCommodityBO {
    public int id;//在购物车里面的ID
    public String listPic;
    public String commodityName;
    public int commodityId;
    public double commodityPrice;
    public int commodityNum;
    public int orderType; //0.普通商品，2.抢购商品，1.特价商品，4.活动商品，3新年活动商品
    public int commodityType;

    public boolean isBuyChecked;
    public boolean isEditChecked;

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
}
