/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

/**
 * 类名 RClassifyBO
 * 描述 三级分类
 * 作者 李苜菲
 * 日期 2016/8/4 16:07
 */
public class RThirdClassifyBO {
    public int id;
    public String categoryName;

    @Override
    public String toString() {
        return "RThirdClassifyBO{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
