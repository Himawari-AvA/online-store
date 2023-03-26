package cn.himawari.product.controller;

import cn.himawari.param.ProductIdParam;
import cn.himawari.param.ProductIdsParam;
import cn.himawari.param.ProductPromoParam;
import cn.himawari.param.ProductSearchParam;
import cn.himawari.product.service.ProductService;
import cn.himawari.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/promo")
    public R promo(@RequestBody @Validated ProductPromoParam productPromoParam, BindingResult result){
        log.info("-----ProductController正常1-----");
        if(result.hasErrors()){
            return R.fail("数据查询失败！");
        }
        log.info("-----ProductController正常2-----");
        log.info(productPromoParam.getCategoryName());
        return productService.promo(productPromoParam.getCategoryName());
    }

    @PostMapping("/category/list")
    public R clist(){
        return productService.clist();
    }


    @PostMapping("bycategory")
    public R byCategory(@RequestBody @Validated ProductIdsParam productIdsParam,BindingResult result){
        log.info("-----ProductController正常bycategory-----");
        if(result.hasErrors()){
            return R.fail("查询类别失败！");
        }
        return productService.byCategory(productIdsParam);
    }

    @PostMapping("all")
    public R all(@RequestBody @Validated ProductIdsParam productIdsParam,BindingResult result){
//        log.info("-----ProductController正常all-----");
        if(result.hasErrors()){
//            log.info("-----查询失败-----{}",result);
            return R.fail("查询类别失败！");
        }
        return productService.byCategory(productIdsParam);
    }

    @PostMapping("detail")
    public R detail(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("查询商品详情失败！");
        }
        return productService.detail(productIdParam.getProductID());
    }

    @PostMapping("pictures")
    public R pictures(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("查询商品图片详情失败！");
        }
        return productService.pictures(productIdParam.getProductID());
    }


    @PostMapping("search")
    public R search(@RequestBody ProductSearchParam productSearchParam){
        return productService.search(productSearchParam);
    }

}
