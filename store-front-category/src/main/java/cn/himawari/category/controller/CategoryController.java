package cn.himawari.category.controller;

import cn.himawari.category.service.CategoryService;
import cn.himawari.utils.R;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable String categoryName){
//        log.info("----------------------CategoryController进入了-----------------");
        if(StringUtils.isEmpty(categoryName)){
            return R.fail("类别名称为null,无法查询类别数据！");
        }
        return categoryService.byName(categoryName);
    }

    @GetMapping("list")
    public R list(){

        return categoryService.list();
    }

}
