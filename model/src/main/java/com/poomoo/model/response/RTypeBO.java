/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

import java.util.List;

/**
 * 类名 RTypeBO
 * 描述 首页分类
 * 作者 李苜菲
 * 日期 2016/8/2 15:46
 */
public class RTypeBO {
    public String picUrl;
    public List<RCateBO> categotys;

    @Override
    public String toString() {
        return "RTypeBO{" +
                "picUrl='" + picUrl + '\'' +
                ", categotys=" + categotys +
                '}';
    }
}
