package cn.himawari.product.controller;

import cn.himawari.pojo.Product;
import cn.himawari.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductSearchController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> allList(){
        return productService.allList();
    }
}
