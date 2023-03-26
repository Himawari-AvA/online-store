package cn.himawari.product.controller;

import cn.himawari.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductAdminController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/count")
    public Long adminCount(@RequestBody Integer categoryId){
        return productService.adminCount(categoryId);

    }

}