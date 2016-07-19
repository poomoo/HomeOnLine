package com.poomoo.homeonline.database;

import org.litepal.crud.DataSupport;

/**
 * 类名 ProvinceInfo
 * 描述 省份数据模型
 * 作者 李苜菲
 * 日期 2016/7/19 11:28
 */
public class ProvinceInfo extends DataSupport {
    private int provinceId;
    private String provinceName;

    public ProvinceInfo(int provinceId, String provinceName) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
