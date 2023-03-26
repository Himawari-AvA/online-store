package cn.himawari.category.service.impl;

import cn.himawari.category.mapper.CategoryMapper;
import cn.himawari.category.service.CategoryService;
import cn.himawari.pojo.Category;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public R byName(String categoryName) {

        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("category_name",categoryName);

        Category category = categoryMapper.selectOne(categoryQueryWrapper);

        if(category == null) {
            return R.fail("类别查询失败！");
        }
        log.info("CategoryServiceImpl.byName业务结束，结果是：{}","类别查询成功");
        return R.ok("类别查询成功！",category);

    }

    /**
     * 查询类别数据，进行返回
     *
     * @return r 类别数据集合
     */
    @Override
    public R list() {

        List<Category> categoryList = categoryMapper.selectList(null);
        R ok = R.ok("类别全部数据查询成功!", categoryList);
        log.info("CategoryServiceImpl.list业务结束，结果:{}",ok);
        return ok;
    }
}
