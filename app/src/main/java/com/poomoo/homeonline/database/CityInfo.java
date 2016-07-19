package com.poomoo.homeonline.database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 类名 CityInfo
 * 描述 城市数据模型
 * 作者 李苜菲
 * 日期 2016/7/19 11:27
 */
public class CityInfo extends DataSupport {
    private int cityId;
    private String cityName;
    @Column(defaultValue = "1")
    private String isHot;
    private int provinceId;

    public CityInfo(int cityId, String cityName, String isHot, int provinceId) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.provinceId = provinceId;
        this.isHot = isHot;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
