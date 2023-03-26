package cn.himawari.category.service;

import cn.himawari.param.PageParam;
import cn.himawari.pojo.Category;
import cn.himawari.utils.R;

public interface CategoryService {
    R byName(String categoryName);

    /**
     * 查询类别数据，进行返回
     * @return r 类别数据集合
     */
    R list();

    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    R listPage(PageParam pageParam);

    /**
     * 添加类别信息
     * @param category
     * @return
     */
    R adminSave(Category category);

    /**
     * 删除类别
     * @param categoryId
     * @return
     */
    R adminRemove(Integer categoryId);

    /**
     * 修改类别名称
     * @param category
     * @return
     */
    R adminUpdate(Category category);
}
