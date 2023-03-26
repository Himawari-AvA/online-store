package cn.himawari.admin.service;

import cn.himawari.param.PageParam;
import cn.himawari.pojo.Category;
import cn.himawari.utils.R;

public interface CategoryService {

    /**
     * 分页查询实现
     * @param pageParam
     * @return
     */
    R pageList(PageParam pageParam);

    /**
     * 分类数据添加
     * @param category
     * @return
     */
    R save(Category category);

    /**
     * 根据Id删除类别数据
     * @param categoryId
     * @return
     */
    R remove(Integer categoryId);

    /**
     * 修改类别信息
     * @param category
     * @return
     */
    R update(Category category);
}
