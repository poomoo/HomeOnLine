/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

import java.util.List;

/**
 * 类名 RClassifyBO
 * 描述 一级分类
 * 作者 李苜菲
 * 日期 2016/8/4 16:07
 */
public class RClassifyBO {
    public int id;
    public String categoryName;
    public String pcPic;
    public String pcReplacePic;
    public List<RSubClassifyBO> childrenList;

    @Override
    public String toString() {
        return "RClassifyBO{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", pcPic='" + pcPic + '\'' +
                ", pcReplacePic='" + pcReplacePic + '\'' +
                ", childrenList=" + childrenList +
                '}';
    }
}
