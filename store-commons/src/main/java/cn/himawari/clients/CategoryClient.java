package cn.himawari.clients;

import cn.himawari.param.PageParam;
import cn.himawari.pojo.Category;
import cn.himawari.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("category-service")
public interface CategoryClient {

    @GetMapping("/category/promo/{categoryName}")
    R byName(@PathVariable String categoryName);

    @GetMapping("/category/list")
    R list();

    @PostMapping("category/admin/list")
    R adminPageList(@RequestBody PageParam pageParam);

    @PostMapping("category/admin/save")
    R adminSave(@RequestBody Category category);

    @PostMapping("category/admin/remove")
    R adminRemove(@RequestBody Integer categoryId);

    @PostMapping("category/admin/update")
    R adminUpdate(@RequestBody Category category);
}
