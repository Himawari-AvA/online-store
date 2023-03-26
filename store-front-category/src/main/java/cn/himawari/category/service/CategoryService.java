package cn.himawari.category.service;

import cn.himawari.utils.R;

public interface CategoryService {
    R byName(String categoryName);

    /**
     * 查询类别数据，进行返回
     * @return r 类别数据集合
     */
    R list();
}
