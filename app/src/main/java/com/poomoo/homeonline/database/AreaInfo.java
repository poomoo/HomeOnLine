package com.poomoo.homeonline.database;

import org.litepal.crud.DataSupport;

/**
 * 类名 AreaInfo
 * 描述 城区数据模型
 * 作者 李苜菲
 * 日期 2016/7/19 11:27
 */
public class AreaInfo extends DataSupport {
    private int areaId;
    private String areaName;
    private int cityId;

    public AreaInfo(int areaId, String areaName, int cityId) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.cityId = cityId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
