package cn.himawari.product.controller;


import cn.himawari.param.ProductHistoryParam;
import cn.himawari.product.service.ProductService;
import cn.himawari.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductHistoryController {
@Autowired
    private ProductService productService;
    @PostMapping("history/list")
    public R productIds(@RequestBody @Validated ProductHistoryParam productHistoryParam, BindingResult result){

        if(result.hasErrors()){
            return R.ok("没有历史数据");
        }
        return productService.ids(productHistoryParam.getProductIds());

    }
}
