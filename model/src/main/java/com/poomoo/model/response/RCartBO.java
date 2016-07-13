/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

import java.util.List;

/**
 * 作者: 李苜菲
 * 日期: 2016/7/11 16:28.
 */
public class RCartBO {
    public int shopId;
    public String shop;
    public List<RCommodityBO> rCommodityBOs;
    public boolean isBuyChecked;
    public boolean isEditChecked;

    public void toggleBuy() {
        this.isBuyChecked = !this.isBuyChecked;
    }

    public void toggleEdit() {
        this.isEditChecked = !this.isEditChecked;
    }

    public int getChildrenCount() {
        return rCommodityBOs.size();
    }

    public RCommodityBO getChildItem(int index) {
        return rCommodityBOs.get(index);
    }

    public void setChildChecked(boolean isChecked) {
        for (RCommodityBO rCommodityBO : rCommodityBOs)
            rCommodityBO.setBuyChecked(isChecked);
    }


    @Override
    public boolean equals(Object o) {
        if (this.shopId == ((RCartBO) o).shopId)
            return true;
        return false;
    }
}
