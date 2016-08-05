/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

import java.util.List;

/**
 * 类名 RCartShopBO
 * 描述 购物车店铺
 * 作者 李苜菲
 * 日期 2016/8/5 16:54
 */
public class RCartShopBO {
    public int shopId;
    public String shop;
    public List<RCartCommodityBO> rCartCommodityBOs;
    public boolean isBuyChecked;
    public boolean isEditChecked;

    public void toggleBuy() {
        this.isBuyChecked = !this.isBuyChecked;
    }

    public void toggleEdit() {
        this.isEditChecked = !this.isEditChecked;
    }

    public int getChildrenCount() {
        return rCartCommodityBOs.size();
    }

    public RCartCommodityBO getChildItem(int index) {
        return rCartCommodityBOs.get(index);
    }

    public void setChildChecked(boolean isChecked) {
        for (RCartCommodityBO rCartCommodityBO : rCartCommodityBOs)
            rCartCommodityBO.setBuyChecked(isChecked);
    }


    @Override
    public boolean equals(Object o) {
        if (this.shopId == ((RCartShopBO) o).shopId)
            return true;
        return false;
    }
}
