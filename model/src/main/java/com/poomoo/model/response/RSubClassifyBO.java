/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

import java.util.List;

/**
 * 类名 RClassifyBO
 * 描述 二级分类
 * 作者 李苜菲
 * 日期 2016/8/4 16:07
 */
public class RSubClassifyBO {
    public int id;
    public String categoryName;
    public List<RThirdClassifyBO> childrenList;

    @Override
    public String toString() {
        return "RSubClassifyBO{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", childrenList=" + childrenList +
                '}';
    }
}
