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
    public String img;
    public String name;
    public int commodityId;
    public String price;
    public int count;
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
        if (this.commodityId == ((RCartCommodityBO) o).commodityId)
            return true;
        return false;
    }
}
